package employeeUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.*;

import Tools.JDBC;
import Tools.SetCenterSize;

import Tools.textEmpty;
import UI.CompanyManage;

import java.sql.*;
public class EmployeeAdd extends JDialog implements ActionListener{
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
	JComboBox<String> comboBox_department=null;
	/*职位*/
	String array_position[];
	JComboBox<String> comboBox_position=null;
	
	
	
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
	JButton button_submit=new JButton("提交");
	JButton button_cancel=new JButton("取消");
	
	//定义构造函数
	public EmployeeAdd(CompanyManage view) {
		super(view);
		//设置窗口
		setLayout(new FlowLayout());
		setTitle("添加员工");
		setBounds(new SetCenterSize().getCenterBounds(500,580));	
		setModal(true);	
		/*设置图标*/
		Image icon = Toolkit.getDefaultToolkit().getImage("images\\icon\\icon3.png");
		setIconImage(icon);
		
		//设置组件初始状态
		text_id.setEditable(false);
				
				//获得部门组件的名称
				Connection con=JDBC.getConnection();
				try {
					Statement sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
					ResultSet rs=sql.executeQuery("select * from department");		
					/*获得行数*/
					rs.last();
					int row=rs.getRow();
					rs.beforeFirst();
					
					//创建数组array_department
					array_department=new String [row];
					
					//创建控制循环的计数器
					for(int i=0;rs.next();i++)
							array_department[i]=rs.getString(1);
					/*创建部门*/
					comboBox_department=new JComboBox<>(array_department);
					
					//获得部门职位
					rs=sql.executeQuery("select 职位 from position where 所属部门="+"'"+array_department[0]+"'");
					rs.last();
					int row_2=rs.getRow();
					//测试语句
					System.out.println("高管职位有："+row_2);
					rs.beforeFirst();
					/*创建数组*/
					array_position=new String[row];
					for(int i=0;rs.next();i++)
						array_position[i]=rs.getString(1);
					/*创建部门*/
					comboBox_position=new JComboBox<>(array_position);
					
					
					//创建
				} 
				catch (SQLException e) {
					
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
		
		box_button.add(button_submit);
		box_button.add(Box.createHorizontalStrut(50));
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
		
		
		
		//为组件添加监视器
		comboBox_department.addActionListener(this);
		button_submit.addActionListener(this);
		button_cancel.addActionListener(this);
		
		//窗口结尾处理
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
	
	
	
	
	//重写继承函数
	@Override
	public void actionPerformed(ActionEvent e) {
		//根据部门更换职位下拉列表
		if(e.getSource()==comboBox_department) {
			//设置工号
			Connection con=JDBC.getConnection();
			try {
				Statement sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet rs=sql.executeQuery("select 工号 from employee ORDER BY 工号 DESC");
				rs.next();
				String temp_id=Integer.parseInt(rs.getString("工号"))+1+"";
				System.out.println("最新工号"+temp_id);
				this.text_id.setText(temp_id);
				
				/*获得选中的部门*/
				String temp_depart=(String)comboBox_department.getSelectedItem();
				System.out.println(temp_depart);

				rs=sql.executeQuery("select 职位 from position where 所属部门="+"'"+temp_depart+"'");
				rs.last();
				int row=rs.getRow();
				rs.beforeFirst();
				/*更新数组*/
				array_position=new String[row];
				for(int i=0;rs.next();i++) {
					array_position[i]=rs.getString(1);
				}
				
				/*更新职位*/
				comboBox_position.removeAllItems();;
				for(String a:array_position) {
					comboBox_position.addItem(a);
				}
			
			} 
			catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			
			
		}
		
		//button_submit区
		if(e.getSource()==button_submit) {
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
				PreparedStatement presql=con.prepareStatement("insert into employee values(?,?,?,?,?,?,?,?,?)");
				presql.setString(1, temp_name);
				presql.setString(2, temp_id);
				presql.setString(3, temp_depart);
				presql.setString(4, temp_gender);
				presql.setString(5, temp_eduBack);
				presql.setString(6, temp_position);
				presql.setString(7, temp_treatment);
				presql.setString(8, temp_year);
				presql.setString(9, temp_month);
				int i=presql.executeUpdate();
				if(i!=0) {
					JOptionPane.showMessageDialog(this, "新员工:"+temp_name+"信息录入成功~！");
					//刷新工号
					Statement sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
					ResultSet rs=sql.executeQuery("select 工号 from employee ORDER BY 工号 DESC");
					rs.next();
					String temp_id_2=Integer.parseInt(rs.getString("工号"))+1+"";
					System.out.println("最新工号"+temp_id_2);
					this.text_id.setText(temp_id_2);
					//重置姓名
					this.text_name.setText("");
				}
				
			} 
			catch (SQLException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(this, "信息录入失败~！");
			}
			}//if判空结束
			else {
				JOptionPane.showMessageDialog(this, "请填写完整信息~！");
			}
		}
		//button_cancel区
		if(e.getSource()==button_cancel) {
			dispose();
		}
}

	
	//测试函数
	public static void main(String[] args) {
		
			new EmployeeAdd(null);
	}


}
