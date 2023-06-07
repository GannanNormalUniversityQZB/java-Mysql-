package positionUI;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Tools.JDBC;
import Tools.SetCenterSize;
import UI.CompanyManage;
import employeeUI.EmployeeAdd;
import employeeUI.EmployeeModify;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
public class PositionQuery extends JDialog implements ActionListener{
	//定义成员变量
		JLabel label_input=new JLabel("请选择部门：");
		
		/*下拉列表区*/
		String array_department[];
		JComboBox<String> comboBox_department;
		
		/*按钮区*/
		JButton button_query=new JButton("查询");
		JButton button_modify=new JButton("修改");
		JButton button_delete=new JButton("删除");
		JButton button_add=new JButton("新增");
		JButton button_cancel=new JButton("取消");
		
		/*表格区*/
		JTable table;
		DefaultTableModel tableModel;
		String title[];
		String data[][];
		
		//定义构造函数
		public PositionQuery(CompanyManage view,String transfered_depart) {
			super(view);
			//设置窗口
			setLayout(new BorderLayout());
			setTitle("查询职位");
			setBounds(new SetCenterSize().getCenterBounds(500,550));	
			setModal(true);	
			/*设置图标*/
			Image icon = Toolkit.getDefaultToolkit().getImage("images\\icon\\icon3.png");
			setIconImage(icon);
			
			//设置组件初始状态
			button_add.setEnabled(false);
			button_delete.setEnabled(false);
			button_modify.setEnabled(false);
			
			
			/*初始化下拉列表*/
			Connection con=JDBC.getConnection();
			try {
				Statement sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet rs=sql.executeQuery("select * from department");
				rs.last();
				int row=rs.getRow();
				rs.beforeFirst();
				/*创建数组*/
				array_department=new String[row+1];
				array_department[0]="------查询所有职位------";
				for(int i=1;rs.next();i++) {
					array_department[i]=rs.getString(1);
				}
				/*初始化下拉列表*/
				comboBox_department=new JComboBox<>(array_department);
				
				/*如果是从其他业务打开界面*/
				if(transfered_depart!=null)
						comboBox_department.setSelectedItem(transfered_depart);
			}
			catch (Exception e) {
				System.out.println(e);
			}
			//添加组件区
			/*设置中间容器*/
			Box north_box=Box.createHorizontalBox();
			Box south_box=Box.createHorizontalBox();
			JPanel north_panel=new JPanel();
			JPanel south_panel=new JPanel();
			
			/*向容器添加组件*/
			north_box.add(label_input);
			north_box.add(Box.createHorizontalStrut(15));
			north_box.add(comboBox_department);
			north_box.add(Box.createHorizontalStrut(25));
			north_box.add(button_query);
			north_panel.add(north_box);
			
			south_box.add(button_delete);
			south_box.add(Box.createHorizontalStrut(45));
			south_box.add(button_modify);
			south_box.add(Box.createHorizontalStrut(45));
			south_box.add(button_add);
			south_box.add(Box.createHorizontalStrut(45));
			south_box.add(button_cancel);
			south_panel.add(south_box);
			
			//添加表格
			tableModel=new DefaultTableModel();
			table=new JTable(tableModel);
			
			/*设置表格居中*/
			DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
			tcr.setHorizontalAlignment(SwingConstants.CENTER);
			table.setDefaultRenderer(Object.class, tcr);
			
			
			add(north_panel,BorderLayout.NORTH);
			add(new JScrollPane(table),BorderLayout.CENTER);
			add(south_panel,BorderLayout.SOUTH);
			
			
			
			
			//为组件添加监视器
			button_query.addActionListener(this);
			button_add.addActionListener(this);
			button_delete.addActionListener(this);
			button_modify.addActionListener(this);
			
			//窗口结尾处理
			setVisible(true);
			setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		}
		
		
		
		//重写接口函数
		@Override
		public void actionPerformed(ActionEvent e) {
			//button_query区
			if(e.getSource()==button_query) {
				/*数据库局部变量区*/
				String temp_depart=(String)this.comboBox_department.getSelectedItem();
				Connection con=JDBC.getConnection();
				Statement sql;
				ResultSetMetaData metaData;
				ResultSet rs;
				/*数据域区*/
				int row=0;
				int col=0;
				/*获得数据*/
				try {
					sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
					//测试语句
					System.out.println(temp_depart);
					
					//文本框内容为空
					if(temp_depart.equals("------查询所有职位------")) {
						rs=sql.executeQuery("select * from position");
					}
					
					else {
					rs=sql.executeQuery("select * from position where 所属部门="+"'"+temp_depart+"'");
					} //else结尾
					
					//测试语句
					System.out.println("1111");
					
					//注释：在以下代码中，要先进行列数的获取，再进行行数的获取，否则会出错

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
					//测试语句
					System.out.println(12);
					
					//设置组件状态
					button_add.setEnabled(true);
					button_delete.setEnabled(true);
					button_modify.setEnabled(true);
				}
				catch (SQLException e1) {
					System.out.println(e1);
					JOptionPane.showMessageDialog(this, "未查询到相关信息，请重新查询", "错误~！",JOptionPane.WARNING_MESSAGE);
				}
				
			
			}
			
			//button_delete区
			if(e.getSource()==button_delete) {
				//获得选中行的部门与职位数据
				String temp_depart=data[this.table.getSelectedRow()][0];
				String temp_position=data[this.table.getSelectedRow()][1];
				System.out.println(temp_depart+temp_position);
				/*进入PositionDelete界面*/
				new PositionDelete(null,temp_depart, temp_position);
				
			}//e.getSource()==button_delete结束
			
			//button_add区
			if(e.getSource()==button_add) {
				//获得选中行的部门与职位数据
				String temp_depart=data[this.table.getSelectedRow()][0];
				new PositionAdd(null,temp_depart);
				
			}
			
			//button_modify区
			if(e.getSource()==button_modify) {
				//获得选中行的部门与职位数据
				String temp_depart=data[this.table.getSelectedRow()][0];
				String temp_position=data[this.table.getSelectedRow()][1];
				System.out.println(temp_depart+temp_position);
				new PositionModify(null,temp_depart,temp_position);
	}
			
		}
		
	
	//测试函数
	public static void main(String[] args) {
		new PositionQuery(null, null);

	}

	

}
