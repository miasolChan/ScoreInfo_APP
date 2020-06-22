package com.example.score.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.score.R;
import com.example.score.bean.MusicInfo;
import com.example.score.listener.MenuOnclickListener;
import com.example.score.service.MusicService;
import com.example.score.util.Global;
import com.example.score.util.PicCacheUtil;

import java.text.SimpleDateFormat;

public class BaseActivity extends AppCompatActivity implements View.OnClickListener{
    /**
     * windowManager对象
     */
    private WindowManager windowManager;
    /**
     * 根视野
     */
    private FrameLayout mContentContainer;
    /**
     * 浮动视野
     */
    private View mFloatView;

    Global global;

    private  ServiceConnection sc = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            global.musicService = ((MusicService.MyBinder)service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            global.musicService = null;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup mDecorView = (ViewGroup) getWindow().getDecorView();
        mContentContainer = (FrameLayout) ((ViewGroup) mDecorView.getChildAt(0)).getChildAt(1);
        mFloatView = LayoutInflater.from(getBaseContext()).inflate(R.layout.activity_music_bottom, null);

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.BOTTOM;
        mContentContainer.addView(mFloatView,layoutParams);
    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    /***
     * 设置前进Activity时候的无动画切换
     * @param intent
     */
    @Override
    public void startActivity(Intent intent){
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);//设置切换没有动画，用来实现活动之间的无缝切换
        super.startActivity(intent);
    }

    /**
     *  在这里设置按下返回键，或者返回button的时候无动画
     */
    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(0, 0);//设置返回没有动画
    }

    /**
     * 为song赋值
     */
    public void setSong(MusicInfo song){
        global.song = song;
    }
    /**
     * 为image赋值
     */
    public void setImage(String imgHttp){
        global.imgHttp = imgHttp;
    }
    /**
     * 初始化Bar
     */
    public void initBar(){
        global.name = (TextView)findViewById(R.id.barName);//歌曲名
        global.btnPlayOrPause = (ImageButton)findViewById(R.id.BtnPlayorPause);//开始暂停键
        global.musicMenu = (ImageButton)findViewById(R.id.musicMenu);//列表
        global.barImg = (ImageView) findViewById(R.id.barImg);//歌曲图片
        global.next = (ImageButton) findViewById(R.id.nextSong);//
        global.previous = (ImageButton)findViewById(R.id.preSong);//
        //进度条
        global.seekBar=(SeekBar)findViewById(R.id.seekBar);
        //时间
        global.musicTime = (TextView)findViewById(R.id.MusicTime);
    }
    /**
     *
     */
    public void initMusicService(Context context){
        global.musicService = MusicService.getInstance(context);
    }

    /**
     * 更新播放器
     */
    SimpleDateFormat time = new SimpleDateFormat("mm:ss");
    public void updateBar(){
        initBar();
        //更改context
        global.musicService.setContext(this);
        //更改资源
        global.musicService.setData();
        //初始化按钮
        global.musicService.updatePlayBtn();
        //更新service
        global.musicService.updateService();
        //按键监听
        global.btnPlayOrPause.setOnClickListener(this);
        global.next.setOnClickListener(this);
        global.previous.setOnClickListener(this);
        //菜单监听
        global.musicMenu.setOnClickListener(new MenuOnclickListener(this));
        //界面显示
        String barname = global.song.getName();
        PicCacheUtil.getInstance().display(global.barImg,global.song.getImageHttp());
        global.name.setText(barname);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.BtnPlayorPause:
                global.musicService.playOrPause();
                global.musicService.updatePlayBtn();
                break;
            case  R.id.nextSong:
                global.musicService.nextMusic();
                updateBar();
                System.out.println("切换下一首"+global.song.getName());
                break;
            case R.id.preSong:
                global.musicService.preMusic();
                updateBar();
                System.out.println("切换上一首");
        }
    }

}
