package com.usermanagement;

import java.util.Scanner;

import Utilities.DBConnection;

public class Login {
	public static String userType;
	public static int userId;
	
	public void loginUser() {
		DBConnection dbConn = new DBConnection();
		dbConn.getConnection();
		
		Scanner scanner = new Scanner(System.in);
		User user = new User();
		
		System.out.println("Enter the username>> ");
		String userName = scanner.next();
		user.setUserName(userName);
		
		System.out.println("Enter the password>> ");
		String password = scanner.next();
		user.setPassword(password);
		
		dbConn.loginUser(user);
	}
	
}
