package ru.home.collaborativeeducation.network.model;


import com.google.gson.annotations.SerializedName;

public class CoursePayload implements Payload {

    private Long uid;
    private String title;
    @SerializedName("course_uid")
    private Long categoryUid;
    private String author;

    public CoursePayload() {
    }

    public CoursePayload(Long uid, String title, Long categoryUid) {
        this.uid = uid;
        this.title = title;
        this.categoryUid = categoryUid;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getCategoryUid() {
        return categoryUid;
    }

    public void setCategoryUid(Long categoryUid) {
        this.categoryUid = categoryUid;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
