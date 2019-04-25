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
    public String EXPANDED = "EXPANDED";
    public NoteAdapter adapter;
    public ArrayList<Map<String, Object>> itemList;
    public ListView listView;
    public int number;
    public Button numberButton;
    public Button topButton;
    public Button LogoutButton;
    public Button colorButton;
    private RelativeLayout layout;
    private static int flag = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.numberButton = ((Button) findViewById(R.id.number));
        this.topButton = ((Button) findViewById(R.id.topButton));
        this.listView = ((ListView) findViewById(R.id.listview));
        this.LogoutButton = (Button) findViewById(R.id.LoButton);
        this.colorButton = (Button)findViewById(R.id.ColorButton);
        this.layout = (RelativeLayout) findViewById(R.id.mainId);
        // this.listView.setDivider(getResources().getDrawable(android.R.color.white));
        this.listView.setDivider(null);
        this.listView.setOnItemClickListener(new ItemClick());
        this.topButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,
                        WriteActivity.class);
                startActivity(intent);

            }
        });

        this.LogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoDialog("是否注销当前账号并退出？");
            }
        });
        this.colorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (flag) {
                    case 1:
                        layout.setBackground(getResources().getDrawable(R.drawable.blue,null));
                        flag = flag + 1;
                        break;
                    case 2:
                        layout.setBackground(getResources().getDrawable(R.drawable.green,null));
                        flag = flag + 1;
                        break;
                    case 3:
                        layout.setBackground(getResources().getDrawable(R.drawable.yello,null));
                        flag = flag + 1;
                        break;
                    case 4:
                        layout.setBackground(getResources().getDrawable(R.mipmap.background03,null));
                        flag = 1;
                        break;
                        default:
                            break;
                }

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        showUpdate();
    }

    public void showUpdate() {
        this.itemList = new ArrayList<Map<String, Object>>();
        SQLiteDatabase localSqLiteDatabase = new SqliteHelper(this,
                null, null,
                0).getReadableDatabase();
        Iterator<Note> localIterator = new SqliteUtil().query(
                localSqLiteDatabase).iterator();
        while (true) {
            if (!localIterator.hasNext()) {
                Collections.reverse(this.itemList);
                this.adapter = new NoteAdapter(this, this.itemList);
                this.listView.setAdapter(this.adapter);
                if (this.itemList.size()==0) {
                    number=0;
                    this.numberButton.setText("");
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
            this.itemList.add(localHashMap);
            this.number = this.itemList.size();
            System.out.println("number----------number=" + number);
            this.numberButton.setText("(" + this.number + ")");
        }

    }

    class ItemClick implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> paramAdapterView,
                                View paramView, int paramInt, long paramLong) {
            System.out.println("item----------click");
            Map<String, Object> localMap = MainActivity.this.itemList
                    .get(paramInt);
            if (((Boolean) localMap.get("EXPANDED")).booleanValue()) {
                localMap.put("EXPANDED", Boolean.valueOf(false));
            } else {
                localMap.put("EXPANDED", Boolean.valueOf(true));
            }
            MainActivity.this.adapter.notifyDataSetChanged();
        }
    }
        //注销对话框
        public void LoDialog(String message) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            dialog.setTitle(" ");
            dialog.setMessage(message);
            dialog.setCancelable(false);
            dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    UserTableUtil userTableUtil = new UserTableUtil();
                    SQLiteDatabase localDatabase =
                            new SqliteHelper(MainActivity.this,
                                    null, null,0 )
                                    .getWritableDatabase();
                    Intent intent = getIntent();
                    String username = intent.getStringExtra("extra_User");
                    Log.d("TAG", username);
                    userTableUtil.delete(localDatabase,username);
                    ExitApp();
                }
            });
            dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            dialog.show();
        }
        public void ExitApp() {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, LoginActivity.class);

            // 设置标记位
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("exit", true);
            startActivity(intent);
        }
}
