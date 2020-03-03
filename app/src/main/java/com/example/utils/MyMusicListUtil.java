package com.example.utils;

import android.graphics.Bitmap;
import android.widget.ImageButton;

import java.io.Serializable;

public class MyMusicListUtil {
    private boolean status = false;
    private Bitmap bitmap;
    private Song_messageUtil song_messageUtil;
//    private ImageButton song_more;

    public MyMusicListUtil(Bitmap bitmap, String title, String singer, String album, String path, String time) {
        this.bitmap = bitmap;

        song_messageUtil = new Song_messageUtil();
        song_messageUtil.setTitle(title);
        song_messageUtil.setSinger(singer);
        song_messageUtil.setAlbum(album);
        song_messageUtil.setPath(path);
        song_messageUtil.setTime(time);
    }

    public MyMusicListUtil(Bitmap bitmap, Song_messageUtil song_messageUtil) {
        this.bitmap = bitmap;
        this.song_messageUtil = song_messageUtil;
    }


    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setDrawable(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getTitle() {
        return song_messageUtil.getTitle();
    }

    public void setTitle(String title) {
        song_messageUtil.setTitle(title);
    }

    public String getSinger() {
        return song_messageUtil.getSinger();
    }

    public void setSinger(String singer) {
        song_messageUtil.setSinger(singer);
    }

//    public ImageButton getSong_more() {
//        return song_more;
//    }
//
//    public void setSong_more(ImageButton song_more) {
//        this.song_more = song_more;
//    }

    public String getAlbum() {
        return song_messageUtil.getAlbum();
    }

    public void setAlbum(String album) {
        song_messageUtil.setAlbum(album);
    }

    public String getPath() {
        return song_messageUtil.getPath();
    }

    public void setPath(String path) {
        song_messageUtil.setPath(path);
    }

    public String getTime() {
        return song_messageUtil.getTime();
    }

    public void setTime(String time) {
        song_messageUtil.setTime(time);
    }
}
