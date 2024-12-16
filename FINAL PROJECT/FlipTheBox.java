import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class FlipTheBox {
    private static UserAccounts ua = new UserAccounts();
    public static void main(String[] args){
        UserAccounts.loadFromFile();
        //FRAME 
        JFrame frame = new JFrame("FLIP THE BOX");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //ICON
        ImageIcon frameIcon = new ImageIcon("Icon.png"); 
        frame.setIconImage(frameIcon.getImage());

        //BACKGROUND
        ImageIcon backgroundIcon = new ImageIcon("bg.png");
        Image backgroundImage = backgroundIcon.getImage().getScaledInstance(Toolkit.getDefaultToolkit().getScreenSize().width,
            Toolkit.getDefaultToolkit().getScreenSize().height, Image.SCALE_SMOOTH);

        backgroundIcon = new ImageIcon(backgroundImage);
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setBounds(0,0, Toolkit.getDefaultToolkit().getScreenSize().width, 
            Toolkit.getDefaultToolkit().getScreenSize().height);
        
        //LOG IN 
        JPanel logInpanel = new JPanel();
        logInpanel.setBounds(550,250,300,300); //log in ni sya kanang nas tunga nga panel
        logInpanel.setLayout(null);
        logInpanel.setBackground(Color.WHITE);
        logInpanel.setBorder(BorderFactory.createMatteBorder(4,4,4,4,Color.ORANGE)); 

        JLabel title = new JLabel("LOG IN");
        title.setFont(new Font("Baskerville", Font.PLAIN,30));
        title.setBounds(108,25,200,30);
        
        JLabel username = new JLabel("Username: ");
        username.setBounds(20, 80, 80, 30);
        username.setHorizontalAlignment(SwingConstants.CENTER);

        JTextField user = new JTextField(10);
        user.setBounds(120, 80, 150, 30);

        JLabel password = new JLabel("Password: ");
        password.setBounds(20, 130, 80, 30);
        password.setHorizontalAlignment(SwingConstants.CENTER);

        JPasswordField pass = new JPasswordField(10);
        pass.setBounds(120, 130, 150, 30);
    
        JCheckBox showPass = new JCheckBox("Show Password");
        showPass.setBounds(25,180,130,20);
        showPass.addActionListener(new ShowPassword(pass));
             
        JLabel forgotPass = new JLabel("<html><u>Forgot Password?</u></html>"); 
        forgotPass.setBounds(170,180,175,20);
        forgotPass.setForeground(Color.BLUE);
        forgotPass.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        forgotPass.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFrame forgotPasswordFrame = new JFrame("Forgot Password");
                forgotPasswordFrame.setSize(250, 300);
                forgotPasswordFrame.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
                forgotPasswordFrame.setLocationRelativeTo(null);
                forgotPasswordFrame.setResizable(false);
                forgotPasswordFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                ImageIcon frameIcon = new ImageIcon("Icon.png"); 
                forgotPasswordFrame.setIconImage(frameIcon.getImage());
        
                // Input Fields
                JLabel oldUsernameLabel = new JLabel("Old Username: ");
                JTextField oldUsernameField = new JTextField(15);
        
                JLabel oldPasswordLabel = new JLabel("Old Password: ");
                JPasswordField oldPasswordField = new JPasswordField(15);
        
                JLabel newPasswordLabel = new JLabel("New Password: ");
                JPasswordField newPasswordField = new JPasswordField(15);
        
                // Change Password Button
                JButton changePasswordButton = new JButton("Change Password");
                changePasswordButton.addActionListener(ev -> {
                    String oldUsername = oldUsernameField.getText();
                    String oldPassword = new String(oldPasswordField.getPassword());
                    String newPassword = new String(newPasswordField.getPassword());

                    if (oldUsername.trim().isEmpty() || oldPassword.trim().isEmpty() || newPassword.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(forgotPasswordFrame, "Fields cannot be empty!");
                        oldUsernameField.setText("");
                        oldPasswordField.setText("");
                        newPasswordField.setText("");
                    }

                    if (UserAccounts.login(oldUsername, oldPassword)){
                        UserAccounts.updatePassword(oldUsername, newPassword);
                        JOptionPane.showMessageDialog(forgotPasswordFrame, "Password successfully updated!");
                        forgotPasswordFrame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(forgotPasswordFrame, "Invalid old username or password!");
                        oldUsernameField.setText("");
                        oldPasswordField.setText("");
                        newPasswordField.setText("");
                    }
                }); 
        
                // Add components to frame
                forgotPasswordFrame.add(oldUsernameLabel);
                forgotPasswordFrame.add(oldUsernameField);
                forgotPasswordFrame.add(oldPasswordLabel);
                forgotPasswordFrame.add(oldPasswordField);
                forgotPasswordFrame.add(newPasswordLabel);
                forgotPasswordFrame.add(newPasswordField);
                forgotPasswordFrame.add(changePasswordButton);
        
                forgotPasswordFrame.setVisible(true);
            }
        });
        


        JButton login = new JButton("SIGN IN"); 
        login.setBounds(50, 220, 100, 40);
        login.addActionListener(new Login(user, pass, frame));

        JButton createAccount = new JButton("SIGN UP");
        createAccount.setBounds(160, 220, 100, 40);
        createAccount.addActionListener(new CreateAccountAction(user, pass, frame));

        // Add components to the login panel
        logInpanel.add(title);
        logInpanel.add(username);
        logInpanel.add(user);
        logInpanel.add(password);
        logInpanel.add(pass);
        logInpanel.add(showPass);
        logInpanel.add(forgotPass);
        logInpanel.add(login);
        logInpanel.add(createAccount);

        logInpanel.setVisible(true);
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width,
            Toolkit.getDefaultToolkit().getScreenSize().height);

        layeredPane.add(backgroundLabel, Integer.valueOf(0)); 
        layeredPane.add(logInpanel, Integer.valueOf(1));

        frame.setContentPane(layeredPane);
        frame.setLayout(null);

        frame.setVisible(true);
    }
}
class ShowPassword implements ActionListener{
    private JPasswordField password;

    public ShowPassword(JPasswordField password){
        this.password = password;
    }

    public void actionPerformed(ActionEvent e) {
        JCheckBox checkBox = (JCheckBox) e.getSource();
        if (checkBox.isSelected()) {
            password.setEchoChar((char) 0); //to show its password
        } else {
            password.setEchoChar('*'); 
        }
    }
}
class ForgotPassword implements ActionListener{
    private JTextField userF;
    private JPasswordField passwordF;
    private JFrame frame;

    public ForgotPassword(JTextField userF, JPasswordField passwordF, JFrame frame) {
        this.userF = userF;
        this.passwordF = passwordF;
        this.frame = frame;
    }

    public void actionPerformed(ActionEvent e) {

        JFrame ccaFrame = new JFrame("Create Account");
        ccaFrame.setSize(300,300);
        ccaFrame.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        ccaFrame.setLocationRelativeTo(null);
        ccaFrame.setResizable(true);
        ccaFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JLabel usernameLabel = new JLabel("Username: ");
        JTextField userField = new JTextField(10);
        
        JLabel passwordLabel = new JLabel("Password: ");
        JPasswordField passwordField = new JPasswordField(10);
    
        JButton saveButton = new JButton("Create Account");
        saveButton.setLayout(new FlowLayout(FlowLayout.CENTER));
        saveButton.addActionListener(ev -> {
            String username = userField.getText();
            String password = new String(passwordField.getPassword());

            if (UserAccounts.contains(username,password)){
                JOptionPane.showMessageDialog(ccaFrame, "This account already exists!");
                UserAccounts.deleteContact(username, password);
            } else {
                UserAccounts.add(username, password);
                JOptionPane.showMessageDialog(ccaFrame, "Account successfully created!");
                userField.setText("");
                passwordField.setText("");
                ccaFrame.dispose(); //this means closing the create account frame 
            }
            frame.setVisible(true); //then going back to log in frame
        });

        ccaFrame.add(usernameLabel);
        ccaFrame.add(userField);
        ccaFrame.add(passwordLabel);
        ccaFrame.add(passwordField);
        ccaFrame.add(saveButton); 

        ccaFrame.setVisible(true);

    }
}

class Login implements ActionListener{
    private JTextField userF;
    private JPasswordField passwordF;
    private JFrame frame;

    public Login(JTextField userF, JPasswordField passwordF, JFrame frame) {
        this.userF = userF;
        this.passwordF = passwordF;
        this.frame = frame;
    }

    public void actionPerformed(ActionEvent e) {
        String username = userF.getText();
        String password = new String(passwordF.getPassword());

        if (UserAccounts.login(username, password)){
            JOptionPane.showMessageDialog(frame, "Log in Successful");
            frame.dispose();
            new Homepage(); // proceed na sya sa home page
        } else {
            JOptionPane.showMessageDialog(frame, "This account does not exist!");
        }
        userF.setText("");
        passwordF.setText("");
        
    }
}
class CreateAccountAction implements ActionListener{
    private JTextField userF;
    private JPasswordField passwordF;
    private JFrame frame;

    public CreateAccountAction(JTextField userF, JPasswordField passwordF, JFrame frame) {
        this.userF = userF;
        this.passwordF = passwordF;
        this.frame = frame;
    }

    public void actionPerformed(ActionEvent e) {

        JFrame ccaFrame = new JFrame("Create Account");
        ccaFrame.setSize(300,250);
        ccaFrame.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        ccaFrame.setLocationRelativeTo(null);
        ccaFrame.setResizable(false);
        ccaFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        ImageIcon frameIcon = new ImageIcon("Icon.png"); 
        ccaFrame.setIconImage(frameIcon.getImage());
        
        JLabel usernameLabel = new JLabel("Username: ");
        JTextField userField = new JTextField(10);
        
        JLabel passwordLabel = new JLabel("Password: ");
        JPasswordField passwordField = new JPasswordField(10);
    
        JButton saveButton = new JButton("Create Account");
        saveButton.setLayout(new FlowLayout(FlowLayout.CENTER));
        saveButton.addActionListener(ev -> {
            String username = userField.getText();
            String password = new String(passwordField.getPassword());

            if (username.trim().isEmpty() || password.trim().isEmpty()) {
                JOptionPane.showMessageDialog(ccaFrame, "Username or Password cannot be empty.");
                return;
            }
            
             else if (UserAccounts.contains(username,password)){
                JOptionPane.showMessageDialog(ccaFrame, "This account already exists!");
                UserAccounts.deleteContact(username, password);
            } else {
                UserAccounts.add(username, password);
                JOptionPane.showMessageDialog(ccaFrame, "Account successfully created!");
                userField.setText("");
                passwordField.setText("");
                ccaFrame.dispose(); //this means closing the create account frame 
            }
            frame.setVisible(true); //then going back to log in frame
        });

        ccaFrame.add(usernameLabel);
        ccaFrame.add(userField);
        ccaFrame.add(passwordLabel);
        ccaFrame.add(passwordField);
        ccaFrame.add(saveButton); 

        ccaFrame.setVisible(true);

    }
}

