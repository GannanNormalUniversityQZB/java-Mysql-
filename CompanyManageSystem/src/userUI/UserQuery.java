package userUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Tools.JDBC;
import Tools.SetCenterSize;
import UI.CompanyManage;


public class UserQuery extends JDialog implements ActionListener{
	//�����Ա����
	JLabel check_Tip=new JLabel("�������û���:	");
	JTextField text_InputID=new JTextField(15);
	JButton button_Check=new JButton("��ѯ");
	/*�����*/
	JTable table;
	DefaultTableModel tableModel;
	String title[];
	String data[][];
	
	//���캯��
	public  UserQuery(CompanyManage view) {
		super(view);
		init();
	}
	
	//�����ʼ������
	public void init() {
		//���ô���
		setTitle("��ҵԱ����Ϣ����ϵͳ");
		setBounds(new SetCenterSize().getCenterBounds(600,450));	
		setModal(true);
		setLayout(new BorderLayout());
		/*���ô���ͼ��*/
		Image icon = Toolkit.getDefaultToolkit().getImage("images\\icon\\icon3.png");
		setIconImage(icon);
		
		/*���ñ�ǩ����*/
		check_Tip.setFont(new Font("����", Font.PLAIN, 16));
		
		//���north���
		Box Vbox=Box.createVerticalBox();
		
		Box northBox=Box.createHorizontalBox();
		northBox.add(check_Tip);
		northBox.add(Box.createHorizontalStrut(15));
		northBox.add(text_InputID);
		northBox.add(Box.createHorizontalStrut(15));
		northBox.add(button_Check);
		northBox.add(Box.createGlue());
		
		
		/*��ֱ����Ƕ��ƽ������*/
		Vbox.add(Vbox.createVerticalStrut(15));
		Vbox.add(northBox);
		Vbox.add(Vbox.createVerticalStrut(15));
		add(Vbox,BorderLayout.NORTH);
		
		//��ӱ��
		tableModel=new DefaultTableModel();
		table=new JTable(tableModel);
		
		/*���ñ�����*/
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
		tcr.setHorizontalAlignment(SwingConstants.CENTER);
		table.setDefaultRenderer(Object.class, tcr);
	
		add(new JScrollPane(table),BorderLayout.CENTER);
		//Ϊ��ť��Ӽ�����
		button_Check.addActionListener(this);
		/*�޸İ�ť��С*/
		button_Check.setPreferredSize(new Dimension(90, 8));
		
		
		
		//���ڽ�β����
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
	

	//��д�ӿں���
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==button_Check) {
			/*���ݿ�ֲ�������*/
			String user_id=text_InputID.getText().trim();
			Connection con=JDBC.getConnection();
			Statement sql;
			ResultSetMetaData metaData;
			ResultSet rs;
			/*��������*/
			int row=0;
			int col=0;
			/*�������*/
			try {
				sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				System.out.println(user_id);
				
				//�ı�������Ϊ��
				if(user_id.equals("")) {
					rs=sql.executeQuery("select * from administrators");
				}
				
				//
				else {
				rs=sql.executeQuery("select * from administrators where name="+"'"+user_id+"'");
				} //else��β
				
				//�������
				System.out.println("1111");
				//ע�ͣ������´����У�Ҫ�Ƚ��������Ļ�ȡ���ٽ��������Ļ�ȡ����������
				                                                         
				
				/*�����ȡ�ֶ�������*/
				metaData=rs.getMetaData();
				col=metaData.getColumnCount();
				/*����ֶ�����*/
				title=new String[col];
				for(int i=0;i<col;i++) {
					title[i]=metaData.getColumnName(i+1);
				}
				
				/*�������*/
				rs.last();
				row=rs.getRow();
				rs.beforeFirst();
				
				//��������all_Information
				data=new String [row][col];
				
				//��������ѭ���ļ�����
				for(int i=0;rs.next();i++)
					for(int j=0;j<col;j++)
						data[i][j]=rs.getString(j+1);
				
				
				
				//�������ʾ�ڽ�����
				tableModel.setDataVector(data, title);
				tableModel.fireTableDataChanged();
				JOptionPane.showMessageDialog(this, "��ѯ�ɹ�~��");
				System.out.println(12);
			
			}
			catch (SQLException e1) {
				System.out.println(e1);
				JOptionPane.showMessageDialog(this, "δ��ѯ�������Ϣ�������²�ѯ", "����~��",JOptionPane.WARNING_MESSAGE);
			}
			
		}
		
		
	}

	

	public static void main(String[] args) {
		
		new UserQuery(null);
	}

}
