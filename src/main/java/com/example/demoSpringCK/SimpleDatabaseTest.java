package com.example.demoSpringCK;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class SimpleDatabaseTest {

    public static void main(String[] args) {
        try {
            Properties props = new Properties();
            InputStream input = SimpleDatabaseTest.class.getClassLoader()
                    .getResourceAsStream("application.properties");

            if (input == null) {
                throw new RuntimeException("Không tìm thấy application.properties");
            }

            props.load(input);

            String url = props.getProperty("spring.datasource.url");
            String user = props.getProperty("spring.datasource.username");
            String pass = props.getProperty("spring.datasource.password");

            if (url == null || user == null || pass == null) {
                System.err.println("✗ Thiếu config trong application.properties:");
                System.err.println("  url: " + url);
                System.err.println("  username: " + user);
                System.err.println("  password: " + pass);
                return;
            }

            Connection conn = DriverManager.getConnection(url, user, pass);
            System.out.println("✓ Kết nối thành công: " +
                    conn.getMetaData().getDatabaseProductName());
            conn.close();

        } catch (Exception e) {
            System.err.println("✗ Lỗi: " + e.getMessage());
            e.printStackTrace();
        }
    }
}