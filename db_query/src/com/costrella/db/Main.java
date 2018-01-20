package com.costrella.db;

import com.costrella.db.compress.CompressJPEGFile;
import com.costrella.db.model.Raport;

import java.io.*;
import java.sql.*;
import java.util.Iterator;
import java.util.LinkedList;

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

        // get the data
        populateListOfRaports(conn, listOfBlogs);

        // print the results
        printRaports(listOfBlogs);
    }

    private void printRaports(LinkedList listOfBlogs) {
        Iterator it = listOfBlogs.iterator();
        while (it.hasNext()) {
            Raport raport = (Raport) it.next();
            System.out.println("id: " + raport.id + ", desc: " + raport.description);
        }
    }

    private void populateListOfRaports(Connection conn, LinkedList listOfBlogs)  {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT id, description, foto_1 FROM raport WHERE id = 36414 ORDER BY id");
            while (rs.next()) {
                Raport raport = new Raport();
                raport.id = rs.getLong("id");
                raport.description = rs.getString("description");
                raport.foto1 = rs.getBytes("foto_1");
//                try {
//                    BufferedImage img = ImageIO.read(new ByteArrayInputStream(raport.foto1));
//                    img.
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                OutputStream out = null;
                try {
                    out = new BufferedOutputStream(new FileOutputStream(pathBefore));
                    out.write(raport.foto1);
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
                    compressJPEGFile.compress();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                listOfBlogs.add(raport);
            }
            rs.close();
            st.close();
        } catch (SQLException se) {
            System.err.println("Threw a SQLException creating the list of blogs.");
            System.err.println(se.getMessage());
        }
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
