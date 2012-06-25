package org.eyeslave.providers;

import java.sql.SQLException;
import java.util.HashMap;

import org.eyeslave.database.DatabaseHelper;
import org.eyeslave.database.NotesTable;
import org.eyeslave.providers.Note.Notes;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class NotesContentProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher;
    private static final int NOTES = 1;
    private static HashMap<String, String> notesProjectionMap;

    public static final String AUTHORITY = "org.eyeslave.providers.NotesContentProvider";
    //public static final Uri CONTENT_URI = Uri.parse("content://");

    private DatabaseHelper dbHelper;

    @Override
    public int delete(Uri uri, String where, String[] whereArgs) {
	SQLiteDatabase db = dbHelper.getWritableDatabase();
	int count;
	switch (sUriMatcher.match(uri)) {
	case NOTES:
	    count = db.delete(NotesTable.TABLE_NOTES, where, whereArgs);
	    break;

	default:
	    throw new IllegalArgumentException("Unknown URI " + uri);
	}

	getContext().getContentResolver().notifyChange(uri, null);
	return count;
    }

    @Override
    public String getType(Uri uri) {

	return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues initialValues) {
	if (sUriMatcher.match(uri) != NOTES) {
	    
	    System.out.println("Insert not possible");
	    throw new IllegalArgumentException("Unknown URI " + uri);
	    
	}

	ContentValues values;
	if (initialValues != null) {
	    values = new ContentValues(initialValues);
	} else {
	    values = new ContentValues();
	}

	SQLiteDatabase db = dbHelper.getWritableDatabase();
	long rowId = db.insert(NotesTable.TABLE_NOTES, NotesTable.TABLE_NOTES, values);
	if (rowId > 0) {
	    Uri noteUri = ContentUris.withAppendedId(Notes.CONTENT_URI, rowId);
	    getContext().getContentResolver().notifyChange(noteUri, null);
	    return noteUri;
	}

	try {
	    throw new SQLException("Failed to insert row into " + uri);
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return null;
    }

    @Override
    public boolean onCreate() {
	dbHelper = new DatabaseHelper(getContext());
	return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
	SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

	switch (sUriMatcher.match(uri)) {
	case NOTES:
	    qb.setTables(NotesTable.TABLE_NOTES);
	    qb.setProjectionMap(notesProjectionMap);
	    break;

	default:
	    throw new IllegalArgumentException("Unknown URI " + uri);
	}

	SQLiteDatabase db = dbHelper.getReadableDatabase();
	Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);

	c.setNotificationUri(getContext().getContentResolver(), uri);
	return c;
    }

    @Override
    public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {
	SQLiteDatabase db = dbHelper.getWritableDatabase();
	int count;
	switch (sUriMatcher.match(uri)) {
	case NOTES:
	    count = db.update(NotesTable.TABLE_NOTES, values, where, whereArgs);
	    break;

	default:
	    throw new IllegalArgumentException("Unknown URI " + uri);
	}

	getContext().getContentResolver().notifyChange(uri, null);
	return count;
    }

    static {
	sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	sUriMatcher.addURI(AUTHORITY, NotesTable.TABLE_NOTES, NOTES);
	
	notesProjectionMap = new HashMap<String, String>();
	notesProjectionMap.put(NotesTable.COLUMN_ID, NotesTable.COLUMN_ID);
	notesProjectionMap.put(NotesTable.COLUMN_TITLE, NotesTable.COLUMN_TITLE);
	notesProjectionMap.put(NotesTable.COLUMN_NOTE, NotesTable.COLUMN_NOTE);

    }
}
