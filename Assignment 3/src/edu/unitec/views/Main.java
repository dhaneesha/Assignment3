/**
 * Our opening menu
 * Created 13.01.16
 */

package edu.unitec.views;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import edu.unitec.assignment3.R;
import edu.unitec.data.Database;

public class Main extends Activity implements OnClickListener {

	//Database helper = new Database(this); //haven't added any code to insert data into the database because it should be empty to begin with.
	GameView currentGame ;
	private Button mButton;	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
    	Log.d("Error ", " Line 1");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        currentGame = (GameView) findViewById(R.id.gameSurfaceView);
    	Log.d("Error ", " Line 2");
		mButton = (Button) findViewById(R.id.btn_Feed); // during creation initializes the on thouch listeners 
		mButton.setOnClickListener(this);
		
		mButton = (Button) findViewById(R.id.btn_Update);
		mButton.setOnClickListener(this);
    	Log.d("Error ", " Line 3");
		mButton = (Button) findViewById(R.id.btn_Testing);		
		mButton.setOnClickListener(this);  
    }
    
    @Override
    public void onDestroy()
    {
    	super.onDestroy();
    	//helper.close();
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

	@Override
	public void onClick(View v) {
		
		if (v.getId() == R.id.btn_Update)  
		{
			
		}
		
		if (v.getId() == R.id.btn_Feed)  
		{
			//currentGame.firstVege.grow();
			currentGame.carrot.testingFeed();
			
		}
		
		if (v.getId() == R.id.btn_Testing)  
		{

		}
	}
}