package com.example.myapplication.api;

    // token -> 0CAED6A-4V9MTCF-KYE49QN-GSQK4PS

import com.example.myapplication.response.MovieResponse;
import com.example.myapplication.response.ReviewResponse;
import com.example.myapplication.response.TrailerResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("movie?token=0CAED6A-4V9MTCF-KYE49QN-GSQK4PS&field=rating.imdb&search=7-10&sortField=votes.imdb&sortType=-1&limit=40")
    Single<MovieResponse> loadMovie(@Query("page") int page);


    @GET("movie/{idMovie}?token=YG6VWAJ-M18M254-P5KF2A4-5VQGA9N")
    Single<TrailerResponse> getTrailer(@Path("idMovie") int idMovie);

    @GET("review?token=0CAED6A-4V9MTCF-KYE49QN-GSQK4PS&limit=3")
    Single<ReviewResponse> getReview(@Query("movieId") int movieId);

}
