import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class SignUpPage extends JFrame implements ActionListener {

    // UI components
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton signUpButton;
    private JLabel messageLabel;

    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost/electronic_project";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "anandlunawat@99";

    public SignUpPage() {
        // Set the frame properties
        setTitle("Sign Up Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a panel for the input fields
        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create the input fields and labels
        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameField = new JTextField();
        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameField = new JTextField();
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        inputPanel.add(firstNameLabel);
        inputPanel.add(firstNameField);
        inputPanel.add(lastNameLabel);
        inputPanel.add(lastNameField);
        inputPanel.add(usernameLabel);
        inputPanel.add(usernameField);
        inputPanel.add(passwordLabel);
        inputPanel.add(passwordField);

        // Create a button to submit the sign up details
        signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(this);

        // Add the input panel and sign up button to the frame
        getContentPane().add(inputPanel, BorderLayout.CENTER);
        getContentPane().add(signUpButton, BorderLayout.SOUTH);

        // Create a label to display messages to the user
        messageLabel = new JLabel("");
        messageLabel.setForeground(Color.RED);
        getContentPane().add(messageLabel, BorderLayout.NORTH);

        // Pack the frame and set its location to the center of the screen
        pack();
        setLocationRelativeTo(null);

        // Show the frame
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == signUpButton) {
            // Get the user details from the input fields
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            // Validate that all fields are filled in
            if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || password.isEmpty()) {
                messageLabel.setText("Please fill in all fields");
                return;
            }

            // Insert the user details into the database
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String sql = "INSERT INTO users (first_name, last_name, username, password) VALUES (?, ?, ?, ?)";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, firstName);
                statement.setString(2, lastName);
                statement.setString(3, username);
                statement.setString(4, password);
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    messageLabel.setText("User signed up successfully");
                    ParameterForm parameterForm = new ParameterForm();
                    parameterForm.setVisible(true);
                    dispose();
                } else {
                    messageLabel.setText("Failed to sign up user");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                messageLabel.setText("An error occurred while signing up user");
            }
        }
    }

    public static void main(String args []) {
    	new SignUpPage();
    }
}
