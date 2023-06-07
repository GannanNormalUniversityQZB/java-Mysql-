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
	//�����Ա����
		JLabel label_input=new JLabel("��ѡ���ţ�");
		
		/*�����б���*/
		String array_department[];
		JComboBox<String> comboBox_department;
		
		/*��ť��*/
		JButton button_query=new JButton("��ѯ");
		JButton button_modify=new JButton("�޸�");
		JButton button_delete=new JButton("ɾ��");
		JButton button_add=new JButton("����");
		JButton button_cancel=new JButton("ȡ��");
		
		/*�����*/
		JTable table;
		DefaultTableModel tableModel;
		String title[];
		String data[][];
		
		//���幹�캯��
		public PositionQuery(CompanyManage view,String transfered_depart) {
			super(view);
			//���ô���
			setLayout(new BorderLayout());
			setTitle("��ѯְλ");
			setBounds(new SetCenterSize().getCenterBounds(500,550));	
			setModal(true);	
			/*����ͼ��*/
			Image icon = Toolkit.getDefaultToolkit().getImage("images\\icon\\icon3.png");
			setIconImage(icon);
			
			//���������ʼ״̬
			button_add.setEnabled(false);
			button_delete.setEnabled(false);
			button_modify.setEnabled(false);
			
			
			/*��ʼ�������б�*/
			Connection con=JDBC.getConnection();
			try {
				Statement sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet rs=sql.executeQuery("select * from department");
				rs.last();
				int row=rs.getRow();
				rs.beforeFirst();
				/*��������*/
				array_department=new String[row+1];
				array_department[0]="------��ѯ����ְλ------";
				for(int i=1;rs.next();i++) {
					array_department[i]=rs.getString(1);
				}
				/*��ʼ�������б�*/
				comboBox_department=new JComboBox<>(array_department);
				
				/*����Ǵ�����ҵ��򿪽���*/
				if(transfered_depart!=null)
						comboBox_department.setSelectedItem(transfered_depart);
			}
			catch (Exception e) {
				System.out.println(e);
			}
			//��������
			/*�����м�����*/
			Box north_box=Box.createHorizontalBox();
			Box south_box=Box.createHorizontalBox();
			JPanel north_panel=new JPanel();
			JPanel south_panel=new JPanel();
			
			/*������������*/
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
				String temp_depart=(String)this.comboBox_department.getSelectedItem();
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
					//�������
					System.out.println(temp_depart);
					
					//�ı�������Ϊ��
					if(temp_depart.equals("------��ѯ����ְλ------")) {
						rs=sql.executeQuery("select * from position");
					}
					
					else {
					rs=sql.executeQuery("select * from position where ��������="+"'"+temp_depart+"'");
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
				//���ѡ���еĲ�����ְλ����
				String temp_depart=data[this.table.getSelectedRow()][0];
				String temp_position=data[this.table.getSelectedRow()][1];
				System.out.println(temp_depart+temp_position);
				/*����PositionDelete����*/
				new PositionDelete(null,temp_depart, temp_position);
				
			}//e.getSource()==button_delete����
			
			//button_add��
			if(e.getSource()==button_add) {
				//���ѡ���еĲ�����ְλ����
				String temp_depart=data[this.table.getSelectedRow()][0];
				new PositionAdd(null,temp_depart);
				
			}
			
			//button_modify��
			if(e.getSource()==button_modify) {
				//���ѡ���еĲ�����ְλ����
				String temp_depart=data[this.table.getSelectedRow()][0];
				String temp_position=data[this.table.getSelectedRow()][1];
				System.out.println(temp_depart+temp_position);
				new PositionModify(null,temp_depart,temp_position);
	}
			
		}
		
	
	//���Ժ���
	public static void main(String[] args) {
		new PositionQuery(null, null);

	}

	

}
