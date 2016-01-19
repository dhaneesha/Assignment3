
/**
 * StatsActivity - shows the player the vegetable's stats when they touch it
 * @author 	Lance Donnell and Dhaneesha Rajakaruna
 * @version 1.0
 * @since 	2016-01-17
 */

package edu.unitec.views;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import edu.unitec.assignment3.R;
import edu.unitec.data.Vegetable.Mood;
import edu.unitec.data.Vegetable.Personality;
import edu.unitec.data.Vegetable.VegetableType;

public class StatsActivity extends Activity implements OnTouchListener {

	private View statsView;
	private int age, waterLevel, foodLevel, feedAmount = 10, waterAmount=10;
	private Personality personality; 
	private Mood mood;
	private VegetableType type;	
	private ImageView moodBar, hungerLevelBar, waterLevelBar, vegeImage;
	private ImageButton btnFeed, btnWater;
	private TextView txtPersonality, txtAge;
	private ImageView water, food;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stats_view);
			
		//Retrieve vegetable stats - mood is not implemented in game yet so has to be commented out (17.1.16)
		age = GameActivity.selectedVege.getCurrentAge();//getIntent().getIntExtra("age", 0);
		waterLevel = GameActivity.selectedVege.getWaterLevel();//getIntent().getIntExtra("waterLevel",0);
		foodLevel = GameActivity.selectedVege.getFoodLevel();//getIntent().getIntExtra("foodLevel", 0);
		personality = GameActivity.selectedVege.getPersonality();//(Personality) getIntent().getSerializableExtra("personality");
		type = GameActivity.selectedVege.getType();//(VegetableType) getIntent().getSerializableExtra("type");		
		//mood = (Mood) getIntent().getSerializableExtra("mood");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.stats_view, menu);
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
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		//Because the size of the screen is not initialised in onCreate, the init() method needs to be called here
		init();
	}
		
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		if(event.getAction() == MotionEvent.ACTION_DOWN)
		{
			switch(v.getId())
		   {
		   	case R.id.btn_give_food:
		   		GameActivity.selectedVege.setFoodLevel(GameActivity.selectedVege.getFoodLevel() + feedAmount);
		   		Log.d("Testing", "Food Clicked from Stats View");
		   		finish();
		   		break;
		   	case R.id.btn_water:
		   		GameActivity.selectedVege.setWaterLevel((GameActivity.selectedVege.getWaterLevel() + waterAmount));
		   		Log.d("Testing", "Water Clicked from Stats View");
		   		finish();
		   		break;
		   	case R.id.statsView:
		   		finish();
		   		break;
		   }
		}  
		return true;
	}
	
	/**
	* Initialises the variables needed for this class and sets OnTouchListeners
	* @return void
	*/
	private void init()
	{	
		moodBar = (ImageView) findViewById(R.id.mood_bar);
		hungerLevelBar= (ImageView) findViewById(R.id.hungerLevelBar);
		waterLevelBar = (ImageView) findViewById(R.id.waterLevelBar);
		vegeImage = (ImageView) findViewById(R.id.vege_image);
		food = (ImageView) findViewById(R.id.sun_icon);
		water = (ImageView) findViewById(R.id.water_icon);
				
		btnFeed = (ImageButton) findViewById(R.id.btn_give_food);
		btnWater = (ImageButton) findViewById(R.id.btn_water);
		
		txtPersonality = (TextView) findViewById(R.id.txt_personality);
		txtAge = (TextView) findViewById(R.id.txt_age);
		
		btnFeed.setOnTouchListener(this);
		btnWater.setOnTouchListener(this);
		
		statsView = (View) findViewById(R.id.statsView);
		statsView.setOnTouchListener(this);
		
		switch(type)
		{
			case POTATO:{vegeImage.setImageResource(R.drawable.potato);
				break;
			}
			case CARROT:{vegeImage.setImageResource(R.drawable.carrot);
				break;
			}
			case CABBAGE:{vegeImage.setImageResource(R.drawable.cabbage);
				break;
			}
			case EGGPLANT:{vegeImage.setImageResource(R.drawable.eggplant);
				break;
			}
			case NULL:{
				break;
			}
		}
				
		food.setX(hungerLevelBar.getX()+hungerLevelBar.getWidth()/100 * foodLevel);
		food.setY(hungerLevelBar.getY() - food.getHeight()/2);
		
		water.setX(waterLevelBar.getX()+waterLevelBar.getWidth()/100 * waterLevel);
		water.setY(waterLevelBar.getY() - water.getHeight()/2);
	
		txtPersonality.setText(personality.toString());
		txtAge.setText("Age (M) :" + Integer.toString(age));			
	}
}