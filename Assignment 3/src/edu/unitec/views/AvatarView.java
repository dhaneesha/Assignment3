/**
 * Our avatar selection activity
 * Created 13.01.16
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

public class AvatarView extends Activity implements OnTouchListener {

	private boolean potatoSelected = false, carrotSelected = false, pumpkinSelected = false, squashSelected = false;
	private int picked = 0;
	private ImageView btnPotato, btnCarrot, btnPumpkin, btnSquash;
	private Intent intent;
	private int numberOfVeges = 2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_avatar_view);
		
		btnPotato = (ImageView) findViewById(R.id.btn_potato);
		btnCarrot = (ImageView) findViewById(R.id.btn_carrot);
		btnPumpkin = (ImageView) findViewById(R.id.btn_pumpkin);
		btnSquash = (ImageView) findViewById(R.id.btn_squash);
		
		btnPotato.setOnTouchListener(this);
		btnCarrot.setOnTouchListener(this);
		btnPumpkin.setOnTouchListener(this);
		btnSquash.setOnTouchListener(this);
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
		
		switch(v.getId())
		{
		case(R.id.btn_potato):
			potatoSelected = !potatoSelected;
			if(potatoSelected){
				picked++;
			}
			else{
				picked--;
			}
			break;
		case(R.id.btn_carrot):
			carrotSelected = !carrotSelected;
			if(carrotSelected){
				picked++;
			}
			else{
				picked--;
			}
			break;
		case(R.id.btn_pumpkin):
			pumpkinSelected = !pumpkinSelected;
			if(pumpkinSelected){
				picked++;
			}
			else{
				picked--;
			}
			break;
		case(R.id.btn_squash):
			squashSelected = !squashSelected;
			if(squashSelected){
				picked++;
			}
			else{
				picked--;
			}
			break;
		}
		
		if(picked == numberOfVeges)
		{
			intent = new Intent(AvatarView.this, GameActivity.class);
			this.startActivity(intent);
		}
		
		return false;
	}
}
