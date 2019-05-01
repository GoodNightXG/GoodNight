package com.example.goodnightnote.utils;
/**

 *Time:2019/04/23
 *Author: zuosc
 *Description:对用户表的CRUD,在他类中进行调用

 */

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class UserTableUtil {

	private final static String TABLE = "table_user";

	//增加操作
	public void add(SQLiteDatabase sqLiteDatabase, String username, String password){
		ContentValues contentValues = new ContentValues();
		contentValues.put("username", username);
		contentValues.put("password", password);
		sqLiteDatabase.insert(TABLE, null, contentValues);
		sqLiteDatabase.close();
	}
	//查找操作
	public Cursor query(SQLiteDatabase sqLiteDatabase, String username){
		Cursor cursor =
		sqLiteDatabase.query(TABLE, new String[] {"password"},"username='"+username+"'",
				null, null, null, null);
		return cursor;
	}
	//删除操作
	public void delete(SQLiteDatabase sqLiteDatabase, String username) {
		sqLiteDatabase.execSQL("delete from table_user where username is ?" , new String[]{username});
		sqLiteDatabase.close();

	}
}
