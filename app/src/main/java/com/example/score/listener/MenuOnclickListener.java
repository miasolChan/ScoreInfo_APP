package com.example.score.listener;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import com.example.score.widget.PlayListDialog;

public class MenuOnclickListener implements View.OnClickListener {
    Context mContext;
    public MenuOnclickListener(Context mContext){
        this.mContext=mContext;
    }
    @Override
    public void onClick(View v) {
        //System.out.println("点击菜单");
        PlayListDialog playListDialog = new PlayListDialog(mContext);
        playListDialog.show();
    }
}
