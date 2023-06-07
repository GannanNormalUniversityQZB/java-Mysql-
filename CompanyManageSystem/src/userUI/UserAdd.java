package userUI;
import javax.swing.*;

import Tools.AuthorityPassword;
import Tools.JDBC;
import Tools.SetCenterSize;
import UI.*;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
public class UserAdd extends JDialog implements ActionListener{
	//定义成员变量
	/*标签区*/
	JLabel label_name=new JLabel("用户名：");
	JLabel label_password=new JLabel("密    码：");
	JLabel label_password_2=new JLabel("确    认：");
	JLabel label_id=new JLabel("身    份：");
	public JLabel companyLogo=new JLabel(new ImageIcon("images\\Logo\\companyLogo2.png"));
	
	/*下拉列表区*/
	String array_id[]= {"----------------请选择身份----------------","管理员","员工","开发人员"};
	JComboBox<String> comboBox_id=new JComboBox<>(array_id);
	
	/*按钮区*/
	JButton button_add=new JButton("添加");
	JButton button_reset=new JButton("重置");
	JButton button_cancel=new JButton("取消");
	
	/*文本框区*/
	JTextField text_name=new JTextField(12);
	JTextField text_password=new JTextField(12);
	JTextField text_password_2=new JTextField(12);
	
	/*最高权限密码*/
	String password=AuthorityPassword.authority;
	
	//定义构造函数
	public UserAdd(CompanyManage view) {
		super(view);
		//设置窗口
		setLayout(new FlowLayout());
		setTitle("添加用户");
		setBounds(new SetCenterSize().getCenterBounds(500,450));	
		setModal(true);
		/*设置图标*/
		Image icon = Toolkit.getDefaultToolkit().getImage("images\\icon\\icon3.png");
		setIconImage(icon);
		
		//添加组件区
		/*设置中间容器*/
		
		/*设置最外层容器*/
		Box Vbox=Box.createVerticalBox();
		
		/*设置内层容器*/
		Box name_box=Box.createHorizontalBox();
		Box password_box=Box.createHorizontalBox();
		Box password_2_box=Box.createHorizontalBox();
		Box id_box=Box.createHorizontalBox();
		Box button_box=Box.createHorizontalBox();
		JPanel panel_button=new JPanel();
		
		/*组装*/
		name_box.add(label_name);
		name_box.add(text_name);
		
		password_box.add(label_password);
		password_box.add(text_password);
		
		password_2_box.add(label_password_2);
		password_2_box.add(text_password_2);
		
		id_box.add(label_id);
		id_box.add(comboBox_id);
		
		Vbox.add(Box.createVerticalStrut(15));
		Vbox.add(name_box);
		Vbox.add(Box.createVerticalStrut(30));
		Vbox.add(password_box);
		Vbox.add(Box.createVerticalStrut(30));
		Vbox.add(password_2_box);
		Vbox.add(Box.createVerticalStrut(30));
		Vbox.add(id_box);
		Vbox.add(Box.createVerticalStrut(30));
		
		button_box.add(button_add);
		button_box.add(Box.createHorizontalStrut(80));
		button_box.add(button_cancel);
		
		Vbox.add(button_box);
		Vbox.add(Box.createVerticalGlue());
		
		add(companyLogo);
		add(Vbox);
		
		//为按钮添加监听器
		button_add.addActionListener(this);
		button_cancel.addActionListener(this);
		
		//窗口结尾处理
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
	
	
	
	//重写接口函数
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==button_add) {
			/*定义关于组件的局部变量*/
			String temp_name=this.text_name.getText().trim();
			String temp_password=this.text_password.getText().trim();
			String temp_password_2=this.text_password_2.getText().trim();
			String temp_id=(String)this.comboBox_id.getSelectedItem();
			if(!textEmpty(temp_name, temp_password, temp_password_2, temp_id) && !temp_id.equals("----------------请选择身份----------------")) {
				//确定两次输入的密码相同
				if(temp_password.equals(temp_password_2)) {
				String comfirm_password=JOptionPane.showInputDialog(this, "请输入最高权限密码\n如果您已忘记，请联系技术开发人员~！");
				//只有最高权限密码正确，才能注册
				if(comfirm_password.equals(password)) {
				//测试语句
				System.out.println(temp_name+temp_password+temp_password_2+temp_id);
				/*定义数据库局部变量*/
				Connection con=JDBC.getConnection();
				try {
					PreparedStatement pre_sql=con.prepareStatement("insert into administrators values(?,?,?)");
					
					pre_sql.setString(1,temp_name);
					pre_sql.setString(2,temp_password);
					pre_sql.setString(3,temp_id);
					//测试语句
					System.out.println(temp_name+temp_password+temp_password_2+temp_id);
					int i=pre_sql.executeUpdate();
					if(i!=0){
						System.out.println(i);
						JOptionPane.showMessageDialog(this, "添加成功~！");
					}
					
					
				} 
				catch (SQLException e1) {JOptionPane.showMessageDialog(this, "请填写好正确的信息", "警告",JOptionPane.WARNING_MESSAGE);}
				}//comfirm_password.equals(password)结束
				else
					JOptionPane.showMessageDialog(this, "密码不匹配~！", "警告", JOptionPane.WARNING_MESSAGE);
			}//temp_password.equals(temp_password_2)结束
			else {
				JOptionPane.showMessageDialog(this, "两次输入的密码不一致~！", "警告",JOptionPane.WARNING_MESSAGE);
			}
			}//!textEmpty(temp_name, temp_password, temp_password_2, temp_id)结束
		else {
				JOptionPane.showMessageDialog(this, "请填写好正确的信息", "警告",JOptionPane.WARNING_MESSAGE);
			}
		}//button_add结束
		
		//button_cancel区
		if(e.getSource()==button_cancel) {
			dispose();
		}
	}

	//定义文本区判空函数
		 boolean textEmpty(String a,String b,String c,String d) {
			boolean isEmpty=false;
			if(a.equals("") || b.equals("") || c.equals("") || d.equals("") )
				isEmpty=true;
			return isEmpty;
		}


	//测试函数
	public static void main(String[] args) {
		new UserAdd(null);
	}

}
