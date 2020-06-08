package com.example.score.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.example.score.R;
import com.example.score.adapter.MusicListAdapter;
import com.example.score.bean.FilmInfo;
import com.example.score.service.GetMusicService;
import com.example.score.util.ImageLoaderUtils;

import java.util.List;

public class MusicDetailActivity extends FragmentActivity {

    Bundle bundle = new Bundle();
    ImageView imageView;//
    TextView name;
    TextView composer;
    TextView intro;
    ListView musicListView;
    private MusicListAdapter musicListAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_detail_page);
        //接收item信息
        bundle = this.getIntent().getExtras();
        final FilmInfo item = (FilmInfo) bundle.get("item");
        //
        System.out.println(item.getName());
        name = (TextView)findViewById(R.id.film_name);
        composer = (TextView)findViewById(R.id.film_composer);
        intro = (TextView)findViewById(R.id.film_intro);
        imageView = (ImageView)findViewById(R.id.music_img);
        musicListView = (ListView)findViewById(R.id.music_list);
        //
        new ImageLoaderUtils().getImageBitmap(item.getImageHttp(),imageView,this);
        name.setText(item.getName());
        composer.setText(item.getComposer());
        intro.setText(item.getIntro());
        //设置Item的配乐
        GetMusicService getMusicService = new GetMusicService();
        item.setMusicList(getMusicService.getMusicList(item.getTvId()));
        //System.out.println("音乐列表"+item.getMusicList().get(0).getName());
        //音乐ListView
        musicListAdapter = new MusicListAdapter(item.getMusicList(),this);
        musicListView.setAdapter(musicListAdapter);
        //
        musicListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String str = item.getMusicList().get(position).getMusicHttp();
                System.out.println("播放"+str);
            }
        });
    }
}