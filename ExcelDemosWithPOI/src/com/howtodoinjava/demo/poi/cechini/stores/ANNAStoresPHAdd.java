package com.howtodoinjava.demo.poi.cechini.stores;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Iterator;

import org.apache.poi.ss.format.CellFormatType;
import org.apache.poi.ss.format.CellTextFormatter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.howtodoinjava.demo.poi.PostgresConnection;

public class ANNAStoresPHAdd 
{
	public static void main(String[] args) 
	{
		try
		{
			PostgresConnection postgresConnection = new PostgresConnection();
			Connection c = postgresConnection.conncet();
			Statement stmt = c.createStatement();
			String fileName = "renata.xlsx";
			String phId = "5008";
			FileInputStream file = new FileInputStream(new File("C://costrella_repo_ssd/cechini/jhipster2/web_app2/excels/zmiastami/"+fileName));

			XSSFWorkbook workbook = new XSSFWorkbook(file);

			XSSFSheet sheet = workbook.getSheetAt(0);

			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) 
			{
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				
				String sql = "INSERT INTO store (name, street, description, storegroup_id, person_id, address_id) "
			               + "VALUES ('"+ getFromCell(row.getCell(1))
			               +"','"+getFromCell(row.getCell(4))
			               +"','"+getFromCell(row.getCell(5))
			               +" "+getFromCell(row.getCell(6))
			               +" "+getFromCell(row.getCell(7))
			               
			               +"',"+getFromCell(row.getCell(0))+", "
			               + phId 
			               + ", "+getFromCell(row.getCell(2))+");";
				
				
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
	
	public static String getFromCell(Cell txt){
		if(txt == null){
			return "";
		}else{
			if(txt.equals("") || txt.equals(" ")){
				return "";
			}else{
				return txt.toString();
			}
		}
	}
}
