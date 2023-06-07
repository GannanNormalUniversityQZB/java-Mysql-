package positionUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;

import Tools.JDBC;
import Tools.SetCenterSize;
import UI.CompanyManage;
public class PositionModify extends JDialog implements ActionListener{
	//定义成员变量
		JLabel label_depart=new JLabel("选择部门：");
		JLabel label_position=new JLabel("修改职位：");
		JLabel label_reset_position=new JLabel("更改职位：");
		
		/*文本框区*/
		JTextField text_reset_position=new JTextField(15);
		
		/*按钮区*/
		JButton button_modify=new JButton("修改");
		JButton button_cancel=new JButton("取消");
		
		/*下拉列表区*/
		String array_position[];
		JComboBox<String> comboBox_position=new JComboBox<>();
		String array_depart[];
		JComboBox<String> comboBox_depart=new JComboBox<>();
		
		/*公司logo区*/
		public JLabel companyLogo=new JLabel(new ImageIcon("images\\Logo\\companyLogo2.png"));
		
		/*用于接收从其他业务传输过来的数据*/
		String transferred_depart;
		String transferred_position;
	
	
	//定义构造函数
	public PositionModify(CompanyManage view,String transferred_depart,String transferred_position) {
		super(view);
		//设置窗口
		setLayout(new FlowLayout());
		setTitle("修改职位");
		setBounds(new SetCenterSize().getCenterBounds(480,365));	
		setModal(true);
		/*设置图标*/
		Image icon = Toolkit.getDefaultToolkit().getImage("images\\icon\\icon3.png");
		setIconImage(icon);
		
		/*接收数据*/
		this.transferred_depart=transferred_depart;
		this.transferred_position=transferred_position;
		
		//添加组件
		/*创建中间容器*/
		Box Vbox=Box.createVerticalBox();
		Box box_depart=Box.createHorizontalBox();
		Box box_position=Box.createHorizontalBox();
		Box box_reset_position=Box.createHorizontalBox();
		Box box_button=Box.createHorizontalBox();
				
		box_depart.add(label_depart);
		box_depart.add(comboBox_depart);
				
		box_position.add(label_position);
		box_position.add(comboBox_position);
		
		box_reset_position.add(label_reset_position);
		box_reset_position.add(text_reset_position);
				
		box_button.add(button_modify);
		box_button.add(Box.createHorizontalStrut(35));
		box_button.add(button_cancel);
				
		Vbox.add(Box.createVerticalStrut(10));
		Vbox.add(box_depart);
		Vbox.add(Box.createVerticalStrut(20));
		Vbox.add(box_position);
		Vbox.add(Box.createVerticalStrut(20));
		Vbox.add(box_reset_position);
		Vbox.add(Box.createVerticalStrut(20));
		Vbox.add(box_button);
				
		add(companyLogo);
		add(Vbox);
		
		//设置部门组件初始状态
			
		/*数据库局部变量*/
		Connection con=JDBC.getConnection();
		try {
			Statement sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs=sql.executeQuery("select * from department ");
			rs.last();
			int row=rs.getRow();
			rs.beforeFirst();
			/*创建数组*/
			array_depart=new String[row];
			for(int i=0;rs.next();i++)
				array_depart[i]=rs.getString(1);			
			
			/*初始化职位*/
			comboBox_depart.removeAllItems();
			for(String a:array_depart)
					comboBox_depart.addItem(a);
			/*如有传输数据，确定其index*/
			if(transferred_depart!=null)
				comboBox_depart.setSelectedItem(transferred_depart);
			
			/*获得对应部门的职位*/
			/*获得选中的部门*/
			String temp_depart=(String)comboBox_depart.getSelectedItem();
			System.out.println(temp_depart);

			rs=sql.executeQuery("select 职位 from position where 所属部门="+"'"+temp_depart+"'");
			rs.last();
			int row_2=rs.getRow();
			rs.beforeFirst();
			/*更新数组*/
			array_position=new String[row_2];
			for(int i=0;rs.next();i++) {
				array_position[i]=rs.getString(1);
			}
			
			/*更新职位*/
			comboBox_position.removeAllItems();;
			for(String a:array_position) {
				comboBox_position.addItem(a);
			}
			/*如有传输数据，确定其index*/
			if(transferred_position!=null)
				comboBox_position.setSelectedItem(transferred_position);
			} 
			catch (SQLException e) {
					e.printStackTrace();
			}
				
				
				
		//为组件添加监视器
		button_modify.addActionListener(this);
		button_cancel.addActionListener(this);
		comboBox_depart.addActionListener(this);
				
		//窗口结尾处理
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
	}
		
	//重写接口函数
	@Override
	public void actionPerformed(ActionEvent e) {
		//根据部门更改相应的职位	
		if(e.getSource()==comboBox_depart) {
			/*数据库局部变量*/
			Connection con=JDBC.getConnection();
			try {
				Statement sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				/*获得对应部门的职位*/
				/*获得选中的部门*/
				String temp_depart=(String)comboBox_depart.getSelectedItem();
				System.out.println("执行comboBox事件源事件"+temp_depart);

				ResultSet rs=sql.executeQuery("select 职位 from position where 所属部门="+"'"+temp_depart+"'");
				rs.last();
				int row_2=rs.getRow();
				rs.beforeFirst();
				/*更新数组*/
				array_position=new String[row_2];
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
		}//e.getSource()==comboBox_depart结束
		
		if(e.getSource()==button_modify) {
			/*获得组件信息*/
			String temp_depart=(String)this.comboBox_depart.getSelectedItem();
			String temp_old_position=(String)this.comboBox_position.getSelectedItem();
			String temp_reset_position=this.text_reset_position.getText().trim();
			/*定义数据库局部变量*/
			Connection con=JDBC.getConnection();
			PreparedStatement presql;
			if(!temp_reset_position.equals("")) {
				try {
					//更新position
					presql=con.prepareStatement("update position set 职位=? where 所属部门=? and 职位=?");
					presql.setString(1, temp_reset_position);
					presql.setString(2, temp_depart);
					presql.setString(3, temp_old_position);
					int i=presql.executeUpdate();
					//更新employee
					presql=con.prepareStatement("update employee set 职位=? where 部门=? and 职位=? ");
					presql.setString(1, temp_reset_position);
					presql.setString(2, temp_depart);
					presql.setString(3, temp_old_position);
					int j=presql.executeUpdate();
					
					if(i!=0 || j!=0) {
						JOptionPane.showMessageDialog(this, "职位名称修改成功~！");
					}
					else {
						JOptionPane.showMessageDialog(this, "职位名称修改失败~！");
					}
					
				} 
				catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			else
				JOptionPane.showMessageDialog(this, "职位修改名称不得为空", "警告", JOptionPane.WARNING_MESSAGE);
		}
		
		else if(e.getSource()==button_cancel) {
			dispose();
		}
			
	}
	
	//测试
	public static void main(String[] args) {
		
		new PositionModify(null, null, null);
	}

}
