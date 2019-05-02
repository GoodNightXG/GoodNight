package com.example.goodnightnote.activity;
/**

 *Time:2019/04/
 *Author: xiaoxi
 *Description:编辑已存在笔记
 *       难点：回显已存在笔记的内容，修改，重新写入

 */
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.example.goodnightnote.view.Date;
import com.example.goodnightnote.view.DrawLine;
import com.example.goodnightnote.R;
import com.example.goodnightnote.utils.SqliteHelper;
import com.example.goodnightnote.utils.SqliteUtil;
import com.example.goodnightnote.domian.Note;

public class EditActivity extends Activity {
	private Button mCancelButton;
	private String mContent;
	private Context mContext;
	private String mDate;
	private Date mDateNow;
	private EditText mEditText;
	private String mId;
	private Button mSureButton;
	private TextView mTextView;
	private String mLabel;
	private Button mLabelButton;

	@Override
	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.writedown);
		this.mContext = this;
		this.mTextView = findViewById(R.id.writedate);
		this.mEditText = ((DrawLine)findViewById(R.id.edittext));
		this.mCancelButton = findViewById(R.id.bt_cancel);
		this.mSureButton = findViewById(R.id.bt_save);
		this.mLabelButton = findViewById(R.id.bt_label);
		this.mDate = getIntent().getStringExtra("dateItem");
		this.mContent = getIntent().getStringExtra("contentItem");
		this.mId = getIntent().getStringExtra("idItem");
		this.mLabel = getIntent().getStringExtra("typeItem");
		this.mEditText.setSelection(this.mEditText.length());
		this.mEditText.setText(this.mContent);
		this.mTextView.setText(this.mDate);
		this.mDateNow = new Date();
		this.mDate = this.mDateNow.getDate();
		this.mTextView.setText(this.mDate);
		this.mLabelButton.setOnClickListener(new View.OnClickListener() {
			String life = (String) getResources().getText(R.string.life);
			String work = (String) getResources().getText(R.string.work);
			String game = (String) getResources().getText(R.string.game);
			String empty = (String) getResources().getText(R.string.empty);
			@Override
			public void onClick(View v) {
				final AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this);
				builder.setIcon(R.drawable.ic_label);
				builder.setTitle(R.string.label);
				builder.setSingleChoiceItems(new String[]{empty, life, work, game}, 0,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								mLabel = which+"";
								dialog.dismiss();
							}
						});
				builder.setNegativeButton(R.string.cancel, null);
				builder.create().show();
			}
		});
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
					Toast.makeText(EditActivity.this.mContext,
							R.string.empty_content,
							Toast.LENGTH_SHORT).show();
					return;
				} else {
					String strTitle = strContent.length() > 11 ? " "
							+ strContent.substring(0, 11) : strContent;
					localNote.setmContent(strContent);
					localNote.setmTitle(strTitle);
					localNote.setmData(mDate);
					localNote.setmId(mId);
					localNote.setmType(mLabel);
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


