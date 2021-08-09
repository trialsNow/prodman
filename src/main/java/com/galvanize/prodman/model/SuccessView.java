package com.galvanize.prodman.model;

import com.fasterxml.jackson.annotation.JsonView;

import java.io.Serializable;

public class SuccessView<T> implements Serializable {
    private static final long serialVersionUID = 6967982790589251991L;

    @JsonView({Views.GetResponse.class})
    private Boolean success = true;

    @JsonView({Views.GetResponse.class})
    private T data;

    public SuccessView(String s) {

    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public SuccessView(T data) {
        this.data = data;
    }
}
