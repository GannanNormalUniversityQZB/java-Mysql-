package employeeUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import Tools.JDBC;
import Tools.SetCenterSize;
import Tools.textEmpty;
import UI.CompanyManage;

import java.awt.*;
import java.sql.*;
public class EmployeeModify extends JDialog implements ActionListener{
	//�����Ա����
		/*��ǩ��*/
		JLabel label_department=new JLabel("���ţ�");
		JLabel label_id=new JLabel("���ţ�");
		JLabel label_name=new JLabel("������");
		JLabel label_gender=new JLabel("�Ա�");
		JLabel label_eduBack=new JLabel("ѧ����");
		JLabel label_position=new JLabel("ְλ��");
		JLabel label_treatment=new JLabel("������");
		JLabel label_date=new JLabel("��ְ��");
		
		
		/*logo��*/
		public JLabel companyLogo=new JLabel(new ImageIcon("images\\Logo\\companyLogo2.png"));
		
		/*�ı����� */
		JTextField text_id=new JTextField(20);
		JTextField text_name=new JTextField(20);
		
		/*�����б���*/
		
		/*ѧ��*/
		String array_eduBack[]= {"Сѧ","����","����","ר��","����","�о���"};
		JComboBox<String> comboBox_eduBack=new JComboBox<>(array_eduBack);
		
		/*����*/
		String array_department[];
		JComboBox<String> comboBox_department=new JComboBox<>();

		/*ְλ*/
		String array_position[];
		JComboBox<String> comboBox_position=new JComboBox<>();
		
		/*����*/
		String array_treatment[]= {"3000","4000","5000","6000","7000","8000","9000","10000+"};
		JComboBox<String> comboBox_treatment=new JComboBox<>(array_treatment);
		
		//ʱ����
		/*��*/
		String array_year[]= {"1999��","2000��","2001��","2002��","2003��","2004��","2005��","2006��","2007��","2008��","2009��",
								"2010��","2011��","2012��","2013��","2014��","2015��","2016��","2017��","2018��","2019��",
								"2020��","2021��","2022��","2023��","2024��","2025��","2026��","2027��","2028��","2029��","2030��"};
		
		JComboBox<String> comboBox_year=new JComboBox<>(array_year);
		
		/*��*/
		String array_month[]= {"һ��","����","����","����","����","����","����","����","����","ʮ��","ʮһ��","ʮ����"};
		JComboBox<String> comboBox_month=new JComboBox<>(array_month);
		
		//��ť��
		/*��ѡ��ť*/
		JRadioButton radioButton_male=new JRadioButton("��");
		JRadioButton radioButton_female=new JRadioButton("Ů");
		ButtonGroup button_group;
		
		/*��ť*/
		JButton button_modify=new JButton("�޸�");
		JButton button_cancel=new JButton("ȡ��");
		JButton button_query=new JButton("��ѯ");

		//���幹�캯��
		public EmployeeModify(CompanyManage view,String transfered_id) {
			super(view);
			//���ô���
			setLayout(new FlowLayout());
			setTitle("�޸�Ա����Ϣ");
			setBounds(new SetCenterSize().getCenterBounds(500,580));	
			setModal(true);	
			/*����ͼ��*/
			Image icon = Toolkit.getDefaultToolkit().getImage("images\\icon\\icon3.png");
			setIconImage(icon);
			
			//��ʼ������
			//���ò���
			Connection con=JDBC.getConnection();
			Statement sql;
			try {
				sql = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet rs=sql.executeQuery("select * from department");
				rs.last();
				int row=rs.getRow();
				rs.beforeFirst();
				/*��������*/
				array_department=new String[row];
				for(int i=0;rs.next();i++)
					array_department[i]=rs.getString(1);
				/*ˢ�²����б�*/
				comboBox_department.removeAllItems();
				for(String a:array_department)
					comboBox_department.addItem(a);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		
			
			
			
			//��������
			/*������Ƕ����*/
			Box Vbox=Box.createVerticalBox();
			/*�����ڲ�����*/
			Box box_name=Box.createHorizontalBox();
			Box box_id=Box.createHorizontalBox();
			Box box_department=Box.createHorizontalBox();
			Box box_gender=Box.createHorizontalBox();
			Box box_eduBack=Box.createHorizontalBox();
			Box box_position=Box.createHorizontalBox();
			Box box_button=Box.createHorizontalBox();
			Box box_treatment=Box.createHorizontalBox();
			Box box_date=Box.createHorizontalBox();
			
			box_name.add(label_name);
			box_name.add(text_name);
			
			
			
			box_id.add(label_id);
			box_id.add(text_id);
			
			
			box_department.add(label_department);
			box_department.add(comboBox_department);

			button_group=new ButtonGroup();
			button_group.add(radioButton_male);
			button_group.add(radioButton_female);
			box_gender.add(label_gender);
			box_gender.add(Box.createHorizontalStrut(30));
			box_gender.add(radioButton_male);
			box_gender.add(Box.createHorizontalStrut(30));
			box_gender.add(radioButton_female);
			box_gender.add(Box.createHorizontalGlue());
			
			box_eduBack.add(label_eduBack);
			box_eduBack.add(comboBox_eduBack);
			
			box_position.add(label_position);
			box_position.add(comboBox_position);
			
			box_treatment.add(label_treatment);
			box_treatment.add(comboBox_treatment);
			
			box_button.add(button_query);
			box_button.add(Box.createHorizontalStrut(20));
			box_button.add(button_modify);
			box_button.add(Box.createHorizontalStrut(20));
			box_button.add(button_cancel);
			
			box_date.add(label_date);
			//box_date.add(Box.createHorizontalStrut(15));
			box_date.add(comboBox_year);
			box_date.add(Box.createHorizontalStrut(15));
			box_date.add(comboBox_month);
			
			
			Vbox.add(Box.createVerticalStrut(10));
			Vbox.add(box_name);
			Vbox.add(Box.createVerticalStrut(15));
			Vbox.add(box_department);
			
			Vbox.add(Box.createVerticalStrut(15));
			Vbox.add(box_id);
			Vbox.add(Box.createVerticalStrut(15));
			Vbox.add(box_gender);
			Vbox.add(Box.createVerticalStrut(15));
			Vbox.add(box_eduBack);
			Vbox.add(Box.createVerticalStrut(20));
			Vbox.add(box_position);
			Vbox.add(Box.createVerticalStrut(20));
			Vbox.add(box_treatment);
			Vbox.add(Box.createVerticalStrut(20));
			Vbox.add(box_date);
			Vbox.add(Box.createVerticalStrut(20));
			Vbox.add(box_button);
			
			
			add(companyLogo);
			add(Vbox);
			
			
			//���������ʼ״̬
			text_name.setEditable(false);
			comboBox_department.setEnabled(false);
			radioButton_female.setEnabled(false);
			radioButton_male.setEnabled(false);
			comboBox_eduBack.setEnabled(false);
			comboBox_position.setEnabled(false);
			comboBox_treatment.setEnabled(false);
			comboBox_year.setEnabled(false);
			comboBox_month.setEnabled(false);
			button_modify.setEnabled(false);
			
			text_id.setText(transfered_id);
			
			//Ϊ�����Ӽ�����
			comboBox_department.addActionListener(this);
			button_modify.addActionListener(this);
			button_cancel.addActionListener(this);
			button_query.addActionListener(this);
			
			
			
			//���ڽ�β����
			setVisible(true);
			setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		}
		
		
		
		
		//��д�̳к���
		@Override
		public void actionPerformed(ActionEvent e) {
			//���ݲ��Ÿ���ְλ�����б�
			if(e.getSource()==comboBox_department) {
				/*�������ݿ�ֲ�����*/
				Connection con=JDBC.getConnection();
				Statement sql;
				try {
					sql = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
					ResultSet rs;
				
				//ˢ�²��Ŷ�Ӧ��ְλ�б�
				String temp_depart=(String)this.comboBox_department.getSelectedItem();
				rs=sql.executeQuery("select ְλ from position where ��������='"+temp_depart+"'");
				rs.last();
				int row_2=rs.getRow();
				rs.beforeFirst();
				/*��������*/
				array_position=new String[row_2];
				for(int j=0;rs.next();j++)
					array_position[j]=rs.getString(1);
				/*ˢ�²����б�*/
				comboBox_position.removeAllItems();
				for(String a:array_position)
					comboBox_position.addItem(a);
				
				}//try����
				catch (SQLException e1) {
					e1.printStackTrace();
				}
						
			}
			
			//button_query��
			if(e.getSource()==button_query) {
				/*��ʱ������*/				
				String temp_id=this.text_id.getText().trim();
				/*���ݿ���*/
				if(temp_id.equals("")) {
					JOptionPane.showMessageDialog(this, "�����빤�Ž��в�ѯ��������޸���Ϣ~��");
				}
				else {
				Connection con=JDBC.getConnection();
				try {
					Statement sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
					ResultSet rs=sql.executeQuery("select * from employee where ����="+"'"+temp_id+"'");
					rs.next();
					//Ϊ��������Ϣ
					this.text_name.setText(rs.getString(1));
					this.text_id.setText(rs.getString(2));
	
					//��ò���
					String temp_depart=rs.getString(3);
					this.comboBox_department.setSelectedItem(temp_depart);
					System.out.println(temp_depart);
					
					
					String temp_gender=rs.getString(4);
					if(temp_gender.equals("��"))
						radioButton_male.setSelected(true);
					else
						radioButton_female.setSelected(true);
					this.comboBox_eduBack.setSelectedItem(rs.getString(5));	
					
					//���ְλ
					String temp_position=rs.getString(6);
					System.out.println(temp_position);
					
					this.comboBox_treatment.setSelectedItem(rs.getString(7));
					this.comboBox_year.setSelectedItem(rs.getString(8));
					this.comboBox_month.setSelectedItem(rs.getString(9));
					
					
				
					
					//���ݲ��ŵ�����ְλ
					rs=sql.executeQuery("select ְλ from position where ��������='"+temp_depart+"'");
					rs.last();
					int row_2=rs.getRow();
					rs.beforeFirst();
					/*��������*/
					array_position=new String[row_2];
					for(int j=0;rs.next();j++)
						array_position[j]=rs.getString(1);
					/*ˢ�²����б�*/
					comboBox_position.removeAllItems();
					for(String a:array_position)
						comboBox_position.addItem(a);
					//����ѡ�еĲ��ţ���ʾ��Ӧ��ְλ
					String selected_depart=(String)this.comboBox_department.getSelectedItem();
					this.comboBox_position.setSelectedItem(temp_position);
					
					//�������״̬
					text_name.setEditable(true);
					comboBox_department.setEnabled(true);
					radioButton_female.setEnabled(true);
					radioButton_male.setEnabled(true);
					comboBox_eduBack.setEnabled(true);
					comboBox_position.setEnabled(true);
					comboBox_treatment.setEnabled(true);
					comboBox_year.setEnabled(true);
					comboBox_month.setEnabled(true);
					button_modify.setEnabled(true);
					text_id.setEditable(false);
					//��ʾ
					JOptionPane.showMessageDialog(this, "��ѯ�ɹ�,�����ڿ����޸��û���"+temp_id+"����Ϣ~��");
					
				} 
				catch (SQLException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(this, "��Ϣ��ѯʧ��,����������Ĺ����Ƿ�����~��");
				}
				}
				
			}
			
			
			//button_cancel��
			if(e.getSource()==button_cancel) {
				dispose();
			}
			
			//button_modify��
			if(e.getSource()==button_modify) {
				/*��ʱ������*/
				String temp_name=this.text_name.getText().trim();
				String temp_id=this.text_id.getText().trim();
				String temp_depart=(String)this.comboBox_department.getSelectedItem();
				String temp_gender=(radioButton_male.isSelected()?"��":"Ů");
				String temp_eduBack=(String)this.comboBox_eduBack.getSelectedItem();
				String temp_position=(String)this.comboBox_position.getSelectedItem();
				String temp_treatment=(String)this.comboBox_treatment.getSelectedItem();
				String temp_year=(String)this.comboBox_year.getSelectedItem();
				String temp_month=(String)this.comboBox_month.getSelectedItem();
				
				/*���ݿ���*/
				if(!textEmpty.textEmpty(temp_name,temp_id,temp_depart,temp_gender,temp_eduBack,temp_position,temp_treatment,temp_year,temp_month)) {
				Connection con=JDBC.getConnection();
				try {
					PreparedStatement presql=con.prepareStatement("update  employee set ����=?,����=?,����=?,�Ա�=?,ѧ��=?,ְλ=?,����=?,��ְ���=?,��ְ�·�=? where ����=?");
					presql.setString(1, temp_name);
					presql.setString(2, temp_id);
					presql.setString(3, temp_depart);
					presql.setString(4, temp_gender);
					presql.setString(5, temp_eduBack);
					presql.setString(6, temp_position);
					presql.setString(7, temp_treatment);
					presql.setString(8, temp_year);
					presql.setString(9, temp_month);
					presql.setString(10, temp_id);
					int i=presql.executeUpdate();
					if(i!=0) {
						JOptionPane.showMessageDialog(this, "Ա��:"+temp_name+"��Ϣ�޸ĳɹ�~��");
					}
					//���������ʼ״̬
					text_name.setEditable(false);
					comboBox_department.setEnabled(false);
					radioButton_female.setEnabled(false);
					radioButton_male.setEnabled(false);
					comboBox_eduBack.setEnabled(false);
					comboBox_position.setEnabled(false);
					comboBox_treatment.setEnabled(false);
					comboBox_year.setEnabled(false);
					comboBox_month.setEnabled(false);
					button_modify.setEnabled(false);
					text_id.setEditable(true);
				} 
				catch (SQLException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(this, "��Ϣ�޸�ʧ��~��");
				}
				}//if�пս���
				else {
					JOptionPane.showMessageDialog(this, "����д������Ϣ~��");
				}
			}
	}

		//���Ժ���
		public static void main(String[] args) {
			
				new EmployeeModify(null,null);
		}

}
