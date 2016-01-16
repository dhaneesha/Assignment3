/**
 * Contains our database methods and an inner database helper class for the schema and logic
 * @author 	Lance Donnell and Dhaneesha Rajakaruna
 * @version 1.1
 * @since 	2016-01-13
 */

package edu.unitec.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database{
	
	DatabaseHelper helper = null;
	
	public Database(Context context)
	{
		System.out.println("Database constructor called");
		helper = new DatabaseHelper(context);
	}
	
	/**
	 * An insert method which will insert a new Vegetable into the table
	 * used when a new vegetable avatar is created
	 * @param type			the type of the vegetable
	 * @param age			the age of the vegetable
	 * @param waterLvl		the water level of the vegetable
	 * @param foodLvl		the food level of the vegetable
	 * @param shadeLvl		the shade level of the vegetable
	 * @param thirstRate	the thirst rate of the vegetable
	 * @param hungerRate	the hunger rate of the vegetable
	 * @param personality	the personality of the vegetable
	 * @param size			the size of the vegetable
	 * @param status		the status of the vegetable ('DECEASED', 'LIVING')
	 * @param condition		the condition of the vegetable
	 * @return long			If long id is less than 0 then something went wrong
	 */
	public long insert(String type, int age, int waterLvl, int foodLvl, int shadeLvl, int thirstRate, int hungerRate, String personality, double size, String status, int condition)
	{
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		
		//no need to cast the types here because they are taken from the parameters
		values.put(DatabaseHelper.TYPE, type);
		values.put(DatabaseHelper.AGE, age);
		values.put(DatabaseHelper.WATERLVL, waterLvl);
		values.put(DatabaseHelper.FOODLVL, foodLvl);
		values.put(DatabaseHelper.SHADELVL, shadeLvl);
		values.put(DatabaseHelper.THIRSTRATE, thirstRate);
		values.put(DatabaseHelper.HUNGERRATE, hungerRate);
		values.put(DatabaseHelper.PERSONALITY, personality);
		values.put(DatabaseHelper.SIZE, size);
		values.put(DatabaseHelper.STATUS, status);
		values.put(DatabaseHelper.CONDITION, condition);
		
		long id = db.insert(DatabaseHelper.TABLE_NAME, null, values);
		
		db.close();
		return id;
	}
	
	/**
	 *  updates all living vegetables with their current stats 
	 *  used when the game activity is paused to save information
	 *  all of these values are updated, but not all of them will necessarily change
	 *  even though some may not change, we have decided to change all of them incase we want to add new features in future
	 * @param _vegeID			the id of the vegetable that is being saved (taken from the object)
	 * @param newType			the new type of the vegetable
	 * @param newAge			the new age of the vegetable
	 * @param newWaterLvl		the new water level
	 * @param newFoodLvl		the new food level
	 * @param newShadeLvl		the new shade level
	 * @param newThirstRate		the new thirst rate
	 * @param newHungerRate		the new hunger rate
	 * @param newPersonality	the new personality
	 * @param newSize			the new size
	 * @param newStatus			the new status ('DECEASED', 'LIVING')
	 * @return int				and integer that counts how many rows were updated (should only be 1)
	 */
	public int update(int _vegeID, String newType, int newAge, int newWaterLvl, int newFoodLvl, int newShadeLvl, int newThirstRate, int newHungerRate, String newPersonality, double newSize, String newStatus, int newCondition)
	{
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		
		values.put(DatabaseHelper.TYPE, newType);
		values.put(DatabaseHelper.AGE, newAge);
		values.put(DatabaseHelper.WATERLVL, newWaterLvl);
		values.put(DatabaseHelper.FOODLVL, newFoodLvl);
		values.put(DatabaseHelper.SHADELVL, newShadeLvl);
		values.put(DatabaseHelper.THIRSTRATE, newThirstRate);
		values.put(DatabaseHelper.HUNGERRATE, newHungerRate);
		values.put(DatabaseHelper.PERSONALITY, newPersonality);
		values.put(DatabaseHelper.SIZE, newSize);
		values.put(DatabaseHelper.STATUS, newStatus);
		values.put(DatabaseHelper.CONDITION, newCondition);
		
		String[] whereArgs = {Integer.toString(_vegeID)};
		
		int count = db.update(DatabaseHelper.TABLE_NAME, values, DatabaseHelper.UID+ "=?", whereArgs);
		return count;
	}
	
	/**
	 * Returns all of the table data as a String
	 * @return String		a String containing all of the table data. Each column is separate by a space " " and each row separated by a new line
	 */
	public String getAllData()
	{
		SQLiteDatabase db = helper.getWritableDatabase();
		String [] columns = {
				DatabaseHelper.UID,
				DatabaseHelper.AGE,
				DatabaseHelper.TYPE,
				DatabaseHelper.WATERLVL,
				DatabaseHelper.FOODLVL,
				DatabaseHelper.SHADELVL,
				DatabaseHelper.THIRSTRATE,
				DatabaseHelper.HUNGERRATE,
				DatabaseHelper.PERSONALITY,
				DatabaseHelper.SIZE,
				DatabaseHelper.STATUS,
				DatabaseHelper.CONDITION };
		
		Cursor cursor = db.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null, null);
		StringBuffer buffer = new StringBuffer();
		buffer = addToBuffer(cursor, buffer);
		db.close();
		return buffer.toString();
	}
	
	/**
	 * Checks if the database is empty, used in the opening menu when Get Growing! 
	 * is selected to see if the next view that should open up is the avatar selection or the game itself
	 * @return boolean			if true the database is empty, otherwise false
	 */
	public boolean isEmpty()
	{
		SQLiteDatabase db = helper.getWritableDatabase();
		Cursor mCursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME, null);
		boolean rowExists = false;

		if (mCursor.moveToFirst()){
		  rowExists = false;

		} else{
		   rowExists = true;
		}
		
		db.close();
		return rowExists;
	}

	/**
	 * Returns a String containing all the rows with a status of DECEASED
	 * used in the grave yard view to check which vegetables should be displayed on a tomb stone
	 * @return String		columns are separate by a space " " and rows are separated by a new line
	 */
	public String getDeceased()
	{
		SQLiteDatabase db = helper.getWritableDatabase();
		String [] columns = {
				DatabaseHelper.UID,
				DatabaseHelper.AGE,
				DatabaseHelper.TYPE,
				DatabaseHelper.WATERLVL,
				DatabaseHelper.FOODLVL,
				DatabaseHelper.SHADELVL,
				DatabaseHelper.THIRSTRATE,
				DatabaseHelper.HUNGERRATE,
				DatabaseHelper.PERSONALITY,
				DatabaseHelper.SIZE,
				DatabaseHelper.STATUS,
				DatabaseHelper.CONDITION };
		Cursor cursor = db.query(DatabaseHelper.TABLE_NAME, columns, DatabaseHelper.STATUS + "='DECEASED'", null, null, null, null, null);
		StringBuffer buffer = new StringBuffer();
		buffer = addToBuffer(cursor, buffer);
		db.close();
		return buffer.toString();
	}
	
	/**
	 * Returns a String containing all the rows with a status of LIVING
	 * checks if the status is living - used in the game to check which Vegetable objects to create	
	 * @return String		columns are separate by a space " " and rows are separated by a new line
	 */
	public String getLiving() 
	{
		SQLiteDatabase db = helper.getWritableDatabase();
		String [] columns = {
				DatabaseHelper.UID,
				DatabaseHelper.AGE,
				DatabaseHelper.TYPE,
				DatabaseHelper.WATERLVL,
				DatabaseHelper.FOODLVL,
				DatabaseHelper.SHADELVL,
				DatabaseHelper.THIRSTRATE,
				DatabaseHelper.HUNGERRATE,
				DatabaseHelper.PERSONALITY,
				DatabaseHelper.SIZE,
				DatabaseHelper.STATUS,
				DatabaseHelper.CONDITION };
		Cursor cursor = db.query(DatabaseHelper.TABLE_NAME, columns, DatabaseHelper.STATUS + "='LIVING'", null, null, null, null, null);
		StringBuffer buffer = new StringBuffer();
		buffer = addToBuffer(cursor, buffer);
		db.close();
		return buffer.toString();
	}	
	
	/**
	 * Runs through a query and saves all the relevant rows to a StringBuffer 
	 * columns are separate by a space " " and rows are separated by a new line
	 * @param cursor		the cursor storing the query information
	 * @param buffer		the buffer you want to save the data into
	 * @return
	 */
	private StringBuffer addToBuffer(Cursor cursor, StringBuffer buffer)
	{
		int iId, iAge, iType, iWaterLvl, iFoodLvl, iShadeLvl, iThirstRate, iHungerRate, iPersonality, iSize, iStatus, iCondition;
		
		while(cursor.moveToNext())
		{
			iId = cursor.getColumnIndex(DatabaseHelper.UID);
			iAge = cursor.getColumnIndex(DatabaseHelper.AGE);
			iType = cursor.getColumnIndex(DatabaseHelper.TYPE);
			iWaterLvl = cursor.getColumnIndex(DatabaseHelper.WATERLVL);
			iFoodLvl = cursor.getColumnIndex(DatabaseHelper.FOODLVL);
			iShadeLvl = cursor.getColumnIndex(DatabaseHelper.SHADELVL);
			iThirstRate = cursor.getColumnIndex(DatabaseHelper.THIRSTRATE);
			iHungerRate = cursor.getColumnIndex(DatabaseHelper.HUNGERRATE);
			iPersonality = cursor.getColumnIndex(DatabaseHelper.PERSONALITY);
			iSize = cursor.getColumnIndex(DatabaseHelper.SIZE);
			iStatus = cursor.getColumnIndex(DatabaseHelper.STATUS);
			iCondition = cursor.getColumnIndex(DatabaseHelper.CONDITION);
			
			int cid = cursor.getInt(iId);
			String type = cursor.getString(iType);
			int age = cursor.getInt(iAge);
			int waterLvl = cursor.getInt(iWaterLvl);
			int foodLvl = cursor.getInt(iFoodLvl);
			int shadeLvl = cursor.getInt(iShadeLvl);
			int thirstRate = cursor.getInt(iThirstRate);
			int hungerRate = cursor.getInt(iHungerRate);
			String personality = cursor.getString(iPersonality);
			double size = cursor.getDouble(iSize);
			String status = cursor.getString(iStatus);
			int condition = cursor.getInt(iCondition);
			
			buffer.append(cid+" "+type+" "+age+" "+waterLvl+" "+foodLvl+" "+shadeLvl+" "+thirstRate+" "+hungerRate+" "+personality+" "+size+" "+status+" "+condition+"\n");
		}
		
		return buffer;
	}
	
	/**
	 * Closes the database connection
	 * @return void
	 */
	public void close()
	{
		helper.close();
	}
	
	/**
	 * An inner class containing the Database schema
	 * static classes can only access static variables (added security)
	 * There is only a single instance of this class, so each Database class can access the same database no matter where they are in the code
	 * @author 	Lance Donnell and Dhaneesha Rajakaruna
	 * @version 1.1
	 * @since 	2016-01-16
	 */
	static class DatabaseHelper extends SQLiteOpenHelper {

		private static final String DATABASE_NAME = "cannables.db";
		private static final String TABLE_NAME = "VEGETABLE";
		
		//VERSION must be incremented by 1 each time changes are made to the database create string after it has been created the first time
		private static final int DATABASE_VERSION = 5;	
		private static final String UID = "_vegeId";
		private static final String TYPE = "Type";
		private static final String AGE = "Age";
		private static final String WATERLVL = "WaterLvl";
		private static final String FOODLVL = "FoodLvl";
		private static final String SHADELVL = "ShadeLvl";
		private static final String THIRSTRATE = "ThirstRate";
		private static final String HUNGERRATE = "HungerRate";
		private static final String PERSONALITY = "Personality";
		private static final String SIZE = "Size";
		private static final String STATUS = "Status";
		private static final String CONDITION = "Condition";
		
		public DatabaseHelper (Context context)
		{
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		public DatabaseHelper (Context context, String dbName, SQLiteDatabase.CursorFactory factory, int version)
		{
			super(context, dbName, factory, version);
		}
	
		@Override
		public void onCreate(SQLiteDatabase db)
		{
			System.out.println("OnCreate called");
			String CREATE_TABLE = 
					"CREATE TABLE "+TABLE_NAME+" ("
					+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
					+TYPE+" VARCHAR(255), "
					+AGE+" INTEGER, "
					+WATERLVL+" INTEGER, "
					+FOODLVL+" INTEGER, "
					+SHADELVL+" INTEGER, "
					+THIRSTRATE+" INTEGER, "
					+HUNGERRATE+" INTEGER, "
					+PERSONALITY+" VARCHAR(255), "
					+SIZE+" INTEGER, "
					+STATUS+" VARCHAR(10), "
					+CONDITION+" INTEGER);";
			try {
				db.execSQL(CREATE_TABLE);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println(CREATE_TABLE);
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
		{
			System.out.println("OnUpgrade called");
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
}
