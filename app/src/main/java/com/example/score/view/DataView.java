package com.example.score.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.score.util.Global;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class DataView extends View {

    public DataView(Context context) {
        super(context);
    }

    public DataView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private Paint xyPaint,textPaint;//
    private int width,height;
    private int arrawSpace=30;
    private Paint linePaint,pointPaint,p2Paint;//线点画笔
    private Paint numPaint;

    private int xSpace;// 两点 X 轴之间的间距
    private int ySpace;// 每次播放所占权重
    private int x[] = new int[7];// 保存各个点的横坐标，近7天
    private int y[] = new int[7];// 保存各个点的横坐标，近7天

    private int[] num = {3,10,30,20,21,33};//前六天数据
    ArrayList<Integer> numlist;

    public void init(){
        xyPaint = new Paint(); //初始化画笔
        xyPaint.setAntiAlias(true); //设置画笔使用抗锯齿
        xyPaint.setStrokeWidth(10); //设置画笔粗细
        xyPaint.setColor(Color.rgb(72,164,236)); //设置画笔颜色为蓝色

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.rgb(102,102,102));// 文本颜色为text颜色
        textPaint.setTextSize(70);// 文字大小为 70

        linePaint = new Paint();
        linePaint.setColor(Color.rgb(246,136,45));// 连接线为橙色
        linePaint.setAntiAlias(true);// 抗锯齿
        linePaint.setStrokeWidth(8);// 宽度为 8 像素
        linePaint.setStyle(Paint.Style.FILL);// 填充
        //环
        pointPaint = new Paint();
        pointPaint.setColor(Color.rgb(246,136,45));// 连接线为橙色
        pointPaint.setStyle(Paint.Style.STROKE);
        pointPaint.setStrokeWidth(8);
        //环内填充
        p2Paint = new Paint();
        p2Paint.setColor(Color.rgb(255,255,255));
        pointPaint.setStrokeWidth(8);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        init();//调用初始画笔的方法
        width=this.getWidth();
        height=this.getHeight();
        canvas.drawLine(100, height - 400, width - 100, height - 400, xyPaint);// 绘 制 X 轴

        canvas.drawLine(100,height-400,100,200,xyPaint);//Y轴
        canvas.drawLine(width - 100 - arrawSpace, height - 400 - arrawSpace, width - 100, height - 400, xyPaint);// 绘制下半部分 X 轴箭头
        canvas.drawLine(width - 100 - arrawSpace, height - 400 + arrawSpace, width - 100, height - 400, xyPaint);// 绘制上半部分 X 轴箭头
        canvas.drawLine(100 + arrawSpace, 200 + arrawSpace, 100, 200, xyPaint);// 绘制 Y 轴箭头右半部分
        canvas.drawLine(100 - arrawSpace, 200 + arrawSpace, 100, 200, xyPaint);// 绘制 Y 轴箭头左半部分
        //文本
        canvas.drawText("时间", width - 170, height - 400 - arrawSpace - 30,textPaint);// X轴坐标提示文本
        canvas.drawText("听歌次数", 100 , 150, textPaint);// Y 轴坐标提示文本
        //

        /*
        点
         */
        //添加数据
        numlist = new ArrayList<Integer>();
        for (int i = 0; i < num.length; i++) {
            numlist.add(num[i]);
        }
        numlist.add(Global.mCount);//添加今天数据
        //x，y
        xSpace = (width - 450) / (x.length - 1);// 计算两个点之间的 X 轴间距，左右各留部分空间
        ySpace = getySpace(numlist);
        for (int i = 0; i <x.length; i++)
        {
            x[i] = 250 + i * xSpace;
            y[i] = height-400-numlist.get(i)*ySpace;
        }
        //画点
        textPaint.setTextSize(40);
        for(int i=0;i<x.length;i++)
        {
            canvas.drawCircle(x[i],y[i],12,pointPaint);
            if(i != x.length-1){
                canvas.drawLine(x[i],y[i],x[i+1],y[i+1],linePaint);
            }
            canvas.drawCircle(x[i],y[i],12,p2Paint);
            canvas.drawText(String.valueOf(numlist.get(i)), x[i]+5, y[i]-20, textPaint);//绘制次数文字
        }

        //时间
        textPaint.setTextSize(40);
        ArrayList<String> dates = new ArrayList<String>();
        dates = getWeekDate();
        for (int i = 0; i <dates.size() ; i++) {
            int pos = dates.size()-1-i;
            canvas.drawText(dates.get(pos), x[i] - 50, height - 350, textPaint);//绘制年份文字
        }

    }

    protected ArrayList<String> getWeekDate(){
        ArrayList<String> strings = new ArrayList<String>();
        SimpleDateFormat sp=new SimpleDateFormat("MM-dd");
        Calendar cal=Calendar.getInstance();
        for (int i = 0; i < 7 ; i++) {
            if(i == 0){
                cal.add(Calendar.DATE,0);
            }else {
                cal.add(Calendar.DATE, -1);
            }
            String day=sp.format(cal.getTime());//获取日期
            System.out.println(day);
            strings.add(day);
        }
        return strings;
    }

    private int getySpace(ArrayList<Integer> numList){
        int y=0;
        //最大值
        //System.out.println(numList+"最大"+Collections.max(numList));
        int max = (int) Collections.max(numList);
        y = (int) (height*0.6)/max;
        return y;
    }
}
