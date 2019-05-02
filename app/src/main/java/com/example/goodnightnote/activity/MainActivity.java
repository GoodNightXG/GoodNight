package com.example.goodnightnote.activity;
/**

 *Time:2019/04/18
 *Author: xiaoxi  wangsheng  zuosc
 *Description:
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.goodnightnote.R;
import com.example.goodnightnote.adapter.NoteAdapter;
import com.example.goodnightnote.login.LoginActivity;
import com.example.goodnightnote.utils.SqliteHelper;
import com.example.goodnightnote.utils.SqliteUtil;
import com.example.goodnightnote.domian.Note;
import com.example.goodnightnote.utils.UserTableUtil;

public class MainActivity extends Activity{
    private static int sNumber;
    private static String sShowText;
    private NoteAdapter mAdapter;
    private ArrayList<Map<String, Object>> mItemList;
    private ListView mListView;
    private Button mNumberButton;
    private Button mTopButton;
    private Button mLogoutButton;
    private String mUsername;
    private Spinner mSpinner;
    private Iterator<Note> mLocalIterator;
    private long mSelectedType = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.mUsername = getIntent().getStringExtra("extra_User");
        this.mNumberButton = findViewById(R.id.number);
        this.mTopButton = findViewById(R.id.topButton);
        this.mListView = findViewById(R.id.listview);
        this.mLogoutButton = findViewById(R.id.lobutton);
        this.mSpinner = findViewById(R.id.spinner);
        this.mListView.setDivider(null);
        this.mListView.setOnItemClickListener(new ItemClick());
        //分类查看
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               mSelectedType = id - 1;
                showUpdate();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //新建按钮
        this.mTopButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,
                        WriteActivity.class);
                intent.putExtra("username", mUsername);
                startActivity(intent);
            }
        });
        //注销按钮
        this.mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loDialog((String)MainActivity.this.getResources().getText(R.string.exit_app));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        showUpdate();
    }
    //刷新页面
    public void showUpdate() {
        this.mItemList = new ArrayList<>();
        SQLiteDatabase localSqLiteDatabase = new SqliteHelper(this,
                null, null,
                0).getReadableDatabase();
        if(mSelectedType == -1){
            this.mLocalIterator = new SqliteUtil().query(
                    localSqLiteDatabase, mUsername).iterator();
        }else if(mSelectedType == 0){
            this.mLocalIterator = new SqliteUtil()
                    .queryEmpty(localSqLiteDatabase, mUsername,mSelectedType).iterator();
        } else{
            this.mLocalIterator = new SqliteUtil()
                    .query(localSqLiteDatabase, mUsername, mSelectedType).iterator();
        }
        //循环读取数据库中的Note记录
        while (mLocalIterator.hasNext()) {
            Note localNote = mLocalIterator.next();
            HashMap<String, Object> localHashMap = new HashMap<>();
            localHashMap.put("titleItem", localNote.getmTitle());
            localHashMap.put("dateItem", localNote.getmData());
            localHashMap.put("contentItem", localNote.getmContent());
            localHashMap.put("idItem", localNote.getmId());
            localHashMap.put("typeItem", localNote.getmType());
            // 默认笔记是摊开还是折叠，true为摊开
            localHashMap.put("EXPANDED", Boolean.valueOf(false));
            this.mItemList.add(localHashMap);
            this.sNumber = this.mItemList.size();
            this.mNumberButton.setText(""+this.sNumber);
             }
            Collections.reverse(this.mItemList);
            this.mAdapter = new NoteAdapter(this, this.mItemList);
            this.mListView.setAdapter(this.mAdapter);
            if (this.mItemList.size() == 0) {
                sNumber = 0;
                this.mNumberButton.setText("");
            }
            return;
    }

    class ItemClick implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> paramAdapterView,
                                View paramView, int paramInt, long paramLong) {
            Map<String, Object> localMap = MainActivity.this.mItemList
                    .get(paramInt);
            if (((Boolean) localMap.get("EXPANDED")).booleanValue()) {
                localMap.put("EXPANDED", Boolean.valueOf(false));
            } else {
                localMap.put("EXPANDED", Boolean.valueOf(true));
            }
            MainActivity.this.mAdapter.notifyDataSetChanged();
        }
    }
        //注销对话框
        public void loDialog(String message) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            dialog.setMessage(message);
            dialog.setCancelable(false);
            sShowText = (String)MainActivity.this.getResources().getText(R.string.ok);
            dialog.setPositiveButton(sShowText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    UserTableUtil userTableUtil = new UserTableUtil();
                    SQLiteDatabase localDatabase =
                            new SqliteHelper(MainActivity.this,
                                    null, null,0 )
                                    .getWritableDatabase();
                    Intent intent = getIntent();
                    String username = intent.getStringExtra("extra_User");
                    userTableUtil.delete(localDatabase,username);
                    exitApp();
                }
            });
            sShowText = (String)MainActivity.this.getResources().getText(R.string.cancel);
            dialog.setNegativeButton(sShowText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            dialog.show();
        }
        public void exitApp() {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, LoginActivity.class);

            // 设置标记位
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("exit", true);
            startActivity(intent);
        }
}
