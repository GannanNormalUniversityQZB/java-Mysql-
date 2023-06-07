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
	//�����Ա����
	JLabel label_depart=new JLabel("����ѡ���ţ�");
	JLabel label_modify_name=new JLabel("�޸Ĳ������ƣ�");
	JLabel label_detail=new JLabel("������ϸ��Ϣ");
	
	/*�ı�����*/
	JTextField text_modify_name=new JTextField(15);
	
	/*��ť��*/
	JButton button_modify=new JButton("�޸�");
	JButton button_cancel=new JButton("ȡ��");
	
	/*�����б���*/
	String array_department[];
	JComboBox<String> comboBox_department;
	
	/*�����*/
	JTable table;
	DefaultTableModel tableModel;
	String title[];
	String data[][];
	
	/*logo��*/
	public JLabel companyLogo=new JLabel(new ImageIcon("images\\Logo\\companyLogo2.png"));
	
	/*���ڽ��մ�����ҵ��������Ĳ�����Ϣ*/
	String transfered_depart;
	
	//���幹�캯��
	public DepartmentModify(CompanyManage view,String transfered_depart) {
		super(view);
		//���ô���
		setLayout(new FlowLayout());
		setTitle("�޸Ĳ���");
		setBounds(new SetCenterSize().getCenterBounds(535,560));	
		setModal(true);	
		/*����ͼ��*/
		Image icon = Toolkit.getDefaultToolkit().getImage("images\\icon\\icon3.png");
		setIconImage(icon);
		
		/*��������*/
		this.transfered_depart=transfered_depart;
		
		/*���������ʼ״̬*/
		//�����б�
		Connection con=JDBC.getConnection();
		try {
			Statement sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs=sql.executeQuery("select * from department");
			rs.last();
			int row=rs.getRow();
			rs.beforeFirst();
			/*��������*/
			array_department=new String[row];
			for(int i=0;rs.next();i++) {
				array_department[i]=rs.getString(1);
			}
			/*��ʼ�������б�*/
			comboBox_department=new JComboBox<>(array_department);
			
			/*����Ǵ�����ҵ��򿪽���*/
			if(transfered_depart!=null)
					comboBox_department.setSelectedItem(transfered_depart);
			
			//���
			//��ʼ�����
			tableModel=new DefaultTableModel();
			table=new JTable(tableModel);
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			
							
			/*���ñ�����*/
			DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
			tcr.setHorizontalAlignment(SwingConstants.CENTER);
			table.setDefaultRenderer(Object.class, tcr);
			
			
			int col;
			int row_2;
			String temp_depart=(String)this.comboBox_department.getSelectedItem();
			ResultSetMetaData metaData;
			rs=sql.executeQuery("select * from employee where ����='"+temp_depart+"'");
		
			/*�����ȡ�ֶ�������*/
			metaData=rs.getMetaData();
			col=metaData.getColumnCount();
			/*����ֶ�����*/
			title=new String[col];
			for(int i=0;i<col;i++) {
				title[i]=metaData.getColumnName(i+1);
			}
			
			/*�������*/
			rs.last();
			row_2=rs.getRow();
			rs.beforeFirst();
			
			//��������all_Information
			data=new String [row_2][col];
			
			//��������ѭ���ļ�����
			for(int i=0;rs.next();i++)
				for(int j=0;j<col;j++)
					data[i][j]=rs.getString(j+1);
			
			
			
			//�������ʾ�ڽ�����
			tableModel.setDataVector(data, title);
			tableModel.fireTableDataChanged();
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		//������
		/*�����м�����*/
		Box box_depart=Box.createHorizontalBox();
		Box box_new_depart=Box.createHorizontalBox();
		Box box_button=Box.createHorizontalBox();
		Box Vbox=Box.createVerticalBox();
		JPanel panel_table=new JPanel();
		JPanel panel_detail=new JPanel();
		JScrollPane scrollpane_table=new JScrollPane(table);
		scrollpane_table.setPreferredSize(new Dimension(500, 200));
		
		/*��װ*/
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
		
		
		//Ϊ�����Ӽ�����
		button_modify.addActionListener(this);
		button_cancel.addActionListener(this);
		comboBox_department.addActionListener(this);
		
		//���ڽ�β����
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
	
	
	
	//��д�ӿں���
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
			ResultSet rs=sql.executeQuery("select * from employee where ����='"+temp_depart+"'");
		
			/*�����ȡ�ֶ�������*/
			metaData=rs.getMetaData();
			col=metaData.getColumnCount();
			/*����ֶ�����*/
			title=new String[col];
			for(int i=0;i<col;i++) {
				title[i]=metaData.getColumnName(i+1);
			}
			
			/*�������*/
			rs.last();
			row_2=rs.getRow();
			rs.beforeFirst();
			
			//��������all_Information
			data=new String [row_2][col];
			
			//��������ѭ���ļ�����
			for(int i=0;rs.next();i++)
				for(int j=0;j<col;j++)
					data[i][j]=rs.getString(j+1);
			
			
			
			//�������ʾ�ڽ�����
			tableModel.setDataVector(data, title);
			tableModel.fireTableDataChanged();
			
		}
		catch (SQLException e1) {
			e1.printStackTrace();
		}
	}//comboBox_department����
		
	//button_mofify��
		if(e.getSource()==button_modify) {
			String temp_depart=(String)this.comboBox_department.getSelectedItem();
			String temp_new_depart=this.text_modify_name.getText();
			if(!temp_new_depart.equals("")) {
				int comfirm=JOptionPane.showConfirmDialog(this, "�����޸�Ϊ��"+temp_new_depart+"��,��ǰ��ְλ����ҵ�񣬶Բ���ְλ������Ӧ�޸�\n��ȷ��Ҫ�޸Ĳ���������", "��ʾ", JOptionPane.YES_NO_OPTION);
				if(comfirm==JOptionPane.YES_OPTION) {
				/*���ݿ�ֲ�����*/
				Connection con=JDBC.getConnection();
				try {
					//����employee
					PreparedStatement presql=con.prepareStatement("update employee set ����=? where ����=?");
					presql.setString(1, temp_new_depart);
					presql.setString(2, temp_depart);
					int i=presql.executeUpdate();
					//����department
					presql=con.prepareStatement("update department set ����=? where ����=?");
					presql.setString(1, temp_new_depart);
					presql.setString(2, temp_depart);
					int j=presql.executeUpdate();
					//����position
					presql=con.prepareStatement("update position set ��������=? where ��������=?");
					presql.setString(1, temp_new_depart);
					presql.setString(2, temp_depart);
					int k=presql.executeUpdate();
					
					if(i!=0 && j!=0 && k!=0) {
						JOptionPane.showMessageDialog(this, "���Ÿ����ɹ�~��");
						
						
					}
					else
						JOptionPane.showMessageDialog(this, "���Ÿ���ʧ��~��");
				}
				catch (SQLException e1) {
					e1.printStackTrace();
				}
				}//!temp_new_depart.equals("")
				
				else
					JOptionPane.showMessageDialog(this, "��ȡ������~��");
				
			}
			else
				JOptionPane.showMessageDialog(this, "�����޸����Ʋ���Ϊ��~��", "����", JOptionPane.WARNING_MESSAGE);
			
			
		}//button_modify����

		
		
		//button_cancel��
		if(e.getSource()==button_cancel) {
			dispose();
		}//button_cancel����
	
}
	
	//���Ժ���
	public static void main(String[] args) {
		
		new DepartmentModify(null,null);
	}

}
