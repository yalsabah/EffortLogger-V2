import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class EffortLoggerDemo extends Application {

    // Database URL and credentials (Replace with your actual database credentials)
    private static final String DB_URL = "jdbc:postgresql://sample-data.popsql.io:5432/world";
    private static final String USER = "yalsabah";
    private static final String PASS = "you19sif2";

    private Stage mainStage;
    private Map<String, String> userCredentials = new HashMap<>();
    private Map<String, String> exportedData = new HashMap<>();

    @Override
    public void start(Stage primaryStage) {
        mainStage = primaryStage;
        mainStage.setTitle("EffortLogger V2");
        LoginPage loginPage = new LoginPage();
        mainStage.setScene(loginPage.getScene(mainStage));
        mainStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    // Define the LoginPage class for user login
    private class LoginPage {
        public Scene getScene(Stage mainStage) {
            GridPane grid = new GridPane();
            grid.setPadding(new Insets(10, 10, 10, 10));
            grid.setVgap(5);
            grid.setHgap(5);
    
            TextField usernameField = new TextField();
            usernameField.setPromptText("Enter your username");
            GridPane.setConstraints(usernameField, 0, 0);
            grid.getChildren().add(usernameField);
    
            PasswordField passwordField = new PasswordField();
            passwordField.setPromptText("Enter your password");
            GridPane.setConstraints(passwordField, 0, 1);
            grid.getChildren().add(passwordField);
    
            Button loginButton = new Button("Login");
            GridPane.setConstraints(loginButton, 0, 2);
            grid.getChildren().add(loginButton);
    
            Button signUpButton = new Button("Sign Up");
            GridPane.setConstraints(signUpButton, 1, 2);
            grid.getChildren().add(signUpButton);
    
            // Event handler for the login button
            loginButton.setOnAction(e -> {
                String username = usernameField.getText();
                String password = passwordField.getText();
    
                if (username.trim().isEmpty() || password.trim().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "Username and password fields cannot be empty.");
                } else if (userCredentials.containsKey(username) && userCredentials.get(username).equals(password)) {
                    MainMenu mainMenu = new MainMenu();
                    mainStage.setScene(mainMenu.getScene(mainStage));
                } else {
                    showAlert(Alert.AlertType.ERROR, "Invalid username or password. Please try again.");
                }
            });
    
            // Event handler for the sign-up button
            signUpButton.setOnAction(e -> {
                SignUpPage signUpPage = new SignUpPage();
                mainStage.setScene(signUpPage.getScene(mainStage));
            });
    
            return new Scene(grid, 300, 200);
        }
    
        private void showAlert(Alert.AlertType alertType, String content) {
            Alert alert = new Alert(alertType, content);
            alert.showAndWait();
        }
    }
    
    // Define the SignUpPage class for user registration
    private class SignUpPage {
        public Scene getScene(Stage mainStage) {
            GridPane grid = new GridPane();
            grid.setPadding(new Insets(10, 10, 10, 10));
            grid.setVgap(5);
            grid.setHgap(5);

            TextField usernameField = new TextField();
            usernameField.setPromptText("Enter your username");
            GridPane.setConstraints(usernameField, 0, 0);
            grid.getChildren().add(usernameField);

            PasswordField passwordField = new PasswordField();
            passwordField.setPromptText("Enter your password");
            GridPane.setConstraints(passwordField, 0, 1);
            grid.getChildren().add(passwordField);

            PasswordField repeatPasswordField = new PasswordField();
            repeatPasswordField.setPromptText("Repeat your password");
            GridPane.setConstraints(repeatPasswordField, 0, 2);
            grid.getChildren().add(repeatPasswordField);

            Button registerButton = new Button("Register");
            GridPane.setConstraints(registerButton, 0, 3);
            grid.getChildren().add(registerButton);

            Button backButton = new Button("Back");
            GridPane.setConstraints(backButton, 1, 3);
            grid.getChildren().add(backButton);

            // Event handler for the registration button
            registerButton.setOnAction(e -> {
                if (usernameField.getText().trim().isEmpty() || passwordField.getText().trim().isEmpty() || repeatPasswordField.getText().trim().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "All fields must be filled out.");
                    alert.show();
                } else if (!passwordField.getText().equals(repeatPasswordField.getText())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Passwords do not match.");
                    alert.show();
                }
                else {
                    userCredentials.put(usernameField.getText(), passwordField.getText());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Registration successful.");
                    alert.show();
                    MainMenu mainMenu = new MainMenu();
                    mainStage.setScene(mainMenu.getScene(mainStage));
                    }
                });

                // Event handler for the back button to return to login page
                backButton.setOnAction(e -> {
                    LoginPage loginPage = new LoginPage();
                    mainStage.setScene(loginPage.getScene(mainStage));
                });
    
                return new Scene(grid, 300, 250);
            }
    }

    // Define the MainMenu class for the main menu
    private class MainMenu {
        public Scene getScene(Stage mainStage) {
            VBox vbox = new VBox();
            vbox.setPadding(new Insets(10, 10, 10, 10));
            vbox.setSpacing(5);

            Button effortLoggerV2Button = new Button("EffortLogger V2");
            vbox.getChildren().add(effortLoggerV2Button);

            Button logsButton = new Button("Logs");
            vbox.getChildren().add(logsButton);

            Button historyLogsButton = new Button("History Logs");
            vbox.getChildren().add(historyLogsButton);

            Button signOutButton = new Button("Sign Out");
            vbox.getChildren().add(signOutButton);

            // Event handler for the EffortLogger V2 button
            effortLoggerV2Button.setOnAction(e -> {
                EffortLoggerConsole console = new EffortLoggerConsole();
                mainStage.setScene(console.createConsoleScene());
            });

            // Event handler for the sign-out button
            signOutButton.setOnAction(e -> {
                LoginPage loginPage = new LoginPage();
                mainStage.setScene(loginPage.getScene(mainStage));
            });

            return new Scene(vbox, 300, 200);
        }    
    }

    // Define the EffortLoggerConsole class for additional functionality
    private class EffortLoggerConsole {
        public Scene createConsoleScene() {
            VBox vbox = new VBox();
            vbox.setPadding(new Insets(10, 10, 10, 10));
            vbox.setSpacing(5);

            Button importButton = new Button("Import Data from External System");
            vbox.getChildren().add(importButton);

            Button exportButton = new Button("Export Data to External System");
            vbox.getChildren().add(exportButton);

            Button checkDataConsistencyButton = new Button("Check Data Consistency");
            vbox.getChildren().add(checkDataConsistencyButton);

            TextArea logArea = new TextArea();
            logArea.setEditable(false);
            logArea.setPrefHeight(150);
            vbox.getChildren().add(logArea);

            // Event handler for the import button
            importButton.setOnAction(event -> {
                Dialog<Pair<String, String>> dialog = new Dialog<>();
                dialog.setTitle("Import Data");

                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20, 150, 10, 10));

                TextField username = new TextField();
                username.setPromptText("Username");
                PasswordField password = new PasswordField();
                password.setPromptText("Password");

                grid.add(new Label("Username:"), 0, 0);
                grid.add(username, 1, 0);
                grid.add(new Label("Password:"), 0, 1);
                grid.add(password, 1, 1);

                dialog.getDialogPane().setContent(grid);
                ButtonType importButtonType = new ButtonType("Import", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(importButtonType, ButtonType.CANCEL);

                dialog.setResultConverter(dialogButton -> {
                    if (dialogButton == importButtonType) {
                        return new Pair<>(username.getText(), password.getText());
                    }
                    return null;
                });

                dialog.showAndWait().ifPresent(credentials -> {
                    if (userCredentials.getOrDefault(credentials.getKey(), "").equals(credentials.getValue())) {
                        logArea.appendText("Import successful. User authenticated.\n");
                    } else {
                        logArea.appendText("Import failed. Username or password is incorrect.\n");
                    }
                });
            });

            // Event handler for the export button
            exportButton.setOnAction(event -> {
                if (!userCredentials.isEmpty()) {
                    String username = userCredentials.keySet().iterator().next(); // Get the first user for this example
                    String password = userCredentials.get(username);
                    exportedData.put(username, password);
                    logArea.appendText("Data exported. Username: " + username + "\n");
                } else {
                    logArea.appendText("No data to export.\n");
                }
            });

            // Event handler for the check data consistency button
            checkDataConsistencyButton.setOnAction(event -> {
                if (exportedData.equals(userCredentials)) {
                    logArea.appendText("Data consistency check: No discrepancies found.\n");
                } else {
                    logArea.appendText("Data consistency check: Discrepancies found!\n");
                }
            });

            Button backToMainMenuButton = new Button("Back to Main Menu");
            vbox.getChildren().add(backToMainMenuButton);

            // Event handler for the back to main menu button
            backToMainMenuButton.setOnAction(event -> {
                MainMenu mainMenu = new MainMenu();
                mainStage.setScene(mainMenu.getScene(mainStage));
            });

            return new Scene(vbox, 300, 250);        
        }
    }

    // Helper class to hold username and password pair
    public static class Pair<K, V> {
        private K key;
        private V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() { return key; }
        public V getValue() { return value; }
    }
}
