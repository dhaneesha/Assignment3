/**
 * Our model class to store all the environmental behavior
 * Created 13.01.16
 */

package edu.unitec.data;

import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.view.Display;
import android.view.WindowManager;
import edu.unitec.assignment3.R;

public class Habitat {
	
	enum Weather{ SUNNY, OVERCAST, RAINY }
	
	private Context contex; 
	
	private Bitmap skyBackground;
	private Bitmap overcastSkyBackground; 
	private Bitmap landBackground;
	private Bitmap overcastLandBackground;
	
	private Weather currentWeather;
	private MediaPlayer backgroundSounds;
	
	private WindowManager wm;
	private Display display;
	private Point size = new Point();
	
	private Random r = new Random();
	private int result = 0;
	private int min = 0;
	private int max = 5;
			
	/** 
	* Habitat constructor.
	*/
	public Habitat(Context appContex)
	{
		this.contex = appContex;
		init();
	}
	
	/** 
	* Initializes the habitat data
	* @return void
	*/
	private void init()
	{
		currentWeather = Weather.SUNNY;
		
		WindowManager wm = (WindowManager) contex.getSystemService(Context.WINDOW_SERVICE);
		display = wm.getDefaultDisplay();
		display.getSize(size);
		
		int width = size.x;
		int height = size.y;
		
		landBackground = BitmapFactory.decodeResource(contex.getResources(), R.drawable.ground);
		landBackground = Bitmap.createScaledBitmap(landBackground, width, height, true);
		
		overcastLandBackground = BitmapFactory.decodeResource(contex.getResources(), R.drawable.groundovercsat);
		overcastLandBackground = Bitmap.createScaledBitmap(overcastLandBackground, width, height, true);
		
		skyBackground = BitmapFactory.decodeResource(contex.getResources(), R.drawable.sky);
		skyBackground = Bitmap.createScaledBitmap(skyBackground, width, height/2, true);
		
		overcastSkyBackground = BitmapFactory.decodeResource(contex.getResources(), R.drawable.overcast);
		overcastSkyBackground = Bitmap.createScaledBitmap(overcastSkyBackground, width, height/2, true);	
	}
	
	/** 
	* Draws the background depending on the currently weather 
	* @return void
	*/
	public void drawBackground(Canvas canvas){
		
		if(currentWeather == Weather.OVERCAST){
			canvas.drawBitmap(overcastLandBackground, 0, 0, null);
			canvas.drawBitmap(overcastSkyBackground, 0, 0, null);
		}
		else if(currentWeather == Weather.SUNNY){
			canvas.drawBitmap(landBackground, 0, 0, null);
			canvas.drawBitmap(skyBackground, 0, 0, null);
		}
		else if(currentWeather == Weather.RAINY){
			canvas.drawBitmap(overcastLandBackground, 0, 0, null);
			canvas.drawBitmap(overcastSkyBackground, 0, 0, null);
		}
	}
	
	/** 
	* Changes the weather to new weather
	* @return void
	*/
	public void updateWeather(){
		result = r.nextInt(max - min + 1) + min;
		if(result == 4){
			currentWeather = Weather.OVERCAST;
		}
		else if(result == 5){
			currentWeather = Weather.RAINY;
		}
		else{
			currentWeather = Weather.SUNNY;
		}
	}
		
	/** 
	* Plays music during game scene (unimplemented) 
	*/
	public void playMusic()
	{
		
	}
	
	/** 
	* Stops any music playing (unimplemented) 
	*/
	public void stopMusic()
	{
		
	}
	
	/** 
	* Plays during game scene (unimplemented) 
	*/
	public void playSoundFX()
	{
		
	}
}
