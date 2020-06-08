package com.example.score.bean;


import com.example.score.util.NetUtil;

/*
* 文章信息
* */
public class ArticleInfo {
    private int type;//类型，1专访，2剖析
    private String imageId;//图片id
    private String title;//标题
    private String  subhead;//副标题
    private String url;//文章网址


    public ArticleInfo(int type, String imageId, String title, String subhead, String url) {
        this.type = type;
        this.imageId = imageId;
        this.title = title;
        this.subhead = subhead;
        this.url = url;
    }

    public ArticleInfo() {

    }
    public String getImageHttp() {
        //String http = "http://qay05o8n9.bkt.clouddn.com/" + imageId;
        String ip = NetUtil.getIpAddressString();
        String http = "http://115.197.153.136:8080/image/article_image/"+imageId;
        return http;
    }

    public int getType() {
        return type;
    }

    public String getImageId() {
        return imageId;
    }

    public String getTitle() {
        return title;
    }

    public String getSubhead() {
        return subhead;
    }

    public String getUrl() {
        return url;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubhead(String subhead) {
        this.subhead = subhead;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
