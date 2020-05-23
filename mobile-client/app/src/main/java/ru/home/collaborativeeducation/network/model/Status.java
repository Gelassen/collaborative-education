package ru.home.collaborativeeducation.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Status<T extends Payload> {

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("payload")
    @Expose
    private List<T> payload = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<T> getPayload() {
        return payload;
    }

    public void setPayload(List<T> payload) {
        this.payload = payload;
    }

}
