package org.eyeslave.database;

import android.database.sqlite.SQLiteDatabase;

public class NotesTable {

	// Database table
	public static final String TABLE_NOTES = "notes";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_TITLE = "title";
	public static final String COLUMN_NOTE = "note";
	

	// Database creation SQL statement
	private static final String DATABASE_CREATE = "create table " 
			+ TABLE_NOTES
			+ "(" 
			+ COLUMN_ID + " integer primary key autoincrement, " 
			+ COLUMN_TITLE + " text not null, " 
			+ COLUMN_NOTE + " text not null);";

	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
		onCreate(database);
	}
}