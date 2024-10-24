import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Twits implements Serializable {
    private int likesCount = 0, savesCount = 0, commentsCount = 0;
    private ArrayList<User> likes = new ArrayList<>();
    private ArrayList<Comment> comments = new ArrayList<>();
    private ArrayList<User> saves = new ArrayList<>();
    boolean comment, close, save;
    private String text;
    Date time;
    User user;
    int TweetID;

    public Twits(int likesCount, int savesCount, int commentsCount, ArrayList<User> likes, ArrayList<Comment> comments, ArrayList<User> saves, boolean comment, boolean close, boolean save, String text, Date time, User user, int tweetID) {
        this.likesCount = likesCount;
        this.savesCount = savesCount;
        this.commentsCount = commentsCount;
        this.likes = likes;
        this.comments = comments;
        this.saves = saves;
        this.comment = comment;
        this.close = close;
        this.save = save;
        this.text = text;
        this.time = time;
        this.user = user;
        TweetID = tweetID;
    }

    public int getTweetID() {
        return TweetID;
    }

    public void setTweetID(int tweetID) {
        TweetID = tweetID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Twits () {

    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public int getSavesCount() {
        return savesCount;
    }

    public void setSavesCount(int savesCount) {
        this.savesCount = savesCount;
    }

    public ArrayList<User> getLikes() {
        if (likes == null)
            return null;
        return likes;
    }

    public void setLikes(ArrayList<User> likes) {
        likes.clear();
        this.likes = likes;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        comments.clear();
        this.comments = comments;
    }

    public boolean isComment() {
        return comment;
    }

    public void setComment(boolean comment) {
        this.comment = comment;
    }

    public boolean isClose() {
        return close;
    }

    public void setClose(boolean close) {
        this.close = close;
    }

    public boolean isSave() {
        return save;
    }

    public void setSave(boolean save) {
        this.save = save;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public ArrayList<User> getSaves() {
        return saves;
    }

    public void setSaves(ArrayList<User> saves) {
        saves.clear();
        this.saves = saves;
    }

    public void setWhoLiked(User user) {
        this.likes.add(user);
    }

}
