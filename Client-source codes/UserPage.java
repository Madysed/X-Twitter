import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;

public class UserPage extends JFrame implements Serializable {
    User LoggedInUser;
    JTextField searchField;
    boolean isLiked = false, isSaved = false, haveFollow = false, isFollowed = false;
    public ObjectInputStream input;
    public ObjectOutputStream output;
    public ArrayList<Twits> allTweets = new ArrayList<>();
    public ArrayList<Twits> likedTweets = new ArrayList<>();
    public ArrayList<Twits> savedTweets = new ArrayList<>();
    public ArrayList<User> tweetLikes = new ArrayList<>();
    public ArrayList<User> tweetSaves = new ArrayList<>();
    public ArrayList<Comment> tweetComments = new ArrayList<>();
    public ArrayList<User> followersList = new ArrayList<>();
    public ArrayList<User> followingsList = new ArrayList<>();
    public ArrayList<Twits> favTweetsList = new ArrayList<>();
    public ArrayList<Twits> savedTweetsList = new ArrayList<>();

    JButton settingButton, homeButton, personalPageButton, statusButton, followersButton, followingsButton, searchButton, twitButton, userLikesButton, userSavesButton;
    UserPage(Socket socket, ObjectInputStream input, ObjectOutputStream output, int thisX, int thisY, int thisWidth, int thisHeight, User person, User loggedInUser) {

        this.LoggedInUser = loggedInUser;
        this.input = input;
        this.output = output;

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
        ImageIcon smallSaveFlag = new ImageIcon("bookmark.png");
        smallSaveFlag.setImage(smallSaveFlag.getImage().getScaledInstance(23, 23, Image.SCALE_SMOOTH));
        ImageIcon redHeart = new ImageIcon("redHeart.png");
        redHeart.setImage(redHeart.getImage().getScaledInstance(27, 27, Image.SCALE_SMOOTH));
        ImageIcon nullHeart = new ImageIcon("nullHeart.png");
        nullHeart.setImage(nullHeart.getImage().getScaledInstance(27, 27, Image.SCALE_SMOOTH));
        ImageIcon nullSaved = new ImageIcon("nullSaved.png");
        nullSaved.setImage(nullSaved.getImage().getScaledInstance(23, 23, Image.SCALE_SMOOTH));
        ImageIcon sender = new ImageIcon("send.png");
        sender.setImage(sender.getImage().getScaledInstance(27, 27, Image.SCALE_SMOOTH));
        ImageIcon commentsLogo = new ImageIcon("commentsLogo.png");
        commentsLogo.setImage(commentsLogo.getImage().getScaledInstance(27, 27, Image.SCALE_SMOOTH));

        Border blackborder = BorderFactory.createLineBorder(Color.BLACK, 1);
        Border cyanborder = BorderFactory.createLineBorder(Color.cyan, 1);

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
        Main.setLayout(new BorderLayout());
        Down.setPreferredSize(new Dimension(100, 60));

        ImageIcon profile = new ImageIcon("user.png");
        profile.setImage(profile.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        JLabel profilePic = new JLabel();
        profilePic.setIcon(profile);
        profilePic.setText("<html>First name: " + person.getFirstName() + "<br>" + "Last name: " + person.getLastname() + "<br>" + "Username: " + person.getUsername() + "</html>");

        statusButton = new JButton();
        statusButton.setText("Follow");
        statusButton.setBackground(Color.BLACK);
        statusButton.setForeground(Color.white);
        statusButton.setFont(new Font(null, Font.PLAIN, 22));
        statusButton.setFocusable(false);

        followersButton = new JButton();
        followersButton.setFocusable(false);
        JLabel followers = new JLabel();
        followersButton.setText(person.getFollowersCount() + " Followers");
        followersButton.setBackground(Color.BLACK);
        followersButton.setForeground(Color.white);
        followersButton.setFont(new Font(null, Font.PLAIN, 22));
        followers.add(followersButton);

        followingsButton = new JButton();
        followingsButton.setFocusable(false);
        JLabel followings = new JLabel();
        followingsButton.setText(person.getFollowingsCount() + " Followings");
        followingsButton.setBackground(Color.BLACK);
        followingsButton.setForeground(Color.white);
        followingsButton.setFont(new Font(null, Font.PLAIN, 22));
        followings.add(followingsButton);
        Top.setPreferredSize(new Dimension(100, 60));

        Top.setBorder(blackborder);
        Top.add(profilePic);
        Top.add(statusButton);
        Top.add(followersButton);
        Top.add(followingsButton);

        JPanel bioArea = new JPanel();
        bioArea.setLayout(new GridLayout());
        JTextArea bioPrint = new JTextArea();
        bioPrint.setText(person.getBio());
        bioPrint.setLineWrap(true);
        bioPrint.setEditable(false);
        bioPrint.setForeground(Color.black);
        bioPrint.setFont(new Font(null, Font.PLAIN, 16));
        bioArea.add(bioPrint);
        JScrollPane bioScrollPane = new JScrollPane(bioPrint);
        bioScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        bioArea.add(bioScrollPane);

        JPanel information = new JPanel();
        information.setLayout(new GridLayout(1, 3));
        information.add(bioArea);

        userLikesButton = new JButton();
        userLikesButton.setFocusable(false);
        userLikesButton.setIcon(likeFlag);
        userLikesButton.setBackground(Color.white);
        userLikesButton.setBorder(blackborder);

        userLikesButton.setFont(new Font(null, Font.PLAIN, 16));

        userSavesButton = new JButton();
        userSavesButton.setFocusable(false);
        userSavesButton.setIcon(saveFlag);
        userSavesButton.setBackground(Color.white);
        userSavesButton.setBorder(blackborder);
        userSavesButton.setFont(new Font(null, Font.PLAIN, 16));

        information.add(userLikesButton);
        information.add(userSavesButton);

        information.setPreferredSize(new Dimension(100, 60));
        information.setBorder(blackborder);
        Main.setBackground(Color.BLACK);

        Main.add(information, BorderLayout.NORTH);

        if (person.getID() == LoggedInUser.getID())
            statusButton.setEnabled(false);

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
        personalPageButton.setBackground(Color.gray);
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
        twitButton.setBackground(Color.white);
        twitButton.setBorder(blackborder);
        twitButton.setPreferredSize(new Dimension(50, 50));

        Down.add(settingButton);
        Down.add(searchButton);
        Down.add(searchField);
        Down.add(twitButton);
        Down.add(personalPageButton);
        Down.add(homeButton);

        //----------------------------------------------

        allTweets.clear();

        try {
            output.writeUTF("GET_TWEETS");
            output.flush();
            output.writeUTF(Integer.toString(person.getID()));
            output.flush();
            Twits temp;
            while (true) {
                temp = (Twits) input.readObject();
                if (temp == null)
                    break;
                allTweets.add(temp);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (person.getID() == LoggedInUser.getID()) {
            likedTweets.clear();

            try {
                output.writeUTF("GET_LIKED_TWEETS");
                output.flush();
                Twits temp;
                while (true) {
                    temp = (Twits) input.readObject();
                    if (temp == null)
                        break;
                    likedTweets.add(temp);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            savedTweets.clear();

            try {
                output.writeUTF("GET_SAVED_TWEETS");
                output.flush();
                Twits temp;
                while (true) {
                    temp = (Twits) input.readObject();
                    if (temp == null)
                        break;
                    savedTweets.add(temp);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        userSavesButton.setText(" " + savedTweets.size());
        userLikesButton.setText(" " + likedTweets.size());

        if (person.getID() != LoggedInUser.getID()) {

            try {
                output.writeUTF("HAVE_FOLLOW");
                output.flush();
                output.writeUTF(Integer.toString(person.getID()));
                output.flush();
                isFollowed = Boolean.parseBoolean(input.readUTF());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (isFollowed)
                statusButton.setText("unFollow");
            else
                statusButton.setText("Follow");

            userSavesButton.setText("");
            userLikesButton.setText("");
            userSavesButton.setEnabled(false);
            userLikesButton.setEnabled(false);

        }

        JPanel allGrid = new JPanel();
        allGrid.setLayout(new GridLayout(1, 2));

        JPanel Tweet = new JPanel();
        Tweet.setLayout(new BorderLayout(50, 50));

        JPanel tweetPanel = new JPanel();
        tweetPanel.setBackground(new Color(0, 0, 0));
        tweetPanel.setLayout(new BoxLayout(tweetPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(tweetPanel);

        for (Twits i : allTweets) {

            JButton likeButton, saveButton, likesButton, savesButton, commentsButton, commentButton, userButton, deleteButton;

            JPanel textPanel = new JPanel();
            textPanel.setLayout(new BorderLayout(10, 10));
            textPanel.setBackground(Color.black);
            textPanel.setPreferredSize(new Dimension((this.getWidth() - 70) / 2, 200));
            textPanel.setMaximumSize(new Dimension((this.getWidth() - 70) / 2, 200));

            textPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 10));

            JTextArea tweetText = new JTextArea(i.getText());
            tweetText.setPreferredSize(new Dimension(this.getWidth() - 20, 130));
            tweetText.setBackground(Color.black);
            tweetText.setForeground(Color.white);
            tweetText.setLineWrap(true);
            tweetText.setEditable(false);
            tweetText.setFont(new Font(null, Font.PLAIN, 21));
            JScrollPane TweetScrollPane = new JScrollPane(tweetText);
            TweetScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            textPanel.add(TweetScrollPane, BorderLayout.CENTER);

            JPanel userPanel = new JPanel();
            userPanel.setPreferredSize(new Dimension((this.getWidth() - 5) / 2, 43));
            userPanel.setLayout(new FlowLayout());
            userButton = new JButton();
            userButton.setBackground(Color.cyan);
            userButton.setFocusable(false);
            userButton.setText("First name : " + i.getUser().getFirstName() + "     ,     " + "Username : " + i.getUser().getUsername());
            userButton.setFont(new Font(Font.MONOSPACED, Font.BOLD, 16));
            deleteButton = new JButton();
            deleteButton.setText("Delete");
            deleteButton.setFont(new Font(Font.MONOSPACED, Font.BOLD, 16));
            deleteButton.setMaximumSize(new Dimension(30,30));
            deleteButton.setBackground(Color.red);
            deleteButton.setForeground(Color.white);
            deleteButton.setFocusable(false);
            userPanel.setBorder(blackborder);
            userPanel.add(userButton);
            userPanel.add(deleteButton);
            textPanel.add(userPanel, BorderLayout.NORTH);

            if (person.getID() != LoggedInUser.getID()) {
                deleteButton.setBackground(Color.gray);
                deleteButton.setEnabled(false);
            }

            likeButton = new JButton();
            likeButton.setIcon(nullHeart);
            likeButton.setBackground(new Color(190, 190, 240));
            likeButton.setBorder(blackborder);
            likeButton.setFocusable(false);
            saveButton = new JButton();
            saveButton.setIcon(nullSaved);
            saveButton.setBackground(new Color(190, 190, 240));
            saveButton.setBorder(blackborder);
            saveButton.setFocusable(false);
            commentButton = new JButton("Comment");
            commentButton.setIcon(sender);
            commentButton.setBackground(new Color(190, 190, 240));
            commentButton.setBorder(blackborder);
            commentButton.setFocusable(false);
            likesButton = new JButton();
            likesButton.setBackground(new Color(190, 190, 240));
            likesButton.setBorder(blackborder);
            likesButton.setFocusable(false);
            savesButton = new JButton("Saves " + i.getSavesCount());
            savesButton.setBackground(new Color(190, 190, 240));
            savesButton.setBorder(blackborder);
            savesButton.setFocusable(false);
            commentsButton = new JButton("Comments " + i.getCommentsCount());
            commentsButton.setIcon(commentsLogo);
            commentsButton.setBackground(new Color(190, 190, 240));
            commentsButton.setBorder(blackborder);
            commentsButton.setFocusable(false);
            JTextArea commentArea = new JTextArea();
            commentArea.setFont(new Font(Font.DIALOG, Font.PLAIN, 15));
            commentArea.setLineWrap(true);
            commentArea.setBorder(blackborder);
            JScrollPane CommentScrollPane = new JScrollPane(commentArea);
            CommentScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            JLabel releaseTime = new JLabel(String.valueOf(i.getTime()));
            releaseTime.setForeground(Color.blue);
            releaseTime.setBorder(blackborder);

            try {
                output.writeUTF("IS_LIKED");
                output.flush();
                output.writeUTF(Integer.toString(i.getTweetID()));
                output.flush();
                output.writeUTF(Integer.toString(LoggedInUser.getID()));
                output.flush();
                isLiked = Boolean.parseBoolean(input.readUTF());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            if (isLiked) {
                likeButton.setIcon(redHeart);
                likesButton.setText("Likes " + i.getLikesCount());
            } else {
                likeButton.setIcon(nullHeart);
                likesButton.setText("Likes " + i.getLikesCount());
            }

            try {
                output.writeUTF("IS_SAVED");
                output.flush();
                output.writeUTF(Integer.toString(i.getTweetID()));
                output.flush();
                output.writeUTF(Integer.toString(LoggedInUser.getID()));
                output.flush();
                isSaved = Boolean.parseBoolean(input.readUTF());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            if (isSaved) {
                saveButton.setIcon(smallSaveFlag);
                savesButton.setText("Saves " + i.getSavesCount());
            } else {
                saveButton.setIcon(nullSaved);
                savesButton.setText("Saves " + i.getSavesCount());
            }

            if (!i.isSave()) {
                saveButton.setEnabled(false);
                saveButton.setEnabled(false);
            }
            if (!i.isComment()) {
                commentButton.setEnabled(false);
                commentArea.setFocusable(false);
            }

            userButton.setActionCommand("USER_" + i.getUser().getID());
            deleteButton.setActionCommand("DELETE_" + i.getTweetID());
            saveButton.setActionCommand("SAVE_" + i.getTweetID());
            likeButton.setActionCommand("LIKE_" + i.getTweetID());
            commentButton.setActionCommand("COMMENT_" + i.getTweetID());
            likesButton.setActionCommand("LIKES_" + i.getTweetID());
            savesButton.setActionCommand("SAVES_" + i.getTweetID());
            commentsButton.setActionCommand("COMMENTS_" + i.getTweetID());

            ActionListener buttonActionListener = e -> {
                try {
                    output.writeUTF("IS_LIKED");
                    output.flush();
                    output.writeUTF(Integer.toString(i.getTweetID()));
                    output.flush();
                    output.writeUTF(Integer.toString(LoggedInUser.getID()));
                    output.flush();
                    isLiked = Boolean.parseBoolean(input.readUTF());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    output.writeUTF("IS_SAVED");
                    output.flush();
                    output.writeUTF(Integer.toString(i.getTweetID()));
                    output.flush();
                    output.writeUTF(Integer.toString(LoggedInUser.getID()));
                    output.flush();
                    isSaved = Boolean.parseBoolean(input.readUTF());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                String command = e.getActionCommand();
                if (command.startsWith("USER_")) {
                    int userID = Integer.parseInt(command.substring(5));
                    try {
                        output.writeUTF("FIND_USER");
                        output.flush();
                        output.writeUTF(Integer.toString(userID));
                        output.flush();
                        new UserPage(socket, input, output, this.getX(), this.getY(), this.getWidth(), this.getHeight(), (User) input.readObject(), LoggedInUser);
                        this.dispose();
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else if (command.startsWith("DELETE_")) {
                    int tweetId = Integer.parseInt(command.substring(7));
                    try {
                        output.writeUTF("REMOVE_TWEET");
                        output.flush();
                        output.writeUTF(String.valueOf(tweetId));
                        output.flush();
                        this.repaint();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else if (command.startsWith("SAVE_")) {
                    int tweetId = Integer.parseInt(command.substring(5));
                    if (isSaved) {
                        try {
                            output.writeUTF("REMOVE_SAVE");
                            output.flush();
                            output.writeUTF(String.valueOf(tweetId));
                            output.flush();
                            output.writeUTF(Integer.toString(LoggedInUser.getID()));
                            output.flush();
                            i.setSavesCount(i.getSavesCount() - 1);
                            savesButton.setText("Saves " + (i.getSavesCount()));
                            this.revalidate();
                            this.repaint();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    } else {
                        try {
                            output.writeUTF("ADD_SAVE");
                            output.flush();
                            output.writeUTF(Integer.toString(tweetId));
                            output.flush();
                            output.writeUTF(Integer.toString(LoggedInUser.getID()));
                            output.flush();
                            i.setSavesCount(i.getSavesCount() + 1);
                            savesButton.setText("Saves " + (i.getSavesCount()));
                            this.revalidate();
                            this.repaint();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                } else if (command.startsWith("LIKE_")) {
                    int tweetId = Integer.parseInt(command.substring(5));
                    if (isLiked) {
                        try {
                            output.writeUTF("REMOVE_LIKE");
                            output.flush();
                            output.writeUTF(String.valueOf(tweetId));
                            output.flush();
                            output.writeUTF(Integer.toString(LoggedInUser.getID()));
                            output.flush();
                            i.setLikesCount(i.getLikesCount() - 1);
                            likesButton.setText("Likes " + (i.getLikesCount()));
                            this.revalidate();
                            this.repaint();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    } else {
                        try {
                            output.writeUTF("ADD_LIKE");
                            output.flush();
                            output.writeUTF(Integer.toString(tweetId));
                            output.flush();
                            output.writeUTF(Integer.toString(LoggedInUser.getID()));
                            output.flush();
                            i.setLikesCount(i.getLikesCount() + 1);
                            likesButton.setText("Likes " + (i.getLikesCount()));
                            this.revalidate();
                            this.repaint();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                } else if (command.startsWith("COMMENT_")) {
                    int tweetId = Integer.parseInt(command.substring(8));
                    if (commentArea.getText().isEmpty())
                        JOptionPane.showMessageDialog(null, "Fill the comment area first!", "Error", JOptionPane.WARNING_MESSAGE);
                    else {
                        try {
                            output.writeUTF("ADD_COMMENT");
                            output.flush();
                            output.writeUTF(Integer.toString(tweetId));
                            output.flush();
                            output.writeUTF(Integer.toString(LoggedInUser.getID()));
                            output.flush();
                            output.writeUTF(commentArea.getText());
                            output.flush();
                            i.setCommentsCount(i.getCommentsCount() + 1);
                            commentsButton.setText("Comments " + (i.getCommentsCount()));
                            commentArea.setText("");
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                } else if (command.startsWith("LIKES_")) {
                    int tweetId = Integer.parseInt(command.substring(6));
                    if (i.getLikesCount() == 0)
                        return;
                    JFrame likesListFrame = new JFrame();
                    likesListFrame.setIconImage(user.getImage());
                    likesListFrame.setTitle("Likes");
                    likesListFrame.setLayout(new BorderLayout());
                    likesListFrame.setMinimumSize(new Dimension(400, 500));
                    likesListFrame.setResizable(false);
                    likesListFrame.toFront();
                    JPanel likesListPanel = new JPanel();

                    tweetLikes.clear();

                    try {
                        output.writeUTF("GET_TWEET_LIKES");
                        output.flush();
                        output.writeUTF(Integer.toString(tweetId));
                        output.flush();
                        User temp;
                        while (true) {
                            temp = (User) input.readObject();
                            if (temp == null)
                                break;
                            tweetLikes.add(temp);
                        }
                    } catch (IOException | ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }

                    likesListPanel.setBackground(new Color(0, 0, 0));
                    likesListPanel.setLayout(new BoxLayout(likesListPanel, BoxLayout.Y_AXIS));
                    JScrollPane likesListPanelScroll = new JScrollPane(likesListPanel);
                    JPanel userInfo;

                    for (User x : tweetLikes) {

                        userInfo = new JPanel();
                        userInfo.setBorder(blackborder);
                        userInfo.setLayout(new GridLayout(1, 2));
                        userInfo.setPreferredSize(new Dimension(400, 50));
                        userInfo.setMaximumSize(new Dimension(400, 50));

                        haveFollow = false;
                        try {
                            output.writeUTF("HAVE_FOLLOW");
                            output.flush();
                            output.writeUTF(Integer.toString(x.getID()));
                            output.flush();
                            haveFollow = Boolean.parseBoolean(input.readUTF());
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }

                        JButton userName = new JButton();
                        userName.setText("<html>First name: " + x.getFirstName() + "<br>" + "Last name: " + x.getLastname() + "<br>" + "Username: " + x.getUsername() + "</html>");
                        userName.setBorder(cyanborder);
                        userName.setBackground(Color.WHITE);
                        userName.setForeground(Color.black);
                        userName.setFocusable(false);
                        JButton followStatus = new JButton();
                        followStatus.setFocusable(false);
                        followStatus.setBorder(cyanborder);
                        followStatus.setBackground(Color.black);
                        followStatus.setForeground(Color.cyan);
                        if (x.getID() == LoggedInUser.getID())
                            followStatus.setEnabled(false);
                        if (haveFollow) {
                            followStatus.setText("unFollow");
                        } else {
                            followStatus.setText("Follow");
                        }

                        followStatus.setActionCommand("FOLLOW_" + x.getID());
                        userName.setActionCommand("USER_" + x.getID());

                        ActionListener listButtonActionListener = eq -> {

                            try {
                                output.writeUTF("HAVE_FOLLOW");
                                output.flush();
                                output.writeUTF(Integer.toString(x.getID()));
                                output.flush();
                                haveFollow = Boolean.parseBoolean(input.readUTF());
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }

                            String listCommand = eq.getActionCommand();
                            if (listCommand.startsWith("FOLLOW_")) {
                                int userID = Integer.parseInt(listCommand.substring(7));
                                if (haveFollow) {
                                    followStatus.setText("Follow");
                                    try {
                                        output.writeUTF("REMOVE_FOLLOWING");
                                        output.flush();
                                        output.writeUTF(Integer.toString(userID));
                                        output.flush();
                                        LoggedInUser.setFollowingsCount(LoggedInUser.getFollowingsCount() - 1);
                                        x.setFollowersCount(x.getFollowersCount() - 1);
                                    } catch (IOException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                } else {
                                    followStatus.setText("unFollow");
                                    try {
                                        output.writeUTF("ADD_FOLLOWING");
                                        output.flush();
                                        output.writeUTF(Integer.toString(userID));
                                        output.flush();
                                        LoggedInUser.setFollowingsCount(LoggedInUser.getFollowingsCount() + 1);
                                        x.setFollowersCount(x.getFollowersCount() + 1);
                                    } catch (IOException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                }
                            } else if (listCommand.startsWith("USER_")) {
                                int userID = Integer.parseInt(listCommand.substring(5));
                                try {
                                    output.writeUTF("FIND_USER");
                                    output.flush();
                                    output.writeUTF(Integer.toString(userID));
                                    output.flush();
                                    new UserPage(socket, input, output, this.getX(), this.getY(), this.getWidth(), this.getHeight(), (User) input.readObject(), LoggedInUser);
                                    this.dispose();
                                    likesListFrame.dispose();
                                } catch (IOException | ClassNotFoundException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }

                        };

                        followStatus.addActionListener(listButtonActionListener);
                        userName.addActionListener(listButtonActionListener);

                        userInfo.add(userName);
                        userInfo.add(followStatus);
                        likesListPanel.add(userInfo);
                    }

                    likesListFrame.add(likesListPanelScroll);
                    likesListFrame.pack();
                    likesListFrame.setVisible(true);

                } else if (command.startsWith("SAVES_")) {
                    int tweetId = Integer.parseInt(command.substring(6));
                    if (i.getSavesCount() == 0)
                        return;
                    JFrame savesListFrame = new JFrame();
                    savesListFrame.setIconImage(user.getImage());
                    savesListFrame.setTitle("Saves");
                    savesListFrame.setLayout(new BorderLayout());
                    savesListFrame.setMinimumSize(new Dimension(400, 500));
                    savesListFrame.setResizable(false);
                    savesListFrame.toFront();
                    JPanel savesListPanel = new JPanel();

                    tweetSaves.clear();

                    try {
                        output.writeUTF("GET_TWEET_SAVES");
                        output.flush();
                        output.writeUTF(Integer.toString(tweetId));
                        output.flush();
                        User temp;
                        while (true) {
                            temp = (User) input.readObject();
                            if (temp == null)
                                break;
                            tweetSaves.add(temp);
                        }
                    } catch (IOException | ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }

                    savesListPanel.setBackground(new Color(0, 0, 0));
                    savesListPanel.setLayout(new BoxLayout(savesListPanel, BoxLayout.Y_AXIS));
                    JScrollPane likesListPanelScroll = new JScrollPane(savesListPanel);
                    JPanel userInfo;

                    for (User x : tweetSaves) {

                        userInfo = new JPanel();
                        userInfo.setBorder(blackborder);
                        userInfo.setLayout(new GridLayout(1, 2));
                        userInfo.setPreferredSize(new Dimension(400, 50));
                        userInfo.setMaximumSize(new Dimension(400, 50));

                        haveFollow = false;
                        try {
                            output.writeUTF("HAVE_FOLLOW");
                            output.flush();
                            output.writeUTF(Integer.toString(x.getID()));
                            output.flush();
                            haveFollow = Boolean.parseBoolean(input.readUTF());
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }

                        JButton userName = new JButton();
                        userName.setText("<html>First name: " + x.getFirstName() + "<br>" + "Last name: " + x.getLastname() + "<br>" + "Username: " + x.getUsername() + "</html>");
                        userName.setBorder(cyanborder);
                        userName.setBackground(Color.WHITE);
                        userName.setForeground(Color.black);
                        userName.setFocusable(false);
                        JButton followStatus = new JButton();
                        followStatus.setBorder(cyanborder);
                        followStatus.setBackground(Color.black);
                        followStatus.setForeground(Color.cyan);
                        if (x.getID() == LoggedInUser.getID())
                            followStatus.setEnabled(false);
                        if (haveFollow) {
                            followStatus.setText("unFollow");
                        } else {
                            followStatus.setText("Follow");
                        }

                        followStatus.setActionCommand("FOLLOW_" + x.getID());
                        userName.setActionCommand("USER_" + x.getID());

                        ActionListener listButtonActionListener = eq -> {

                            try {
                                output.writeUTF("HAVE_FOLLOW");
                                output.flush();
                                output.writeUTF(Integer.toString(x.getID()));
                                output.flush();
                                haveFollow = Boolean.parseBoolean(input.readUTF());
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }

                            String listCommand = eq.getActionCommand();
                            if (listCommand.startsWith("FOLLOW_")) {
                                int userID = Integer.parseInt(listCommand.substring(7));
                                if (haveFollow) {
                                    followStatus.setText("Follow");
                                    try {
                                        output.writeUTF("REMOVE_FOLLOWING");
                                        output.flush();
                                        output.writeUTF(Integer.toString(userID));
                                        output.flush();
                                        LoggedInUser.setFollowingsCount(LoggedInUser.getFollowingsCount() - 1);
                                        x.setFollowersCount(x.getFollowersCount() - 1);
                                    } catch (IOException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                } else {
                                    followStatus.setText("unFollow");
                                    try {
                                        output.writeUTF("ADD_FOLLOWING");
                                        output.flush();
                                        output.writeUTF(Integer.toString(userID));
                                        output.flush();
                                        LoggedInUser.setFollowingsCount(LoggedInUser.getFollowingsCount() + 1);
                                        x.setFollowersCount(x.getFollowersCount() + 1);
                                    } catch (IOException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                }
                            } else if (listCommand.startsWith("USER_")) {
                                int userID = Integer.parseInt(listCommand.substring(5));
                                try {
                                    output.writeUTF("FIND_USER");
                                    output.flush();
                                    output.writeUTF(Integer.toString(userID));
                                    output.flush();
                                    new UserPage(socket, input, output, this.getX(), this.getY(), this.getWidth(), this.getHeight(), (User) input.readObject(), LoggedInUser);
                                    this.dispose();
                                    savesListFrame.dispose();
                                } catch (IOException | ClassNotFoundException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }
                        };
                        followStatus.addActionListener(listButtonActionListener);
                        userName.addActionListener(listButtonActionListener);

                        userInfo.add(userName);
                        userInfo.add(followStatus);
                        savesListPanel.add(userInfo);
                    }

                    savesListFrame.add(likesListPanelScroll);
                    savesListFrame.pack();
                    savesListFrame.setVisible(true);

                } else if (command.startsWith("COMMENTS_")) {
                    int tweetId = Integer.parseInt(command.substring(9));

                    JFrame commentsListFrame = new JFrame();
                    commentsListFrame.setIconImage(user.getImage());
                    commentsListFrame.setTitle("Comments");
                    commentsListFrame.setLayout(new BorderLayout());
                    commentsListFrame.setMinimumSize(new Dimension(500, 500));
                    commentsListFrame.setResizable(false);
                    commentsListFrame.toFront();
                    JPanel commentsListPanel = new JPanel();

                    tweetComments.clear();

                    try {
                        output.writeUTF("GET_TWEET_COMMENTS");
                        output.flush();
                        output.writeUTF(Integer.toString(tweetId));
                        output.flush();
                        Comment temp;
                        while (true) {
                            temp = (Comment) input.readObject();
                            if (temp == null)
                                break;
                            tweetComments.add(temp);
                        }
                    } catch (IOException | ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }

                    if (tweetComments.isEmpty())
                        return;

                    commentsListPanel.setBackground(new Color(0, 0, 0));
                    commentsListPanel.setLayout(new BoxLayout(commentsListPanel, BoxLayout.Y_AXIS));
                    JScrollPane likesListPanelScroll = new JScrollPane(commentsListPanel);
                    JPanel userInfo;

                    for (Comment x : tweetComments) {

                        userInfo = new JPanel();
                        userInfo.setBorder(blackborder);
                        userInfo.setLayout(new GridLayout(1, 2));
                        userInfo.setPreferredSize(new Dimension(500, 50));
                        userInfo.setMaximumSize(new Dimension(500, 50));

                        haveFollow = false;
                        try {
                            output.writeUTF("HAVE_FOLLOW");
                            output.flush();
                            output.writeUTF(Integer.toString(x.getUser().getID()));
                            output.flush();
                            haveFollow = Boolean.parseBoolean(input.readUTF());
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }

                        JButton userName = new JButton();
                        userName.setText("<html>First name: " + x.getUser().getFirstName() + "<br>" + "Last name: " + x.getUser().getLastname() + "<br>" + "Username: " + x.getUser().getUsername() + "</html>");
                        userName.setBorder(cyanborder);
                        userName.setBackground(Color.WHITE);
                        userName.setForeground(Color.black);
                        userName.setFocusable(false);
                        JButton followStatus = new JButton();
                        followStatus.setBorder(cyanborder);
                        followStatus.setBackground(Color.black);
                        followStatus.setForeground(Color.cyan);
                        if (x.getUser().getID() == LoggedInUser.getID())
                            followStatus.setEnabled(false);
                        if (haveFollow) {
                            followStatus.setText("unFollow");
                        } else {
                            followStatus.setText("Follow");
                        }

                        JTextArea commentText = new JTextArea(x.getComment());
                        commentText.setPreferredSize(new Dimension(30, 50));
                        commentText.setBackground(Color.black);
                        commentText.setForeground(Color.white);
                        commentText.setLineWrap(true);
                        commentText.setEditable(false);
                        commentText.setFont(new Font(null, Font.PLAIN, 17));
                        JScrollPane commentTextScrollPane = new JScrollPane(commentText);
                        commentTextScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

                        followStatus.setActionCommand("FOLLOW_" + x.getUser().getID());
                        userName.setActionCommand("USER_" + x.getUser().getID());

                        ActionListener listButtonActionListener = eq -> {

                            try {
                                output.writeUTF("HAVE_FOLLOW");
                                output.flush();
                                output.writeUTF(Integer.toString(x.getUser().getID()));
                                output.flush();
                                haveFollow = Boolean.parseBoolean(input.readUTF());
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }

                            String listCommand = eq.getActionCommand();
                            if (listCommand.startsWith("FOLLOW_")) {
                                int userID = Integer.parseInt(listCommand.substring(7));
                                if (haveFollow) {
                                    followStatus.setText("Follow");
                                    try {
                                        output.writeUTF("REMOVE_FOLLOWING");
                                        output.flush();
                                        output.writeUTF(Integer.toString(userID));
                                        output.flush();
                                        LoggedInUser.setFollowingsCount(LoggedInUser.getFollowingsCount() - 1);
                                        x.getUser().setFollowersCount(x.getUser().getFollowersCount() - 1);
                                    } catch (IOException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                } else {
                                    followStatus.setText("unFollow");
                                    try {
                                        output.writeUTF("ADD_FOLLOWING");
                                        output.flush();
                                        output.writeUTF(Integer.toString(userID));
                                        output.flush();
                                        LoggedInUser.setFollowingsCount(LoggedInUser.getFollowingsCount() + 1);
                                        x.getUser().setFollowersCount(x.getUser().getFollowersCount() + 1);
                                    } catch (IOException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                }
                            } else if (listCommand.startsWith("USER_")) {
                                int userID = Integer.parseInt(listCommand.substring(5));
                                try {
                                    output.writeUTF("FIND_USER");
                                    output.flush();
                                    output.writeUTF(Integer.toString(userID));
                                    output.flush();
                                    new UserPage(socket, input, output, this.getX(), this.getY(), this.getWidth(), this.getHeight(), (User) input.readObject(), LoggedInUser);
                                    this.dispose();
                                    commentsListFrame.dispose();
                                } catch (IOException | ClassNotFoundException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }

                        };

                        followStatus.addActionListener(listButtonActionListener);
                        userName.addActionListener(listButtonActionListener);

                        userInfo.add(userName);
                        userInfo.add(commentTextScrollPane);
                        userInfo.add(followStatus);
                        commentsListPanel.add(userInfo);
                    }

                    commentsListFrame.add(likesListPanelScroll);
                    commentsListFrame.pack();
                    commentsListFrame.setVisible(true);
                }

            };

            userButton.addActionListener(buttonActionListener);
            deleteButton.addActionListener(buttonActionListener);
            saveButton.addActionListener(buttonActionListener);
            likeButton.addActionListener(buttonActionListener);
            commentButton.addActionListener(buttonActionListener);
            likesButton.addActionListener(buttonActionListener);
            savesButton.addActionListener(buttonActionListener);
            commentsButton.addActionListener(buttonActionListener);

            JPanel tweetDown = new JPanel();
            tweetDown.setPreferredSize(new Dimension((this.getWidth() - 5) / 2, 55));
            tweetDown.setLayout(new GridLayout(2, 3));

            tweetDown.add(CommentScrollPane);
            tweetDown.add(commentButton);
            tweetDown.add(likeButton);
            tweetDown.add(saveButton);
            tweetDown.add(releaseTime);
            tweetDown.add(commentsButton);
            tweetDown.add(likesButton);
            tweetDown.add(savesButton);

            textPanel.add(tweetDown, BorderLayout.SOUTH);
            tweetPanel.add(textPanel);

        }

        tweetPanel.revalidate();
        tweetPanel.repaint();

        Tweet.add(scrollPane, BorderLayout.CENTER);
        allGrid.add(Tweet);

        JPanel RightPage = new JPanel();
        RightPage.setLayout(new BorderLayout());
        RightPage.setBackground(Color.black);

        JPanel rightTweet = new JPanel();
        rightTweet.setLayout(new BorderLayout(50, 50));

        JPanel rightTweetPanel = new JPanel();
        rightTweetPanel.setBackground(new Color(0, 0, 0));
        rightTweetPanel.setLayout(new BoxLayout(rightTweetPanel, BoxLayout.Y_AXIS));

        JScrollPane rightScrollPane = new JScrollPane(rightTweetPanel);

        userLikesButton.addActionListener(ez -> {

            favTweetsList.clear();

            try {
                output.writeUTF("GET_LIKED_TWEETS");
                output.flush();
                output.writeUTF(Integer.toString(LoggedInUser.getID()));
                output.flush();
                Twits temp;
                while (true) {
                    temp = (Twits) input.readObject();
                    if (temp == null)
                        break;
                    favTweetsList.add(temp);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            for (Twits i : favTweetsList) {

                JButton likeButton, saveButton, likesButton, savesButton, commentsButton, commentButton, userButton, deleteButton;

                JPanel textPanel = new JPanel();
                textPanel.setLayout(new BorderLayout(10, 10));
                textPanel.setBackground(Color.black);
                textPanel.setPreferredSize(new Dimension((this.getWidth() - 70) / 2, 200));
                textPanel.setMaximumSize(new Dimension((this.getWidth() - 70) / 2, 200));

                textPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 10));

                JTextArea tweetText = new JTextArea(i.getText());
                tweetText.setPreferredSize(new Dimension(this.getWidth() - 20, 130));
                tweetText.setBackground(Color.black);
                tweetText.setForeground(Color.white);
                tweetText.setLineWrap(true);
                tweetText.setEditable(false);
                tweetText.setFont(new Font(null, Font.PLAIN, 21));
                JScrollPane TweetScrollPane = new JScrollPane(tweetText);
                TweetScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                textPanel.add(TweetScrollPane, BorderLayout.CENTER);

                JPanel userPanel = new JPanel();
                userPanel.setPreferredSize(new Dimension((this.getWidth() - 5) / 2, 43));
                userPanel.setLayout(new FlowLayout());
                userButton = new JButton();
                userButton.setBackground(Color.cyan);
                userButton.setFocusable(false);
                userButton.setText("First name : " + i.getUser().getFirstName() + "     ,     " + "Username : " + i.getUser().getUsername());
                userButton.setFont(new Font(Font.MONOSPACED, Font.BOLD, 16));
                deleteButton = new JButton();
                deleteButton.setText("Delete");
                deleteButton.setFont(new Font(Font.MONOSPACED, Font.BOLD, 16));
                deleteButton.setMaximumSize(new Dimension(30,30));
                deleteButton.setBackground(Color.red);
                deleteButton.setForeground(Color.white);
                deleteButton.setFocusable(false);
                userPanel.setBorder(blackborder);
                userPanel.add(userButton);
                userPanel.add(deleteButton);
                textPanel.add(userPanel, BorderLayout.NORTH);

                if (person.getID() != LoggedInUser.getID()) {
                    deleteButton.setBackground(Color.gray);
                    deleteButton.setEnabled(false);
                }

                if (i.getUser().getID() != LoggedInUser.getID()) {
                    deleteButton.setBackground(Color.gray);
                    deleteButton.setEnabled(false);
                }

                likeButton = new JButton();
                likeButton.setIcon(nullHeart);
                likeButton.setBackground(new Color(190, 190, 240));
                likeButton.setBorder(blackborder);
                likeButton.setFocusable(false);
                saveButton = new JButton();
                saveButton.setIcon(nullSaved);
                saveButton.setBackground(new Color(190, 190, 240));
                saveButton.setBorder(blackborder);
                saveButton.setFocusable(false);
                commentButton = new JButton("Comment");
                commentButton.setIcon(sender);
                commentButton.setBackground(new Color(190, 190, 240));
                commentButton.setBorder(blackborder);
                commentButton.setFocusable(false);
                likesButton = new JButton();
                likesButton.setBackground(new Color(190, 190, 240));
                likesButton.setBorder(blackborder);
                likesButton.setFocusable(false);
                savesButton = new JButton("Saves " + i.getSavesCount());
                savesButton.setBackground(new Color(190, 190, 240));
                savesButton.setBorder(blackborder);
                savesButton.setFocusable(false);
                commentsButton = new JButton("Comments " + i.getCommentsCount());
                commentsButton.setIcon(commentsLogo);
                commentsButton.setBackground(new Color(190, 190, 240));
                commentsButton.setBorder(blackborder);
                commentsButton.setFocusable(false);
                JTextArea commentArea = new JTextArea();
                commentArea.setFont(new Font(Font.DIALOG, Font.PLAIN, 15));
                commentArea.setLineWrap(true);
                commentArea.setBorder(blackborder);
                JScrollPane CommentScrollPane = new JScrollPane(commentArea);
                CommentScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                JLabel releaseTime = new JLabel(String.valueOf(i.getTime()));
                releaseTime.setForeground(Color.blue);
                releaseTime.setBorder(blackborder);

                try {
                    output.writeUTF("IS_LIKED");
                    output.flush();
                    output.writeUTF(Integer.toString(i.getTweetID()));
                    output.flush();
                    output.writeUTF(Integer.toString(LoggedInUser.getID()));
                    output.flush();
                    isLiked = Boolean.parseBoolean(input.readUTF());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                if (isLiked) {
                    likeButton.setIcon(redHeart);
                    likesButton.setText("Likes " + i.getLikesCount());
                } else {
                    likeButton.setIcon(nullHeart);
                    likesButton.setText("Likes " + i.getLikesCount());
                }

                try {
                    output.writeUTF("IS_SAVED");
                    output.flush();
                    output.writeUTF(Integer.toString(i.getTweetID()));
                    output.flush();
                    output.writeUTF(Integer.toString(LoggedInUser.getID()));
                    output.flush();
                    isSaved = Boolean.parseBoolean(input.readUTF());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                if (isSaved) {
                    saveButton.setIcon(smallSaveFlag);
                    savesButton.setText("Saves " + i.getSavesCount());
                } else {
                    saveButton.setIcon(nullSaved);
                    savesButton.setText("Saves " + i.getSavesCount());
                }

                if (!i.isSave()) {
                    saveButton.setEnabled(false);
                    saveButton.setEnabled(false);
                }
                if (!i.isComment()) {
                    commentButton.setEnabled(false);
                    commentArea.setFocusable(false);
                }

                userButton.setActionCommand("USER_" + i.getUser().getID());
                deleteButton.setActionCommand("DELETE_" + i.getTweetID());
                saveButton.setActionCommand("SAVE_" + i.getTweetID());
                likeButton.setActionCommand("LIKE_" + i.getTweetID());
                commentButton.setActionCommand("COMMENT_" + i.getTweetID());
                likesButton.setActionCommand("LIKES_" + i.getTweetID());
                savesButton.setActionCommand("SAVES_" + i.getTweetID());
                commentsButton.setActionCommand("COMMENTS_" + i.getTweetID());

                ActionListener buttonActionListener = e -> {
                    try {
                        output.writeUTF("IS_LIKED");
                        output.flush();
                        output.writeUTF(Integer.toString(i.getTweetID()));
                        output.flush();
                        output.writeUTF(Integer.toString(LoggedInUser.getID()));
                        output.flush();
                        isLiked = Boolean.parseBoolean(input.readUTF());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    try {
                        output.writeUTF("IS_SAVED");
                        output.flush();
                        output.writeUTF(Integer.toString(i.getTweetID()));
                        output.flush();
                        output.writeUTF(Integer.toString(LoggedInUser.getID()));
                        output.flush();
                        isSaved = Boolean.parseBoolean(input.readUTF());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    String command = e.getActionCommand();
                    if (command.startsWith("USER_")) {
                        int userID = Integer.parseInt(command.substring(5));
                        try {
                            output.writeUTF("FIND_USER");
                            output.flush();
                            output.writeUTF(Integer.toString(userID));
                            output.flush();
                            new UserPage(socket, input, output, this.getX(), this.getY(), this.getWidth(), this.getHeight(), (User) input.readObject(), LoggedInUser);
                            this.dispose();
                        } catch (IOException | ClassNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    else if (command.startsWith("DELETE_")) {
                        int tweetId = Integer.parseInt(command.substring(7));
                        try {
                            output.writeUTF("REMOVE_TWEET");
                            output.flush();
                            output.writeUTF(String.valueOf(tweetId));
                            output.flush();
                            this.repaint();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    else if (command.startsWith("SAVE_")) {
                        int tweetId = Integer.parseInt(command.substring(5));
                        if (isSaved) {
                            try {
                                output.writeUTF("REMOVE_SAVE");
                                output.flush();
                                output.writeUTF(String.valueOf(tweetId));
                                output.flush();
                                output.writeUTF(Integer.toString(LoggedInUser.getID()));
                                output.flush();
                                i.setSavesCount(i.getSavesCount() - 1);
                                savesButton.setText("Saves " + (i.getSavesCount()));
                                this.revalidate();
                                this.repaint();
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        } else {
                            try {
                                output.writeUTF("ADD_SAVE");
                                output.flush();
                                output.writeUTF(Integer.toString(tweetId));
                                output.flush();
                                output.writeUTF(Integer.toString(LoggedInUser.getID()));
                                output.flush();
                                i.setSavesCount(i.getSavesCount() + 1);
                                savesButton.setText("Saves " + (i.getSavesCount()));
                                this.revalidate();
                                this.repaint();
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    } else if (command.startsWith("LIKE_")) {
                        int tweetId = Integer.parseInt(command.substring(5));
                        if (isLiked) {
                            try {
                                output.writeUTF("REMOVE_LIKE");
                                output.flush();
                                output.writeUTF(String.valueOf(tweetId));
                                output.flush();
                                output.writeUTF(Integer.toString(LoggedInUser.getID()));
                                output.flush();
                                i.setLikesCount(i.getLikesCount() - 1);
                                likesButton.setText("Likes " + (i.getLikesCount()));
                                this.revalidate();
                                this.repaint();
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        } else {
                            try {
                                output.writeUTF("ADD_LIKE");
                                output.flush();
                                output.writeUTF(Integer.toString(tweetId));
                                output.flush();
                                output.writeUTF(Integer.toString(LoggedInUser.getID()));
                                output.flush();
                                i.setLikesCount(i.getLikesCount() + 1);
                                likesButton.setText("Likes " + (i.getLikesCount()));
                                this.revalidate();
                                this.repaint();
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    } else if (command.startsWith("COMMENT_")) {
                        int tweetId = Integer.parseInt(command.substring(8));
                        if (commentArea.getText().isEmpty())
                            JOptionPane.showMessageDialog(null, "Fill the comment area first!", "Error", JOptionPane.WARNING_MESSAGE);
                        else {
                            try {
                                output.writeUTF("ADD_COMMENT");
                                output.flush();
                                output.writeUTF(Integer.toString(tweetId));
                                output.flush();
                                output.writeUTF(Integer.toString(LoggedInUser.getID()));
                                output.flush();
                                output.writeUTF(commentArea.getText());
                                output.flush();
                                i.setCommentsCount(i.getCommentsCount() + 1);
                                commentsButton.setText("Comments " + (i.getCommentsCount()));
                                commentArea.setText("");
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    } else if (command.startsWith("LIKES_")) {
                        int tweetId = Integer.parseInt(command.substring(6));
                        if (i.getLikesCount() == 0)
                            return;
                        JFrame likesListFrame = new JFrame();
                        likesListFrame.setIconImage(user.getImage());
                        likesListFrame.setTitle("Likes");
                        likesListFrame.setLayout(new BorderLayout());
                        likesListFrame.setMinimumSize(new Dimension(400, 500));
                        likesListFrame.setResizable(false);
                        likesListFrame.toFront();
                        JPanel likesListPanel = new JPanel();

                        tweetLikes.clear();

                        try {
                            output.writeUTF("GET_TWEET_LIKES");
                            output.flush();
                            output.writeUTF(Integer.toString(tweetId));
                            output.flush();
                            User temp;
                            while (true) {
                                temp = (User) input.readObject();
                                if (temp == null)
                                    break;
                                tweetLikes.add(temp);
                            }
                        } catch (IOException | ClassNotFoundException ex) {
                            ex.printStackTrace();
                        }

                        likesListPanel.setBackground(new Color(0, 0, 0));
                        likesListPanel.setLayout(new BoxLayout(likesListPanel, BoxLayout.Y_AXIS));
                        JScrollPane likesListPanelScroll = new JScrollPane(likesListPanel);
                        JPanel userInfo;

                        for (User x : tweetLikes) {

                            userInfo = new JPanel();
                            userInfo.setBorder(blackborder);
                            userInfo.setLayout(new GridLayout(1, 2));
                            userInfo.setPreferredSize(new Dimension(400, 50));
                            userInfo.setMaximumSize(new Dimension(400, 50));

                            haveFollow = false;
                            try {
                                output.writeUTF("HAVE_FOLLOW");
                                output.flush();
                                output.writeUTF(Integer.toString(x.getID()));
                                output.flush();
                                haveFollow = Boolean.parseBoolean(input.readUTF());
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }

                            JButton userName = new JButton();
                            userName.setText("<html>First name: " + x.getFirstName() + "<br>" + "Last name: " + x.getLastname() + "<br>" + "Username: " + x.getUsername() + "</html>");
                            userName.setBorder(cyanborder);
                            userName.setBackground(Color.WHITE);
                            userName.setForeground(Color.black);
                            userName.setFocusable(false);
                            JButton followStatus = new JButton();
                            followStatus.setFocusable(false);
                            followStatus.setBorder(cyanborder);
                            followStatus.setBackground(Color.black);
                            followStatus.setForeground(Color.cyan);
                            if (x.getID() == LoggedInUser.getID())
                                followStatus.setEnabled(false);
                            if (haveFollow) {
                                followStatus.setText("unFollow");
                            } else {
                                followStatus.setText("Follow");
                            }

                            followStatus.setActionCommand("FOLLOW_" + x.getID());
                            userName.setActionCommand("USER_" + x.getID());

                            ActionListener listButtonActionListener = eq -> {

                                try {
                                    output.writeUTF("HAVE_FOLLOW");
                                    output.flush();
                                    output.writeUTF(Integer.toString(x.getID()));
                                    output.flush();
                                    haveFollow = Boolean.parseBoolean(input.readUTF());
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }

                                String listCommand = eq.getActionCommand();
                                if (listCommand.startsWith("FOLLOW_")) {
                                    int userID = Integer.parseInt(listCommand.substring(7));
                                    if (haveFollow) {
                                        followStatus.setText("Follow");
                                        try {
                                            output.writeUTF("REMOVE_FOLLOWING");
                                            output.flush();
                                            output.writeUTF(Integer.toString(userID));
                                            output.flush();
                                            LoggedInUser.setFollowingsCount(LoggedInUser.getFollowingsCount() - 1);
                                            x.setFollowersCount(x.getFollowersCount() - 1);
                                        } catch (IOException ex) {
                                            throw new RuntimeException(ex);
                                        }
                                    } else {
                                        followStatus.setText("unFollow");
                                        try {
                                            output.writeUTF("ADD_FOLLOWING");
                                            output.flush();
                                            output.writeUTF(Integer.toString(userID));
                                            output.flush();
                                            LoggedInUser.setFollowingsCount(LoggedInUser.getFollowingsCount() + 1);
                                            x.setFollowersCount(x.getFollowersCount() + 1);
                                        } catch (IOException ex) {
                                            throw new RuntimeException(ex);
                                        }
                                    }
                                } else if (listCommand.startsWith("USER_")) {
                                    int userID = Integer.parseInt(listCommand.substring(5));
                                    try {
                                        output.writeUTF("FIND_USER");
                                        output.flush();
                                        output.writeUTF(Integer.toString(userID));
                                        output.flush();
                                        new UserPage(socket, input, output, this.getX(), this.getY(), this.getWidth(), this.getHeight(), (User) input.readObject(), LoggedInUser);
                                        this.dispose();
                                        likesListFrame.dispose();
                                    } catch (IOException | ClassNotFoundException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                }

                            };

                            followStatus.addActionListener(listButtonActionListener);
                            userName.addActionListener(listButtonActionListener);

                            userInfo.add(userName);
                            userInfo.add(followStatus);
                            likesListPanel.add(userInfo);
                        }

                        likesListFrame.add(likesListPanelScroll);
                        likesListFrame.pack();
                        likesListFrame.setVisible(true);

                    } else if (command.startsWith("SAVES_")) {
                        int tweetId = Integer.parseInt(command.substring(6));
                        if (i.getSavesCount() == 0)
                            return;
                        JFrame savesListFrame = new JFrame();
                        savesListFrame.setIconImage(user.getImage());
                        savesListFrame.setTitle("Saves");
                        savesListFrame.setLayout(new BorderLayout());
                        savesListFrame.setMinimumSize(new Dimension(400, 500));
                        savesListFrame.setResizable(false);
                        savesListFrame.toFront();
                        JPanel savesListPanel = new JPanel();

                        tweetSaves.clear();

                        try {
                            output.writeUTF("GET_TWEET_SAVES");
                            output.flush();
                            output.writeUTF(Integer.toString(tweetId));
                            output.flush();
                            User temp;
                            while (true) {
                                temp = (User) input.readObject();
                                if (temp == null)
                                    break;
                                tweetSaves.add(temp);
                            }
                        } catch (IOException | ClassNotFoundException ex) {
                            ex.printStackTrace();
                        }

                        savesListPanel.setBackground(new Color(0, 0, 0));
                        savesListPanel.setLayout(new BoxLayout(savesListPanel, BoxLayout.Y_AXIS));
                        JScrollPane likesListPanelScroll = new JScrollPane(savesListPanel);
                        JPanel userInfo;

                        for (User x : tweetSaves) {

                            userInfo = new JPanel();
                            userInfo.setBorder(blackborder);
                            userInfo.setLayout(new GridLayout(1, 2));
                            userInfo.setPreferredSize(new Dimension(400, 50));
                            userInfo.setMaximumSize(new Dimension(400, 50));

                            haveFollow = false;
                            try {
                                output.writeUTF("HAVE_FOLLOW");
                                output.flush();
                                output.writeUTF(Integer.toString(x.getID()));
                                output.flush();
                                haveFollow = Boolean.parseBoolean(input.readUTF());
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }

                            JButton userName = new JButton();
                            userName.setText("<html>First name: " + x.getFirstName() + "<br>" + "Last name: " + x.getLastname() + "<br>" + "Username: " + x.getUsername() + "</html>");
                            userName.setBorder(cyanborder);
                            userName.setBackground(Color.WHITE);
                            userName.setForeground(Color.black);
                            userName.setFocusable(false);
                            JButton followStatus = new JButton();
                            followStatus.setBorder(cyanborder);
                            followStatus.setBackground(Color.black);
                            followStatus.setForeground(Color.cyan);
                            if (x.getID() == LoggedInUser.getID())
                                followStatus.setEnabled(false);
                            if (haveFollow) {
                                followStatus.setText("unFollow");
                            } else {
                                followStatus.setText("Follow");
                            }

                            followStatus.setActionCommand("FOLLOW_" + x.getID());
                            userName.setActionCommand("USER_" + x.getID());

                            ActionListener listButtonActionListener = eq -> {

                                try {
                                    output.writeUTF("HAVE_FOLLOW");
                                    output.flush();
                                    output.writeUTF(Integer.toString(x.getID()));
                                    output.flush();
                                    haveFollow = Boolean.parseBoolean(input.readUTF());
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }

                                String listCommand = eq.getActionCommand();
                                if (listCommand.startsWith("FOLLOW_")) {
                                    int userID = Integer.parseInt(listCommand.substring(7));
                                    if (haveFollow) {
                                        followStatus.setText("Follow");
                                        try {
                                            output.writeUTF("REMOVE_FOLLOWING");
                                            output.flush();
                                            output.writeUTF(Integer.toString(userID));
                                            output.flush();
                                            LoggedInUser.setFollowingsCount(LoggedInUser.getFollowingsCount() - 1);
                                            x.setFollowersCount(x.getFollowersCount() - 1);
                                        } catch (IOException ex) {
                                            throw new RuntimeException(ex);
                                        }
                                    } else {
                                        followStatus.setText("unFollow");
                                        try {
                                            output.writeUTF("ADD_FOLLOWING");
                                            output.flush();
                                            output.writeUTF(Integer.toString(userID));
                                            output.flush();
                                            LoggedInUser.setFollowingsCount(LoggedInUser.getFollowingsCount() + 1);
                                            x.setFollowersCount(x.getFollowersCount() + 1);
                                        } catch (IOException ex) {
                                            throw new RuntimeException(ex);
                                        }
                                    }
                                } else if (listCommand.startsWith("USER_")) {
                                    int userID = Integer.parseInt(listCommand.substring(5));
                                    try {
                                        output.writeUTF("FIND_USER");
                                        output.flush();
                                        output.writeUTF(Integer.toString(userID));
                                        output.flush();
                                        new UserPage(socket, input, output, this.getX(), this.getY(), this.getWidth(), this.getHeight(), (User) input.readObject(), LoggedInUser);
                                        this.dispose();
                                        savesListFrame.dispose();
                                    } catch (IOException | ClassNotFoundException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                }
                            };
                            followStatus.addActionListener(listButtonActionListener);
                            userName.addActionListener(listButtonActionListener);

                            userInfo.add(userName);
                            userInfo.add(followStatus);
                            savesListPanel.add(userInfo);
                        }

                        savesListFrame.add(likesListPanelScroll);
                        savesListFrame.pack();
                        savesListFrame.setVisible(true);

                    } else if (command.startsWith("COMMENTS_")) {
                        int tweetId = Integer.parseInt(command.substring(9));

                        JFrame commentsListFrame = new JFrame();
                        commentsListFrame.setIconImage(user.getImage());
                        commentsListFrame.setTitle("Comments");
                        commentsListFrame.setLayout(new BorderLayout());
                        commentsListFrame.setMinimumSize(new Dimension(500, 500));
                        commentsListFrame.setResizable(false);
                        commentsListFrame.toFront();
                        JPanel commentsListPanel = new JPanel();

                        tweetComments.clear();

                        try {
                            output.writeUTF("GET_TWEET_COMMENTS");
                            output.flush();
                            output.writeUTF(Integer.toString(tweetId));
                            output.flush();
                            Comment temp;
                            while (true) {
                                temp = (Comment) input.readObject();
                                if (temp == null)
                                    break;
                                tweetComments.add(temp);
                            }
                        } catch (IOException | ClassNotFoundException ex) {
                            ex.printStackTrace();
                        }

                        if (tweetComments.isEmpty())
                            return;

                        commentsListPanel.setBackground(new Color(0, 0, 0));
                        commentsListPanel.setLayout(new BoxLayout(commentsListPanel, BoxLayout.Y_AXIS));
                        JScrollPane likesListPanelScroll = new JScrollPane(commentsListPanel);
                        JPanel userInfo;

                        for (Comment x : tweetComments) {

                            userInfo = new JPanel();
                            userInfo.setBorder(blackborder);
                            userInfo.setLayout(new GridLayout(1, 2));
                            userInfo.setPreferredSize(new Dimension(500, 50));
                            userInfo.setMaximumSize(new Dimension(500, 50));

                            haveFollow = false;
                            try {
                                output.writeUTF("HAVE_FOLLOW");
                                output.flush();
                                output.writeUTF(Integer.toString(x.getUser().getID()));
                                output.flush();
                                haveFollow = Boolean.parseBoolean(input.readUTF());
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }

                            JButton userName = new JButton();
                            userName.setText("<html>First name: " + x.getUser().getFirstName() + "<br>" + "Last name: " + x.getUser().getLastname() + "<br>" + "Username: " + x.getUser().getUsername() + "</html>");
                            userName.setBorder(cyanborder);
                            userName.setBackground(Color.WHITE);
                            userName.setForeground(Color.black);
                            userName.setFocusable(false);
                            JButton followStatus = new JButton();
                            followStatus.setBorder(cyanborder);
                            followStatus.setBackground(Color.black);
                            followStatus.setForeground(Color.cyan);
                            if (x.getUser().getID() == LoggedInUser.getID())
                                followStatus.setEnabled(false);
                            if (haveFollow) {
                                followStatus.setText("unFollow");
                            } else {
                                followStatus.setText("Follow");
                            }

                            JTextArea commentText = new JTextArea(x.getComment());
                            commentText.setPreferredSize(new Dimension(30, 50));
                            commentText.setBackground(Color.black);
                            commentText.setForeground(Color.white);
                            commentText.setLineWrap(true);
                            commentText.setEditable(false);
                            commentText.setFont(new Font(null, Font.PLAIN, 17));
                            JScrollPane commentTextScrollPane = new JScrollPane(commentText);
                            commentTextScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

                            followStatus.setActionCommand("FOLLOW_" + x.getUser().getID());
                            userName.setActionCommand("USER_" + x.getUser().getID());

                            ActionListener listButtonActionListener = eq -> {

                                try {
                                    output.writeUTF("HAVE_FOLLOW");
                                    output.flush();
                                    output.writeUTF(Integer.toString(x.getUser().getID()));
                                    output.flush();
                                    haveFollow = Boolean.parseBoolean(input.readUTF());
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }

                                String listCommand = eq.getActionCommand();
                                if (listCommand.startsWith("FOLLOW_")) {
                                    int userID = Integer.parseInt(listCommand.substring(7));
                                    if (haveFollow) {
                                        followStatus.setText("Follow");
                                        try {
                                            output.writeUTF("REMOVE_FOLLOWING");
                                            output.flush();
                                            output.writeUTF(Integer.toString(userID));
                                            output.flush();
                                            LoggedInUser.setFollowingsCount(LoggedInUser.getFollowingsCount() - 1);
                                            x.getUser().setFollowersCount(x.getUser().getFollowersCount() - 1);
                                        } catch (IOException ex) {
                                            throw new RuntimeException(ex);
                                        }
                                    } else {
                                        followStatus.setText("unFollow");
                                        try {
                                            output.writeUTF("ADD_FOLLOWING");
                                            output.flush();
                                            output.writeUTF(Integer.toString(userID));
                                            output.flush();
                                            LoggedInUser.setFollowingsCount(LoggedInUser.getFollowingsCount() + 1);
                                            x.getUser().setFollowersCount(x.getUser().getFollowersCount() + 1);
                                        } catch (IOException ex) {
                                            throw new RuntimeException(ex);
                                        }
                                    }
                                } else if (listCommand.startsWith("USER_")) {
                                    int userID = Integer.parseInt(listCommand.substring(5));
                                    try {
                                        output.writeUTF("FIND_USER");
                                        output.flush();
                                        output.writeUTF(Integer.toString(userID));
                                        output.flush();
                                        new UserPage(socket, input, output, this.getX(), this.getY(), this.getWidth(), this.getHeight(), (User) input.readObject(), LoggedInUser);
                                        this.dispose();
                                        commentsListFrame.dispose();
                                    } catch (IOException | ClassNotFoundException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                }

                            };

                            followStatus.addActionListener(listButtonActionListener);
                            userName.addActionListener(listButtonActionListener);

                            userInfo.add(userName);
                            userInfo.add(commentTextScrollPane);
                            userInfo.add(followStatus);
                            commentsListPanel.add(userInfo);
                        }

                        commentsListFrame.add(likesListPanelScroll);
                        commentsListFrame.pack();
                        commentsListFrame.setVisible(true);
                    }

                };

                userButton.addActionListener(buttonActionListener);
                deleteButton.addActionListener(buttonActionListener);
                saveButton.addActionListener(buttonActionListener);
                likeButton.addActionListener(buttonActionListener);
                commentButton.addActionListener(buttonActionListener);
                likesButton.addActionListener(buttonActionListener);
                savesButton.addActionListener(buttonActionListener);
                commentsButton.addActionListener(buttonActionListener);

                JPanel tweetDown = new JPanel();
                tweetDown.setPreferredSize(new Dimension((this.getWidth() - 5) / 2, 55));
                tweetDown.setLayout(new GridLayout(2, 3));

                tweetDown.add(CommentScrollPane);
                tweetDown.add(commentButton);
                tweetDown.add(likeButton);
                tweetDown.add(saveButton);
                tweetDown.add(releaseTime);
                tweetDown.add(commentsButton);
                tweetDown.add(likesButton);
                tweetDown.add(savesButton);

                textPanel.add(tweetDown, BorderLayout.SOUTH);
                rightTweetPanel.add(textPanel);

            }

            rightTweetPanel.revalidate();
            rightTweetPanel.repaint();

            rightTweet.add(rightScrollPane, BorderLayout.CENTER);
            allGrid.add(rightTweet);
            this.revalidate();
            this.repaint();

        });

        userSavesButton.addActionListener(ez -> {

            savedTweetsList.clear();

            try {
                output.writeUTF("GET_SAVED_TWEETS");
                output.flush();
                output.writeUTF(Integer.toString(LoggedInUser.getID()));
                output.flush();
                Twits temp;
                while (true) {
                    temp = (Twits) input.readObject();
                    if (temp == null)
                        break;
                    savedTweetsList.add(temp);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            for (Twits i : savedTweetsList) {

                JButton likeButton, saveButton, likesButton, savesButton, commentsButton, commentButton, userButton, deleteButton;

                JPanel textPanel = new JPanel();
                textPanel.setLayout(new BorderLayout(10, 10));
                textPanel.setBackground(Color.black);
                textPanel.setPreferredSize(new Dimension((this.getWidth() - 70) / 2, 200));
                textPanel.setMaximumSize(new Dimension((this.getWidth() - 70) / 2, 200));

                textPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 10));

                JTextArea tweetText = new JTextArea(i.getText());
                tweetText.setPreferredSize(new Dimension(this.getWidth() - 20, 130));
                tweetText.setBackground(Color.black);
                tweetText.setForeground(Color.white);
                tweetText.setLineWrap(true);
                tweetText.setEditable(false);
                tweetText.setFont(new Font(null, Font.PLAIN, 21));
                JScrollPane TweetScrollPane = new JScrollPane(tweetText);
                TweetScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                textPanel.add(TweetScrollPane, BorderLayout.CENTER);

                JPanel userPanel = new JPanel();
                userPanel.setPreferredSize(new Dimension((this.getWidth() - 5) / 2, 43));
                userPanel.setLayout(new FlowLayout());
                userButton = new JButton();
                userButton.setBackground(Color.cyan);
                userButton.setFocusable(false);
                userButton.setText("First name : " + i.getUser().getFirstName() + "     ,     " + "Username : " + i.getUser().getUsername());
                userButton.setFont(new Font(Font.MONOSPACED, Font.BOLD, 16));
                deleteButton = new JButton();
                deleteButton.setText("Delete");
                deleteButton.setFont(new Font(Font.MONOSPACED, Font.BOLD, 16));
                deleteButton.setMaximumSize(new Dimension(30,30));
                deleteButton.setBackground(Color.red);
                deleteButton.setForeground(Color.white);
                deleteButton.setFocusable(false);
                userPanel.setBorder(blackborder);
                userPanel.add(userButton);
                userPanel.add(deleteButton);
                textPanel.add(userPanel, BorderLayout.NORTH);

                if (person.getID() != LoggedInUser.getID()) {
                    deleteButton.setBackground(Color.gray);
                    deleteButton.setEnabled(false);
                }

                if (i.getUser().getID() != LoggedInUser.getID()) {
                    deleteButton.setBackground(Color.gray);
                    deleteButton.setEnabled(false);
                }

                likeButton = new JButton();
                likeButton.setIcon(nullHeart);
                likeButton.setBackground(new Color(190, 190, 240));
                likeButton.setBorder(blackborder);
                likeButton.setFocusable(false);
                saveButton = new JButton();
                saveButton.setIcon(nullSaved);
                saveButton.setBackground(new Color(190, 190, 240));
                saveButton.setBorder(blackborder);
                saveButton.setFocusable(false);
                commentButton = new JButton("Comment");
                commentButton.setIcon(sender);
                commentButton.setBackground(new Color(190, 190, 240));
                commentButton.setBorder(blackborder);
                commentButton.setFocusable(false);
                likesButton = new JButton();
                likesButton.setBackground(new Color(190, 190, 240));
                likesButton.setBorder(blackborder);
                likesButton.setFocusable(false);
                savesButton = new JButton("Saves " + i.getSavesCount());
                savesButton.setBackground(new Color(190, 190, 240));
                savesButton.setBorder(blackborder);
                savesButton.setFocusable(false);
                commentsButton = new JButton("Comments " + i.getCommentsCount());
                commentsButton.setIcon(commentsLogo);
                commentsButton.setBackground(new Color(190, 190, 240));
                commentsButton.setBorder(blackborder);
                commentsButton.setFocusable(false);
                JTextArea commentArea = new JTextArea();
                commentArea.setFont(new Font(Font.DIALOG, Font.PLAIN, 15));
                commentArea.setLineWrap(true);
                commentArea.setBorder(blackborder);
                JScrollPane CommentScrollPane = new JScrollPane(commentArea);
                CommentScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                JLabel releaseTime = new JLabel(String.valueOf(i.getTime()));
                releaseTime.setForeground(Color.blue);
                releaseTime.setBorder(blackborder);

                try {
                    output.writeUTF("IS_LIKED");
                    output.flush();
                    output.writeUTF(Integer.toString(i.getTweetID()));
                    output.flush();
                    output.writeUTF(Integer.toString(LoggedInUser.getID()));
                    output.flush();
                    isLiked = Boolean.parseBoolean(input.readUTF());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                if (isLiked) {
                    likeButton.setIcon(redHeart);
                    likesButton.setText("Likes " + i.getLikesCount());
                } else {
                    likeButton.setIcon(nullHeart);
                    likesButton.setText("Likes " + i.getLikesCount());
                }

                try {
                    output.writeUTF("IS_SAVED");
                    output.flush();
                    output.writeUTF(Integer.toString(i.getTweetID()));
                    output.flush();
                    output.writeUTF(Integer.toString(LoggedInUser.getID()));
                    output.flush();
                    isSaved = Boolean.parseBoolean(input.readUTF());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                if (isSaved) {
                    saveButton.setIcon(smallSaveFlag);
                    savesButton.setText("Saves " + i.getSavesCount());
                } else {
                    saveButton.setIcon(nullSaved);
                    savesButton.setText("Saves " + i.getSavesCount());
                }

                if (!i.isSave()) {
                    saveButton.setEnabled(false);
                    saveButton.setEnabled(false);
                }
                if (!i.isComment()) {
                    commentButton.setEnabled(false);
                    commentArea.setFocusable(false);
                }

                userButton.setActionCommand("USER_" + i.getUser().getID());
                deleteButton.setActionCommand("DELETE_" + i.getTweetID());
                saveButton.setActionCommand("SAVE_" + i.getTweetID());
                likeButton.setActionCommand("LIKE_" + i.getTweetID());
                commentButton.setActionCommand("COMMENT_" + i.getTweetID());
                likesButton.setActionCommand("LIKES_" + i.getTweetID());
                savesButton.setActionCommand("SAVES_" + i.getTweetID());
                commentsButton.setActionCommand("COMMENTS_" + i.getTweetID());

                ActionListener buttonActionListener = e -> {
                    try {
                        output.writeUTF("IS_LIKED");
                        output.flush();
                        output.writeUTF(Integer.toString(i.getTweetID()));
                        output.flush();
                        output.writeUTF(Integer.toString(LoggedInUser.getID()));
                        output.flush();
                        isLiked = Boolean.parseBoolean(input.readUTF());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    try {
                        output.writeUTF("IS_SAVED");
                        output.flush();
                        output.writeUTF(Integer.toString(i.getTweetID()));
                        output.flush();
                        output.writeUTF(Integer.toString(LoggedInUser.getID()));
                        output.flush();
                        isSaved = Boolean.parseBoolean(input.readUTF());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    String command = e.getActionCommand();
                    if (command.startsWith("USER_")) {
                        int userID = Integer.parseInt(command.substring(5));
                        try {
                            output.writeUTF("FIND_USER");
                            output.flush();
                            output.writeUTF(Integer.toString(userID));
                            output.flush();
                            new UserPage(socket, input, output, this.getX(), this.getY(), this.getWidth(), this.getHeight(), (User) input.readObject(), LoggedInUser);
                            this.dispose();
                        } catch (IOException | ClassNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    else if (command.startsWith("DELETE_")) {
                        int tweetId = Integer.parseInt(command.substring(7));
                        try {
                            output.writeUTF("REMOVE_TWEET");
                            output.flush();
                            output.writeUTF(String.valueOf(tweetId));
                            output.flush();
                            this.repaint();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    else if (command.startsWith("SAVE_")) {
                        int tweetId = Integer.parseInt(command.substring(5));
                        if (isSaved) {
                            try {
                                output.writeUTF("REMOVE_SAVE");
                                output.flush();
                                output.writeUTF(String.valueOf(tweetId));
                                output.flush();
                                output.writeUTF(Integer.toString(LoggedInUser.getID()));
                                output.flush();
                                i.setSavesCount(i.getSavesCount() - 1);
                                savesButton.setText("Saves " + (i.getSavesCount()));
                                this.revalidate();
                                this.repaint();
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        } else {
                            try {
                                output.writeUTF("ADD_SAVE");
                                output.flush();
                                output.writeUTF(Integer.toString(tweetId));
                                output.flush();
                                output.writeUTF(Integer.toString(LoggedInUser.getID()));
                                output.flush();
                                i.setSavesCount(i.getSavesCount() + 1);
                                savesButton.setText("Saves " + (i.getSavesCount()));
                                this.revalidate();
                                this.repaint();
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    } else if (command.startsWith("LIKE_")) {
                        int tweetId = Integer.parseInt(command.substring(5));
                        if (isLiked) {
                            try {
                                output.writeUTF("REMOVE_LIKE");
                                output.flush();
                                output.writeUTF(String.valueOf(tweetId));
                                output.flush();
                                output.writeUTF(Integer.toString(LoggedInUser.getID()));
                                output.flush();
                                i.setLikesCount(i.getLikesCount() - 1);
                                likesButton.setText("Likes " + (i.getLikesCount()));
                                this.revalidate();
                                this.repaint();
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        } else {
                            try {
                                output.writeUTF("ADD_LIKE");
                                output.flush();
                                output.writeUTF(Integer.toString(tweetId));
                                output.flush();
                                output.writeUTF(Integer.toString(LoggedInUser.getID()));
                                output.flush();
                                i.setLikesCount(i.getLikesCount() + 1);
                                likesButton.setText("Likes " + (i.getLikesCount()));
                                this.revalidate();
                                this.repaint();
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    } else if (command.startsWith("COMMENT_")) {
                        int tweetId = Integer.parseInt(command.substring(8));
                        if (commentArea.getText().isEmpty())
                            JOptionPane.showMessageDialog(null, "Fill the comment area first!", "Error", JOptionPane.WARNING_MESSAGE);
                        else {
                            try {
                                output.writeUTF("ADD_COMMENT");
                                output.flush();
                                output.writeUTF(Integer.toString(tweetId));
                                output.flush();
                                output.writeUTF(Integer.toString(LoggedInUser.getID()));
                                output.flush();
                                output.writeUTF(commentArea.getText());
                                output.flush();
                                i.setCommentsCount(i.getCommentsCount() + 1);
                                commentsButton.setText("Comments " + (i.getCommentsCount()));
                                commentArea.setText("");
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    } else if (command.startsWith("LIKES_")) {
                        int tweetId = Integer.parseInt(command.substring(6));
                        if (i.getLikesCount() == 0)
                            return;
                        JFrame likesListFrame = new JFrame();
                        likesListFrame.setIconImage(user.getImage());
                        likesListFrame.setTitle("Likes");
                        likesListFrame.setLayout(new BorderLayout());
                        likesListFrame.setMinimumSize(new Dimension(400, 500));
                        likesListFrame.setResizable(false);
                        likesListFrame.toFront();
                        JPanel likesListPanel = new JPanel();

                        tweetLikes.clear();

                        try {
                            output.writeUTF("GET_TWEET_LIKES");
                            output.flush();
                            output.writeUTF(Integer.toString(tweetId));
                            output.flush();
                            User temp;
                            while (true) {
                                temp = (User) input.readObject();
                                if (temp == null)
                                    break;
                                tweetLikes.add(temp);
                            }
                        } catch (IOException | ClassNotFoundException ex) {
                            ex.printStackTrace();
                        }

                        likesListPanel.setBackground(new Color(0, 0, 0));
                        likesListPanel.setLayout(new BoxLayout(likesListPanel, BoxLayout.Y_AXIS));
                        JScrollPane likesListPanelScroll = new JScrollPane(likesListPanel);
                        JPanel userInfo;

                        for (User x : tweetLikes) {

                            userInfo = new JPanel();
                            userInfo.setBorder(blackborder);
                            userInfo.setLayout(new GridLayout(1, 2));
                            userInfo.setPreferredSize(new Dimension(400, 50));
                            userInfo.setMaximumSize(new Dimension(400, 50));

                            haveFollow = false;
                            try {
                                output.writeUTF("HAVE_FOLLOW");
                                output.flush();
                                output.writeUTF(Integer.toString(x.getID()));
                                output.flush();
                                haveFollow = Boolean.parseBoolean(input.readUTF());
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }

                            JButton userName = new JButton();
                            userName.setText("<html>First name: " + x.getFirstName() + "<br>" + "Last name: " + x.getLastname() + "<br>" + "Username: " + x.getUsername() + "</html>");
                            userName.setBorder(cyanborder);
                            userName.setBackground(Color.WHITE);
                            userName.setForeground(Color.black);
                            userName.setFocusable(false);
                            JButton followStatus = new JButton();
                            followStatus.setFocusable(false);
                            followStatus.setBorder(cyanborder);
                            followStatus.setBackground(Color.black);
                            followStatus.setForeground(Color.cyan);
                            if (x.getID() == LoggedInUser.getID())
                                followStatus.setEnabled(false);
                            if (haveFollow) {
                                followStatus.setText("unFollow");
                            } else {
                                followStatus.setText("Follow");
                            }

                            followStatus.setActionCommand("FOLLOW_" + x.getID());
                            userName.setActionCommand("USER_" + x.getID());

                            ActionListener listButtonActionListener = eq -> {

                                try {
                                    output.writeUTF("HAVE_FOLLOW");
                                    output.flush();
                                    output.writeUTF(Integer.toString(x.getID()));
                                    output.flush();
                                    haveFollow = Boolean.parseBoolean(input.readUTF());
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }

                                String listCommand = eq.getActionCommand();
                                if (listCommand.startsWith("FOLLOW_")) {
                                    int userID = Integer.parseInt(listCommand.substring(7));
                                    if (haveFollow) {
                                        followStatus.setText("Follow");
                                        try {
                                            output.writeUTF("REMOVE_FOLLOWING");
                                            output.flush();
                                            output.writeUTF(Integer.toString(userID));
                                            output.flush();
                                            LoggedInUser.setFollowingsCount(LoggedInUser.getFollowingsCount() - 1);
                                            x.setFollowersCount(x.getFollowersCount() - 1);
                                        } catch (IOException ex) {
                                            throw new RuntimeException(ex);
                                        }
                                    } else {
                                        followStatus.setText("unFollow");
                                        try {
                                            output.writeUTF("ADD_FOLLOWING");
                                            output.flush();
                                            output.writeUTF(Integer.toString(userID));
                                            output.flush();
                                            LoggedInUser.setFollowingsCount(LoggedInUser.getFollowingsCount() + 1);
                                            x.setFollowersCount(x.getFollowersCount() + 1);
                                        } catch (IOException ex) {
                                            throw new RuntimeException(ex);
                                        }
                                    }
                                } else if (listCommand.startsWith("USER_")) {
                                    int userID = Integer.parseInt(listCommand.substring(5));
                                    try {
                                        output.writeUTF("FIND_USER");
                                        output.flush();
                                        output.writeUTF(Integer.toString(userID));
                                        output.flush();
                                        new UserPage(socket, input, output, this.getX(), this.getY(), this.getWidth(), this.getHeight(), (User) input.readObject(), LoggedInUser);
                                        this.dispose();
                                        likesListFrame.dispose();
                                    } catch (IOException | ClassNotFoundException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                }

                            };

                            followStatus.addActionListener(listButtonActionListener);
                            userName.addActionListener(listButtonActionListener);

                            userInfo.add(userName);
                            userInfo.add(followStatus);
                            likesListPanel.add(userInfo);
                        }

                        likesListFrame.add(likesListPanelScroll);
                        likesListFrame.pack();
                        likesListFrame.setVisible(true);

                    } else if (command.startsWith("SAVES_")) {
                        int tweetId = Integer.parseInt(command.substring(6));
                        if (i.getSavesCount() == 0)
                            return;
                        JFrame savesListFrame = new JFrame();
                        savesListFrame.setIconImage(user.getImage());
                        savesListFrame.setTitle("Saves");
                        savesListFrame.setLayout(new BorderLayout());
                        savesListFrame.setMinimumSize(new Dimension(400, 500));
                        savesListFrame.setResizable(false);
                        savesListFrame.toFront();
                        JPanel savesListPanel = new JPanel();

                        tweetSaves.clear();

                        try {
                            output.writeUTF("GET_TWEET_SAVES");
                            output.flush();
                            output.writeUTF(Integer.toString(tweetId));
                            output.flush();
                            User temp;
                            while (true) {
                                temp = (User) input.readObject();
                                if (temp == null)
                                    break;
                                tweetSaves.add(temp);
                            }
                        } catch (IOException | ClassNotFoundException ex) {
                            ex.printStackTrace();
                        }

                        savesListPanel.setBackground(new Color(0, 0, 0));
                        savesListPanel.setLayout(new BoxLayout(savesListPanel, BoxLayout.Y_AXIS));
                        JScrollPane likesListPanelScroll = new JScrollPane(savesListPanel);
                        JPanel userInfo;

                        for (User x : tweetSaves) {

                            userInfo = new JPanel();
                            userInfo.setBorder(blackborder);
                            userInfo.setLayout(new GridLayout(1, 2));
                            userInfo.setPreferredSize(new Dimension(400, 50));
                            userInfo.setMaximumSize(new Dimension(400, 50));

                            haveFollow = false;
                            try {
                                output.writeUTF("HAVE_FOLLOW");
                                output.flush();
                                output.writeUTF(Integer.toString(x.getID()));
                                output.flush();
                                haveFollow = Boolean.parseBoolean(input.readUTF());
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }

                            JButton userName = new JButton();
                            userName.setText("<html>First name: " + x.getFirstName() + "<br>" + "Last name: " + x.getLastname() + "<br>" + "Username: " + x.getUsername() + "</html>");
                            userName.setBorder(cyanborder);
                            userName.setBackground(Color.WHITE);
                            userName.setForeground(Color.black);
                            userName.setFocusable(false);
                            JButton followStatus = new JButton();
                            followStatus.setBorder(cyanborder);
                            followStatus.setBackground(Color.black);
                            followStatus.setForeground(Color.cyan);
                            if (x.getID() == LoggedInUser.getID())
                                followStatus.setEnabled(false);
                            if (haveFollow) {
                                followStatus.setText("unFollow");
                            } else {
                                followStatus.setText("Follow");
                            }

                            followStatus.setActionCommand("FOLLOW_" + x.getID());
                            userName.setActionCommand("USER_" + x.getID());

                            ActionListener listButtonActionListener = eq -> {

                                try {
                                    output.writeUTF("HAVE_FOLLOW");
                                    output.flush();
                                    output.writeUTF(Integer.toString(x.getID()));
                                    output.flush();
                                    haveFollow = Boolean.parseBoolean(input.readUTF());
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }

                                String listCommand = eq.getActionCommand();
                                if (listCommand.startsWith("FOLLOW_")) {
                                    int userID = Integer.parseInt(listCommand.substring(7));
                                    if (haveFollow) {
                                        followStatus.setText("Follow");
                                        try {
                                            output.writeUTF("REMOVE_FOLLOWING");
                                            output.flush();
                                            output.writeUTF(Integer.toString(userID));
                                            output.flush();
                                            LoggedInUser.setFollowingsCount(LoggedInUser.getFollowingsCount() - 1);
                                            x.setFollowersCount(x.getFollowersCount() - 1);
                                        } catch (IOException ex) {
                                            throw new RuntimeException(ex);
                                        }
                                    } else {
                                        followStatus.setText("unFollow");
                                        try {
                                            output.writeUTF("ADD_FOLLOWING");
                                            output.flush();
                                            output.writeUTF(Integer.toString(userID));
                                            output.flush();
                                            LoggedInUser.setFollowingsCount(LoggedInUser.getFollowingsCount() + 1);
                                            x.setFollowersCount(x.getFollowersCount() + 1);
                                        } catch (IOException ex) {
                                            throw new RuntimeException(ex);
                                        }
                                    }
                                } else if (listCommand.startsWith("USER_")) {
                                    int userID = Integer.parseInt(listCommand.substring(5));
                                    try {
                                        output.writeUTF("FIND_USER");
                                        output.flush();
                                        output.writeUTF(Integer.toString(userID));
                                        output.flush();
                                        new UserPage(socket, input, output, this.getX(), this.getY(), this.getWidth(), this.getHeight(), (User) input.readObject(), LoggedInUser);
                                        this.dispose();
                                        savesListFrame.dispose();
                                    } catch (IOException | ClassNotFoundException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                }
                            };
                            followStatus.addActionListener(listButtonActionListener);
                            userName.addActionListener(listButtonActionListener);

                            userInfo.add(userName);
                            userInfo.add(followStatus);
                            savesListPanel.add(userInfo);
                        }

                        savesListFrame.add(likesListPanelScroll);
                        savesListFrame.pack();
                        savesListFrame.setVisible(true);

                    } else if (command.startsWith("COMMENTS_")) {
                        int tweetId = Integer.parseInt(command.substring(9));

                        JFrame commentsListFrame = new JFrame();
                        commentsListFrame.setIconImage(user.getImage());
                        commentsListFrame.setTitle("Comments");
                        commentsListFrame.setLayout(new BorderLayout());
                        commentsListFrame.setMinimumSize(new Dimension(500, 500));
                        commentsListFrame.setResizable(false);
                        commentsListFrame.toFront();
                        JPanel commentsListPanel = new JPanel();

                        tweetComments.clear();

                        try {
                            output.writeUTF("GET_TWEET_COMMENTS");
                            output.flush();
                            output.writeUTF(Integer.toString(tweetId));
                            output.flush();
                            Comment temp;
                            while (true) {
                                temp = (Comment) input.readObject();
                                if (temp == null)
                                    break;
                                tweetComments.add(temp);
                            }
                        } catch (IOException | ClassNotFoundException ex) {
                            ex.printStackTrace();
                        }

                        if (tweetComments.isEmpty())
                            return;

                        commentsListPanel.setBackground(new Color(0, 0, 0));
                        commentsListPanel.setLayout(new BoxLayout(commentsListPanel, BoxLayout.Y_AXIS));
                        JScrollPane likesListPanelScroll = new JScrollPane(commentsListPanel);
                        JPanel userInfo;

                        for (Comment x : tweetComments) {

                            userInfo = new JPanel();
                            userInfo.setBorder(blackborder);
                            userInfo.setLayout(new GridLayout(1, 2));
                            userInfo.setPreferredSize(new Dimension(500, 50));
                            userInfo.setMaximumSize(new Dimension(500, 50));

                            haveFollow = false;
                            try {
                                output.writeUTF("HAVE_FOLLOW");
                                output.flush();
                                output.writeUTF(Integer.toString(x.getUser().getID()));
                                output.flush();
                                haveFollow = Boolean.parseBoolean(input.readUTF());
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }

                            JButton userName = new JButton();
                            userName.setText("<html>First name: " + x.getUser().getFirstName() + "<br>" + "Last name: " + x.getUser().getLastname() + "<br>" + "Username: " + x.getUser().getUsername() + "</html>");
                            userName.setBorder(cyanborder);
                            userName.setBackground(Color.WHITE);
                            userName.setForeground(Color.black);
                            userName.setFocusable(false);
                            JButton followStatus = new JButton();
                            followStatus.setBorder(cyanborder);
                            followStatus.setBackground(Color.black);
                            followStatus.setForeground(Color.cyan);
                            if (x.getUser().getID() == LoggedInUser.getID())
                                followStatus.setEnabled(false);
                            if (haveFollow) {
                                followStatus.setText("unFollow");
                            } else {
                                followStatus.setText("Follow");
                            }

                            JTextArea commentText = new JTextArea(x.getComment());
                            commentText.setPreferredSize(new Dimension(30, 50));
                            commentText.setBackground(Color.black);
                            commentText.setForeground(Color.white);
                            commentText.setLineWrap(true);
                            commentText.setEditable(false);
                            commentText.setFont(new Font(null, Font.PLAIN, 17));
                            JScrollPane commentTextScrollPane = new JScrollPane(commentText);
                            commentTextScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

                            followStatus.setActionCommand("FOLLOW_" + x.getUser().getID());
                            userName.setActionCommand("USER_" + x.getUser().getID());

                            ActionListener listButtonActionListener = eq -> {

                                try {
                                    output.writeUTF("HAVE_FOLLOW");
                                    output.flush();
                                    output.writeUTF(Integer.toString(x.getUser().getID()));
                                    output.flush();
                                    haveFollow = Boolean.parseBoolean(input.readUTF());
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }

                                String listCommand = eq.getActionCommand();
                                if (listCommand.startsWith("FOLLOW_")) {
                                    int userID = Integer.parseInt(listCommand.substring(7));
                                    if (haveFollow) {
                                        followStatus.setText("Follow");
                                        try {
                                            output.writeUTF("REMOVE_FOLLOWING");
                                            output.flush();
                                            output.writeUTF(Integer.toString(userID));
                                            output.flush();
                                            LoggedInUser.setFollowingsCount(LoggedInUser.getFollowingsCount() - 1);
                                            x.getUser().setFollowersCount(x.getUser().getFollowersCount() - 1);
                                        } catch (IOException ex) {
                                            throw new RuntimeException(ex);
                                        }
                                    } else {
                                        followStatus.setText("unFollow");
                                        try {
                                            output.writeUTF("ADD_FOLLOWING");
                                            output.flush();
                                            output.writeUTF(Integer.toString(userID));
                                            output.flush();
                                            LoggedInUser.setFollowingsCount(LoggedInUser.getFollowingsCount() + 1);
                                            x.getUser().setFollowersCount(x.getUser().getFollowersCount() + 1);
                                        } catch (IOException ex) {
                                            throw new RuntimeException(ex);
                                        }
                                    }
                                } else if (listCommand.startsWith("USER_")) {
                                    int userID = Integer.parseInt(listCommand.substring(5));
                                    try {
                                        output.writeUTF("FIND_USER");
                                        output.flush();
                                        output.writeUTF(Integer.toString(userID));
                                        output.flush();
                                        new UserPage(socket, input, output, this.getX(), this.getY(), this.getWidth(), this.getHeight(), (User) input.readObject(), LoggedInUser);
                                        this.dispose();
                                        commentsListFrame.dispose();
                                    } catch (IOException | ClassNotFoundException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                }

                            };

                            followStatus.addActionListener(listButtonActionListener);
                            userName.addActionListener(listButtonActionListener);

                            userInfo.add(userName);
                            userInfo.add(commentTextScrollPane);
                            userInfo.add(followStatus);
                            commentsListPanel.add(userInfo);
                        }

                        commentsListFrame.add(likesListPanelScroll);
                        commentsListFrame.pack();
                        commentsListFrame.setVisible(true);
                    }

                };

                userButton.addActionListener(buttonActionListener);
                deleteButton.addActionListener(buttonActionListener);
                saveButton.addActionListener(buttonActionListener);
                likeButton.addActionListener(buttonActionListener);
                commentButton.addActionListener(buttonActionListener);
                likesButton.addActionListener(buttonActionListener);
                savesButton.addActionListener(buttonActionListener);
                commentsButton.addActionListener(buttonActionListener);

                JPanel tweetDown = new JPanel();
                tweetDown.setPreferredSize(new Dimension((this.getWidth() - 5) / 2, 55));
                tweetDown.setLayout(new GridLayout(2, 3));

                tweetDown.add(CommentScrollPane);
                tweetDown.add(commentButton);
                tweetDown.add(likeButton);
                tweetDown.add(saveButton);
                tweetDown.add(releaseTime);
                tweetDown.add(commentsButton);
                tweetDown.add(likesButton);
                tweetDown.add(savesButton);

                textPanel.add(tweetDown, BorderLayout.SOUTH);
                rightTweetPanel.add(textPanel);

            }

            rightTweetPanel.revalidate();
            rightTweetPanel.repaint();

            rightTweet.add(rightScrollPane, BorderLayout.CENTER);
            allGrid.add(rightTweet);
            this.revalidate();
            this.repaint();

        });

        statusButton.addActionListener(ez -> {

            if (isFollowed) {
                try {
                    output.writeUTF("REMOVE_FOLLOWING");
                    output.flush();
                    output.writeUTF(Integer.toString(person.getID()));
                    output.flush();
                    statusButton.setText("Follow");
                    LoggedInUser.setFollowingsCount(LoggedInUser.getFollowingsCount() - 1);
                    person.setFollowersCount(person.getFollowersCount() - 1);
                    this.revalidate();
                    this.repaint();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            else {
                try {
                    output.writeUTF("ADD_FOLLOWING");
                    output.flush();
                    output.writeUTF(Integer.toString(person.getID()));
                    output.flush();
                    statusButton.setText("unFollow");
                    LoggedInUser.setFollowingsCount(LoggedInUser.getFollowingsCount() + 1);
                    person.setFollowersCount(person.getFollowersCount() + 1);
                    this.revalidate();
                    this.repaint();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

        });

        followersButton.addActionListener(ez -> {

            if (person.getFollowersCount() == 0)
                return;

            JFrame followersListFrame = new JFrame();
            followersListFrame.setIconImage(user.getImage());
            followersListFrame.setTitle(person.getUsername() + " Followers");
            followersListFrame.setLayout(new BorderLayout());
            followersListFrame.setMinimumSize(new Dimension(400, 500));
            followersListFrame.setResizable(false);
            followersListFrame.toFront();
            JPanel followersListPanel = new JPanel();

            followersList.clear();

            try {
                output.writeUTF("GET_FOLLOWERS");
                output.flush();
                output.writeUTF(Integer.toString(person.getID()));
                output.flush();
                User temp;
                while (true) {
                    temp = (User) input.readObject();
                    if (temp == null)
                        break;
                    followersList.add(temp);
                }
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }

            followersListPanel.setBackground(new Color(0, 0, 0));
            followersListPanel.setLayout(new BoxLayout(followersListPanel, BoxLayout.Y_AXIS));
            JScrollPane followersListPanelScroll = new JScrollPane(followersListPanel);
            JPanel userInfo;

            for (User x : followersList) {

                userInfo = new JPanel();
                userInfo.setBorder(blackborder);
                userInfo.setLayout(new GridLayout(1, 2));
                userInfo.setPreferredSize(new Dimension(400, 50));
                userInfo.setMaximumSize(new Dimension(400, 50));

                haveFollow = false;
                try {
                    output.writeUTF("HAVE_FOLLOW");
                    output.flush();
                    output.writeUTF(Integer.toString(x.getID()));
                    output.flush();
                    haveFollow = Boolean.parseBoolean(input.readUTF());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                JButton userName = new JButton();
                userName.setText("<html>First name: " + x.getFirstName() + "<br>" + "Last name: " + x.getLastname() + "<br>" + "Username: " + x.getUsername() + "</html>");
                userName.setBorder(cyanborder);
                userName.setBackground(Color.WHITE);
                userName.setForeground(Color.black);
                userName.setFocusable(false);
                JButton followStatus = new JButton();
                followStatus.setFocusable(false);
                followStatus.setBorder(cyanborder);
                followStatus.setBackground(Color.black);
                followStatus.setForeground(Color.cyan);
                if (x.getID() == LoggedInUser.getID())
                    followStatus.setEnabled(false);
                if (haveFollow) {
                    followStatus.setText("unFollow");
                } else {
                    followStatus.setText("Follow");
                }

                followStatus.setActionCommand("FOLLOW_" + x.getID());
                userName.setActionCommand("USER_" + x.getID());

                ActionListener listButtonActionListener = eq -> {

                    try {
                        output.writeUTF("HAVE_FOLLOW");
                        output.flush();
                        output.writeUTF(Integer.toString(x.getID()));
                        output.flush();
                        haveFollow = Boolean.parseBoolean(input.readUTF());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    String listCommand = eq.getActionCommand();
                    if (listCommand.startsWith("FOLLOW_")) {
                        int userID = Integer.parseInt(listCommand.substring(7));
                        if (haveFollow) {
                            followStatus.setText("Follow");
                            try {
                                output.writeUTF("REMOVE_FOLLOWING");
                                output.flush();
                                output.writeUTF(Integer.toString(userID));
                                output.flush();
                                LoggedInUser.setFollowingsCount(LoggedInUser.getFollowingsCount() - 1);
                                x.setFollowersCount(x.getFollowersCount() - 1);
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        } else {
                            followStatus.setText("unFollow");
                            try {
                                output.writeUTF("ADD_FOLLOWING");
                                output.flush();
                                output.writeUTF(Integer.toString(userID));
                                output.flush();
                                LoggedInUser.setFollowingsCount(LoggedInUser.getFollowingsCount() + 1);
                                x.setFollowersCount(x.getFollowersCount() + 1);
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    } else if (listCommand.startsWith("USER_")) {
                        int userID = Integer.parseInt(listCommand.substring(5));
                        try {
                            output.writeUTF("FIND_USER");
                            output.flush();
                            output.writeUTF(Integer.toString(userID));
                            output.flush();
                            new UserPage(socket, input, output, this.getX(), this.getY(), this.getWidth(), this.getHeight(), (User) input.readObject(), LoggedInUser);
                            this.dispose();
                            followersListFrame.dispose();
                        } catch (IOException | ClassNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                    }

                };

                followStatus.addActionListener(listButtonActionListener);
                userName.addActionListener(listButtonActionListener);

                userInfo.add(userName);
                userInfo.add(followStatus);
                followersListPanel.add(userInfo);
            }

            followersListFrame.add(followersListPanelScroll);
            followersListFrame.pack();
            followersListFrame.setVisible(true);

        });

        followingsButton.addActionListener(ez -> {

            if (person.getFollowingsCount() == 0)
                return;

            JFrame followingsListFrame = new JFrame();
            followingsListFrame.setIconImage(user.getImage());
            followingsListFrame.setTitle(person.getUsername() + " Followings");
            followingsListFrame.setLayout(new BorderLayout());
            followingsListFrame.setMinimumSize(new Dimension(400, 500));
            followingsListFrame.setResizable(false);
            followingsListFrame.toFront();
            JPanel followingsListPanel = new JPanel();

            followingsList.clear();

            try {
                output.writeUTF("GET_FOLLOWINGS");
                output.flush();
                output.writeUTF(Integer.toString(person.getID()));
                output.flush();
                User temp;
                while (true) {
                    temp = (User) input.readObject();
                    if (temp == null)
                        break;
                    followingsList.add(temp);
                }
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }

            followingsListPanel.setBackground(new Color(0, 0, 0));
            followingsListPanel.setLayout(new BoxLayout(followingsListPanel, BoxLayout.Y_AXIS));
            JScrollPane followingsListPanelScroll = new JScrollPane(followingsListPanel);
            JPanel userInfo;

            for (User x : followingsList) {

                userInfo = new JPanel();
                userInfo.setBorder(blackborder);
                userInfo.setLayout(new GridLayout(1, 2));
                userInfo.setPreferredSize(new Dimension(400, 50));
                userInfo.setMaximumSize(new Dimension(400, 50));

                haveFollow = false;
                try {
                    output.writeUTF("HAVE_FOLLOW");
                    output.flush();
                    output.writeUTF(Integer.toString(x.getID()));
                    output.flush();
                    haveFollow = Boolean.parseBoolean(input.readUTF());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                JButton userName = new JButton();
                userName.setText("<html>First name: " + x.getFirstName() + "<br>" + "Last name: " + x.getLastname() + "<br>" + "Username: " + x.getUsername() + "</html>");
                userName.setBorder(cyanborder);
                userName.setBackground(Color.WHITE);
                userName.setForeground(Color.black);
                userName.setFocusable(false);
                JButton followStatus = new JButton();
                followStatus.setFocusable(false);
                followStatus.setBorder(cyanborder);
                followStatus.setBackground(Color.black);
                followStatus.setForeground(Color.cyan);
                if (x.getID() == LoggedInUser.getID())
                    followStatus.setEnabled(false);
                if (haveFollow) {
                    followStatus.setText("unFollow");
                } else {
                    followStatus.setText("Follow");
                }

                followStatus.setActionCommand("FOLLOW_" + x.getID());
                userName.setActionCommand("USER_" + x.getID());

                ActionListener listButtonActionListener = eq -> {

                    try {
                        output.writeUTF("HAVE_FOLLOW");
                        output.flush();
                        output.writeUTF(Integer.toString(x.getID()));
                        output.flush();
                        haveFollow = Boolean.parseBoolean(input.readUTF());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    String listCommand = eq.getActionCommand();
                    if (listCommand.startsWith("FOLLOW_")) {
                        int userID = Integer.parseInt(listCommand.substring(7));
                        if (haveFollow) {
                            followStatus.setText("Follow");
                            try {
                                output.writeUTF("REMOVE_FOLLOWING");
                                output.flush();
                                output.writeUTF(Integer.toString(userID));
                                output.flush();
                                LoggedInUser.setFollowingsCount(LoggedInUser.getFollowingsCount() - 1);
                                x.setFollowersCount(x.getFollowersCount() - 1);
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        } else {
                            followStatus.setText("unFollow");
                            try {
                                output.writeUTF("ADD_FOLLOWING");
                                output.flush();
                                output.writeUTF(Integer.toString(userID));
                                output.flush();
                                LoggedInUser.setFollowingsCount(LoggedInUser.getFollowingsCount() + 1);
                                x.setFollowersCount(x.getFollowersCount() + 1);
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    } else if (listCommand.startsWith("USER_")) {
                        int userID = Integer.parseInt(listCommand.substring(5));
                        try {
                            output.writeUTF("FIND_USER");
                            output.flush();
                            output.writeUTF(Integer.toString(userID));
                            output.flush();
                            new UserPage(socket, input, output, this.getX(), this.getY(), this.getWidth(), this.getHeight(), (User) input.readObject(), LoggedInUser);
                            this.dispose();
                            followingsListFrame.dispose();
                        } catch (IOException | ClassNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                    }

                };

                followStatus.addActionListener(listButtonActionListener);
                userName.addActionListener(listButtonActionListener);

                userInfo.add(userName);
                userInfo.add(followStatus);
                followingsListPanel.add(userInfo);
            }

            followingsListFrame.add(followingsListPanelScroll);
            followingsListFrame.pack();
            followingsListFrame.setVisible(true);

        });

        Main.add(allGrid, BorderLayout.CENTER);

        this.add(Top, BorderLayout.NORTH);
        this.add(Main, BorderLayout.CENTER);
        this.add(Down, BorderLayout.SOUTH);

        //----------------------------------------------

        settingButton.addActionListener(e -> {

            //------

        });

        homeButton.addActionListener(e -> {

            new HomePage(socket, input, output, this.getX(), this.getY(), this.getWidth(), this.getHeight(), LoggedInUser, LoggedInUser);
            this.dispose();

        });

        personalPageButton.addActionListener(e -> {

            new UserPage(socket, input, output, this.getX(), this.getY(), this.getWidth(), this.getHeight(), LoggedInUser, LoggedInUser);
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

            new Twit(socket, input, output, this.getX(), this.getY(), this.getWidth(), this.getHeight(), LoggedInUser, LoggedInUser);
            this.dispose();

        });

        this.setVisible(true);

    }

}
