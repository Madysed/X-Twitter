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

public class SearchPage extends JFrame implements Serializable {
    User LoggedInUser;
    public ObjectInputStream input;
    public ObjectOutputStream output;
    ArrayList<User> result = new ArrayList<>();
    ArrayList<User> history = new ArrayList<>();
    boolean haveFollow = false;
    SearchPage(Socket socket, ObjectInputStream input, ObjectOutputStream output, int thisX, int thisY, int thisWidth, int thisHeight, JFrame frame, String username,User loggedInUser) {

        this.LoggedInUser = loggedInUser;
        this.input = input;
        this.output = output;

        ImageIcon search = new ImageIcon("magnifying-glass.png");
        search.setImage(search.getImage().getScaledInstance(23, 23, Image.SCALE_SMOOTH));
        Border blackborder = BorderFactory.createLineBorder(Color.BLACK, 1);
        Border cyanborder = BorderFactory.createLineBorder(Color.cyan, 1);

        JFrame searchListFrame = new JFrame();
        searchListFrame.setIconImage(search.getImage());
        searchListFrame.setTitle("Searching");
        searchListFrame.setLayout(new BorderLayout());
        searchListFrame.setMinimumSize(new Dimension(850, 400));
        searchListFrame.setResizable(false);
        searchListFrame.toFront();
        JPanel all = new JPanel(new GridLayout(1,2));
        JPanel searchListPanel = new JPanel();
        JPanel searchHistoryListPanel = new JPanel();

        history.clear();

        try {
            output.writeUTF("GET_HISTORY");
            output.flush();
            User temp;
            while (true) {
                temp = (User) input.readObject();
                if (temp == null)
                    break;
                history.add(temp);
            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        result.clear();

        try {
            output.writeUTF("SEARCHING");
            output.flush();
            output.writeUTF(username);
            output.flush();
            User temp;
            while (true) {
                temp = (User) input.readObject();
                if (temp == null)
                    break;
                result.add(temp);
            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        searchListPanel.setBackground(new Color(0, 0, 0));
        searchListPanel.setLayout(new BoxLayout(searchListPanel, BoxLayout.Y_AXIS));
        JScrollPane searchListPanelScroll = new JScrollPane(searchListPanel);
        JPanel userInfo;

        searchHistoryListPanel.setBackground(new Color(0, 0, 0));
        searchHistoryListPanel.setLayout(new BoxLayout(searchHistoryListPanel, BoxLayout.Y_AXIS));
        JScrollPane searchHistoryListPanelScroll = new JScrollPane(searchHistoryListPanel);
        JPanel userInfo2;
        JLabel historyLabel = new JLabel("Search history:");
        historyLabel.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
        historyLabel.setForeground(Color.cyan);
        searchHistoryListPanel.add(historyLabel);

        if (history.isEmpty()) {
            JLabel message = new JLabel("History is empty");
            message.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
            message.setForeground(Color.cyan);
            searchHistoryListPanel.add(message);
        }
        if (result.isEmpty()) {
            JLabel message = new JLabel(" User not found");
            message.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
            message.setForeground(Color.cyan);
            searchListPanel.add(message);
        }

        for (User x : history) {

            userInfo2 = new JPanel();
            userInfo2.setBorder(blackborder);
            userInfo2.setLayout(new GridLayout(1, 2));
            userInfo2.setPreferredSize(new Dimension(400, 60));
            userInfo2.setMaximumSize(new Dimension(400, 60));

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
                            searchListFrame.revalidate();
                            searchListFrame.repaint();
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
                            searchListFrame.revalidate();
                            searchListFrame.repaint();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                } else if (listCommand.startsWith("USER_")) {
                    int userID = Integer.parseInt(listCommand.substring(5));
                    try {
                        output.writeUTF("ADD_TO_HISTORY");
                        output.flush();
                        output.writeUTF(Integer.toString(userID));
                        output.flush();
                        output.writeUTF("FIND_USER");
                        output.flush();
                        output.writeUTF(Integer.toString(userID));
                        output.flush();
                        new UserPage(socket, input, output, thisX, thisY, thisWidth, thisHeight, (User) input.readObject(), LoggedInUser);
                        frame.dispose();
                        searchListFrame.dispose();
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }

            };

            followStatus.addActionListener(listButtonActionListener);
            userName.addActionListener(listButtonActionListener);

            userInfo2.add(userName);
            userInfo2.add(followStatus);
            searchHistoryListPanel.add(userInfo2);

        }

        for (User x : result) {

            userInfo = new JPanel();
            userInfo.setBorder(blackborder);
            userInfo.setLayout(new GridLayout(1, 2));
            userInfo.setPreferredSize(new Dimension(400, 60));
            userInfo.setMaximumSize(new Dimension(400, 60));

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
                            searchListFrame.revalidate();
                            searchListFrame.repaint();
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
                            searchListFrame.revalidate();
                            searchListFrame.repaint();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                } else if (listCommand.startsWith("USER_")) {
                    int userID = Integer.parseInt(listCommand.substring(5));
                    try {
                        output.writeUTF("ADD_TO_HISTORY");
                        output.flush();
                        output.writeUTF(Integer.toString(userID));
                        output.flush();
                        output.writeUTF("FIND_USER");
                        output.flush();
                        output.writeUTF(Integer.toString(userID));
                        output.flush();
                        new UserPage(socket, input, output, thisX, thisY, thisWidth, thisHeight, (User) input.readObject(), LoggedInUser);
                        frame.dispose();
                        searchListFrame.dispose();
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }

            };

            followStatus.addActionListener(listButtonActionListener);
            userName.addActionListener(listButtonActionListener);

            userInfo.add(userName);
            userInfo.add(followStatus);
            searchListPanel.add(userInfo);

        }

        all.add(searchListPanelScroll);
        all.add(searchHistoryListPanelScroll);
        searchListFrame.add(all);
        searchListFrame.setVisible(true);

    }

}
