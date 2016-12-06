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

public class AddressAdd 
{
	public static void main(String[] args) 
	{
		try
		{
			PostgresConnection postgresConnection = new PostgresConnection();
			Connection c = postgresConnection.conncet();
			Statement stmt = c.createStatement();
			
			FileInputStream file = new FileInputStream(new File("C://costrella_repo_ssd/cechini/jhipster2/web_app2/excels/zmiastami/miasta.xlsx"));

			XSSFWorkbook workbook = new XSSFWorkbook(file);

			XSSFSheet sheet = workbook.getSheetAt(0);

			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) 
			{
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				
				String sql = "INSERT INTO address (id, city) "
			               + "VALUES ("
			               +row.getCell(0) +", '"+row.getCell(1)
			               + "');";
				
				stmt.executeUpdate(sql);
			}
			file.close();
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
