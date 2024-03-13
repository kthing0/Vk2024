package dto;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;
import java.util.Scanner;

public class DatabaseConnector {
    public static Connection getConnection() throws SQLException, FileNotFoundException {
        String url = "jdbc:postgresql://localhost:5432/your_vk_test_db";
        Scanner auth = new Scanner(new FileReader("db_auth.txt"));
        String username = auth.nextLine().trim();
        String password = auth.nextLine().trim();
        return DriverManager.getConnection(url, username, password);
    }
}
