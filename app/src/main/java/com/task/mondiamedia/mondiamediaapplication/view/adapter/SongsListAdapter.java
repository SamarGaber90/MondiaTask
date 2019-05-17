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
import com.task.mondiamedia.mondiamediaapplication.model.SongModel;

import java.util.List;

/**
 * Created by Summer on 5/17/2019.
 */
public class SongsListAdapter extends RecyclerView.Adapter<SongsListAdapter.SongsViewHolder> {
    private List<SongModel> songModels;
    private SongsListListener songsListListener;

    public SongsListAdapter(List<SongModel> songModels, SongsListListener songsListListener) {
        this.songModels = songModels;
        this.songsListListener = songsListListener;
    }

    @NonNull
    @Override
    public SongsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, viewGroup, false);
        return new SongsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongsViewHolder songsViewHolder, final int i) {
        songsViewHolder.title.setText(songModels.get(i).getTitle());
        songsViewHolder.type.setText(songModels.get(i).getType());
        songsViewHolder.artist.setText(songModels.get(i).getArtistName());
        //default img
        songsViewHolder.imageView.setImageResource(android.R.drawable.alert_dark_frame);
        if (songsViewHolder.imageView != null) {
            new DownloadImageTask(songsViewHolder.imageView).execute(songModels.get(i).getSongImg());
        }
        songsViewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                songsListListener.onSongsItemClicked(songModels.get(i));
            }
        });
    }

    @Override
    public int getItemCount() {
        return songModels.size();
    }

    public static class SongsViewHolder extends RecyclerView.ViewHolder {
        private ViewGroup container;
        private ImageView imageView;
        private TextView title;
        private TextView type;
        private TextView artist;

        public SongsViewHolder(View v) {
            super(v);
            container = v.findViewById(R.id.container);
            imageView = v.findViewById(R.id.img);
            title = v.findViewById(R.id.song_title);
            type = v.findViewById(R.id.song_type);
            artist = v.findViewById(R.id.song_artist);
        }
    }
}
