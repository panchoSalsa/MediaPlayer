package com.example.franciscofranco.mediaplayer;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MainActivity extends Activity {

    private ResponseReceiver responseReceiver;
    public static final String NEXT_SONG = "nextSong";
    private ArrayList<Song> list;
    int currentSong;
    private ImageView imageView;
    private ImageView play;
    private ImageView stop;
    private ImageView rewind;
    private ImageView forward;
    private TextView artist;
    private TextView title;
    boolean playing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null)
            currentSong = 0;

        IntentFilter filter = new IntentFilter(ResponseReceiver.ACTION_RESP);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        responseReceiver = new ResponseReceiver();
        registerReceiver(responseReceiver, filter);

        list = new ArrayList<Song>();

        initializeList();

        imageView = (ImageView) findViewById(R.id.imageView);
        play = (ImageView) findViewById(R.id.play);
        stop = (ImageView) findViewById(R.id.stop);
        rewind = (ImageView) findViewById(R.id.rewind);
        forward = (ImageView) findViewById(R.id.forward);
        artist = (TextView) findViewById(R.id.artist);
        title = (TextView) findViewById(R.id.title);

        initializeControls();

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        playing = savedInstanceState.getBoolean("playing");
        currentSong = savedInstanceState.getInt("currentSong");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean("playing", playing);
        outState.putInt("currentSong", currentSong);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (playing) {
            setImage();
            setHeader();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(responseReceiver);
    }

    private void initializeList() {
        list.add(new Song("Celia Cruz", "Yo Soy La Guaracha",
                "https://celiacruzfoundation1.files.wordpress.com/2007/05/34200_10150221218200524_72224900523_13430708_5340389_n.jpg"));
        list.add(new Song("Sonora Carruseles", "La Joda",
                "http://images.coveralia.com/audio/s/Sonora_Carruseles-Con_Mas_Salsa_-Frontal.jpg"));
        list.add(new Song("Romeo Santos", "Necio",
                "https://i.ytimg.com/vi/W8r-eIhp4j0/maxresdefault.jpg"));
        list.add(new Song("Joe Arroyo", "Pal Bailador",
                "http://musicvideosdeconstructed.com/wp-content/uploads/2015/06/Joe-Joven.jpg"));
        list.add(new Song("Ray Barretto", "Adelante Simpre Voy",
                "http://audiopreservationfund.org/graphics/archives/8/Front%20Covers/Big/ARC_8_00015.jpg"));
    }

    public void play(View view) {
        playing = true;

        playSong(list.get(currentSong).getTitle());
    }

    public void stop(View view) {
        playing = false;

        stopService();
    }

    public void rewind(View view) {
        stopService();

        if (currentSong != 0) {
            --currentSong;
        }

        playSong(list.get(currentSong).getTitle());
    }

    public void forward(View view) {
        stopService();

        if (currentSong == (list.size() - 1) ) {
            currentSong = 0;
        } else {
            ++currentSong;
        }

        playSong(list.get(currentSong).getTitle());
    }

    public class ResponseReceiver extends BroadcastReceiver {

        public static final String  ACTION_RESP =
                "com.example.franciscofranco.mediaplayer.action.MESSAGE_PROCESSED";
        @Override
        public void onReceive(Context context, Intent intent) {
            playSong("la_joda");
        }
    }

    private void stopService() {
        stopService(new Intent(this, MusicService.class));
    }

    private void playSong(String song) {

        setHeader();
        setImage();

        Intent intent = new Intent(this, MusicService.class);
        intent.putExtra(NEXT_SONG, song);
        startService(intent);
    }

    private void setHeader() {
        artist.setText(list.get(currentSong).getSinger());
        title.setText(list.get(currentSong).getTitle());
    }

    private void setImage() {
        Picasso.with(this)
                .load(list.get(currentSong).getUrl())
                .into(imageView);
    }

    private void initializeControls() {
        Picasso.with(this)
                .load(R.mipmap.play)
                .into(play);

        Picasso.with(this)
                .load(R.mipmap.stop)
                .into(stop);

        Picasso.with(this)
                .load(R.mipmap.rewind)
                .into(rewind);

        Picasso.with(this)
                .load(R.mipmap.forward)
                .into(forward);
    }
}