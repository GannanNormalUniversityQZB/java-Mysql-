package HelpUI;
import javax.swing.*;

import Tools.SetCenterSize;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TechHelp extends JDialog implements ActionListener{
	//�����Ա����
	JLabel label_picture=new JLabel(new ImageIcon("images\\help\\wechat.png"));
	JLabel label_contact=new JLabel("            ����������Ա΢��");
	JLabel label_sentence_1=new JLabel("�������ʹ�ù�������������");
	JLabel label_sentence_2=new JLabel("������󷽼���������Ա΢��");
	JLabel label_sentence_3=new JLabel("���Ƕ�ʮ��Сʱȫ��Ϊ������");
	
	//���幹�캯��
	public TechHelp() {
		super();
		//���ô���
		setLayout(new FlowLayout());
		setTitle("����֧��");
		setBounds(new SetCenterSize().getCenterBounds(480,350));	
		setModal(true);
		/*����ͼ��*/
		Image icon = Toolkit.getDefaultToolkit().getImage("images\\icon\\icon3.png");
		setIconImage(icon);
		
		
		//��װ
		/*�����м�����*/
		Box box_picture=Box.createVerticalBox();
		Box box_text=Box.createVerticalBox();
		JPanel panel_picture=new JPanel();
		Box box_layout=Box.createHorizontalBox();
		
		box_picture.add(label_picture);
		box_picture.add(Box.createVerticalStrut(20));
		box_picture.add(label_contact);
		panel_picture.add(box_picture);
		
		box_text.add(label_sentence_1);
		box_text.add(Box.createVerticalStrut(20));
		box_text.add(label_sentence_2);
		box_text.add(Box.createVerticalStrut(20));
		box_text.add(label_sentence_3);
		
		box_layout.add(panel_picture);
		box_layout.add(Box.createHorizontalStrut(30));
		box_layout.add(box_text);
		
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
		new TechHelp();

	}

}
