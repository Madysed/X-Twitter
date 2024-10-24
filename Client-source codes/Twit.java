import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

public class Twit extends JFrame implements Serializable {
    User LoggedInUser;
    JTextField searchField;
    public ObjectInputStream input;
    public ObjectOutputStream output;
    JButton settingButton, homeButton, personalPageButton, searchButton, twitButton, TwitButton;
    Twit (Socket socket, ObjectInputStream input, ObjectOutputStream output, int thisX, int thisY, int thisWidth, int thisHeight, User person, User loggedInUser) {

        this.LoggedInUser = loggedInUser;
        this.input = input;
        this.output = output;

        ImageIcon check = new ImageIcon("check-mark.png");
        check.setImage(check.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        ImageIcon unCheck = new ImageIcon("un-check.png");
        unCheck.setImage(unCheck.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        ImageIcon twitter = new ImageIcon("twitter-title.png");
        twitter.setImage(twitter.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        ImageIcon setting = new ImageIcon("settings.png");
        setting.setImage(setting.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        ImageIcon home = new ImageIcon("home.png");
        home.setImage(home.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        ImageIcon user = new ImageIcon("user.png");
        user.setImage(user.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        ImageIcon search = new ImageIcon("magnifying-glass.png");
        search.setImage(search.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        ImageIcon twit = new ImageIcon("writing.png");
        twit.setImage(twit.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        ImageIcon likeFlag = new ImageIcon("heart.png");
        likeFlag.setImage(likeFlag.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        ImageIcon saveFlag = new ImageIcon("bookmark.png");
        saveFlag.setImage(saveFlag.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        Border border = BorderFactory.createLineBorder(Color.lightGray, 1);
        Border blackborder = BorderFactory.createLineBorder(Color.BLACK, 1);
        Border BlackB = BorderFactory.createLineBorder(Color.BLACK, 5);
        Border cyanborder = BorderFactory.createLineBorder(Color.cyan, 5);

        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setIconImage(twitter.getImage());
        this.setTitle("Twitter");
        this.setLocation(thisX, thisY);
        this.setSize(thisWidth, thisHeight);
        this.setLayout(new BorderLayout());
        this.setMinimumSize(new Dimension(1200, 500));

        JPanel Top = new JPanel();
        Top.setLayout(new GridLayout(1, 4));
        JPanel Down = new JPanel();
        Down.setLayout(new GridLayout(1, 5));
        JPanel Main = new JPanel();
        Main.setLayout(new GridLayout(1,2));
        Down.setPreferredSize(new Dimension(100,60));

        ImageIcon profile = new ImageIcon("user.png");
        profile.setImage(profile.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        JLabel profilePic = new JLabel();
        profilePic.setIcon(profile);
        profilePic.setText("<html>First name: " + person.getFirstName() + "<br>" + "Last name: " + person.getLastname() + "<br>" + "Username: " + person.getUsername() + "</html>");
        Top.add(profilePic);
        Top.setBorder(blackborder);

        settingButton = new JButton();
        settingButton.setFocusable(false);
        settingButton.setIcon(setting);
        settingButton.setBackground(Color.white);
        settingButton.setBorder(blackborder);
        settingButton.setPreferredSize(new Dimension(50, 50));

        homeButton = new JButton();
        homeButton.setFocusable(false);
        homeButton.setIcon(home);
        homeButton.setBackground(Color.white);
        homeButton.setBorder(blackborder);
        homeButton.setPreferredSize(new Dimension(50, 50));

        personalPageButton = new JButton();
        personalPageButton.setFocusable(false);
        personalPageButton.setIcon(user);
        personalPageButton.setBackground(Color.white);
        personalPageButton.setBorder(blackborder);
        personalPageButton.setPreferredSize(new Dimension(50, 50));

        searchButton = new JButton();
        searchButton.setFocusable(false);
        searchButton.setIcon(search);
        searchButton.setBackground(Color.white);
        searchButton.setBorder(blackborder);
        searchButton.setPreferredSize(new Dimension(50, 50));

        searchField = new JTextField("Search username: ");
        searchField.setBackground(Color.white);
        searchField.setBorder(blackborder);
        searchField.setFont(new Font(null, Font.PLAIN, 16));
        searchField.setPreferredSize(new Dimension(70, 50));

        twitButton = new JButton();
        twitButton.setFocusable(false);
        twitButton.setIcon(twit);
        twitButton.setBackground(Color.gray);
        twitButton.setBorder(blackborder);
        twitButton.setPreferredSize(new Dimension(50, 50));

        Down.add(settingButton);
        Down.add(searchButton);
        Down.add(searchField);
        Down.add(twitButton);
        Down.add(personalPageButton);
        Down.add(homeButton);

        //----------------------------------------------

        JPanel twitPrint = new JPanel();
        twitPrint.setLayout(new GridLayout());
        JTextArea TwitArea = new JTextArea("Write Here...");
        TwitArea.setFont(new Font(null, Font.PLAIN, 28));
        TwitArea.setBorder(border);
        TwitArea.setLineWrap(true);
        TwitArea.setBackground(Color.black);
        TwitArea.setForeground(Color.white);
        JScrollPane twitScrollPane = new JScrollPane(TwitArea);
        twitScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        twitPrint.add(twitScrollPane);
        Main.add(twitPrint);

        JPanel options = new JPanel();
        options.setLayout(new GridLayout(4,1,5,5));

        JPanel closeCheckPanel = new JPanel();
        closeCheckPanel.setLayout(new GridLayout());
        closeCheckPanel.setBackground(Color.WHITE);
        closeCheckPanel.setBorder(BlackB);
        JCheckBox closeCheck = new JCheckBox();
        closeCheck.setIcon(unCheck);
        closeCheck.setSelectedIcon(check);
        closeCheck.setText(" Only share with my followers ");
        closeCheck.setFont(new Font(null, Font.PLAIN, 24));
        closeCheck.setBackground(Color.white);
        closeCheck.setForeground(Color.black);
        closeCheck.setHorizontalTextPosition(JCheckBox.LEFT);
        closeCheck.setFocusable(false);
        closeCheckPanel.add(closeCheck);

        JPanel saveCheckPanel = new JPanel();
        saveCheckPanel.setLayout(new GridLayout());
        saveCheckPanel.setBackground(Color.WHITE);
        saveCheckPanel.setBorder(BlackB);
        JCheckBox saveCheck = new JCheckBox();
        saveCheck.setIcon(unCheck);
        saveCheck.setSelectedIcon(check);
        saveCheck.setText(" People can save my twit ");
        saveCheck.setFont(new Font(null, Font.PLAIN, 24));
        saveCheck.setBackground(Color.white);
        saveCheck.setForeground(Color.black);
        saveCheck.setHorizontalTextPosition(JCheckBox.LEFT);
        saveCheck.setFocusable(false);
        saveCheckPanel.add(saveCheck);

        JPanel commentCheckPanel = new JPanel();
        commentCheckPanel.setLayout(new GridLayout());
        commentCheckPanel.setBackground(Color.WHITE);
        commentCheckPanel.setBorder(BlackB);
        JCheckBox commentCheck = new JCheckBox();
        commentCheck.setIcon(unCheck);
        commentCheck.setSelectedIcon(check);
        commentCheck.setText(" Comments of my twit is ");
        commentCheck.setFont(new Font(null, Font.PLAIN, 24));
        commentCheck.setBackground(Color.white);
        commentCheck.setForeground(Color.black);
        commentCheck.setHorizontalTextPosition(JCheckBox.LEFT);
        commentCheck.setFocusable(false);
        commentCheckPanel.add(commentCheck);

        TwitButton = new JButton();
        TwitButton.setText(" Submit and Twit ");
        TwitButton.setFocusable(false);
        TwitButton.setBackground(Color.black);
        TwitButton.setForeground(Color.cyan);
        TwitButton.setFont(new Font(null, Font.PLAIN, 24));
        TwitButton.setBorder(cyanborder);

        options.add(closeCheckPanel);
        options.add(saveCheckPanel);
        options.add(commentCheckPanel);
        options.add(TwitButton);
        Main.add(options);

        //----------------------------------------------

        this.add(Main, BorderLayout.CENTER);
        this.add(Top, BorderLayout.NORTH);
        this.add(Down, BorderLayout.SOUTH);


        //----------------------------------------------

        TwitButton.addActionListener(e -> {

            int op = JOptionPane.showOptionDialog(null, "Are you sure? ", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
            if (op == 1) {
                return;
            }
            JOptionPane.showMessageDialog(null, "Done");
            boolean closeQ = false, saveQ = false, commentQ = false;

            if (closeCheck.isSelected()) {
                closeQ = true;
            }
            if (saveCheck.isSelected()) {
                saveQ = true;
            }
            if (commentCheck.isSelected()) {
                commentQ = true;
            }
            try {
                output.writeUTF("TWEET");
                output.flush();
                output.writeUTF(String.valueOf(closeQ));
                output.flush();
                output.writeUTF(String.valueOf(saveQ));
                output.flush();
                output.writeUTF(String.valueOf(commentQ));
                output.flush();
                output.writeUTF(TwitArea.getText());
                output.flush();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        });

        //----------------------------------------------


        settingButton.addActionListener(e -> {

            //------

        });

        homeButton.addActionListener(e -> {

            new HomePage(socket, input, output, this.getX(), this.getY(), this.getWidth(), this.getHeight(), LoggedInUser, LoggedInUser);
            this.dispose();

        });

        personalPageButton.addActionListener(e -> {

            new UserPage(socket, input, output, this.getX(), this.getY(), this.getWidth(), this.getHeight(), person, LoggedInUser);
            this.dispose();

        });

        searchButton.addActionListener(e -> {

            if (searchField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Fill the search field first!", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            new SearchPage(socket, input, output, this.getX(), this.getY(), this.getWidth(), this.getHeight(), this, searchField.getText(), LoggedInUser);

        });

        twitButton.addActionListener(e -> {

            new Twit(socket, input, output, this.getX(), this.getY(), this.getWidth(), this.getHeight(), person, LoggedInUser);
            this.dispose();

        });

        this.setVisible(true);

    }

}
