package edu.unitec.views;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;
import edu.unitec.assignment3.R;
import edu.unitec.data.Vegetable.Mood;
import edu.unitec.data.Vegetable.Personality;

public class StatsActivity extends Activity implements OnTouchListener {

	private TextView displayBox;
	private View statsView;
	private int age, waterLevel, foodLevel;
	private Personality personality; 
	private Mood mood;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stats_view);
				
		displayBox = (TextView) findViewById(R.id.display_box);
		displayBox.setOnTouchListener(this);
		
		statsView = (View) findViewById(R.id.statsView);
		statsView.setOnTouchListener(this);
		
		//Retrieve vegetable stats - mood is not implemented in game yet so has to be commented out (17.1.16)
		age = getIntent().getIntExtra("age", 0);
		waterLevel = getIntent().getIntExtra("waterLevel",0);
		foodLevel = getIntent().getIntExtra("foodLevel", 0);
		personality = (Personality) getIntent().getSerializableExtra("personality");
		//mood = (Mood) getIntent().getSerializableExtra("mood");
		
		System.out.println(age+","+waterLevel+","+foodLevel+","+personality.toString());//+","+mood.toString());
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
	public boolean onTouch(View v, MotionEvent event) {
		
		if(event.getAction() == MotionEvent.ACTION_DOWN)
		{
			if(v.getId() == R.id.display_box)
		    {
				//do nothing (yet)	
		    }
			else if(v.getId() == R.id.statsView)
			{
				finish();	
			}
		}  
		//Set to true because both statsView and display_box were being registered as touched when display_box was touched
		return true;
	}
}
