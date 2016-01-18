/**
 * Avatar selection activity
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
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import edu.unitec.assignment3.R;
import edu.unitec.data.Vegetable.VegetableType;

public class AvatarActivity extends Activity implements OnTouchListener {

	private boolean potatoSelected = false, carrotSelected = false, cabbageSelected = false, eggplantSelected = false;
	private int picked = 0;
	private ImageView btnPotato, btnCarrot, btnCabbage, btnEggplant;
	private Intent intent;
	private int numberOfVeges = 2;
	private boolean newGame = false;
	private VegetableType choice1 = VegetableType.NULL, choice2 = VegetableType.NULL;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_avatar_view);
		
		btnPotato = (ImageView) findViewById(R.id.btn_potato);
		btnCarrot = (ImageView) findViewById(R.id.btn_carrot);
		btnCabbage = (ImageView) findViewById(R.id.btn_cabbage);
		btnEggplant = (ImageView) findViewById(R.id.btn_eggplant);
		
		btnPotato.setOnTouchListener(this);
		btnCarrot.setOnTouchListener(this);
		btnCabbage.setOnTouchListener(this);
		btnEggplant.setOnTouchListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.avatar_view, menu);
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
		
		//check if the action is down, otherwise the picked variable keeps changing based on how long the user holds down the button
		if(event.getAction() == MotionEvent.ACTION_DOWN)
		{
			switch(v.getId())
			{
				case(R.id.btn_potato):
					potatoSelected = !potatoSelected;
					if(potatoSelected){
						picked++;
						if(picked == 1)
							choice1 = VegetableType.POTATO;
						else if(picked == 2)
							choice2 = VegetableType.POTATO;
					}
					else{
						picked--;
					}
					break;
				case(R.id.btn_carrot):
					carrotSelected = !carrotSelected;
					if(carrotSelected){
						picked++;
						if(picked == 1)
							choice1 = VegetableType.CARROT;
						else if(picked == 2)
							choice2 = VegetableType.CARROT;
					}
					else{
						picked--;
					}
					break;
				case(R.id.btn_cabbage):
					cabbageSelected = !cabbageSelected;
					if(cabbageSelected){
						picked++;
						if(picked == 1)
							choice1 = VegetableType.CABBAGE;
						else if(picked == 2)
							choice2 = VegetableType.CABBAGE;
					}
					else{
						picked--;
					}
					break;
				case(R.id.btn_eggplant):
					eggplantSelected = !eggplantSelected;
					if(eggplantSelected){
						picked++;
						if(picked == 1)
							choice1 = VegetableType.EGGPLANT;
						else if(picked == 2)
							choice2 = VegetableType.EGGPLANT;
					}
					else{
						picked--;
					}
					break;
				}
			}
			
		if(picked == numberOfVeges)
		{	
			newGame = true;
			intent = new Intent(AvatarActivity.this, GameActivity.class);
			intent.putExtra("GameState", newGame); 
			intent.putExtra("choice1", choice1); 
			intent.putExtra("choice2", choice2);
			
			this.startActivity(intent);
		}
		
		return false;
	}
}
