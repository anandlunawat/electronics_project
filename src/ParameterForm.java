import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ParameterForm extends JFrame {
	 private JLabel environmentFactorLabel, qualityFactorLabel, resistanceFactorLabel,
     		baseFailureRateLabel, capacitanceFactorLabel, seriesResistanceFactorLabel,
     temperatureFactorLabel, electricalStressFactorLabel;

	 private JTextField environmentFactorText, qualityFactorText, resistanceFactorText,
          baseFailureRateText, capacitanceFactorText, seriesResistanceFactorText,
          temperatureFactorText, electricalStressFactorText;
    
    private JButton calculateButton, clearButton;      
    
    public ParameterForm() {
        setTitle("Resistor and Capacitor");
        setSize(300, 280);
        setLocationRelativeTo(null); // center the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel resistorHeadingLabel = new JLabel("Resistor", JLabel.CENTER);
        resistorHeadingLabel.setFont(new Font("Verdana", Font.BOLD, 20));
        resistorHeadingLabel.setBounds(0, 10, 300, 30);

        environmentFactorLabel = new JLabel("Environment Factor:");
        environmentFactorLabel.setBounds(20, 50, 120, 20);
        qualityFactorLabel = new JLabel("Quality Factor:");
        qualityFactorLabel.setBounds(20, 80, 100, 20);
        resistanceFactorLabel = new JLabel("Resistance Factor:");
        resistanceFactorLabel.setBounds(20, 110, 120, 20);
        baseFailureRateLabel = new JLabel("Base Failure Rate:");
        baseFailureRateLabel.setBounds(20, 140, 120, 20);

        environmentFactorText = new JTextField();
        environmentFactorText.setBounds(150, 50, 120, 20);
        qualityFactorText = new JTextField();
        qualityFactorText.setBounds(150, 80, 120, 20);
        resistanceFactorText = new JTextField();
        resistanceFactorText.setBounds(150, 110, 120, 20);
        baseFailureRateText = new JTextField();
        baseFailureRateText.setBounds(150, 140, 120, 20);

        JLabel capacitorHeadingLabel = new JLabel("Capacitor", JLabel.CENTER);
        capacitorHeadingLabel.setFont(new Font("Verdana", Font.BOLD, 20));
        capacitorHeadingLabel.setBounds(0, 180, 300, 30);

        capacitanceFactorLabel = new JLabel("Capacitance Factor:");
        capacitanceFactorLabel.setBounds(20, 220, 120, 20);
        seriesResistanceFactorLabel = new JLabel("Series Resistance Factor:");
        seriesResistanceFactorLabel.setBounds(20, 250, 150, 20);

        capacitanceFactorText = new JTextField();
        capacitanceFactorText.setBounds(150, 220, 120, 20);
        seriesResistanceFactorText = new JTextField();
        seriesResistanceFactorText.setBounds(150, 250, 120, 20);

        calculateButton = new JButton("Calculate");
        calculateButton.setBounds(30, 400, 100, 30);
        clearButton = new JButton("Clear");
        clearButton.setBounds(150, 400, 100, 30);

        JPanel panel = new JPanel(null);
        panel.add(resistorHeadingLabel);
        panel.add(environmentFactorLabel);
        panel.add(environmentFactorText);
        panel.add(qualityFactorLabel);
        panel.add(qualityFactorText);
        panel.add(resistanceFactorLabel);
        panel.add(resistanceFactorText);
        panel.add(baseFailureRateLabel);
        panel.add(baseFailureRateText);

        panel.add(capacitorHeadingLabel);
        panel.add(capacitanceFactorLabel);
        panel.add(capacitanceFactorText);
        panel.add(seriesResistanceFactorLabel);
        panel.add(seriesResistanceFactorText);
        
        JLabel diodeHeadingLabel = new JLabel("Diode", JLabel.CENTER);
        diodeHeadingLabel.setFont(new Font("Verdana", Font.BOLD, 20));
        diodeHeadingLabel.setBounds(0, 290, 400, 30);

        temperatureFactorLabel = new JLabel("Temperature Factor:");
        temperatureFactorLabel.setBounds(20, 330, 150, 20);
        electricalStressFactorLabel = new JLabel("Electrical Stress Factor:");
        electricalStressFactorLabel.setBounds(20, 360, 180, 20);

        temperatureFactorText = new JTextField();
        temperatureFactorText.setBounds(180, 330, 120, 20);
        electricalStressFactorText = new JTextField();
        electricalStressFactorText.setBounds(190,360,160,20);
        
        panel.add(diodeHeadingLabel);
        panel.add(temperatureFactorLabel);
        panel.add(temperatureFactorText);
        panel.add(electricalStressFactorLabel);
        panel.add(electricalStressFactorText);

        panel.add(calculateButton);
        panel.add(clearButton);

        setContentPane(panel);
        
        calculateButton.addActionListener(e -> calculateResistor());
        clearButton.addActionListener(e -> clearForm());
    }
    
    private void calculateResistor() {
        double environmentFactor = Double.parseDouble(environmentFactorText.getText());
        double qualityFactor = Double.parseDouble(qualityFactorText.getText());
        double resistanceFactor = Double.parseDouble(resistanceFactorText.getText());
        double baseFailureRate = Double.parseDouble(baseFailureRateText.getText());

        double failure_rate = environmentFactor * qualityFactor * resistanceFactor * baseFailureRate;
        resistanceFactorText.setText(String.format("%.2f", failure_rate));
        
        // JDBC code to create table in database
        try {
            // Load the JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            // Establish a connection to the database
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/electronic_project", "root", "anandlunawat@99");

            // Create a Statement object
            Statement statement = connection.createStatement();

            // Insert the calculated resistor value into the table
            String sql = "INSERT INTO theoretical_values (resistor) VALUES (" + failure_rate + ")";
            statement.executeUpdate(sql);

            // Close the resources
            statement.close();
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void clearForm() {
        environmentFactorText.setText("");
        qualityFactorText.setText("");
        resistanceFactorText.setText("");
        baseFailureRateText.setText("");
        resistanceFactorText.setText("");
        capacitanceFactorText.setText("");
        seriesResistanceFactorText.setText("");
    }

    public static void main(String[] args) {
        ParameterForm parameterForm = new ParameterForm();
        parameterForm.setVisible(true);
    }
}
