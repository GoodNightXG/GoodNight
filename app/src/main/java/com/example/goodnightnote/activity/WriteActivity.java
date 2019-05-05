package com.example.goodnightnote.activity;
/**

 *Time:2019/04/18
 *Author: xiaoxi
 *Description:增加新的笔记的业务逻辑
 */
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.example.goodnightnote.view.Date;
import com.example.goodnightnote.R;
import com.example.goodnightnote.utils.SqliteUtil;
import com.example.goodnightnote.domian.Note;
import com.example.goodnightnote.view.DrawLine;

public class WriteActivity extends Activity {
	private Button mCancelButton;
	private Button mLabelButton;
	private Context mContext;
	private String mDateNow;
	private EditText mEditText;
	private Date mGetDate;
	private Button mSureButton;
	private TextView mTextView;
	private String mUsername;
	private String mLabel;
	private String mShowText;
	private Note mLocalNote;
	private final static String USERNANE = "username";

	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.writedown);
		this.mLocalNote = new Note();
		this.mContext = this;
		this.mUsername = getIntent().getStringExtra(USERNANE);
		this.mTextView = findViewById(R.id.writedate);
		this.mEditText = (DrawLine)findViewById(R.id.edittext);
		this.mCancelButton = findViewById(R.id.bt_cancel);
		this.mSureButton = findViewById(R.id.bt_save);
		this.mGetDate = new Date();
		this.mDateNow = this.mGetDate.getDate();
		this.mTextView.setText(this.mDateNow);
		this.mLabelButton = findViewById(R.id.bt_label);

		//设置标签按钮
		this.mLabelButton.setOnClickListener(new View.OnClickListener() {
			String life = (String) getResources().getText(R.string.life);
			String work = (String) getResources().getText(R.string.work);
			String game = (String) getResources().getText(R.string.game);
			String empty = (String) getResources().getText(R.string.empty);
			@Override
			public void onClick(View v) {
				final AlertDialog.Builder builder = new AlertDialog.Builder(WriteActivity.this);
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
				SqliteUtil localSqliteUtil = new SqliteUtil();
				String strContent = WriteActivity.this.mEditText.getText().toString();
				if (strContent.equals("")) {
					mShowText =  (String) WriteActivity.this.getResources().getText(R.string.empty_content);
					Toast.makeText(WriteActivity.this.mContext,mShowText, Toast.LENGTH_SHORT).show();
					return;
				}
				String strTitle=strContent.length()>11?" "+strContent.substring(0, 11):strContent;
				mLocalNote.setmContent(strContent);
				mLocalNote.setmTitle(strTitle);
				mLocalNote.setmData(mDateNow);
				mLocalNote.setmUser(mUsername);
				mLocalNote.setmType(mLabel);
				localSqliteUtil.add(WriteActivity.this, mLocalNote);
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