package com.example.score.util;

import com.example.score.fragment.ArticleFragment;

import java.util.ArrayList;
import java.util.List;

public class Global {
    /**
     * 文章相关信息
     */
    public static List<String> A_image = new ArrayList<String>();
    public static List<String> A_title = new ArrayList<String>();
    public static List<String> A_subhead = new ArrayList<String>();
    public static List<String> A_url = new ArrayList<String>();
    public static List<Integer> A_type = new ArrayList<Integer>();
    public static int AF_CNT = 0; //文章Fragment访问次数

    /**
     * 配乐详情
     */
    public static List<Integer> F_tvId = new ArrayList<Integer>();//影视剧Id
    public static List<String> F_image = new ArrayList<String>();//图片id
    public static List<String> F_name = new ArrayList<String>();//电影名
    public static List<String> F_composer = new ArrayList<String>();//作曲家
    public static List<String> F_intro = new ArrayList<String>();//简介
    public static List<Integer> F_hot = new ArrayList<Integer>();//热度
    public static int FILM_CNT = 0; //访问次数

    /**
     * 数据库
     */
    public static DBManager DB = new DBManager("score.db");
}
