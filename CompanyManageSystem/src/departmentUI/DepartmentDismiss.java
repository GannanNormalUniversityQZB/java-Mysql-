package departmentUI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Tools.JDBC;
import Tools.SetCenterSize;
import UI.CompanyManage;

public class DepartmentDismiss extends JDialog implements ActionListener{
	//定义成员变量
		JLabel label_depart=new JLabel("请您选择部门：");
		JLabel label_detail=new JLabel("部门详细信息");
		
		/*按钮区*/
		JButton button_dismiss=new JButton("解散");
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
		public DepartmentDismiss(CompanyManage view,String transfered_depart) {
			super(view);
			//设置窗口
			setLayout(new FlowLayout());
			setTitle("修改部门");
			setBounds(new SetCenterSize().getCenterBounds(535,560));	
			setModal(true);	
			this.transfered_depart=transfered_depart;
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
				array_department=new String[row-1];
				String temp_department;
				for(int i=0;rs.next();i++) {
					temp_department=rs.getString(1);
					if(!temp_department.equals("待业部门"))
						array_department[i]=temp_department;
					else
						i--;
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
			
	
			panel_detail.add(label_detail);
			panel_table.add(scrollpane_table);
			
			box_button.add(button_dismiss);
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
			button_dismiss.addActionListener(this);
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
			
		//button_dismiss区
			if(e.getSource()==button_dismiss) {
				String temp_depart=(String)this.comboBox_department.getSelectedItem();
				
					int comfirm=JOptionPane.showConfirmDialog(this, "您确定要解散部门"+temp_depart+"吗？\n部门解散后,该部门所有员工将会转移到待业部门中~！", "提示", JOptionPane.YES_NO_OPTION);
					if(comfirm==JOptionPane.YES_OPTION) {
					/*数据库局部变量*/
					Connection con=JDBC.getConnection();
					try {
						//更新employee
						PreparedStatement presql=con.prepareStatement("update employee set 部门=? ,职位=? where 部门=?");
						presql.setString(1, "待业部门");
						presql.setString(2, "待业人员");
						presql.setString(3, temp_depart);
						int i=presql.executeUpdate();
						//更新department
						presql=con.prepareStatement("delete from department where 部门=?");
						presql.setString(1, temp_depart);
						int j=presql.executeUpdate();
						//更新position
						presql=con.prepareStatement("delete from position where 所属部门=?");
						presql.setString(1, temp_depart);
						int k=presql.executeUpdate();
						
						if(i!=0 && j!=0 && k!=0) {
							JOptionPane.showMessageDialog(this, "部门解散成功~！");
							repaint();
							
						}
						else
							JOptionPane.showMessageDialog(this, "部门解散失败~！");
					}
					catch (SQLException e1) {
						e1.printStackTrace();
					}
					}//!temp_new_depart.equals("")
					
					else
						JOptionPane.showMessageDialog(this, "已取消解散~！");
					
				
				
				
			}//button_dismiss结束

			
			
			//button_cancel区
			if(e.getSource()==button_cancel) {
				dispose();
			}//button_cancel结束
		
	}
	public static void main(String[] args) {
		new DepartmentDismiss(null, null);

	}

}
