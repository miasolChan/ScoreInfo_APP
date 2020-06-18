package com.example.score.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextPaint;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.score.R;
import com.example.score.fragment.ArticleFragment;
import com.example.score.fragment.ScoreFragment;
import com.example.score.service.MusicService;
import com.example.score.util.DBFileUtil;

import java.io.File;

public class HomeActivity extends BaseActivity
{

    private String[] tags=new String[]{"page1","page2"};
    private String[] titles=new String[]{"文章","配乐"};
    private TabHost tabHost;
    private boolean ifTabInit = false;
    private ImageButton userBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //
        //欢迎
        Intent intent = getIntent();
        String name = intent.getStringExtra("Username");
        Toast.makeText(this, "欢迎"+name, Toast.LENGTH_SHORT).show();
        //初始化
        init();
    }


    /**
     * 初始化
     */
    protected void init(){
        //标签栏
        tabHost = (TabHost)findViewById(R.id.tabHost);
        initTab(tabHost);
        //
        ActivityCompat.requestPermissions(HomeActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        //用户界面切换
        userBtn = (ImageButton) findViewById(R.id.btn_user);
        userBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, UserInfoActivity.class);
                //HomeActivity.this.finish();
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onRestart() {
        try {
            updateBar();
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        super.onRestart();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //创建文件夹
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        File file = new File(Environment.getExternalStorageDirectory() + "/aa/bb/");
                        System.out.println(Environment.getExternalStorageDirectory());
                        if (!file.exists()) {
                            file.mkdirs();
                        }
                        if (!file.exists()) {
                            System.out.println("创建失败");
                        }else {
                            System.out.println("创建成功");
                        }
                        initDB();
                        getSupportFragmentManager().beginTransaction().add(R.id.tab1, new ScoreFragment()).commit();
                    }
                    break;
                }
        }
    }
    private void initDB() {
        DBFileUtil.setWorkPath(HomeActivity.this, "scoreAPP/");
        DBFileUtil.copyAccessDB(HomeActivity.this);
    }

    /**
     * 初始化Tab按键
     */
    private void initTab(final TabHost tabHost){
        tabHost.setup();
        for (int i = 0; i < titles.length ; i++) {
            TabHost.TabSpec tabSpec = tabHost.newTabSpec(tags[i]);
            View view=getLayoutInflater().inflate(R.layout.tab, null);
            TextView tv1 = (TextView)view.findViewById(R.id.tabLable);
            tv1.setText(titles[i]);
            tabSpec.setIndicator(view);
            tabSpec.setContent(R.id.tab1);
            tabHost.addTab(tabSpec);
        }
        tabHost.setOnTabChangedListener(new HomeActivity.MyTabChangedListener());
        tabHost.setCurrentTab(1);
        ifTabInit = true;
        updateTab(tabHost);

    }

    /**
     * 更新Tab按键
     */
    private void updateTab(final TabHost tabHost)
    {
        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++)
        {
            View view = tabHost.getTabWidget().getChildAt(i);
            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(R.id.tabLable);
            view.setBackground(getResources().getDrawable(R.color.tab_bg));
            TextPaint tp = tv.getPaint();
            if (tabHost.getCurrentTab() == i)
            {
                //选中
                tp.setFakeBoldText(true);
                tv.setTextColor(this.getResources().getColorStateList(R.color.tabText_selected));
            }
            else
            {
                //未选中
                tp.setFakeBoldText(false);
                tv.setTextColor(this.getResources().getColorStateList(R.color.tabText_unselected));
            }
        }
    }

    /**
     * Tab按键界面跳转
     */
    private class MyTabChangedListener implements TabHost.OnTabChangeListener {
        @Override
        public void onTabChanged(String tabId) {
            tabHost.setCurrentTabByTag(tabId);
            updateTab(tabHost);
            if(tabId.equals("page1")){
                getSupportFragmentManager().beginTransaction().replace(R.id.tab1, new ArticleFragment()).commit();
            }else if(tabId.equals("page2")&&ifTabInit){
                getSupportFragmentManager().beginTransaction().replace(R.id.tab1, new ScoreFragment()).commit();
            }
        }
    }
}