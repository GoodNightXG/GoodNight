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

	  private static String INFONAME;
	  private static String NAME;
	  private static int VERSION = 1;

	  static
	  {
	    NAME = " table_notepad";
	    INFONAME = "notepad.db";
	  }
	  /*
	  * SQL 语句
	  * create table NAME (
	  *   id INTEGER PRIMARY KEY AUTOINCREMENT,
	  *   title TEXT,
	  *   date TEXT,
	  *   content TEXT)
	  *   此条SQL语句会创建一张table_notepad表
	  * */
	  //拼写SQL语句
      public static final String CREARE_NOTE = "create table"+NAME
	        +"(id INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT,date TEXT,content TEXT)";


	  //构造函数
	  public SqliteHelper(Context paramContext, String paramString,
						  SQLiteDatabase.CursorFactory paramCursorFactory, int paramInt)
	  {
	    super(paramContext, INFONAME, null, VERSION);
	  }


	  public void onCreate(SQLiteDatabase paramSQLiteDatabase)
	  {
	  	//执行SQL语句
	    paramSQLiteDatabase.execSQL(CREARE_NOTE);
	  }

	  public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
	  {
	  	//
	  }
}
