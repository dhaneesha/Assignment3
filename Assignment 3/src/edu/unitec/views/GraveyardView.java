/**
* <h1>GraveyardView.java</h1>
* Implements the onDraw method so that tombstones can be drawn (incomplete)
*
* @author  Lance Donnell and Dhaneesha Rajakaruna
* @version 1.0
* @since   2016-01-20
*/

package edu.unitec.views;
import java.util.ArrayList;

import edu.unitec.assignment3.R;
import edu.unitec.data.Database;
import edu.unitec.data.Vegetable;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class GraveyardView extends View{
	
	private Database helper;
	private ArrayList<Vegetable> veges = new ArrayList<Vegetable>();
	private Bitmap tomb;	
	private Context context;
	final Paint paint = new Paint();
	
	/**
	 * Constructors
	 * @param context		the context of this View
	 */
	public GraveyardView(Context context) 
	{
		super(context);
		this.context = context;
		setup();
	}

	public GraveyardView(Context context, AttributeSet attrs, int defStyleAttr) 
	{
		super(context, attrs, defStyleAttr);
		this.context = context;
		setup();
	}

	public GraveyardView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		this.context = context;
		setup();
	}
	
	/**
	 * Retrieves an array of veges that are deceased
	 * @return void
	 */
	public void setup()
	{
		tomb = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.tomb);

		helper = new Database(context);
		
		String deceased = helper.getDeceased();
		String[] deadVeges = deceased.split("#");
		
        for (int i = 0; i<deadVeges.length; i++)
        {
            if (deadVeges[i] != ""){
                veges.add(new Vegetable(deadVeges[i], (Activity)context));
            }
        }	
			
		helper.close();
	}
	
	@Override
	protected void onDraw(Canvas canvas)
	{
		//incomplete - prototype stage
		canvas.drawColor(Color.DKGRAY);
		for(int i = 0; i<veges.size(); i++){
			canvas.drawBitmap(tomb, 100+tomb.getWidth()*i, 100, null);
		}

	}
}
