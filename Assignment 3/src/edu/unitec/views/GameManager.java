/**
 * Game loop is in charge of regulating and drawing the game
 * @author 	Lance Donnell and Dhaneesha Rajakaruna
 * @version 1.0
 * @since 	2016-01-16
 */

package edu.unitec.views;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import edu.unitec.data.Habitat;
import edu.unitec.data.Vegetable;

public class GameManager extends SurfaceView implements Runnable, SurfaceHolder.Callback {
 
	private Thread animationThread;
	private SurfaceHolder holder;
	public Handler mHandler;
	private Canvas canvas;
	private Paint paint;	
	private Boolean running = false;	
	private ArrayList<Vegetable> veges = new ArrayList<Vegetable>();	//all the vegetables currently drawn on screen
	private Habitat hab;
	private CountDownTimer countdown;
	
	private int screenWidth,screenHeight;
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
		hab = new Habitat(context);
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
			paint.setColor(Color.WHITE);
			paint.setTextSize(36);
			paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
		}
		catch(Exception ex)
		{
			Log.d("Error", "Error in surfaceCreated" + ex.getMessage());
		}
		screenWidth = holder.getSurfaceFrame().right ; // gets the size of the surface frame to store in the model
		screenHeight = holder.getSurfaceFrame().bottom ;

		//Waits a minute before checking to see if the weather has changed
		countdown = new CountDownTimer(1000*60, 1000) {

		     public void onTick(long millisUntilFinished) {
		     }

		     public void onFinish() {
		    	 //Code to update    	 
		    	 hab.updateWeather();
		    	 this.start();
		     }

		}.start();	
		
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
				
				if (veges.get(0).isCannibalistic() || veges.get(1).isCannibalistic() ) // If either vegetable is canibalistic .....
				{
					eatAlive(); // this will cause to eat the other 
				}
				
				//Canvas becomes null whenever you rotate the view, so this check has been added to ensure it isn't before attempting to draw
				if(canvas != null)
				{	
					hab.drawBackground(canvas);
					showLifeAndMood(); // display the current mood & life of the vegetable to give the user an idea of the status of vegetable
					for(int i = 0; i<veges.size(); i++)
					{
						veges.get(i).drawVegetables(canvas, veges);		
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
	* Passes the vegetables Arraylist from the GameActivity to the GameManager
	* @param veges		Contains the vegetables we want to draw (initialised in GameActivity)
	*/
	public void setVeges(ArrayList<Vegetable> veges)
	{
		this.veges = veges;
	}


	/**
	 * Passes the veges to the GameActivity for saving the game progress
	 * @return ArrayList<Vegetable>		the array of currently living veges that need to be updated
	 */
	public ArrayList<Vegetable> getVeges()
	{
		return veges;
	}
	
	
	/**
	* Decides which vegetable will be eaten by checking points
	*/
	public void eatAlive()
	{
		if ((veges.get(0).getDead() )|| (veges.get(1).getDead())) // If either vegetable is dead will no longer eat anything 
		{
			return;
		}
		
		if (veges.get(0).getCanibalPoints() > veges.get(1).getCanibalPoints())
		{
			veges.get(1).setEaten(true);
			veges.get(1).setDead(true);
			
			veges.get(0).setFoodLevel(100); // Once it eats a vegetable the canibal will get full water and food & continue to grow
			veges.get(0).setWaterLevel(100);
		}
		else
		{
			veges.get(0).setEaten(true);
			veges.get(0).setDead(true);
			
			veges.get(1).setFoodLevel(100); // Once it eats a vegetable the canibal will get full water and food & continue to grow
			veges.get(1).setWaterLevel(100);
		}
	}
	
	/**
	* Shows the mood and Status of vegetable
	*/
	public void showLifeAndMood()
	{
		if (veges.get(0).getEaten())
		{
			canvas.drawText("Mood : Eaten"  , 10, 150, paint);	
		}
		else
		{
			canvas.drawText("Mood :" + veges.get(0).getMood() , 10, 150, paint);	
		}
		
		if (veges.get(0).getEaten())
		{
			canvas.drawText("Mood : Eaten" , screenWidth - 300,150, paint);
		}
		else
		{
			canvas.drawText("Mood :" + veges.get(1).getMood() , screenWidth - 300,150, paint);
		}
		
		canvas.drawText("Life :" + String.format("%.1f",veges.get(0).getFinalAge()) , 10, 100, paint);	
		canvas.drawText("Life :" + String.format("%.1f",veges.get(1).getFinalAge()) , screenWidth - 300, 100, paint);		
		canvas.drawText("Vege 1 :" + veges.get(0).getType(), 10, 50, paint);
		canvas.drawText("Vege 2 :" + veges.get(1).getType(), screenWidth - 300, 50, paint);
		
	}
}