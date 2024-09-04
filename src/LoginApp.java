import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import git .simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;

public class LoginApp {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                LoginApp window = new LoginApp();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public LoginApp() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new GridBagLayout());

        // Load logo
        ImageIcon logo = new ImageIcon("path/to/university_logo.png"); // ruta al logo
        JLabel logoLabel = new JLabel(logo);
        GridBagConstraints gbc_logo = new GridBagConstraints();
        gbc_logo.insets = new Insets(0, 0, 5, 5);
        gbc_logo.gridx = 1;
        gbc_logo.gridy = 0;
        frame.getContentPane().add(logoLabel, gbc_logo);

        JLabel lblUsername = new JLabel("Username:");
        GridBagConstraints gbc_lblUsername = new GridBagConstraints();
        gbc_lblUsername.anchor = GridBagConstraints.EAST;
        gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
        gbc_lblUsername.gridx = 1;
        gbc_lblUsername.gridy = 1;
        frame.getContentPane().add(lblUsername, gbc_lblUsername);

        usernameField = new JTextField();
        GridBagConstraints gbc_usernameField = new GridBagConstraints();
        gbc_usernameField.insets = new Insets(0, 0, 5, 0);
        gbc_usernameField.fill = GridBagConstraints.HORIZONTAL;
        gbc_usernameField.gridx = 2;
        gbc_usernameField.gridy = 1;
        frame.getContentPane().add(usernameField, gbc_usernameField);
        usernameField.setColumns(10);

        JLabel lblPassword = new JLabel("Password:");
        GridBagConstraints gbc_lblPassword = new GridBagConstraints();
        gbc_lblPassword.anchor = GridBagConstraints.EAST;
        gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
        gbc_lblPassword.gridx = 1;
        gbc_lblPassword.gridy = 2;
        frame.getContentPane().add(lblPassword, gbc_lblPassword);

        passwordField = new JPasswordField();
        GridBagConstraints gbc_passwordField = new GridBagConstraints();
        gbc_passwordField.insets = new Insets(0, 0, 5, 0);
        gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
        gbc_passwordField.gridx = 2;
        gbc_passwordField.gridy = 2;
        frame.getContentPane().add(passwordField, gbc_passwordField);

        JButton btnLogin = new JButton("Login");
        GridBagConstraints gbc_btnLogin = new GridBagConstraints();
        gbc_btnLogin.insets = new Insets(0, 0, 0, 5);
        gbc_btnLogin.gridx = 2;
        gbc_btnLogin.gridy = 3;
        frame.getContentPane().add(btnLogin, gbc_btnLogin);

        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Cargar las credenciales desde el archivo JSON
                JSONParser parser = new JSONParser();
                try (FileReader reader = new FileReader("credentials.json")) {
                    JSONObject jsonObject = (JSONObject) parser.parse(reader);

                    String storedUsername = (String) jsonObject.get("username");
                    String storedPassword = (String) jsonObject.get("password");

                    // Verificar las credenciales
                    if (storedUsername.equals(username) && storedPassword.equals(password)) {
                        JOptionPane.showMessageDialog(frame, "Login Successful!");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Invalid Credentials!");
                    }

                } catch (IOException | ParseException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error reading credentials file.");
                }
            }
        });
    }
}
