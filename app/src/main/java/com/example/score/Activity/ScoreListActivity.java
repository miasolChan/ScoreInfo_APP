package com.example.score.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.score.R;
import com.example.score.adapter.ScoreListViewAdapter;
import com.example.score.adapter.ScoreRecyclerViewAdapter;
import com.example.score.bean.FilmInfo;
import com.example.score.service.GetMusicService;
import com.example.score.util.Global;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ScoreListActivity extends BaseActivity {

    //ListView
    //private ListView filmLV;
    private RecyclerView recyclerView;
    private ScoreRecyclerViewAdapter scoreRecyclerViewAdapter;
    private List<FilmInfo> filmInfos;
    private GetMusicService getMusicService;
    private ScoreListViewAdapter scoreListViewAdapter;
    ImageButton button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_list);
        //
        super.initMusicService(ScoreListActivity.this);
        //接受跳转消息
        Intent intent = getIntent();
        Bundle bundleExtra = intent.getBundleExtra("score_bundle");
        //
        //初始化列表信息
        //
        getMusicService = new GetMusicService();
        filmInfos = getMusicService.setFilmList();
        //
        //绑定控件
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        // 设置布局管理器
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //绑定适配器
        scoreRecyclerViewAdapter = new ScoreRecyclerViewAdapter(filmInfos,this);
        recyclerView.setAdapter(scoreRecyclerViewAdapter);
        //为热门配乐传值
        Bundle bundle = new Bundle();
        List<FilmInfo> filmlist = new ArrayList<FilmInfo>();
        bundle.putSerializable("filmInfos", (Serializable) filmInfos);
        //
        //返回
        button = (ImageButton)findViewById(R.id.listToMain);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScoreListActivity.this.finish();
            }
        });

    }


    @Override
    protected void  onResume() {
        if(Global.song!=null) updateBar();
        super.onResume();
    }


}
