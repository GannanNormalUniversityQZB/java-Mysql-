package HelpUI;
import javax.swing.*;

import Tools.SetCenterSize;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class CopyrightUI extends JDialog implements ActionListener{
	//�����Ա����
		JLabel label_picture=new JLabel(new ImageIcon("images\\copyright\\copyright.png"));
		JLabel label_software_name=new JLabel("������ƣ���ҵԱ����Ϣ����ϵͳ");
		JLabel label_tech=new JLabel("���������java swing+Mysql");
		JLabel label_madeof_1=new JLabel("���������eclipse(version:2022.03)");
		JLabel label_madeof_2=new JLabel("                   Mysql(version:8.0.26)");
		JLabel label_madeof_3=new JLabel("                   Navicat(version:12))");
		JLabel label_unit_1=new JLabel("������λ������ʦ����ѧ");
		JLabel label_unit_2=new JLabel("                   ��ѧ��������ѧѧԺ");
		JLabel label_unit_3=new JLabel("                   �������ѧ�뼼��2102��");
		JLabel label_author=new JLabel("������ߣ���־��");
		JLabel label_tutor=new JLabel("ָ����ʦ����С��");
		
		//���幹�캯��
		public CopyrightUI() {
			super();
			//���ô���
			setLayout(new FlowLayout());
			setTitle("��Ȩ����");
			setBounds(new SetCenterSize().getCenterBounds(480,450));	
			setModal(true);
			/*����ͼ��*/
			Image icon = Toolkit.getDefaultToolkit().getImage("images\\icon\\icon3.png");
			setIconImage(icon);
			
			
			//��װ
			/*�����м�����*/
			Box box_picture=Box.createVerticalBox();
			JPanel panel_picture=new JPanel();

			Box box_layout=Box.createVerticalBox();
			
			/*�Ű�*/
			box_picture.add(label_picture);
			panel_picture.add(box_picture);


			
			box_layout.add(label_software_name);
			box_layout.add(Box.createVerticalStrut(10));
			box_layout.add(label_tech);
			box_layout.add(Box.createVerticalStrut(10));
			box_layout.add(label_madeof_1);
			box_layout.add(label_madeof_2);
			box_layout.add(label_madeof_3);
			box_layout.add(Box.createVerticalStrut(10));
			box_layout.add(label_unit_1);
			box_layout.add(label_unit_2);
			box_layout.add(label_unit_3);
			box_layout.add(Box.createVerticalStrut(10));
			box_layout.add(label_author);
			box_layout.add(Box.createVerticalStrut(10));
			box_layout.add(label_tutor);
			
			add(panel_picture);
			add(box_layout);
			
			//���ڽ�β����
			setVisible(true);
			setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		}
		
	
	//��д�ӿں���
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	//���Ժ���
	public static void main(String[] args) {
		new CopyrightUI();

	}


}
