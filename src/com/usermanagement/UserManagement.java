package com.usermanagement;

import java.util.Scanner;

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
		
		scanner.close();
	}
}
