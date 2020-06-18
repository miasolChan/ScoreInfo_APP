package com.example.score.listener;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.example.score.R;
import com.example.score.bean.MyDialog;

public class PagerOnClickListener implements View.OnClickListener{

    Context mContext;
    public PagerOnClickListener(Context mContext){
        this.mContext=mContext;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pager_img1:
                Toast.makeText(mContext, "图片1被点击", Toast.LENGTH_SHORT).show();
                break;
            case R.id.pager_img2:
                Toast.makeText(mContext, "图片2被点击", Toast.LENGTH_SHORT).show();
                break;
            case R.id.pager_img3:
                Toast.makeText(mContext, "图片3被点击", Toast.LENGTH_SHORT).show();
                break;
            case R.id.pager_img4:
                Toast.makeText(mContext, "图片4被点击", Toast.LENGTH_SHORT).show();
                break;
            case R.id.pager_img5:
                initQuestionDialog();
                break;
        }
    }

    public void initQuestionDialog(){
        final MyDialog dialog = new MyDialog(mContext,true);

        dialog.setOnClickBottomListener(new MyDialog.OnClickBottomListener() {
            @Override
            public void onPositiveClick() {
                dialog.dismiss();
                Toast.makeText(mContext, "反馈成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNegtiveClick() {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}