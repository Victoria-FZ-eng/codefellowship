package codefellowship.example.codefellowship;

import javax.persistence.*;


@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String body;
    private String time;

    @ManyToOne
    private ApplicationUser userPost;

    public Post(String body, String time, ApplicationUser userPost) {
        this.body = body;
        this.time = time;
        this.userPost = userPost;
    }


    public int getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public String getTime() {
        return time;
    }

    public ApplicationUser getUserPost() {
        return userPost;
    }


    public void setBody(String body) {
        this.body = body;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setUserPost(ApplicationUser userPost) {
        this.userPost = userPost;
    }
}
