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
			c = DriverManager.getConnection(
					"jdbc:postgresql://localhost:5432/jhipster", "postgres",
					"postgres");
//			c = DriverManager.getConnection(
//					"jdbc:postgresql://137.v.tld.pl:5432/pg137_cechini01", "pg137_cechini01",
//					"Aasdasd1!");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");
			return c;
			
//			stmt = c.createStatement();
//			String sql = "INSERT INTO store (ID,NAME,PERSON_ID) "
//					+ "VALUES (1, 'Biedronka', 32);";
//			stmt.executeUpdate(sql);

			// sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
			// + "VALUES (2, 'Allen', 25, 'Texas', 15000.00 );";
			// stmt.executeUpdate(sql);
			//
			// sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
			// + "VALUES (3, 'Teddy', 23, 'Norway', 20000.00 );";
			// stmt.executeUpdate(sql);
			//
			// sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
			// + "VALUES (4, 'Mark', 25, 'Rich-Mond ', 65000.00 );";
			// stmt.executeUpdate(sql);

//			stmt.close();
//			c.commit();
//			c.close();
			
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Records created successfully");
		return null;
	}
}
