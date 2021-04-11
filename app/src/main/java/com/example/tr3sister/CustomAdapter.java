package com.example.tr3sister;

import android.content.Context;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private Context context;
    private List<MovieModel> filmlist;


    public CustomAdapter(Context context,List<MovieModel> filmlist){
        this.context = context;
        this.filmlist = filmlist;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater = LayoutInflater.from(context);
        v = inflater.inflate(R.layout.cardfilm,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(filmlist.get(position).getTitle());
        holder.voteaverage.setText(filmlist.get(position).getRating());

        //image
        //https://image.tmdb.org/t/p/w500
        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500"+filmlist.get(position).img)
                .into(holder.path);

        holder.path.setOnClickListener(view -> {
            Intent intent = new Intent(context,detail.class);
            intent.putExtra("img",filmlist.get(position).getImg());
            intent.putExtra("title",filmlist.get(position).getTitle());
            intent.putExtra("rating",filmlist.get(position).getRating());
            intent.putExtra("release_date",filmlist.get(position).getRelease_date());
            intent.putExtra("overview",filmlist.get(position).getOverview());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return filmlist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView voteaverage;
        ImageButton path;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            voteaverage = itemView.findViewById(R.id.rating);
            path = itemView.findViewById(R.id.poster);
        }
    }
}
