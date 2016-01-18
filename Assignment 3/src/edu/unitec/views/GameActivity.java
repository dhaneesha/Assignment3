/**
 * Game interface
 * @author 	Lance Donnell and Dhaneesha Rajakaruna
 * @version 1.0
 * @since 	2016-01-16
 */

package edu.unitec.views;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import edu.unitec.assignment3.R;
import edu.unitec.data.Database;
import edu.unitec.data.Vegetable;
import edu.unitec.data.Vegetable.VegetableType;

public class GameActivity extends Activity implements OnTouchListener {

	private Database helper;
	private Vegetable vegeOne, vegeTwo;
	private ArrayList<Vegetable> veges = new ArrayList<Vegetable>();
	private GameManager gameManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		helper = new Database(this);	
		init();
		
		gameManager = (GameManager) findViewById(R.id.gameSurfaceView);
		gameManager.setVeges(veges);
		gameManager.setOnTouchListener(this);
		
		Button btn = (Button) findViewById(R.id.btn_Feed);
		btn.setOnTouchListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
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
		
		id = item.getItemId();
		if (id == R.id.action_exit) {
			System.exit(0);
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * Initialize the game by either creating or loading in the player's Vegetables
	 */
	private void init()
	{
		//both intents (from Main and AvatarSelection) that get you here send a newGameState value
		boolean newGameState = getIntent().getBooleanExtra("GameState", false);
		
		//if it is a new game, create new vegetables based on player's choice
		if(newGameState){
			VegetableType choice1 = (VegetableType) getIntent().getSerializableExtra("choice1");
			VegetableType choice2 = (VegetableType) getIntent().getSerializableExtra("choice2");
			
			vegeOne = new Vegetable(this, choice1);
			vegeTwo = new Vegetable(this, choice2);
						
			//Add the new veges to the database
			long first = helper.insert(choice1.toString(), vegeOne.getCurrentAge(), vegeOne.getWaterLevel(), vegeOne.getFoodLevel(), vegeOne.getShadeLevel(), vegeOne.getThirstRate(), vegeOne.getHungerRate(), vegeOne.getPersonality().toString(), vegeOne.getSize(), vegeOne.getStatus(), vegeOne.getCondition());
			long second = helper.insert(choice2.toString(), vegeTwo.getCurrentAge(), vegeTwo.getWaterLevel(), vegeTwo.getFoodLevel(), vegeTwo.getShadeLevel(), vegeTwo.getThirstRate(), vegeTwo.getHungerRate(), vegeTwo.getPersonality().toString(), vegeTwo.getSize(), vegeTwo.getStatus(), vegeTwo.getCondition());
			
			if(first < 0)
			{
				Log.e("ERROR", "First went wrong.");
			}
			if(second < 0)
			{
				Log.e("ERROR", "Second went wrong.");
			}
			
			//Add the veges to the arraylist
			veges.add(vegeOne); 
			veges.add(vegeTwo);
		
		//if it is not a new game, load in all living vegetables and create objects for them
		}else{
			
			String living = helper.getLiving();
			String[] livingVeges = living.split("#");
			
            for (int i = 0; i<livingVeges.length; i++)
            {
                if (livingVeges[i] != ""){
                    veges.add(new Vegetable(livingVeges[i], this));
                }
            }			
		}
		
		helper.close();
	}

	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		if (v.getId() == R.id.btn_Feed)
		{
			veges.get(0).resizeImage();
		}
		
		Vegetable selectedVege = new Vegetable();	//The vegetable that was selected with the screen touch
		Intent intent;
		boolean vegeFound = false;
		
		for(int i = 0; i<veges.size(); i++)
		{
			//found it easiest to use this already established rect method (contains)
			if(veges.get(i).getBoundingRect().contains((int)event.getX(), (int)event.getY())){
				selectedVege = veges.get(i);
				vegeFound = true;
			}
		}
		
		if(vegeFound)
		{
			intent = new Intent(GameActivity.this, StatsActivity.class);
			intent.putExtra("age", selectedVege.getCurrentAge());
			intent.putExtra("waterLevel", selectedVege.getWaterLevel());
			intent.putExtra("foodLevel", selectedVege.getFoodLevel());
			intent.putExtra("personality", selectedVege.getPersonality());
			//intent.putExtra("size", selectedVege.getSize());
			//intent.putExtra("mood", selectedVege.getMood());
	  	  	this.startActivity(intent);
		}

		return false;
	}
}
