package com.example.score.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.score.R;
import com.example.score.adapter.ScoreListViewAdapter;
import com.example.score.bean.FilmInfo;
import com.example.score.service.GetMusicService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ScoreListActivity extends FragmentActivity {

    //ListView
    private ListView filmLV;
    private List<FilmInfo> filmInfos;
    private GetMusicService getMusicService;
    private ScoreListViewAdapter scoreListViewAdapter;
    Button button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_list_main);
        //接受跳转消息
        Intent intent = getIntent();
        Bundle bundleExtra = intent.getBundleExtra("score_bundle");
        //绑定控件
        filmLV = (ListView)findViewById(R.id.film_list);
        //初始化列表信息
        getMusicService = new GetMusicService();
        filmInfos = getMusicService.setFilmList();
        //绑定适配器
        scoreListViewAdapter = new ScoreListViewAdapter(filmInfos,this);
        filmLV.setAdapter(scoreListViewAdapter);

        //为热门配乐传值
        Bundle bundle = new Bundle();
        List<FilmInfo> filmlist = new ArrayList<FilmInfo>();
        bundle.putSerializable("filmInfos", (Serializable) filmInfos);
        //返回
        button = (Button)findViewById(R.id.listToMain);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ScoreListActivity.this, HomeActivity.class);
                startActivity(intent);
                ScoreListActivity.this.finish();

            }
        });

        filmLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FilmInfo item = filmInfos.get(position);
                int hot = item.getHot();
                hot++;
                item.setHot(hot);
                System.out.println(item.getName()+"热度+1"+"现在为"+hot);
                //打开详细信息Acticity
                Intent intent = new Intent();
                intent.setClass(ScoreListActivity.this, MusicDetailActivity.class);
                //传对象
                Bundle bundle = new Bundle();
                bundle.putSerializable("item",(Serializable)item);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
