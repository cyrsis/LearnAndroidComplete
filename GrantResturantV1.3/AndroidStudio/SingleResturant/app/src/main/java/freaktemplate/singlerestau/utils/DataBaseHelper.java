package freaktemplate.singlerestau.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper {

	private static String DB_PATH = "/data/data/freaktemplate.singlerestau/databases/";

	private static String DB_NAME = "Res.sqlite";

	private SQLiteDatabase myDataBase;

	private final Context myContext;

	public DataBaseHelper(Context context) {

		super(context, DB_NAME, null, 1);
		this.myContext = context;
	}

	public void createDataBase() throws IOException {

		boolean dbExist = checkDataBase();

		if (dbExist) {

		} else {
			this.getReadableDatabase();

			try {

				copyDataBase();

			} catch (IOException e) {
				Log.d("Error", "Copy Database");
				throw new Error("Error copying database" + e);

			}
		}
	}

	private boolean checkDataBase() {

		File dbFile = new File(DB_PATH + DB_NAME);
		Log.d("db", "" + dbFile);
		return dbFile.exists();
	}

	@Override
	public synchronized SQLiteDatabase getReadableDatabase() {
		return super.getReadableDatabase();
	}

	@Override
	public synchronized SQLiteDatabase getWritableDatabase() {
		// TODO Auto-generated method stub
		return super.getWritableDatabase();
	}

	private void copyDataBase() throws IOException {
		InputStream myInput = myContext.getAssets().open(DB_NAME);
		String outFileName = DB_PATH + DB_NAME;
		OutputStream myOutput = new FileOutputStream(outFileName);
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}

		myOutput.flush();
		myOutput.close();
		myInput.close();
	}

	public void openDataBase() throws SQLException {

		String myPath = DB_PATH + DB_NAME;
		myDataBase = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READONLY
						| SQLiteDatabase.NO_LOCALIZED_COLLATORS);

	}

	@Override
	public synchronized void close() {

		if (myDataBase != null)
			myDataBase.close();

		super.close();

	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	
	/*public Cursor getSearchData(String name) {
		String QUERY = "select * from en_to_hi where sr_word LIKE '" + name+ "%'limit 20;";
		// String QUERY = "select * from en_to_hi where sr_word LIKE '" + name +
		// "%';";
		Log.d("QUERY", "" + QUERY);
		Cursor cur = myDataBase.rawQuery(QUERY, null);
		if (cur != null) {
			Log.d("NEWDATA", "" + cur);
			cur.moveToFirst();
		}

		return cur;
	}*/

	/*public Cursor getSearchData1(int name, int name1) {
		String QUERY = "select * from en_to_hi where id >=" + name
				+ " and id<=" + name1 + ";";
		// String QUERY = "select * from en_to_hi where sr_word LIKE '" + name +
		// "%';";
		Log.d("QUERY", "" + QUERY);
		Cursor cur = myDataBase.rawQuery(QUERY, null);
		if (cur != null) {
			Log.d("NEWDATA", "" + cur);
			cur.moveToFirst();
		}

		return cur;
	}
*/
	/*public Cursor getNotes() {
		// TODO Auto-generated method stub
		String QUERY = "select * from recent";

		Log.d("QUERY", "" + QUERY);
		Cursor cur = myDataBase.rawQuery(QUERY, null);
		if (cur != null) {
			Log.d("NEWDATA", "" + cur);
			cur.moveToFirst();
		}

		return cur;
	}*/

	/*public Cursor getday() {
		// TODO Auto-generated method stub
		String QUERY = "select * from en_to_hi order by random() limit 1 ;";

		Log.d("QUERY", "" + QUERY);
		Cursor cur = myDataBase.rawQuery(QUERY, null);
		if (cur != null) {
			Log.d("NEWDATA", "" + cur);
			cur.moveToFirst();
		}

		return cur;
	}*/

	/*public Cursor getFavourite1() throws SQLException {
		Cursor cur;
		cur = myDataBase.rawQuery("select * from en_to_hi where favourite=1;",
				null);
		return cur;

	}*/
}