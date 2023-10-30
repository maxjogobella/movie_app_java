package com.example.myapplication.models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Poster implements Serializable {

    @SerializedName("url")
    private final String url;

    public Poster(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    @NonNull
    @Override
    public String toString() {
        return "Poster{" +
                "url='" + url + '\'' +
                '}';
    }
}
