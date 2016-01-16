package edu.unitec.views;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import edu.unitec.data.Vegetable;
import edu.unitec.data.Vegetable.VegetableType;
import edu.unitec.assignment3.R;

public class GameView extends SurfaceView implements Runnable,SurfaceHolder.Callback{
 
Thread animationThread;
int screenWidth;
int screenHeight;
Boolean running = false;
SurfaceHolder holder;
public Handler mHandler;
Canvas canvas;
Paint paint;

Vegetable carrot = new Vegetable();
Bitmap vegetableBitmap;
Bitmap tempBitmap;  // to be used to rescale and display the vegetable 
/** 
 * Class constructor.
 * @param context Context of the application
 */				
public GameView(Context context) // Constructor
{		
	super(context);
	getHolder().addCallback(this); 
}

/**
 * Class alternate constructor.
 * @param context Context of the application
 * @param attrs attributeset
*/

public GameView(Context context, AttributeSet attrs) { // Alternate Constructor
	super(context, attrs);
	getHolder().addCallback(this);
}

public void surfaceCreated	(SurfaceHolder	holder) 
{	Log.d("surfaceCreated ", " Line ");
	animationThread = new Thread(this);
	running  = true;
	animationThread.start(); // start a new thread to handle this activities animation
	
	try 
	{	
		//initialize startup the variables 
		canvas = new Canvas();
		paint = new Paint ();
		vegetableBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.carrot);
		carrot.setVegetableType(VegetableType.CARROT);
		carrot.setVegetableImage(vegetableBitmap);
		
	}
	catch(Exception ex)
	{
		Log.d("Error", "Error in surfaceCreated" + ex.getMessage());
	}
	screenWidth = holder.getSurfaceFrame().right ; // gets the size of the surface frame and stores in Model
	screenHeight = holder.getSurfaceFrame().bottom ;	
	
}

public void surfaceChanged	(SurfaceHolder holder, int format,int width, int height) 
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
	while(running){
		canvas= null;
		holder = getHolder();
		
		synchronized(holder) 
		{
		canvas = holder.lockCanvas();	             		
		canvas.drawColor(Color.BLACK);						
		paint.setColor(Color.RED);			
		
		
		// I think this 2 lines of code is causing a problem
		tempBitmap = Bitmap.createScaledBitmap(vegetableBitmap,20 + carrot.testingGrowth,30 + carrot.testingGrowth,false);
		canvas.drawBitmap(tempBitmap, screenWidth/2,screenHeight/2, paint);
		
		}
		try
		{
			Thread.sleep(50/1000); 
		} 
		catch(InterruptedException e) 
		{
			e.printStackTrace();
			Log.d("", " Run method error in thread" );
		}
			holder.unlockCanvasAndPost(canvas);
		}
	
}
		
}
