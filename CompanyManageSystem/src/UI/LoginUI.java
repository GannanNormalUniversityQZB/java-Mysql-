package UI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import HelpUI.CopyrightUI;
import HelpUI.TechHelp;
import Tools.*;
import userUI.UserAdd;
import userUI.UserModify;

import java.sql.*;
public class LoginUI extends JFrame implements ActionListener{
	//�����Ա����
	/*���ݿ���*/
	Connection con;
	PreparedStatement sql;
	ResultSet rs;
	
	/*�û�����*/
	JLabel user=new JLabel("�û�����");
	JLabel user_icon=new JLabel(new ImageIcon("images\\icon\\�û���.png"));
	JTextField text_user=new JTextField(22);
	
	/*������*/
	JLabel password=new JLabel("��    �룺");
	JLabel password_icon=new JLabel(new ImageIcon("images\\icon\\����.png"));
	JPasswordField text_password=new JPasswordField(22);
	
	/*�����б�*/
	JLabel jUserType=new JLabel("��    �ݣ�");
	JLabel jUserType_icon=new JLabel(new ImageIcon("images\\icon\\�б�.png"));
	String userArray[]= {"----------------��ѡ�����----------------","����Ա","Ա��","������Ա"};
	JComboBox<String> userType=new JComboBox<>(userArray);
	
	/*��ť��*/
	JButton button_Login=new JButton("��¼");
	JButton button_register=new JButton("ע��");
	JButton button_reset=new JButton("��������");
	JButton button_Cancel=new JButton("ȡ��");
	
	/*logo��*/
	public JLabel companyLogo=new JLabel(new ImageIcon("images\\Logo\\companyLogo2.png"));
	
	/*��¼��¼�������*/
	int error=0;
	
	//���幹�캯��
	public LoginUI() {
		//���ô���
		setLayout(new FlowLayout());
		setTitle("��ҵԱ����Ϣ����ϵͳ");
		setBounds(new SetCenterSize().getCenterBounds(480, 400));
		/*����ͼ��*/
		Image icon = Toolkit.getDefaultToolkit().getImage("images\\icon\\icon3.png");
		setIconImage(icon);
		
		/*������Box����*/
		Box centerBox=Box.createVerticalBox();
		
		/*�����û������*/
		JPanel userPanel=new JPanel();
		userPanel.add(user_icon);
		userPanel.add(user);
		userPanel.add(text_user);
		
		/*�����������*/
		JPanel passwordPanel=new JPanel();
		passwordPanel.add(password_icon);
		passwordPanel.add(password);
		passwordPanel.add(text_password);
		
		/*�����û��������*/
		JPanel userTypePanel=new JPanel();
		userTypePanel.add(jUserType_icon);
		userTypePanel.add(jUserType);
		userTypePanel.add(userType);
		
		/*������ť���*/
		Box buttonBox=Box.createHorizontalBox();
		buttonBox.add(button_Login);
		buttonBox.add(Box.createHorizontalStrut(40));
		buttonBox.add(button_register);
		buttonBox.add(Box.createHorizontalStrut(40));
		buttonBox.add(button_reset);
		//buttonBox.add(Box.createHorizontalStrut(30));
		//buttonBox.add(button_Cancel);
		
		//��װ���
		/*�Ű������*/
		centerBox.add(Box.createHorizontalGlue());
		companyLogo.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		centerBox.add(companyLogo);
		
		centerBox.add(Box.createVerticalStrut(20));
		centerBox.add(userPanel);
		centerBox.add(Box.createVerticalStrut(15));
		
		centerBox.add(passwordPanel);
		centerBox.add(Box.createVerticalStrut(15));
		centerBox.add(Box.createHorizontalGlue());
		centerBox.add(userTypePanel);
		centerBox.add(Box.createVerticalStrut(25));
		centerBox.add(Box.createHorizontalGlue());
		centerBox.add(buttonBox);
		
		add(centerBox);
		
		/*Ϊ��ť����¼�*/
		button_Login.addActionListener(this);
		button_register.addActionListener(this);
		button_reset.addActionListener(this);
		
		
		//���ڽ�β����
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	//��������
	@Override
	public void actionPerformed(ActionEvent e) {
		//��¼��ť��
		if(e.getSource()==button_Login) {
			String name=this.text_user.getText();
			char pawChar[]=this.text_password.getPassword();
			String paw=new String(pawChar);
			String type=this.userType.getSelectedItem().toString();
			
			if(name==null ||  paw==null || type=="----------------��ѡ�����----------------") {
				JOptionPane.showMessageDialog(this,"����д��������Ϣ~��");
				text_user.setText(null);
				text_password.setText(null);
				error++;
				if(error==3) {
					JOptionPane.showMessageDialog(this, "��¼��Ϣ�������Σ�ϵͳ�Զ��ر�~��", "����", JOptionPane.WARNING_MESSAGE);
					dispose();
				}
			}
			else {
				//�������ݿ�
				con=JDBC.getConnection();
				//���sql����
				try {
					sql=con.prepareStatement("select * from administrators where name=? and password=? and identity=?");
				} 
				catch (SQLException e1) {}
				//��ѯ
				try {
					//���ò���
					sql.setString(1,name.trim());
					sql.setString(2,paw.trim());
					sql.setString(3,type.trim());
					rs=sql.executeQuery();

				} 
				catch (SQLException e1) {}
				
				//��������
				System.out.println(name+paw+type);
			
				/*����rs���ݼ�*/
				try {
					/*��һ���ǳ��ؼ�����õ�rsָ�����ݼ���һ�е�ǰ�棬����Ҫ��next���α�����*/
					rs.next();
					System.out.println(rs.getString(1)+rs.getString(2)+rs.getString(3));
				} catch (SQLException e2) {}
				
				
				
				//��ò�ѯ����
				try {
					if(name.equals(rs.getString(1)) && paw.equals(rs.getString(2)) && type.equals(rs.getString(3)) ) {
						JOptionPane.showMessageDialog(this,"��ϲ����¼�ɹ�~��");
						new TechHelp();
						new CopyrightUI();
						new CompanyManage();
						setVisible(false);
					}
					else {
						JOptionPane.showMessageDialog(this,"��¼��Ϣ��������������~��");
						error++;
						if(error==3) {
							JOptionPane.showMessageDialog(this, "��¼��Ϣ�������Σ�ϵͳ�Զ��ر�~��", "����", JOptionPane.WARNING_MESSAGE);
							dispose();
						}
							
						text_user.setText(null);
						text_password.setText(null);
					}
						
				} 
				catch (SQLException e1) {
					JOptionPane.showMessageDialog(this,"��¼��Ϣ��������������~��");
					error++;
					if(error==3) {
						JOptionPane.showMessageDialog(this, "��¼��Ϣ�������Σ�ϵͳ�Զ��ر�~��", "����", JOptionPane.WARNING_MESSAGE);
						dispose();
					}
					text_user.setText(null);
					text_password.setText(null);
				}
				
			}//try������
				
		}//if������
		//ȡ����ť��
		
		//ע����
		else if(e.getSource()==button_register) {
			new UserAdd(null);
		}
		
		//����������
		else if(e.getSource()==button_reset) {
			new UserModify(null);
		}
		
	}//��������
	
	
	//���Ժ���
	public static void main(String[] args) {
		new LoginUI();

	}


	

}
