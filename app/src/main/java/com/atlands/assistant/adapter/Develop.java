package com.atlands.assistant.adapter;

public class Develop {
    private String title;
    private String recommend;
    private int icon;
    private String url;

    public Develop(String title, String recommend, int icon, String url) {
        this.title = title;
        this.recommend = recommend;
        this.icon = icon;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getRecommend() {
        return recommend;
    }

    public int getIcon() {
        return icon;
    }

    public String getUrl() {
        return url;
    }
}
