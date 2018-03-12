package com.howtodoinjava.demo.poi.cechini;

import java.io.File;
import java.io.FileInputStream;
import java.sql.*;
import java.util.Iterator;

import com.howtodoinjava.demo.poi.cechini.model.Address;
import com.howtodoinjava.demo.poi.cechini.model.Storegroup;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.howtodoinjava.demo.poi.PostgresConnection;

public class AddressAdd {
    public static void main(String[] args) {

        try {
            PostgresConnection postgresConnection = new PostgresConnection();
            Connection c = postgresConnection.conncet();
            Statement stmt = c.createStatement();

            AddressAdd converter = new AddressAdd();

            FileInputStream file = new FileInputStream(new File("C://mkostrzewa//projects//cechini//import//02a.xlsx"));

            XSSFWorkbook workbook = new XSSFWorkbook(file);

            XSSFSheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.iterator();
            int i = 2;
            Row row1 = rowIterator.next();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                String cityFromCell = converter.getCellStringValue(row, 3);
                long idAdr = 0;
                if (cityFromCell == null) {
                    idAdr = 5005; //'nieprzypisane'
                } else {
                    Address address = converter.getCity(c, cityFromCell);
                    if (address != null) {
                        idAdr = address.getId();
                    } else {
                        String insertAddress = "INSERT INTO address (city) VALUES (?)";
                        PreparedStatement statement = c.prepareStatement(insertAddress);
                        statement.setString(1, cityFromCell);
                        statement.executeUpdate();
                        c.commit();
                        Address addressAdded = converter.getCity(c, cityFromCell);
                        idAdr = addressAdded.getId();
                    }
                }

                String sgFromCell = converter.getCellStringValue(row, 2);
                long idSg = 0;
                if (sgFromCell == null) {
                    idSg = 5003; //'nieprzypisane'
                } else {
                    Storegroup sg = converter.getStoregroup(c, sgFromCell);
                    if (sg != null) {
                        idSg = sg.getId();
                    } else {
                        String insertSg = "INSERT INTO storegroup (name) VALUES (?)";
                        PreparedStatement statement = c.prepareStatement(insertSg);
                        statement.setString(1, sgFromCell);
                        statement.executeUpdate();
                        c.commit();
                        Storegroup sgAdded = converter.getStoregroup(c, sgFromCell);
                        idSg = sgAdded.getId();
                    }
                }
//row.getCell(0)
                String storename = converter.getCellStringValue(row, 1);
                if (storename == null) {
                    System.out.println(i++ + "store null");
                    continue;
                }
                String street = converter.getCellStringValue(row, 4);
                long personId = 5004;
                String sql = "INSERT INTO store (name, street, person_id, storegroup_id, address_id) "
                        + "VALUES (?, ?, ?, ?, ?)";
                PreparedStatement statement = c.prepareStatement(sql);
                statement.setString(1, storename);
                statement.setString(2, street);
                statement.setLong(3, personId);
                statement.setLong(4, idSg);
                statement.setLong(5, idAdr);

                statement.executeUpdate();
                System.out.println(i++ + "done");
            }
            file.close();
            stmt.close();
            c.commit();
            c.close();
            System.out.println("everything done");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private String getCellStringValue(Row row, int index) {
        if (row.getCell(index) != null && row.getCell(index).getStringCellValue() != null && row.getCell(index).getStringCellValue() != "") {
            return row.getCell(index).getStringCellValue().trim();
        } else {
            return null;
        }
    }

    public Address getCity(Connection c, String city) throws SQLException {
        String selectTableSQL = "SELECT id, city from address WHERE city = ?";
        PreparedStatement statement = c.prepareStatement(selectTableSQL);
        statement.setString(1, city);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            Address address = new Address();
            address.setId(rs.getLong("id"));
            address.setCity(rs.getString("city"));
            return address;
        }

        return null;
    }

    public Storegroup getStoregroup(Connection c, String storegroupName) throws SQLException {
        String selectTableSQL = "SELECT id, name from storegroup WHERE name = ?";
        PreparedStatement statement = c.prepareStatement(selectTableSQL);
        statement.setString(1, storegroupName);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            Storegroup storegroup = new Storegroup();
            storegroup.setId(rs.getLong("id"));
            storegroup.setName(rs.getString("name"));
            return storegroup;
        }

        return null;
    }
}
