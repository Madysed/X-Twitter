import java.io.Serializable;

public class Comment implements Serializable {

    String comment;
    User user;
    public Comment(String comment, User user) {
        this.comment = comment;
        this.user = user;
    }

    public Comment() {

    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
