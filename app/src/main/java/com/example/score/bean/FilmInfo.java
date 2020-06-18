package com.example.score.bean;

import com.example.score.util.Global;
import com.example.score.util.NetUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FilmInfo implements Serializable,Comparable<FilmInfo> {
    private int tvId;//影视剧Id
    private String imageId;//图片id
    private String name;//电影名
    private String composer;//作曲家
    private String intro;//简介
    private List<MusicInfo> musicList;//配乐合集
    private int hot;//点击热度

    public FilmInfo() {
    }

    public String getImageHttp() {
        //String http = "http://qay05o8n9.bkt.clouddn.com/" + imageId;
        //String ip = NetUtil.getIpAddressString();
        String http = Global.localIp+ "image/film_image/"+imageId;
        return http;
    }
    public List<String> getMusicUrlList(){
        List<String> musicUrlList = new ArrayList<String>();
        for (int i = 0; i < musicList.size(); i++) {
            String url = musicList.get(i).getMusicHttp();
            musicUrlList.add(url);
        }
        return musicUrlList;
    }

    public void setHot(int hot) {
        this.hot = hot;
    }



    public int getTvId() {
        return tvId;
    }

    public String getImageId() {
        return imageId;
    }

    public List<MusicInfo> getMusicList() {
        return musicList;
    }

    public String getName() {
        return name;
    }

    public String getComposer() {
        return composer;
    }

    public String getIntro() {
        return intro;
    }

    public void setTvId(int tvId) {
        this.tvId = tvId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public void setMusicList(List<MusicInfo> musicList) {
        for (int i = 0; i < musicList.size(); i++) {

        }
        this.musicList = musicList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setComposer(String composer) {
        this.composer = composer;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public int getHot() {
        return hot;
    }

    @Override
    public int compareTo(FilmInfo o) {
        // TODO Auto-generated method stub
        // 先按热点数排序,降序
        if (this.hot > o.getHot()) {
            return (o.getHot()-this.hot);
        }
        if (this.hot < o.getHot()) {
            return (o.getHot()-this.hot);
        }

        // 按Id排序
        if (this.tvId > o.getTvId()) {
            return -1;
        }
        if (this.tvId < o.getTvId()) {
            return 1;
        }
        return 0;
    }
}
