package userUI;
import javax.swing.*;

import Tools.AuthorityPassword;
import Tools.JDBC;
import Tools.SetCenterSize;
import UI.*;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
public class UserAdd extends JDialog implements ActionListener{
	//�����Ա����
	/*��ǩ��*/
	JLabel label_name=new JLabel("�û�����");
	JLabel label_password=new JLabel("��    �룺");
	JLabel label_password_2=new JLabel("ȷ    �ϣ�");
	JLabel label_id=new JLabel("��    �ݣ�");
	public JLabel companyLogo=new JLabel(new ImageIcon("images\\Logo\\companyLogo2.png"));
	
	/*�����б���*/
	String array_id[]= {"----------------��ѡ�����----------------","����Ա","Ա��","������Ա"};
	JComboBox<String> comboBox_id=new JComboBox<>(array_id);
	
	/*��ť��*/
	JButton button_add=new JButton("���");
	JButton button_reset=new JButton("����");
	JButton button_cancel=new JButton("ȡ��");
	
	/*�ı�����*/
	JTextField text_name=new JTextField(12);
	JTextField text_password=new JTextField(12);
	JTextField text_password_2=new JTextField(12);
	
	/*���Ȩ������*/
	String password=AuthorityPassword.authority;
	
	//���幹�캯��
	public UserAdd(CompanyManage view) {
		super(view);
		//���ô���
		setLayout(new FlowLayout());
		setTitle("����û�");
		setBounds(new SetCenterSize().getCenterBounds(500,450));	
		setModal(true);
		/*����ͼ��*/
		Image icon = Toolkit.getDefaultToolkit().getImage("images\\icon\\icon3.png");
		setIconImage(icon);
		
		//��������
		/*�����м�����*/
		
		/*�������������*/
		Box Vbox=Box.createVerticalBox();
		
		/*�����ڲ�����*/
		Box name_box=Box.createHorizontalBox();
		Box password_box=Box.createHorizontalBox();
		Box password_2_box=Box.createHorizontalBox();
		Box id_box=Box.createHorizontalBox();
		Box button_box=Box.createHorizontalBox();
		JPanel panel_button=new JPanel();
		
		/*��װ*/
		name_box.add(label_name);
		name_box.add(text_name);
		
		password_box.add(label_password);
		password_box.add(text_password);
		
		password_2_box.add(label_password_2);
		password_2_box.add(text_password_2);
		
		id_box.add(label_id);
		id_box.add(comboBox_id);
		
		Vbox.add(Box.createVerticalStrut(15));
		Vbox.add(name_box);
		Vbox.add(Box.createVerticalStrut(30));
		Vbox.add(password_box);
		Vbox.add(Box.createVerticalStrut(30));
		Vbox.add(password_2_box);
		Vbox.add(Box.createVerticalStrut(30));
		Vbox.add(id_box);
		Vbox.add(Box.createVerticalStrut(30));
		
		button_box.add(button_add);
		button_box.add(Box.createHorizontalStrut(80));
		button_box.add(button_cancel);
		
		Vbox.add(button_box);
		Vbox.add(Box.createVerticalGlue());
		
		add(companyLogo);
		add(Vbox);
		
		//Ϊ��ť��Ӽ�����
		button_add.addActionListener(this);
		button_cancel.addActionListener(this);
		
		//���ڽ�β����
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
	
	
	
	//��д�ӿں���
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==button_add) {
			/*�����������ľֲ�����*/
			String temp_name=this.text_name.getText().trim();
			String temp_password=this.text_password.getText().trim();
			String temp_password_2=this.text_password_2.getText().trim();
			String temp_id=(String)this.comboBox_id.getSelectedItem();
			if(!textEmpty(temp_name, temp_password, temp_password_2, temp_id) && !temp_id.equals("----------------��ѡ�����----------------")) {
				//ȷ�����������������ͬ
				if(temp_password.equals(temp_password_2)) {
				String comfirm_password=JOptionPane.showInputDialog(this, "���������Ȩ������\n����������ǣ�����ϵ����������Ա~��");
				//ֻ�����Ȩ��������ȷ������ע��
				if(comfirm_password.equals(password)) {
				//�������
				System.out.println(temp_name+temp_password+temp_password_2+temp_id);
				/*�������ݿ�ֲ�����*/
				Connection con=JDBC.getConnection();
				try {
					PreparedStatement pre_sql=con.prepareStatement("insert into administrators values(?,?,?)");
					
					pre_sql.setString(1,temp_name);
					pre_sql.setString(2,temp_password);
					pre_sql.setString(3,temp_id);
					//�������
					System.out.println(temp_name+temp_password+temp_password_2+temp_id);
					int i=pre_sql.executeUpdate();
					if(i!=0){
						System.out.println(i);
						JOptionPane.showMessageDialog(this, "��ӳɹ�~��");
					}
					
					
				} 
				catch (SQLException e1) {JOptionPane.showMessageDialog(this, "����д����ȷ����Ϣ", "����",JOptionPane.WARNING_MESSAGE);}
				}//comfirm_password.equals(password)����
				else
					JOptionPane.showMessageDialog(this, "���벻ƥ��~��", "����", JOptionPane.WARNING_MESSAGE);
			}//temp_password.equals(temp_password_2)����
			else {
				JOptionPane.showMessageDialog(this, "������������벻һ��~��", "����",JOptionPane.WARNING_MESSAGE);
			}
			}//!textEmpty(temp_name, temp_password, temp_password_2, temp_id)����
		else {
				JOptionPane.showMessageDialog(this, "����д����ȷ����Ϣ", "����",JOptionPane.WARNING_MESSAGE);
			}
		}//button_add����
		
		//button_cancel��
		if(e.getSource()==button_cancel) {
			dispose();
		}
	}

	//�����ı����пպ���
		 boolean textEmpty(String a,String b,String c,String d) {
			boolean isEmpty=false;
			if(a.equals("") || b.equals("") || c.equals("") || d.equals("") )
				isEmpty=true;
			return isEmpty;
		}


	//���Ժ���
	public static void main(String[] args) {
		new UserAdd(null);
	}

}
