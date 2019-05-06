package com.example.goodnightnote.utils;
/**

 *Time:2019/04/23
 *Author: zuosc
 *Description:对用户表的CRUD,在他类中进行调用

 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserTableUtil {

	private final static String TABLE = "table_user";
	private final static String USERNAME = "username";
	private final static String PASSWORD = "password";
	private SQLiteDatabase sqLiteDatabase;

	//增加操作
	public void add(Context context, String username, String password){
		sqLiteDatabase = new SqliteHelper(context,null, null,0 )
				.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(USERNAME, username);
		contentValues.put(PASSWORD, password);
		sqLiteDatabase.insert(TABLE, null, contentValues);
		sqLiteDatabase.close();
	}
	//查找操作
	public Cursor query(Context context, String username){
		sqLiteDatabase = new SqliteHelper(context,null, null,0 )
				.getWritableDatabase();
		//close
		Cursor cursor =
		sqLiteDatabase.query(TABLE, new String[] {PASSWORD},USERNAME + "='"+username+"'",
				null, null, null, null);
		return cursor;
	}
}
