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
	//�����Ա����
		JLabel label_depart=new JLabel("ѡ���ţ�");
		JLabel label_position=new JLabel("�޸�ְλ��");
		JLabel label_reset_position=new JLabel("����ְλ��");
		
		/*�ı�����*/
		JTextField text_reset_position=new JTextField(15);
		
		/*��ť��*/
		JButton button_modify=new JButton("�޸�");
		JButton button_cancel=new JButton("ȡ��");
		
		/*�����б���*/
		String array_position[];
		JComboBox<String> comboBox_position=new JComboBox<>();
		String array_depart[];
		JComboBox<String> comboBox_depart=new JComboBox<>();
		
		/*��˾logo��*/
		public JLabel companyLogo=new JLabel(new ImageIcon("images\\Logo\\companyLogo2.png"));
		
		/*���ڽ��մ�����ҵ�������������*/
		String transferred_depart;
		String transferred_position;
	
	
	//���幹�캯��
	public PositionModify(CompanyManage view,String transferred_depart,String transferred_position) {
		super(view);
		//���ô���
		setLayout(new FlowLayout());
		setTitle("�޸�ְλ");
		setBounds(new SetCenterSize().getCenterBounds(480,365));	
		setModal(true);
		/*����ͼ��*/
		Image icon = Toolkit.getDefaultToolkit().getImage("images\\icon\\icon3.png");
		setIconImage(icon);
		
		/*��������*/
		this.transferred_depart=transferred_depart;
		this.transferred_position=transferred_position;
		
		//������
		/*�����м�����*/
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
		
		//���ò��������ʼ״̬
			
		/*���ݿ�ֲ�����*/
		Connection con=JDBC.getConnection();
		try {
			Statement sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs=sql.executeQuery("select * from department ");
			rs.last();
			int row=rs.getRow();
			rs.beforeFirst();
			/*��������*/
			array_depart=new String[row];
			for(int i=0;rs.next();i++)
				array_depart[i]=rs.getString(1);			
			
			/*��ʼ��ְλ*/
			comboBox_depart.removeAllItems();
			for(String a:array_depart)
					comboBox_depart.addItem(a);
			/*���д������ݣ�ȷ����index*/
			if(transferred_depart!=null)
				comboBox_depart.setSelectedItem(transferred_depart);
			
			/*��ö�Ӧ���ŵ�ְλ*/
			/*���ѡ�еĲ���*/
			String temp_depart=(String)comboBox_depart.getSelectedItem();
			System.out.println(temp_depart);

			rs=sql.executeQuery("select ְλ from position where ��������="+"'"+temp_depart+"'");
			rs.last();
			int row_2=rs.getRow();
			rs.beforeFirst();
			/*��������*/
			array_position=new String[row_2];
			for(int i=0;rs.next();i++) {
				array_position[i]=rs.getString(1);
			}
			
			/*����ְλ*/
			comboBox_position.removeAllItems();;
			for(String a:array_position) {
				comboBox_position.addItem(a);
			}
			/*���д������ݣ�ȷ����index*/
			if(transferred_position!=null)
				comboBox_position.setSelectedItem(transferred_position);
			} 
			catch (SQLException e) {
					e.printStackTrace();
			}
				
				
				
		//Ϊ�����Ӽ�����
		button_modify.addActionListener(this);
		button_cancel.addActionListener(this);
		comboBox_depart.addActionListener(this);
				
		//���ڽ�β����
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
	}
		
	//��д�ӿں���
	@Override
	public void actionPerformed(ActionEvent e) {
		//���ݲ��Ÿ�����Ӧ��ְλ	
		if(e.getSource()==comboBox_depart) {
			/*���ݿ�ֲ�����*/
			Connection con=JDBC.getConnection();
			try {
				Statement sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				/*��ö�Ӧ���ŵ�ְλ*/
				/*���ѡ�еĲ���*/
				String temp_depart=(String)comboBox_depart.getSelectedItem();
				System.out.println("ִ��comboBox�¼�Դ�¼�"+temp_depart);

				ResultSet rs=sql.executeQuery("select ְλ from position where ��������="+"'"+temp_depart+"'");
				rs.last();
				int row_2=rs.getRow();
				rs.beforeFirst();
				/*��������*/
				array_position=new String[row_2];
				for(int i=0;rs.next();i++) {
					array_position[i]=rs.getString(1);
				}
				
				/*����ְλ*/
				comboBox_position.removeAllItems();;
				for(String a:array_position) {
					comboBox_position.addItem(a);
				}
				
				} 
				catch (SQLException e1) {
						e1.printStackTrace();
				}
		}//e.getSource()==comboBox_depart����
		
		if(e.getSource()==button_modify) {
			/*��������Ϣ*/
			String temp_depart=(String)this.comboBox_depart.getSelectedItem();
			String temp_old_position=(String)this.comboBox_position.getSelectedItem();
			String temp_reset_position=this.text_reset_position.getText().trim();
			/*�������ݿ�ֲ�����*/
			Connection con=JDBC.getConnection();
			PreparedStatement presql;
			if(!temp_reset_position.equals("")) {
				try {
					//����position
					presql=con.prepareStatement("update position set ְλ=? where ��������=? and ְλ=?");
					presql.setString(1, temp_reset_position);
					presql.setString(2, temp_depart);
					presql.setString(3, temp_old_position);
					int i=presql.executeUpdate();
					//����employee
					presql=con.prepareStatement("update employee set ְλ=? where ����=? and ְλ=? ");
					presql.setString(1, temp_reset_position);
					presql.setString(2, temp_depart);
					presql.setString(3, temp_old_position);
					int j=presql.executeUpdate();
					
					if(i!=0 || j!=0) {
						JOptionPane.showMessageDialog(this, "ְλ�����޸ĳɹ�~��");
					}
					else {
						JOptionPane.showMessageDialog(this, "ְλ�����޸�ʧ��~��");
					}
					
				} 
				catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			else
				JOptionPane.showMessageDialog(this, "ְλ�޸����Ʋ���Ϊ��", "����", JOptionPane.WARNING_MESSAGE);
		}
		
		else if(e.getSource()==button_cancel) {
			dispose();
		}
			
	}
	
	//����
	public static void main(String[] args) {
		
		new PositionModify(null, null, null);
	}

}
