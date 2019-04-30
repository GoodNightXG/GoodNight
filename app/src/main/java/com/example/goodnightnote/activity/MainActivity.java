package com.example.goodnightnote.activity;
/**

 *Time:2019/04/18
 *Author: xiaoxi  wangsheng
 *Description:
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.goodnightnote.R;
import com.example.goodnightnote.adapter.NoteAdapter;
import com.example.goodnightnote.login.LoginActivity;
import com.example.goodnightnote.login.RegisterActivity;
import com.example.goodnightnote.utils.SqliteHelper;
import com.example.goodnightnote.utils.SqliteUtil;
import com.example.goodnightnote.domian.Note;
import com.example.goodnightnote.utils.UserTableUtil;

public class MainActivity extends Activity{
    private final static String EXPANDED = "EXPANDED";
    private NoteAdapter mAdapter;
    private ArrayList<Map<String, Object>> mItemList;
    private ListView mListView;
    private static int sNumber;
    private Button mNumberButton;
    private Button mTopButton;
    private Button mLogoutButton;
    private RelativeLayout mLayout;
    private static String sShowText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mNumberButton = ((Button) findViewById(R.id.number));
        this.mTopButton = ((Button) findViewById(R.id.topButton));
        this.mListView = ((ListView) findViewById(R.id.listview));
        this.mLogoutButton = (Button) findViewById(R.id.lobutton);
        this.mLayout = (RelativeLayout) findViewById(R.id.mainId);
        // this.listView.setDivider(getResources().getDrawable(android.R.color.white));
        this.mListView.setDivider(null);
        this.mListView.setOnItemClickListener(new ItemClick());
        this.mTopButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,
                        WriteActivity.class);
                startActivity(intent);

            }
        });

        this.mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                LoDialog((String)MainActivity.this.getResources().getText(R.string.exit_app));
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

    public void showUpdate() {
        this.mItemList = new ArrayList<Map<String, Object>>();
        SQLiteDatabase localSqLiteDatabase = new SqliteHelper(this,
                null, null,
                0).getReadableDatabase();
        Iterator<Note> localIterator = new SqliteUtil().query(
                localSqLiteDatabase).iterator();
        while (true) {
            if (!localIterator.hasNext()) {
                Collections.reverse(this.mItemList );
                this.mAdapter = new NoteAdapter(this, this.mItemList );
                this.mListView.setAdapter(this.mAdapter);
                if (this.mItemList.size()==0) {
                    sNumber=0;
                    this.mNumberButton.setText("");
                }
                return;
            }
            Note localNote = (Note) localIterator.next();
            HashMap<String, Object> localHashMap = new HashMap<String, Object>();
            localHashMap.put("titleItem", localNote.getTitle());
            localHashMap.put("dateItem", localNote.getdata());
            localHashMap.put("contentItem", localNote.getContent());
            localHashMap.put("idItem", localNote.getid());
            // 默认笔记是摊开还是折叠，true为摊开
            localHashMap.put("EXPANDED", Boolean.valueOf(false));
            this.mItemList.add(localHashMap);
            this.sNumber = this.mItemList.size();
            this.mNumberButton.setText("(" + this.sNumber + ")");
        }

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
        public void LoDialog(String message) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            dialog.setTitle(" ");
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
