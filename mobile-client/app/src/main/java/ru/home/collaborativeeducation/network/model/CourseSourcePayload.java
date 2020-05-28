package ru.home.collaborativeeducation.network.model;

import java.io.Serializable;
import java.util.ArrayList;

public class CourseSourcePayload implements Payload, Serializable {

    private Long uid;
    private String title;
    private String source;
    private Long courseUid;
    private String author;

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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Long getCourseUid() {
        return courseUid;
    }

    public void setCourseUid(Long courseUid) {
        this.courseUid = courseUid;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
