package ru.home.collaborativeeducation.network.model;


import com.google.gson.annotations.SerializedName;

public class CoursePayload implements Payload {

    private Long uid;
    private String title;
    @SerializedName("course_uid")
    private Long categoryUid;

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
}
