package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.Trailer;

import java.util.ArrayList;
import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolderTrailer> {

    private List<Trailer> trailerList = new ArrayList<>();
    private onClickTrailerView onClickTrailerView;

    public void setOnClickTrailerView(TrailerAdapter.onClickTrailerView onClickTrailerView) {
        this.onClickTrailerView = onClickTrailerView;
    }

    @NonNull
    @Override
    public ViewHolderTrailer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext())
               .inflate(R.layout.item_trailer,
                       parent,
                       false);
       return new ViewHolderTrailer(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderTrailer holder, int position) {

        Trailer trailer = trailerList.get(position);
        holder.textViewTrailer.setText(trailer.getName());
        holder.imageViewTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickTrailerView != null) {
                    onClickTrailerView.onClick(trailer);
                }
            }
        });
    }

    public interface onClickTrailerView {
        void onClick(Trailer trailer);
    }

    @Override
    public int getItemCount() {
        return trailerList.size();
    }

    static class ViewHolderTrailer extends RecyclerView.ViewHolder {

        private final TextView textViewTrailer;
        private final ImageView imageViewTrailer;

        public ViewHolderTrailer(@NonNull View itemView) {
            super(itemView);
            textViewTrailer = itemView.findViewById(R.id.textViewTrailer);
            imageViewTrailer = itemView.findViewById(R.id.imageViewTrailer);
        }
    }

    public void setTrailerList(List<Trailer> trailerList) {
        this.trailerList = trailerList;
        notifyDataSetChanged();
    }
}
