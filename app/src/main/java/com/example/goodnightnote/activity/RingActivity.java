package com.example.goodnightnote.activity;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Vibrator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.goodnightnote.R;

public class RingActivity extends AppCompatActivity {
    private Button mButton;
    private TextView mTextView;
    private final static String CONTENT = "content";
    private final static String ID = "id";

    @TargetApi(Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring);
        Intent intent = getIntent();
        String content = intent.getStringExtra(CONTENT);
        String id = intent.getStringExtra(ID);
        mButton = findViewById(R.id.bt_close);
        mTextView = findViewById(R.id.tv_content);
        mTextView.setText(content);
        NotificationManager manager = (NotificationManager) this.getSystemService(this.NOTIFICATION_SERVICE);
        //设置通知,Android8.0以上使用
        NotificationChannel channel = new NotificationChannel(id, getResources()
                .getText(R.string.importantnotification), NotificationManager.IMPORTANCE_DEFAULT);
        manager.createNotificationChannel(channel);
        final Notification notification = new Notification.Builder(RingActivity.this, id)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(getResources().getText(R.string.remind))
                .setContentText(content)
                .build();
            manager.notify(Integer.parseInt(id), notification);

        //设置震动
        final Vibrator vibrator = (Vibrator) this.getSystemService(this.VIBRATOR_SERVICE);
        long[] patter = {1000, 1000, 1000, 1000};
        vibrator.vibrate(patter, 0);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator.cancel();
                finish();
            }
        });
    }
}
