package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.MovieReview;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolderReview> {

    private static final String TYPE_POSITIVE = "Позитивный";
    private static final String TYPE_NEUTRAL = "Нейтральный";
    private static final String TYPE_NEGATIVE = "Негативный";

    private List<MovieReview> reviews = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolderReview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_review,
                        parent,
                        false);
        return new ViewHolderReview(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderReview holder, int position) {

        MovieReview movieReview = reviews.get(position);

        holder.textViewReviewAuthor.setText(movieReview.getAuthor());
        holder.textViewReviewDescription.setText(movieReview.getReview());
        holder.textViewReviewTitle.setText(movieReview.getTitle());

        String type = movieReview.getType();
        
        int colorResId = 0;
        switch (type) {
            case TYPE_POSITIVE:
                colorResId = android.R.color.holo_green_light;
                break;
            case TYPE_NEUTRAL:
                colorResId = android.R.color.holo_orange_light;
                break;
            case TYPE_NEGATIVE:
                colorResId = android.R.color.holo_red_light;
                break;
        }

        int color = ContextCompat.getColor(holder.itemView.getContext(), colorResId);
        holder.linearLayoutContainer.setBackgroundColor(color);


    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    static class ViewHolderReview extends RecyclerView.ViewHolder {

        private final TextView textViewReviewTitle;
        private final TextView textViewReviewAuthor;
        private final TextView textViewReviewDescription;
        private final LinearLayout linearLayoutContainer;


        public ViewHolderReview(@NonNull View itemView) {
            super(itemView);
            textViewReviewAuthor = itemView.findViewById(R.id.textViewReviewAuthor);
            textViewReviewTitle = itemView.findViewById(R.id.textViewReviewTitle);
            textViewReviewDescription = itemView.findViewById(R.id.textViewReviewDescription);
            linearLayoutContainer = itemView.findViewById(R.id.linerLayoutContainer);

        }
    }

    public void setReviews(List<MovieReview> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }
}
