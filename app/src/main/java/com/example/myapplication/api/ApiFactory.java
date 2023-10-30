package com.example.myapplication.api;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiFactory {

    private static final String BASE_MOVIE_URL = "https://api.kinopoisk.dev/v1.3/";
    private static final String BASE_REVIEW_URL = "https://api.kinopoisk.dev/v1/";

    private static ApiService apiService;
    private static final Retrofit retrofitMovie = new Retrofit.Builder()
            .baseUrl(BASE_MOVIE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build();

    private static final Retrofit retrofitReview = new Retrofit.Builder()
            .baseUrl(BASE_REVIEW_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build();

    public static ApiService getApiService() {
        return apiService = retrofitMovie.create(ApiService.class);
    }

    public static ApiService getApiReview() {
        return apiService = retrofitReview.create(ApiService.class);
    }

}
