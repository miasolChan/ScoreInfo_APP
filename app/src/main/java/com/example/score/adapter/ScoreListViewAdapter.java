package com.example.score.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.score.R;

import com.example.score.bean.FilmInfo;
import com.example.score.util.ImageLoaderUtils;

import java.util.List;

public class ScoreListViewAdapter extends BaseAdapter {

    private List<FilmInfo> infoList;
    private Context context;
    private int count = 0;

    public ScoreListViewAdapter(List<FilmInfo> infoList, Context context) {
        this.infoList = infoList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return infoList.size();
    }

    @Override
    public Object getItem(int position) {
        return infoList;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if(convertView == null){
            convertView = LayoutInflater.from(this.context).inflate(R.layout.score_list_item,null,false);
            holder.imageView = (ImageView)convertView.findViewById(R.id.film_item_image);
            holder.name = (TextView)convertView.findViewById(R.id.film_item_name);
            holder.composer = (TextView)convertView.findViewById(R.id.film_item_comp);
            convertView.setTag(holder);
        }else {
            holder =(ScoreListViewAdapter.ViewHolder)convertView.getTag();
        }

        holder.name.setText(infoList.get(position).getName());
        holder.composer.setText(infoList.get(position).getComposer());
        //
        if(parent.getChildCount() == position) {
            FilmInfo item = infoList.get(position);
            new ImageLoaderUtils().getImageBitmap(item.getImageHttp(), holder.imageView, this.context);
            count++;
            System.out.println(count);
        }
        return convertView;
    }

    class ViewHolder{
        ImageView imageView;
        TextView name;
        TextView composer;

    }


}
