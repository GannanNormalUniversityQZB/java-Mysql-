package UI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.*;
import javax.swing.*;

import org.w3c.dom.css.RGBColor;

import HelpUI.CopyrightUI;
import HelpUI.TechHelp;
import Tools.*;
import departmentUI.DepartmentAdd;
import departmentUI.DepartmentDismiss;
import departmentUI.DepartmentModify;
import departmentUI.DepartmentQuery;
import employeeUI.EmployeeAdd;
import employeeUI.EmployeeModify;
import employeeUI.EmployeeQuery;
import positionUI.PositionAdd;
import positionUI.PositionDelete;
import positionUI.PositionModify;
import positionUI.PositionQuery;
import userUI.UserAdd;
import userUI.UserDelete;
import userUI.UserModify;
import userUI.UserQuery;

public class CompanyManage extends JFrame implements ActionListener,WindowListener{
	//�����Ա����
	/*�˵���*/
	JMenuBar menubar=new  JMenuBar();
	//�û�������
	public JMenu user_Manage=new JMenu("�û�����");
	public JMenuItem user_Query=new JMenuItem("��ѯ�û�");
	public JMenuItem user_Modify=new JMenuItem("�޸��û�");
	public JMenuItem user_Add=new JMenuItem("����û�");
	public JMenuItem user_Delete=new JMenuItem("ɾ���û�");
			
	//Ա������
	public JMenu emp_Manage=new JMenu("Ա������");
	public JMenuItem emp_Add=new JMenuItem("Ա��¼��");
	public JMenuItem emp_Query=new JMenuItem("Ա����ѯ");
	public JMenuItem emp_Modify=new JMenuItem("Ա���޸�");
	public JMenuItem emp_Delete=new JMenuItem("Ա��ɾ��");

	//���Ź���
	public JMenu depart_Manage=new JMenu("���Ź���");
	public JMenuItem depart_Query=new JMenuItem("��ѯ����");
	public JMenuItem depart_Modify=new JMenuItem("�޸Ĳ���");
	public JMenuItem depart_Add=new JMenuItem("��������");
	public JMenuItem depart_Dismiss=new JMenuItem("��ɢ����");
	
	//�û�����
	public JMenu position_Manage=new JMenu("ְλ����");
	public JMenuItem position_Query=new JMenuItem("��ѯְλ");
	public JMenuItem position_Modify=new JMenuItem("�޸�ְλ");
	public JMenuItem position_Add=new JMenuItem("���ְλ");
	public JMenuItem position_Delete=new JMenuItem("ɾ��ְλ");
	
	
	//������
	public JMenu help_Menu=new JMenu("��ð���");
	public JMenuItem help_tech=new JMenuItem("����֧��");
	public JMenuItem help_copyright=new JMenuItem("��Ȩ����");
	
	//������
	public JMenu menu_set=new JMenu("ϵͳ����");
	public JMenuItem menuItem_unsubscribe=new JMenuItem("ע���˻�");
	//public JMenuItem menuItem_authority=new JMenuItem("Ȩ������");	
	
	//������Logo
	public JLabel label_companyBack=new JLabel(new ImageIcon("images\\background\\companyBack2.jpg"));
	
	//��Ȩ��Ϣ
	public JLabel copyRight_Uni=new JLabel("����ʡ����ʦ����ѧ");
	public JLabel copyRight_Col=new JLabel("��ѧ��������ѧѧԺ");
	public JLabel copyRight_Cla=new JLabel("�������ѧ�뼼��2102");
	public JLabel copyRight_Aut=new JLabel("��Ȩ���У���־��");
	
	//����
	Font font=new Font("����", Font.ITALIC, 18);
	
	
	//���캯����
	
	public CompanyManage() {
		//���ô���,ʹ��Ĭ�ϲ���BorderLayout
		setLayout(new BorderLayout());
		setTitle("��ҵԱ����Ϣ����ϵͳ");
		setBounds(new SetCenterSize().getCenterBounds(750, 500));
		/*����ͼ��*/
		Image icon = Toolkit.getDefaultToolkit().getImage("images\\icon\\icon3.png");
		setIconImage(icon);
		
		//��װ�˵�
		user_Manage.add(user_Query);
		user_Manage.add(user_Modify);
		user_Manage.add(user_Add);
		user_Manage.add(user_Delete);
		
		emp_Manage.add(emp_Query);
		emp_Manage.add(emp_Modify);
		emp_Manage.add(emp_Add);
		
		depart_Manage.add(depart_Query);
		depart_Manage.add(depart_Modify);
		depart_Manage.add(depart_Add);
		depart_Manage.add(depart_Dismiss);
		
		position_Manage.add(position_Query);
		position_Manage.add(position_Modify);
		position_Manage.add(position_Add);
		position_Manage.add(position_Delete);

		help_Menu.add(help_tech);
		help_Menu.add(help_copyright);
		
		menu_set.add(menuItem_unsubscribe);
		//menu_set.add(menuItem_authority);
		
		/*��Ӳ˵�*/
		menubar.add(user_Manage);
		menubar.add(emp_Manage);
		menubar.add(depart_Manage);
		menubar.add(position_Manage);
		menubar.add(help_Menu);
		menubar.add(menu_set);
		setJMenuBar(menubar);
		

		
		/*���Logo���Ȩ������*/
		add(label_companyBack,BorderLayout.CENTER);
		
		
		//Ϊ������Ӽ�����
		/*�û�������*/
		user_Add.addActionListener(this);
		user_Query.addActionListener(this);
		user_Modify.addActionListener(this);
		user_Delete.addActionListener(this);
		
		/*Ա��������*/
		emp_Add.addActionListener(this);
		//emp_Delete.addActionListener(this);
		emp_Modify.addActionListener(this);
		emp_Query.addActionListener(this);
		
		/*���Ź���ȡ*/
		depart_Add.addActionListener(this);
		depart_Dismiss.addActionListener(this);
		depart_Modify.addActionListener(this);
		depart_Query.addActionListener(this);
		
		/*ְλ������*/
		position_Add.addActionListener(this);
		position_Delete.addActionListener(this);
		position_Modify.addActionListener(this);
		position_Query.addActionListener(this);
		
		/*��ð�����*/
		help_tech.addActionListener(this);
		help_copyright.addActionListener(this);
		
		/*ϵͳ������*/
		menuItem_unsubscribe.addActionListener(this);
		//menuItem_authority.addActionListener(this);
		//���ڽ�β����
		addWindowListener(this);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
	}
	


	@Override
	public void actionPerformed(ActionEvent e) {
		//�û�������
		if(e.getSource()==user_Add) {
			new UserAdd(this);
		}
		else if(e.getSource()==user_Query) {
			new UserQuery(this);
		}
		else if(e.getSource()==user_Delete) {
			new UserDelete(this);
		}
		else if(e.getSource()==user_Modify) {
			new UserModify(this);
		}
		
		//Ա��������
		else if(e.getSource()==emp_Add) {
			new EmployeeAdd(this);
		}
		else if(e.getSource()==emp_Delete) {
			//new EmployeeAdd(this);
		}
		else if(e.getSource()==emp_Modify) {
			new EmployeeModify(this,null);
		}
		else if(e.getSource()==emp_Query) {
			new EmployeeQuery(this);
		}
		
		//���Ź�����
		else if(e.getSource()==depart_Query) {
			new DepartmentQuery(this);
		}
		else if(e.getSource()==depart_Dismiss) {
			new DepartmentDismiss(this, null);
		}
		else if(e.getSource()==depart_Modify) {
			new DepartmentModify(this, null);
		}
		else if(e.getSource()==depart_Add) {
			new DepartmentAdd(null);
		}
		
		//ְλ������
		else if(e.getSource()==position_Query) {
			new PositionQuery(this, null);
		}
		else if(e.getSource()==position_Delete) {
			new PositionDelete(this, null, null);
		}
		else if(e.getSource()==position_Modify) {
			new PositionModify(this, null, null);
		}
		else if(e.getSource()==position_Add) {
			new PositionAdd(null, null);
		}
		
		//��ð�����
		else if(e.getSource()==help_tech) {
			new TechHelp();
		}
		else if(e.getSource()==help_copyright) {
			new CopyrightUI();
		}
		
		//ϵͳ������
		else if(e.getSource()==menuItem_unsubscribe) {
			int comfirm=JOptionPane.showConfirmDialog(this, "��ȷ��Ҫע�����˻�������ϵͳ��","����",JOptionPane.YES_NO_OPTION);
			if(comfirm==JOptionPane.YES_OPTION) {
				new LoginUI();
				dispose();
				JOptionPane.showMessageDialog(this, "�˻�ע���ɹ���ϵͳ������~��");
			}
			else {
				JOptionPane.showMessageDialog(this, "��ȡ������~��");
			}
		}
	}
	
	//���Ժ���
	public static void main(String[] args) {
		new CompanyManage();
}



	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void windowClosing(WindowEvent e) {
		int confirm=JOptionPane.showConfirmDialog(this, "��ȷ��Ҫ�ر�ϵͳ��", "��ҵԱ����Ϣ����ϵͳ",JOptionPane.YES_NO_OPTION);
		if(confirm==JOptionPane.YES_OPTION) {
			dispose();
			JOptionPane.showMessageDialog(this, "ϵͳ�ѹرգ���л����ʹ��~��");
		}
		else
			JOptionPane.showMessageDialog(this, "��ȡ������~��");
	}



	@Override
	public void windowClosed(WindowEvent e) {
		
		
	}



	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}
