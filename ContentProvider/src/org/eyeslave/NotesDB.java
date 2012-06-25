package org.eyeslave;

import org.eyeslave.providers.Note.Notes;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;

public class NotesDB {

    /**
     * 
     */
    private NotesDB () {
    }

    private static final NotesDB  instance = new NotesDB ();
    public static final String AUTHORITY = "org.eyeslave.providers.NotesContentProvider";
   
  //  public static final  Uri CONTENT_URI = Uri.parse("content://org.eyeslave.providers.NotesContentProvider"+"/" + "notes");
    
    
    
    
    
    public static NotesDB  getInstance() {
        return instance;
    }

    // adds a new note with a given title and text
    public void addNewNote(ContentResolver contentResolver, String title, String text) {
        ContentValues contentValue = new ContentValues();
        // note that we don't have to add an id as our table set id as autoincrement
        contentValue.put("title", title);
        contentValue.put("note", text);
        contentResolver.insert(Notes.CONTENT_URI, contentValue);
    }
    
    // checks to see if a note with a given title is in our database
    public boolean isNoteInDB(ContentResolver contentResolver, String title) {
        boolean ret = false;
        Cursor cursor = contentResolver.query(Notes.CONTENT_URI, null, "title" + "='" + title + "'", null, null);
        if (null != cursor && cursor.moveToNext()) {
            ret = true;
        }
        cursor.close();
        return ret;
    }

    // get the note text from the title of the note
    public String getTextFromTitle(ContentResolver contentResolver, String title) {
        String ret = "";
        String[] projection = new String[] { "note" };
        Cursor cursor = contentResolver.query(Notes.CONTENT_URI, projection, "title" + "='" + title + "'", null, null);
        if (null != cursor && cursor.moveToNext()) {
            int index = cursor.getColumnIndex("note");
            ret = cursor.getString(index);
        }
        cursor.close();
        return ret;
    }

    public void updateTextFromTitle(ContentResolver contentResolver, String title, String text) {
        ContentValues contentValue = new ContentValues();
        contentValue.put("note", text);
        contentResolver.update(Notes.CONTENT_URI, contentValue, "title" + "='" + title + "'", null);
    }

    // erases all entries in the database
    public void refreshCache(ContentResolver contentResolver) {
        int delete = contentResolver.delete(Notes.CONTENT_URI, null, null);
        System.out.println("DELETED " + delete + " RECORDS FROM CONTACTS DB");
    }

}
