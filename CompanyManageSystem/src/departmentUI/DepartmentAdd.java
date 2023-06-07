package departmentUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import javax.swing.*;

import Tools.JDBC;
import Tools.SetCenterSize;
import positionUI.PositionAdd;

import java.sql.*;
public class DepartmentAdd extends JDialog implements ActionListener{
	//�����Ա����
	/*��ǩ��*/
	JLabel label_new_depart=new JLabel("�½��������ƣ�");
	JLabel label_create_position=new JLabel("�����½�ְλ��");
	JLabel label_original_depart=new JLabel("������ԭ���ţ�");
	JLabel label_name=new JLabel("��������������");
	JLabel label_id=new JLabel("�������乤�ţ�");
	JLabel label_original_position=new JLabel("������ԭְλ��");
	JLabel label_new_position=new JLabel("��������ְλ��");
	
	/*���������Ϣ����*/
	String array_original_depart[];
	String array_name[];
	String array_new_position[];
	
	/*�ı�����*/
	JTextField text_new_depart=new JTextField(15);
	JTextField text_original_position=new JTextField(15);
	JTextField text_id=new JTextField(15);
	
	/*�����б���*/
	JComboBox<String> comboBox_original_depart=new JComboBox<>();
	JComboBox<String> comboBox_name=new JComboBox<>();
	JComboBox<String> comboBox_new_position=new JComboBox<>();
	
	/*��ť��*/
	JButton button_submit=new JButton("�ύ");
	JButton button_create_position=new JButton("��������˴�����ְλ");
	JButton button_cancel=new JButton("ȡ��");
	
	/*logo��*/
	public JLabel companyLogo=new JLabel(new ImageIcon("images\\Logo\\companyLogo2.png"));

	
	
	//���幹�캯��
	public DepartmentAdd(JFrame view) {
		super(view);
		//���ô���
		setLayout(new FlowLayout());
		setTitle("��Ӳ���");
		setBounds(new SetCenterSize().getCenterBounds(480,580));	
		setModal(true);	
		/*����ͼ��*/
		Image icon = Toolkit.getDefaultToolkit().getImage("images\\icon\\icon3.png");
		setIconImage(icon);
		
		//������
		/*�����м�����*/
		Box Vbox=Box.createVerticalBox();
		Box box_new_depart=Box.createHorizontalBox();
		Box box_original_depart=Box.createHorizontalBox();
		Box box_name=Box.createHorizontalBox();
		Box box_id=Box.createHorizontalBox();
		Box box_original_position=Box.createHorizontalBox();
		Box box_new_position=Box.createHorizontalBox();
		Box box_create_position=Box.createHorizontalBox();
		Box box_button=Box.createHorizontalBox();
		
		
		/*��װ���*/
		box_new_depart.add(label_new_depart);
		box_new_depart.add(text_new_depart);
		
		box_create_position.add(label_create_position);
		box_create_position.add(button_create_position);
		box_create_position.add(Box.createHorizontalGlue());
		
		box_original_depart.add(label_original_depart);
		box_original_depart.add(comboBox_original_depart);
		
		box_name.add(label_name);
		box_name.add(comboBox_name);
		
		box_id.add(label_id);
		box_id.add(text_id);
		
		box_original_position.add(label_original_position);
		box_original_position.add(text_original_position);
		
		box_new_position.add(label_new_position);
		box_new_position.add(comboBox_new_position);
		
		box_button.add(button_submit);
		box_button.add(Box.createHorizontalStrut(60));
		box_button.add(button_cancel);
		
		Vbox.add(Box.createGlue());
		Vbox.add(Box.createVerticalStrut(20));
		Vbox.add(box_new_depart);
		Vbox.add(Box.createVerticalStrut(20));
		Vbox.add(box_create_position);
		Vbox.add(Box.createVerticalStrut(20));
		Vbox.add(box_original_depart);
		Vbox.add(Box.createVerticalStrut(20));
		Vbox.add(box_name);
		Vbox.add(Box.createVerticalStrut(20));
		Vbox.add(box_id);
		Vbox.add(Box.createVerticalStrut(20));
		Vbox.add(box_original_position);
		Vbox.add(Box.createVerticalStrut(20));
		Vbox.add(box_new_position);
		Vbox.add(Box.createVerticalStrut(30));
		Vbox.add(box_button);
		
		
		add(companyLogo);
		add(Vbox);
		
		//���������ʼ״̬
		button_submit.setEnabled(false);
		text_original_position.setEditable(false);
		text_id.setEditable(false);
		
		
		//Ϊ�����Ӽ�����
		button_create_position.addActionListener(this);
		button_cancel.addActionListener(this);
		button_submit.addActionListener(this);
		comboBox_original_depart.addActionListener(this);
		comboBox_name.addActionListener(this);
		comboBox_new_position.addActionListener(this);
		//���ڽ�β����
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
	}
	
	
	//��д�ӿں���
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==button_create_position) {
			/*����²�������*/
			String new_depart=this.text_new_depart.getText().trim();
			//�����Ĳ�����ְλ��PositionAdd��ɣ�ͬ��department��position��
			//DepartmentAdd��Ҫ���ڸ���ְԱ��Ϣ��ͬ��employee��
			new PositionAdd((JDialog)this,new_depart);
		
			/*�������ݿ�ֲ�����*/
			Connection con=JDBC.getConnection();
			Statement sql;
			ResultSet rs;
			try {
				 sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				 //������в�����Ϣ
				 rs=sql.executeQuery("select * from department");
				 rs.last();
				 int row=rs.getRow();
				 rs.beforeFirst();
				 array_original_depart=new String[row];
				 for(int i=0;rs.next();i++)
					 array_original_depart[i]=rs.getString(1);
				 /*ˢ�������б�*/
				 comboBox_original_depart.removeAllItems();
				 for(String a:array_original_depart)
					 comboBox_original_depart.addItem(a);
				 
				//����²���ְλ��Ϣ
				 rs=sql.executeQuery("select ְλ from position where ��������='"+new_depart+"'");
				 rs.last();
				 int row_2=rs.getRow();
				 rs.beforeFirst();
				 /*��������*/
				 array_new_position=new String[row_2];
				 for(int i=0;rs.next();i++)
					 array_new_position[i]=rs.getString(1);
				 /*ˢ����ְλ�б�*/
				 comboBox_new_position.removeAllItems();
				 for(String a:array_new_position)
					 comboBox_new_position.addItem(a);
				 
			} catch (SQLException e1) {	
				e1.printStackTrace();
			}
			
			//���ð�ť״̬
			button_submit.setEnabled(true);
		}
		
		//comboBox_original_depart��
		if(e.getSource()==comboBox_original_depart) {
			/*�������ݿ�ֲ�����*/
			Connection con=JDBC.getConnection();
			Statement sql;
			ResultSet rs;
			 //��ò�����������
			String temp_depart=(String)comboBox_original_depart.getSelectedItem();
			 try {
				sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rs=sql.executeQuery("select ���� from employee where ����='"+temp_depart+"'");
				rs.last();
				int row=rs.getRow();
				rs.beforeFirst();
				/*��������*/
				array_name=new String[row];
				for(int i=0;rs.next();i++) {
					array_name[i]=rs.getString(1);
				}
				 /*ˢ�������б�*/
				comboBox_name.removeAllItems();
				 for(String a:array_name)
					 comboBox_name.addItem(a);
			} 
			 catch (SQLException e1) {
				e1.printStackTrace();
			}
		}//e.getSource()==comboBox_original_depart
		
		
		//e.getSource()==comboBox_name����ˢ���ı���
		if(e.getSource()==comboBox_name) {
			String temp_name=(String)this.comboBox_name.getSelectedItem();
			/*�������ݿ�ֲ�����*/
			Connection con=JDBC.getConnection();
			Statement sql;
			ResultSet rs;
			try {
				sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				/*��ø����˹���*/
				rs=sql.executeQuery("select ���� from employee where ����='"+temp_name+"'");
				rs.next();
				this.text_id.setText(rs.getString(1));
				/*���ԭְλ*/
				rs=sql.executeQuery("select ְλ from employee where ����='"+temp_name+"'");
				rs.next();
				this.text_original_position.setText(rs.getString(1));
			} 
			catch (SQLException e1) {
				e1.printStackTrace();
			}	
			
		}//e.getSource()==comboBox_name
		
		//button_submit�������ڸ���Ա����Ϣ
		if(e.getSource()==button_submit) {
			String temp_new_depart=this.text_new_depart.getText().trim();
			String temp_new_position=(String)this.comboBox_new_position.getSelectedItem();
			String temp_id=this.text_id.getText().trim();
			if(temp_new_depart.equals("")) {
				JOptionPane.showMessageDialog(this, "�²������Ʋ�����Ϊ��", "����",JOptionPane.WARNING_MESSAGE);
			}	
			else {
				/*�������ݿ�ֲ�����*/
				Connection con=JDBC.getConnection();
				Statement sql;
				ResultSet rs;
				try {
					sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
					int i=sql.executeUpdate("update employee set ����='"+temp_new_depart+"',ְλ='"+temp_new_position+"' where ����='"+temp_id+"'");
					if(i!=0) {
						JOptionPane.showMessageDialog(this, "�²��ţ�"+temp_new_depart+"�����ɹ�~��");
						//�����ύ���״̬
						button_submit.setEnabled(false);
						repaint();
					}
					else
						JOptionPane.showMessageDialog(this, "�²��ţ�"+temp_new_depart+"����ʧ��~��");
				} 
				catch (SQLException e1) {
					JOptionPane.showMessageDialog(this, "�²��ţ�"+temp_new_depart+"����ʧ��~��");
					e1.printStackTrace();
				}
			}
				
		}//e.getSource()==button_submit
		
		if(e.getSource()==button_cancel)
			dispose();
	}
	
	
	
	//���Ժ���
	public static void main(String[] args) {
		new DepartmentAdd(null);

	}

}//class������
