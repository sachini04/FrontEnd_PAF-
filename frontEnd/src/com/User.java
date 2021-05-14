package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class User {
	
	// connect to the DB
		private Connection connect(){
			Connection con = null;
			try{
				Class.forName("com.mysql.cj.jdbc.Driver");

				//Provide the correct details: DBServer/DBName, username, password
				con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/userdb", "root", "");
				
			}catch (Exception e){
				e.printStackTrace();
			}
			
			return con;
		}
				
			
			
			//Insert Project Details
			public String insertCart(String orderId, String buyerName, String address, String NIC,String softwareName, String size, String version, String cost, String date){
				String output = "";
				try{
					Connection con = connect();
						if (con == null){
							return "Error while connecting to the database for inserting."; 
					}
					
						
						// create a prepared statement
						String query = "INSERT INTO `orders`(`orderId`, `buyerName`, `address`, `NIC`, `softwareName`, `size`, `version`, `cost`, `date`) VALUES (?,?,?,?,?,?,?,?,?)";
						PreparedStatement preparedStmt = con.prepareStatement(query);
						
						
						 // binding values
						 preparedStmt.setInt(1, 0);
						 preparedStmt.setString(2, buyerName);
						 preparedStmt.setString(3, address);
						 preparedStmt.setString(4, NIC);
						 preparedStmt.setString(5, softwareName);
						 preparedStmt.setDouble(6, Integer.parseInt(size));
						 preparedStmt.setDouble(7, Integer.parseInt(version));
						 preparedStmt.setDouble(8, Float.parseFloat(cost)); 
						 preparedStmt.setString(9, date);
						 
						 // execute the statement
						 preparedStmt.execute();
						 con.close();
						 
						 String newCart = readCart(); 
						 output = "{\"status\":\"success\", \"data\": \"" + newCart + "\"}";
						 
						 }catch (Exception e)
						 {
							 
							 output = "{\"status\":\"error\", \"data\":\"Error while inserting the order to cart.\"}"; 
							 System.err.println(e.getMessage());
						 }
				 return output;
			 }
			

			public String readCart(){
				String output = "";
				try{
					Connection con = connect();
						if (con == null){
							return "Error while connecting to the database for reading."; 
				}
						
					// Prepare the html table to be displayed
					output = 
							"<table border='1' >"+ 
							"<tr >" +
								 "<th >Buyer Name</th>" +
								 "<th >Address</th>" +
								 "<th>NIC</th>" +
								 "<th>Software Name</th>" +
								 "<th>Size</th>" +
								 "<th>Version</th>" +
								 "<th>Cost</th>" +
								 "<th>Date</th>" +
								 "<th>Update</th>" +
								 "<th>Remove</th>" +
							
							 "</tr>";
		
					String query = "select * from `orders`";
					 Statement stmt = con.createStatement();
					 ResultSet rs = stmt.executeQuery(query);
					 
					 
					 // iterate through the rows in the result set
					 while (rs.next()){
						 
						 
						 String orderId =  Integer.toString(rs.getInt("orderId"));
						 String buyerName = rs.getString("buyerName");
						 String address = rs.getString("address");
						 String NIC = rs.getString("NIC");
						 String softwareName = rs.getString("softwareName");
						 String size =  Integer.toString(rs.getInt("size"));
						 String version =  Integer.toString(rs.getInt("version"));
						 String cost = Float.toString(rs.getFloat("cost"));
						 String date = rs.getString("date");
		
						 
						 // Add into the html table
						 
						 //output += "<tr><td>" + orderId + "</td>";
						 output += "<td>" + buyerName + "</td>";
						 output += "<td>" + address + "</td>";
						 output += "<td>" + NIC + "</td>";
						 output += "<td>" + softwareName + "</td>";
						 output += "<td>" + size + "</td>";
						 output += "<td>" + version + "</td>";
						 output += "<td>" + cost + "</td>";
						 output += "<td>" + date + "</td>";
			
						 
						 
						 // buttons
						
						 output += "<td><input name='btnUpdate' type='button' value='Update' "
									+ "class='btnUpdate btn btn-secondary' data-userid='" + orderId + "'></td>"
									+ "<td><input name='btnRemove' type='button' value='Remove' "
									+ "class='btnRemove btn btn-danger' data-userid='" + orderId + "'></td></tr>"; 
					 }
				 con.close();
				 
				 // Complete the html table
				 output += "</table>";
				 
				 
				 }catch (Exception e){
					 
					 output = "Error while reading the cart orders.";
					 System.err.println(e.getMessage());
				 }
				 return output;
				 
			}
			
			
			public String updateCart(String orderId, String buyerName, String address, String NIC,String softwareName, String size, String version, String cost, String date){ 
				String output = ""; 
				try{
					Connection con = connect();
					if (con == null){
						return "Error while connecting to the database for updating."; 
					} 
					
					 // create a prepared statement
					String query = "UPDATE `orders` SET `buyerName`=?,`address`=?,`NIC`=?,`softwareName`=?,`size`=?,`version`=?,`cost`=?,`date`=? WHERE `orderId`=?";
					 PreparedStatement preparedStmt = con.prepareStatement(query); 
					 
					 // binding values
					  
					 preparedStmt.setString(1, buyerName);
					 preparedStmt.setString(2, address);
					 preparedStmt.setString(3, NIC);
					 preparedStmt.setString(4, softwareName);
					 preparedStmt.setDouble(5, Integer.parseInt(size));
					 preparedStmt.setDouble(6, Integer.parseInt(version));
					 preparedStmt.setDouble(7, Float.parseFloat(cost)); 
					 preparedStmt.setString(8, date);
					 preparedStmt.setString(9, orderId);
					 
					// preparedStmt.setString(4, sector);
					
					 
	 
					 
					 // execute the statement
					 preparedStmt.execute(); 
					 con.close(); 
					 
					 String newCart = readCart(); 
					 output = "{\"status\":\"success\", \"data\": \"" + newCart + "\"}";
					 
			
					 } catch (Exception e) {
						 
						 output = "{\"status\":\"error\", \"data\": \"Error while updating the cart order.\"}";
						 System.err.println(e.getMessage()); 
					 } 
					 return output; 
			 }
			
			public String deleteCart(String orderId) { 
				String output = ""; 
				try{ 
					Connection con = connect();
					if (con == null) { 
						return "Error while connecting to the database for deleting."; 
					} 
						// create a prepared statement
					    String query ="DELETE FROM `orders` WHERE orderId=?";
						PreparedStatement preparedStmt = con.prepareStatement(query); 
						
						// binding values
						preparedStmt.setInt(1, Integer.parseInt(orderId)); 
						
						// execute the statement
						preparedStmt.execute(); 
						con.close(); 
						
						String newCart = readCart(); 
						output = "{\"status\":\"success\", \"data\": \"" + newCart + "\"}"; 
						
				} catch (Exception e) { 
					output = "{\"status\":\"error\", \"data\": \"Error while deleting the cart order.\"}"; 
					System.err.println(e.getMessage()); 
				} 
				return output; 
			}
			
			
			
			
}
