package com.example.score.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;

import com.example.score.Activity.ArticleWebActivity;
import com.example.score.adapter.ArticleListViewAdapter;
import com.example.score.adapter.SpinnerAdapter;

import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.score.R;
import com.example.score.bean.ArticleInfo;
import com.example.score.service.GetArticleService;

import java.util.List;

import static android.content.ContentValues.TAG;


public class ArticleFragment extends Fragment {

    //spinner
    private Spinner kindSpinner;
    private String[] kinds = {"全部","专访","乐评"};
    private int selectId = 0;
    //listView
    private ListView articleLV;
    private List<ArticleInfo> infoList;
    private GetArticleService getArticleService = new GetArticleService();
    private SpinnerAdapter spinnerAdapter;
    private ArticleListViewAdapter articleAdapter;
    private MySpinnerListener mySpinnerListener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.article_main_page,container,false);

        //spinner
        //关联
        kindSpinner = (Spinner) view.findViewById(R.id.spinner);
        //建立Adapter,绑定数据源
        spinnerAdapter = new SpinnerAdapter(this.getContext(),R.layout.kind_spinner,kinds);
        //绑定到控件
        kindSpinner.setAdapter(spinnerAdapter);
        //初始化
        kindSpinner.setSelection(0);
        kindSpinner.setOnItemSelectedListener(new MySpinnerListener());
        //
        //listView
        //绑定
        articleLV = (ListView)view.findViewById(R.id.article_LV);
        //初始化列表信息
        infoList = getArticleService.setArticleList();
        //绑定适配器
        articleAdapter = new ArticleListViewAdapter(infoList,this.getContext());
        articleLV.setAdapter(articleAdapter);
        //点击监听
        articleLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ArticleWebActivity.class);
                Bundle bundle = new Bundle();
                intent.putExtra("article_bundle",bundle);
                bundle.putString("articleUrl",infoList.get(position).getUrl());
                startActivity(intent);
            }
        });
        return view;
    }



    private class MySpinnerListener implements Spinner.OnItemClickListener,Spinner.OnItemSelectedListener{
        public MySpinnerListener() {
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            spinnerAdapter.setSelectId(position);
            //更新数据
            List<ArticleInfo> selectedInfoList;
            selectedInfoList = getArticleService.selectItemType(infoList,position);
            //刷新listView的Adapter
            articleAdapter = new ArticleListViewAdapter(selectedInfoList,getContext());
            articleLV.setAdapter(articleAdapter);

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

}
