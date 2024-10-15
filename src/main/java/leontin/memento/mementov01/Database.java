package leontin.memento.mementov01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    private static final String DB_URL = "jdbc:sqlite:users.db";

    // Establish the database connection
    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    // Create the users table
    public static void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT NOT NULL UNIQUE," +
                "password TEXT NOT NULL," +
                "email TEXT NOT NULL UNIQUE" +  // Corrected spacing and syntax here
                ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Users table created.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


}
