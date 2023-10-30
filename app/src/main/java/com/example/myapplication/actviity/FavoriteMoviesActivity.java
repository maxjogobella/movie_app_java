package com.example.myapplication.actviity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.adapter.MovieAdapter;
import com.example.myapplication.database.MovieDao;
import com.example.myapplication.database.MovieDatabase;
import com.example.myapplication.models.Movie;

import java.util.List;

public class FavoriteMoviesActivity extends AppCompatActivity {

    private RecyclerView recyclerViewFavoriteMovies;
    private MovieDao movieDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_movies);
        initViews();
        MovieAdapter movieAdapter = new MovieAdapter();
        recyclerViewFavoriteMovies.setAdapter(movieAdapter);
        recyclerViewFavoriteMovies.setLayoutManager(new GridLayoutManager(this, 2));
        movieDao = MovieDatabase.getInstance(getApplication()).movieDao();

        movieAdapter.setOnMovieClickListener(new MovieAdapter.onMovieClickListener() {
            @Override
            public void onMovieClick(Movie movie) {
                Intent intent = MovieDetailActivity.newIntent(FavoriteMoviesActivity.this,
                        movie);
                startActivity(intent);
            }
        });

       getMovies().observe(this, new Observer<List<Movie>>() {
           @Override
           public void onChanged(List<Movie> movies) {
               movieAdapter.setMovies(movies);
           }
       });

    }

    LiveData<List<Movie>> getMovies() {
        return movieDao.getMovies();
    }

    private void initViews()
    {
        recyclerViewFavoriteMovies = findViewById(R.id.recyclerViewFavoriteMovies);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, FavoriteMoviesActivity.class);
    }
}