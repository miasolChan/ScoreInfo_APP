package com.example.score.service;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ListView;

import com.example.score.bean.FilmInfo;
import com.example.score.bean.MusicInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.score.util.Global.DB;
import static com.example.score.util.Global.FILM_CNT;
import static com.example.score.util.Global.F_composer;
import static com.example.score.util.Global.F_hot;
import static com.example.score.util.Global.F_image;
import static com.example.score.util.Global.F_intro;
import static com.example.score.util.Global.F_name;
import static com.example.score.util.Global.F_tvId;

public class GetMusicService {

    private int[] tvId = {0,1,2,3,4,5};//影视剧Id
    private String[] imageId = {"tv_image_0.jpg","tv_image_1.jpg","tv_image_2.jpg","tv_image_3.jpg","tv_image_4.jpg","tv_image_5.jpg",};//图片id
    private String[] name = {"爱乐之城","星际穿越","神探夏洛克第一季","西部世界第一季","千与千寻","末代皇帝"};//电影名
    private String[] composer = {"Justin Hurwitz","Hans Zimmer","David Arnold/Michael Price","Ramin Djawadi","久石让","坂本龙一/David Byrne/苏聪",};//作曲家
    //private String[] intro = {"爱乐之城简介","星际穿越简介","神探夏洛克第一季简介","西部世界第一季简介","千与千寻简介","末代皇帝简介"};//简介
    private int[] hot = {1,2,6,5,7,3};


    public GetMusicService() {
        //第一次加载数据库赋值
        if(FILM_CNT == 0) {
            initGetFilmService();
            System.out.println("初始化电影信息成功");
            FILM_CNT++;
        }

    }
    /**
     * 数据库调取赋值
     */
    public void initGetFilmService(){
        DB.openDB();
        SQLiteDatabase thisDB = DB.getmDB();
        List<FilmInfo> filmInfos = new ArrayList<FilmInfo>();
        String sql = "select * from filmInfo";
        Cursor cuser = thisDB.rawQuery(sql,null);
        try {
            while (cuser.moveToNext()){
                FilmInfo item = new FilmInfo();
                int tvId = cuser.getInt(cuser.getColumnIndex("tvId"));
                int hot = cuser.getInt(cuser.getColumnIndex("hot"));
                String name = cuser.getString(cuser.getColumnIndex("name"));
                String compser = cuser.getString(cuser.getColumnIndex("composer"));
                String  intro = cuser.getString(cuser.getColumnIndex("intro"));
                String imageId = cuser.getString(cuser.getColumnIndex("tv_image"));
                F_tvId.add(tvId);
                F_hot.add(hot);
                F_name.add(name);
                F_composer.add(compser);
                F_intro.add(intro);
                F_image.add(imageId);
            }
            cuser.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    /**、
     * 初始化添加
     * @return
     */

    public List<FilmInfo> setFilmList(){
        System.out.println("加载音乐");
        List<FilmInfo> filmInfos = new ArrayList<FilmInfo>();
        for (int i = 0; i < F_name.size(); i++) {
            FilmInfo item = new FilmInfo();
            item.setTvId(F_tvId.get(i));
            item.setName(F_name.get(i));
            item.setComposer(F_composer.get(i));
            item.setHot(F_hot.get(i));
            item.setIntro(F_intro.get(i));
            item.setImageId(F_image.get(i));
            filmInfos.add(item);
        }
        Collections.sort(filmInfos);
        return filmInfos;
    }

    /**
     * 获取配乐列表
     */
    public List<MusicInfo> getMusicList(int index){
        List<MusicInfo> musicInfos = new ArrayList<MusicInfo>();
        DB.openDB();
        SQLiteDatabase thisDB = DB.getmDB();
        //SELECT * FROM Persons WHERE City='Beijing'
        String sql = "select * from musicInfo where tvId="+String.valueOf(index);
        Cursor cuser = thisDB.rawQuery(sql,null);
        try {
            while (cuser.moveToNext()){
                //
                MusicInfo music = new MusicInfo();
                String name = cuser.getString(cuser.getColumnIndex("music_name"));
                music.setName(name);
                musicInfos.add(music);
            }
            cuser.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return musicInfos;
    }
}
