package com.example.score.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.score.R;
import com.example.score.activity.ScoreListActivity;
import com.example.score.adapter.LoopViewAdapter;
import com.example.score.bean.FilmInfo;
import com.example.score.listener.PagerOnClickListener;
import com.example.score.service.GetMusicService;
import com.example.score.util.PicCacheUtil;

import java.util.ArrayList;
import java.util.List;

public class ScoreFragment extends Fragment {

    private ViewPager viewPager;  //轮播图模块
    private int[] mImg;
    private int[] mImg_id;
    private String[] mDec;
    private ArrayList<ImageView> mImgList;
    private LinearLayout ll_dots_container;
    private TextView loop_dec;
    private int previousSelectedPosition = 0;
    boolean isRunning = false;
    static Object lock = new Object();
    //更多按钮
    private Button more_bt;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_score_page,container,false);
        initLoopView(view);

        //按钮绑定
        more_bt = view.findViewById(R.id.more_bt);
        //点击跳转
        more_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ScoreListActivity.class);
                Bundle bundle = new Bundle();
                intent.putExtra("score_bundle",bundle);
                startActivity(intent);
            }
        });
        //绑定热门影片信息
        TextView[] arrTextView = {(TextView)view.findViewById(R.id.hot_0_textView),(TextView)view.findViewById(R.id.hot_1_textView),(TextView)view.findViewById(R.id.hot_2_textView)};
        ImageView[] arrImageView = {(ImageView)view.findViewById(R.id.hot_0_image),(ImageView)view.findViewById(R.id.hot_1_image),(ImageView)view.findViewById(R.id.hot_2_image)};
        //接收
        FilmInfo filmInfo = new FilmInfo();
        List<FilmInfo> filmInfos = new ArrayList<FilmInfo>();
        //更新热度
        try{
            Bundle bundle = new Bundle();
            filmInfos = (List<FilmInfo>) bundle.getSerializable("filmInfos");
            filmInfos.get(0);
        }catch (NullPointerException e){
            GetMusicService getMusicService = new GetMusicService();
            filmInfos = getMusicService.setFilmList();
        }finally {
            for (int i = 0; i < arrImageView.length ; i++) {
                arrTextView[i].setText(filmInfos.get(i).getName());
                String imageIdHttp = filmInfos.get(i).getImageHttp();
                //ImageLoaderUtils.getImageBitmap(imageIdHttp, arrImageView[i], view.getContext());
                PicCacheUtil.getInstance().display(arrImageView[i],imageIdHttp);
            }
        }
        //System.out.println("配乐页加载成功");
        return view;
    }

    /**
     * 轮播图设置
     * @param view
     */
    private void initLoopView(View view) {
        viewPager = (ViewPager)view.findViewById(R.id.loopviewpager);
        ll_dots_container = (LinearLayout)view.findViewById(R.id.ll_dots_loop);
        loop_dec = (TextView)view.findViewById(R.id.loop_dec);

        // 图片资源id数组
        mImg = new int[]{
                R.drawable.banner_0, R.drawable.banner_1, R.drawable.banner_2, R.drawable.banner_5, R.drawable.banner_4
        };
        // 文本描述
        mDec = new String[]{
                "Test1", "Test2", "Test3", "Test4", "Test5"
        };

        mImg_id = new int[]{R.id.pager_img1, R.id.pager_img2, R.id.pager_img3, R.id.pager_img4, R.id.pager_img5
        };

        // 初始化要展示的5个ImageView
        mImgList = new ArrayList<ImageView>();
        ImageView imageView;
        View dotView;
        LinearLayout.LayoutParams layoutParams;
        for(int i=0;i<mImg.length;i++){
            //初始化要显示的图片对象
            layoutParams = new LinearLayout.LayoutParams(288,180);
            layoutParams.gravity = Gravity.CENTER;
            imageView = new ImageView(view.getContext());
            imageView.setLayoutParams(layoutParams);
            imageView.setBackgroundResource(mImg[i]);
            imageView.setId(mImg_id[i]);
            imageView.setOnClickListener(new PagerOnClickListener(view.getContext()));
            mImgList.add(imageView);
            //加引导点
            dotView = new View(view.getContext());
            dotView.setBackgroundResource(R.drawable.dot);
            layoutParams = new LinearLayout.LayoutParams(25,25);
            if(i!=0){
                layoutParams.leftMargin=10;
            }
            //设置默认所有都不可用
            dotView.setEnabled(false);
            ll_dots_container.addView(dotView,layoutParams);
        }

        ll_dots_container.getChildAt(0).setEnabled(true);
        loop_dec.setText(mDec[0]);
        previousSelectedPosition=0;
        //设置适配器
        viewPager.setAdapter(new LoopViewAdapter(mImgList));
        // 把ViewPager设置为默认选中Integer.MAX_VALUE / t2，从十几亿次开始轮播图片，达到无限循环目的;
        int m = (Integer.MAX_VALUE / 2) %mImgList.size();
        int currentPosition = Integer.MAX_VALUE / 2 - m;
        viewPager.setCurrentItem(currentPosition);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                int newPosition = i % mImgList.size();
                loop_dec.setText(mDec[newPosition]);
                ll_dots_container.getChildAt(previousSelectedPosition).setEnabled(false);
                ll_dots_container.getChildAt(newPosition).setEnabled(true);
                previousSelectedPosition = newPosition;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        // 开启轮询
        new Thread(){
            public synchronized void run(){
                isRunning = true;
                while(isRunning){
                    try{
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //下一条
                    try{
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                            }
                        });
                    }catch (NullPointerException e){
                        System.out.println("无对象");
                    }
                }
            }

        }.start();

    }
}
