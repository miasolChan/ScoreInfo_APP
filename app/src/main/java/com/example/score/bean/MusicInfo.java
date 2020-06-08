package com.example.score.bean;

public class MusicInfo {
    private int tvId;//id号
    private String name;//音乐名

    public MusicInfo(int tvId, String name) {
        this.tvId = tvId;
        this.name = name;
    }

    public MusicInfo() {
    }
    public String getMusicHttp(){
        String http = "http://115.197.153.136:8080/music/"+name+".mp3";
        return http;
    }

    public int getTvId() {
        return tvId;
    }

    public String getName() {
        return name;
    }

    public void setTvId(int tvId) {
        this.tvId = tvId;
    }

    public void setName(String name) {
        this.name = name;
    }
}
