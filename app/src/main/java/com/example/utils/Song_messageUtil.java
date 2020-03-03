package com.example.utils;

import java.io.Serializable;

public class Song_messageUtil implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id = -1;
    private int status = -1;// -1 未查询封面   0 查询无封面   1 有封面
    public static final int PRE_FIND = -1;
    public static final int NO = 0;
    public static final int YES = 1;
    private String title, singer, album, path, time;
    private boolean playing = false;

    public Song_messageUtil(long id, int status,String title, String singer, String album, String path, String time) {
        this.id = id;
        this.status  = status;
        this.title = title;
        this.singer = singer;
        this.album = album;
        this.path = path;
        this.time = time;
    }

    public Song_messageUtil() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }
}
