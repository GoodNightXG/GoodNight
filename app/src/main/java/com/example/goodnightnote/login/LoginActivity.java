package com.example.goodnightnote.login;
/**

 *Time:2019/04/21
 *Author: zuosc  wangsheng

 */
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.goodnightnote.R;
import com.example.goodnightnote.utils.SqliteHelper;
import com.example.goodnightnote.utils.UserTableUtil;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mUsernameEdit;
    private EditText mEditPassword;
    private Button mLoginButton;
    private Button mRegisterButton;
    private CheckBox mCheckBox;
    private SharedPreferences mSharedPreference;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mSharedPreference = PreferenceManager.getDefaultSharedPreferences(this);
        mLoginButton = (Button) findViewById(R.id.bt_login);
        mRegisterButton = (Button) findViewById(R.id.bt_register);
        mUsernameEdit = (EditText) findViewById(R.id.et_username);
        mEditPassword = (EditText) findViewById(R.id.et_password);
        mCheckBox = (CheckBox) findViewById(R.id.cb_rememberpwd);
        mLoginButton.setOnClickListener(this);
        mRegisterButton.setOnClickListener(this);
        //记住密码默认为false
        Boolean remember = mSharedPreference.getBoolean("remember", false);
        if(remember){
            mUsernameEdit.setText(mSharedPreference.getString("Username",""));
            mEditPassword.setText(mSharedPreference.getString("Password",""));
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            // 是否退出App的标识
            boolean isExitApp = intent.getBooleanExtra("exit", false);
            if (isExitApp) {
                // 关闭自身
                this.finish();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_login:
                //验证用户名密码
                check();
                break;
            case R.id.bt_register:
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
    //验证用户名密码
    private void check(){
        UserTableUtil userTableUtil = new UserTableUtil();
        SQLiteDatabase localDatabase =
                new SqliteHelper(LoginActivity.this,
                        null, null,0 )
                        .getWritableDatabase();
        String username = mUsernameEdit.getText().toString().trim();
        String password = mEditPassword.getText().toString().trim();
        //判断非空
        if (username .equals("")  || password .equals("")){
            Toast.makeText(LoginActivity.this,"用户名和密码不能为空",
                    Toast.LENGTH_SHORT).show();
        }else {
            Cursor cursor = userTableUtil.query(localDatabase, username);
            //判断用户是否存在
            if (cursor.getCount() != 0) {
                //若不调用该方法，会产生CursorIndexOutOfBoundsException
                cursor.moveToFirst();
                String pwd =
                        cursor.getString(cursor.getColumnIndex("password"));
                //判断密码是否正确
                if (pwd.equals(password)) {
                    Toast.makeText(LoginActivity.this,"登陆成功",
                            Toast.LENGTH_SHORT).show();
                    mEditor = mSharedPreference.edit();
                    //判断是否勾选记住密码
                    if(mCheckBox.isChecked()){
                        mEditor.putBoolean("remember", true);
                        mEditor.putString("Username", username);
                        mEditor.putString("Password", password);
                    }else{
                        mEditor.clear();
                    }
                    mEditor.apply();
                    //跳转到内容页
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this,
                            com.example.goodnightnote.activity.MainActivity.class);
                    intent.putExtra("extra_User",username);
                    startActivity(intent);
                   }
                else{
                    Toast.makeText(LoginActivity.this, "用户名或密码错误",
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(LoginActivity.this, "用户未注册",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}
