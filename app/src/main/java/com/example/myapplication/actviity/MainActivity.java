package com.example.myapplication.actviity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.myapplication.R;
import com.example.myapplication.adapter.MovieAdapter;
import com.example.myapplication.models.Movie;
import com.example.myapplication.viewmodels.MainViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;
    private ProgressBar progressBar;
    private RecyclerView recyclerViewMovie;
    private MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        movieAdapter = new MovieAdapter();
        initViews();
        recyclerViewMovie.setAdapter(movieAdapter);
        recyclerViewMovie.setLayoutManager(new GridLayoutManager(this, 2));

        if (savedInstanceState == null) {
            viewModel.loadMovie();
        }

        movieAdapter.setOnMovieClickListener(new MovieAdapter.onMovieClickListener() {
            @Override
            public void onMovieClick(Movie movie) {
                Intent intent = MovieDetailActivity.newIntent(MainActivity.this, movie);
                startActivity(intent);
            }
        });

        movieAdapter.setOnEndReachListener(new MovieAdapter.onEndReachListener() {
            @Override
            public void onReachEnd() {
                viewModel.loadMovie();
            }
        });

        viewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if (isLoading) {
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        viewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                movieAdapter.setMovies(movies);
            }
        });
    }

    private void initViews() {
        progressBar = findViewById(R.id.progressBar);
        recyclerViewMovie = findViewById(R.id.recyclerViewMovies);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.itemFavorite) {
            Intent intent = FavoriteMoviesActivity.newIntent(this);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}