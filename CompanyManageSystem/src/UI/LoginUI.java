package UI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import HelpUI.CopyrightUI;
import HelpUI.TechHelp;
import Tools.*;
import userUI.UserAdd;
import userUI.UserModify;

import java.sql.*;
public class LoginUI extends JFrame implements ActionListener{
	//定义成员变量
	/*数据库区*/
	Connection con;
	PreparedStatement sql;
	ResultSet rs;
	
	/*用户名区*/
	JLabel user=new JLabel("用户名：");
	JLabel user_icon=new JLabel(new ImageIcon("images\\icon\\用户名.png"));
	JTextField text_user=new JTextField(22);
	
	/*密码区*/
	JLabel password=new JLabel("密    码：");
	JLabel password_icon=new JLabel(new ImageIcon("images\\icon\\密码.png"));
	JPasswordField text_password=new JPasswordField(22);
	
	/*下拉列表*/
	JLabel jUserType=new JLabel("身    份：");
	JLabel jUserType_icon=new JLabel(new ImageIcon("images\\icon\\列表.png"));
	String userArray[]= {"----------------请选择身份----------------","管理员","员工","开发人员"};
	JComboBox<String> userType=new JComboBox<>(userArray);
	
	/*按钮区*/
	JButton button_Login=new JButton("登录");
	JButton button_register=new JButton("注册");
	JButton button_reset=new JButton("忘记密码");
	JButton button_Cancel=new JButton("取消");
	
	/*logo区*/
	public JLabel companyLogo=new JLabel(new ImageIcon("images\\Logo\\companyLogo2.png"));
	
	/*记录登录错误次数*/
	int error=0;
	
	//定义构造函数
	public LoginUI() {
		//设置窗口
		setLayout(new FlowLayout());
		setTitle("企业员工信息管理系统");
		setBounds(new SetCenterSize().getCenterBounds(480, 400));
		/*设置图标*/
		Image icon = Toolkit.getDefaultToolkit().getImage("images\\icon\\icon3.png");
		setIconImage(icon);
		
		/*创建主Box容器*/
		Box centerBox=Box.createVerticalBox();
		
		/*创建用户名面板*/
		JPanel userPanel=new JPanel();
		userPanel.add(user_icon);
		userPanel.add(user);
		userPanel.add(text_user);
		
		/*创建密码面板*/
		JPanel passwordPanel=new JPanel();
		passwordPanel.add(password_icon);
		passwordPanel.add(password);
		passwordPanel.add(text_password);
		
		/*创建用户类型面板*/
		JPanel userTypePanel=new JPanel();
		userTypePanel.add(jUserType_icon);
		userTypePanel.add(jUserType);
		userTypePanel.add(userType);
		
		/*创建按钮面板*/
		Box buttonBox=Box.createHorizontalBox();
		buttonBox.add(button_Login);
		buttonBox.add(Box.createHorizontalStrut(40));
		buttonBox.add(button_register);
		buttonBox.add(Box.createHorizontalStrut(40));
		buttonBox.add(button_reset);
		//buttonBox.add(Box.createHorizontalStrut(30));
		//buttonBox.add(button_Cancel);
		
		//组装面板
		/*排版与添加*/
		centerBox.add(Box.createHorizontalGlue());
		companyLogo.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		centerBox.add(companyLogo);
		
		centerBox.add(Box.createVerticalStrut(20));
		centerBox.add(userPanel);
		centerBox.add(Box.createVerticalStrut(15));
		
		centerBox.add(passwordPanel);
		centerBox.add(Box.createVerticalStrut(15));
		centerBox.add(Box.createHorizontalGlue());
		centerBox.add(userTypePanel);
		centerBox.add(Box.createVerticalStrut(25));
		centerBox.add(Box.createHorizontalGlue());
		centerBox.add(buttonBox);
		
		add(centerBox);
		
		/*为按钮添加事件*/
		button_Login.addActionListener(this);
		button_register.addActionListener(this);
		button_reset.addActionListener(this);
		
		
		//窗口结尾处理
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	//监视器区
	@Override
	public void actionPerformed(ActionEvent e) {
		//登录按钮区
		if(e.getSource()==button_Login) {
			String name=this.text_user.getText();
			char pawChar[]=this.text_password.getPassword();
			String paw=new String(pawChar);
			String type=this.userType.getSelectedItem().toString();
			
			if(name==null ||  paw==null || type=="----------------请选择身份----------------") {
				JOptionPane.showMessageDialog(this,"请填写好所有信息~！");
				text_user.setText(null);
				text_password.setText(null);
				error++;
				if(error==3) {
					JOptionPane.showMessageDialog(this, "登录信息错误三次，系统自动关闭~！", "警告", JOptionPane.WARNING_MESSAGE);
					dispose();
				}
			}
			else {
				//连接数据库
				con=JDBC.getConnection();
				//获得sql对象
				try {
					sql=con.prepareStatement("select * from administrators where name=? and password=? and identity=?");
				} 
				catch (SQLException e1) {}
				//查询
				try {
					//设置参数
					sql.setString(1,name.trim());
					sql.setString(2,paw.trim());
					sql.setString(3,type.trim());
					rs=sql.executeQuery();

				} 
				catch (SQLException e1) {}
				
				//测试数据
				System.out.println(name+paw+type);
			
				/*测试rs数据集*/
				try {
					/*这一步非常关键，获得的rs指向数据集第一行的前面，所以要用next将游标下移*/
					rs.next();
					System.out.println(rs.getString(1)+rs.getString(2)+rs.getString(3));
				} catch (SQLException e2) {}
				
				
				
				//获得查询数据
				try {
					if(name.equals(rs.getString(1)) && paw.equals(rs.getString(2)) && type.equals(rs.getString(3)) ) {
						JOptionPane.showMessageDialog(this,"恭喜您登录成功~！");
						new TechHelp();
						new CopyrightUI();
						new CompanyManage();
						setVisible(false);
					}
					else {
						JOptionPane.showMessageDialog(this,"登录信息错误，请重新输入~！");
						error++;
						if(error==3) {
							JOptionPane.showMessageDialog(this, "登录信息错误三次，系统自动关闭~！", "警告", JOptionPane.WARNING_MESSAGE);
							dispose();
						}
							
						text_user.setText(null);
						text_password.setText(null);
					}
						
				} 
				catch (SQLException e1) {
					JOptionPane.showMessageDialog(this,"登录信息错误，请重新输入~！");
					error++;
					if(error==3) {
						JOptionPane.showMessageDialog(this, "登录信息错误三次，系统自动关闭~！", "警告", JOptionPane.WARNING_MESSAGE);
						dispose();
					}
					text_user.setText(null);
					text_password.setText(null);
				}
				
			}//try结束区
				
		}//if结束区
		//取消按钮区
		
		//注册区
		else if(e.getSource()==button_register) {
			new UserAdd(null);
		}
		
		//重置密码区
		else if(e.getSource()==button_reset) {
			new UserModify(null);
		}
		
	}//函数结束
	
	
	//测试函数
	public static void main(String[] args) {
		new LoginUI();

	}


	

}
