package com.example.goodnightnote.activity;
/**

 *Time:2019/04/18
 *Author: xiaoxi
 *Description:增加新的笔记的业务逻辑
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.example.goodnightnote.base.BaseActivity;
import com.example.goodnightnote.view.Date;
import com.example.goodnightnote.R;
import com.example.goodnightnote.utils.SqliteHelper;
import com.example.goodnightnote.utils.SqliteUtil;
import com.example.goodnightnote.domian.Note;
import com.example.goodnightnote.view.DrawLine;

public class WriteActivity extends BaseActivity {
	private Button cancelButton;
	private Context context = this;
	private String date;
	private EditText editText;
	private Date getDate;
	private Button sureButton;
	private TextView textView;

	protected void onCreate(Bundle paramBundle)
	{
		super.onCreate(paramBundle);
		setContentView(R.layout.writedown);
		this.textView = ((TextView)findViewById(R.id.writedate));
		this.editText = ((DrawLine)findViewById(R.id.edittext));
		this.cancelButton = ((Button)findViewById(R.id.button));
		this.sureButton = ((Button)findViewById(R.id.button2));
		this.getDate = new Date();
		this.date = this.getDate.getDate();
		this.textView.setText(this.date);
		this.sureButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				SQLiteDatabase localSqLiteDatabase = new SqliteHelper(WriteActivity.this.context, null,
						null, 0).getWritableDatabase();
				Note localNote = new Note();
				SqliteUtil localSqliteUtil = new SqliteUtil();
				String strContent = WriteActivity.this.editText.getText().toString();
				if (strContent.equals("")) {
					Toast.makeText(WriteActivity.this.context, "内容为空", Toast.LENGTH_SHORT).show();
					return;
				}
				String strTitle=strContent.length()>11?" "+strContent.substring(0, 11):strContent;
				localNote.setContent(strContent);
				localNote.setTitle(strTitle);
				localNote.setdata(date);
				localSqliteUtil.add(localSqLiteDatabase, localNote);
				finish();
			}
		});
		this.cancelButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}

























