package employeeUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import Tools.JDBC;
import Tools.SetCenterSize;
import Tools.textEmpty;
import UI.CompanyManage;

import java.awt.*;
import java.sql.*;
public class EmployeeModify extends JDialog implements ActionListener{
	//定义成员变量
		/*标签区*/
		JLabel label_department=new JLabel("部门：");
		JLabel label_id=new JLabel("工号：");
		JLabel label_name=new JLabel("姓名：");
		JLabel label_gender=new JLabel("性别：");
		JLabel label_eduBack=new JLabel("学历：");
		JLabel label_position=new JLabel("职位：");
		JLabel label_treatment=new JLabel("待遇：");
		JLabel label_date=new JLabel("入职：");
		
		
		/*logo区*/
		public JLabel companyLogo=new JLabel(new ImageIcon("images\\Logo\\companyLogo2.png"));
		
		/*文本框区 */
		JTextField text_id=new JTextField(20);
		JTextField text_name=new JTextField(20);
		
		/*下拉列表区*/
		
		/*学历*/
		String array_eduBack[]= {"小学","初中","高中","专科","本科","研究生"};
		JComboBox<String> comboBox_eduBack=new JComboBox<>(array_eduBack);
		
		/*部门*/
		String array_department[];
		JComboBox<String> comboBox_department=new JComboBox<>();

		/*职位*/
		String array_position[];
		JComboBox<String> comboBox_position=new JComboBox<>();
		
		/*待遇*/
		String array_treatment[]= {"3000","4000","5000","6000","7000","8000","9000","10000+"};
		JComboBox<String> comboBox_treatment=new JComboBox<>(array_treatment);
		
		//时间区
		/*年*/
		String array_year[]= {"1999年","2000年","2001年","2002年","2003年","2004年","2005年","2006年","2007年","2008年","2009年",
								"2010年","2011年","2012年","2013年","2014年","2015年","2016年","2017年","2018年","2019年",
								"2020年","2021年","2022年","2023年","2024年","2025年","2026年","2027年","2028年","2029年","2030年"};
		
		JComboBox<String> comboBox_year=new JComboBox<>(array_year);
		
		/*月*/
		String array_month[]= {"一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"};
		JComboBox<String> comboBox_month=new JComboBox<>(array_month);
		
		//按钮区
		/*单选按钮*/
		JRadioButton radioButton_male=new JRadioButton("男");
		JRadioButton radioButton_female=new JRadioButton("女");
		ButtonGroup button_group;
		
		/*按钮*/
		JButton button_modify=new JButton("修改");
		JButton button_cancel=new JButton("取消");
		JButton button_query=new JButton("查询");

		//定义构造函数
		public EmployeeModify(CompanyManage view,String transfered_id) {
			super(view);
			//设置窗口
			setLayout(new FlowLayout());
			setTitle("修改员工信息");
			setBounds(new SetCenterSize().getCenterBounds(500,580));	
			setModal(true);	
			/*设置图标*/
			Image icon = Toolkit.getDefaultToolkit().getImage("images\\icon\\icon3.png");
			setIconImage(icon);
			
			//初始化数据
			//设置部门
			Connection con=JDBC.getConnection();
			Statement sql;
			try {
				sql = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet rs=sql.executeQuery("select * from department");
				rs.last();
				int row=rs.getRow();
				rs.beforeFirst();
				/*创建数组*/
				array_department=new String[row];
				for(int i=0;rs.next();i++)
					array_department[i]=rs.getString(1);
				/*刷新部门列表*/
				comboBox_department.removeAllItems();
				for(String a:array_department)
					comboBox_department.addItem(a);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		
			
			
			
			//添加组件区
			/*设置外嵌容器*/
			Box Vbox=Box.createVerticalBox();
			/*设置内部容器*/
			Box box_name=Box.createHorizontalBox();
			Box box_id=Box.createHorizontalBox();
			Box box_department=Box.createHorizontalBox();
			Box box_gender=Box.createHorizontalBox();
			Box box_eduBack=Box.createHorizontalBox();
			Box box_position=Box.createHorizontalBox();
			Box box_button=Box.createHorizontalBox();
			Box box_treatment=Box.createHorizontalBox();
			Box box_date=Box.createHorizontalBox();
			
			box_name.add(label_name);
			box_name.add(text_name);
			
			
			
			box_id.add(label_id);
			box_id.add(text_id);
			
			
			box_department.add(label_department);
			box_department.add(comboBox_department);

			button_group=new ButtonGroup();
			button_group.add(radioButton_male);
			button_group.add(radioButton_female);
			box_gender.add(label_gender);
			box_gender.add(Box.createHorizontalStrut(30));
			box_gender.add(radioButton_male);
			box_gender.add(Box.createHorizontalStrut(30));
			box_gender.add(radioButton_female);
			box_gender.add(Box.createHorizontalGlue());
			
			box_eduBack.add(label_eduBack);
			box_eduBack.add(comboBox_eduBack);
			
			box_position.add(label_position);
			box_position.add(comboBox_position);
			
			box_treatment.add(label_treatment);
			box_treatment.add(comboBox_treatment);
			
			box_button.add(button_query);
			box_button.add(Box.createHorizontalStrut(20));
			box_button.add(button_modify);
			box_button.add(Box.createHorizontalStrut(20));
			box_button.add(button_cancel);
			
			box_date.add(label_date);
			//box_date.add(Box.createHorizontalStrut(15));
			box_date.add(comboBox_year);
			box_date.add(Box.createHorizontalStrut(15));
			box_date.add(comboBox_month);
			
			
			Vbox.add(Box.createVerticalStrut(10));
			Vbox.add(box_name);
			Vbox.add(Box.createVerticalStrut(15));
			Vbox.add(box_department);
			
			Vbox.add(Box.createVerticalStrut(15));
			Vbox.add(box_id);
			Vbox.add(Box.createVerticalStrut(15));
			Vbox.add(box_gender);
			Vbox.add(Box.createVerticalStrut(15));
			Vbox.add(box_eduBack);
			Vbox.add(Box.createVerticalStrut(20));
			Vbox.add(box_position);
			Vbox.add(Box.createVerticalStrut(20));
			Vbox.add(box_treatment);
			Vbox.add(Box.createVerticalStrut(20));
			Vbox.add(box_date);
			Vbox.add(Box.createVerticalStrut(20));
			Vbox.add(box_button);
			
			
			add(companyLogo);
			add(Vbox);
			
			
			//设置组件初始状态
			text_name.setEditable(false);
			comboBox_department.setEnabled(false);
			radioButton_female.setEnabled(false);
			radioButton_male.setEnabled(false);
			comboBox_eduBack.setEnabled(false);
			comboBox_position.setEnabled(false);
			comboBox_treatment.setEnabled(false);
			comboBox_year.setEnabled(false);
			comboBox_month.setEnabled(false);
			button_modify.setEnabled(false);
			
			text_id.setText(transfered_id);
			
			//为组件添加监视器
			comboBox_department.addActionListener(this);
			button_modify.addActionListener(this);
			button_cancel.addActionListener(this);
			button_query.addActionListener(this);
			
			
			
			//窗口结尾处理
			setVisible(true);
			setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		}
		
		
		
		
		//重写继承函数
		@Override
		public void actionPerformed(ActionEvent e) {
			//根据部门更换职位下拉列表
			if(e.getSource()==comboBox_department) {
				/*定义数据库局部变量*/
				Connection con=JDBC.getConnection();
				Statement sql;
				try {
					sql = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
					ResultSet rs;
				
				//刷新部门对应的职位列表
				String temp_depart=(String)this.comboBox_department.getSelectedItem();
				rs=sql.executeQuery("select 职位 from position where 所属部门='"+temp_depart+"'");
				rs.last();
				int row_2=rs.getRow();
				rs.beforeFirst();
				/*创建数组*/
				array_position=new String[row_2];
				for(int j=0;rs.next();j++)
					array_position[j]=rs.getString(1);
				/*刷新部门列表*/
				comboBox_position.removeAllItems();
				for(String a:array_position)
					comboBox_position.addItem(a);
				
				}//try结束
				catch (SQLException e1) {
					e1.printStackTrace();
				}
						
			}
			
			//button_query区
			if(e.getSource()==button_query) {
				/*临时变量区*/				
				String temp_id=this.text_id.getText().trim();
				/*数据库区*/
				if(temp_id.equals("")) {
					JOptionPane.showMessageDialog(this, "请输入工号进行查询，随后再修改信息~！");
				}
				else {
				Connection con=JDBC.getConnection();
				try {
					Statement sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
					ResultSet rs=sql.executeQuery("select * from employee where 工号="+"'"+temp_id+"'");
					rs.next();
					//为组件填充信息
					this.text_name.setText(rs.getString(1));
					this.text_id.setText(rs.getString(2));
	
					//获得部门
					String temp_depart=rs.getString(3);
					this.comboBox_department.setSelectedItem(temp_depart);
					System.out.println(temp_depart);
					
					
					String temp_gender=rs.getString(4);
					if(temp_gender.equals("男"))
						radioButton_male.setSelected(true);
					else
						radioButton_female.setSelected(true);
					this.comboBox_eduBack.setSelectedItem(rs.getString(5));	
					
					//获得职位
					String temp_position=rs.getString(6);
					System.out.println(temp_position);
					
					this.comboBox_treatment.setSelectedItem(rs.getString(7));
					this.comboBox_year.setSelectedItem(rs.getString(8));
					this.comboBox_month.setSelectedItem(rs.getString(9));
					
					
				
					
					//根据部门调整下职位
					rs=sql.executeQuery("select 职位 from position where 所属部门='"+temp_depart+"'");
					rs.last();
					int row_2=rs.getRow();
					rs.beforeFirst();
					/*创建数组*/
					array_position=new String[row_2];
					for(int j=0;rs.next();j++)
						array_position[j]=rs.getString(1);
					/*刷新部门列表*/
					comboBox_position.removeAllItems();
					for(String a:array_position)
						comboBox_position.addItem(a);
					//根据选中的部门，显示对应的职位
					String selected_depart=(String)this.comboBox_department.getSelectedItem();
					this.comboBox_position.setSelectedItem(temp_position);
					
					//设置组件状态
					text_name.setEditable(true);
					comboBox_department.setEnabled(true);
					radioButton_female.setEnabled(true);
					radioButton_male.setEnabled(true);
					comboBox_eduBack.setEnabled(true);
					comboBox_position.setEnabled(true);
					comboBox_treatment.setEnabled(true);
					comboBox_year.setEnabled(true);
					comboBox_month.setEnabled(true);
					button_modify.setEnabled(true);
					text_id.setEditable(false);
					//提示
					JOptionPane.showMessageDialog(this, "查询成功,您现在可以修改用户："+temp_id+"的信息~！");
					
				} 
				catch (SQLException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(this, "信息查询失败,请检查您输入的工号是否有误~！");
				}
				}
				
			}
			
			
			//button_cancel区
			if(e.getSource()==button_cancel) {
				dispose();
			}
			
			//button_modify区
			if(e.getSource()==button_modify) {
				/*临时变量区*/
				String temp_name=this.text_name.getText().trim();
				String temp_id=this.text_id.getText().trim();
				String temp_depart=(String)this.comboBox_department.getSelectedItem();
				String temp_gender=(radioButton_male.isSelected()?"男":"女");
				String temp_eduBack=(String)this.comboBox_eduBack.getSelectedItem();
				String temp_position=(String)this.comboBox_position.getSelectedItem();
				String temp_treatment=(String)this.comboBox_treatment.getSelectedItem();
				String temp_year=(String)this.comboBox_year.getSelectedItem();
				String temp_month=(String)this.comboBox_month.getSelectedItem();
				
				/*数据库区*/
				if(!textEmpty.textEmpty(temp_name,temp_id,temp_depart,temp_gender,temp_eduBack,temp_position,temp_treatment,temp_year,temp_month)) {
				Connection con=JDBC.getConnection();
				try {
					PreparedStatement presql=con.prepareStatement("update  employee set 姓名=?,工号=?,部门=?,性别=?,学历=?,职位=?,待遇=?,入职年份=?,入职月份=? where 工号=?");
					presql.setString(1, temp_name);
					presql.setString(2, temp_id);
					presql.setString(3, temp_depart);
					presql.setString(4, temp_gender);
					presql.setString(5, temp_eduBack);
					presql.setString(6, temp_position);
					presql.setString(7, temp_treatment);
					presql.setString(8, temp_year);
					presql.setString(9, temp_month);
					presql.setString(10, temp_id);
					int i=presql.executeUpdate();
					if(i!=0) {
						JOptionPane.showMessageDialog(this, "员工:"+temp_name+"信息修改成功~！");
					}
					//设置组件初始状态
					text_name.setEditable(false);
					comboBox_department.setEnabled(false);
					radioButton_female.setEnabled(false);
					radioButton_male.setEnabled(false);
					comboBox_eduBack.setEnabled(false);
					comboBox_position.setEnabled(false);
					comboBox_treatment.setEnabled(false);
					comboBox_year.setEnabled(false);
					comboBox_month.setEnabled(false);
					button_modify.setEnabled(false);
					text_id.setEditable(true);
				} 
				catch (SQLException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(this, "信息修改失败~！");
				}
				}//if判空结束
				else {
					JOptionPane.showMessageDialog(this, "请填写完整信息~！");
				}
			}
	}

		//测试函数
		public static void main(String[] args) {
			
				new EmployeeModify(null,null);
		}

}
