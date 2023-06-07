package UI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.*;
import javax.swing.*;

import org.w3c.dom.css.RGBColor;

import HelpUI.CopyrightUI;
import HelpUI.TechHelp;
import Tools.*;
import departmentUI.DepartmentAdd;
import departmentUI.DepartmentDismiss;
import departmentUI.DepartmentModify;
import departmentUI.DepartmentQuery;
import employeeUI.EmployeeAdd;
import employeeUI.EmployeeModify;
import employeeUI.EmployeeQuery;
import positionUI.PositionAdd;
import positionUI.PositionDelete;
import positionUI.PositionModify;
import positionUI.PositionQuery;
import userUI.UserAdd;
import userUI.UserDelete;
import userUI.UserModify;
import userUI.UserQuery;

public class CompanyManage extends JFrame implements ActionListener,WindowListener{
	//定义成员变量
	/*菜单条*/
	JMenuBar menubar=new  JMenuBar();
	//用户管理区
	public JMenu user_Manage=new JMenu("用户管理");
	public JMenuItem user_Query=new JMenuItem("查询用户");
	public JMenuItem user_Modify=new JMenuItem("修改用户");
	public JMenuItem user_Add=new JMenuItem("添加用户");
	public JMenuItem user_Delete=new JMenuItem("删除用户");
			
	//员工管理
	public JMenu emp_Manage=new JMenu("员工管理");
	public JMenuItem emp_Add=new JMenuItem("员工录入");
	public JMenuItem emp_Query=new JMenuItem("员工查询");
	public JMenuItem emp_Modify=new JMenuItem("员工修改");
	public JMenuItem emp_Delete=new JMenuItem("员工删除");

	//部门管理
	public JMenu depart_Manage=new JMenu("部门管理");
	public JMenuItem depart_Query=new JMenuItem("查询部门");
	public JMenuItem depart_Modify=new JMenuItem("修改部门");
	public JMenuItem depart_Add=new JMenuItem("新增部门");
	public JMenuItem depart_Dismiss=new JMenuItem("解散部门");
	
	//用户管理
	public JMenu position_Manage=new JMenu("职位管理");
	public JMenuItem position_Query=new JMenuItem("查询职位");
	public JMenuItem position_Modify=new JMenuItem("修改职位");
	public JMenuItem position_Add=new JMenuItem("添加职位");
	public JMenuItem position_Delete=new JMenuItem("删除职位");
	
	
	//帮助区
	public JMenu help_Menu=new JMenu("获得帮助");
	public JMenuItem help_tech=new JMenuItem("技术支持");
	public JMenuItem help_copyright=new JMenuItem("版权声明");
	
	//设置区
	public JMenu menu_set=new JMenu("系统设置");
	public JMenuItem menuItem_unsubscribe=new JMenuItem("注销账户");
	//public JMenuItem menuItem_authority=new JMenuItem("权限重置");	
	
	//主界面Logo
	public JLabel label_companyBack=new JLabel(new ImageIcon("images\\background\\companyBack2.jpg"));
	
	//版权信息
	public JLabel copyRight_Uni=new JLabel("江西省赣南师范大学");
	public JLabel copyRight_Col=new JLabel("数学与计算机科学学院");
	public JLabel copyRight_Cla=new JLabel("计算机科学与技术2102");
	public JLabel copyRight_Aut=new JLabel("版权所有：邱志斌");
	
	//字体
	Font font=new Font("宋体", Font.ITALIC, 18);
	
	
	//构造函数区
	
	public CompanyManage() {
		//设置窗口,使用默认布局BorderLayout
		setLayout(new BorderLayout());
		setTitle("企业员工信息管理系统");
		setBounds(new SetCenterSize().getCenterBounds(750, 500));
		/*设置图标*/
		Image icon = Toolkit.getDefaultToolkit().getImage("images\\icon\\icon3.png");
		setIconImage(icon);
		
		//组装菜单
		user_Manage.add(user_Query);
		user_Manage.add(user_Modify);
		user_Manage.add(user_Add);
		user_Manage.add(user_Delete);
		
		emp_Manage.add(emp_Query);
		emp_Manage.add(emp_Modify);
		emp_Manage.add(emp_Add);
		
		depart_Manage.add(depart_Query);
		depart_Manage.add(depart_Modify);
		depart_Manage.add(depart_Add);
		depart_Manage.add(depart_Dismiss);
		
		position_Manage.add(position_Query);
		position_Manage.add(position_Modify);
		position_Manage.add(position_Add);
		position_Manage.add(position_Delete);

		help_Menu.add(help_tech);
		help_Menu.add(help_copyright);
		
		menu_set.add(menuItem_unsubscribe);
		//menu_set.add(menuItem_authority);
		
		/*添加菜单*/
		menubar.add(user_Manage);
		menubar.add(emp_Manage);
		menubar.add(depart_Manage);
		menubar.add(position_Manage);
		menubar.add(help_Menu);
		menubar.add(menu_set);
		setJMenuBar(menubar);
		

		
		/*添加Logo与版权到界面*/
		add(label_companyBack,BorderLayout.CENTER);
		
		
		//为功能添加监视器
		/*用户管理区*/
		user_Add.addActionListener(this);
		user_Query.addActionListener(this);
		user_Modify.addActionListener(this);
		user_Delete.addActionListener(this);
		
		/*员工管理区*/
		emp_Add.addActionListener(this);
		//emp_Delete.addActionListener(this);
		emp_Modify.addActionListener(this);
		emp_Query.addActionListener(this);
		
		/*部门管理取*/
		depart_Add.addActionListener(this);
		depart_Dismiss.addActionListener(this);
		depart_Modify.addActionListener(this);
		depart_Query.addActionListener(this);
		
		/*职位管理区*/
		position_Add.addActionListener(this);
		position_Delete.addActionListener(this);
		position_Modify.addActionListener(this);
		position_Query.addActionListener(this);
		
		/*获得帮助区*/
		help_tech.addActionListener(this);
		help_copyright.addActionListener(this);
		
		/*系统设置区*/
		menuItem_unsubscribe.addActionListener(this);
		//menuItem_authority.addActionListener(this);
		//窗口结尾处理
		addWindowListener(this);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
	}
	


	@Override
	public void actionPerformed(ActionEvent e) {
		//用户管理区
		if(e.getSource()==user_Add) {
			new UserAdd(this);
		}
		else if(e.getSource()==user_Query) {
			new UserQuery(this);
		}
		else if(e.getSource()==user_Delete) {
			new UserDelete(this);
		}
		else if(e.getSource()==user_Modify) {
			new UserModify(this);
		}
		
		//员工管理区
		else if(e.getSource()==emp_Add) {
			new EmployeeAdd(this);
		}
		else if(e.getSource()==emp_Delete) {
			//new EmployeeAdd(this);
		}
		else if(e.getSource()==emp_Modify) {
			new EmployeeModify(this,null);
		}
		else if(e.getSource()==emp_Query) {
			new EmployeeQuery(this);
		}
		
		//部门管理区
		else if(e.getSource()==depart_Query) {
			new DepartmentQuery(this);
		}
		else if(e.getSource()==depart_Dismiss) {
			new DepartmentDismiss(this, null);
		}
		else if(e.getSource()==depart_Modify) {
			new DepartmentModify(this, null);
		}
		else if(e.getSource()==depart_Add) {
			new DepartmentAdd(null);
		}
		
		//职位管理区
		else if(e.getSource()==position_Query) {
			new PositionQuery(this, null);
		}
		else if(e.getSource()==position_Delete) {
			new PositionDelete(this, null, null);
		}
		else if(e.getSource()==position_Modify) {
			new PositionModify(this, null, null);
		}
		else if(e.getSource()==position_Add) {
			new PositionAdd(null, null);
		}
		
		//获得帮助区
		else if(e.getSource()==help_tech) {
			new TechHelp();
		}
		else if(e.getSource()==help_copyright) {
			new CopyrightUI();
		}
		
		//系统设置区
		else if(e.getSource()==menuItem_unsubscribe) {
			int comfirm=JOptionPane.showConfirmDialog(this, "您确定要注销次账户，重启系统吗？","提醒",JOptionPane.YES_NO_OPTION);
			if(comfirm==JOptionPane.YES_OPTION) {
				new LoginUI();
				dispose();
				JOptionPane.showMessageDialog(this, "账户注销成功，系统重启中~！");
			}
			else {
				JOptionPane.showMessageDialog(this, "已取消操作~！");
			}
		}
	}
	
	//测试函数
	public static void main(String[] args) {
		new CompanyManage();
}



	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void windowClosing(WindowEvent e) {
		int confirm=JOptionPane.showConfirmDialog(this, "您确定要关闭系统吗？", "企业员工信息管理系统",JOptionPane.YES_NO_OPTION);
		if(confirm==JOptionPane.YES_OPTION) {
			dispose();
			JOptionPane.showMessageDialog(this, "系统已关闭，感谢您的使用~！");
		}
		else
			JOptionPane.showMessageDialog(this, "已取消操作~！");
	}



	@Override
	public void windowClosed(WindowEvent e) {
		
		
	}



	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}
