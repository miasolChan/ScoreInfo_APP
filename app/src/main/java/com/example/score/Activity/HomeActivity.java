package com.example.score.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextPaint;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.score.R;
import com.example.score.fragment.ArticleFragment;
import com.example.score.fragment.ScoreFragment;
import com.example.score.util.DBFileUtil;

import java.io.File;

public class HomeActivity extends FragmentActivity {

    private String[] tags=new String[]{"page1","page2","page3"};
    private String[] titles=new String[]{"用户","文章","配乐"};
    private TabHost tabHost;
    private boolean ifTabInit = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        //标签栏
        tabHost = (TabHost)findViewById(R.id.tabHost);
        initTab(tabHost);
        //
        //
        ActivityCompat.requestPermissions(HomeActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);


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
        tabHost.setCurrentTab(2);
        ifTabInit = true;
        updateTab(tabHost);

//        tabHost.setOnTabChangedListener(new OnTabChangeListener() {
//            @Override
//            public void onTabChanged(String tabId) {
//                tabHost.setCurrentTabByTag(tabId);
//                updateTab(tabHost);
//            }
//        });
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
                //System.out.println("配乐页");
                //getSupportFragmentManager().beginTransaction().replace(R.id.tab1, new ScoreFragment()).commit();
            }else if(tabId.equals("page2")){
                System.out.println("文章页");
                getSupportFragmentManager().beginTransaction().replace(R.id.tab1, new ArticleFragment()).commit();
            }else if (tabId.equals("page3")&&ifTabInit){
                getSupportFragmentManager().beginTransaction().replace(R.id.tab1, new ScoreFragment()).commit();
            }
        }
    }
}