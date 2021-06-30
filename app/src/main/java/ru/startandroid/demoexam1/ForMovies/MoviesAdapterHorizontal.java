package ru.startandroid.demoexam1.ForMovies;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ru.startandroid.demoexam1.Activities.MoviesCard;
import ru.startandroid.demoexam1.R;


public class MoviesAdapterHorizontal extends RecyclerView.Adapter<MoviesAdapterHorizontal.ViewHolder> {

    private final static String PHOTO_URL = "http://cinema.areas.su/up/images/";
    private List<Movies> mMovies;
    private Context mContext;
    public MoviesAdapterHorizontal(List<Movies> movies) {
        this.mMovies = movies;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_movies_horizontal, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movies movie = mMovies.get(position);
        Picasso.with(mContext)
                .load(PHOTO_URL + movie.getPoster())
                .resize(500,700)
                .into(holder.posterImageView);

        holder.posterImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MoviesCard.class).putExtra("movieId", movie.getMovieId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mMovies == null) {
            return 0;
        }
        return mMovies.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView posterImageView;
        ViewHolder(View itemView) {
            super(itemView);
            posterImageView = (ImageView) itemView.findViewById(R.id.ivPoster);
        }
    }
}
