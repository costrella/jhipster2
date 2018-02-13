package com.costrella.db;

import com.costrella.db.compress.CompressJPEGFile;
import com.costrella.db.model.Raport;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Iterator;
import java.util.LinkedList;

import static com.costrella.db.compress.CompressJPEGFile.pathArchive;
import static com.costrella.db.compress.CompressJPEGFile.pathBefore;

public class Main {
    CompressJPEGFile compressJPEGFile;

    class Blog {
        public int id;
        public String subject;
        public String permalink;
    }

    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        compressJPEGFile = new CompressJPEGFile();
        Connection conn = null;
        LinkedList listOfBlogs = new LinkedList();

        // connect to the database
        conn = connectToDatabaseOrDie();
        int id = 36408;
        // get the data
        for(int i = 7000; i<=15672; i++){
            startQuery(conn, i);
        }

        // print the results
    }

    private void printRaports(LinkedList listOfBlogs) {
        Iterator it = listOfBlogs.iterator();
        while (it.hasNext()) {
            Raport raport = (Raport) it.next();
            System.out.println("id: " + raport.id + ", desc: " + raport.description);
        }
    }

    //UPDATE table_name
//SET column1 = value1, column2 = value2, ...
//    WHERE condition;
    private void startQuery(Connection conn, int id) {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT id, foto_1, foto_2, foto_3, description FROM raport WHERE id = " + id + " ORDER BY id");
            while (rs.next()) {
                Raport raport = new Raport();
                raport.id = rs.getLong("id");
                raport.foto1 = rs.getBytes("foto_1");
                raport.foto2 = rs.getBytes("foto_2");
                raport.foto3 = rs.getBytes("foto_3");
                raport.description = rs.getString("description");

                compressAndUpdate(raport.foto1, "foto_1", conn, id, raport.description);
                compressAndUpdate(raport.foto2, "foto_2", conn, id, raport.description);
                compressAndUpdate(raport.foto3, "foto_3", conn, id, raport.description);
            }
            rs.close();
            st.close();
        } catch (SQLException se) {
            System.err.println("Threw a SQLException creating the list of blogs.");
            System.err.println(se.getMessage());
        }
    }

    void compressAndUpdate(byte[] photo, String fieldName, Connection conn, int id, String desc) {
        if (photo != null) {
            OutputStream out = null;
            try {
                new File(pathArchive+id).mkdirs();
                out = new BufferedOutputStream(new FileOutputStream(pathArchive+id+"\\"+fieldName+".jpg"));
                out.write(photo);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (out != null) try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try {
//                Path path = Paths.get("C:\\mkostrzewa\\projects\\cechini\\a.jpg");
//                byte[] data = Files.readAllBytes(path);

//                byte[] compressed = compressJPEGFile.compress();

                String sql = "UPDATE raport SET " + fieldName + " = NULL, description = (?) WHERE id=" + id + "";
                PreparedStatement pstmt = conn.prepareStatement(sql);

                String za = " *[za]";
                pstmt.setString(1, desc+za);
                pstmt.executeUpdate();
                System.out.print(id + ",");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void insert() throws SQLException {
        Connection conn = connectToDatabaseOrDie();
        Statement stmt = conn.createStatement();


        String sql = "INSERT INTO survey (name) VALUES(?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setBytes(1, "asdfasdf".getBytes());
        pstmt.executeUpdate();
    }

    private Connection connectToDatabaseOrDie() {
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://137.v.tld.pl:5432/pg137_c1_5a";
            conn = DriverManager.getConnection(url, "pg137_c1_5a", "Aasdasd1!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(2);
        }
        return conn;
    }

}
