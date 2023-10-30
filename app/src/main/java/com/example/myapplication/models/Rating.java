package com.example.myapplication.models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Rating implements Serializable {

    @SerializedName("imdb")
    private final double imdb;

    public Rating(double imdb) {
        this.imdb = imdb;
    }

    public double getImdb() {
        return imdb;
    }

    @Override
    @NonNull
    public String toString() {
        return "Rating{" +
                "imdb=" + imdb +
                '}';
    }
}
