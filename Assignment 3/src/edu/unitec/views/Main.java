/**
 * Our opening menu, players can select to get growing or visit the graveyard
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
import android.view.Window;
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
        requestWindowFeature(Window.FEATURE_NO_TITLE);
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