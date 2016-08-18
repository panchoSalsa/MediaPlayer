package com.example.franciscofranco.mediaplayer;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {

    private ResponseReceiver responseReceiver;
    public static final String NEXT_SONG = "nextSong";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IntentFilter filter = new IntentFilter(ResponseReceiver.ACTION_RESP);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        responseReceiver = new ResponseReceiver();
        registerReceiver(responseReceiver, filter);
    }

    public void play(View view) {
        Log.d("FRANCO_DEBUG", "playing...");
        playSong("tequila");
    }

    public void stop(View view) {
        Log.d("FRANCO_DEBUG", "stop...");
        stopService();
    }

    public void rewind(View view) {
        Log.d("FRANCO_DEBUG", "rewind...");
        stopService();
        playSong("tequila");


    }

    public void forward(View view) {
        Log.d("FRANCO_DEBUG", "forward...");
        stopService();
        playSong("la_joda");
    }

    public class ResponseReceiver extends BroadcastReceiver {

        public static final String  ACTION_RESP =
                "com.example.franciscofranco.mediaplayer.action.MESSAGE_PROCESSED";
        @Override
        public void onReceive(Context context, Intent intent) {
            playNextSong();
        }
    }

    private void playNextSong() {
        Log.d("FRANCO_DEBUG", "playing next song...");
        Intent intent = new Intent(this, MusicService.class);
        intent.putExtra(NEXT_SONG, "la_joda");
        startService(intent);
    }

    private void stopService() {
        stopService(new Intent(this, MusicService.class));
    }

    private void playSong(String song) {
        Intent intent = new Intent(this, MusicService.class);
        intent.putExtra(NEXT_SONG, song);
        startService(intent);
    }
}
