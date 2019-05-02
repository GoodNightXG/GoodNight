package com.example.goodnightnote.utils;
/**

 *Time:2019/04/18
 *Author: xiaoxi
 *Description:数据库的CRUD,再其他类中进行调用即可

 */
import java.util.ArrayList;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.goodnightnote.activity.WriteActivity;
import com.example.goodnightnote.domian.Note;

public class SqliteUtil {
	private final static String TABLE = "table_notepad";
	//增加操作
	public long add(SQLiteDatabase paramSQLiteDatabase,Note paramNote) {
		ContentValues localContentValues = new ContentValues();
		localContentValues.put("title", paramNote.getmTitle());
		localContentValues.put("date", paramNote.getmData());
		localContentValues.put("content", paramNote.getmContent());
        localContentValues.put("type", paramNote.getmType());
        localContentValues.put("user", paramNote.getmUser());
		long l = paramSQLiteDatabase.insert(TABLE, null, localContentValues);
		paramSQLiteDatabase.close();
		return l;
	}

	//删除操作
	public void delete(SQLiteDatabase paramSQLiteDatabase, Note paramNote) {
		paramSQLiteDatabase.delete(TABLE, "id=" + paramNote.getmId(), null);
		paramSQLiteDatabase.close();
	}

	//查找操作
	public ArrayList<Note> query(SQLiteDatabase paramSQLiteDatabase, String username) {
		ArrayList<Note> localArrayList = new ArrayList<Note>();
		Cursor localCursor = paramSQLiteDatabase.query(TABLE, new String[] {
						"id", "title", "content", "date","type"},
				"user='"+username+"'",null, null,null,
				null);
		while (true) {
			if (!localCursor.moveToNext()) {
				paramSQLiteDatabase.close();
				return localArrayList;
			}
			Note localNote = new Note();
			localNote.setmId(localCursor.getString(localCursor
					.getColumnIndex("id")));
			localNote.setmTitle(localCursor.getString(localCursor
					.getColumnIndex("title")));
			localNote.setmContent(localCursor.getString(localCursor
					.getColumnIndex("content")));
			localNote.setmData(localCursor.getString(localCursor
					.getColumnIndex("date")));
			localNote.setmType(localCursor.getString(localCursor
					.getColumnIndex("type")));
			localArrayList.add(localNote);
		}
	}
	public ArrayList<Note> query(SQLiteDatabase paramSQLiteDatabase, String username ,Long selectedType) {
		ArrayList<Note> localArrayList = new ArrayList<Note>();
		Cursor localCursor = paramSQLiteDatabase.query(TABLE, new String[] {
						"id", "title", "content", "date","type"},
				"user='"+username+"' and type = '"+selectedType+"'",
				null, null,null,
				null);
		while (true) {
			if (!localCursor.moveToNext()) {
				paramSQLiteDatabase.close();
				return localArrayList;
			}
			Note localNote = new Note();
			localNote.setmId(localCursor.getString(localCursor
					.getColumnIndex("id")));
			localNote.setmTitle(localCursor.getString(localCursor
					.getColumnIndex("title")));
			localNote.setmContent(localCursor.getString(localCursor
					.getColumnIndex("content")));
			localNote.setmData(localCursor.getString(localCursor
					.getColumnIndex("date")));
			localNote.setmType(localCursor.getString(localCursor
					.getColumnIndex("type")));
			localArrayList.add(localNote);
		}
	}
	public ArrayList<Note> queryEmpty(SQLiteDatabase paramSQLiteDatabase, String username ,Long selectedType) {
		ArrayList<Note> localArrayList = new ArrayList<Note>();
		Cursor localCursor = paramSQLiteDatabase.query(TABLE, new String[] {
						"id", "title", "content", "date","type"},
				"user='"+username+"' and type = '"+selectedType+"' or type is null",
				null, null,null,
				null);
		while (true) {
			if (!localCursor.moveToNext()) {
				paramSQLiteDatabase.close();
				return localArrayList;
			}
			Note localNote = new Note();
			localNote.setmId(localCursor.getString(localCursor
					.getColumnIndex("id")));
			localNote.setmTitle(localCursor.getString(localCursor
					.getColumnIndex("title")));
			localNote.setmContent(localCursor.getString(localCursor
					.getColumnIndex("content")));
			localNote.setmData(localCursor.getString(localCursor
					.getColumnIndex("date")));
			localNote.setmType(localCursor.getString(localCursor
					.getColumnIndex("type")));
			localArrayList.add(localNote);
		}
	}

	//修改操作
	public void update(SQLiteDatabase paramSQLiteDatabase, Note paramNote) {
		ContentValues localContentValues = new ContentValues();
		localContentValues.put("title", paramNote.getmTitle());
		localContentValues.put("content", paramNote.getmContent());
		localContentValues.put("date", paramNote.getmData());
		localContentValues.put("type", paramNote.getmType());
		paramSQLiteDatabase.update(TABLE, localContentValues, "id="
				+ paramNote.getmId(), null);
		paramSQLiteDatabase.close();
	}
}
