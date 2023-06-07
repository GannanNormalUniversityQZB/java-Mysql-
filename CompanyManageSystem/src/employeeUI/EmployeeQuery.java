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
	//�����Ա����
	JLabel label_input=new JLabel("�����빤�ţ�");
	JTextField text_input=new JTextField(18);
	/*��ť��*/
	JButton button_query=new JButton("��ѯ");
	JButton button_modify=new JButton("�޸�");
	JButton button_delete=new JButton("ɾ��");
	JButton button_add=new JButton("����");
	
	/*�����*/
	JTable table;
	DefaultTableModel tableModel;
	String title[];
	String data[][];
	
	//���幹�캯��
	public EmployeeQuery(CompanyManage view) {
		super(view);
		//���ô���
		setLayout(new BorderLayout());
		setTitle("��ѯԱ��");
		setBounds(new SetCenterSize().getCenterBounds(1000,550));	
		setModal(true);	
		/*����ͼ��*/
		Image icon = Toolkit.getDefaultToolkit().getImage("images\\icon\\icon3.png");
		setIconImage(icon);
		
		//��������
		/*�����м�����*/
		Box north_box=Box.createHorizontalBox();
		Box south_box=Box.createHorizontalBox();
		JPanel north_panel=new JPanel();
		JPanel south_panel=new JPanel();
		
		/*������������*/
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
		
		//��ӱ��
		tableModel=new DefaultTableModel();
		table=new JTable(tableModel);
		
		/*���ñ�����*/
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
		tcr.setHorizontalAlignment(SwingConstants.CENTER);
		table.setDefaultRenderer(Object.class, tcr);
		
		
		add(north_panel,BorderLayout.NORTH);
		add(new JScrollPane(table),BorderLayout.CENTER);
		add(south_panel,BorderLayout.SOUTH);
		
		
		//���������ʼ״̬
		button_add.setEnabled(false);
		button_delete.setEnabled(false);
		button_modify.setEnabled(false);
		
		//Ϊ�����Ӽ�����
		button_query.addActionListener(this);
		button_add.addActionListener(this);
		button_delete.addActionListener(this);
		button_modify.addActionListener(this);
		
		//���ڽ�β����
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
	
	
	
	//��д�ӿں���
	@Override
	public void actionPerformed(ActionEvent e) {
		//button_query��
		if(e.getSource()==button_query) {
			/*���ݿ�ֲ�������*/
			String user_id=text_input.getText().trim();
			Connection con=JDBC.getConnection();
			Statement sql;
			ResultSetMetaData metaData;
			ResultSet rs;
			/*��������*/
			int row=0;
			int col=0;
			/*�������*/
			try {
				sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				System.out.println(user_id);
				
				//�ı�������Ϊ��
				if(user_id.equals("")) {
					rs=sql.executeQuery("select * from employee");
				}
				
				else {
				rs=sql.executeQuery("select * from employee where ����="+"'"+user_id+"'");
				} //else��β
				
				//�������
				System.out.println("1111");
				//ע�ͣ������´����У�Ҫ�Ƚ��������Ļ�ȡ���ٽ��������Ļ�ȡ����������
				                                                         
				
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
				//�������
				System.out.println(12);
				
				//�������״̬
				button_add.setEnabled(true);
				button_delete.setEnabled(true);
				button_modify.setEnabled(true);
			}
			catch (SQLException e1) {
				System.out.println(e1);
				JOptionPane.showMessageDialog(this, "δ��ѯ�������Ϣ�������²�ѯ", "����~��",JOptionPane.WARNING_MESSAGE);
			}
			
		
		}
		
		//button_delete��
		if(e.getSource()==button_delete) {
			//���ѡ���еĹ�������
			String temp_id=data[this.table.getSelectedRow()][1];
			System.out.println(temp_id);
			/*��������*/
				int command_2=JOptionPane.showConfirmDialog(this, "�Ƿ�ɾ��Ա��"+temp_id+"?", "ɾ��Ա��ȷ��", JOptionPane.YES_NO_OPTION);
				if(command_2==JOptionPane.YES_OPTION) {
				/*���ݿ�ֲ�������*/
				Connection con=JDBC.getConnection();
				try {
					Statement sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
					int i=sql.executeUpdate("delete from employee where ����="+"'"+temp_id+"'");
					if(i!=0) {
						JOptionPane.showMessageDialog(this, "ɾ���ɹ�~��");
					}
					else
						JOptionPane.showMessageDialog(this, "ɾ��ʧ��~��");
				} 
				catch (SQLException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(this, "ɾ�����̷�������~��");
				}
				}//command_2==JOptionPane.YES_OPTION
				else {
					JOptionPane.showMessageDialog(this, "ȡ��ɾ��Ա��"+temp_id);
				}//command_2==JOptionPane.NO_OPTION����
			
			
		}//e.getSource()==button_delete����
		
		//button_add��
		if(e.getSource()==button_add) {
			
			new EmployeeAdd(null);
			
		}
		
		//button_modify��
		if(e.getSource()==button_modify) {
			//���ѡ���еĹ�������
			String temp_id=data[this.table.getSelectedRow()][1];
			System.out.println(temp_id);
			EmployeeModify view= new EmployeeModify(null,temp_id);
}
		
	}
	
	
	//���Ժ���
	public static void main(String[] args) {
		new EmployeeQuery(null);

	}
}
