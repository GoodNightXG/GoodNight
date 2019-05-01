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
    private static String sShowText;
    private final static String REGEX = "^(?!([a-zA-Z]+|\\d+)$)[a-zA-Z\\d]{6,20}$";
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
             String username = mUsername.getText().toString();
             String password = mPassword.getText().toString();
             String pwd = mPwd.getText().toString();
             SQLiteDatabase localDatabase = new SqliteHelper(RegisterActivity.this,
                                null, null, 0)
                                .getWritableDatabase();
             Cursor cursor = userTableUtil.query(localDatabase, username);
                if (username.equals("") || password.equals("") || pwd.equals("")) {
                    sShowText = (String) RegisterActivity.this.getResources()
                            .getText(R.string.please_input);
                    Toast.makeText(RegisterActivity.this, sShowText,
                            Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (cursor.getCount() != 0) {
                        cursor.moveToFirst();
                        sShowText = (String) RegisterActivity.this.getResources()
                                .getText(R.string.account_exiet);
                        Toast.makeText(RegisterActivity.this, sShowText,
                                Toast.LENGTH_SHORT).show();
                        return;
                    } else {

                        if (password.matches(REGEX)) {
                            if (password.equals(pwd)) {
                                userTableUtil.add(localDatabase, username, password);
                                sShowText = (String) RegisterActivity.this.getResources()
                                        .getText(R.string.register_success);
                                Toast.makeText(RegisterActivity.this, sShowText,
                                        Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                sShowText = (String) RegisterActivity.this.getResources()
                                        .getText(R.string.confirm_pssword_error);
                                Toast.makeText(RegisterActivity.this, sShowText,
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            sShowText = (String) RegisterActivity.this.getResources()
                                    .getText(R.string.password_specification);
                            Toast.makeText(RegisterActivity.this,
                                    sShowText,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }
}
