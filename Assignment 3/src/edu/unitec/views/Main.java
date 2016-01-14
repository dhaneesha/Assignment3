/**
 * Our opening menu
 * Created 13.01.16
 */

package edu.unitec.views;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import edu.unitec.assignment3.R;
import edu.unitec.data.Database;

public class Main extends Activity {

	Database helper = null;
	SQLiteDatabase db = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        helper = new Database(this); //haven't added any code to insert data into the database because it should be empty to begin with.
        //db = helper.getWritableDatabase(); //commented out because I don't want to make the database yet    
    }
    
    @Override
    public void onDestroy()
    {
    	super.onDestroy();
    	helper.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
