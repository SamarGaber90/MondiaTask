package com.task.mondiamedia.mondiamediaapplication.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.task.mondiamedia.mondiamediaapplication.R;
import com.task.mondiamedia.mondiamediaapplication.view.adapter.SongsListListener;
import com.task.mondiamedia.mondiamediaapplication.view.presenter.SongsPresenter;
import com.task.mondiamedia.mondiamediaapplication.model.SongModel;
import com.task.mondiamedia.mondiamediaapplication.view.adapter.SongsListAdapter;

import java.util.List;

/**
 * Created by Summer on 5/16/2019.
 */
public class SongsListFragment extends Fragment implements ApiContract.SongsView {
    private SongsPresenter songsPresenter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private static final String TMP_SEARCH_KEY = "all";
    private TextView hintTv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_songs_list, container, false);
        initView(rootView);

        songsPresenter = new SongsPresenter(this);
        startFechingList(TMP_SEARCH_KEY);
        return rootView;
    }

    private void startFechingList(String searchKey) {
        songsPresenter.initialize();
        songsPresenter.fetch(searchKey);
    }

    private void initView(View rootView) {
        recyclerView = rootView.findViewById(R.id.songs_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        hintTv = rootView.findViewById(R.id.hint);
        progressBar = rootView.findViewById(R.id.loading);
    }

    @Override
    public void showSongsList(List<SongModel> songModels) {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setAdapter(new SongsListAdapter(songModels, (SongsListListener) getActivity()));
    }

    @Override
    public void showError(String errorMsg) {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        hintTv.setVisibility(View.VISIBLE);

        hintTv.setText(errorMsg);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        songsPresenter.stop();
    }

    public void filterList(String searchKey) {
        if (TextUtils.isEmpty(searchKey)) {
            searchKey = TMP_SEARCH_KEY;
        }
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        startFechingList(searchKey);
    }
}
