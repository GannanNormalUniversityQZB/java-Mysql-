package departmentUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Tools.JDBC;
import Tools.SetCenterSize;
import UI.CompanyManage;

import java.sql.*;
import java.awt.*;
public class DepartmentModify extends JDialog implements ActionListener{
	//定义成员变量
	JLabel label_depart=new JLabel("请您选择部门：");
	JLabel label_modify_name=new JLabel("修改部门名称：");
	JLabel label_detail=new JLabel("部门详细信息");
	
	/*文本框区*/
	JTextField text_modify_name=new JTextField(15);
	
	/*按钮区*/
	JButton button_modify=new JButton("修改");
	JButton button_cancel=new JButton("取消");
	
	/*下拉列表区*/
	String array_department[];
	JComboBox<String> comboBox_department;
	
	/*表格区*/
	JTable table;
	DefaultTableModel tableModel;
	String title[];
	String data[][];
	
	/*logo区*/
	public JLabel companyLogo=new JLabel(new ImageIcon("images\\Logo\\companyLogo2.png"));
	
	/*用于接收从其他业务传输过来的部门信息*/
	String transfered_depart;
	
	//定义构造函数
	public DepartmentModify(CompanyManage view,String transfered_depart) {
		super(view);
		//设置窗口
		setLayout(new FlowLayout());
		setTitle("修改部门");
		setBounds(new SetCenterSize().getCenterBounds(535,560));	
		setModal(true);	
		/*设置图标*/
		Image icon = Toolkit.getDefaultToolkit().getImage("images\\icon\\icon3.png");
		setIconImage(icon);
		
		/*接收数据*/
		this.transfered_depart=transfered_depart;
		
		/*设置组件初始状态*/
		//下拉列表
		Connection con=JDBC.getConnection();
		try {
			Statement sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs=sql.executeQuery("select * from department");
			rs.last();
			int row=rs.getRow();
			rs.beforeFirst();
			/*创建数组*/
			array_department=new String[row];
			for(int i=0;rs.next();i++) {
				array_department[i]=rs.getString(1);
			}
			/*初始化下拉列表*/
			comboBox_department=new JComboBox<>(array_department);
			
			/*如果是从其他业务打开界面*/
			if(transfered_depart!=null)
					comboBox_department.setSelectedItem(transfered_depart);
			
			//表格
			//初始化表格
			tableModel=new DefaultTableModel();
			table=new JTable(tableModel);
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			
							
			/*设置表格居中*/
			DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
			tcr.setHorizontalAlignment(SwingConstants.CENTER);
			table.setDefaultRenderer(Object.class, tcr);
			
			
			int col;
			int row_2;
			String temp_depart=(String)this.comboBox_department.getSelectedItem();
			ResultSetMetaData metaData;
			rs=sql.executeQuery("select * from employee where 部门='"+temp_depart+"'");
		
			/*这里获取字段与列数*/
			metaData=rs.getMetaData();
			col=metaData.getColumnCount();
			/*获得字段数组*/
			title=new String[col];
			for(int i=0;i<col;i++) {
				title[i]=metaData.getColumnName(i+1);
			}
			
			/*获得行数*/
			rs.last();
			row_2=rs.getRow();
			rs.beforeFirst();
			
			//创建数组all_Information
			data=new String [row_2][col];
			
			//创建控制循环的计数器
			for(int i=0;rs.next();i++)
				for(int j=0;j<col;j++)
					data[i][j]=rs.getString(j+1);
			
			
			
			//将结果显示在界面上
			tableModel.setDataVector(data, title);
			tableModel.fireTableDataChanged();
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		//添加组件
		/*设置中间容器*/
		Box box_depart=Box.createHorizontalBox();
		Box box_new_depart=Box.createHorizontalBox();
		Box box_button=Box.createHorizontalBox();
		Box Vbox=Box.createVerticalBox();
		JPanel panel_table=new JPanel();
		JPanel panel_detail=new JPanel();
		JScrollPane scrollpane_table=new JScrollPane(table);
		scrollpane_table.setPreferredSize(new Dimension(500, 200));
		
		/*组装*/
		box_depart.add(label_depart);
		box_depart.add(comboBox_department);
		
		box_new_depart.add(label_modify_name);
		box_new_depart.add(text_modify_name);
		
		panel_detail.add(label_detail);
		panel_table.add(scrollpane_table);
		
		box_button.add(button_modify);
		box_button.add(Box.createHorizontalStrut(50));
		box_button.add(button_cancel);
		
		Vbox.add(Box.createVerticalStrut(10));
		Vbox.add(box_depart);
		Vbox.add(Box.createVerticalStrut(15));
		Vbox.add(box_new_depart);
		Vbox.add(Box.createVerticalStrut(5));
		Vbox.add(panel_detail);
		Vbox.add(Box.createVerticalStrut(5));
		Vbox.add(panel_table);
		Vbox.add(Box.createVerticalStrut(15));
		Vbox.add(box_button);
		
		add(companyLogo);
		add(Vbox);
		
		
		//为组件添加监视器
		button_modify.addActionListener(this);
		button_cancel.addActionListener(this);
		comboBox_department.addActionListener(this);
		
		//窗口结尾处理
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
	
	
	
	//重写接口函数
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==comboBox_department) {
			Connection con=JDBC.getConnection();
		try {
			Statement sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int col;
			int row_2;
			String temp_depart=(String)this.comboBox_department.getSelectedItem();
			ResultSetMetaData metaData;
			ResultSet rs=sql.executeQuery("select * from employee where 部门='"+temp_depart+"'");
		
			/*这里获取字段与列数*/
			metaData=rs.getMetaData();
			col=metaData.getColumnCount();
			/*获得字段数组*/
			title=new String[col];
			for(int i=0;i<col;i++) {
				title[i]=metaData.getColumnName(i+1);
			}
			
			/*获得行数*/
			rs.last();
			row_2=rs.getRow();
			rs.beforeFirst();
			
			//创建数组all_Information
			data=new String [row_2][col];
			
			//创建控制循环的计数器
			for(int i=0;rs.next();i++)
				for(int j=0;j<col;j++)
					data[i][j]=rs.getString(j+1);
			
			
			
			//将结果显示在界面上
			tableModel.setDataVector(data, title);
			tableModel.fireTableDataChanged();
			
		}
		catch (SQLException e1) {
			e1.printStackTrace();
		}
	}//comboBox_department结束
		
	//button_mofify区
		if(e.getSource()==button_modify) {
			String temp_depart=(String)this.comboBox_department.getSelectedItem();
			String temp_new_depart=this.text_modify_name.getText();
			if(!temp_new_depart.equals("")) {
				int comfirm=JOptionPane.showConfirmDialog(this, "部门修改为："+temp_new_depart+"后,请前往职位管理业务，对部门职位进行相应修改\n您确定要修改部门名称吗？", "提示", JOptionPane.YES_NO_OPTION);
				if(comfirm==JOptionPane.YES_OPTION) {
				/*数据库局部变量*/
				Connection con=JDBC.getConnection();
				try {
					//更新employee
					PreparedStatement presql=con.prepareStatement("update employee set 部门=? where 部门=?");
					presql.setString(1, temp_new_depart);
					presql.setString(2, temp_depart);
					int i=presql.executeUpdate();
					//更新department
					presql=con.prepareStatement("update department set 部门=? where 部门=?");
					presql.setString(1, temp_new_depart);
					presql.setString(2, temp_depart);
					int j=presql.executeUpdate();
					//更新position
					presql=con.prepareStatement("update position set 所属部门=? where 所属部门=?");
					presql.setString(1, temp_new_depart);
					presql.setString(2, temp_depart);
					int k=presql.executeUpdate();
					
					if(i!=0 && j!=0 && k!=0) {
						JOptionPane.showMessageDialog(this, "部门更名成功~！");
						
						
					}
					else
						JOptionPane.showMessageDialog(this, "部门更名失败~！");
				}
				catch (SQLException e1) {
					e1.printStackTrace();
				}
				}//!temp_new_depart.equals("")
				
				else
					JOptionPane.showMessageDialog(this, "已取消更名~！");
				
			}
			else
				JOptionPane.showMessageDialog(this, "部门修改名称不可为空~！", "警告", JOptionPane.WARNING_MESSAGE);
			
			
		}//button_modify结束

		
		
		//button_cancel区
		if(e.getSource()==button_cancel) {
			dispose();
		}//button_cancel结束
	
}
	
	//测试函数
	public static void main(String[] args) {
		
		new DepartmentModify(null,null);
	}

}
