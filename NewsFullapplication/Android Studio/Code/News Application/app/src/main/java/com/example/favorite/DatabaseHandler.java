package com.example.favorite;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
	
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "AddtoFav";
	private static final String TABLE_NAME = "Favorite";
	private static final String KEY_ID = "id";
	private static final String KEY_CATID = "catid";
	private static final String KEY_CID = "cid";
	private static final String KEY_CATEGORYNAME = "categoryname";
 	private static final String KEY_NEWSHEADING = "newsheading";
	private static final String KEY_NEWSIMAGE = "newsimage";
	private static final String KEY_NEWSDESC = "newsdesc";
	private static final String KEY_NEWSDATE = "newsdate";
	 

	public DatabaseHandler(Context context) 
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
			@Override
			public void onCreate(SQLiteDatabase db) 
			{
				String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
						+ KEY_ID + " INTEGER PRIMARY KEY," 
						+ KEY_CATID + " TEXT,"
						+ KEY_CID + " TEXT," 
						+ KEY_CATEGORYNAME + " TEXT,"
 						+ KEY_NEWSHEADING + " TEXT,"
						+ KEY_NEWSIMAGE + " TEXT,"
						+ KEY_NEWSDESC + " TEXT,"
						+ KEY_NEWSDATE + " TEXT"
					 	+ ")";
				db.execSQL(CREATE_CONTACTS_TABLE);		
				
			}

			// Upgrading database
			@Override
			public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
				// TODO Auto-generated method stub
				// Drop older table if existed
						db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

						// Create tables again
						onCreate(db);
			}
			
			//Adding Record in Database
			
			public	void AddtoFavorite(Pojo pj)
			{
				SQLiteDatabase db = this.getWritableDatabase();

				ContentValues values = new ContentValues();
				values.put(KEY_CATID, pj.getCatId());
				values.put(KEY_CID, pj.getCId());
				values.put(KEY_CATEGORYNAME, pj.getCategoryName());
 				values.put(KEY_NEWSHEADING, pj.getNewsHeading());
				values.put(KEY_NEWSIMAGE, pj.getNewsImage());
				values.put(KEY_NEWSDESC, pj.getNewsDesc());
				values.put(KEY_NEWSDATE, pj.getNewsDate());
			 
				// Inserting Row
				db.insert(TABLE_NAME, null, values);
				db.close(); // Closing database connection
				
			}
			
			// Getting All Data
			public List<Pojo> getAllData() 
			{
				List<Pojo> dataList = new ArrayList<Pojo>();
				// Select All Query
				String selectQuery = "SELECT  * FROM " + TABLE_NAME;

				SQLiteDatabase db = this.getWritableDatabase();
				Cursor cursor = db.rawQuery(selectQuery, null);

				// looping through all rows and adding to list
				if (cursor.moveToFirst()) 
				{
					do {
						Pojo contact = new Pojo();
						contact.setId(Integer.parseInt(cursor.getString(0)));
						contact.setCatId(cursor.getString(1));
						contact.setCId(cursor.getString(2));
						contact.setCategoryName(cursor.getString(3));
 						contact.setNewsHeading(cursor.getString(4));
						contact.setNewsImage(cursor.getString(5));
						contact.setNewsDesc(cursor.getString(6));
						contact.setNewsDate(cursor.getString(7));
						 
						// Adding contact to list
						dataList.add(contact);
					} while (cursor.moveToNext());
				}

				// return contact list
				return dataList;
			}
			
		//getting single row
			
			public List<Pojo> getFavRow(String id) 
			{
				List<Pojo> dataList = new ArrayList<Pojo>();
				// Select All Query
				String selectQuery = "SELECT  * FROM " + TABLE_NAME +" WHERE catid="+id;

				SQLiteDatabase db = this.getWritableDatabase();
				Cursor cursor = db.rawQuery(selectQuery, null);

				// looping through all rows and adding to list
				if (cursor.moveToFirst()) 
				{
					do {
						Pojo contact = new Pojo();
						contact.setId(Integer.parseInt(cursor.getString(0)));
						contact.setCatId(cursor.getString(1));
						contact.setCId(cursor.getString(2));
						contact.setCategoryName(cursor.getString(3));
 						contact.setNewsHeading(cursor.getString(4));
						contact.setNewsImage(cursor.getString(5));
						contact.setNewsDesc(cursor.getString(6));
						contact.setNewsDate(cursor.getString(7));
						
						// Adding contact to list
						dataList.add(contact);
					} while (cursor.moveToNext());
				}

				// return contact list
				return dataList;
			}
			
		//for remove favorite
			
			public void RemoveFav(Pojo contact)
			{
			    SQLiteDatabase db = this.getWritableDatabase();
			    db.delete(TABLE_NAME, KEY_CATID + " = ?",
			            new String[] { String.valueOf(contact.getCatId()) });
			    db.close();
			}
			
			public enum DatabaseManager {
				INSTANCE;
				private SQLiteDatabase db;
				private boolean isDbClosed =true;
				DatabaseHandler dbHelper;
				public void init(Context context) {
					dbHelper = new DatabaseHandler(context);
				if(isDbClosed){
				isDbClosed =false;
				this.db = dbHelper.getWritableDatabase();
				}

				}


				public boolean isDatabaseClosed(){
				return isDbClosed;
				}

				public void closeDatabase(){
				if(!isDbClosed && db != null){
				isDbClosed =true;
				db.close();
				dbHelper.close();
				}
				}
			}
}
