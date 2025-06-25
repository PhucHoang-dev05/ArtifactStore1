package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;


public class JDBCUtil {
	public static Connection getConnection() {
	    Connection c = null;

	    try {
	        // Load MySQL Driver (bắt buộc với Java 8)
	        Class.forName("com.mysql.cj.jdbc.Driver");

	        String url = "jdbc:mysql://localhost:3306/mywebsite?useSSL=false&serverTimezone=UTC";
	        String username = "root";
	        String password = "";

	        c = DriverManager.getConnection(url, username, password);
	        System.out.println("✅ Kết nối CSDL thành công.");
	    } catch (Exception e) {
	        System.out.println("❌ Kết nối CSDL thất bại:");
	        e.printStackTrace();  // XEM lỗi cụ thể ở console
	    }

	    return c;
	}


	public static void closeConnection(Connection c) {
		try {
			if(c!=null) {
				c.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void printInfo(Connection c) {
		try {
			if(c!=null) {
				DatabaseMetaData mtdt = c.getMetaData();
				System.out.println(mtdt.getDatabaseProductName());
				System.out.println(mtdt.getDatabaseProductVersion());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}