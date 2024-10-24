import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.time.chrono.JapaneseDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.border.Border;

public class LoginPage extends JFrame implements ActionListener,Serializable {

    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    User LoggedIn;
    static void animation() {
        try {
            Thread.sleep(15);
        } catch (InterruptedException ignored) {
        }
    }
    static void delay() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ignored) {
        }
    }
    JLabel label;
    JTextField email;
    JPasswordField pass;
    JButton login, signup, showhide, exit;
    ImageIcon view, hide;
    int cnt = 0;
    public LoginPage(Socket socket) {

        this.socket = socket;
        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Border border = BorderFactory.createLineBorder(Color.WHITE, 1);

        ImageIcon twitter = new ImageIcon("twitter-title.png");
        twitter.setImage(twitter.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setSize(456, 300);
        this.setLocationRelativeTo(null);
        this.setUndecorated(true);
        this.setResizable(false);
        this.setLayout(null);
        this.getContentPane().setBackground(Color.BLACK);
        this.getRootPane().setBorder(border);
        this.setIconImage(twitter.getImage());
        this.setTitle("X");

        final Point point = new Point();
        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                point.x = e.getX();
                point.y = e.getY();
            }
        });
        this.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                Point p = LoginPage.this.getLocation();
                LoginPage.this.setLocation(p.x + e.getX() - point.x, p.y + e.getY() - point.y);
            }
        });

        label = new JLabel();
        ImageIcon image = new ImageIcon("twitter.png");
        image.setImage(image.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        label.setBounds((this.getWidth() - image.getIconWidth()) / 2, (this.getHeight() - image.getIconHeight()) / 2, image.getIconWidth(), image.getIconHeight());
        label.setIcon(image);
        this.add(label);
        this.setVisible(true);

        delay();

        while (true) {
            int width = label.getWidth();
            int height = label.getHeight();
            if (width > 35 && height > 35) {
                width--;
                height--;
                Image scaledImage = image.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                label.setIcon(new ImageIcon(scaledImage));
                label.setBounds(label.getX(), label.getY() - 1, width, height);
                animation();
                continue;
            }
            break;
        }

        JLabel welcome = new JLabel("Welcome");
        welcome.setForeground(Color.WHITE);
        welcome.setFont(new Font(Font.SERIF, Font.BOLD, 17));
        welcome.setBounds(label.getX() + 35, label.getY() + 2, this.getWidth(), 30);

        JLabel emailFlag = new JLabel();
        emailFlag.setText("Email/Username");
        emailFlag.setForeground(Color.WHITE);
        emailFlag.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 19));
        emailFlag.setBounds(this.getWidth() / 6 - 1, 84, this.getWidth(), 30);
        email = new JTextField();
        email.setBounds(this.getWidth() / 6 - 1, 114, this.getWidth() / 3 * 2, 30);
        email.setFont(new Font(Font.MONOSPACED, Font.BOLD, 16));

        JLabel passFlag = new JLabel();
        passFlag.setText("Password");
        passFlag.setForeground(Color.WHITE);
        passFlag.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 19));
        passFlag.setBounds(this.getWidth() / 6 - 1, 146, this.getWidth(), 30);
        view = new ImageIcon("view.png");
        hide = new ImageIcon("hide.png");
        showhide = new JButton();
        view.setImage(view.getImage().getScaledInstance(20, 21, Image.SCALE_SMOOTH));
        hide.setImage(hide.getImage().getScaledInstance(20, 21, Image.SCALE_SMOOTH));
        showhide.setBounds(this.getWidth() / 6 - 1 + this.getWidth() / 3 * 2 - 22, 181, 20, 21);
        showhide.setIcon(view);
        showhide.setBackground(Color.white);
        showhide.setBorder(border);
        showhide.addActionListener(this);
        showhide.setFocusable(false);
        pass = new JPasswordField(19);
        pass.setBounds(this.getWidth() / 6 - 1, 176, this.getWidth() / 3 * 2 - 30, 30);
        pass.setFont(new Font(Font.MONOSPACED, Font.BOLD, 16));

        login = new JButton();
        login.setBounds(this.getWidth() / 6 + 30, 235, 100, 30);
        login.setText("Log In");
        login.setBorder(border);
        login.setBackground(Color.black);
        login.setForeground(Color.cyan);
        login.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 17));
        login.addActionListener(this);
        login.setFocusable(false);

        signup = new JButton();
        signup.setBounds(this.getWidth() / 3 * 2 - 54, 235, 100, 30);
        signup.setText("Sign Up");
        signup.setBorder(border);
        signup.setBackground(Color.black);
        signup.setForeground(Color.cyan);
        signup.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 17));
        signup.addActionListener(this);
        signup.setFocusable(false);

        ImageIcon exitIcon = new ImageIcon("close.png");
        exit = new JButton();
        exit.setBounds(this.getWidth() - 40, 19, 20, 20);
        exitIcon.setImage(exitIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        exit.setFocusable(false);
        exit.setIcon(exitIcon);
        exit.setBorder(border);
        exit.setBackground(Color.black);
        exit.setToolTipText("Exit");
        exit.addActionListener(this);

        this.add(welcome);
        this.add(email);
        this.add(pass);
        this.add(login);
        this.add(signup);
        this.add(exit);
        this.add(showhide);
        this.add(emailFlag);
        this.add(passFlag);
        this.setVisible(true);
        this.repaint();
    }

    public void close(JFrame frame) {
        WindowEvent closeWindow = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closeWindow);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == exit) {
            System.exit(0);
        } else if (e.getSource() == login) {
            if (Pattern.compile("^\\s").matcher(pass.getText()).find() || Pattern.compile("^\\s").matcher(email.getText()).find()) {
                JOptionPane.showMessageDialog(null, "Enter pass and email without space!", "ERROR", JOptionPane.WARNING_MESSAGE);
            } else if (!email.getText().isEmpty() && !pass.getText().isEmpty()) {
                try {
                    output.writeUTF("LOGIN");
                    output.flush();
                    output.writeUTF(email.getText());
                    output.flush();
                    output.writeUTF(pass.getText());
                    output.flush();
                    String message = input.readUTF();
                    if (!message.equals("LOGGED_IN")) {
                        JOptionPane.showMessageDialog(null, message, "ERROR", JOptionPane.WARNING_MESSAGE);
                    } else {


                        LoggedIn = (User) input.readObject();
                        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
                        int x = (int) ((dimension.getWidth() - 1200) / 2);
                        int y = (int) ((dimension.getHeight() - 800) / 2);
                        new HomePage(socket, input, output, x, y, 1200,800, LoggedIn, LoggedIn);
                        this.dispose();

                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, "First fill the blanks!", "ERROR", JOptionPane.WARNING_MESSAGE);
            }

        } else if (e.getSource() == signup) {

            this.setVisible(false);
            SignUp();

        } else if (e.getSource() == showhide) {
            if (cnt % 2 == 1) {
                pass.setEchoChar('*');
                showhide.setIcon(view);
            } else {
                pass.setEchoChar((char) 0);
                showhide.setIcon(hide);
            }
            cnt++;
        }

    }

    public void SignUp() {

        Border border3 = BorderFactory.createLineBorder(Color.cyan, 2);
        Border border2 = BorderFactory.createLineBorder(Color.black, 3);
        Border border = BorderFactory.createLineBorder(Color.gray, 2);

        ImageIcon adduser = new ImageIcon("add-user.png");
        adduser.setImage(adduser.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        JFrame signup = new JFrame();
        signup.setTitle("Sign Up");
        signup.setDefaultCloseOperation(signup.EXIT_ON_CLOSE);
        signup.getContentPane().setBackground(Color.BLACK);
        signup.setSize(440, 500);
        signup.setLocationRelativeTo(null);
        signup.setResizable(false);
        signup.setLayout(new GridLayout(9, 2, 4, 4));
        signup.setIconImage(adduser.getImage());

        final Point point = new Point();
        signup.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                point.x = e.getX();
                point.y = e.getY();
            }
        });
        signup.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                Point p = signup.getLocation();
                signup.setLocation(p.x + e.getX() - point.x, p.y + e.getY() - point.y);
            }
        });

        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayout());
        panel1.setBackground(Color.black);
        JLabel firstName = new JLabel(" First Name:");
        firstName.setForeground(Color.white);
        firstName.setFont(new Font(Font.SERIF, Font.TRUETYPE_FONT, 24));
        panel1.add(firstName);
        JTextField firstNameField = new JTextField();
        firstNameField.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
        firstNameField.setPreferredSize(new Dimension(260, 40));
        firstNameField.setBorder(border);
        firstNameField.setBorder(border2);
        firstName.setBorder(border);
        panel1.add(firstNameField);
        panel1.setBorder(border);

        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout());
        panel2.setBackground(Color.black);
        JLabel lastName = new JLabel(" Last Name:");
        lastName.setForeground(Color.white);
        lastName.setFont(new Font(Font.SERIF, Font.TRUETYPE_FONT, 24));
        panel2.add(lastName);
        JTextField lastNameField = new JTextField();
        lastNameField.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
        lastNameField.setPreferredSize(new Dimension(260, 40));
        lastNameField.setBorder(border);
        lastNameField.setBorder(border2);
        lastName.setBorder(border);
        panel2.add(lastNameField);
        panel2.setBorder(border);

        JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayout());
        panel3.setBackground(Color.black);
        JLabel username = new JLabel(" Username:");
        username.setForeground(Color.white);
        username.setFont(new Font(Font.SERIF, Font.TRUETYPE_FONT, 24));
        panel3.add(username);
        JTextField usernameField = new JTextField();
        usernameField.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
        usernameField.setPreferredSize(new Dimension(260, 40));
        usernameField.setBorder(border);
        usernameField.setBorder(border2);
        username.setBorder(border);
        panel3.add(usernameField);
        panel3.setBorder(border);

        JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayout());
        panel4.setBackground(Color.black);
        JLabel password = new JLabel(" Password:");
        password.setForeground(Color.white);
        password.setFont(new Font(Font.SERIF, Font.TRUETYPE_FONT, 24));
        panel4.add(password);
        JTextField passwordField = new JTextField();
        passwordField.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
        passwordField.setPreferredSize(new Dimension(260, 40));
        passwordField.setBorder(border);
        passwordField.setBorder(border2);
        password.setBorder(border);
        panel4.add(passwordField);
        panel4.setBorder(border);

        JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayout());
        panel5.setBackground(Color.black);
        JLabel passwordconf = new JLabel(" Password confirmation:");
        passwordconf.setForeground(Color.white);
        passwordconf.setFont(new Font(Font.SERIF, Font.TRUETYPE_FONT, 20));
        panel5.add(passwordconf);
        JTextField passwordconfField = new JTextField();
        passwordconfField.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
        passwordconfField.setPreferredSize(new Dimension(260, 40));
        passwordconfField.setBorder(border);
        passwordconfField.setBorder(border2);
        passwordconf.setBorder(border);
        panel5.add(passwordconfField);
        panel5.setBorder(border);

        JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayout());
        panel6.setBackground(Color.black);
        JLabel email = new JLabel(" Email:");
        email.setForeground(Color.white);
        email.setFont(new Font(Font.SERIF, Font.TRUETYPE_FONT, 24));
        panel6.add(email);
        JTextField emailField = new JTextField();
        emailField.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
        emailField.setPreferredSize(new Dimension(260, 40));
        emailField.setBorder(border);
        emailField.setBorder(border2);
        email.setBorder(border);
        panel6.add(emailField);
        panel6.setBorder(border);

        JPanel panel7 = new JPanel();
        panel7.setLayout(new GridLayout());
        panel7.setBackground(Color.black);
        JLabel birthdate = new JLabel(" Birthdate(dd/mm/yyyy):");
        birthdate.setForeground(Color.white);
        birthdate.setFont(new Font(Font.SERIF, Font.TRUETYPE_FONT, 20));
        panel7.add(birthdate);
        JTextField birthdateField = new JTextField();
        birthdateField.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
        birthdateField.setPreferredSize(new Dimension(260, 40));
        birthdateField.setBorder(border);
        birthdateField.setBorder(border2);
        birthdate.setBorder(border);
        panel7.add(birthdateField);
        panel7.setBorder(border);

        JPanel panel8 = new JPanel();
        panel8.setLayout(new GridLayout());
        panel8.setBackground(Color.black);
        JLabel number = new JLabel(" Number:");
        number.setForeground(Color.white);
        number.setFont(new Font(Font.SERIF, Font.TRUETYPE_FONT, 24));
        panel8.add(number);
        JTextField numberField = new JTextField();
        numberField.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
        numberField.setPreferredSize(new Dimension(260, 40));
        numberField.setBorder(border);
        numberField.setBorder(border2);
        number.setBorder(border);
        panel8.add(numberField);
        panel8.setBorder(border);

        JButton submitButton = new JButton("Submit");
        submitButton.setFont(new Font(null, Font.BOLD, 12));
        submitButton.setBackground(Color.black);
        submitButton.setForeground(Color.cyan);
        submitButton.setBorder(border3);
        submitButton.setFocusable(false);

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font(null, Font.BOLD, 12));
        backButton.setBackground(Color.black);
        backButton.setForeground(Color.cyan);
        backButton.setBorder(border3);
        backButton.setFocusable(false);
        backButton.addActionListener(e -> {
            signup.dispose();
            this.setVisible(true);
        });

        JLabel bio = new JLabel("   Bio:");
        bio.setBorder(border2);
        bio.setBackground(Color.black);
        bio.setForeground(Color.white);
        bio.setBorder(border);
        bio.setFont(new Font(Font.SERIF, Font.TRUETYPE_FONT, 22));
        JPanel panel10 = new JPanel();
        panel10.setLayout(new GridLayout());
        panel10.setBackground(Color.black);
        JPanel panel11 = new JPanel();
        panel11.setLayout(new GridLayout(1, 3));
        panel11.setBackground(Color.black);

        JPanel panel12 = new JPanel();
        panel12.setLayout(new GridLayout());
        JTextArea bioArea = new JTextArea();
        bioArea.setFont(new Font(null, Font.PLAIN, 13));
        bioArea.setBorder(border);
        bioArea.setLineWrap(true);
        JScrollPane bioScrollPane = new JScrollPane(bioArea);
        bioScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        panel12.add(bioScrollPane);

        panel11.add(backButton);
        panel11.add(submitButton);
        panel11.add(bio);

        panel10.add(panel11);
        panel10.add(panel12);

        //-------------------------------------
        submitButton.addActionListener(e -> {

            if (firstNameField.getText().isEmpty() || Pattern.compile("[^a-zA-Z/@|#|\\$|!|%|&|\\^|\\*|-|\\+|_|=|{|}|\\[|\\]|\\(|\\)|~|`|\\.|\\?|\\<|\\>|,|\\/|:|;|\"|'|\\\\/]").matcher(firstNameField.getText()).find()) {
                JOptionPane.showMessageDialog(null, "Fill the First name field without space!", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (usernameField.getText().isEmpty() || Pattern.compile("[^*[a-zA-Z0-9_]]").matcher(usernameField.getText()).find()) {
                JOptionPane.showMessageDialog(null, "Fill the Username field only by letters\nand without characters! ('_' is usable)", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (passwordField.getText().isEmpty() || !Pattern.compile("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])[^\\s]").matcher(passwordField.getText()).find() || (passwordField.getText().length() < 8 || passwordField.getText().length() > 18)) {
                JOptionPane.showMessageDialog(null, "Fill the Password field with uppercase and lowercase letters, numbers, and characters.\nThe minimum length of the password must be 8 characters!", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (passwordconfField.getText().isEmpty() || !(passwordconfField.getText().equals(passwordField.getText()))) {
                JOptionPane.showMessageDialog(null, "Fill the Password confirmation field correctly!", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (emailField.getText().isEmpty() || !emailField.getText().contains("@gmail.com") || emailField.getText().length() < 11) {
                JOptionPane.showMessageDialog(null, "Fill the Email field with a valid type!", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (birthdateField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Fill the birthdate field!", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                output.writeUTF("CHECK_USERNAME");
                output.flush();
                output.writeUTF(usernameField.getText());
                output.flush();
                String status = input.readUTF();
                if (status.equals("TRUE")) {
                    JOptionPane.showMessageDialog(null, "This username already exists, try another one!", "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            try {
                output.writeUTF("CHECK_EMAIL");
                output.flush();
                output.writeUTF(emailField.getText());
                output.flush();
                String status = input.readUTF();
                if (status.equals("TRUE")) {
                    JOptionPane.showMessageDialog(null, "This email is already exist, try another one!", "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            try {
                output.writeUTF("SIGNUP");
                output.flush();
                output.writeUTF(firstNameField.getText());
                output.flush();
                output.writeUTF(lastNameField.getText());
                output.flush();
                output.writeUTF(usernameField.getText());
                output.flush();
                output.writeUTF(passwordField.getText());
                output.flush();
                output.writeUTF(emailField.getText());
                output.flush();
                output.writeUTF(birthdateField.getText());
                output.flush();
                output.writeUTF(numberField.getText());
                output.flush();
                output.writeUTF(bioArea.getText());
                output.flush();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            signup.dispose();
            this.setVisible(true);

        });
        //-------------------------------------

        signup.add(panel1);
        signup.add(panel2);
        signup.add(panel3);
        signup.add(panel4);
        signup.add(panel5);
        signup.add(panel6);
        signup.add(panel7);
        signup.add(panel8);
        signup.add(panel10);

        signup.setVisible(true);

    }

}