package com.example.score.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.score.R;
import com.example.score.adapter.MusicListAdapter;
import com.example.score.bean.FilmInfo;
import com.example.score.bean.MusicInfo;
import com.example.score.bean.MyDialog;
import com.example.score.service.GetMusicService;

import com.example.score.util.Global;
import com.example.score.util.PicCacheUtil;


import static com.example.score.service.MusicService.mp;

public class MusicDetailActivity extends BaseActivity {

    Bundle bundle = new Bundle();
    ImageView imageView;//
    TextView name;
    TextView composer;
    TextView intro;
    ListView musicListView;
    ImageButton imageButton;
    private MusicListAdapter musicListAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_detail);
        //接收item信息
        bundle = this.getIntent().getExtras();
        final FilmInfo item = (FilmInfo) bundle.get("item");
        //绑定控件
        System.out.println(item.getName());
        name = (TextView)findViewById(R.id.film_name);
        composer = (TextView)findViewById(R.id.film_composer);
        intro = (TextView)findViewById(R.id.film_intro);
        imageView = (ImageView)findViewById(R.id.music_img);
        musicListView = (ListView)findViewById(R.id.music_list);
        imageButton = (ImageButton)findViewById(R.id.detailToList);
        //
        //初始化
        //ImageLoaderUtils.getImageBitmap(item.getImageHttp(),imageView,this);
        PicCacheUtil.getInstance().display(imageView,item.getImageHttp());
        name.setText(item.getName());
        composer.setText(item.getComposer());
        intro.setText(item.getIntro());
        //返回
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicDetailActivity.this.finish();
            }
        });
        //intro打开
        intro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MyDialog dialog = new MyDialog(MusicDetailActivity.this,false);
                dialog.setSingle(true);
                dialog.setTitle(item.getName());
                dialog.setMessage(item.getIntro());
                dialog.setOnClickBottomListener(new MyDialog.OnClickBottomListener() {
                    @Override
                    public void onPositiveClick() {
                        dialog.dismiss();
                    }

                    @Override
                    public void onNegtiveClick() {

                    }
                });
                dialog.show();

            }
        });
        //设置Item的配乐
        GetMusicService getMusicService = new GetMusicService();
        item.setMusicList(getMusicService.getMusicList(item.getTvId()));
        //System.out.println("音乐列表"+item.getMusicList().get(0).getName());

        //音乐ListView
        musicListAdapter = new MusicListAdapter(item.getMusicList(),this);
        musicListView.setAdapter(musicListAdapter);
        //Item点击事件
        musicListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                MusicInfo song = item.getMusicList().get(position);
                //判断歌曲是否已经加入
                if(!Global.IF_First&&Global.song.getName().equals(song.getName())){
                    Toast.makeText(MusicDetailActivity.this, "歌曲已在列表中", Toast.LENGTH_SHORT).show();
                    Global.IF_First = false;
                    return;
                }
                //播放次数增加
                global.mCount++;
                //添加到列表
                Global.musicQue.add(song);
                Global.MUSIC_INDEX++;
                Toast.makeText(MusicDetailActivity.this, "成功加入播放列表", Toast.LENGTH_SHORT).show();
                //设置歌曲信息
                MusicDetailActivity.super.setSong(song);
                //Global.IF_SONG_CHANGE = true;
                //
                mp.reset();
                //更新
                MusicDetailActivity.super.updateBar();
                //
                Global.IF_First = false;
            }
        });

    }

    @Override
    protected void  onResume() {
        if(Global.song!=null) updateBar();
        super.onResume();
    }


}