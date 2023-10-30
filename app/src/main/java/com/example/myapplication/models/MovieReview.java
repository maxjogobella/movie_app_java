package com.example.myapplication.models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class MovieReview {

    @SerializedName("id")
    private final int id;
    @SerializedName("title")
    private final String title;
    @SerializedName("type")
    private final String type;
    @SerializedName("review")
    private final String review;
    @SerializedName("author")
    private final String author;

    public MovieReview(int id, String title, String type, String review, String author) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.review = review;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getReview() {
        return review;
    }

    public String getAuthor() {
        return author;
    }

    @NonNull
    @Override
    public String toString() {
        return "MovieReview{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", review='" + review + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
