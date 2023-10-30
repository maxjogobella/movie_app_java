package com.example.myapplication.actviity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.adapter.ReviewAdapter;
import com.example.myapplication.adapter.TrailerAdapter;
import com.example.myapplication.database.MovieDao;
import com.example.myapplication.database.MovieDatabase;
import com.example.myapplication.models.Movie;
import com.example.myapplication.models.MovieReview;
import com.example.myapplication.models.Trailer;
import com.example.myapplication.viewmodels.MovieDetailActivityModel;

import java.util.List;

import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieDetailActivity extends AppCompatActivity {

    private ImageView imageViewPosterDescription;
    private TextView textViewTitle;
    private TextView textViewYear;
    private TextView textViewDescription;
    private static final String EXTRA_MOVIE = "movieObject";
    private MovieDetailActivityModel viewModel;
    private TrailerAdapter trailerAdapter;
    private RecyclerView recyclerViewTrailers;
    private ReviewAdapter reviewAdapter;
    private RecyclerView recyclerViewReview;
    private ImageView imageViewStar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        viewModel = new ViewModelProvider(this).get(MovieDetailActivityModel.class);
        initViews();
        trailerAdapter = new TrailerAdapter();
        reviewAdapter = new ReviewAdapter();
        recyclerViewTrailers.setAdapter(trailerAdapter);
        recyclerViewReview.setAdapter(reviewAdapter);
        recyclerViewTrailers.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewReview.setLayoutManager(new LinearLayoutManager(this));

        Movie movie = (Movie) getIntent().getSerializableExtra(EXTRA_MOVIE);

        viewModel.loadTrailer(movie.getId());
        viewModel.loadReview(movie.getId());

        Drawable starOff= ContextCompat.getDrawable(MovieDetailActivity.this,
                android.R.drawable.star_big_off);

        Drawable starOn= ContextCompat.getDrawable(MovieDetailActivity.this,
                android.R.drawable.star_big_on);

        viewModel.getFavoriteMovie(movie.getId()).observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(Movie movieFromDB) {
                if (movieFromDB == null) {
                    imageViewStar.setImageDrawable(starOff);
                    imageViewStar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            viewModel.insertMovie(movie);
                        }
                    });
                } else {
                    imageViewStar.setImageDrawable(starOn);
                    imageViewStar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            viewModel.removeMovie(movie.getId());
                        }
                    });
                }
            }
        });



        trailerAdapter.setOnClickTrailerView(new TrailerAdapter.onClickTrailerView() {
            @Override
            public void onClick(Trailer trailer) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(trailer.getUrl()));
                startActivity(intent);
            }
        });

        viewModel.getReviews().observe(this, new Observer<List<MovieReview>>() {
            @Override
            public void onChanged(List<MovieReview> movieReviews) {
                reviewAdapter.setReviews(movieReviews);
            }
        });


        viewModel.getTrailers().observe(this, new Observer<List<Trailer>>() {
            @Override
            public void onChanged(List<Trailer> trailers) {
                trailerAdapter.setTrailerList(trailers);
            }
        });

        if (movie != null && movie.getPoster() != null) {
            Glide.with(this)
                    .load(movie.getPoster().getUrl())
                    .into(imageViewPosterDescription);
            textViewTitle.setText(movie.getName());
            textViewDescription.setText(movie.getDescription());
            textViewYear.setText(String.valueOf(movie.getYear()));
        }

    }

    private void initViews() {
        imageViewStar = findViewById(R.id.imageViewStar);
        recyclerViewReview = findViewById(R.id.recyclerViewReviews);
        recyclerViewTrailers = findViewById(R.id.recyclerViewTrailers);
        imageViewPosterDescription = findViewById(R.id.imageViewPosterDetail);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewYear = findViewById(R.id.textViewYear);
        textViewDescription = findViewById(R.id.textViewDescription);
    }

    public static Intent newIntent(Context context, Movie movie) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(EXTRA_MOVIE, movie);
        return intent;
    }


}