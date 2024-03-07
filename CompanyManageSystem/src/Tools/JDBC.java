package Tools;
import java.sql.*;

public class JDBC {
	static Connection con;
	static String url="jdbc:mysql://localhost:3306/company";
	static String user="root";
	static String password="";
	static String driver="com.mysql.cj.jdbc.Driver";
	//连接数据库函数
	public static Connection getConnection() {
		//加载驱动
		try {
			Class.forName(driver);
			System.out.println("驱动加载成功~！");
		} 
		catch (ClassNotFoundException e) {
			System.out.println("驱动加载失败~！");
		}
		//连接数据库
		try {
			con=DriverManager.getConnection(url, user, password);
			System.out.println("连接数据成功~！");
		} 
		catch (SQLException e) {
			System.out.println("连接数据失败~！");
		}
		
		return con;
	}
	
}
