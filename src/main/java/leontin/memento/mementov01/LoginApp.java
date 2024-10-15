package leontin.memento.mementov01;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class LoginApp extends Application {

    //Store users in a HashMap (later replace with a database)
    private final Map<String, String> users = new HashMap<>();

    @Override
    public void init() throws Exception {
        super.init();
        // Ensure the users table is created before the application starts
        Database.createUsersTable();
    }

    @Override
    public void start(Stage primaryStage) {

        //Create the login form
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        // Username label and field

        Label usernameLabel = new Label("Username");
        TextField usernameField = new TextField();
        gridPane.add(usernameLabel, 0, 0);
        gridPane.add(usernameField, 1, 0);

        //Password label and field
        Label passwordLabel = new Label("Password");
        PasswordField passwordField = new PasswordField();
        gridPane.add(passwordLabel, 0, 1);
        gridPane.add(passwordField, 1, 1);

        //Login button
        Button loginButton = new Button("Login");
        gridPane.add(loginButton, 1, 2);

        // Hyperlink for creating a new user
        Hyperlink createUserLink = new Hyperlink("Create New User");
        createUserLink.setOnAction(e -> openCreateUserWindow());
        gridPane.add(createUserLink, 0, 3);

        // Hyperlink for forgotten username/password
        Hyperlink forgotPasswordLink = new Hyperlink("Forgot Username/Password");
        forgotPasswordLink.setOnAction(e -> openForgotPasswordWindow());
        gridPane.add(forgotPasswordLink, 1, 3);

        //Handle login button click
        loginButton.setOnAction(actionEvent -> handleLogin(usernameField.getText(), passwordField.getText()));

        // Set up the scene and stage
        Scene scene = new Scene(gridPane, 600, 600);
        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    //Method to handle login logic
    private void handleLogin(String username, String password) {

        // Validate login credentials using the UserDAO method
        if (UserDAO.validateLogin(username, password)) {
            // If the credentials are valid
            showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome, " + username + "!");
        } else {
            // If credentials are invalid (either user doesn't exist or password is incorrect)
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid credentials.");
        }
    }


    // Method to open the 'Create New User' window
    private void openCreateUserWindow() {
        Stage createUserStage = new Stage();
        createUserStage.setTitle("Create New User");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        usernameField.setPrefWidth(200); // Set a preferred width

        Label passwordLabel = new Label("Password");
        PasswordField passwordField = new PasswordField();
        passwordField.setPrefWidth(200); // Set a preferred width

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        emailField.setPrefWidth(200); // Set a preferred width

        Button createButton = new Button("Create");

        gridPane.add(usernameLabel, 0, 0);
        gridPane.add(usernameField, 1, 0);
        gridPane.add(passwordLabel, 0, 1);
        gridPane.add(passwordField, 1, 1);
        gridPane.add(emailLabel, 0, 2);
        gridPane.add(emailField, 1, 2);
        gridPane.add(createButton, 1, 3);

        //Handle user creation
        createButton.setOnAction(actionEvent -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String email = emailField.getText();

            if (!isValidMail(email)) {
                showAlert(Alert.AlertType.ERROR, "Invalid Email", "Please enter a valid email address with an '@' and at least 5 characters.");
                return;
            }

            if (!username.isEmpty() && !password.isEmpty() && !email.isEmpty()) {
                String result = UserDAO.addUser(username, password, email);

                if (result.equals("User created successfully")) {
                    showAlert(Alert.AlertType.INFORMATION, "User Created", "User " + username + " created successfully!");
                    createUserStage.close();
                } else if (result.equals("Username already exists")) {
                    showAlert(Alert.AlertType.WARNING, "User Creation Failed", "Username '" + username + "' is already taken. Please choose a different username.");
                } else if (result.equals("Email already registered")) {
                    showAlert(Alert.AlertType.WARNING, "User Creation Failed", "Email '" + email + "' is already registered. Please use a different email.");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", result);
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Creation Failed", "Please fill in all fields.");
            }
        });

        Scene scene = new Scene(gridPane, 300, 150);
        createUserStage.setScene(scene);
        createUserStage.show();
    }

    // Method to open the 'Forgot Username/Password' window
    private void openForgotPasswordWindow() {
        Stage forgotPasswordStage = new Stage();
        forgotPasswordStage.setTitle("Forgot Username/Password");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        Label label = new Label("Enter your username to recover your account:");
        TextField usernameField = new TextField();
        Button submitButton = new Button("Submit");

        gridPane.add(label, 0, 0);
        gridPane.add(usernameField, 0, 1);
        gridPane.add(submitButton, 0, 2);

        //Handle password recovery
        submitButton.setOnAction(actionEvent -> {
            String username = usernameField.getText();
            if (users.containsKey(username)) {
                showAlert(Alert.AlertType.INFORMATION, "Password Recovery", "Your password is: " + users.get(username));
                forgotPasswordStage.close();
            } else {
                showAlert(Alert.AlertType.ERROR, "Recovery Failed", "Username does not exist.");
            }
        });

        Scene scene = new Scene(gridPane, 300, 150);
        forgotPasswordStage.setScene(scene);
        forgotPasswordStage.show();
    }

    // Helper method to show alert messages
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    //Helper method to validate the email
    private boolean isValidMail(String email) {
        return email.contains("@") && email.length() >= 5;
    }
}
