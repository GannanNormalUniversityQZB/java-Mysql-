package Tools;
import java.sql.*;

public class JDBC {
	static Connection con;
	static String url="jdbc:mysql://localhost:3306/company";
	static String user="root";
	static String password="Qzb15607074531";
	static String driver="com.mysql.cj.jdbc.Driver";
	//�������ݿ⺯��
	public static Connection getConnection() {
		//��������
		try {
			Class.forName(driver);
			System.out.println("�������سɹ�~��");
		} 
		catch (ClassNotFoundException e) {
			System.out.println("��������ʧ��~��");
		}
		//�������ݿ�
		try {
			con=DriverManager.getConnection(url, user, password);
			System.out.println("�������ݳɹ�~��");
		} 
		catch (SQLException e) {
			System.out.println("��������ʧ��~��");
		}
		
		return con;
	}
	
}
