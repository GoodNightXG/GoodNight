package com.example.goodnightnote.login;
/**
 *Time:2019/04/23
 *Author: zuosc
 */
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.goodnightnote.R;
import com.example.goodnightnote.utils.UserTableUtil;

public class RegisterActivity extends AppCompatActivity {
    private EditText mUsername;
    private EditText mPassword;
    private EditText mPwd;
    private Button mButton;
    private String mShowText;
    private final static String REGEX = "^(?!([a-zA-Z]+|\\d+)$)[a-zA-Z\\d]{6,20}$";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mUsername = findViewById(R.id.et_username_new);
        mPassword = findViewById(R.id.et_password_new);
        mPwd = findViewById(R.id.et_password_ack);
        mButton = findViewById(R.id.bt_send_register);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             UserTableUtil userTableUtil = new UserTableUtil();
             String username = mUsername.getText().toString().trim();
             String password = mPassword.getText().toString().trim();
             String pwd = mPwd.getText().toString();
             Cursor cursor = userTableUtil.query(RegisterActivity.this, username);
                if (username.equals("") || password.equals("") || pwd.equals("")) {
                    mShowText = (String) RegisterActivity.this.getResources()
                            .getText(R.string.please_input);
                    Toast.makeText(RegisterActivity.this, mShowText,
                            Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (cursor.getCount() != 0) {
                        cursor.moveToFirst();
                        String showText = (String) RegisterActivity.this.getResources()
                                .getText(R.string.account_exiet);
                        Toast.makeText(RegisterActivity.this, showText,
                                Toast.LENGTH_SHORT).show();
                        return;
                    } else {

                        if (password.matches(REGEX)) {
                            if (password.equals(pwd)) {
                                userTableUtil.add(RegisterActivity.this, username, password);
                                mShowText = (String) RegisterActivity.this.getResources()
                                        .getText(R.string.register_success);
                                Toast.makeText(RegisterActivity.this, mShowText,
                                        Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                mShowText = (String) RegisterActivity.this.getResources()
                                        .getText(R.string.confirm_pssword_error);
                                Toast.makeText(RegisterActivity.this, mShowText,
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            mShowText = (String) RegisterActivity.this.getResources()
                                    .getText(R.string.password_specification);
                            Toast.makeText(RegisterActivity.this, mShowText,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }
}
