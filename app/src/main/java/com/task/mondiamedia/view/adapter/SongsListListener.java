package com.task.mondiamedia.view.adapter;

import com.task.mondiamedia.model.SongModel;

/**
 * Created by Summer on 5/17/2019.
 */
public interface SongsListListener {
    void onSongsItemClicked(SongModel songModel);

    void setSearchStatus(boolean show);

}
