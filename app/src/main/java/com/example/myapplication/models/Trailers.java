package com.example.myapplication.models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Trailers {

    @SerializedName("trailers")
    private final List<Trailer> trailerList;

    public Trailers(List<Trailer> trailerList) {
        this.trailerList = trailerList;
    }

    public List<Trailer> getTrailerList() {
        return trailerList;
    }

    @Override
    @NonNull
    public String toString() {
        return "Trailers{" +
                "trailerList=" + trailerList +
                '}';
    }
}
