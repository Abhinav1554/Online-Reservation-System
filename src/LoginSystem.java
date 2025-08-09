import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class LoginSystem extends JFrame implements ActionListener {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginSystem() {
        setTitle("Online Reservation System - Login");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2));

        JLabel userLabel = new JLabel("Username:");
        JLabel passLabel = new JLabel("Password:");

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        loginButton = new JButton("Login");
        loginButton.addActionListener(this);

        add(userLabel);
        add(usernameField);
        add(passLabel);
        add(passwordField);
        add(new JLabel()); // Empty cell for spacing
        add(loginButton);

        setLocationRelativeTo(null); // Center on screen
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = usernameField.getText();
        String password = String.valueOf(passwordField.getPassword());

        if (checkLogin(username, password)) {
            JOptionPane.showMessageDialog(this, "Login Successful!");
            dispose(); // close login window
            new MainMenu();
            // Reservation form will open in Phase 2
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Username or Password!");
        }
    }

    private boolean checkLogin(String username, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2 && parts[0].equals(username) && parts[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error reading users file!");
        }
        return false;
    }

    public static void main(String[] args) {
        new LoginSystem();
    }
}