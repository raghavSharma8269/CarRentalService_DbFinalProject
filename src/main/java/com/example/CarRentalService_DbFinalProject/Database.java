package com.example.CarRentalService_DbFinalProject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class Database {
	public static Connection connection;
	
	public static void connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost/doctors_office?serverTimezone=EST", "root", "minecraft242");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void setupClosingDBConnection() {
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				try { Database.connection.close(); System.out.println("Application Closed - DB Connection Closed");
				} catch (SQLException e) { e.printStackTrace(); }
			}
		}, "Shutdown-thread"));
	}
}