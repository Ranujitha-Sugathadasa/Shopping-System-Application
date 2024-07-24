package GUI;


import Console.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUp extends JFrame implements ActionListener {

    private final JTextField usernameInput;
    private final JTextField passwordInput;
    public SignUp() {

        JPanel titlePanel = new JPanel();
        JLabel title = new JLabel("Westminster Shopping Centre");

        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();

        //Setting up labels and text-fields
        JLabel username = new JLabel("Username");
        JLabel password = new JLabel("Password");
        usernameInput = new JTextField();
        passwordInput = new JTextField();

        JButton signInBtn = new JButton("signup");
        signInBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        signInBtn.addActionListener(this);

        //Setting layout and adding components
        panel1.setLayout(new GridLayout(2,2));
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
        panel1.add(username);
        panel1.add(usernameInput);
        panel1.add(password);
        panel1.add(passwordInput);
        panel2.add(signInBtn);

        //Setting layout managers
        titlePanel.setLayout(new FlowLayout());

        //Adding to panel
        titlePanel.add(title);

        //Setting up frame
        setTitle("Westminster Shopping Centre");
        add(titlePanel);
        add(panel1);
        add(new JPanel());
        add(panel2);
        setSize(380,200);
        setLayout(new GridLayout(4,1));
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (usernameInput.getText().isEmpty() || passwordInput.getText().isEmpty()) {
            infoBox("Please enter credentials","");
        }
        else {
            String username = usernameInput.getText();
            String password = passwordInput.getText();
            if (!(User.getUserDetails().containsKey(username))) {
                User person = new User(username, password);
                User.getUserList().add(person);
                User.getUserDetails().put(username, password);
                User.writeToFile();
                new HomePage(person);
                dispose();
            }
            else {
                infoBox("Account already exists, Please login", "");
                new Login();
            }
        }
    }

    public void infoBox(String infoMessage, String titleBar) {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
}
