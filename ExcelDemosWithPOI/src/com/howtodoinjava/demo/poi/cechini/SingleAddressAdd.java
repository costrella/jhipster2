package com.howtodoinjava.demo.poi.cechini;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.howtodoinjava.demo.poi.PostgresConnection;

public class SingleAddressAdd 
{
	public static void main(String[] args) 
	{
		try
		{
			PostgresConnection postgresConnection = new PostgresConnection();
			Connection c = postgresConnection.conncet();
			Statement stmt = c.createStatement();
			
			
				String sql = "INSERT INTO address (id, city) "
			               + "VALUES ("
			               +383 +",'Chorzów" 
			               + "');";
				
				stmt.executeUpdate(sql);
			
			
			stmt.close();
			c.commit();
			c.close();
			System.out.println("done");
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
