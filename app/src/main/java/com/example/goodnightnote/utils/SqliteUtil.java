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

import com.example.goodnightnote.domian.Note;


public class SqliteUtil {

	private final static String TABLE = "table_notepad";

	//增加操作
	public long add(SQLiteDatabase paramSQLiteDatabase, Note paramNote) {
		ContentValues localContentValues = new ContentValues();
		/*
		* param1:列名
		* param2:数据
		* */
		localContentValues.put("title", paramNote.getTitle());
		localContentValues.put("date", paramNote.getdata());
		localContentValues.put("content", paramNote.getContent());
		long l = paramSQLiteDatabase.insert(TABLE, null, localContentValues);
		paramSQLiteDatabase.close();
		return l;
	}

	//删除操作
	public void delete(SQLiteDatabase paramSQLiteDatabase, Note paramNote) {
		paramSQLiteDatabase.delete(TABLE, "id=" + paramNote.getid(), null);
		paramSQLiteDatabase.close();
	}

	//查找操作
	public ArrayList<Note> query(SQLiteDatabase paramSQLiteDatabase) {
		ArrayList<Note> localArrayList = new ArrayList<Note>();
		Cursor localCursor = paramSQLiteDatabase.query(TABLE, new String[] {
						"id", "title", "content", "date" }, null,
				null, null, null,
				null);
		while (true) {
			if (!localCursor.moveToNext()) {
				paramSQLiteDatabase.close();
				return localArrayList;
			}
			Note localNote = new Note();
			localNote.setid(localCursor.getString(localCursor
					.getColumnIndex("id")));
			localNote.setTitle(localCursor.getString(localCursor
					.getColumnIndex("title")));
			localNote.setContent(localCursor.getString(localCursor
					.getColumnIndex("content")));
			localNote.setdata(localCursor.getString(localCursor
					.getColumnIndex("date")));
			localArrayList.add(localNote);
		}
	}

	//修改操作
	public void update(SQLiteDatabase paramSQLiteDatabase, Note paramNote) {
		ContentValues localContentValues = new ContentValues();
		localContentValues.put("title", paramNote.getTitle());
		localContentValues.put("content", paramNote.getContent());
		localContentValues.put("date", paramNote.getdata());
		paramSQLiteDatabase.update(TABLE, localContentValues, "id="
				+ paramNote.getid(), null);
		paramSQLiteDatabase.close();
	}
}
