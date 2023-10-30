package com.example.myapplication.response;

import androidx.annotation.NonNull;

import com.example.myapplication.models.Trailers;
import com.google.gson.annotations.SerializedName;

public class TrailerResponse {

    @SerializedName("videos")
    private final Trailers trailers;

    public TrailerResponse(Trailers trailers) {
        this.trailers = trailers;
    }

    public Trailers getTrailers() {
        return trailers;
    }

    @Override
    @NonNull
    public String toString() {
        return "TrailerResponse{" +
                "trailers=" + trailers +
                '}';
    }
}
