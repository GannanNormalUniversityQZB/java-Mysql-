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
	//�����Ա����
	JLabel label_tip=new JLabel("�������û���:");
	JTextField text_id=new JTextField(18);
	JTextArea area_information=new JTextArea(18,45);
	JButton button_check=new JButton("��ѯ");
	JButton button_delete=new JButton("ɾ��");
	JButton button_cancel=new JButton("ȡ��");
	
	/*�����checkȷ����ѧ�Ų���*/
	String id_deleted;
	
		
	//���幹�캯��
	public UserDelete(CompanyManage view) {
		super(view);
		//���ô���
		setLayout(new FlowLayout());
		setTitle("ɾ���û�");
		setBounds(new SetCenterSize().getCenterBounds(500,450));	
		setModal(true);	
		/*����ͼ��*/
		Image icon = Toolkit.getDefaultToolkit().getImage("images\\icon\\icon3.png");
		setIconImage(icon);
		
		//������
		
		/*�����м�����*/
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
		
		
		//���ð�ť��ʼ״̬
		button_delete.setEnabled(false);
		
		
		//Ϊ��ť��Ӽ�����
		button_check.addActionListener(this);
		button_delete.addActionListener(this);
		button_cancel.addActionListener(this);
		
		
		//���ڽ�β����
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
	
	
	
	
	
	
	
	//��д�ӿں���
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==button_check) {
			String temp_id=this.text_id.getText().trim();
			/*�������ݿ�ֲ�����*/
			Connection con=JDBC.getConnection();
			try {
				Statement sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet rs=sql.executeQuery("select * from administrators where name="+"'"+temp_id+"'");
				ResultSetMetaData metaData;
				//�������
				System.out.println(123);
				
				rs.next();
				//�������
				area_information.append("��ѯ�û���"+temp_id+"\n"+"�����"+rs.getString(1).equals(temp_id)+"\n");
				
					//�������
					System.out.println(temp_id);
					/*��������*/
					String all_Infomation[][]=null;
					String title_Head[];
					int row=0;
					int col=0;
					/*�������*/
					rs.beforeFirst();
						metaData=rs.getMetaData();
						col=metaData.getColumnCount();
						/*����ֶ�����*/
						title_Head=new String[col];
						for(int i=0;i<col;i++) {
							title_Head[i]=metaData.getColumnName(i+1);
						}
						
						/*�������*/
						rs.last();
						row=rs.getRow();
						rs.beforeFirst();
						
						//��������all_Information
						all_Infomation=new String [row][col];
						
						//��������ѭ���ļ�����
						for(int i=0;rs.next();i++)
							for(int j=0;j<col;j++)
								all_Infomation[i][j]=rs.getString(j+1);
						
						
						
						//�������ʾ�ڽ�����

						/*�ֶ�*/
						for(int i=0;i<col;i++) 
							area_information.append(title_Head[i]+"\t");
						area_information.append("\n");
						
						/*����*/
						for(int i=0;i<all_Infomation.length;i++) {
							for(int j=0;j<all_Infomation[0].length;j++) 
								area_information.append(all_Infomation[i][j]+"\t");
							area_information.append("\n");
						}
						
						//�������ݸ�ɾ����ť
						id_deleted=temp_id;
						//��ɾ����ťȨ��
						this.button_delete.setEnabled(true);
			}//try�����
			
			catch (SQLException e1) {
				System.out.println("sql���ִ���~��");
				area_information.append("��ѯʧ�ܣ�������Ϣ�Ƿ���ȷ~��\n");
			}
			
		}
		
		//ɾ����ť��
		if(e.getSource()==button_delete) {
			/*����ֲ�����*/
			Connection con=JDBC.getConnection();
			Statement sql;
			try {
				sql = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				int state=JOptionPane.showConfirmDialog(this, "��ȷ��Ҫɾ���û�"+id_deleted+"��");
				if(state==JOptionPane.YES_OPTION) {
				int i=sql.executeUpdate("delete from administrators where name="+"'"+id_deleted+"'");
				if(i!=0) {
					JOptionPane.showMessageDialog(this, "ɾ���ɹ�~��");
					this.button_delete.setEnabled(false);
				}
				}
				else {
					JOptionPane.showMessageDialog(this, "ɾ��ʧ��~��");
				}
			} 
			catch (SQLException e1) {
				e1.printStackTrace();
				
			}
					
		}
		
		//ȡ����ť��
		if(e.getSource()==button_cancel) {
			dispose();
		}
}
		
	
	
	//���Ժ���
	public static void main(String[] args) {
		
		new UserDelete(null);
	}
}
