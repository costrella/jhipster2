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

public class StoresPHAdd 
{
	public static void main(String[] args) 
	{
		try
		{
			PostgresConnection postgresConnection = new PostgresConnection();
			Connection c = postgresConnection.conncet();
			Statement stmt = c.createStatement();
			String fileName = "arturTymosz";
			String phId = "1000";
			FileInputStream file = new FileInputStream(new File("C://costrella_repo_ssd/cechini/jhipster2/web_app2/excels/"+fileName+".xlsx"));

			XSSFWorkbook workbook = new XSSFWorkbook(file);

			XSSFSheet sheet = workbook.getSheetAt(0);

			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) 
			{
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				
				String sql = "INSERT INTO store (name, city, street, description, storegroup_id, person_id) "
			               + "VALUES ('"+row.getCell(1)
			               +"','"+row.getCell(2)
			               +"','"+row.getCell(3)
			               +"','"+row.getCell(4)
//			               +"','"+row.getCell(5)==""?null:row.getCell(5)
			               +"',"+row.getCell(0)+", "
			               + phId +");";
				
				
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
