package com.example.goodnightnote.utils;
/**

 *Time:2019/04/23
 *Author: zuosc
 *Description:对用户表的CRUD,在他类中进行调用

 */

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class UserTableUtil {

	public static String table = "table_user";

	//增加操作
	public void add(SQLiteDatabase sqLiteDatabase, String username, String password){
		String sql = "insert into "+table+"(username, password) values ('"+username+"','"+password+"')";
		sqLiteDatabase.execSQL(sql);
		sqLiteDatabase.close();
	}
	//查找操作
	public Cursor query(SQLiteDatabase sqLiteDatabase, String username){
		String sql = "select password from "+table+" where username = '"+ username+"'";
		Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
		return cursor;
	}
	//删除操作
	public void delete(SQLiteDatabase sqLiteDatabase, String username) {
		sqLiteDatabase.execSQL("delete from table_user where username is ?" , new String[]{username});
		sqLiteDatabase.close();

	}
}
