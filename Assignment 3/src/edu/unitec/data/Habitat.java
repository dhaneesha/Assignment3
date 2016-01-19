/**
 * Our model class to store all the environmental behavior
 * Created 13.01.16
 */

package edu.unitec.data;

import edu.unitec.assignment3.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;

public class Habitat {

	/** 
	* Environment constructor.
	*/
	public enum Weather  {  
		SUNNY,
		OVERCAST,
		RAINY		
		}
	
	Context contex; 
	
	Bitmap skyBackground; 
	Bitmap mountainBackground;
	
	Weather currentWeather;
	MediaPlayer backgroundSounds;
	
	public Habitat(Context appContex)
	{
		contex = appContex;
		currentWeather = Weather.SUNNY;
		skyBackground = BitmapFactory.decodeResource(contex.getResources(), R.drawable.background_sky);
		mountainBackground = BitmapFactory.decodeResource(contex.getResources(), R.drawable.background_mountain);
	}
	
	
	/** 
	* No Code yet 
	*/
	public void playMusic()
	{
		
	}
	
	/** 
	* No Code yet 
	*/
	public void stopMusic()
	{
		
	}
	
	/** 
	* No Code yet 
	*/
	public void playSoundFX()
	{
		
	}
	
	/** 
	* Changes the weather to new weather
	* @param newWeather The new weather to be set
	*/
	public void changeWeather(Weather newWeather)
	{
		currentWeather = newWeather;
	}
}
