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
	//�����Ա����
	JLabel label_depart=new JLabel("��ѡ���ţ�");
	
	/*��ť��*/
	JButton button_query=new JButton("��ѯ");
	JButton button_new_depart=new JButton("��������");
	JButton button_modify_depart=new JButton("�޸Ĳ���");
	JButton button_cancel=new JButton("ȡ������");
	JButton button_dismiss=new JButton("��ɢ����");
	/*�����б���*/
	String array_depart[];
	JComboBox<String> comboBox_depart;
	
	/*�����*/
	JTable table;
	DefaultTableModel tableModel;
	String title[];
	String data[][];
	
	//�������캯��
	public DepartmentQuery(CompanyManage view) {
		super(view);
		//���ô���
		setLayout(new BorderLayout());
		setTitle("��ѯ����");
		setBounds(new SetCenterSize().getCenterBounds(1000,550));	
		setModal(true);	
		/*����ͼ��*/
		Image icon = Toolkit.getDefaultToolkit().getImage("images\\icon\\icon3.png");
		setIconImage(icon);
		
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
			array_depart=new String[row+1];
			array_depart[0]="------��ѯ���в���------";
			for(int i=1;rs.next();i++) {
				array_depart[i]=rs.getString(1);
			}
			/*��ʼ�������б�*/
			comboBox_depart=new JComboBox<>(array_depart);
			
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		//������
		/*�����м�*/
		Box north_box=Box.createHorizontalBox();
		JPanel panel_north=new JPanel();
		
		Box south_box=Box.createHorizontalBox();
		JPanel panel_south=new JPanel();
		
		/*��װ*/
		north_box.add(label_depart);
		north_box.add(Box.createHorizontalStrut(15));
		north_box.add(comboBox_depart);
		north_box.add(Box.createHorizontalStrut(15));
		north_box.add(button_query);
		north_box.add(Box.createHorizontalStrut(15));
		panel_north.add(north_box);
		
		
		//��ӱ��
		tableModel=new DefaultTableModel();
		table=new JTable(tableModel);
				
		/*���ñ�����*/
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
		
		
		
		//Ϊ�����Ӽ�����
		button_modify_depart.addActionListener(this);
		button_query.addActionListener(this);
		button_new_depart.addActionListener(this);
		button_cancel.addActionListener(this);
		button_dismiss.addActionListener(this);
		
		//���ڽ�β����
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
	
	
	
	
	
	//��д�ӿں���
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==button_query) {
			/*�����Ϣ�ֲ�����*/
			int col;
			int row;
			String temp_depart=(String)this.comboBox_depart.getSelectedItem();
			/*���ݿ���ʱ������*/
			Connection con=JDBC.getConnection();
			Statement sql;
			ResultSet rs;
			ResultSetMetaData metaData;
			try {
				sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				if(temp_depart.equals("------��ѯ���в���------")) {
					rs=sql.executeQuery("select * from employee order by ���� ");
				}
				else {
					rs=sql.executeQuery("select * from employee where ����='"+temp_depart+"'");
				}
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
					row=rs.getRow();
					rs.beforeFirst();
					
					//��������all_Information
					data=new String [row][col];
					
					//��������ѭ���ļ�����
					for(int i=0;rs.next();i++)
						for(int j=0;j<col;j++)
							data[i][j]=rs.getString(j+1);
					
					
					
					//�������ʾ�ڽ�����
					tableModel.setDataVector(data, title);
					tableModel.fireTableDataChanged();
					
					JOptionPane.showMessageDialog(this, "��ѯ�ɹ�~��");
					
				}
			 
			catch (SQLException e1) {
				e1.printStackTrace();
			}
	
		}//button_query��
		
		//button_new_depart��
		if(e.getSource()==button_new_depart) {
			new DepartmentAdd(null);
		}
			
		//button_cancel��
		if(e.getSource()==button_cancel)
			dispose();
		
		//button_modify_depart
		if(e.getSource()==button_modify_depart) {
			String temp_depart=(String)this.comboBox_depart.getSelectedItem();
			new DepartmentModify(null, temp_depart);
		}
		
		//button_dismissȡ
		if(e.getSource()==button_dismiss) {
			String temp_depart=(String)this.comboBox_depart.getSelectedItem();
			new DepartmentDismiss(null, temp_depart);
		}
	
	}
	
	//���Ժ���
	public static void main(String[] args) {
			new DepartmentQuery(null);
	}
}
