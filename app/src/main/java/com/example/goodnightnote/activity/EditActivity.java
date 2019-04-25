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
	private Button cancelButton;
	private String content;
	private Context context = this;
	private String date;
	private Date dateNow;
	private EditText editText;
	private String id;
	private Button sureButton;
	private TextView textView;

	@Override
	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.showedit);
		this.textView = ((TextView) findViewById(R.id.editdate));
		this.editText = ((DrawLine) findViewById(R.id.edittexttwo));
		this.cancelButton = ((Button) findViewById(R.id.editbutton));
		this.sureButton = ((Button) findViewById(R.id.editbutton2));
		this.date = getIntent().getStringExtra("dateItem");
		this.content = getIntent().getStringExtra("contentItem");
		this.id = getIntent().getStringExtra("idItem");

		System.out.println("-----idItem-----id=" + id);
		this.editText.setSelection(this.editText.length());
		this.editText.setText(this.content);
		this.textView.setText(this.date);
		this.dateNow = new Date();
		this.date = this.dateNow.getDate();
		this.textView.setText(this.date);
		this.sureButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				SQLiteDatabase localSqLiteDatabase = new SqliteHelper(
						EditActivity.this.context, null,
						null, 0)
						.getWritableDatabase();
				Note localNote = new Note();
				SqliteUtil localSqliteUtil = new SqliteUtil();
				String strContent = EditActivity.this.editText.getText()
						.toString();
				if (strContent.equals("")) {
					Toast.makeText(EditActivity.this.context, "内容为空",
							Toast.LENGTH_SHORT).show();
					return;
				}
				String strTitle = strContent.length() > 11 ? " "
						+ strContent.substring(0, 11) : strContent;
				localNote.setContent(strContent);
				localNote.setTitle(strTitle);
				localNote.setdata(date);
				localNote.setid(id);
				System.out.println("-----id-----id=" + id);
				localSqliteUtil.update(localSqLiteDatabase, localNote);
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


