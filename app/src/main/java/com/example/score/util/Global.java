package com.example.score.util;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.score.adapter.ScoreRecyclerViewAdapter;
import com.example.score.bean.MusicInfo;
import com.example.score.fragment.ArticleFragment;
import com.example.score.service.MusicService;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
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

    /**
     * 正在播放的音乐信息
     */
    public static SeekBar seekBar;//
    public static TextView musicTime;//时长
    public static ImageButton btnPlayOrPause;//停止播放按钮
    public static ImageButton previous;//上一首
    public static ImageButton next;//下一首
    public static ImageView barImg;//图片
    public static TextView name;//音乐名字
    public static MusicInfo song;//正在播放的音乐
    public static String imgHttp;//图片网址
    public static ImageButton musicMenu;
    public static int MUSIC_INDEX = -1;//点击播放的音乐在列表的位置
    //public static Boolean IF_SONG_CHANGE = false;
    public static Boolean IF_First = true;

    /**
     * 正在播放的列表，双向队列
     */
    public static ArrayList<MusicInfo> musicQue = new ArrayList<MusicInfo>();

    /**
     * 测试用本地ip
     */
    //public static String localIp = "http://10.50.193.95:8080/";
    public static String localIp = "http://qatlb8fbq.bkt.clouddn.com/";

    /**
     * 接收服务
     * @param
     */
    public static MusicService musicService;

    /**
     * 听歌次数
     */
    public static int mCount = 0;

}
