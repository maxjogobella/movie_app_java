package com.example.myapplication.response;

import androidx.annotation.NonNull;

import com.example.myapplication.models.MovieReview;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewResponse {

    @SerializedName("docs")
    private final List<MovieReview> listReview;

    public ReviewResponse(List<MovieReview> listReview) {
        this.listReview = listReview;
    }

    public List<MovieReview> getListReview() {
        return listReview;
    }

    @NonNull
    @Override
    public String toString() {
        return "ReviewResponse{" +
                "listReview=" + listReview +
                '}';
    }
}

