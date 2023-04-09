package com.usermanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.products.Product;

import Utilities.DBConnection;

public class UserManagement {
	
	public void addUser() {
		DBConnection dbConn = new DBConnection();
		dbConn.getConnection();
		
		Scanner scanner = new Scanner(System.in);
		User user = new User();
		
		System.out.println("Enter the first name>> ");
		String firstName = scanner.nextLine();
		user.setFirstName(firstName);
		
		System.out.println("Enter the last name>> ");
		String lastName = scanner.nextLine();
		user.setLastName(lastName);

		System.out.println("Enter the username>> ");
		String userName = scanner.next();
		user.setUserName(userName);

		System.out.println("Enter the password>> ");
		String password = scanner.next();
		user.setPassword(password);

		System.out.println("Enter the city>> ");
		String city = scanner.next();
		user.setCity(city);

		System.out.println("Enter the mail id>> ");
		String mailId = scanner.next();
		user.setMailId(mailId);

		System.out.println("Enter the mobile number>> ");
		String mobileNo = scanner.next();
		user.setMobileNumber(mobileNo);
		
		dbConn.addUser(user);
	}

	public void getAllUsers() {

		DBConnection dbConnectio = new DBConnection();
		Connection conn = dbConnectio.getConnection();
		
		try {
			PreparedStatement ps = conn.prepareStatement("select * from userinformation");
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				do {					
					System.out.println("User Id>> "+rs.getInt(1));
					System.out.println("First Name>> "+rs.getString(2));
					System.out.println("Last Name>> "+rs.getString(3));
					System.out.println("City>> "+rs.getString(6));
					System.out.println("Mail Id>> "+rs.getString(7));
					System.out.println("Mobile>> "+rs.getString(8));
					System.out.println("User Type>> "+rs.getString(9));
					System.out.println("----------------------------");
				}while(rs.next());
				
			} else {
				System.out.println("Users not found");
			}

			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getUserHistory(int userId) {

		DBConnection dbConnectio = new DBConnection();
		Connection conn = dbConnectio.getConnection();
		
		try {
			PreparedStatement ps = conn.prepareStatement("select userinformation.firstname, product.product_name, orderhistory.quantity from orderhistory \r\n" + 
					"INNER JOIN userinformation ON orderhistory.user_id = userinformation.id \r\n" + 
					"INNER JOIN product ON orderhistory.product_id = product.product_id where user_id=?");
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				do {
					System.out.println("User name: "+rs.getString(1)+" | Product Name: " +rs.getString(2)+ " | Quantity: "+ rs.getInt(3));
				} while(rs.next());
			} else {
				System.out.println("No items in your cart.");
			}

			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
