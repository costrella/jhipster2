package com.howtodoinjava.demo.poi;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcelDemo 
{
	public static void main(String[] args) 
	{
		try
		{
			PostgresConnection postgresConnection = new PostgresConnection();
			Connection c = postgresConnection.conncet();
			Statement stmt = c.createStatement();
			
			FileInputStream file = new FileInputStream(new File("C://costrella_repo_ssd/cechini/jhipster2/web_app2/sieci.xlsx"));
//			FileInputStream file = new FileInputStream(new File("C://Users/mike/Desktop/sklepyPH/adam.xls"));

			//Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			//Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(5);

			//Iterate through each rows one by one
			Iterator<Row> rowIterator = sheet.iterator();
			int i = 2;
			while (rowIterator.hasNext()) 
			{
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				
				String sql = "INSERT INTO store (name, city, street, description, person_id) "
			               + "VALUES ('"+row.getCell(0)
			               +"','"+row.getCell(1)
			               +"','"+row.getCell(2)
			               +"','"+row.getCell(3)
			               +"', 1000);";
				
				String insertStoreGroup = 
						 "INSERT INTO storegroup (name) VALUES ('"+row.getCell(0)+"')";
				
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
