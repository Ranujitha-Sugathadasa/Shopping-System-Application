package GUI;

import Console.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Login extends JFrame implements ActionListener {

    private final JTextField usernameInput;
    private final JTextField passwordInput;
    public Login() {
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();

        //Setting up labels and text-fields
        JLabel usernameLabel = new JLabel("Username");
        JLabel passwordLabel = new JLabel("Password");
        usernameInput = new JTextField();
        passwordInput = new JTextField();

        //Setting up signup button
        JLabel signup = new JLabel("Don't have an account?");
        signup.setAlignmentX(Component.CENTER_ALIGNMENT);
        signup.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        signup.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new SignUp();
                dispose();
            }
        });

        JButton loginBtn = new JButton("login");
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginBtn.addActionListener(this);

        //Setting layout and adding components
        panel1.setLayout(new GridLayout(3,2));
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
        panel1.add(new JLabel());
        panel1.add(new JLabel());
        panel1.add(usernameLabel);
        panel1.add(usernameInput);
        panel1.add(passwordLabel);
        panel1.add(passwordInput);
        panel2.add(loginBtn);
        panel2.add(signup);

        //Setting up frame
        setTitle("Westminster Shopping Centre");
        add(panel1);
        add(new JPanel());
        add(panel2);
        setSize(380,200);
        setLayout(new GridLayout(3,1));
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (usernameInput.getText().isEmpty() || passwordInput.getText().isEmpty())
            infoBox("Please enter credentials", "");
        else {
            String username = usernameInput.getText();
            String password = passwordInput.getText();
            if (User.getUserDetails().containsKey(username) && User.getUserDetails().get(username).equals(password)) {
                for (User u: User.getUserList()) {
                    if (u.getUsername().equals(username))
                        new HomePage(u);
                }
                //new HomePage();
                dispose();
            }
            else if (!(User.getUserDetails().containsKey(username)))
                infoBox("Account doesn't exist. Sign up","");
            else
                infoBox("Incorrect password","");
        }
    }
    public void infoBox(String infoMessage, String titleBar) {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
}
