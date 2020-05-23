package ru.home.collaborativeeducation.network.model;

public class CategoryPayload implements Payload {

    private Long uid;
    private String title;

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
}
