/**
 * Our model class to store all the vegetable data
 * Created 13.01.16
 */

package edu.unitec.data;

public class Vegetable {
	
	public enum VegetableType {  
		PUMPKIN,
		CARROT,
		POTATO,
		SQUASH
		}
	
	public enum Mood {
		HAPPY,
		OK,
		SAD,
		ANGRY  // can delete this if we dont need 
		}
	
	public enum Personality {
		SASSY,
		HARDY,
		TIMID,				
	}
	
	private VegetableType type; // unique
	private double currentAge; 
	private double finalAge; // unique
	private double waterLevel; 
	private double foodLevel;
	private double shadeLevel;
	//double temperature; // We dont need shade and temperature. Either will work
	private int hungerRate; // unique
	private int thirstRate; // unique
	private int growthRate;
	
	private Mood mood;
	private Personality personality; // unique
	private double size; // unique
	private int location = 200;  // just initializing some value for ease
	private boolean cannibalize = false;
	private boolean dead = false;
	/** 
	* Vegetable constructor.
	* Sets up the default values for vegetables
	*/
 	public Vegetable ( )
 	{
 		currentAge = 0; 
 		waterLevel = 100; 
 		foodLevel = 100;
 		shadeLevel = 100;
 		mood = Mood.HAPPY;		
 	}
 	
	/** 
	* Setup unique values depending on vegetable type.
	* Sets up the default values for vegetables
	* @param vType values will be initialized depending on the VegetableType
	*/ 	
 	public void setVegetableType(VegetableType vType)
 	{		 		
 		switch (vType) {
		case PUMPKIN:
			 finalAge = 100; 
			 hungerRate = 1; 
			 thirstRate = 1; 
			 growthRate = 2;
			 personality = Personality.HARDY; 
			 size = 100; 
			break;
		
		case CARROT:
			 finalAge = 50; 
			 hungerRate = 5; 
			 thirstRate = 5;
			 growthRate = 3;
			 personality = Personality.SASSY; 
			 size = 30; 
			break;
			
		case POTATO:
			 finalAge = 60; 
			 hungerRate = 5; 
			 thirstRate = 3;
			 growthRate = 3;
			 personality = Personality.TIMID; 
			 size = 20; 
			break;
		
		case SQUASH:
			 finalAge = 80; 
			 hungerRate = 2; 
			 thirstRate = 2;
			 growthRate = 4;
			 personality = Personality.HARDY; 
			 size = 80; 
			break;

//		default:
//			break;
		}	
 	}
 	
	/** 
	* Updates the the vegetable by reducing finalAge, waterLevel,foodLevel, updates mood
	*/ 	
 	public void update ()
 	{
 		finalAge--;
 		foodLevel -= hungerRate;
 		waterLevel -= thirstRate;
 		
 		if (!isDead())  // if vegetable is alive 
 		{
 			grow (); // it grows
 			calculateMood();
 		}				
 	}
 	
	/** 
	* Calculates mood, sets the cannibalize flag if Vege is too hungry
	*/ 	
 	public void calculateMood()
 	{
 		double score = waterLevel + foodLevel + shadeLevel; // score is out of 300
 		
 		if (score >= 200)
 		{
 			mood = Mood.HAPPY;
 		}
 		
 		if (score >= 100)
 		{
 			mood = Mood.OK;
 		}
 		
 		if (score <= 100)
 		{
 			mood = Mood.SAD;
 		}
 		
 		if (foodLevel < 5)
 		{
 			cannibalize = true;
 		}
 	}
 	
	/** 
	* Check if Vegetable is alive. 
	* To be alive food & water level must be above 0. If dead dead flag will be made to true
	* 
	* @return <code>true</code> If dead
	* @return <code>false</code> If alive
	*/ 	
 	public boolean isDead()
 	{
 		if (waterLevel <=0	|| foodLevel <=0)
 		{
 			dead = true;
 			return true;
 		}		
 		return false;
 	}
	/** 
	* Increments the foodlevel of the Vegetable
	* @param food how much food to increment
	*/ 
 	public void feed (int food)
 	{
 		foodLevel += food;
 	}
 	
	/** 
	* Increments the waterlevel of the Vegetable
	* @param water how much water to increment
	*/ 
 	public void irrigate (int water)
 	{
 		waterLevel += water;
 	}
 	
	/** 
	* Increments the size of the Vegetable by adding the food range 
	*/ 
 	public void grow ()
 	{
 		size += growthRate;
 	}
 	
	/** 
	* I dont understand the purpose of this method to impliment it 
	*/
 	public void move ()
 	{
 		
 	}
}
