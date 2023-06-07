package HelpUI;
import javax.swing.*;

import Tools.SetCenterSize;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class CopyrightUI extends JDialog implements ActionListener{
	//定义成员变量
		JLabel label_picture=new JLabel(new ImageIcon("images\\copyright\\copyright.png"));
		JLabel label_software_name=new JLabel("软件名称：企业员工信息管理系统");
		JLabel label_tech=new JLabel("软件技术：java swing+Mysql");
		JLabel label_madeof_1=new JLabel("制作软件：eclipse(version:2022.03)");
		JLabel label_madeof_2=new JLabel("                   Mysql(version:8.0.26)");
		JLabel label_madeof_3=new JLabel("                   Navicat(version:12))");
		JLabel label_unit_1=new JLabel("制作单位：赣南师范大学");
		JLabel label_unit_2=new JLabel("                   数学与计算机科学学院");
		JLabel label_unit_3=new JLabel("                   计算机科学与技术2102班");
		JLabel label_author=new JLabel("软件作者：邱志斌");
		JLabel label_tutor=new JLabel("指导老师：程小扬");
		
		//定义构造函数
		public CopyrightUI() {
			super();
			//设置窗口
			setLayout(new FlowLayout());
			setTitle("版权声明");
			setBounds(new SetCenterSize().getCenterBounds(480,450));	
			setModal(true);
			/*设置图标*/
			Image icon = Toolkit.getDefaultToolkit().getImage("images\\icon\\icon3.png");
			setIconImage(icon);
			
			
			//组装
			/*创建中间容器*/
			Box box_picture=Box.createVerticalBox();
			JPanel panel_picture=new JPanel();

			Box box_layout=Box.createVerticalBox();
			
			/*排版*/
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
			
			//窗口结尾处理
			setVisible(true);
			setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		}
		
	
	//重写接口函数
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	//测试函数
	public static void main(String[] args) {
		new CopyrightUI();

	}


}
