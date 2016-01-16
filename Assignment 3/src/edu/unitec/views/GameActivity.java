/**
 * Game interface
 * @author 	Lance Donnell and Dhaneesha Rajakaruna
 * @version 1.0
 * @since 	2016-01-16
 */

package edu.unitec.views;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import edu.unitec.assignment3.R;
import edu.unitec.data.Database;
import edu.unitec.data.Vegetable;
import edu.unitec.data.Vegetable.VegetableType;

public class GameActivity extends Activity {

	private Database helper;
	private Vegetable vegeOne = new Vegetable(), vegeTwo = new Vegetable();
	private ArrayList<Vegetable> veges;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		helper = new Database(this);
		
		//both intents (from Main and AvatarSelection) that get you here send a newGameState value
		boolean newGameState = getIntent().getBooleanExtra("GameState", false);
		
		//if it is a new game, create new vegetables based on player's choice
		if(newGameState){
			VegetableType choice1 = (VegetableType) getIntent().getSerializableExtra("choice1");
			VegetableType choice2 = (VegetableType) getIntent().getSerializableExtra("choice2");
			
			vegeOne.setVegetableType(choice1);
			vegeTwo.setVegetableType(choice2);
			
			//Add the new veges to the database
			helper.insert(choice1.toString(), vegeOne.getCurrentAge(), vegeOne.getWaterLevel(), vegeOne.getFoodLevel(), vegeOne.getShadeLevel(), vegeOne.getThirstRate(), vegeOne.getHungerRate(), vegeOne.getPersonalityStr(), vegeOne.getSize(), vegeOne.getStatus(), vegeOne.getCondition());
			helper.insert(choice2.toString(), vegeTwo.getCurrentAge(), vegeTwo.getWaterLevel(), vegeTwo.getFoodLevel(), vegeTwo.getShadeLevel(), vegeTwo.getThirstRate(), vegeTwo.getHungerRate(), vegeTwo.getPersonalityStr(), vegeTwo.getSize(), vegeTwo.getStatus(), vegeTwo.getCondition());
			
			//Add the veges to the arraylist
			veges.add(vegeOne); veges.add(vegeTwo);
		
		//if it is not a new game, load in all living vegetables and create objects for them
		}else{
			
			String living = helper.getLiving();
			String[] livingVeges = living.split("#");
            for (int i = 0; i<livingVeges.length; i++)
            {
            	//System.out.println(livingVeges[i]);
            	
                if (livingVeges[i] != ""){
                    veges.add(new Vegetable(livingVeges[i]));
                }
            }			
		}
		
		helper.close();
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
		return super.onOptionsItemSelected(item);
	}
}
