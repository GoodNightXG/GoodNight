package com.example.goodnightnote.adapter;
/**

 *Time:2019/04/18
 *Author: xiaoxi
 *Description:
 */
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
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
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.goodnightnote.R;
import com.example.goodnightnote.activity.EditActivity;
import com.example.goodnightnote.activity.MainActivity;
import com.example.goodnightnote.activity.RingActivity;
import com.example.goodnightnote.activity.WriteActivity;
import com.example.goodnightnote.domian.Note;
import com.example.goodnightnote.login.LoginActivity;
import com.example.goodnightnote.utils.SqliteHelper;
import com.example.goodnightnote.utils.SqliteUtil;
import com.example.goodnightnote.view.TextViewLine;

public class NoteAdapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater mInflater;
	private ArrayList<Map<String, Object>> mList;
	private TextView mContentView;
	private TextView mDateView;
	private TextViewLine mLineContentView;
	private TextView mExDateView;
	private Button mStyleButtonWrite;
	private Button mStyleButtonDelete;
	private Button mShowButtonWrite;
	private Button mShowButtonDelete;
	private Button mAlarmButton;
	private Calendar mCalendar;
	private AlarmManager mAlarmManager;

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
		Map<String, Object> map = mList.get(arg0);
		this.mCalendar = Calendar.getInstance();
		mAlarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
		// 取出map中的展开标记，根据EXPANDED决定适配哪种View
		boolean boo = (Boolean) map.get("EXPANDED");
		if (!boo) {
			arg1 = mInflater.inflate(R.layout.showtypes, arg2, false);
			mContentView =  arg1.findViewById(R.id.contentTextView);
			mDateView = (TextView) arg1.findViewById(R.id.dateTextView);
			String str = (String) mList.get(arg0).get("titleItem");
			String dateStr = (String) mList.get(arg0).get("dateItem");
			String label = (String) mList.get(arg0).get("typeItem");
			mContentView.setText(str);
			setBack(mContentView, label);
			mDateView.setText(dateStr);
			mAlarmButton = arg1.findViewById(R.id.bt_alarm);
			mShowButtonWrite = arg1.findViewById(R.id.smallbutton1);
			mShowButtonDelete = arg1.findViewById(R.id.smallbutton2);
			mAlarmButton.setOnClickListener(new AlarmButtonListener(arg0));
			mShowButtonWrite.setOnClickListener(new WriteButtonListener(arg0));
			mShowButtonDelete.setOnClickListener(new DeleteButtonListener(arg0));
		} else {
			arg1 = mInflater.inflate(R.layout.style, arg2, false);
			mLineContentView = (TextViewLine) arg1
					.findViewById(R.id.changecontentview);
			mExDateView = (TextView) arg1
					.findViewById(R.id.changedateview);
			String str = (String) mList.get(arg0).get("contentItem");
			String dateStr = (String) mList.get(arg0).get("dateItem");
			String label = (String) mList.get(arg0).get("typeItem");
			mLineContentView.setText(str);
			setBack(mLineContentView, label);
			mExDateView.setText(dateStr);
			mAlarmButton = arg1.findViewById(R.id.bt_alarm);
			mStyleButtonWrite = arg1.findViewById(R.id.stylebutton1);
			mStyleButtonDelete = arg1.findViewById(R.id.stylebutton2);
			mAlarmButton.setOnClickListener(new AlarmButtonListener(arg0));
			mStyleButtonWrite.setOnClickListener(new WriteButtonListener(arg0));
			mStyleButtonDelete.setOnClickListener(new DeleteButtonListener(arg0));
		}
		return arg1;
	}
	//设置提醒
	class AlarmButtonListener implements OnClickListener {
		private int position;
		public AlarmButtonListener(int position) {
				this.position = position;
			}

		@Override
		public void onClick(View v) {
			Calendar calendar=Calendar.getInstance();
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH);
			int day = calendar.get(Calendar.DATE);
			int hour=calendar.get(Calendar.HOUR_OF_DAY);
			int minute=calendar.get(Calendar.MINUTE);

			//弹出time对话框
			TimePickerDialog tpd=new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
				@Override
				public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
					mCalendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
					mCalendar.set(Calendar.MINUTE,minute);
//                    Toast.makeText(mContext, "make a alarm", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(mContext, RingActivity.class);
					intent.putExtra("content",(String) mList.get(position).get("contentItem"));
					intent.putExtra("id",(String) mList.get(position).get("idItem"));
					//requestcode如果设置成常数，则设置多个提醒时后设置的会覆盖掉前边的提醒
					PendingIntent pi=PendingIntent.getActivity(mContext,position,intent,0);
					mAlarmManager.setExact(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(),pi);

				}
			},hour,minute,true);
			tpd.show();

			DatePickerDialog dpd = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
				@Override
				public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
					mCalendar.set(Calendar.YEAR, year);
					mCalendar.set(Calendar.MONTH, month);
					mCalendar.set(Calendar.DATE,dayOfMonth);
				}
			}, year, month ,day);
			dpd.show();
			}
		}
	//新建按钮的监听
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
			b.putString("typeItem",(String) mList.get(position).get("typeItem"));
			Intent intent = new Intent((MainActivity) mContext,
					EditActivity.class);
			intent.putExtras(b);
			((MainActivity) mContext).startActivity(intent);
		}
	}
	//删除按钮的监听
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
					note.setmId((String) mList.get(position).get("idItem"));
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
	};
	//根据标签设置notelist的背景颜色
	private void setBack(TextView content, String label){
		if(label == null){
			content.setBackgroundResource(R.color.fcolor);
		}else {
			switch (label) {
				case "0":
					content.setBackgroundResource(R.color.fcolor);
					break;
				case "1":
					content.setBackgroundResource(R.color.red);
					break;
				case "2":
					content.setBackgroundResource(R.color.blue);
					break;
				case "3":
					content.setBackgroundResource(R.color.green);
					break;
				default:
					break;
			}
		}
	}
}
