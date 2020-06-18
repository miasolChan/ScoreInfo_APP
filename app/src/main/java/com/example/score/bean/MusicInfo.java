package com.example.score.bean;

import com.example.score.util.Global;

public class MusicInfo {
    private int tvId;//id号
    private String name;//音乐名
    private String imageHttp;//
    private String fileId;//文件名

    public MusicInfo(int tvId, String name,String fileId) {
        this.tvId = tvId;
        this.name = name;
        this.fileId = fileId;
    }

    public MusicInfo() {
    }
    public String getMusicHttp(){
        String http = Global.localIp+ "music/"+fileId+".mp3";
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

    public void setImageHttp() {
        this.imageHttp = Global.localIp+"image/film_image/tv_image_"+tvId+".jpg";
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getImageHttp() {
        return imageHttp;
    }
}
