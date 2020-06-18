package com.example.score.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.score.R;
import com.example.score.bean.MusicInfo;
import com.example.score.util.Global;


public class PlayListAdapter extends BaseAdapter {

    Context context;

    public PlayListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return Global.musicQue.size();
    }

    @Override
    public Object getItem(int position) {
        return Global.musicQue;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final MusicInfo song = Global.musicQue.get(position);
        ViewHolder holder = new ViewHolder();
        if(convertView == null){
            convertView = LayoutInflater.from(this.context).inflate(R.layout.dialog_music_list_item,null,false);
            holder.name = (TextView)convertView.findViewById(R.id.menuItemName);
            holder.delete = (ImageButton)convertView.findViewById(R.id.deleteSongItem);
            convertView.setTag(holder);
        }else {
            holder = (PlayListAdapter.ViewHolder)convertView.getTag();
        }
        //赋值
        try{
            holder.name.setText(song.getName());
            if(Global.musicQue.get(position).getName().equals(Global.song.getName())){
                holder.name.setTextColor(Color.rgb(246,136,45));
            }else {
                holder.name.setTextColor(Color.rgb(102,102,102));
            }
            //
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(Global.musicQue.get(position).getName().equals(Global.song.getName())){
                        Toast.makeText(context, "正在播放,无法删除", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Global.musicQue.remove(position);
                    Global.MUSIC_INDEX--;
                    System.out.println("删除音乐位置"+position);
                    notifyDataSetChanged();
                }
            });
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        return convertView;
    }
    class ViewHolder{
        TextView name;
        ImageButton delete;
    }

}
