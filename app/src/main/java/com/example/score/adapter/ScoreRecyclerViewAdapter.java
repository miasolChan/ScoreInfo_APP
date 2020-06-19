package com.example.score.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.score.R;
import com.example.score.activity.MusicDetailActivity;
import com.example.score.activity.ScoreListActivity;
import com.example.score.bean.FilmInfo;
import com.example.score.util.ImageLoaderUtils;
import com.example.score.util.PicCacheUtil;

import java.io.Serializable;
import java.util.List;

public class ScoreRecyclerViewAdapter extends RecyclerView.Adapter<ScoreRecyclerViewAdapter.ViewHolder> {

    private List<FilmInfo> infoList;
    private Context context;

    public ScoreRecyclerViewAdapter(List<FilmInfo> infoList, Context context) {
        this.infoList = infoList;
        this.context = context;
    }

    @NonNull
    @Override
    public ScoreRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.score_list_item, parent, false);
        final ViewHolder holder = new ViewHolder(v);
        holder.imageView = (ImageView)v.findViewById(R.id.film_item_image);
        holder.name = (TextView)v.findViewById(R.id.film_item_name);
        holder.composer = (TextView)v.findViewById(R.id.film_item_comp);
        /*
        点击事件
         */
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position= holder.getAdapterPosition();
                FilmInfo item = infoList.get(position);
                int hot = item.getHot();
                hot++;
                item.setHot(hot);
                System.out.println(item.getName()+"热度+1"+"现在为"+hot);
                //打开详细信息Acticity
                Intent intent = new Intent();
                intent.setClass(context, MusicDetailActivity.class);
                //传对象
                Bundle bundle = new Bundle();
                bundle.putSerializable("item",(Serializable)item);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        return holder;
    }

    /**
     * 将数据绑定到ViewHolder上
     */
    @Override
    public void onBindViewHolder(@NonNull ScoreRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.name.setText(infoList.get(position).getName());
        holder.composer.setText(infoList.get(position).getComposer());
        FilmInfo item = infoList.get(position);
        //ImageLoaderUtils.getImageBitmap(item.getImageHttp(), holder.imageView, this.context);
        PicCacheUtil.getInstance().display(holder.imageView,item.getImageHttp());
    }

    @Override
    public int getItemCount() {
        return infoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name;
        TextView composer;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
