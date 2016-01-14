/**
 * Contains our database schema and logic
 * Created 13.01.16
 */

package edu.unitec.data;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "cannables.db";
	private static final String TABLE_NAME = "VEGETABLE";
	//VERSION must be incremented by 1 each time changes are made to the database create string after it has been created the first time
	private static final int DATABASE_VERSION = 1;	
	private static final String UID = "_vegeId";
	private static final String TYPE = "Type";
	private static final String AGE = "Age";
	private static final int WATERLVL = 100;
	private static final int FOODLVL = 100;
	private static final int SHADELVL = 100;
	private static final int THIRSTRATE = 1;
	private static final int HUNGERRATE = 1;
	private static final String PERSONALITY = "Default";
	private static final double SIZE = 100;
	private static final String STATUS = "ALIVE";
	
	public Database (Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public Database (Context context, String dbName, SQLiteDatabase.CursorFactory factory, int version)
	{
		super(context, dbName, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("
				+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
				+TYPE+" VARCHAR(255), "
				+AGE+" INTEGER, "
				+WATERLVL+" INTEGER, "
				+FOODLVL+" INTEGER, "
				+SHADELVL+" INTEGER, "
				+THIRSTRATE+" INTEGER, "
				+HUNGERRATE+" INTEGER,"
				+PERSONALITY+" VARCHAR(255),"
				+SIZE+" INTEGER, "
				+STATUS+" VARCHAR(10));";
		try
		{
			db.execSQL(CREATE_TABLE);
		} catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		//Consider replacing this if we don't want to lose all the data
		String DROP_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME+";";
		
		try{
			db.execSQL(DROP_TABLE);
			onCreate(db);
			
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
}
