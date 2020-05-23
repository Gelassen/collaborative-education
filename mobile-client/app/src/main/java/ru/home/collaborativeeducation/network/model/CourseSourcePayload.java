package ru.home.collaborativeeducation.network.model;

import java.io.Serializable;
import java.util.ArrayList;

public class CourseSourcePayload implements Payload, Serializable {

    private Long uid;
    private String title;
    private String source;
    private Long courseUid;
    private ArrayList<String> users;

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

    public ArrayList<String> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<String> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "CourseSourcePayload{" +
                "uid=" + uid +
                ", title='" + title + '\'' +
                ", source='" + source + '\'' +
                ", courseUid=" + courseUid +
                ", users=" + users +
                '}';
    }
}
