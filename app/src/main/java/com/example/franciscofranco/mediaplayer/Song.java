package com.example.franciscofranco.mediaplayer;

public class Song {

    private String title;
    private String url;
    private String singer;

    public Song(String singer, String title, String url) {
        this.singer = singer;
        this.title = title;
        this.url = url;
    }

    public String getSinger() {
        return singer;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}
