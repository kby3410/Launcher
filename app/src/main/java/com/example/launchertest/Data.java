package com.example.launchertest;

import android.graphics.drawable.Drawable;

public class Data {
    private String title;
    private String pack;
    private Drawable icon;

    public Data(String title, String pack, Drawable icon) {
        this.title = title;
        this.pack = pack;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }




}
