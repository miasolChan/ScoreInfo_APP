package com.example.score.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.score.R;
import com.example.score.bean.MusicInfo;

import java.util.List;

public class MusicListAdapter extends BaseAdapter {

    private List<MusicInfo> musicInfos;
    private Context context;

    public MusicListAdapter(List<MusicInfo> musicInfos, Context context) {
        this.musicInfos = musicInfos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return musicInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return musicInfos;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if(convertView == null){
            convertView = LayoutInflater.from(this.context).inflate(R.layout.music_list_item,null,false);
            holder.num = (TextView)convertView.findViewById(R.id.music_num);
            holder.name = (TextView)convertView.findViewById(R.id.music_name);
            convertView.setTag(holder);
        }else {
            holder =(MusicListAdapter.ViewHolder)convertView.getTag();
        }
        int num = position+1;
        holder.num.setText(String.valueOf(num));
        holder.name.setText(musicInfos.get(position).getName());
        return convertView;
    }
    class ViewHolder{
        TextView num;
        TextView name;
    }
}
