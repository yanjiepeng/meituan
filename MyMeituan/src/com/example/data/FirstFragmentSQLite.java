package com.example.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class FirstFragmentSQLite extends SQLiteOpenHelper {
	public FirstFragmentSQLite(){
		this(null, null, null, 1);
	}
	
	


	public FirstFragmentSQLite(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table firstfragmentlistview(_id integer primary key autoincrement,"
				+ "s_id text, s_name text,s_address text,s_starting text,listviewpic blob)");
		db.execSQL("create table firstfragmentpagerview(_id integer primary key autoincrement,viewpagerpic blob)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
