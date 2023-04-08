package com.velocity.miniproject.ecommers;

import java.util.Scanner;

import Utilities.DBConnection;
import com.usermanagement.Login;
import com.usermanagement.UserManagement;

public class ECommerce {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ECommerce ecommerce = new ECommerce();
		ecommerce.showMenu();
	}
	
	public void showMenu() {
		System.out.println("Welcome to E-Commerce based application\r\n" + 
				"User Operation\r\n" + 
				"1. User Registration\r\n" + 
				"2. User Login\r\n" + 
				"3. User view Product item as Sorted Order\r\n" + 
				"4. Buy Product\r\n" + 
				"5. View Cart\r\n" + 
				"6. Purchase the item\r\n\n" + 
				"Admin Operation\r\n" + 
				"7. Add product item\r\n" + 
				"8. Calculate Bill\r\n" + 
				"9. Display amount to End User\r\n" + 
				"10.Check Quantity\r\n" + 
				"11. Check registered user\r\n" + 
				"12. Check the particular user history\r\n" + 
				"Guest Operation\r\n\n" + 
				"13. View product item\r\n" + 
				"14. Not purchase item");
		
		System.out.println("Enter your choice - ");
		Scanner scanner = new Scanner(System.in);
		int userInput = scanner.nextInt();
		
		switch(userInput) {
			case 1:
				UserManagement userManagement = new UserManagement();
				userManagement.addUser();
				break;
			case 2:
				Login login = new Login();
				login.loginUser();
		}
		
		scanner.close();
	}

}
