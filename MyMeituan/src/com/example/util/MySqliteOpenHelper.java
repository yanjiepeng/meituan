package com.example.util;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MySqliteOpenHelper extends SQLiteOpenHelper {
	private Context context;

	public MySqliteOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		System.out.println("onCreate");
		db.execSQL("drop table if exists 'data_customer'");
		String sql = "create table data_customer(_id integer primary key autoincrement, b_id text, u_name text,u_address text,b_price text,b_status text,f_name text,b_time text,u_tele text,s_name text)";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
