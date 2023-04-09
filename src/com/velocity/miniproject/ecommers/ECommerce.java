package com.velocity.miniproject.ecommers;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import Utilities.DBConnection;

import com.products.Product;
import com.products.ProductManagement;
import com.usermanagement.Login;
import com.usermanagement.UserManagement;

public class ECommerce {
	static List<Product> pl;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		showMenu(true);
	}
	
	public static void showMenu(boolean showFullMessage) {
		if(showFullMessage) {
			System.out.println("----------------------------\r\n"+
				"Welcome to E-Commerce based application\r\n" + 
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
		}
		
		System.out.println("Enter your choice - ");
		Scanner scanner = new Scanner(System.in);
		int userInput = scanner.nextInt();
		performAction(userInput);
	}
		
	public static void performAction(int userInput) {
		ProductManagement pm = new ProductManagement();
		switch(userInput) {
			case 1:
				UserManagement userManagement = new UserManagement();
				userManagement.addUser();
				showMenu(true);
				break;
			case 2:
				Login login = new Login();
				login.loginUser();
				showMenu(true);
				break;
			case 3:
				pl = pm.getAllProducts();
				showMenu(false);
				break;
			case 4:
				if (isLoggedIn() && getUsertype().equalsIgnoreCase("normal")) {
					pm.addToCart(pl);
					
					Scanner scanner = new Scanner(System.in);
					System.out.println("DO you want to view cart (Yes/No)");
					String showCart = scanner.next();
					
					if (showCart.equalsIgnoreCase("yes")) {
						performAction(5);
					} else {
						showMenu(false);
					}
				} else {
					System.out.println("**Please login first");
					showMenu(false);
				}
				break;
			case 5:
				if (isLoggedIn() && getUsertype().equalsIgnoreCase("normal")) {
					pm.showCart();
				} else {
					System.out.println("**Please login first");
				}
				showMenu(false);
				break;
			case 6:
				if (isLoggedIn() && getUsertype().equalsIgnoreCase("normal")) {
					pm.buyItem();
				} else {
					System.out.println("**Please login first");
				}
				showMenu(false);
				break;
			case 7:
				if (isLoggedIn() && getUsertype().equalsIgnoreCase("admin")) {
					ProductManagement productManagement = new ProductManagement();
					try {
						productManagement.addProduct();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} else {
					System.out.println("**Please note: Only Admin can add products.");
					showMenu(false);
				}
				break;
			case 10:
				if (isLoggedIn() && getUsertype().equalsIgnoreCase("admin")) {
					ProductManagement productManagement = new ProductManagement();
					try {
						productManagement.getProductCount();
						showMenu(false);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} else {
					System.out.println("**Please note: You cannot perform this action.");
					showMenu(false);
				}
				break;
		}
	}
	
	public static boolean isLoggedIn() {
		return new Boolean(Login.userType != null);
	}
	
	public static String getUsertype() {
		return Login.userType;
	}	

}
