/*
 * Copyright © 2017 Merck Sharp & Dohme Corp., a subsidiary of Merck & Co., Inc.
 * All rights reserved.
 */
package com.ivolasek.sparkcourse.ingest.dto;

public class Survey {
    private long id;
    private String city;
    private int votes;

    public Survey() {
    }

    public Survey(long id, String city, int votes) {
        this.id = id;
        this.city = city;
        this.votes = votes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }
}
