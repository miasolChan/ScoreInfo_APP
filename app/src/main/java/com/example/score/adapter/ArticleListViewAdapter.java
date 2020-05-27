package com.example.score.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.score.R;
import com.example.score.bean.ArticleInfo;
import com.example.score.util.ImageLoaderUtils;

import java.util.List;

public class ArticleListViewAdapter extends BaseAdapter {
    private List<ArticleInfo> infoList;
    private Context context;
    private int count = 0;

    public ArticleListViewAdapter(List<ArticleInfo> infoList, Context context) {
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
            convertView = LayoutInflater.from(this.context).inflate(R.layout.article_list_item,null,false);
            holder.imageView = (ImageView)convertView.findViewById(R.id.article_image);
            holder.title = (TextView)convertView.findViewById(R.id.article_title);
            holder.subhead = (TextView)convertView.findViewById(R.id.article_subhead);
            convertView.setTag(holder);
        }else {
            holder =(ViewHolder)convertView.getTag();
        }
        holder.title.setText(infoList.get(position).getTitle());
        holder.subhead.setText(infoList.get(position).getSubhead());
        //
        if(parent.getChildCount() == position) {
            ArticleInfo item = infoList.get(position);
            new ImageLoaderUtils().getImageBitmap(item.getImageHttp(), holder.imageView, this.context);
            count++;
            System.out.println(count);
        }
       return convertView;
    }
    class ViewHolder{
        ImageView imageView;
        TextView title;
        TextView subhead;
    }
}
