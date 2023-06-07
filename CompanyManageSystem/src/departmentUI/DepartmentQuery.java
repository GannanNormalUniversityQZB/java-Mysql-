package departmentUI;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Tools.JDBC;
import Tools.SetCenterSize;
import UI.CompanyManage;

import java.sql.*;
import java.sql.*;
public class DepartmentQuery extends JDialog implements ActionListener{
	//定义成员变量
	JLabel label_depart=new JLabel("请选择部门：");
	
	/*按钮区*/
	JButton button_query=new JButton("查询");
	JButton button_new_depart=new JButton("新增部门");
	JButton button_modify_depart=new JButton("修改部门");
	JButton button_cancel=new JButton("取消操作");
	JButton button_dismiss=new JButton("解散部门");
	/*下拉列表区*/
	String array_depart[];
	JComboBox<String> comboBox_depart;
	
	/*表格区*/
	JTable table;
	DefaultTableModel tableModel;
	String title[];
	String data[][];
	
	//创建构造函数
	public DepartmentQuery(CompanyManage view) {
		super(view);
		//设置窗口
		setLayout(new BorderLayout());
		setTitle("查询部门");
		setBounds(new SetCenterSize().getCenterBounds(1000,550));	
		setModal(true);	
		/*设置图标*/
		Image icon = Toolkit.getDefaultToolkit().getImage("images\\icon\\icon3.png");
		setIconImage(icon);
		
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
			array_depart=new String[row+1];
			array_depart[0]="------查询所有部门------";
			for(int i=1;rs.next();i++) {
				array_depart[i]=rs.getString(1);
			}
			/*初始化下拉列表*/
			comboBox_depart=new JComboBox<>(array_depart);
			
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		//添加组件
		/*设置中间*/
		Box north_box=Box.createHorizontalBox();
		JPanel panel_north=new JPanel();
		
		Box south_box=Box.createHorizontalBox();
		JPanel panel_south=new JPanel();
		
		/*组装*/
		north_box.add(label_depart);
		north_box.add(Box.createHorizontalStrut(15));
		north_box.add(comboBox_depart);
		north_box.add(Box.createHorizontalStrut(15));
		north_box.add(button_query);
		north_box.add(Box.createHorizontalStrut(15));
		panel_north.add(north_box);
		
		
		//添加表格
		tableModel=new DefaultTableModel();
		table=new JTable(tableModel);
				
		/*设置表格居中*/
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
		tcr.setHorizontalAlignment(SwingConstants.CENTER);
		table.setDefaultRenderer(Object.class, tcr);
		
		south_box.add(button_new_depart);
		south_box.add(Box.createHorizontalStrut(60));
		south_box.add(button_modify_depart);
		south_box.add(Box.createHorizontalStrut(60));
		south_box.add(button_dismiss);
		south_box.add(Box.createHorizontalStrut(60));
		south_box.add(button_cancel);
		panel_south.add(south_box);
		
		add(panel_north,BorderLayout.NORTH);
		add(new JScrollPane(table),BorderLayout.CENTER);
		add(panel_south,BorderLayout.SOUTH);
		
		
		
		//为组件添加监视器
		button_modify_depart.addActionListener(this);
		button_query.addActionListener(this);
		button_new_depart.addActionListener(this);
		button_cancel.addActionListener(this);
		button_dismiss.addActionListener(this);
		
		//窗口结尾处理
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
	
	
	
	
	
	//重写接口函数
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==button_query) {
			/*组件信息局部变量*/
			int col;
			int row;
			String temp_depart=(String)this.comboBox_depart.getSelectedItem();
			/*数据库临时变量区*/
			Connection con=JDBC.getConnection();
			Statement sql;
			ResultSet rs;
			ResultSetMetaData metaData;
			try {
				sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				if(temp_depart.equals("------查询所有部门------")) {
					rs=sql.executeQuery("select * from employee order by 部门 ");
				}
				else {
					rs=sql.executeQuery("select * from employee where 部门='"+temp_depart+"'");
				}
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
					row=rs.getRow();
					rs.beforeFirst();
					
					//创建数组all_Information
					data=new String [row][col];
					
					//创建控制循环的计数器
					for(int i=0;rs.next();i++)
						for(int j=0;j<col;j++)
							data[i][j]=rs.getString(j+1);
					
					
					
					//将结果显示在界面上
					tableModel.setDataVector(data, title);
					tableModel.fireTableDataChanged();
					
					JOptionPane.showMessageDialog(this, "查询成功~！");
					
				}
			 
			catch (SQLException e1) {
				e1.printStackTrace();
			}
	
		}//button_query区
		
		//button_new_depart区
		if(e.getSource()==button_new_depart) {
			new DepartmentAdd(null);
		}
			
		//button_cancel区
		if(e.getSource()==button_cancel)
			dispose();
		
		//button_modify_depart
		if(e.getSource()==button_modify_depart) {
			String temp_depart=(String)this.comboBox_depart.getSelectedItem();
			new DepartmentModify(null, temp_depart);
		}
		
		//button_dismiss取
		if(e.getSource()==button_dismiss) {
			String temp_depart=(String)this.comboBox_depart.getSelectedItem();
			new DepartmentDismiss(null, temp_depart);
		}
	
	}
	
	//测试函数
	public static void main(String[] args) {
			new DepartmentQuery(null);
	}
}
