package com.products;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.usermanagement.Login;

import Utilities.DBConnection;

public class ProductManagement {
	
	public void addProduct() throws SQLException {
		Scanner scanner = new Scanner(System.in);
		Product product = new Product();
		
		System.out.println("Product Id>> ");
		product.setProductId(scanner.nextLong());
		scanner.nextLine();
		
		System.out.println("Product Name>> ");
		product.setProductName(scanner.nextLine());
		
		System.out.println("Product Description>> ");
		product.setProductDescription(scanner.nextLine());

		System.out.println("Quantity >> ");
		product.setAvailableQuantity(scanner.nextInt());
		
		System.out.println("Price>> ");
		product.setPrice(scanner.nextDouble());
		
		DBConnection dbConnectio = new DBConnection();
		Connection conn = dbConnectio.getConnection();
		
		try {
			PreparedStatement ps = conn.prepareStatement("insert into product (product_id, product_name, product_description, available_quantity, price) values(?, ?, ?, ?, ?)");
			ps.setLong(1, product.getProductId());
			ps.setString(2, product.getProductName());
			ps.setString(3, product.getProductDescription());
			ps.setInt(4, product.getAvailableQuantity());
			ps.setDouble(5, product.getPrice());
			int rowsUpdated = ps.executeUpdate();
			
			if (rowsUpdated == 1) {
				System.out.println(rowsUpdated + " product Added.");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}

	public void getProductCount() throws SQLException {
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Enter Product Id>> ");
		long productId = scanner.nextLong();
		
		DBConnection dbConnectio = new DBConnection();
		Connection conn = dbConnectio.getConnection();
		
		try {
			PreparedStatement ps = conn.prepareStatement("select available_quantity from product where product_id=?");
			ps.setLong(1, productId);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				System.out.println("Quantity is>> "+rs.getInt(1));
			} else {
				System.out.println("Product not found");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
	

	public List<Product> getAllProducts() {
		DBConnection dbConnectio = new DBConnection();
		Connection conn = dbConnectio.getConnection();

		List<Product> pl = new ArrayList<Product>();
		
		try {
			PreparedStatement ps = conn.prepareStatement("select * from product");
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				do {
					Product product = new Product();
					
					product.setProductId(rs.getInt(1));
					System.out.println("Product Id>> "+product.getProductId());
					product.setProductName(rs.getString(2));
					System.out.println("Product Name>> "+product.getProductName());
					product.setProductDescription(rs.getString(3));
					System.out.println("Product Description>> "+product.getProductDescription());
					product.setAvailableQuantity(rs.getInt(4));
					System.out.println("Available Quantity>> "+product.getAvailableQuantity());
					product.setPrice(rs.getInt(5));
					System.out.println("Price>> "+product.getPrice());
					System.out.println("----------------------------");
					pl.add(product);
				}while(rs.next());
				
			} else {
				System.out.println("Product not found");
			}

			conn.close();
			return pl;
		} catch (Exception e) {
			e.printStackTrace();
			return pl;
		}
	}
	
	public void addToCart(List<Product> pl) {
		DBConnection dbConnectio = new DBConnection();
		Connection conn = dbConnectio.getConnection();
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Enter the product id to buy product>> ");
		int productId = scanner.nextInt();
		System.out.println("Enter the quantity>> ");
		int quantity = scanner.nextInt();
		
		for(Product product: pl) {
			if (product.getProductId() == productId) {
				if (quantity <= product.getAvailableQuantity()) {
					try {
						PreparedStatement ps = conn.prepareStatement("insert into cart values(?, ?, ?)");
						ps.setInt(1, Login.userId);
						ps.setLong(2, product.getProductId());
						ps.setInt(3, quantity);
						int rowCount = ps.executeUpdate();
						if(rowCount == 0) {
							System.out.println("Error");
						}

						conn.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					System.out.println("Cannot add more than "+product.getAvailableQuantity()+" items");
				}
			}
		}
	}
	
	public void showCart() {
		DBConnection dbConnectio = new DBConnection();
		Connection conn = dbConnectio.getConnection();
			
		try {
			PreparedStatement ps = conn.prepareStatement("select userinformation.firstname, product.product_name, cart.quantity from cart \r\n" + 
					"INNER JOIN userinformation ON cart.user_id = userinformation.id \r\n" + 
					"INNER JOIN product ON cart.product_id = product.product_id where user_id=?");
			ps.setInt(1, Login.userId);
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
	
	public void buyItem() {
		DBConnection dbConnectio = new DBConnection();
		Connection conn = dbConnectio.getConnection();
		
		double totalAmount = 0;
			
		try {
			PreparedStatement ps = conn.prepareStatement("select product.available_quantity, product.price, cart.quantity, cart.product_id from cart \r\n" + 
					"INNER JOIN product ON cart.product_id = product.product_id where user_id=?");
			ps.setInt(1, Login.userId);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				do {
					totalAmount += (rs.getInt(3) * rs.getDouble(2));
					// STatement to update quantity in product table after buy
					PreparedStatement ps1 = conn.prepareStatement("update product SET available_quantity=?\r\n" +
							"where product_id=?");
					ps1.setInt(1, (rs.getInt(1) - rs.getInt(3)));
					ps1.setInt(2, (rs.getInt(4)));
					ps1.executeUpdate();
					
					// Statement to add row in order history table after buy
					ps1 = conn.prepareStatement("insert into orderhistory values(?, ?, ?)");
					ps1.setInt(1, Login.userId);
					ps1.setInt(2, rs.getInt(4));
					ps1.setInt(3, rs.getInt(3));
					int rowCount = ps1.executeUpdate();
					if(rowCount == 0) {
						System.out.println("Error updating order history");
					}
					
					// Statement to remove rows from cart table after buy
					ps1 = conn.prepareStatement("DELETE FROM cart\r\n" +
							"where product_id=? AND user_id=? AND quantity=?");
					ps1.setInt(1, rs.getInt(4));
					ps1.setInt(2, Login.userId);
					ps1.setInt(3, rs.getInt(3));
					int removeRowCount = ps1.executeUpdate();
					if (removeRowCount == 0) {
						System.out.println("Error in deleteing from cart");
					}
					
				} while(rs.next());
				System.out.println("Total Bill Amount>> "+totalAmount);
			} else {
				System.out.println("No items in your cart.");
			}

			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
