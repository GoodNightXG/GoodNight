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
	private Button mCancelButton;
	private Context mContext;
	private String mDate;
	private EditText mEditText;
	private Date mGetDate;
	private Button mSureButton;
	private TextView mTextView;
	private String mUsername;
	private static String sShowText;

	protected void onCreate(Bundle paramBundle)
	{
		super.onCreate(paramBundle);
		setContentView(R.layout.writedown);
		this.mContext = this;
		this.mUsername = getIntent().getStringExtra("username");
		this.mTextView = ((TextView)findViewById(R.id.writedate));
		this.mEditText = ((DrawLine)findViewById(R.id.edittext));
		this.mCancelButton = ((Button)findViewById(R.id.button));
		this.mSureButton = ((Button)findViewById(R.id.button2));
		this.mGetDate = new Date();
		this.mDate = this.mGetDate.getDate();
		this.mTextView.setText(this.mDate);
		this.mSureButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				SQLiteDatabase localSqLiteDatabase = new SqliteHelper(WriteActivity.this.mContext, null,
						null, 0).getWritableDatabase();
				Note localNote = new Note();
				SqliteUtil localSqliteUtil = new SqliteUtil();
				String strContent = WriteActivity.this.mEditText.getText().toString();
				if (strContent.equals("")) {
					sShowText =  (String) WriteActivity.this.getResources().getText(R.string.empty_content);
					Toast.makeText(WriteActivity.this.mContext,sShowText, Toast.LENGTH_SHORT).show();
					return;
				}
				String strTitle=strContent.length()>11?" "+strContent.substring(0, 11):strContent;
				localNote.setmContent(strContent);
				localNote.setmTitle(strTitle);
				localNote.setmData(mDate);
				localNote.setmUser(mUsername);
				localSqliteUtil.add(localSqLiteDatabase, localNote);
				finish();
			}
		});
		this.mCancelButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}