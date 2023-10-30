package com.example.myapplication.adapter;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolderMovie> {

    private List<Movie> movies = new ArrayList<>();
    private onEndReachListener onEndReachListener;
    private onMovieClickListener onMovieClickListener;

    public void setOnMovieClickListener(MovieAdapter.onMovieClickListener onMovieClickListener) {
        this.onMovieClickListener = onMovieClickListener;
    }

    public void setOnEndReachListener(MovieAdapter.onEndReachListener onEndReachListener) {
        this.onEndReachListener = onEndReachListener;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolderMovie onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie,
                        parent,
                        false);
        return new ViewHolderMovie(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderMovie holder, int position) {

        Log.d("MovieAdapter", "Loaded position " + position);

        Movie movie = movies.get(position);

        if (movie != null && movie.getPoster() != null) {
            Glide.with(holder.itemView)
                    .load(movie.getPoster().getUrl())
                    .error("https://st.kp.yandex.net/images/no-poster.gif")
                    .into(holder.imageViewPoster);
    }

        double rating = movie.getRating().getImdb();
        holder.textViewRating.setText(String.valueOf(rating));

        int backgroundId;
        if (rating > 7) {
            backgroundId = R.drawable.green_circle;
        } else if (rating > 4) {
            backgroundId = R.drawable.orange_circle;
        } else {
            backgroundId = R.drawable.red_circle;
        }

        Drawable background = ContextCompat.getDrawable(holder.itemView.getContext(), backgroundId);
        holder.textViewRating.setBackground(background);

        if (position >= movies.size() - 10 && onEndReachListener != null) {
            onEndReachListener.onReachEnd();
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onMovieClickListener != null) {
                    onMovieClickListener.onMovieClick(movie);
                }
            }
        });
    }


    public interface onEndReachListener {
        void onReachEnd();
    }

    public interface onMovieClickListener {
        void onMovieClick(Movie movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    static class ViewHolderMovie extends RecyclerView.ViewHolder {

        private final ImageView imageViewPoster;
        private final TextView textViewRating;

        public ViewHolderMovie(@NonNull View itemView) {
            super(itemView);
            imageViewPoster = itemView.findViewById(R.id.imageViewPoster);
            textViewRating = itemView.findViewById(R.id.textViewRating);
        }
    }
}
