package com.example.a123;

public class TextReview {
    private String userName;
    private String textReview;

    public TextReview() {}

    public TextReview(String userName, String textReview) {
        this.userName = userName;
        this.textReview = textReview;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTextReview() {
        return textReview;
    }

    public void setTextReview(String textReview) {
        this.textReview = textReview;
    }
}
