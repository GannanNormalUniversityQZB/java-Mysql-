package HelpUI;
import javax.swing.*;

import Tools.SetCenterSize;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TechHelp extends JDialog implements ActionListener{
	//定义成员变量
	JLabel label_picture=new JLabel(new ImageIcon("images\\help\\wechat.png"));
	JLabel label_contact=new JLabel("            技术开发人员微信");
	JLabel label_sentence_1=new JLabel("如果您在使用过程中遇到问题");
	JLabel label_sentence_2=new JLabel("请添加左方技术开发人员微信");
	JLabel label_sentence_3=new JLabel("我们二十四小时全天为您服务");
	
	//定义构造函数
	public TechHelp() {
		super();
		//设置窗口
		setLayout(new FlowLayout());
		setTitle("技术支持");
		setBounds(new SetCenterSize().getCenterBounds(480,350));	
		setModal(true);
		/*设置图标*/
		Image icon = Toolkit.getDefaultToolkit().getImage("images\\icon\\icon3.png");
		setIconImage(icon);
		
		
		//组装
		/*创建中间容器*/
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
		new TechHelp();

	}

}
