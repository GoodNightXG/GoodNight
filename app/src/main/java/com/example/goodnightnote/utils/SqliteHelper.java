package com.example.goodnightnote.utils;
/**

 *Time:2019/04/18
 *Author: xiaoxi
 *Description:数据库操作的基础类
 * SQLiteOpenHelper使用说明：
 * 1.SQLiteOpenHelper是一个抽象类，需要创建一个帮助类继承SQLiteOpenHelper
 * 2.重写onCreate()和onUpgrade()方法

 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteHelper extends SQLiteOpenHelper {

	  private final static String DATABASE_NAME = "myluckdatabase.db";
	  private final static String NAME = "table_notepad";
	  private final static String USER_TABLE  = "table_user";
	  private final static int VERSION = 1;

	  //拼写SQL语句
	  private static final String CREATE_USER = "create table "+USER_TABLE
			+" (username TEXT,password TEXT)";
      private static final String CREARE_NOTE = "create table "+NAME
	        +" (id INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT,date TEXT,"
			  +"content TEXT,type INTEGER,user TEXT)";


	  //构造函数
	  public SqliteHelper(Context paramContext, String paramString,
                          SQLiteDatabase.CursorFactory paramCursorFactory, int paramInt) {
	    super(paramContext, DATABASE_NAME, null, VERSION);
	  }

	  @Override
	  public void onCreate(SQLiteDatabase paramSQLiteDatabase) {
	  	//执行SQL语句
	    paramSQLiteDatabase.execSQL(CREATE_USER);
		paramSQLiteDatabase.execSQL(CREARE_NOTE);
	  }

	  @Override
	  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	  }
}
