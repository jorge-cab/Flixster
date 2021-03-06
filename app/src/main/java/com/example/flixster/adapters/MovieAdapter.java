package com.example.flixster.adapters;

import android.content.Context;
import android.content.res.Configuration;

import android.text.TextUtils;

import android.util.Log;

import android.util.proto.ProtoOutputStream;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.target.Target;
import com.example.flixster.GlideApp;
import com.example.flixster.R;
import com.example.flixster.databinding.ActivityMainBinding;
import com.example.flixster.models.Movie;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {



    public interface OnClickListener {
        void onMovieClicked(int position);
    }

    Context context;
    List<Movie> movies;
    OnClickListener clickListener;

    public MovieAdapter(Context context, List<Movie> movies, OnClickListener clickListener) {
        this.clickListener = clickListener;
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the movie at the position
        Movie movie = movies.get(position);
        // Bind the movie data into the view holder
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvTitle.setMaxLines(1);
            tvTitle.setEllipsize(TextUtils.TruncateAt.END);
            tvOverview.setText(movie.getOverview());
            tvOverview.setMaxLines(5);
            tvOverview.setEllipsize(TextUtils.TruncateAt.END);
            String imageUrl;

            ivPoster.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onMovieClicked(getAdapterPosition());
                }
            });


            //image settings
            int radius = 30;
            int margin = 10;
            // if phone in landscape
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                // imageUrl = backdrop image
                imageUrl = movie.getBackdropPath();
                GlideApp
                        .with(context)
                        .load(imageUrl)
                        .placeholder(R.drawable.flicks_backdrop_placeholder)
                        .transform(new RoundedCornersTransformation(radius, margin))
                        .into(ivPoster);
            } else {
                // else imageUrl = poster image
                imageUrl = movie.getPosterPath();
                GlideApp
                        .with(context)
                        .load(imageUrl)
                        .placeholder(R.drawable.flicks_movie_placeholder)
                        .transform(new RoundedCornersTransformation(radius, margin))
                        .into(ivPoster);
            }




        }
    }
}
