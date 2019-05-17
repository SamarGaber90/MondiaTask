package com.task.mondiamedia.mondiamediaapplication.view.presenter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.task.mondiamedia.mondiamediaapplication.R;
import com.task.mondiamedia.mondiamediaapplication.StringUtil;
import com.task.mondiamedia.mondiamediaapplication.model.SongModel;
import com.task.mondiamedia.mondiamediaapplication.network.DownloadImageTask;

/**
 * Created by Summer on 5/17/2019.
 */
public class SongDetailsFragment extends Fragment {
    private static final String SONG_DETAILS_KEY = "songs_details";

    private TextView title;
    private TextView type;
    private TextView artistName;
    private ImageView songImg;
    private TextView publishingDate;
    private TextView trackNumber;
    private TextView duration;

    private DownloadImageTask downloadImageTask;

    public static SongDetailsFragment getDetailsFragmentInstance(SongModel songModel) {
        SongDetailsFragment detailsFragment = new SongDetailsFragment();

        Bundle argument = new Bundle();
        argument.putSerializable(SONG_DETAILS_KEY, songModel);

        detailsFragment.setArguments(argument);

        return detailsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_song_details, container, false);
        initView(rootView);

        SongModel songModel = (SongModel) getArguments().getSerializable(SONG_DETAILS_KEY);
        bindViews(songModel);

        return rootView;
    }

    private void initView(View rootView) {
        title = rootView.findViewById(R.id.song_title);
        type = rootView.findViewById(R.id.song_type);
        artistName = rootView.findViewById(R.id.song_artist);
        songImg = rootView.findViewById(R.id.song_img);
        publishingDate = rootView.findViewById(R.id.publish_date);
        trackNumber = rootView.findViewById(R.id.track_number);
        duration = rootView.findViewById(R.id.duration);
    }

    private void bindViews(SongModel songModel) {
        title.setText(songModel.getTitle());
        type.setText(StringUtil.getFullText(songModel.getType(), getString(R.string.type)));
        artistName.setText(StringUtil.getFullText(songModel.getArtistName(), getString(R.string.artistName)));
        publishingDate.setText(StringUtil.getFullText(songModel.getPublishingDate(), getString(R.string.publishingDate)));
        trackNumber.setText(StringUtil.getFullText(songModel.getTrackNumber(), getString(R.string.trackNumber)));
        duration.setText(StringUtil.getFullText(songModel.getDuration(), getString(R.string.duration)));

        downloadImageTask = new DownloadImageTask(songImg);
        downloadImageTask.execute(songModel.getSongImg());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        downloadImageTask.cancel(true);
    }
}

