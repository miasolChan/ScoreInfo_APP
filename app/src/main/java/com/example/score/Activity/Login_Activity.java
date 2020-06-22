package com.example.score.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.score.R;
import com.example.score.util.DBManager;
import com.example.score.util.UsersDBHelper;

public class Login_Activity extends Activity implements View.OnClickListener{
    private EditText edit_account, edit_password;
    private TextView text_msg;
    private Button btn_login, btn_register;
    private ImageButton openpwd;
    private boolean flag = false;
    private String account=null, password=null;
    private UsersDBHelper dbHelper;
    private CheckBox checkBox;
    private  SharedPreferences sp;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        edit_account = (EditText) findViewById(R.id.edit_account);
        edit_account.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    edit_account.clearFocus();
                }
                return false;
            }
        });
        edit_password = (EditText) findViewById(R.id.edit_password);
        edit_password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    edit_password.clearFocus();
                    //隐藏软键盘
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edit_password.getWindowToken(), 0);
                }
                return false;
            }
        });

        //控件绑定与监听设置
        text_msg = (TextView) findViewById(R.id.text_msg);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_register = (Button) findViewById(R.id.btn_register);
        openpwd = (ImageButton) findViewById(R.id.btn_openpwd);
        checkBox = (CheckBox)findViewById(R.id.checkBox);

        text_msg.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        openpwd.setOnClickListener(this);

        dbHelper = new UsersDBHelper(this, "UserData.db", null, 1);

        //若SP中有值且记住密码
        sp = getSharedPreferences("UserInfo",MODE_PRIVATE);
        editor = sp.edit();//获取编辑器
        try{
            account = sp.getString("account","");
            password = sp.getString("password","");

        }catch (NullPointerException e){
            //System.out.println("首次登陆无信息");
            e.printStackTrace();
        }

        //获取注册传来的账号
        try{
            Intent intent = getIntent();
            Bundle bundleExtra = intent.getBundleExtra("user_bundle");
            account = bundleExtra.getString("new_name");
            password = bundleExtra.getString("new_password");
        }catch (NullPointerException e){
            //e.printStackTrace();
        }

        //显示账户与密码
        edit_account.setText(account);
        edit_password.setText(password);

        //
        // 选项框checkBox
        if(password!= ""){//不为空，即记住密码
            checkBox.setChecked(true);
            System.out.println("密码"+password+"记住密码");
        }else {
            checkBox.setChecked(false);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                if (edit_account.getText().toString().trim().equals("") | edit_password.getText().
                        toString().trim().equals("")) {
                    Toast.makeText(this, "请输入账号或者注册账号！", Toast.LENGTH_SHORT).show();
                } else {
                    //验证
                    readUserInfo();
                }
                break;
            case R.id.btn_register:
                Intent intent = new Intent(Login_Activity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_openpwd:
                if (flag == true) {//不可见
                    edit_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    flag = false;
                    openpwd.setBackgroundResource(R.drawable.visible);
                } else {
                    edit_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    flag = true;
                    openpwd.setBackgroundResource(R.drawable.invisible);
                }
                break;
            case R.id.text_msg:
                //
                //System.out.println("忘记密码");
                Toast.makeText(this, "暂无此功能", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * 验证UserData.db中的用户信息，并通过SP记住密码
     * */
    protected void readUserInfo() {
        if (login(edit_account.getText().toString(), edit_password.getText().toString())) {
            //
            //登陆成功
            //Toast.makeText(this, "登陆成功！", Toast.LENGTH_SHORT).show();
            //存入SP
            if(checkBox.isChecked()){
                editor.putString("account", String.valueOf(edit_account.getText()));
                editor.putString("password",String.valueOf(edit_password.getText()));
                editor.commit();
                //System.out.println("存储信息成功");
            }else {
                editor.putString("account", String.valueOf(edit_account.getText()));
                editor.putString(null,String.valueOf(edit_password.getText()));
                editor.commit();
            }
            //跳转
            Intent intent = new Intent(Login_Activity.this, HomeActivity.class);
            intent.putExtra("Username",edit_account.getText().toString());
            startActivity(intent);
            this.finish();
        } else {
            Toast.makeText(this, "账户或密码错误，请重新输入！！", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 验证登录信息
     * */
    public boolean login(String username, String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "Select * from usertable where username=? and password=?";
        Cursor cursor = db.rawQuery(sql, new String[]{username, password});
        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
        return false;
    }
}
