package com.prpr.androidpprog2.entregable.controller.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prpr.androidpprog2.entregable.R;
import com.prpr.androidpprog2.entregable.controller.adapters.Add2PlaylistListAdapter;
import com.prpr.androidpprog2.entregable.controller.callbacks.Add2PlaylistListCallback;
import com.prpr.androidpprog2.entregable.controller.dialogs.ErrorDialog;
import com.prpr.androidpprog2.entregable.controller.dialogs.StateDialog;
import com.prpr.androidpprog2.entregable.controller.restapi.callback.PlaylistCallback;
import com.prpr.androidpprog2.entregable.controller.restapi.manager.PlaylistManager;
import com.prpr.androidpprog2.entregable.model.Playlist;
import com.prpr.androidpprog2.entregable.model.Track;
import com.prpr.androidpprog2.entregable.model.UserToken;
import com.prpr.androidpprog2.entregable.utils.Constants;
import com.prpr.androidpprog2.entregable.utils.Session;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Add2PlaylistActivity extends AppCompatActivity implements PlaylistCallback, Add2PlaylistListCallback {

    private static final String TAG = "Add2PlaylistActivity";
    private static final String PLAY_VIEW = "PlayIcon";
    private static final String STOP_VIEW = "StopIcon";


    private TextView trackTitle;
    private TextView trackAuthor;
    private ImageView trackImg;

    private Button cancel;
    private Track trck;
    private Handler mHandler;
    private Runnable mRunnable;

    private RecyclerView mRecyclerView;



    private ArrayList<Playlist> playlists;
    private int currentPlaylist = 0;
    private PlaylistManager pManager;
    private Add2PlaylistListAdapter adapter;
    private Playlist currentList;


    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_song_2_playlist);
        trck = (Track) getIntent().getSerializableExtra("Trck");
        currentList = (Playlist) getIntent().getSerializableExtra("Playlst");
        initViews();
        UserToken userToken = Session.getInstance(this).getUserToken();
        String usertkn = userToken.getIdToken();
        pManager = new PlaylistManager(this);
        pManager.getAllMyPlaylists(this);
    }


    private void initViews() {

        mRecyclerView = (RecyclerView) findViewById(R.id.llistatDplaylists);
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        adapter = new Add2PlaylistListAdapter(this,  null, null, null);
        mRecyclerView.setLayoutManager(manager);
        adapter.setPlaylistCallback(this);
        mRecyclerView.setAdapter(adapter);

        mHandler = new Handler();

        trackTitle = findViewById(R.id.track_title);
        trackAuthor = findViewById(R.id.track_author);
        trackImg = findViewById(R.id.track_img);

        trackTitle.setText(trck.getName());
        trackAuthor.setText(trck.getUserLogin());
        if(trck.getThumbnail()!=null){
            Picasso.get().load(trck.getThumbnail()).into(trackImg);
        }else{
            Picasso.get().load("https://community.spotify.com/t5/image/serverpage/image-id/25294i2836BD1C1A31BDF2/image-size/original?v=mpbl-1&px=-1").into(trackImg);
        }


        cancel = findViewById(R.id.cancelButton);
        cancel.setEnabled(true);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PlaylistActivity.class);
                intent.putExtra("Playlst", currentList);
                startActivityForResult(intent, Constants.NETWORK.LOGIN_OK);
            }
        });

    }


    @Override
    public void onPlaylistCreated(Playlist playlist) {

    }

    @Override
    public void onPlaylistFailure(Throwable throwable) {

    }

    @Override
    public void onPlaylistRecieved(List<Playlist> playlists) {
        this.playlists = (ArrayList) playlists;
        Add2PlaylistListAdapter p = new Add2PlaylistListAdapter(this, (ArrayList) playlists, trck, currentList);
        mRecyclerView.setAdapter(p);
    }

    @Override
    public void onNoPlaylists(Throwable throwable) {
        Toast.makeText(this, "No tens playlists", Toast.LENGTH_LONG);
    }

    @Override
    public void onPlaylistSelected(Playlist P) {

    }

    @Override
    public void onTrackAdded(Playlist body) {

    }

    @Override
    public void onTrackAddFailure(Throwable throwable) {

    }

    @Override
    public void onAllPlaylistRecieved(List<Playlist> body) {

    }

    @Override
    public void onAllNoPlaylists(Throwable throwable) {

    }

    @Override
    public void onAllPlaylistFailure(Throwable throwable) {

    }


    @Override
    public void onPlaylistAddSelected(int position, ArrayList<Playlist> playlist, Track track) {

    }


}
