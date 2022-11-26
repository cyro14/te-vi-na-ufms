package com.example.myapplication.model;

public class HomeModel {

    private String userName, timestamp, imagemPerfil, postImage, uid;

    private int likeCount;

    public HomeModel(){

    }

    public HomeModel(String userName, String timestamp, String imagemPerfil, String postImage, String uid, int likeCount) {
        this.userName = userName;
        this.timestamp = timestamp;
        this.imagemPerfil = imagemPerfil;
        this.postImage = postImage;
        this.uid = uid;
        this.likeCount = likeCount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getProfileImage() {
        return imagemPerfil;
    }

    public void setProfileImage(String profileImage) {
        this.imagemPerfil = profileImage;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }
}
