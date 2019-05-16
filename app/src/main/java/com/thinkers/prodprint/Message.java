package com.thinkers.prodprint;

import java.io.Serializable;

public class Message implements Serializable {
    private String id, message;


    Message() {
    }

    public Message(String id, String message, String createdAt) {
        this.id = id;
        this.message = message;


    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String getMessage() {
        return message;
    }

    void setMessage(String message) {
        this.message = message;
    }


}

