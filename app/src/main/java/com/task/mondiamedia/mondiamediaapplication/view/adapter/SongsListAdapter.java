package com.task.mondiamedia.mondiamediaapplication.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.task.mondiamedia.mondiamediaapplication.R;
import com.task.mondiamedia.mondiamediaapplication.network.DownloadImageTask;
import com.task.mondiamedia.mondiamediaapplication.network.modle.SongModel;

import java.util.List;

/**
 * Created by Summer on 5/17/2019.
 */
public class SongsListAdapter extends RecyclerView.Adapter<SongsListAdapter.SongsViewHolder> {
    List<SongModel> songModels;

    public SongsListAdapter(List<SongModel> songModels) {
        this.songModels = songModels;
    }

    @NonNull
    @Override
    public SongsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, viewGroup, false);
        return new SongsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongsViewHolder songsViewHolder, int i) {
        songsViewHolder.title.setText(songModels.get(i).getTitle());
        songsViewHolder.type.setText(songModels.get(i).getType());
        songsViewHolder.artist.setText(songModels.get(i).getArtistName());
        //default img
        songsViewHolder.imageView.setImageResource(android.R.drawable.alert_dark_frame);
        if (songsViewHolder.imageView != null) {
            new DownloadImageTask(songsViewHolder.imageView).execute(songModels.get(i).getSongImg());
        }
    }

    @Override
    public int getItemCount() {
        return songModels.size();
    }

    public static class SongsViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView title;
        private TextView type;
        private TextView artist;

        public SongsViewHolder(View v) {
            super(v);
            imageView = v.findViewById(R.id.img);
            title = v.findViewById(R.id.song_title);
            type = v.findViewById(R.id.song_type);
            artist = v.findViewById(R.id.song_artist);
        }
    }
}
