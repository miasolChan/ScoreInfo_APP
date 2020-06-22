package com.example.score.service;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.score.R;
import com.example.score.activity.BaseActivity;
import com.example.score.activity.MusicDetailActivity;
import com.example.score.bean.MusicInfo;
import com.example.score.util.Global;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;


public class MusicService extends Service {

    public final IBinder binder = new MyBinder();

    Global global;

    public class MyBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
    public static MediaPlayer mp = new MediaPlayer();//播放器
    private MusicInfo song;//歌曲信息
    private Context context;
    private String musicHttp;
    public android.os.Handler handler = new android.os.Handler();

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private static MusicService getInstance(){
        return getInstance();
    }

    private static MusicService ins;
    public static MusicService getInstance(Context context){
        ins = new MusicService(context);
        return ins;
    }

    private MusicService(Context context){
        this.context = context;
    }

    /**
     * 更新控件绑定
     */
    View rootView;
    public void updateService(){
        //rootView = ((Activity)context).getWindow().getDecorView().findViewById(android.R.id.content);
        global.seekBar = (SeekBar) ((Activity)context).findViewById(R.id.seekBar);
        global.musicTime = (TextView)((Activity)context).findViewById(R.id.MusicTime);
        global.seekBar.setProgress(mp.getCurrentPosition());
        global.seekBar.setMax(mp.getDuration());
        handler.post(runnable);
    }

    /**
     * 更新seekbar和时间的线程
     */
    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            updateMusicTime();
            handler.postDelayed(runnable,100);
        }
    };

    /**
     * 更新seekbar和时间
     */
    SimpleDateFormat time = new SimpleDateFormat("mm:ss");
    public void updateMusicTime(){
        global.musicTime.setText(time.format(mp.getCurrentPosition()) + "/"
                + time.format(mp.getDuration()));
        global.seekBar.setProgress(mp.getCurrentPosition());
        global.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mp.seekTo(seekBar.getProgress());
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    /**
     * 加载资源
     */
    public void setData(){
        try {
            if(!mp.isPlaying()) {
                mp.reset();
                try{
                    mp.setDataSource(context, Uri.parse(getMusicHttp(global.MUSIC_INDEX)));
                }catch (FileNotFoundException e){
                    Toast.makeText(context, "未找到资源", Toast.LENGTH_SHORT).show();
                }
                mp.prepare();
            }else{
                System.out.println("bbbbbb");
                return;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }catch (IllegalStateException e){
            e.printStackTrace();
            //Toast.makeText(context, "音乐加载中", Toast.LENGTH_SHORT).show();
        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
    }


    public void playOrPause() {
        if(global.MUSIC_INDEX==-1){
            try{
                Toast.makeText(context, "列表中没有音乐", Toast.LENGTH_SHORT).show();
            }catch (NullPointerException e){

            }

        }
        if(mp.isPlaying()){
            System.out.println("音乐暂停");
            mp.pause();
        } else {
            System.out.println("开始播放");
            mp.start();
        }
    }
    public void updatePlayBtn(){
        if(mp.isPlaying()){
            global.btnPlayOrPause.setBackgroundResource(R.drawable.play);
        } else {
            global.btnPlayOrPause.setBackgroundResource(R.drawable.pause);
        }
    }

    public void nextMusic() {
        updatePlayBtn();
        if(mp != null) {
            if(global.MUSIC_INDEX < global.musicQue.size()-1){
                //mp.stop();
                try {
                    mp.reset();
                    //mp.setDataSource(musicDir[musicIndex+1]);
                    mp.setDataSource(context,Uri.parse(getMusicHttp(global.MUSIC_INDEX+1)));
                    global.song = global.musicQue.get(global.MUSIC_INDEX+1);
                    Global.MUSIC_INDEX++;
                    System.out.println(global.song.getName());
                    mp.prepare();
                    mp.seekTo(0);
                    mp.start();
                } catch (Exception e) {
                    Log.d("hint", "can't jump next music");
                    e.printStackTrace();
                }
            }
            else {
                Global.MUSIC_INDEX = 0;
            }

        }
    }
    public void preMusic() {
        updatePlayBtn();
        if(mp != null && Global.MUSIC_INDEX > 0) {
            //mp.stop();
            try {
                mp.reset();
                //mp.setDataSource(musicDir[musicIndex-1]);
                mp.setDataSource(context,Uri.parse(getMusicHttp(global.MUSIC_INDEX-1)));
                global.song = global.musicQue.get(global.MUSIC_INDEX-1);
                global.MUSIC_INDEX--;
                mp.prepare();
                mp.seekTo(0);
                mp.start();
            } catch (Exception e) {
                Log.d("hint", "can't jump pre music");
                e.printStackTrace();
            }
        }
    }
    /**
     * 获取音乐http
     */
    public String getMusicHttp(int index){
        this.musicHttp = global.musicQue.get(index).getMusicHttp();
        return  musicHttp;
    }



}
