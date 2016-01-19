/**
 * Game loop
 * @author 	Lance Donnell and Dhaneesha Rajakaruna
 * @version 1.0
 * @since 	2016-01-16
 */

package edu.unitec.views;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import edu.unitec.data.Vegetable;

public class GameManager extends SurfaceView implements Runnable, SurfaceHolder.Callback {
 
	private Thread animationThread;
	private SurfaceHolder holder;
	public Handler mHandler;
	private Canvas canvas;
	private Paint paint;	
	private Boolean running = false;	
	private ArrayList<Vegetable> veges = new ArrayList<Vegetable>();	//all the vegetables currently drawn on screen
	
	/** 
	 * Class constructor.
	 * @param context Context of the application
	 */				
	public GameManager(Context context) // Constructor
	{		
		super(context);
		getHolder().addCallback(this); 
	}
	
	/**
	 * Class alternate constructor.
	 * @param context Context of the application
	 * @param attrs attributeset
	*/
	
	public GameManager(Context context, AttributeSet attrs) { // Alternate Constructor
		super(context, attrs);
		getHolder().addCallback(this);
	}
	
	public void surfaceCreated	(SurfaceHolder	holder) {	
		
		animationThread = new Thread(this);
		running = true;
		animationThread.start(); // start a new thread to handle this activities animation
		
		try 
		{	
			//initialize startup the variables 
			canvas = new Canvas();
			
			paint = new Paint (); // debug code
			paint.setTextSize(36);
			paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
		}
		catch(Exception ex)
		{
			Log.d("Error", "Error in surfaceCreated" + ex.getMessage());
		}
		//screenWidth = holder.getSurfaceFrame().right ; // gets the size of the surface frame to store in the model
		//screenHeight = holder.getSurfaceFrame().bottom ;	
		
	}
	
	public void surfaceChanged	(SurfaceHolder holder, int format, int width, int height) 
	{
		
	}
	
	public void surfaceDestroyed	(SurfaceHolder holder) 
	{
		running = false;
		if(animationThread!= null)
		{
			try 
			{
				animationThread.join(); // finish the animation thread and let the thread die
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}		
		}
	}
	
	@Override
	public void run() {
		
		while(running) {
			canvas= null;
			holder = getHolder();
			
			synchronized(holder) 
			{	
				canvas = holder.lockCanvas();
				
				//Canvas becomes null whenever you rotate the view, so this check has been added to ensure it isn't before attempting to draw
				if(canvas != null)
				{
					canvas.drawColor(Color.BLACK);
					
					//debug stats
					paint.setColor(Color.WHITE);
					canvas.drawText("Vege 1 Water :" + veges.get(0).getWaterLevel(), 10, 50, paint);
					canvas.drawText("Vege 1 Food :" +  veges.get(0).getFoodLevel(),300, 50, paint);
					canvas.drawText("Vege 1 Life :" +  veges.get(0).getFinalAge(),600, 50, paint);
					canvas.drawText("Vege 2 Water : " +  veges.get(1).getWaterLevel(), 10, 100, paint);
					canvas.drawText("Vege 2 Food : " +  veges.get(1).getFoodLevel(), 300, 100, paint);
					canvas.drawText("Vege 2 Life :" +  veges.get(1).getFinalAge(),600, 100, paint);
					//debug stats ends
					
					for(int i = 0; i<veges.size(); i++)
					{
						veges.get(i).drawVegetables(canvas, veges);	//need to implement this better so it's not a static method		
					}
				}
					
			}
			try{
				Thread.sleep(50/1000); 
			} catch(InterruptedException e) 
			{
				e.printStackTrace();
				Log.e("ERROR", "Run method error in thread");
			}
			
			//Canvas was becoming null whenever you rotated the view, so this check has been added to deal with that
			if(canvas != null)
				holder.unlockCanvasAndPost(canvas);
		}
	}
	
	/**
	 * bit of a hacky solution to passing information between the GameActivity and GameManager view
	 * @param veges		Contains the vegetables we want to draw (initialised in GameActivity)
	 */
	public void setVeges(ArrayList<Vegetable> veges)
	{
		this.veges = veges;
	}
}
