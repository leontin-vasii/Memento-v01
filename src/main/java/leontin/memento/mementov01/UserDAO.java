package leontin.memento.mementov01;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    // Method to insert a new user into the database
    public static String addUser(String username, String password, String email) {
        String sql = "INSERT INTO users(username, password, email) VALUES(?, ?, ?)";

        // Hash the password before storing it
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, hashedPassword);
            pstmt.setString(3, email);
            pstmt.executeUpdate();
            return "User created successfully";

        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE constraint failed")) {
                if (e.getMessage().contains("users.username")) {
                    return "Username already exists";
                } else if (e.getMessage().contains("users.email")) {
                    return "Email already registered";
                }
            }
            return "Error: " + e.getMessage();
        }
    }

    // Method to validate user login
    public static boolean validateLogin(String username, String password) {
        String sql = "SELECT password FROM users WHERE username = ?";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password");
                // Compare the stored hash with the entered password
                return BCrypt.checkpw(password, storedHash);
            } else {
                System.out.println("User not found.");
                return false;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


}
