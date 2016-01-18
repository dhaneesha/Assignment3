/**
 * Our opening menu
 * @author 	Lance Donnell and Dhaneesha Rajakaruna
 * @version 1.0
 * @since 	2016-01-13
 */

package edu.unitec.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import edu.unitec.assignment3.R;
import edu.unitec.data.Database;

public class Main extends Activity implements OnClickListener {

	private Database helper;
	private Button btnGetGrowing, btnGraveyard;
	private Intent intent;
	private boolean newGame = false;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        helper = new Database(this); //haven't added any code to insert data into the database because it should be empty to begin with.
        
        btnGetGrowing = (Button) findViewById(R.id.btnGetGrowing);
        btnGraveyard = (Button) findViewById(R.id.btnGraveyard);
        btnGetGrowing.setOnClickListener(this);
        btnGraveyard.setOnClickListener(this);
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

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) 
	    {
	       case R.id.btnGetGrowing:
	    	   
	    	   helper.removeAll(); //Uncomment this to simulate a new game (for testing) - it clears the VEGETABLE table
	    	   
	    	   //is empty, so let the user pick their vegetables
	    	   if(helper.isEmpty()) {
		    	  intent = new Intent(Main.this, AvatarActivity.class);
		    	  this.startActivity(intent);
	    	   }
	    	   //is not empty, so load game
	    	   else {
		    	  intent = new Intent(Main.this, GameActivity.class);
		    	  intent.putExtra("GameState", newGame);
		    	  this.startActivity(intent);
	    	   }
	    	   
	    	   break;
	    	   
	       case R.id.btnGraveyard:
	    	   intent = new Intent(this, GraveyardActivity.class);
	    	   this.startActivity(intent);
	    	   break;
	    }
	}
}