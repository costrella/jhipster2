package com.howtodoinjava.demo.poi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class PostgresConnection {
	
	public Connection conncet() {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.postgresql.Driver");
//			c = DriverManager.getConnection(
//					"jdbc:postgresql://localhost:5432/jhipster", "postgres",
//					"postgres");
			
			
			c = DriverManager.getConnection(
					"jdbc:postgresql://137.v.tld.pl:5432/pg137_c1_2", "pg137_c1_2",
					"Aasdasd1!");
			
			
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");
			return c;
			
			
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Records created successfully");
		return null;
	}
}
