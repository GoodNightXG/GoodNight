package com.example.goodnightnote.login;
/**

 *Time:2019/04/21
 *Author: zuosc  wangsheng

 */
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.example.goodnightnote.R;
import com.example.goodnightnote.utils.UserTableUtil;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mUsernameEdit;
    private EditText mEditPassword;
    private Button mLoginButton;
    private Button mRegisterButton;
    private CheckBox mCheckBox;
    private SharedPreferences mSharedPreference;
    private SharedPreferences.Editor mEditor;
    private String mShowText;
    private final static String REMEMBER = "remember";
    private final static String USERNAME = "Username";
    private final static String PASSWORD = "Password";
    private final static String PASSWORDS = "password";
    private final static String CHANGE_USER = "change";
    private final static String EXTRA_USER = "extra_User";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mSharedPreference = PreferenceManager.getDefaultSharedPreferences(this);
        mLoginButton = findViewById(R.id.bt_login);
        mRegisterButton = findViewById(R.id.bt_register);
        mUsernameEdit = findViewById(R.id.et_username);
        mEditPassword = findViewById(R.id.et_password);
        mCheckBox = findViewById(R.id.cb_rememberpwd);
        mLoginButton.setOnClickListener(this);
        mRegisterButton.setOnClickListener(this);
        //自动登录默认为false
        Boolean remember = mSharedPreference.getBoolean(REMEMBER, false);
        Boolean change = getIntent().getBooleanExtra(CHANGE_USER, false);
        if(remember){
            String username = mSharedPreference.getString(USERNAME,"");
            String password = mSharedPreference.getString(PASSWORD, "");
            if(change) {
                mCheckBox.setChecked(false);
            } else {
                mCheckBox.setChecked(true);
                check(username , password);
                finish();
            }
        } else {

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_login:
                //验证用户名密码
                String username = mUsernameEdit.getText().toString().trim();
                String password = mEditPassword.getText().toString().trim();
                check(username, password);
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
    private void check(String username, String password){
        UserTableUtil userTableUtil = new UserTableUtil();
        //判断非空
        if (username.equals("") || password.equals("")){
            return;
        }else {
            Cursor cursor = userTableUtil.query(LoginActivity.this, username);
            //判断用户是否存在
            if(cursor.getCount() != 0) {
                //若不调用该方法，会产生CursorIndexOutOfBoundsException
                cursor.moveToFirst();
                String pwd =
                        cursor.getString(cursor.getColumnIndex(PASSWORDS));
                //判断密码是否正确
                if(pwd.equals(password)) {
                    mShowText = (String)LoginActivity.this.getResources().
                            getText(R.string.login_success);
                    Toast.makeText(LoginActivity.this,mShowText + username,
                            Toast.LENGTH_SHORT).show();
                    mEditor = mSharedPreference.edit();
                    //判断是否勾选记住密码
                    if(mCheckBox.isChecked()) {
                        mEditor.putBoolean(REMEMBER, true);
                        mEditor.putString(USERNAME, username);
                        mEditor.putString(PASSWORD, password);
                    } else {
                        mEditor.clear();
                    }
                    mEditor.apply();
                    //跳转到内容页
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this,
                            com.example.goodnightnote.activity.MainActivity.class);
                    intent.putExtra(EXTRA_USER,username);
                    startActivity(intent);
                   } else {
                    String string = (String)LoginActivity.this.getResources().
                            getText(R.string.password_error);
                    Toast.makeText(LoginActivity.this, string,
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                mShowText = (String)LoginActivity.this.getResources().
                        getText(R.string.no_this_count);
                    Toast.makeText(LoginActivity.this, mShowText,
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}
