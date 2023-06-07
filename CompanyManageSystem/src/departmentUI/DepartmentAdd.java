package departmentUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import javax.swing.*;

import Tools.JDBC;
import Tools.SetCenterSize;
import positionUI.PositionAdd;

import java.sql.*;
public class DepartmentAdd extends JDialog implements ActionListener{
	//定义成员变量
	/*标签区*/
	JLabel label_new_depart=new JLabel("新建部门名称：");
	JLabel label_create_position=new JLabel("部门新建职位：");
	JLabel label_original_depart=new JLabel("负责人原部门：");
	JLabel label_name=new JLabel("负责人其姓名：");
	JLabel label_id=new JLabel("负责人其工号：");
	JLabel label_original_position=new JLabel("负责人原职位：");
	JLabel label_new_position=new JLabel("负责人新职位：");
	
	/*定义组件信息变量*/
	String array_original_depart[];
	String array_name[];
	String array_new_position[];
	
	/*文本框区*/
	JTextField text_new_depart=new JTextField(15);
	JTextField text_original_position=new JTextField(15);
	JTextField text_id=new JTextField(15);
	
	/*下拉列表区*/
	JComboBox<String> comboBox_original_depart=new JComboBox<>();
	JComboBox<String> comboBox_name=new JComboBox<>();
	JComboBox<String> comboBox_new_position=new JComboBox<>();
	
	/*按钮区*/
	JButton button_submit=new JButton("提交");
	JButton button_create_position=new JButton("请您点击此处新增职位");
	JButton button_cancel=new JButton("取消");
	
	/*logo区*/
	public JLabel companyLogo=new JLabel(new ImageIcon("images\\Logo\\companyLogo2.png"));

	
	
	//定义构造函数
	public DepartmentAdd(JFrame view) {
		super(view);
		//设置窗口
		setLayout(new FlowLayout());
		setTitle("添加部门");
		setBounds(new SetCenterSize().getCenterBounds(480,580));	
		setModal(true);	
		/*设置图标*/
		Image icon = Toolkit.getDefaultToolkit().getImage("images\\icon\\icon3.png");
		setIconImage(icon);
		
		//添加组件
		/*创建中间容器*/
		Box Vbox=Box.createVerticalBox();
		Box box_new_depart=Box.createHorizontalBox();
		Box box_original_depart=Box.createHorizontalBox();
		Box box_name=Box.createHorizontalBox();
		Box box_id=Box.createHorizontalBox();
		Box box_original_position=Box.createHorizontalBox();
		Box box_new_position=Box.createHorizontalBox();
		Box box_create_position=Box.createHorizontalBox();
		Box box_button=Box.createHorizontalBox();
		
		
		/*组装组件*/
		box_new_depart.add(label_new_depart);
		box_new_depart.add(text_new_depart);
		
		box_create_position.add(label_create_position);
		box_create_position.add(button_create_position);
		box_create_position.add(Box.createHorizontalGlue());
		
		box_original_depart.add(label_original_depart);
		box_original_depart.add(comboBox_original_depart);
		
		box_name.add(label_name);
		box_name.add(comboBox_name);
		
		box_id.add(label_id);
		box_id.add(text_id);
		
		box_original_position.add(label_original_position);
		box_original_position.add(text_original_position);
		
		box_new_position.add(label_new_position);
		box_new_position.add(comboBox_new_position);
		
		box_button.add(button_submit);
		box_button.add(Box.createHorizontalStrut(60));
		box_button.add(button_cancel);
		
		Vbox.add(Box.createGlue());
		Vbox.add(Box.createVerticalStrut(20));
		Vbox.add(box_new_depart);
		Vbox.add(Box.createVerticalStrut(20));
		Vbox.add(box_create_position);
		Vbox.add(Box.createVerticalStrut(20));
		Vbox.add(box_original_depart);
		Vbox.add(Box.createVerticalStrut(20));
		Vbox.add(box_name);
		Vbox.add(Box.createVerticalStrut(20));
		Vbox.add(box_id);
		Vbox.add(Box.createVerticalStrut(20));
		Vbox.add(box_original_position);
		Vbox.add(Box.createVerticalStrut(20));
		Vbox.add(box_new_position);
		Vbox.add(Box.createVerticalStrut(30));
		Vbox.add(box_button);
		
		
		add(companyLogo);
		add(Vbox);
		
		//设置组件初始状态
		button_submit.setEnabled(false);
		text_original_position.setEditable(false);
		text_id.setEditable(false);
		
		
		//为组件添加监视器
		button_create_position.addActionListener(this);
		button_cancel.addActionListener(this);
		button_submit.addActionListener(this);
		comboBox_original_depart.addActionListener(this);
		comboBox_name.addActionListener(this);
		comboBox_new_position.addActionListener(this);
		//窗口结尾处理
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
	}
	
	
	//重写接口函数
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==button_create_position) {
			/*获得新部门名称*/
			String new_depart=this.text_new_depart.getText().trim();
			//新增的部门与职位由PositionAdd完成，同步department与position表
			//DepartmentAdd主要用于更新职员信息，同步employee表
			new PositionAdd((JDialog)this,new_depart);
		
			/*定义数据库局部变量*/
			Connection con=JDBC.getConnection();
			Statement sql;
			ResultSet rs;
			try {
				 sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				 //获得所有部门信息
				 rs=sql.executeQuery("select * from department");
				 rs.last();
				 int row=rs.getRow();
				 rs.beforeFirst();
				 array_original_depart=new String[row];
				 for(int i=0;rs.next();i++)
					 array_original_depart[i]=rs.getString(1);
				 /*刷新下拉列表*/
				 comboBox_original_depart.removeAllItems();
				 for(String a:array_original_depart)
					 comboBox_original_depart.addItem(a);
				 
				//获得新部门职位信息
				 rs=sql.executeQuery("select 职位 from position where 所属部门='"+new_depart+"'");
				 rs.last();
				 int row_2=rs.getRow();
				 rs.beforeFirst();
				 /*创建数组*/
				 array_new_position=new String[row_2];
				 for(int i=0;rs.next();i++)
					 array_new_position[i]=rs.getString(1);
				 /*刷新新职位列表*/
				 comboBox_new_position.removeAllItems();
				 for(String a:array_new_position)
					 comboBox_new_position.addItem(a);
				 
			} catch (SQLException e1) {	
				e1.printStackTrace();
			}
			
			//设置按钮状态
			button_submit.setEnabled(true);
		}
		
		//comboBox_original_depart区
		if(e.getSource()==comboBox_original_depart) {
			/*定义数据库局部变量*/
			Connection con=JDBC.getConnection();
			Statement sql;
			ResultSet rs;
			 //获得部门所有人名
			String temp_depart=(String)comboBox_original_depart.getSelectedItem();
			 try {
				sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rs=sql.executeQuery("select 姓名 from employee where 部门='"+temp_depart+"'");
				rs.last();
				int row=rs.getRow();
				rs.beforeFirst();
				/*创建数组*/
				array_name=new String[row];
				for(int i=0;rs.next();i++) {
					array_name[i]=rs.getString(1);
				}
				 /*刷新下拉列表*/
				comboBox_name.removeAllItems();
				 for(String a:array_name)
					 comboBox_name.addItem(a);
			} 
			 catch (SQLException e1) {
				e1.printStackTrace();
			}
		}//e.getSource()==comboBox_original_depart
		
		
		//e.getSource()==comboBox_name区，刷新文本框
		if(e.getSource()==comboBox_name) {
			String temp_name=(String)this.comboBox_name.getSelectedItem();
			/*定义数据库局部变量*/
			Connection con=JDBC.getConnection();
			Statement sql;
			ResultSet rs;
			try {
				sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				/*获得负责人工号*/
				rs=sql.executeQuery("select 工号 from employee where 姓名='"+temp_name+"'");
				rs.next();
				this.text_id.setText(rs.getString(1));
				/*获得原职位*/
				rs=sql.executeQuery("select 职位 from employee where 姓名='"+temp_name+"'");
				rs.next();
				this.text_original_position.setText(rs.getString(1));
			} 
			catch (SQLException e1) {
				e1.printStackTrace();
			}	
			
		}//e.getSource()==comboBox_name
		
		//button_submit区，用于更新员工信息
		if(e.getSource()==button_submit) {
			String temp_new_depart=this.text_new_depart.getText().trim();
			String temp_new_position=(String)this.comboBox_new_position.getSelectedItem();
			String temp_id=this.text_id.getText().trim();
			if(temp_new_depart.equals("")) {
				JOptionPane.showMessageDialog(this, "新部门名称不可以为空", "警告",JOptionPane.WARNING_MESSAGE);
			}	
			else {
				/*定义数据库局部变量*/
				Connection con=JDBC.getConnection();
				Statement sql;
				ResultSet rs;
				try {
					sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
					int i=sql.executeUpdate("update employee set 部门='"+temp_new_depart+"',职位='"+temp_new_position+"' where 工号='"+temp_id+"'");
					if(i!=0) {
						JOptionPane.showMessageDialog(this, "新部门："+temp_new_depart+"创建成功~！");
						//设置提交组件状态
						button_submit.setEnabled(false);
						repaint();
					}
					else
						JOptionPane.showMessageDialog(this, "新部门："+temp_new_depart+"创建失败~！");
				} 
				catch (SQLException e1) {
					JOptionPane.showMessageDialog(this, "新部门："+temp_new_depart+"创建失败~！");
					e1.printStackTrace();
				}
			}
				
		}//e.getSource()==button_submit
		
		if(e.getSource()==button_cancel)
			dispose();
	}
	
	
	
	//测试函数
	public static void main(String[] args) {
		new DepartmentAdd(null);

	}

}//class结束区
