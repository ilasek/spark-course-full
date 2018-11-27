/*
 * Copyright © 2017 Merck Sharp & Dohme Corp., a subsidiary of Merck & Co., Inc.
 * All rights reserved.
 */
package com.ivolasek.sparkcourse.streaming;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;


/**
 * Utility class to represent a simplified tweet structure.
 */
public class Tweet {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date createdAt;
    private String text;

    public Tweet() {
    }

    public  Tweet(Date createdAt, String text) {
        this.createdAt = createdAt;
        this.text = text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "createdAt=" + createdAt +
                ", text='" + text + '\'' +
                '}';
    }
}
