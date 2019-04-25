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
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.goodnightnote.R;
import com.example.goodnightnote.adapter.NoteAdapter;
import com.example.goodnightnote.utils.SqliteHelper;
import com.example.goodnightnote.utils.SqliteUtil;
import com.example.goodnightnote.domian.Note;

public class MainActivity extends Activity{
    public String EXPANDED = "EXPANDED";
    public NoteAdapter adapter;
    public ArrayList<Map<String, Object>> itemList;
    public ListView listView;
    public int number;
    public Button numberButton;
    public Button topButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.numberButton = ((Button) findViewById(R.id.number));
        this.topButton = ((Button) findViewById(R.id.topButton));
        this.listView = ((ListView) findViewById(R.id.listview));
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

}
