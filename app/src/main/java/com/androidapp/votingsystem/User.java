package com.androidapp.votingsystem;

/**
 * Created by Nico on 4/17/2017.
 */

public class User {
    public String uid;
    public String name;
    public String vote;

    public User() {
    }

    public User(String uid, String name, String vote) {
        this.uid = uid;
        this.name = name;
        this.vote = vote;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }
}
