package com.dbtest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ankit on 08/03/16.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "wordsManager";

    // Contacts table name
    private static final String TABLE_WORDS = "words";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_RATIO = "ratio";
    private static final String KEY_MEANING = "meaning";
    private static final String KEY_VARIANT = "variant";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_WORDS + "("
                + KEY_ID + " TEXT," + KEY_NAME + " TEXT,"
                + KEY_RATIO + " TEXT," + KEY_MEANING + " TEXT," + KEY_VARIANT + " TEXT" +")";


        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORDS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    void addContact(Words words) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, words.getId()); // Contact Name
        values.put(KEY_NAME, words.getWord()); // Contact Phone
        values.put(KEY_MEANING, words.getMeaning()); // Contact Name
        values.put(KEY_RATIO, words.getRatio()); // Contact Phone
        values.put(KEY_VARIANT, words.getVariant()); // Contact Name
        // Inserting Row
        db.insert(TABLE_WORDS, null, values);
        db.close(); // Closing database connection
    }


    // Getting All Contacts
    public List<Words> getAllContacts() {
        List<Words> contactList = new ArrayList<Words>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_WORDS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Words words = new Words();
                words.setId(cursor.getString(0));
                words.setWord(cursor.getString(1));
                words.setRatio(cursor.getString(2));
                words.setMeaning(cursor.getString(3));
                words.setVariant(cursor.getString(4));

                // Adding contact to list
                contactList.add(words);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }


    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_WORDS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        // return count
        int i = cursor.getCount();
        cursor.close();
        return i;
    }

}