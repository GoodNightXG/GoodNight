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
	private Button mAlarmButton;
	private Calendar mCalendar;
	private AlarmManager mAlarmManager;
	private PendingIntent mPendingIntent;
	private SqliteUtil mSqliteUtil;
	private final static String DATEITEM = "dateItem";
	private final static String CONTENTITEM = "contentItem";
	private final static String IDITEM = "idItem";
	private final static String TYPEITEM = "typeItem";
	private final static String EXPANDED = "EXPANDED";
	private final static String CONTENT = "content";
	private final static String ID = "id";


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
		mSqliteUtil = new SqliteUtil();
		Map<String, Object> map = mList.get(arg0);
		this.mCalendar = Calendar.getInstance();
		mAlarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
		String content = (String) mList.get(arg0).get(CONTENTITEM);
		String date = (String) mList.get(arg0).get(DATEITEM);
		String label =  (String) mList.get(arg0).get(TYPEITEM);

		// 取出map中的展开标记，根据EXPANDED决定适配哪种View
		boolean boo = (Boolean) map.get(EXPANDED);
		if (!boo) {
			arg1 = mInflater.inflate(R.layout.showtypes, arg2, false);
			mContentView =  arg1.findViewById(R.id.contentTextView);
			mDateView = arg1.findViewById(R.id.dateTextView);
			mContentView.setText(content);
			mDateView.setText(date);
			setBack(mContentView, label);
		} else {
			arg1 = mInflater.inflate(R.layout.style, arg2, false);
			mLineContentView = arg1.findViewById(R.id.changecontentview);
			mExDateView = arg1.findViewById(R.id.changedateview);
			mLineContentView.setText(content);
			mExDateView.setText(date);
			setBack(mLineContentView, label);
		}
			mAlarmButton = arg1.findViewById(R.id.bt_alarm);
			mStyleButtonWrite = arg1.findViewById(R.id.bt_edit);
			mStyleButtonDelete = arg1.findViewById(R.id.bt_delete);
			mAlarmButton.setOnClickListener(new AlarmButtonListener(arg0));
			mAlarmButton.setOnLongClickListener(new AlarmButtonLongListener(mPendingIntent));
			mStyleButtonWrite.setOnClickListener(new WriteButtonListener(arg0));
			mStyleButtonDelete.setOnClickListener(new DeleteButtonListener(arg0));
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
					mCalendar.set(Calendar.SECOND, 0);

					Toast.makeText(mContext, R.string.alarmhint, Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(mContext, RingActivity.class);
					intent.putExtra(CONTENT,(String) mList.get(position).get(CONTENTITEM));
					intent.putExtra(ID,(String) mList.get(position).get(IDITEM));
					//requestcode如果设置成常数，则为多个事项设置提醒时后设置的会覆盖掉前边的提醒
					//flags值为0，即FLAG_CANCAL_CURRENT，对参数的操作位
					mPendingIntent = PendingIntent.getActivity(mContext,position,intent,0);
					mAlarmManager.setExact(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(),mPendingIntent);
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

	//取消提醒
	class AlarmButtonLongListener implements View.OnLongClickListener {
		private PendingIntent pendingIntent;
		public AlarmButtonLongListener(PendingIntent pendingIntent) {
			this.pendingIntent = pendingIntent;
		}
		@Override
		public boolean onLongClick(View v) {
			if (pendingIntent == null) {
				Toast.makeText(mContext, R.string.alarmnotset, Toast.LENGTH_SHORT).show();
				return true;
			}else {
				mAlarmManager.cancel(mPendingIntent);
				Toast.makeText(mContext, R.string.alarmcancel, Toast.LENGTH_SHORT).show();
				return true;
			}
		}
	}

	//编辑按钮的监听
	class WriteButtonListener implements OnClickListener {
		private int position;
		public WriteButtonListener(int position) {
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			Bundle b = new Bundle();
			b.putString(CONTENTITEM, (String) mList.get(position).get(CONTENTITEM));
			b.putString(DATEITEM, (String) mList.get(position).get(DATEITEM));
			b.putString(IDITEM, (String) mList.get(position).get(IDITEM));
			b.putString(TYPEITEM, (String) mList.get(position).get(TYPEITEM));
			Intent intent = new Intent(mContext, EditActivity.class);
			intent.putExtras(b);
			mContext.startActivity(intent);
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
			builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int i) {
					//删除note后提醒失效
					if(mPendingIntent != null) {
						mAlarmManager.cancel(mPendingIntent);
						Toast.makeText(mContext, R.string.alarmcancel, Toast.LENGTH_SHORT).show();
					}
					Note note = new Note();
					note.setmId((String) mList.get(position).get("idItem"));
					mSqliteUtil.delete(mContext, note);

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
		if (label == null){
			content.setBackgroundResource(R.color.fcolor);
		}else {
			int type = Integer.parseInt(label);
			switch (type) {
				case 0:
					content.setBackgroundResource(R.color.other);
					break;
				case 1:
					content.setBackgroundResource(R.color.red);
					break;
				case 2:
					content.setBackgroundResource(R.color.blue);
					break;
				case 3:
					content.setBackgroundResource(R.color.green);
					break;
				default:
					break;
			}
		}
	}
}
