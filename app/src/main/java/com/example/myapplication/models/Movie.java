package com.example.myapplication.models;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "favorite_movies")
public class Movie implements Serializable {

    @PrimaryKey
    @SerializedName("id")
    private final int id;
    @SerializedName("name")
    private final String name;
    @SerializedName("description")
    private final String description;
    @SerializedName("year")
    private final int year;
    @SerializedName("rating")
    @Embedded
    private final Rating rating;
    @Embedded
    @SerializedName("poster")
    private final Poster poster;

    public Movie(int id, String name, String description, int year, Rating rating, Poster poster) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.year = year;
        this.rating = rating;
        this.poster = poster;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getYear() {
        return year;
    }

    public Rating getRating() {
        return rating;
    }

    public Poster getPoster() {
        return poster;
    }

    @NonNull
    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", year=" + year +
                ", rating=" + rating +
                ", poster=" + poster +
                '}';
    }
}
