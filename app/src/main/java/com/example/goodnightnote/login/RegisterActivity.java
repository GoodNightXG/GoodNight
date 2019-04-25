package com.example.goodnightnote.login;
/**

 *Time:2019/04/23
 *Author: zuosc

 */
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.goodnightnote.R;
import com.example.goodnightnote.utils.SqliteHelper;
import com.example.goodnightnote.utils.UserTableUtil;


public class RegisterActivity extends AppCompatActivity {
    private EditText mUsername;
    private EditText mPassword;
    private EditText mPwd;
    private Button mButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mUsername = (EditText) findViewById(R.id.et_username_new);
        mPassword = (EditText) findViewById(R.id.et_password_new);
        mPwd =  (EditText) findViewById(R.id.et_password_ack);
        mButton = (Button) findViewById(R.id.bt_send_register);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserTableUtil userTableUtil = new UserTableUtil();
                String username  = mUsername.getText().toString();
                String password = mPassword.getText().toString();
                String pwd = mPwd.getText().toString();
                SQLiteDatabase localDatabase =
                        new SqliteHelper(RegisterActivity.this,
                                null, null,0 )
                                .getWritableDatabase();
                Cursor cursor =
                userTableUtil.query(localDatabase, username);
                if(username.equals("")||password.equals("")||pwd.equals("")){
                    Toast.makeText(RegisterActivity.this, "请输入账号和密码",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if(cursor.getCount() != 0){
                    cursor.moveToFirst();
                    Toast.makeText(RegisterActivity.this, "该账号已被注册",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                String regex = "^(?!([a-zA-Z]+|\\d+)$)[a-zA-Z\\d]{6,20}$";
                if(password.matches(regex)) {

                    if (password.equals(pwd)){
                        userTableUtil.add(localDatabase,username,password);
                        Toast.makeText(RegisterActivity.this, "注册成功",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(RegisterActivity.this, "确认密码错误",
                                Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(RegisterActivity.this,
                            "密码长度为6-20位，且包含数字和字母",
                            Toast.LENGTH_SHORT).show();
                }
                }
        });
    }
}
