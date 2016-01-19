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
import android.os.CountDownTimer;
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
	private Intent intent;
	private boolean vegeFound = false;
	private CountDownTimer countdown;
	static Vegetable selectedVege;	//The vegetable that was selected with the screen touch
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		helper = new Database(this);	
		init();
		
		gameManager = (GameManager) findViewById(R.id.gameSurfaceView);
		gameManager.setVeges(veges);
		gameManager.setOnTouchListener(this);
		
		countdown = new CountDownTimer(1000, 1000) {

		    public void onTick(long millisUntilFinished) {
		       //Log.d("Tic","seconds remaining: " + millisUntilFinished / 1000);
		    }

		    public void onFinish() {
		   	//Code to update
		   	 
		   	for(Vegetable vege : veges)
		   	{
		   		vege.update();
		   	}
		   	this.start();
		    }
		 }.start();

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
			startNewGame();
		
		//if it is not a new game, load in all living vegetables and create objects for them
		}else{			
			loadGame();	
		}
		
		helper.close();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
	
		//countdown.cancel();
		
		vegeFound = false;
		
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
	 	 	this.startActivity(intent);
		}

		return false;
	}
	
	/**
	* Initialize a new game
	*/
	private void startNewGame()
	{
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
	}
	
	/**
	* retrieve stats of existing vegetables from the database
	*/
	private void loadGame()
	{
		String living = helper.getLiving();
		String[] livingVeges = living.split("#");
		
        for (int i = 0; i<livingVeges.length; i++)
        {
            if (livingVeges[i] != ""){
                veges.add(new Vegetable(livingVeges[i], this));
            }
        }		
	}
	
}