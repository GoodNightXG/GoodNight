package com.example.goodnightnote.activity;
/**

 *Time:2019/04/
 *Author: xiaoxi
 *Description:编辑已存在笔记
 *       难点：回显已存在笔记的内容，修改，重新写入

 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.example.goodnightnote.base.BaseActivity;
import com.example.goodnightnote.view.Date;
import com.example.goodnightnote.view.DrawLine;
import com.example.goodnightnote.R;
import com.example.goodnightnote.utils.SqliteHelper;
import com.example.goodnightnote.utils.SqliteUtil;
import com.example.goodnightnote.domian.Note;

public class EditActivity extends BaseActivity {
	private Button mCancelButton;
	private String mContent;
	private Context mContext;
	private String mDate;
	private Date mDateNow;
	private EditText mEditText;
	private String mId;
	private Button mSureButton;
	private TextView mTextView;

	@Override
	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.showedit);
		this.mContext = this;
		this.mTextView = ((TextView) findViewById(R.id.editdate));
		this.mEditText = ((DrawLine) findViewById(R.id.edittexttwo));
		this.mCancelButton = ((Button) findViewById(R.id.editbutton));
		this.mSureButton = ((Button) findViewById(R.id.editbutton2));
		this.mDate = getIntent().getStringExtra("dateItem");
		this.mContent = getIntent().getStringExtra("contentItem");
		this.mId = getIntent().getStringExtra("idItem");


		this.mEditText.setSelection(this.mEditText.length());
		this.mEditText.setText(this.mContent);
		this.mTextView.setText(this.mDate);
		this.mDateNow = new Date();
		this.mDate = this.mDateNow.getDate();
		this.mTextView.setText(this.mDate);
		this.mSureButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				SQLiteDatabase localSqLiteDatabase = new SqliteHelper(
						EditActivity.this.mContext, null,
						null, 0)
						.getWritableDatabase();
				Note localNote = new Note();
				SqliteUtil localSqliteUtil = new SqliteUtil();
				String strContent = EditActivity.this.mEditText.getText()
						.toString();
				if (strContent.equals("")) {
					Toast.makeText(EditActivity.this.mContext, EditActivity.this.getResources().getText(R.string.empty_content),
							Toast.LENGTH_SHORT).show();
					return;
				} else {
					String strTitle = strContent.length() > 11 ? " "
							+ strContent.substring(0, 11) : strContent;
					localNote.setmContent(strContent);
					localNote.setmTitle(strTitle);
					localNote.setmData(mDate);
					localNote.setmId(mId);
					localSqliteUtil.update(localSqLiteDatabase, localNote);
					finish();
				}
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


