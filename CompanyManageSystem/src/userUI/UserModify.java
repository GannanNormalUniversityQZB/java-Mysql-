package userUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;

import Tools.AuthorityPassword;
import Tools.JDBC;
import Tools.SetCenterSize;
import Tools.textEmpty;
import UI.CompanyManage;



public class UserModify extends JDialog implements ActionListener{
	//定义成员变量
	/*标签区*/
	JLabel label_name=new JLabel("用户名：");
	JLabel label_new_password=new JLabel("新密码：");
	JLabel label_id=new JLabel("身    份：");
	public JLabel companyLogo=new JLabel(new ImageIcon("images\\Logo\\companyLogo2.png"));
	
	
	/*下拉列表区*/
	String array_id[]= {"----------------请选择身份----------------","管理员","员工","开发人员"};
	JComboBox<String> comboBox_id=new JComboBox<>(array_id);
	
	/*按钮区*/
	JButton button_check=new JButton("查询");
	JButton button_modify=new JButton("修改");
	JButton button_cancel=new JButton("取消");
	
	
	/*文本框区*/
	JTextField text_name=new JTextField(12);
	JTextField text_new_password=new JTextField(12);
	/*设置id记录器*/
	String temp_name;
	
	/*最高权限密码*/
	String password=AuthorityPassword.authority;
	
	//定义构造函数
	public UserModify(CompanyManage view) {
		super(view);
		//设置窗口
		setLayout(new FlowLayout());
		setTitle("修改用户");
		setBounds(new SetCenterSize().getCenterBounds(500,400));	
		setModal(true);	
		/*设置图标*/
		Image icon = Toolkit.getDefaultToolkit().getImage("images\\icon\\icon3.png");
		setIconImage(icon);
		
		/*设置中间容器*/
		
		/*设置最外层容器*/
		Box Vbox=Box.createVerticalBox();
		
		/*设置内层容器*/
		Box name_box=Box.createHorizontalBox();
		Box new_password_box=Box.createHorizontalBox();
		Box id_box=Box.createHorizontalBox();
		Box button_box=Box.createHorizontalBox();
		JPanel panel_button=new JPanel();
		
		/*组装*/
		name_box.add(label_name);
		name_box.add(text_name);

		
		new_password_box.add(label_new_password);
		new_password_box.add(text_new_password);
		
	
		
		id_box.add(label_id);
		id_box.add(comboBox_id);
		
		Vbox.add(Box.createVerticalStrut(15));
		Vbox.add(name_box);
		Vbox.add(Box.createVerticalStrut(30));
		Vbox.add(new_password_box);
		Vbox.add(Box.createVerticalStrut(30));

		Vbox.add(id_box);
		Vbox.add(Box.createVerticalStrut(30));
		
		button_box.add(button_check);
		button_box.add(Box.createHorizontalStrut(30));
		button_box.add(button_modify);
		button_box.add(Box.createHorizontalStrut(30));
		button_box.add(button_cancel);
		
		Vbox.add(button_box);
		Vbox.add(Box.createVerticalGlue());
		
		add(companyLogo);
		add(Vbox);
		
		
		//设置组件初始状态
		
		text_new_password.setEditable(false);
		comboBox_id.setEnabled(false);
		button_modify.setEnabled(false);
		
		
		//未按钮添加监听器
		button_check.addActionListener(this);
		button_modify.addActionListener(this);
		button_cancel.addActionListener(this);
		
		
		
		//窗口结尾处理
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	
	}
	
	


	//重写接口函数
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==button_check) {
			temp_name=this.text_name.getText().trim();
			/*定义数据库局部变量*/
			Connection con=JDBC.getConnection();
			try {
				Statement sql=con.createStatement();
				ResultSet rs=sql.executeQuery("select * from administrators where name="+"'"+temp_name+"'");
				
				//测试语句
				System.out.println(123);
				
				rs.next();
				if(rs.getString(1).equals(temp_name)) {
					//设置编辑权限
					text_new_password.setEditable(true);
					comboBox_id.setEnabled(true);
					button_modify.setEnabled(true);
					text_name.setEditable(false);
					//提示用户
					JOptionPane.showMessageDialog(this,"查询到用户："+temp_name+"成功~！\n您现在可以修改信息~！");
				}
			} 
			catch (SQLException e1) {
				System.out.println("sql出现错误~！");
				JOptionPane.showMessageDialog(this,"未查询到用户："+temp_name);
				//设置编辑权限
				text_new_password.setEditable(false);
				comboBox_id.setEnabled(false);
				button_modify.setEnabled(false);
			}
			
		}
		
		//button_modify
		if(e.getSource()==button_modify) {
			/*定义局部变量区*/
			String temp_name=this.text_name.getText().trim();
			String temp_new_password=this.text_new_password.getText().trim();
			String temp_id=(String) this.comboBox_id.getSelectedItem();
			if(! textEmpty.textEmpty(temp_name,temp_new_password) && !temp_id.equals("----------------请选择身份----------------")) {
				String comfirm_password=JOptionPane.showInputDialog(this, "请输入最高权限密码\n如果您已忘记，请联系技术开发人员~！");
				//只有最高权限密码正确，才能注册
				if(comfirm_password.equals(password)) {
				/*定义数据库局部变量*/
				Connection con=JDBC.getConnection();
				try {
					PreparedStatement pre_sql=con.prepareStatement("update	administrators set name=? , password=? , identity=? where name=?");
					pre_sql.setString(1,temp_name);
				    pre_sql.setString(2,temp_new_password);
					pre_sql.setString(3,temp_id);
					pre_sql.setString(4,temp_name);
					int i=pre_sql.executeUpdate();
					if(i!=0){
						System.out.println(i);
						JOptionPane.showMessageDialog(this, "修改成功~！");
					}	
				} 
				catch (SQLException e1) {System.out.println(e1);}
				}
			}
				else {
						JOptionPane.showMessageDialog(this, "请填写好正确的信息", "警告",JOptionPane.WARNING_MESSAGE);
					}
				}
		
		//button_cancel区
		if(e.getSource()==button_cancel) {
			dispose();
		}
	}
	


	//测试函数
	public static void main(String[] args) {
		new UserModify(null);

	}

}
