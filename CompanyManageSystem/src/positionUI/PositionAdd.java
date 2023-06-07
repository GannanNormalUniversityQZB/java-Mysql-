package positionUI;

import Tools.JDBC;
import Tools.SetCenterSize;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.sql.*;
public class PositionAdd extends JDialog implements ActionListener{
	//�����Ա����
	JLabel label_depart=new JLabel("ѡ���ţ�");
	JLabel label_position=new JLabel("����ְλ��");
	
	/*�ı�����*/
	JTextField text_depart=new JTextField(15);
	
	/*��ť��*/
	JButton button_add=new JButton("���");
	JButton button_cancel=new JButton("ȡ��");
	
	/*�����б���*/
	
	String array_depart[];
	JComboBox<String> comboBox_depart=new JComboBox<>();
	
	/*��˾logo��*/
	public JLabel companyLogo=new JLabel(new ImageIcon("images\\Logo\\companyLogo2.png"));
	
	/*���ڽ��մ�����ҵ����������²�������*/
	String new_depart;
	
	//������ʼ������
	public PositionAdd(JDialog view,String new_depart) {
		super(view);
		//���ô���
		setLayout(new FlowLayout());
		setTitle("����ְλ");
		setBounds(new SetCenterSize().getCenterBounds(480,310));	
		setModal(true);
		/*����ͼ��*/
		Image icon = Toolkit.getDefaultToolkit().getImage("images\\icon\\icon3.png");
		setIconImage(icon);
		
		//������
		/*�����м�����*/
		Box Vbox=Box.createVerticalBox();
		Box box_depart=Box.createHorizontalBox();
		Box box_position=Box.createHorizontalBox();
		Box box_button=Box.createHorizontalBox();
		
		box_depart.add(label_depart);
		box_depart.add(comboBox_depart);
		
		box_position.add(label_position);
		box_position.add(text_depart);
		
		box_button.add(button_add);
		box_button.add(Box.createHorizontalStrut(35));
		box_button.add(button_cancel);
		
		Vbox.add(Box.createVerticalStrut(10));
		Vbox.add(box_depart);
		Vbox.add(Box.createVerticalStrut(20));
		Vbox.add(box_position);
		Vbox.add(Box.createVerticalStrut(20));
		Vbox.add(box_button);
		
		add(companyLogo);
		add(Vbox);
		
		//���ò��������ʼ״̬
		this.new_depart=new_depart;
		//�������
		System.out.println(this.new_depart);
		
		/*���ݿ�ֲ�����*/
		Connection con=JDBC.getConnection();
		try {
			Statement sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs=sql.executeQuery("select * from department ");
			rs.last();
			int row=rs.getRow();
			rs.beforeFirst();
			/*��������*/
			/*ȷ���Ƿ���Ҫ��������*/
			if(new_depart!=null){
				array_depart=new String[row+1];
				for(int i=0;rs.next();i++)
					array_depart[i]=rs.getString(1);
				array_depart[array_depart.length-1]=new_depart;
			}
			else {
				array_depart=new String[row];
				for(int i=0;rs.next();i++)
					array_depart[i]=rs.getString(1);
			}
				
			/*��ʼ��ְλ*/
			comboBox_depart.removeAllItems();
			for(String a:array_depart)
				comboBox_depart.addItem(a);
			/*�����������ţ�ȷ����index*/
			if(new_depart!=null)
				comboBox_depart.setSelectedItem(array_depart[array_depart.length-1]);
			
			
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
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
			/*�����Ϣ�ֲ�������*/
			String temp_depart=(String)this.comboBox_depart.getSelectedItem();
			String temp_position=this.text_depart.getText();
			
			/*���ݿ�ֲ�������*/
			Connection con=JDBC.getConnection();
			Statement sql;
			ResultSet rs;
			boolean isInMysql=false;
			try {
				sql = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rs=sql.executeQuery("select * from department");
				//�жϴ�����ҵ��������Ĳ����Ƿ����
				if(new_depart!=null) {
					while(rs.next())
						if(new_depart.equals(rs.getString(1))) {
							isInMysql=true;
							break;
						}
				}
				else {	
					System.out.println("û����������");
				}
			} 
			catch (SQLException e2) {
				e2.printStackTrace();
			}
			
			//������²��ţ�����department��position��
			if(new_depart!=null && !isInMysql  &&  !temp_position.equals("")) {
				try {
					sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
					/*����department*/
					int update=sql.executeUpdate("insert into department values('"+new_depart+"')");
					System.out.println("department���Ѿ�����");
					/*position��*/
					int update_2=sql.executeUpdate("insert into position values('"+new_depart+"','"+temp_position+"')" );
					//��position����ͬ��������ҵ��Աְλ
					int update_3=sql.executeUpdate("insert into position values('"+new_depart+"','"+"��ҵ��Ա"+"')" );
					/*��������*/
					if(update!=0 && update_2!=0 && update_3!=0) {
						JOptionPane.showMessageDialog(this, "����ְλ:"+temp_position+"��ӳɹ�~!");
					}
					else
						JOptionPane.showMessageDialog(this, "����ְλ:"+temp_position+"���ʧ��~!");
				}
				catch (SQLException e1) {
					JOptionPane.showMessageDialog(this, "����ְλ:"+temp_position+"���ʧ��~!");
					e1.printStackTrace();
				}
				
			}//new_depart!=null
			
			//���û���²��ţ�������position��
			else if(!temp_position.equals("")){
				try {
					sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
					int update=sql.executeUpdate("insert into position values('"+temp_depart+"','"+temp_position+"')" );
					/*��������*/
					if(update!=0) {
						JOptionPane.showMessageDialog(this, "����ְλ:"+temp_position+"��ӳɹ�~!");
					}
					else
						JOptionPane.showMessageDialog(this, "����ְλ:"+temp_position+"���ʧ��~!");
				}
				catch (SQLException e1) {
					JOptionPane.showMessageDialog(this, "����ְλ:"+temp_position+"���ʧ��~!");
					e1.printStackTrace();
				}
			}
				else
					JOptionPane.showMessageDialog(this, "ְλ����Ϊ��,�����ظ�~��", "����", JOptionPane.WARNING_MESSAGE);
		}//e.getSource()==button_add
		
		
		else if(e.getSource()==button_cancel) {
			dispose();
		}
	}
	
	
	//���Ժ���
	public static void main(String[] args) {
		new PositionAdd(null,null);

	}

}
