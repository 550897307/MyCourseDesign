package com.example.utils;

import android.widget.Button;

public final class Main_list_displayUtil {
    private String name;
    private String song1_title, song1_singer;
    private String song2_title, song2_singer;
    private Button button;


    public Main_list_displayUtil(String name, String song1_title, String song1_singer, String song2_title, String song2_singer, Button button) {
        this.name = name;
        this.song1_title = song1_title;
        this.song1_singer = song1_singer;
        this.song2_title = song2_title;
        this.song2_singer = song2_singer;
        this.button = button;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSong1_title() {
        return song1_title;
    }

    public void setSong1_title(String song1_title) {
        this.song1_title = song1_title;
    }

    public String getSong1_singer() {
        return song1_singer;
    }

    public void setSong1_singer(String song1_singer) {
        this.song1_singer = song1_singer;
    }

    public String getSong2_title() {
        return song2_title;
    }

    public void setSong2_title(String song2_title) {
        this.song2_title = song2_title;
    }

    public String getSong2_singer() {
        return song2_singer;
    }

    public void setSong2_singer(String song2_singer) {
        this.song2_singer = song2_singer;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }
}
