package com.example.score;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.text.TextPaint;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

    private String[] tags=new String[]{"page1","page2"};
    private String[] titles=new String[]{"配乐","文章"};
    private TabHost tabHost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //标签栏
        tabHost = (TabHost)findViewById(R.id.tabHost);
        initTab(tabHost);
    }

    /**
     * 初始化Tab按键
     */
    private void initTab(final TabHost tabHost){
        tabHost.setup();
        for (int i = 0; i < 2 ; i++) {
            TabHost.TabSpec tabSpec = tabHost.newTabSpec(tags[i]);
            View view=getLayoutInflater().inflate(R.layout.tab, null);
            TextView tv1 = (TextView)view.findViewById(R.id.tabLable);
            tv1.setText(titles[i]);
            tabSpec.setIndicator(view);
            tabSpec.setContent(R.id.tab1);
            tabHost.addTab(tabSpec);
        }
        tabHost.setCurrentTab(0);
        updateTab(tabHost);
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                tabHost.setCurrentTabByTag(tabId);
                updateTab(tabHost);
            }
        });
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

}
