package com.example.franciscofranco.mediaplayer;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class MusicService extends Service {
    private MediaPlayer mp;

    public MusicService() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        setNextSong(intent.getStringExtra(MainActivity.NEXT_SONG));

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
        mp.release();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void performOnEnd() {
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(MainActivity.ResponseReceiver.ACTION_RESP);
        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
        sendBroadcast(broadcastIntent);
    }

    private void setNextSong(String nextSong) {
        switch(nextSong) {
            case "Adelante Simpre Voy":
                mp = MediaPlayer.create(getApplicationContext(), R.raw.adelante);
                break;
            case "La Joda":
                mp = MediaPlayer.create(getApplicationContext(), R.raw.la_joda);
                break;
            case "Yo Soy La Guaracha":
                mp = MediaPlayer.create(getApplicationContext(), R.raw.guaracha);
                break;
            case "Necio":
                mp = MediaPlayer.create(getApplicationContext(), R.raw.necio);
                break;
            case "Pal Bailador":
                mp = MediaPlayer.create(getApplicationContext(), R.raw.pal_bailador);
                break;
        }
    }
}