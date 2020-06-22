package com.example.score.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.score.R;

public class UserInfoActivity extends Activity {

    private TextView text_name, text_condition;
    private String name;
    private ImageButton back;
    private ImageButton setting;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        init();
    }

    protected void init() {
        //名字
        SharedPreferences sp = getSharedPreferences("UserInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        name = sp.getString("account","");
        text_name = (TextView) findViewById(R.id.text_name);
        text_name.setText(name);
        //返回
        back = (ImageButton)findViewById(R.id.usrToHome);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfoActivity.this.finish();
            }
        });
        //设置
        setting = (ImageButton) findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UserInfoActivity.this, "设置功能未开放", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
