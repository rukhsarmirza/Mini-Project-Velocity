package Utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.usermanagement.Login;
import com.usermanagement.User;


public class DBConnection {
	Connection conn;
	
	public void getConnection() {
		String connClass = "com.mysql.jdbc.Driver";
		String dbPath = "jdbc:mysql://localhost:3306/e-commerce";
		try {
			Class.forName(connClass);
			conn = DriverManager.getConnection(dbPath, "root", "Ti1@d234");
			
		} catch(Exception exception) {
			exception.printStackTrace();
		}
	}
	
	public void addUser(User user) {
		try {
			PreparedStatement ps = conn.prepareStatement("insert into userInformation (firstname, lastname, username, password, city, mailid, mobile) values(?, ?, ?, ?, ?, ?, ?)");
			ps.setString(1, user.getFirstName());
			ps.setString(2, user.getLastName());
			ps.setString(3, user.getUserName());
			ps.setString(4, user.getPassword());
			ps.setString(5, user.getCity());
			ps.setString(6, user.getMailId());
			ps.setString(7, user.getMobileNumber());
			int rowsUpdated = ps.executeUpdate();
			
			if (rowsUpdated == 1) {
				System.out.println(rowsUpdated + " user Added.");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loginUser(User user) {
		// Todo validate and login user
		try {
			PreparedStatement ps = conn.prepareStatement("select usertype from userinformation where username=? AND password=?");
			ps.setString(1, user.getUserName());
			ps.setString(2, user.getPassword());
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
			 do{
				System.out.println("logged in successfully.");
				Login.userType = rs.getString(1);
			 } while(rs.next());
			} else {
				System.out.println("Invalid Username and password");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
