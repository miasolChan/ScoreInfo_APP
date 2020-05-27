package com.example.score.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.score.R;
import com.example.score.fragment.ArticleFragment;

import java.util.List;

public class SpinnerAdapter extends ArrayAdapter<String> {

    private int selectId = 0;
    private Context context;
    private int resource;

    public SpinnerAdapter(Context context, int resource, String[] objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        if (convertView == null) {
            convertView = inflater.inflate(resource, parent,false);
        }
        //选择后的字体变蓝
        if(position == selectId){
            TextView tv = convertView.findViewById(R.id.spinner_item);
            tv.setTextColor(context.getResources().getColor(R.color.tabText_selected));
        }
        return super.getDropDownView(position, convertView, parent);
    }

    public void setSelectId(int selectId) {
        this.selectId = selectId;
    }
}
