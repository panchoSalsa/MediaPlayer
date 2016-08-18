package com.example.franciscofranco.mediaplayer;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class MusicService extends Service {
    private MediaPlayer mp;

    public MusicService() {
        Log.d("FRANCO_DEBUG", "constructor");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("FRANCO_DEBUG", "started service");

        setNextSong(intent.getStringExtra(MainActivity.NEXT_SONG));

        Log.d("FRANCO_DEBUG", "ok");
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                performOnEnd();
            }
        });

        mp.start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("FRANCO_DEBUG", "onDestroy");
        mp.stop();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void performOnEnd() {
        Log.d("FRANCO_DEBUG", "song over...");

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(MainActivity.ResponseReceiver.ACTION_RESP);
        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
        sendBroadcast(broadcastIntent);
    }

    private void setNextSong(String nextSong) {
        switch(nextSong) {
            case "tequila":

                mp = MediaPlayer.create(getApplicationContext(), R.raw.tequila);
                break;
            case "la_joda":
                mp = MediaPlayer.create(getApplicationContext(), R.raw.la_joda);
                break;
        }
    }
}