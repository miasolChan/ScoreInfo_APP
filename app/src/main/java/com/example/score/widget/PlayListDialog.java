package com.example.score.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.score.R;
import com.example.score.adapter.PlayListAdapter;
import com.example.score.bean.MusicInfo;
import com.example.score.util.Global;

public class PlayListDialog extends Dialog {

    Context context;
    ListView playList;
    public PlayListDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_music_list);
        //
        playList = (ListView)findViewById(R.id.playList);
        //绑定适配器
        playList.setAdapter(new PlayListAdapter(context));
        //设置监听

    }
}
