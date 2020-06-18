package com.example.score.bean;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.score.R;


public class MyDialog extends Dialog {


    private TextView titleTv;
    private TextView messageTv;
    private Button negtiveBn ,positiveBn;
    private View columnLineView ;
    private Boolean ifEdit;//消息种类是否为反馈
    private Context context;
    private String message;
    private String title;
    private String positive,negtive;

    public MyDialog(Context context, Boolean ifEdit) {
        super(context, R.style.myDialog);
        this.context = context;
        this.ifEdit = ifEdit;
    }
    /**
     * 调用接口
     */
    public interface OnClickBottomListener{
        //点击确定按钮事件
        public void onPositiveClick();
        //点击取消按钮事件
        public void onNegtiveClick();
    }

    /**
     * 底部是否只有一个按钮
     */
    private boolean isSingle = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //是否为问题反馈
        if(ifEdit) {
            setContentView(R.layout.dialog_question);
            titleTv = findViewById(R.id.title_question);
        }
        else setContentView(R.layout.dialog_normal);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
        //初始化界面控件
        initView();
        //初始化界面数据
        refreshView();
        //初始化界面控件的事件
        initEvent();
    }

    /**
     * 初始化界面的确定和取消监听器
     */
    private void initEvent() {
        //设置确定按钮被点击后，向外界提供监听
        positiveBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( onClickBottomListener!= null) {
                    onClickBottomListener.onPositiveClick();
                }
            }
        });
        //设置取消按钮被点击后，向外界提供监听
        negtiveBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( onClickBottomListener!= null) {
                    onClickBottomListener.onNegtiveClick();
                }
            }
        });
    }

    /**
     * 初始化界面控件的显示数据
     */
    private void refreshView() {
        /**
         * 只显示一个按钮的时候隐藏取消按钮，回掉只执行确定的事件
         */
        if (isSingle){
            columnLineView.setVisibility(View.GONE);
            negtiveBn.setVisibility(View.GONE);
        }else {
            negtiveBn.setVisibility(View.VISIBLE);
            columnLineView.setVisibility(View.VISIBLE);
        }
        if(ifEdit) return;
        //如果用户自定了title和message
        if (!TextUtils.isEmpty(title)) {
            System.out.println(title);
            titleTv.setText(title);
            titleTv.setVisibility(View.VISIBLE);
        }else {
            titleTv.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(message)) {
            messageTv.setText(message);
        }
        //如果设置按钮的文字
        if (!TextUtils.isEmpty(positive)) {
            positiveBn.setText(positive);
        }else {
            positiveBn.setText("确定");
        }
        if (!TextUtils.isEmpty(negtive)) {
            negtiveBn.setText(negtive);
        }else {
            negtiveBn.setText("取消");
        }
    }

    @Override
    public void show() {
        super.show();
        refreshView();
    }

    /**
     * 初始化界面控件
     */
    private void initView() {
        titleTv=(TextView) findViewById(R.id.title_dialog);
        messageTv=(TextView) findViewById(R.id.message_dialog);
        negtiveBn = (Button) findViewById(R.id.negtive);
        positiveBn = (Button) findViewById(R.id.positive);
        columnLineView = findViewById(R.id.column_line);
    }

    /**
     * 设置确定取消按钮的回调
     */
    public OnClickBottomListener onClickBottomListener;
    public MyDialog setOnClickBottomListener(OnClickBottomListener onClickBottomListener) {
        this.onClickBottomListener = onClickBottomListener;
        return this;
    }


    public String getMessage() {
        return message;
    }

    public String getTitle() {
        return title;
    }

    public String getPositive() {
        return positive;
    }

    public String getNegtive() {
        return negtive;
    }


    public boolean isSingle() {
        return isSingle;
    }


    public void setTitleTv(TextView titleTv) {
        this.titleTv = titleTv;
    }

    public void setMessageTv(TextView messageTv) {
        this.messageTv = messageTv;
    }

    public void setNegtiveBn(Button negtiveBn) {
        this.negtiveBn = negtiveBn;
    }

    public void setPositiveBn(Button positiveBn) {
        this.positiveBn = positiveBn;
    }

    public void setColumnLineView(View columnLineView) {
        this.columnLineView = columnLineView;
    }

    public void setIfEdit(Boolean ifEdit) {
        this.ifEdit = ifEdit;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPositive(String positive) {
        this.positive = positive;
    }

    public void setNegtive(String negtive) {
        this.negtive = negtive;
    }

    public void setSingle(boolean single) {
        isSingle = single;
    }
}
