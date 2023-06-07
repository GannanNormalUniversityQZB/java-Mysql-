package employeeUI;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Tools.JDBC;
import Tools.SetCenterSize;
import UI.CompanyManage;

import java.sql.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class EmployeeQuery extends JDialog implements ActionListener{
	//定义成员变量
	JLabel label_input=new JLabel("请输入工号：");
	JTextField text_input=new JTextField(18);
	/*按钮区*/
	JButton button_query=new JButton("查询");
	JButton button_modify=new JButton("修改");
	JButton button_delete=new JButton("删除");
	JButton button_add=new JButton("新增");
	
	/*表格区*/
	JTable table;
	DefaultTableModel tableModel;
	String title[];
	String data[][];
	
	//定义构造函数
	public EmployeeQuery(CompanyManage view) {
		super(view);
		//设置窗口
		setLayout(new BorderLayout());
		setTitle("查询员工");
		setBounds(new SetCenterSize().getCenterBounds(1000,550));	
		setModal(true);	
		/*设置图标*/
		Image icon = Toolkit.getDefaultToolkit().getImage("images\\icon\\icon3.png");
		setIconImage(icon);
		
		//添加组件区
		/*设置中间容器*/
		Box north_box=Box.createHorizontalBox();
		Box south_box=Box.createHorizontalBox();
		JPanel north_panel=new JPanel();
		JPanel south_panel=new JPanel();
		
		/*向容器添加组件*/
		north_box.add(label_input);
		north_box.add(Box.createHorizontalStrut(15));
		north_box.add(text_input);
		north_box.add(Box.createHorizontalStrut(25));
		north_box.add(button_query);
		north_panel.add(north_box);
		
		south_box.add(button_delete);
		south_box.add(Box.createHorizontalStrut(45));
		south_box.add(button_modify);
		south_box.add(Box.createHorizontalStrut(45));
		south_box.add(button_add);
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
		
		
		//设置组件初始状态
		button_add.setEnabled(false);
		button_delete.setEnabled(false);
		button_modify.setEnabled(false);
		
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
			String user_id=text_input.getText().trim();
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
				System.out.println(user_id);
				
				//文本框内容为空
				if(user_id.equals("")) {
					rs=sql.executeQuery("select * from employee");
				}
				
				else {
				rs=sql.executeQuery("select * from employee where 工号="+"'"+user_id+"'");
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
			//获得选中行的工号数据
			String temp_id=data[this.table.getSelectedRow()][1];
			System.out.println(temp_id);
			/*弹窗提醒*/
				int command_2=JOptionPane.showConfirmDialog(this, "是否删除员工"+temp_id+"?", "删除员工确认", JOptionPane.YES_NO_OPTION);
				if(command_2==JOptionPane.YES_OPTION) {
				/*数据库局部变量区*/
				Connection con=JDBC.getConnection();
				try {
					Statement sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
					int i=sql.executeUpdate("delete from employee where 工号="+"'"+temp_id+"'");
					if(i!=0) {
						JOptionPane.showMessageDialog(this, "删除成功~！");
					}
					else
						JOptionPane.showMessageDialog(this, "删除失败~！");
				} 
				catch (SQLException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(this, "删除过程发生错误~！");
				}
				}//command_2==JOptionPane.YES_OPTION
				else {
					JOptionPane.showMessageDialog(this, "取消删除员工"+temp_id);
				}//command_2==JOptionPane.NO_OPTION结束
			
			
		}//e.getSource()==button_delete结束
		
		//button_add区
		if(e.getSource()==button_add) {
			
			new EmployeeAdd(null);
			
		}
		
		//button_modify区
		if(e.getSource()==button_modify) {
			//获得选中行的工号数据
			String temp_id=data[this.table.getSelectedRow()][1];
			System.out.println(temp_id);
			EmployeeModify view= new EmployeeModify(null,temp_id);
}
		
	}
	
	
	//测试函数
	public static void main(String[] args) {
		new EmployeeQuery(null);

	}
}
