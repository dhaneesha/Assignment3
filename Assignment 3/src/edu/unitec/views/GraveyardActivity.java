/**
* <h1>GraveyardActivity.java</h1>
* Loads the graveyard area, where deceased vegetables are displayed on tombstones (incomplete)
*
* @author  Lance Donnell and Dhaneesha Rajakaruna
* @version 1.0
* @since   2016-01-20
*/

package edu.unitec.views;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import edu.unitec.assignment3.R;

public class GraveyardActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_graveyard_view);		
	}
}
