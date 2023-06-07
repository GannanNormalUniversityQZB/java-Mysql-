package userUI;

import javax.swing.*;

import Tools.JDBC;
import Tools.SetCenterSize;
import UI.CompanyManage;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class UserDelete extends JDialog implements ActionListener{
	//定义成员变量
	JLabel label_tip=new JLabel("请输入用户名:");
	JTextField text_id=new JTextField(18);
	JTextArea area_information=new JTextArea(18,45);
	JButton button_check=new JButton("查询");
	JButton button_delete=new JButton("删除");
	JButton button_cancel=new JButton("取消");
	
	/*定义从check确定的学号参数*/
	String id_deleted;
	
		
	//定义构造函数
	public UserDelete(CompanyManage view) {
		super(view);
		//设置窗口
		setLayout(new FlowLayout());
		setTitle("删除用户");
		setBounds(new SetCenterSize().getCenterBounds(500,450));	
		setModal(true);	
		/*设置图标*/
		Image icon = Toolkit.getDefaultToolkit().getImage("images\\icon\\icon3.png");
		setIconImage(icon);
		
		//添加组件
		
		/*设置中间容器*/
		Box top_box=Box.createHorizontalBox();
		Box bottom_box=Box.createHorizontalBox();
		Box center_box=Box.createVerticalBox();
		
		top_box.add(label_tip);
		top_box.add(Box.createHorizontalStrut(20));
		top_box.add(text_id);
		
		
		bottom_box.add(button_check);
		bottom_box.add(Box.createHorizontalStrut(35));
		bottom_box.add(button_delete);
		bottom_box.add(Box.createHorizontalStrut(35));
		bottom_box.add(button_cancel);
		
		center_box.add(Box.createVerticalStrut(3));
		center_box.add(new JScrollPane(area_information));
		center_box.add(Box.createVerticalStrut(5));
		add(top_box);
		add(center_box);
		add(bottom_box);
		
		
		//设置按钮初始状态
		button_delete.setEnabled(false);
		
		
		//为按钮添加监视器
		button_check.addActionListener(this);
		button_delete.addActionListener(this);
		button_cancel.addActionListener(this);
		
		
		//窗口结尾处理
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
	
	
	
	
	
	
	
	//重写接口函数
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==button_check) {
			String temp_id=this.text_id.getText().trim();
			/*定义数据库局部变量*/
			Connection con=JDBC.getConnection();
			try {
				Statement sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet rs=sql.executeQuery("select * from administrators where name="+"'"+temp_id+"'");
				ResultSetMetaData metaData;
				//测试语句
				System.out.println(123);
				
				rs.next();
				//测试语句
				area_information.append("查询用户："+temp_id+"\n"+"结果："+rs.getString(1).equals(temp_id)+"\n");
				
					//测试语句
					System.out.println(temp_id);
					/*数据域区*/
					String all_Infomation[][]=null;
					String title_Head[];
					int row=0;
					int col=0;
					/*获得数据*/
					rs.beforeFirst();
						metaData=rs.getMetaData();
						col=metaData.getColumnCount();
						/*获得字段数组*/
						title_Head=new String[col];
						for(int i=0;i<col;i++) {
							title_Head[i]=metaData.getColumnName(i+1);
						}
						
						/*获得行数*/
						rs.last();
						row=rs.getRow();
						rs.beforeFirst();
						
						//创建数组all_Information
						all_Infomation=new String [row][col];
						
						//创建控制循环的计数器
						for(int i=0;rs.next();i++)
							for(int j=0;j<col;j++)
								all_Infomation[i][j]=rs.getString(j+1);
						
						
						
						//将结果显示在界面上

						/*字段*/
						for(int i=0;i<col;i++) 
							area_information.append(title_Head[i]+"\t");
						area_information.append("\n");
						
						/*数据*/
						for(int i=0;i<all_Infomation.length;i++) {
							for(int j=0;j<all_Infomation[0].length;j++) 
								area_information.append(all_Infomation[i][j]+"\t");
							area_information.append("\n");
						}
						
						//传送数据给删除按钮
						id_deleted=temp_id;
						//打开删除按钮权限
						this.button_delete.setEnabled(true);
			}//try块结束
			
			catch (SQLException e1) {
				System.out.println("sql出现错误~！");
				area_information.append("查询失败，请检查信息是否正确~！\n");
			}
			
		}
		
		//删除按钮区
		if(e.getSource()==button_delete) {
			/*定义局部变量*/
			Connection con=JDBC.getConnection();
			Statement sql;
			try {
				sql = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				int state=JOptionPane.showConfirmDialog(this, "您确定要删除用户"+id_deleted+"吗？");
				if(state==JOptionPane.YES_OPTION) {
				int i=sql.executeUpdate("delete from administrators where name="+"'"+id_deleted+"'");
				if(i!=0) {
					JOptionPane.showMessageDialog(this, "删除成功~！");
					this.button_delete.setEnabled(false);
				}
				}
				else {
					JOptionPane.showMessageDialog(this, "删除失败~！");
				}
			} 
			catch (SQLException e1) {
				e1.printStackTrace();
				
			}
					
		}
		
		//取消按钮区
		if(e.getSource()==button_cancel) {
			dispose();
		}
}
		
	
	
	//测试函数
	public static void main(String[] args) {
		
		new UserDelete(null);
	}
}
