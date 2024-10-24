import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private String FirstName, Lastname, Username, Email, Password, BirthDate, Number, Bio;
    private int ID, followersCount = 0, followingsCount = 0, userLikes = 0, userSaves = 0;
    private ArrayList<Twits> twits = new ArrayList<>();
    private ArrayList<User> followers = new ArrayList<>();
    private ArrayList<User> followings = new ArrayList<>();
    private ArrayList<Twits> favTwits = new ArrayList<>();
    private ArrayList<Twits> savedTwits = new ArrayList<>();
    public ArrayList<User> searchHistory = new ArrayList<>();

    public User(String firstName, String lastname, String username, String email, String password, String birthDate, String number, String bio, int ID, int followersCount, int followingsCount, int userLikes, int userSaves, ArrayList<Twits> twits, ArrayList<User> followers, ArrayList<User> followings, ArrayList<Twits> favTwits, ArrayList<Twits> savedTwits, ArrayList<User> searchHistory) {
        FirstName = firstName;
        Lastname = lastname;
        Username = username;
        Email = email;
        Password = password;
        BirthDate = birthDate;
        Number = number;
        Bio = bio;
        this.ID = ID;
        this.followersCount = followersCount;
        this.followingsCount = followingsCount;
        this.userLikes = userLikes;
        this.userSaves = userSaves;
        this.twits = twits;
        this.followers = followers;
        this.followings = followings;
        this.favTwits = favTwits;
        this.savedTwits = savedTwits;
        this.searchHistory = searchHistory;
    }

    public User () {

    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(String birthDate) {
        BirthDate = birthDate;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getBio() {
        return Bio;
    }

    public void setBio(String bio) {
        Bio = bio;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public int getFollowingsCount() {
        return followingsCount;
    }

    public void setFollowingsCount(int followingsCount) {
        this.followingsCount = followingsCount;
    }

    public int getUserLikes() {
        return userLikes;
    }

    public void setUserLikes(int userLikes) {
        this.userLikes = userLikes;
    }

    public int getUserSaves() {
        return userSaves;
    }

    public void setUserSaves(int userSaves) {
        this.userSaves = userSaves;
    }

    public void setFollowers(User user) {
        this.followers.add(user);
    }

    public ArrayList<User> getFollowers() {
        return followers;
    }

    public void setFollowings(User user) {
        this.followings.add(user);
    }

    public ArrayList<User> getFollowings() {
        return followings;
    }

    public void setFavTwits(Twits twits) {
        this.favTwits.add(twits);
    }

    public ArrayList<Twits> getFavTwits() {
        return favTwits;
    }

    public void setSavedTwits(Twits twits) {
        this.savedTwits.add(twits);
    }

    public ArrayList<Twits> getSavedTwits() {
        return savedTwits;
    }

    public void setTwitsEach(Twits twits) {
        this.twits.add(twits);
    }

    public ArrayList<Twits> getTwits() {
        if (twits == null)
            return null;
        return twits;
    }

    public ArrayList<User> getFollowersArrayList() {
        return followers;
    }

    public ArrayList<User> getFollowingsArrayList() {
        return followings;
    }

    public void setTwits(ArrayList<Twits> twits) {
        twits.clear();
        this.twits = twits;
    }

    public void setFollowers(ArrayList<User> followers) {
        this.followers = followers;
    }

    public void setFollowings(ArrayList<User> followings) {
        this.followings = followings;
    }

    public void setFavTwits(ArrayList<Twits> favTwits) {
        favTwits.clear();
        this.favTwits = favTwits;
    }

    public void setSavedTwits(ArrayList<Twits> savedTwits) {
        savedTwits.clear();
        this.savedTwits = savedTwits;
    }

    public ArrayList<Twits> getFavTwitsArrayList() {
        return favTwits;
    }

    public ArrayList<Twits> getSavedTwitsArrayList() {
        return savedTwits;
    }

    public void removeTweets() {
        twits.clear();
    }

    public ArrayList<User> getSearchHistory() {
        return searchHistory;
    }

    public void setSearchHistory(ArrayList<User> searchHistory) {
        this.searchHistory = searchHistory;
    }

}
