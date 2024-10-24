import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server extends Thread implements Serializable {
    private static ObjectInputStream input;
    private static ObjectOutputStream output;
    public ArrayList<User> allUsers = new ArrayList<>();
    Random random = new Random(new Date().getTime());
    User LoggedIn;
    boolean checkFile;

    public static void main(String[] args) {

        ServerSocket serverSocket;
        while (true) {
            try {
                serverSocket = new ServerSocket(8080);
                Socket socket = serverSocket.accept();
                new Server(socket);
            } catch (IOException e) {
            }
        }

    }

    public Server(Socket socket) throws IOException {

        input = new ObjectInputStream(socket.getInputStream());
        output = new ObjectOutputStream(socket.getOutputStream());
        String request;
        while (true) {
            try {
                request = input.readUTF();
                switch (request) {
                    case "LOGIN":
                        Login();
                        break;
                    case "SIGNUP":
                        Signup();
                        break;
                    case "CHECK_USERNAME":
                        username_check();
                        break;
                    case "CHECK_EMAIL":
                        email_check();
                        break;
                    case "TWEET":
                        tweet();
                        break;
                    case "GET_TWEETS":
                        getTweets();
                        break;
                    case "GET_EXPLORE_TWEETS":
                        getExploreTweets();
                        break;
                    case "GET_FOLLOWING_TWEETS":
                        getFollowingTweets();
                        break;
                    case "IS_LIKED":
                        isLiked();
                        break;
                    case "IS_SAVED":
                        isSaved();
                        break;
                    case "REMOVE_LIKE":
                        LikeRemover();
                        break;
                    case "ADD_LIKE":
                        addLike();
                        break;
                    case "REMOVE_SAVE":
                        SaveRemover();
                        break;
                    case "ADD_SAVE":
                        addSave();
                        break;
                    case "GET_SAVED_TWEETS":
                        getSavedTweets();
                        break;
                    case "GET_LIKED_TWEETS":
                        getLikedTweets();
                        break;
                    case "GET_TWEET_LIKES":
                        getTweetLikes();
                        break;
                    case "GET_TWEET_SAVES":
                        getTweetSaves();
                        break;
                    case "HAVE_FOLLOW":
                        haveFollow();
                        break;
                    case "FIND_USER":
                        findUser();
                        break;
                    case "ADD_FOLLOWING":
                        addFollowing();
                        break;
                    case "REMOVE_FOLLOWING":
                        removeFollowing();
                        break;
                    case "GET_TWEET_COMMENTS":
                        getTweetComments();
                        break;
                    case "ADD_COMMENT":
                        addComment();
                        break;
                    case "REMOVE_TWEET":
                        removeTweet();
                        break;
                    case "GET_FOLLOWERS":
                        getFollowers();
                        break;
                    case "GET_FOLLOWINGS":
                        getFollowings();
                        break;
                    case "SEARCHING":
                        searching();
                        break;
                    case "ADD_TO_HISTORY":
                        addToHistory();
                        break;
                    case "GET_HISTORY":
                        getHistory();
                        break;
                }
            } catch (IOException e) {
                Save_Users_Information();
                System.out.println("Client disconnected");
                return;
            }
        }
    }

    private void Login() throws IOException {

        Load_Users_Information();
        boolean flag = false;
        String username, password;
        try {
            username = input.readUTF();
            password = input.readUTF();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (User i : allUsers) {
            flag = false;
            if ((i.getEmail().toUpperCase()).equals(username.toUpperCase()) || (i.getUsername().toUpperCase()).equals(username.toUpperCase())) {
                if (password.equals(i.getPassword())) {

                    output.writeUTF("LOGGED_IN");
                    output.flush();
                    output.writeObject(i);
                    output.flush();
                    LoggedIn = i;
                    flag = true;
                    break;

                } else {
                    try {
                        output.writeUTF("Wrong Password!");
                        output.flush();
                        flag = true;
                        break;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        if (!flag) {
            try {
                output.writeUTF("This account was not found!");
                output.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private void Signup() throws IOException {

        Load_Users_Information();
        LoggedIn = new User();
        try {
            LoggedIn.setFirstName(input.readUTF());
            LoggedIn.setLastname(input.readUTF());
            LoggedIn.setUsername(input.readUTF());
            LoggedIn.setPassword(input.readUTF());
            LoggedIn.setEmail(input.readUTF());
            LoggedIn.setBirthDate(input.readUTF());
            LoggedIn.setNumber(input.readUTF());
            LoggedIn.setBio(input.readUTF());
            IDGenerator();
            allUsers.add(LoggedIn);
            Save_Users_Information();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void username_check() {

        try {
            String username = input.readUTF();
            for (User i : allUsers) {
                if ((i.getUsername().toUpperCase()).equals(username.toUpperCase())) {
                    output.writeUTF("TRUE");
                    output.flush();
                    return;
                }
            }
            output.writeUTF("FALSE");
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void email_check() {

        try {
            String email = input.readUTF();
            for (User i : allUsers) {
                if ((i.getEmail().toUpperCase()).equals(email.toUpperCase())) {
                    output.writeUTF("TRUE");
                    output.flush();
                    return;
                }
            }
            output.writeUTF("FALSE");
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    private void tweet() {

        Date tweetTime = new Date();
        Twits newTweet = new Twits();
        try {
            newTweet.setClose(Boolean.parseBoolean(input.readUTF()));
            newTweet.setSave(Boolean.parseBoolean(input.readUTF()));
            newTweet.setComment(Boolean.parseBoolean(input.readUTF()));
            newTweet.setText(input.readUTF());
            newTweet.setTime(tweetTime);

            int id;
            Gen:
            while (true) {
                id = random.nextInt(100000, 999999);
                for (User user : allUsers)
                    for (Twits twit : user.getTwits())
                        if (twit.getTweetID() == id)
                            continue Gen;
                break;
            }
            newTweet.setTweetID(id);
            newTweet.setUser(LoggedIn);
            LoggedIn.getTwits().add(newTweet);
            Save_Users_Information();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }

    private void getTweets() {

        boolean isPrivate = false;
        try {
            User user = FindUserById(Integer.parseInt(input.readUTF()));
            if (user.getID() != LoggedIn.getID()) {
                for (User i : LoggedIn.getFollowings()) {
                    if (i.getID() == user.getID()) {
                        isPrivate = true;
                        break;
                    }
                }
            }
            for (Twits i : user.getTwits()) {
                if (i.isClose() && !isPrivate && (user.getID() != LoggedIn.getID()))
                    continue;
                output.writeObject(i);
                output.flush();
            }
            output.writeObject(null);
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void getExploreTweets() throws IOException {

        Users:
        for (User user : allUsers) {
            if (user.getID() == LoggedIn.getID())
                continue;
            for (User i : LoggedIn.getFollowings())
                if (i.getID() == user.getID())
                    continue Users;
            for (Twits twit : user.getTwits()) {
                if (twit.isClose())
                    continue;
                output.writeObject(twit);
                output.flush();
            }
        }
        output.writeObject(null);
        output.flush();

    }

    private void getFollowingTweets() throws IOException {

        Users:
        for (User user : allUsers) {
            if (user.getID() == LoggedIn.getID())
                continue;
            for (User i : LoggedIn.getFollowings()) {
                if (i.getID() == user.getID()) {
                    for (Twits j : i.getTwits()) {
                        output.writeObject(j);
                        output.flush();
                    }
                }
            }
        }
        output.writeObject(null);
        output.flush();

    }

    private void getSavedTweets() throws IOException {

        for (Twits twit : LoggedIn.getSavedTwits()) {
            output.writeObject(twit);
            output.flush();
        }
        output.writeObject(null);
        output.flush();

    }

    private void getLikedTweets() throws IOException {

        for (Twits twit : LoggedIn.getFavTwits()) {
            output.writeObject(twit);
            output.flush();
        }
        output.writeObject(null);
        output.flush();

    }

    private void getTweetLikes() throws IOException {

        Twits twit;
        twit = FindTweetById(Integer.parseInt(input.readUTF()));
        assert twit != null;
        for (User user : twit.getLikes()) {
            output.writeObject(user);
            output.flush();
        }
        output.writeObject(null);
        output.flush();

    }

    private void getTweetSaves() throws IOException {

        Twits twit;
        twit = FindTweetById(Integer.parseInt(input.readUTF()));
        assert twit != null;
        for (User user : twit.getSaves()) {
            output.writeObject(user);
            output.flush();
        }
        output.writeObject(null);
        output.flush();

    }

    private void LikeRemover() {

        Twits twit;
        User user;
        ArrayList<User> twitTemp = new ArrayList<>();
        ArrayList<Twits> userTemp = new ArrayList<>();
        try {
            twit = FindTweetById(Integer.parseInt(input.readUTF()));
            user = FindUserById(Integer.parseInt(input.readUTF()));
            assert twit != null;
            if (twit.getLikesCount() == 0) {
                output.writeUTF(String.valueOf(false));
                output.flush();
                return;
            }
            for (User i : twit.getLikes()) {
                assert user != null;
                if (i.getID() == user.getID())
                    continue;
                twitTemp.add(i);
            }
            twit.setLikes(twitTemp);
            twit.setLikesCount(twit.getLikesCount() - 1);
            assert user != null;
            for (Twits i : user.getFavTwits()) {
                if (i.getTweetID() == twit.getTweetID())
                    continue;
                userTemp.add(i);
            }
            user.setFavTwits(userTemp);
            user.setUserLikes(user.getUserLikes() - 1);
            Save_Users_Information();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void SaveRemover() {

        Twits twit;
        User user;
        ArrayList<User> twitTemp = new ArrayList<>();
        ArrayList<Twits> userTemp = new ArrayList<>();
        try {
            twit = FindTweetById(Integer.parseInt(input.readUTF()));
            user = FindUserById(Integer.parseInt(input.readUTF()));
            assert twit != null;
            if (twit.getSavesCount() == 0) {
                output.writeUTF(String.valueOf(false));
                output.flush();
                return;
            }
            for (User i : twit.getSaves()) {
                assert user != null;
                if (i.getID() == user.getID())
                    continue;
                twitTemp.add(i);
            }
            twit.setSaves(twitTemp);
            twit.setSavesCount(twit.getSavesCount() - 1);
            assert user != null;
            for (Twits i : user.getSavedTwits()) {
                if (i.getTweetID() == twit.getTweetID())
                    continue;
                userTemp.add(i);
            }
            user.setSavedTwits(userTemp);
            user.setUserSaves(user.getUserSaves() - 1);
            Save_Users_Information();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void isLiked() {

        Twits twit;
        User user;
        try {
            twit = FindTweetById(Integer.parseInt(input.readUTF()));
            user = FindUserById(Integer.parseInt(input.readUTF()));
            assert twit != null;
            for (User i : twit.getLikes()) {
                assert user != null;
                if (i.getID() == user.getID()) {
                    output.writeUTF(String.valueOf(true));
                    output.flush();
                    return;
                }
            }
            output.writeUTF(String.valueOf(false));
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    private void isSaved() {

        Twits twit;
        User user;
        try {
            twit = FindTweetById(Integer.parseInt(input.readUTF()));
            user = FindUserById(Integer.parseInt(input.readUTF()));
            assert twit != null;
            for (User i : twit.getSaves()) {
                assert user != null;
                if (i.getID() == user.getID()) {
                    output.writeUTF(String.valueOf(true));
                    output.flush();
                    return;
                }
            }
            output.writeUTF(String.valueOf(false));
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void addLike() {

        Twits twit;
        User user;
        try {
            twit = FindTweetById(Integer.parseInt(input.readUTF()));
            user = FindUserById(Integer.parseInt(input.readUTF()));
            assert twit != null;
            twit.getLikes().add(user);
            twit.setLikesCount(twit.getLikesCount() + 1);
            assert user != null;
            user.getFavTwits().add(twit);
            user.setUserLikes(user.getUserLikes() + 1);
            Save_Users_Information();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void addSave() {

        Twits twit;
        User user;
        try {
            twit = FindTweetById(Integer.parseInt(input.readUTF()));
            user = FindUserById(Integer.parseInt(input.readUTF()));
            assert twit != null;
            twit.getSaves().add(user);
            twit.setSavesCount(twit.getSavesCount() + 1);
            assert user != null;
            user.getSavedTwits().add(twit);
            user.setUserSaves(user.getUserSaves() + 1);
            Save_Users_Information();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void haveFollow() throws IOException {

        User user;
        user = FindUserById(Integer.parseInt(input.readUTF()));
        assert user != null;
        for (User i : user.getFollowers()) {
            if (i.getID() == LoggedIn.getID()) {
                output.writeUTF(String.valueOf(true));
                output.flush();
                return;
            }
        }
        output.writeUTF(String.valueOf(false));
        output.flush();

    }

    private void addFollowing() throws IOException {

        User user;
        user = FindUserById(Integer.parseInt(input.readUTF()));
        LoggedIn.setFollowings(user);
        LoggedIn.setFollowingsCount(LoggedIn.getFollowingsCount() + 1);
        assert user != null;
        user.setFollowers(LoggedIn);
        user.setFollowersCount(user.getFollowersCount() + 1);
        Save_Users_Information();

    }

    private void removeFollowing() throws IOException {

        ArrayList<User> followingTemp = new ArrayList<>();
        ArrayList<User> followerTemp = new ArrayList<>();
        ArrayList<Twits> newFavTweets = new ArrayList<>();
        ArrayList<Twits> newSavedTweets = new ArrayList<>();
        User user;
        user = FindUserById(Integer.parseInt(input.readUTF()));
        for (User i : LoggedIn.getFollowings()) {
            assert user != null;
            if (i.getID() == user.getID())
                continue;
            followingTemp.add(i);
        }
        LoggedIn.setFollowings(followingTemp);
        LoggedIn.setFollowingsCount(LoggedIn.getFollowingsCount() - 1);
        for (Twits i : LoggedIn.getFavTwits()) {
            if (i.isClose() && (i.getUser().getID() != LoggedIn.getID()) && (i.getUser().getID() == user.getID())) {
                continue;
            }
            newFavTweets.add(i);
        }
        LoggedIn.setFavTwits(newFavTweets);
        for (Twits i : LoggedIn.getSavedTwits()) {
            if (i.isClose() && (i.getUser().getID() != LoggedIn.getID()) && (i.getUser().getID() == user.getID())) {
                continue;
            }
            newSavedTweets.add(i);
        }
        LoggedIn.setSavedTwits(newSavedTweets);
        assert user != null;
        for (User i : user.getFollowers()) {
            if (i.getID() == LoggedIn.getID())
                continue;
            followerTemp.add(i);
        }
        user.setFollowers(followerTemp);
        user.setFollowersCount(user.getFollowersCount() - 1);
        for (Twits i : user.getTwits()) {
            if (i.isClose()) {
                for (User j : i.getLikes()) {
                    if (j.getID() == LoggedIn.getID()) {
                        i.getLikes().remove(LoggedIn);
                        i.setLikesCount(i.getLikesCount() - 1);
                        break;
                    }
                }
                for (User j : i.getSaves()) {
                    if (j.getID() == LoggedIn.getID()) {
                        i.getSaves().remove(LoggedIn);
                        i.setSavesCount(i.getSavesCount() - 1);
                        break;
                    }
                }
                for (Comment j : i.getComments()) {
                    if (j.getUser().getID() == LoggedIn.getID()) {
                        i.getComments().remove(j);
                        i.setCommentsCount(i.getCommentsCount() - 1);
                        break;
                    }
                }
            }
        }
        Save_Users_Information();

    }

    private void addComment() throws IOException {

        Twits twit;
        User user;
        twit = FindTweetById(Integer.parseInt(input.readUTF()));
        user = FindUserById(Integer.parseInt(input.readUTF()));
        Comment comment = new Comment();
        comment.setComment(input.readUTF());
        comment.setUser(user);
        assert twit != null;
        twit.setCommentsCount(twit.getCommentsCount() + 1);
        twit.getComments().add(comment);
        Save_Users_Information();

    }

    private void getTweetComments() throws IOException {

        Twits twit;
        twit = FindTweetById(Integer.parseInt(input.readUTF()));
        assert twit != null;
        for (Comment comment : twit.getComments()) {
            output.writeObject(comment);
            output.flush();
        }
        output.writeObject(null);
        output.flush();

    }

    private void removeTweet() throws IOException {

        Twits twit;
        twit = FindTweetById(Integer.parseInt(input.readUTF()));
        for (User user : allUsers) {
            for (Twits twits : user.getFavTwits()) {
                if (twits.getTweetID() == twit.getTweetID()) {
                    user.getFavTwits().remove(twit);
                    user.setUserLikes(user.getUserLikes() - 1);
                    break;
                }
            }
            for (Twits twits : user.getSavedTwits()) {
                if (twits.getTweetID() == twit.getTweetID()) {
                    user.getSavedTwits().remove(twit);
                    user.setUserSaves(user.getUserSaves() - 1);
                    break;
                }
            }
        }
        LoggedIn.getTwits().remove(twit);
        Save_Users_Information();

    }

    private void getFollowers() throws IOException {

        User user;
        user = FindUserById(Integer.parseInt(input.readUTF()));
        assert user != null;
        for (User i : user.getFollowers()) {
            output.writeObject(i);
            output.flush();
        }
        output.writeObject(null);
        output.flush();

    }

    private void getFollowings() throws IOException {

        User user;
        user = FindUserById(Integer.parseInt(input.readUTF()));
        assert user != null;
        for (User i : user.getFollowings()) {
            output.writeObject(i);
            output.flush();
        }
        output.writeObject(null);
        output.flush();

    }

    private void searching() throws IOException {

        String username = input.readUTF();
        for (User user : allUsers) {
            if (user.getUsername().contains(username)) {
                output.writeObject(user);
                output.flush();
            }
        }
        output.writeObject(null);
        output.flush();

    }

    private void addToHistory() throws IOException {

        User user;
        user = FindUserById(Integer.parseInt(input.readUTF()));
        for (User i : LoggedIn.getSearchHistory()) {
            if (i.getID() == user.getID())
                return;
        }
        LoggedIn.getSearchHistory().add(user);
        Save_Users_Information();

    }

    private void getHistory() throws IOException {

        for (User user : LoggedIn.searchHistory) {
            output.writeObject(user);
            output.flush();
        }
        output.writeObject(null);
        output.flush();

    }

    private void IDGenerator() {

        int id;
        Gen:
        while (true) {
            id = random.nextInt(10000, 99999);
            for (User i : allUsers) {
                if (i.getID() == id) {
                    continue Gen;
                }
            }
            break;
        }
        LoggedIn.setID(id);

    }

    private User FindUserById(int id) {

        for (User user : allUsers)
            if (user.getID() == id)
                return user;

        return null;
    }

    private Twits FindTweetById(int id) {

        for (User user : allUsers)
            for (Twits twit : user.getTwits())
                if (twit.getTweetID() == id)
                    return twit;

        return null;
    }

    private void findUser() throws IOException {

        User user;
        user = FindUserById(Integer.parseInt(input.readUTF()));
        output.writeObject(user);
        output.flush();

    }

    private void Save_Users_Information() throws IOException {

        File Info = new File("Users.dat");
        checkFile = Info.createNewFile();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(Info))) {
            oos.writeObject(allUsers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void Load_Users_Information() {

        File Info = new File("Users.dat");
        if (Info.exists()) {
            allUsers.clear();
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(Info))) {
                allUsers.addAll((ArrayList<User>) ois.readObject());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


}

