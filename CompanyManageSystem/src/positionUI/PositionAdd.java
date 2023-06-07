package positionUI;

import Tools.JDBC;
import Tools.SetCenterSize;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.sql.*;
public class PositionAdd extends JDialog implements ActionListener{
	//定义成员变量
	JLabel label_depart=new JLabel("选择部门：");
	JLabel label_position=new JLabel("新增职位：");
	
	/*文本框区*/
	JTextField text_depart=new JTextField(15);
	
	/*按钮区*/
	JButton button_add=new JButton("添加");
	JButton button_cancel=new JButton("取消");
	
	/*下拉列表区*/
	
	String array_depart[];
	JComboBox<String> comboBox_depart=new JComboBox<>();
	
	/*公司logo区*/
	public JLabel companyLogo=new JLabel(new ImageIcon("images\\Logo\\companyLogo2.png"));
	
	/*用于接收从其他业务传输过来的新部门名称*/
	String new_depart;
	
	//创建初始化函数
	public PositionAdd(JDialog view,String new_depart) {
		super(view);
		//设置窗口
		setLayout(new FlowLayout());
		setTitle("新增职位");
		setBounds(new SetCenterSize().getCenterBounds(480,310));	
		setModal(true);
		/*设置图标*/
		Image icon = Toolkit.getDefaultToolkit().getImage("images\\icon\\icon3.png");
		setIconImage(icon);
		
		//添加组件
		/*创建中间容器*/
		Box Vbox=Box.createVerticalBox();
		Box box_depart=Box.createHorizontalBox();
		Box box_position=Box.createHorizontalBox();
		Box box_button=Box.createHorizontalBox();
		
		box_depart.add(label_depart);
		box_depart.add(comboBox_depart);
		
		box_position.add(label_position);
		box_position.add(text_depart);
		
		box_button.add(button_add);
		box_button.add(Box.createHorizontalStrut(35));
		box_button.add(button_cancel);
		
		Vbox.add(Box.createVerticalStrut(10));
		Vbox.add(box_depart);
		Vbox.add(Box.createVerticalStrut(20));
		Vbox.add(box_position);
		Vbox.add(Box.createVerticalStrut(20));
		Vbox.add(box_button);
		
		add(companyLogo);
		add(Vbox);
		
		//设置部门组件初始状态
		this.new_depart=new_depart;
		//测试语句
		System.out.println(this.new_depart);
		
		/*数据库局部变量*/
		Connection con=JDBC.getConnection();
		try {
			Statement sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs=sql.executeQuery("select * from department ");
			rs.last();
			int row=rs.getRow();
			rs.beforeFirst();
			/*创建数组*/
			/*确定是否需要新增部门*/
			if(new_depart!=null){
				array_depart=new String[row+1];
				for(int i=0;rs.next();i++)
					array_depart[i]=rs.getString(1);
				array_depart[array_depart.length-1]=new_depart;
			}
			else {
				array_depart=new String[row];
				for(int i=0;rs.next();i++)
					array_depart[i]=rs.getString(1);
			}
				
			/*初始化职位*/
			comboBox_depart.removeAllItems();
			for(String a:array_depart)
				comboBox_depart.addItem(a);
			/*如有新增部门，确定其index*/
			if(new_depart!=null)
				comboBox_depart.setSelectedItem(array_depart[array_depart.length-1]);
			
			
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
		//为按钮添加监视器
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
			/*组件信息局部变量区*/
			String temp_depart=(String)this.comboBox_depart.getSelectedItem();
			String temp_position=this.text_depart.getText();
			
			/*数据库局部变量区*/
			Connection con=JDBC.getConnection();
			Statement sql;
			ResultSet rs;
			boolean isInMysql=false;
			try {
				sql = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rs=sql.executeQuery("select * from department");
				//判断从其他业务传输过来的部门是否存在
				if(new_depart!=null) {
					while(rs.next())
						if(new_depart.equals(rs.getString(1))) {
							isInMysql=true;
							break;
						}
				}
				else {	
					System.out.println("没有新增部门");
				}
			} 
			catch (SQLException e2) {
				e2.printStackTrace();
			}
			
			//如果有新部门，更新department和position表
			if(new_depart!=null && !isInMysql  &&  !temp_position.equals("")) {
				try {
					sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
					/*更新department*/
					int update=sql.executeUpdate("insert into department values('"+new_depart+"')");
					System.out.println("department表已经更新");
					/*position表*/
					int update_2=sql.executeUpdate("insert into position values('"+new_depart+"','"+temp_position+"')" );
					//在position表里同步创建待业人员职位
					int update_3=sql.executeUpdate("insert into position values('"+new_depart+"','"+"待业人员"+"')" );
					/*弹窗提醒*/
					if(update!=0 && update_2!=0 && update_3!=0) {
						JOptionPane.showMessageDialog(this, "新增职位:"+temp_position+"添加成功~!");
					}
					else
						JOptionPane.showMessageDialog(this, "新增职位:"+temp_position+"添加失败~!");
				}
				catch (SQLException e1) {
					JOptionPane.showMessageDialog(this, "新增职位:"+temp_position+"添加失败~!");
					e1.printStackTrace();
				}
				
			}//new_depart!=null
			
			//如果没有新部门，仅更新position表
			else if(!temp_position.equals("")){
				try {
					sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
					int update=sql.executeUpdate("insert into position values('"+temp_depart+"','"+temp_position+"')" );
					/*弹窗提醒*/
					if(update!=0) {
						JOptionPane.showMessageDialog(this, "新增职位:"+temp_position+"添加成功~!");
					}
					else
						JOptionPane.showMessageDialog(this, "新增职位:"+temp_position+"添加失败~!");
				}
				catch (SQLException e1) {
					JOptionPane.showMessageDialog(this, "新增职位:"+temp_position+"添加失败~!");
					e1.printStackTrace();
				}
			}
				else
					JOptionPane.showMessageDialog(this, "职位不可为空,或者重复~！", "警告", JOptionPane.WARNING_MESSAGE);
		}//e.getSource()==button_add
		
		
		else if(e.getSource()==button_cancel) {
			dispose();
		}
	}
	
	
	//测试函数
	public static void main(String[] args) {
		new PositionAdd(null,null);

	}

}
