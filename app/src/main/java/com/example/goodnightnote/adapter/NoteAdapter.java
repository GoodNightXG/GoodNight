package com.example.goodnightnote.adapter;
/**

 *Time:2019/04/18
 *Author: xiaoxi
 *Description:
 */
import java.util.ArrayList;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.goodnightnote.R;
import com.example.goodnightnote.activity.EditActivity;
import com.example.goodnightnote.activity.MainActivity;
import com.example.goodnightnote.domian.Note;
import com.example.goodnightnote.login.LoginActivity;
import com.example.goodnightnote.utils.SqliteHelper;
import com.example.goodnightnote.utils.SqliteUtil;
import com.example.goodnightnote.view.TextViewLine;

public class NoteAdapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater mInflater;
	private ArrayList<Map<String, Object>> mList;
	public NoteAdapter(Activity activity, ArrayList<Map<String, Object>> list) {

		this.mContext = activity;
		this.mList = list;
		mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	//回显数据
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// 此处在去掉if语句之后，刷新语句重新生效
		SetShow setShow = new SetShow();
		// 取出map中的展开标记
		Map<String, Object> map = mList.get(arg0);
		boolean boo = (Boolean) map.get("EXPANDED");
		if (!boo) {
			arg1 = mInflater.inflate(R.layout.showtypes, arg2, false);
			setShow.contentView = (TextView) arg1
					.findViewById(R.id.contentTextView);
			setShow.dateView = (TextView) arg1.findViewById(R.id.dateTextView);
			String str = (String) mList.get(arg0).get("titleItem");
			String dateStr = (String) mList.get(arg0).get("dateItem");
			setShow.contentView.setText(str);
			setShow.dateView.setText(dateStr);
			setShow.showButtonWrite = (Button) arg1
					.findViewById(R.id.smallbutton1);
			setShow.showButtonDelete = (Button) arg1
					.findViewById(R.id.smallbutton2);
			setShow.showButtonWrite.setOnClickListener(new WriteButtonListener(
					arg0));
			setShow.showButtonDelete
					.setOnClickListener(new DeleteButtonListener(arg0));
		} else {
			arg1 = mInflater.inflate(R.layout.style, arg2, false);
			setShow.cContentView = (TextViewLine) arg1
					.findViewById(R.id.changecontentview);
			setShow.cDateView = (TextView) arg1
					.findViewById(R.id.changedateview);
			String str = (String) mList.get(arg0).get("contentItem");
			String dateStr = (String) mList.get(arg0).get("dateItem");
			setShow.cContentView.setText("" + str);
			setShow.cDateView.setText(dateStr);
			setShow.styleButtonWrite = (Button) arg1
					.findViewById(R.id.stylebutton1);
			setShow.styleButtonWrite
					.setOnClickListener(new WriteButtonListener(arg0));
			setShow.styleButtonDelete = (Button) arg1
					.findViewById(R.id.stylebutton2);
			setShow.styleButtonDelete
					.setOnClickListener(new DeleteButtonListener(arg0));
		}
		return arg1;
	}

	class WriteButtonListener implements OnClickListener {
		private int position;

		public WriteButtonListener(int position) {
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			Bundle b = new Bundle();
			b.putString("contentItem", (String) mList.get(position).get("contentItem"));
			b.putString("dateItem", (String) mList.get(position).get("dateItem"));
			b.putString("idItem", (String) mList.get(position).get("idItem"));
			Intent intent = new Intent((MainActivity) mContext,
					EditActivity.class);
			intent.putExtras(b);
			((MainActivity) mContext).startActivity(intent);
		}
	}

	class DeleteButtonListener implements OnClickListener {
		private int position;

		public DeleteButtonListener(int position) {
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			Builder builder = new Builder(mContext);
			builder.setTitle(R.string.enter_delete);
			builder.setPositiveButton(R.string.ok,
					new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int i) {
					SqliteHelper sql = new SqliteHelper(mContext, null,
							null, 0);
					SQLiteDatabase dataBase = sql.getWritableDatabase();
					SqliteUtil change = new SqliteUtil();
					Note note = new Note();
					note.setmId((String) mList.get(position).get(
							"idItem"));
					change.delete(dataBase, note);
					// 此处调用activity里的方法需逐渐向上转型
					((MainActivity) mContext).showUpdate();
						}
					});
			builder.setNegativeButton(R.string.cancel,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
			builder.create();
			builder.show();
		}
	}

	class SetShow {
		public TextView contentView;
		public TextView dateView;
		public TextViewLine cContentView;
		public TextView cDateView;
		public Button styleButtonWrite;
		public Button styleButtonDelete;
		public Button showButtonWrite;
		public Button showButtonDelete;
	}

}
