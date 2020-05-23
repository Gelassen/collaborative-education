package ru.home.collaborativeeducation.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiResponse<T extends Payload> {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("status")
    @Expose
    private Status<T> status;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Status<T> getStatus() {
        return status;
    }

    public void setStatus(Status<T> status) {
        this.status = status;
    }
}

