package com.example.goodnightnote.utils;
/**

 *Time:2019/04/18
 *Author: xiaoxi
 *Description:数据库的CRUD,再其他类中进行调用即可

 */
import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.goodnightnote.domian.Note;

public class SqliteUtil {

	private final static String USER  = "user";
	private final static String TITLE = "title";
	private final static String DATE = "date";
	private final static String CONTENT = "content";
	private final static String ID = "id";
	private final static String TYPE = "type";
	private final static String TABLE = "table_notepad";
	private final static String STYPE = "' and type = '";
	private final static String SSTYPE = "' and type is null";
	private SQLiteDatabase paramSQLiteDatabase;
	//增加操作
	public long add(Context context, Note paramNote) {
		paramSQLiteDatabase = new SqliteHelper(context,null, null,0 )
				.getWritableDatabase();
		ContentValues localContentValues = new ContentValues();
		localContentValues.put(TITLE, paramNote.getmTitle());
		localContentValues.put(DATE, paramNote.getmData());
		localContentValues.put(CONTENT, paramNote.getmContent());
        localContentValues.put(TYPE, paramNote.getmType());
        localContentValues.put(USER, paramNote.getmUser());
		long l = paramSQLiteDatabase.insert(TABLE, null, localContentValues);
		paramSQLiteDatabase.close();
		return l;
	}

	//删除操作
	public void delete(Context context, Note paramNote) {
		paramSQLiteDatabase = new SqliteHelper(context,null, null,0 )
				.getWritableDatabase();
		paramSQLiteDatabase.delete(TABLE, ID + "=" + paramNote.getmId(), null);
		paramSQLiteDatabase.close();
	}
	public void delete(Context context, String user) {
		paramSQLiteDatabase = new SqliteHelper(context,null, null,0 )
				.getWritableDatabase();
		paramSQLiteDatabase.delete(TABLE, USER + "='" + user +"'", null);
	}

	//查找操作
	public ArrayList<Note> query(Context context, String username) {
		paramSQLiteDatabase = new SqliteHelper(context,null, null,0 )
				.getWritableDatabase();
		ArrayList<Note> localArrayList = new ArrayList<Note>();
		Cursor localCursor = paramSQLiteDatabase.query(TABLE, new String[] {ID, TITLE, CONTENT, DATE, TYPE},
				USER + "='"+username+"'",null, null,null,
				null);
		return queryUtil(localCursor, localArrayList);
	}

	public ArrayList<Note> query(Context context, String username ,Long selectedType) {
		paramSQLiteDatabase = new SqliteHelper(context,null, null,0 )
				.getWritableDatabase();
		ArrayList<Note> localArrayList = new ArrayList<Note>();
		Cursor localCursor = paramSQLiteDatabase.query(TABLE, new String[] {ID, TITLE, CONTENT, DATE, TYPE},
				USER + "='"+username+ STYPE +selectedType+"'",
				null, null,null,
				null);
		return queryUtil(localCursor, localArrayList);
	}
	//查找未设置标签的note
	public ArrayList<Note> queryEmpty(Context context, String username ,Long selectedType) {
		paramSQLiteDatabase = new SqliteHelper(context,null, null,0 )
				.getWritableDatabase();
		ArrayList<Note> localArrayList = new ArrayList<Note>();
		Cursor localCursor = paramSQLiteDatabase.query(TABLE, new String[] {
						ID, TITLE, CONTENT, DATE},
				USER + "='"+username+SSTYPE,
				null, null,null,
				null);

		while (localCursor.moveToNext()) {
			Note localNote = new Note();
			localNote.setmId(localCursor.getString(localCursor
					.getColumnIndex(ID)));
			localNote.setmTitle(localCursor.getString(localCursor
					.getColumnIndex(TITLE)));
			localNote.setmContent(localCursor.getString(localCursor
					.getColumnIndex(CONTENT)));
			localNote.setmData(localCursor.getString(localCursor
					.getColumnIndex(DATE)));
			localArrayList.add(localNote);
		}
		return localArrayList;
	}

	public ArrayList<Note> queryUtil(Cursor localCursor, ArrayList<Note> localArrayList){
        while (localCursor.moveToNext()) {
			Note localNote = new Note();
			localNote.setmId(localCursor.getString(localCursor
					.getColumnIndex(ID)));
			localNote.setmTitle(localCursor.getString(localCursor
					.getColumnIndex(TITLE)));
			localNote.setmContent(localCursor.getString(localCursor
					.getColumnIndex(CONTENT)));
			localNote.setmData(localCursor.getString(localCursor
					.getColumnIndex(DATE)));
			localNote.setmType(localCursor.getString(localCursor
					.getColumnIndex(TYPE)));
			localArrayList.add(localNote);
		}
                return localArrayList;
    }
	//修改操作
	public void update(Context context, Note paramNote) {
		paramSQLiteDatabase = new SqliteHelper(context,null, null,0 )
				.getWritableDatabase();
		ContentValues localContentValues = new ContentValues();
		localContentValues.put(TITLE, paramNote.getmTitle());
		localContentValues.put(CONTENT, paramNote.getmContent());
		localContentValues.put(DATE, paramNote.getmData());
		localContentValues.put(TYPE, paramNote.getmType());
		paramSQLiteDatabase.update(TABLE, localContentValues, ID + "="
				+ paramNote.getmId(), null);
		paramSQLiteDatabase.close();
	}
}
