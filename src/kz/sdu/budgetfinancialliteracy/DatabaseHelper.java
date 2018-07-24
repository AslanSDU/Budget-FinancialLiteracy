package kz.sdu.budgetfinancialliteracy;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "bank";
	private static final String TABLE = "save";
	private static final String[] sort = { "day", "month", "year" };

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + TABLE
				+ "(id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "money INTEGER,forwhat INTEGER,type INTEGER,"
				+ "account INTEGER,day INTEGER,"
				+ "month INTEGER,year INTEGER)");
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	public void add(ForDB f) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		Log.d("ADD", "Money=" + f.getMoney() + ", For=" + f.getForwhat()
				+ ", Type=" + f.getType() + ", Account=" + f.getAccount()
				+ ", Day=" + f.getDay() + ", Month=" + f.getMonth() + ", Year="
				+ f.getYear());
		values.put("money", f.getMoney());
		values.put("forwhat", f.getForwhat());
		values.put("type", f.getType());
		values.put("account", f.getAccount());
		values.put("day", f.getDay());
		values.put("month", f.getMonth());
		values.put("year", f.getYear());

		db.insert(TABLE, null, values);
		db.close();
	}

	public ArrayList<ForDB> get(int type, int account, int by, int value) {
		SQLiteDatabase db = this.getWritableDatabase();
		ArrayList<ForDB> list = new ArrayList<ForDB>();
		Cursor c = db.rawQuery("SELECT * FROM " + TABLE + " WHERE type=" + type
				+ " AND account=" + account + " AND " + sort[by] + "=" + value,
				null);
		if (c.moveToFirst()) {
			do {
				Log.d("GIVE", "Money=" + c.getInt(1) + ", For=" + c.getInt(2)
						+ ", Type=" + c.getInt(3) + ", Account=" + c.getInt(4)
						+ ", Day=" + c.getInt(5) + ", Month=" + c.getInt(6)
						+ ", Year=" + c.getInt(7));
				list.add(new ForDB(c.getInt(1), c.getInt(2), c.getInt(3), c
						.getInt(4), c.getInt(5), c.getInt(6), c.getInt(7)));
			} while (c.moveToNext());
		}
		db.close();

		return list;
	}
}
