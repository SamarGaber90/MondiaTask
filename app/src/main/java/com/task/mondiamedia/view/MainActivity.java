package com.task.mondiamedia.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.task.mondiamedia.R;
import com.task.mondiamedia.model.SongModel;
import com.task.mondiamedia.view.adapter.SongsListListener;
import com.task.mondiamedia.view.presenter.SongDetailsFragment;

public class MainActivity extends AppCompatActivity implements SongsListListener {
    private SongsListFragment songsListFragment;
    private MenuItem searchMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        songsListFragment = new SongsListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.container, songsListFragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        searchMenuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setQueryHint("Search");
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                songsListFragment.filterList(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onSongsItemClicked(SongModel songModel) {
        getSupportFragmentManager().beginTransaction().add(R.id.container, SongDetailsFragment.getDetailsFragmentInstance(songModel)).addToBackStack("details").commit();
    }

    @Override
    public void setSearchStatus(boolean show) {
        if (searchMenuItem != null)
            searchMenuItem.setVisible(show);
    }
}
