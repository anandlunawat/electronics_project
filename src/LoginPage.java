import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginPage extends JFrame implements ActionListener {
    private JLabel userLabel, passwordLabel, messageLabel;
    private JTextField userText;
    private JPasswordField passwordText;
    private JButton loginButton, cancelButton;

    public LoginPage() {
        setTitle("Login Page");
        setSize(300, 200);
        setLocationRelativeTo(null); // center the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        userLabel = new JLabel("Username:");
        passwordLabel = new JLabel("Password:");
        userText = new JTextField();
        passwordText = new JPasswordField();
        loginButton = new JButton("Login");
        cancelButton = new JButton("Cancel");
        messageLabel = new JLabel("");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));
        panel.add(userLabel);
        panel.add(userText);
        panel.add(passwordLabel);
        panel.add(passwordText);
        panel.add(loginButton);
        panel.add(cancelButton);

        JPanel messagePanel = new JPanel();
        messagePanel.add(messageLabel);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        add(messagePanel, BorderLayout.SOUTH);

        loginButton.addActionListener(this);
        cancelButton.addActionListener(this);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == loginButton) {
            String username = userText.getText();
            String password = new String(passwordText.getPassword());

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost/electronic_project", "root", "anandlunawat@99");
                Statement stmt = con.createStatement();
                String sql = "SELECT * FROM users WHERE username='" + username + "' AND password='" + password + "'";
                ResultSet rs = stmt.executeQuery(sql);
                if (rs.next()) {
                    messageLabel.setText("Login successful!");
                    ParameterForm parameterForm = new ParameterForm();
                    parameterForm.setVisible(true);
                    dispose();
                } else {
                    messageLabel.setText("Invalid username or password");
                }
                con.close();
            } catch (Exception e) {
                messageLabel.setText("Error: " + e.getMessage());
            }
        } else if (ae.getSource() == cancelButton) {
            dispose(); // close the window
        }
    }

    public static void main(String[] args) {
        LoginPage loginPage = new LoginPage();
        loginPage.setVisible(true);
    }
}
