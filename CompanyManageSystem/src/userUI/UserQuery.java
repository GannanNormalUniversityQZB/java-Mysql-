package userUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Tools.JDBC;
import Tools.SetCenterSize;
import UI.CompanyManage;


public class UserQuery extends JDialog implements ActionListener{
	//定义成员变量
	JLabel check_Tip=new JLabel("请输入用户名:	");
	JTextField text_InputID=new JTextField(15);
	JButton button_Check=new JButton("查询");
	/*表格区*/
	JTable table;
	DefaultTableModel tableModel;
	String title[];
	String data[][];
	
	//构造函数
	public  UserQuery(CompanyManage view) {
		super(view);
		init();
	}
	
	//定义初始化函数
	public void init() {
		//设置窗口
		setTitle("企业员工信息管理系统");
		setBounds(new SetCenterSize().getCenterBounds(600,450));	
		setModal(true);
		setLayout(new BorderLayout());
		/*设置窗口图标*/
		Image icon = Toolkit.getDefaultToolkit().getImage("images\\icon\\icon3.png");
		setIconImage(icon);
		
		/*设置标签字体*/
		check_Tip.setFont(new Font("宋体", Font.PLAIN, 16));
		
		//添加north组件
		Box Vbox=Box.createVerticalBox();
		
		Box northBox=Box.createHorizontalBox();
		northBox.add(check_Tip);
		northBox.add(Box.createHorizontalStrut(15));
		northBox.add(text_InputID);
		northBox.add(Box.createHorizontalStrut(15));
		northBox.add(button_Check);
		northBox.add(Box.createGlue());
		
		
		/*垂直容器嵌套平行容器*/
		Vbox.add(Vbox.createVerticalStrut(15));
		Vbox.add(northBox);
		Vbox.add(Vbox.createVerticalStrut(15));
		add(Vbox,BorderLayout.NORTH);
		
		//添加表格
		tableModel=new DefaultTableModel();
		table=new JTable(tableModel);
		
		/*设置表格居中*/
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
		tcr.setHorizontalAlignment(SwingConstants.CENTER);
		table.setDefaultRenderer(Object.class, tcr);
	
		add(new JScrollPane(table),BorderLayout.CENTER);
		//为按钮添加监听器
		button_Check.addActionListener(this);
		/*修改按钮大小*/
		button_Check.setPreferredSize(new Dimension(90, 8));
		
		
		
		//窗口结尾处理
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
	

	//重写接口函数
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==button_Check) {
			/*数据库局部变量区*/
			String user_id=text_InputID.getText().trim();
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
					rs=sql.executeQuery("select * from administrators");
				}
				
				//
				else {
				rs=sql.executeQuery("select * from administrators where name="+"'"+user_id+"'");
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
				System.out.println(12);
			
			}
			catch (SQLException e1) {
				System.out.println(e1);
				JOptionPane.showMessageDialog(this, "未查询到相关信息，请重新查询", "错误~！",JOptionPane.WARNING_MESSAGE);
			}
			
		}
		
		
	}

	

	public static void main(String[] args) {
		
		new UserQuery(null);
	}

}
