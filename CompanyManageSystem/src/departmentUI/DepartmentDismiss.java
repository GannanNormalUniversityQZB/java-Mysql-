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
	//�����Ա����
		JLabel label_depart=new JLabel("����ѡ���ţ�");
		JLabel label_detail=new JLabel("������ϸ��Ϣ");
		
		/*��ť��*/
		JButton button_dismiss=new JButton("��ɢ");
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
		public DepartmentDismiss(CompanyManage view,String transfered_depart) {
			super(view);
			//���ô���
			setLayout(new FlowLayout());
			setTitle("�޸Ĳ���");
			setBounds(new SetCenterSize().getCenterBounds(535,560));	
			setModal(true);	
			this.transfered_depart=transfered_depart;
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
				array_department=new String[row-1];
				String temp_department;
				for(int i=0;rs.next();i++) {
					temp_department=rs.getString(1);
					if(!temp_department.equals("��ҵ����"))
						array_department[i]=temp_department;
					else
						i--;
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
			
			
			//Ϊ�����Ӽ�����
			button_dismiss.addActionListener(this);
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
			
		//button_dismiss��
			if(e.getSource()==button_dismiss) {
				String temp_depart=(String)this.comboBox_department.getSelectedItem();
				
					int comfirm=JOptionPane.showConfirmDialog(this, "��ȷ��Ҫ��ɢ����"+temp_depart+"��\n���Ž�ɢ��,�ò�������Ա������ת�Ƶ���ҵ������~��", "��ʾ", JOptionPane.YES_NO_OPTION);
					if(comfirm==JOptionPane.YES_OPTION) {
					/*���ݿ�ֲ�����*/
					Connection con=JDBC.getConnection();
					try {
						//����employee
						PreparedStatement presql=con.prepareStatement("update employee set ����=? ,ְλ=? where ����=?");
						presql.setString(1, "��ҵ����");
						presql.setString(2, "��ҵ��Ա");
						presql.setString(3, temp_depart);
						int i=presql.executeUpdate();
						//����department
						presql=con.prepareStatement("delete from department where ����=?");
						presql.setString(1, temp_depart);
						int j=presql.executeUpdate();
						//����position
						presql=con.prepareStatement("delete from position where ��������=?");
						presql.setString(1, temp_depart);
						int k=presql.executeUpdate();
						
						if(i!=0 && j!=0 && k!=0) {
							JOptionPane.showMessageDialog(this, "���Ž�ɢ�ɹ�~��");
							repaint();
							
						}
						else
							JOptionPane.showMessageDialog(this, "���Ž�ɢʧ��~��");
					}
					catch (SQLException e1) {
						e1.printStackTrace();
					}
					}//!temp_new_depart.equals("")
					
					else
						JOptionPane.showMessageDialog(this, "��ȡ����ɢ~��");
					
				
				
				
			}//button_dismiss����

			
			
			//button_cancel��
			if(e.getSource()==button_cancel) {
				dispose();
			}//button_cancel����
		
	}
	public static void main(String[] args) {
		new DepartmentDismiss(null, null);

	}

}
