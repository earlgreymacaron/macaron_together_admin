package com.cs496.macaron_together_admin;

import java.io.Serializable;

/**
 * Created by q on 2017-01-06.
 */

public class PostData implements Serializable {
    private String title;
    private String author;
    private String body;
    private String timestamp;

    public PostData() { }

    public PostData(String title, String author, String body, String timestamp) {
        this.title = title;
        this.author = author;
        this.body = body;
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String t) {
        title = t;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String t) {
        author = t;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String t) {
        timestamp = t;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String t) { body = t; }


}
