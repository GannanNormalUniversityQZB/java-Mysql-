package userUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;

import Tools.AuthorityPassword;
import Tools.JDBC;
import Tools.SetCenterSize;
import Tools.textEmpty;
import UI.CompanyManage;



public class UserModify extends JDialog implements ActionListener{
	//�����Ա����
	/*��ǩ��*/
	JLabel label_name=new JLabel("�û�����");
	JLabel label_new_password=new JLabel("�����룺");
	JLabel label_id=new JLabel("��    �ݣ�");
	public JLabel companyLogo=new JLabel(new ImageIcon("images\\Logo\\companyLogo2.png"));
	
	
	/*�����б���*/
	String array_id[]= {"----------------��ѡ�����----------------","����Ա","Ա��","������Ա"};
	JComboBox<String> comboBox_id=new JComboBox<>(array_id);
	
	/*��ť��*/
	JButton button_check=new JButton("��ѯ");
	JButton button_modify=new JButton("�޸�");
	JButton button_cancel=new JButton("ȡ��");
	
	
	/*�ı�����*/
	JTextField text_name=new JTextField(12);
	JTextField text_new_password=new JTextField(12);
	/*����id��¼��*/
	String temp_name;
	
	/*���Ȩ������*/
	String password=AuthorityPassword.authority;
	
	//���幹�캯��
	public UserModify(CompanyManage view) {
		super(view);
		//���ô���
		setLayout(new FlowLayout());
		setTitle("�޸��û�");
		setBounds(new SetCenterSize().getCenterBounds(500,400));	
		setModal(true);	
		/*����ͼ��*/
		Image icon = Toolkit.getDefaultToolkit().getImage("images\\icon\\icon3.png");
		setIconImage(icon);
		
		/*�����м�����*/
		
		/*�������������*/
		Box Vbox=Box.createVerticalBox();
		
		/*�����ڲ�����*/
		Box name_box=Box.createHorizontalBox();
		Box new_password_box=Box.createHorizontalBox();
		Box id_box=Box.createHorizontalBox();
		Box button_box=Box.createHorizontalBox();
		JPanel panel_button=new JPanel();
		
		/*��װ*/
		name_box.add(label_name);
		name_box.add(text_name);

		
		new_password_box.add(label_new_password);
		new_password_box.add(text_new_password);
		
	
		
		id_box.add(label_id);
		id_box.add(comboBox_id);
		
		Vbox.add(Box.createVerticalStrut(15));
		Vbox.add(name_box);
		Vbox.add(Box.createVerticalStrut(30));
		Vbox.add(new_password_box);
		Vbox.add(Box.createVerticalStrut(30));

		Vbox.add(id_box);
		Vbox.add(Box.createVerticalStrut(30));
		
		button_box.add(button_check);
		button_box.add(Box.createHorizontalStrut(30));
		button_box.add(button_modify);
		button_box.add(Box.createHorizontalStrut(30));
		button_box.add(button_cancel);
		
		Vbox.add(button_box);
		Vbox.add(Box.createVerticalGlue());
		
		add(companyLogo);
		add(Vbox);
		
		
		//���������ʼ״̬
		
		text_new_password.setEditable(false);
		comboBox_id.setEnabled(false);
		button_modify.setEnabled(false);
		
		
		//δ��ť��Ӽ�����
		button_check.addActionListener(this);
		button_modify.addActionListener(this);
		button_cancel.addActionListener(this);
		
		
		
		//���ڽ�β����
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	
	}
	
	


	//��д�ӿں���
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==button_check) {
			temp_name=this.text_name.getText().trim();
			/*�������ݿ�ֲ�����*/
			Connection con=JDBC.getConnection();
			try {
				Statement sql=con.createStatement();
				ResultSet rs=sql.executeQuery("select * from administrators where name="+"'"+temp_name+"'");
				
				//�������
				System.out.println(123);
				
				rs.next();
				if(rs.getString(1).equals(temp_name)) {
					//���ñ༭Ȩ��
					text_new_password.setEditable(true);
					comboBox_id.setEnabled(true);
					button_modify.setEnabled(true);
					text_name.setEditable(false);
					//��ʾ�û�
					JOptionPane.showMessageDialog(this,"��ѯ���û���"+temp_name+"�ɹ�~��\n�����ڿ����޸���Ϣ~��");
				}
			} 
			catch (SQLException e1) {
				System.out.println("sql���ִ���~��");
				JOptionPane.showMessageDialog(this,"δ��ѯ���û���"+temp_name);
				//���ñ༭Ȩ��
				text_new_password.setEditable(false);
				comboBox_id.setEnabled(false);
				button_modify.setEnabled(false);
			}
			
		}
		
		//button_modify
		if(e.getSource()==button_modify) {
			/*����ֲ�������*/
			String temp_name=this.text_name.getText().trim();
			String temp_new_password=this.text_new_password.getText().trim();
			String temp_id=(String) this.comboBox_id.getSelectedItem();
			if(! textEmpty.textEmpty(temp_name,temp_new_password) && !temp_id.equals("----------------��ѡ�����----------------")) {
				String comfirm_password=JOptionPane.showInputDialog(this, "���������Ȩ������\n����������ǣ�����ϵ����������Ա~��");
				//ֻ�����Ȩ��������ȷ������ע��
				if(comfirm_password.equals(password)) {
				/*�������ݿ�ֲ�����*/
				Connection con=JDBC.getConnection();
				try {
					PreparedStatement pre_sql=con.prepareStatement("update	administrators set name=? , password=? , identity=? where name=?");
					pre_sql.setString(1,temp_name);
				    pre_sql.setString(2,temp_new_password);
					pre_sql.setString(3,temp_id);
					pre_sql.setString(4,temp_name);
					int i=pre_sql.executeUpdate();
					if(i!=0){
						System.out.println(i);
						JOptionPane.showMessageDialog(this, "�޸ĳɹ�~��");
					}	
				} 
				catch (SQLException e1) {System.out.println(e1);}
				}
			}
				else {
						JOptionPane.showMessageDialog(this, "����д����ȷ����Ϣ", "����",JOptionPane.WARNING_MESSAGE);
					}
				}
		
		//button_cancel��
		if(e.getSource()==button_cancel) {
			dispose();
		}
	}
	


	//���Ժ���
	public static void main(String[] args) {
		new UserModify(null);

	}

}
