/**
 * Our model class to store all the vegetable data
 * @author 	Lance Donnell and Dhaneesha Rajakaruna
 * @version 1.0
 * @since 	2016-01-13
 */

package edu.unitec.data;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Vegetable {
	
	//Vegetable variables appear in the order that they are entered in the database, for ease of reference
	
	//ID
	private int vegeId;	//unique - auto-incremented in database, not initialized here, used for the database update method
	
	//TYPE
	public enum VegetableType {CABBAGE, CARROT, POTATO, EGGPLANT, NULL}	//used to determine what type the player selected, NULL is used for calculations
	private VegetableType type; // unique
	private String typeStr;	//the type of the vegetable as a string, used to convert to an enum upon loading
	
	//AGE
	private int currentAge; 
	private double finalAge; // unique
	
	//WATERLVL, FOODLVL, SHADELVL
	private int waterLevel; 
	private int foodLevel;
	private int shadeLevel;
	
	//HUNGERRATE, THIRSTRATE
	private double hungerRate; 
	private double thirstRate; 
	private double growthRate;	//Not used as at 17.1.16
	
	//PERSONALITY
	public enum Personality {SASSY,	HARDY, TIMID}
	private String personalityStr;	//the personality of the vegetable as a string, used when loading the game
	private Personality personality; 
	
	//SIZE
	private double size;
	private int growthMultiplierX; // This is a small value initialized during vegetable creation which is used set the image size by multiplying with the current size of vegetable 
	private int growthMultiplierY;
	//STATUS
	private String status;	//defaults to LIVING (two values are LIVING and DEAD)
	private boolean dead = false;	//not used as at 17.1.16
	
	//CONDITION
	private int condition;
	
	//The following values are not recorded in the database recorded in database:
	public enum Mood {HAPPY, OK, SAD, ANGRY}	//determines the mood of the vegetable
	//Mood will be defined based on current statistics
	private Mood mood;
	
	private boolean cannibalize = false;
	private Bitmap vegetableImage;

	//location and dimensions of the vegetable
//	private int locationX = 100;
//	private int locationY = 100;
	private int width = 20;
	private int height = 30;
	//A rectangle that fits around the vegetable, used for scaling and positioning
	private Rect boundingRect;
	
	private Activity activity;
	
	public Vegetable ()
	{
		//default constructor
	}
	
	/** 
	* Vegetable constructor.
	* Sets up the default values that every vegetable shares
	*/
 	public Vegetable (Activity activity, VegetableType vType)
 	{
 		this.activity = activity;
 		this.type = vType;
 		
 		this.currentAge = 0; 
 		this.waterLevel = 100; 
 		this.foodLevel = 100;
 		this.shadeLevel = 100;
 		this.mood = Mood.HAPPY;
 		this.status = "LIVING";
 		this.condition = 100;
 		
 		setVegetableStats();
 		setPosition();
 	}
 	
 	//Used to 'load game'
 	public Vegetable (String savedVege, Activity activity)
 	{
 		this.activity = activity;
        String[] vg = savedVege.split(",");
        
        this.vegeId = (Integer.parseInt(vg[0]));
        this.typeStr = (vg[1]);
        this.currentAge = (Integer.parseInt(vg[2]));
        this.waterLevel = Integer.parseInt(vg[3]);
        this.foodLevel = Integer.parseInt(vg[4]);
        this.shadeLevel = Integer.parseInt(vg[5]);
        this.thirstRate = Double.parseDouble(vg[6]);
        this.hungerRate = Double.parseDouble(vg[7]);
        this.personalityStr = (vg[8]);
        this.size = Double.parseDouble(vg[9]);
        this.status = (vg[10]);
        this.condition = Integer.parseInt(vg[11]);
              
        updateTypeAndPersonality();
        setVegetableStats();
        setPosition();
 	}
 	
 	private void updateTypeAndPersonality()
 	{
        if(personalityStr.equals("HARDY"))
        	this.personality = Personality.HARDY;
        if(personalityStr.equals("SASSY"))
        	this.personality = Personality.SASSY;
        if(personalityStr.equals("TIMID"))
        	this.personality = Personality.TIMID; 
        
        if(typeStr.equals("CABBAGE"))
        	this.type = VegetableType.CABBAGE;
        if(typeStr.equals("CARROT"))
        	this.type = VegetableType.CARROT;
        if(typeStr.equals("POTATO"))
        	this.type = VegetableType.POTATO;
        if(typeStr.equals("EGGPLANT"))
        	this.type = VegetableType.EGGPLANT;
 	}
 	
	/** 
	* Setup unique values depending on vegetable type.
	* and the default values for vegetables
	*/ 	
 	private void setVegetableStats()
 	{		 		
 		switch (type) {
		case CABBAGE:
			 this.finalAge = 100; 
			 this.hungerRate = 1; 
			 this.thirstRate = 1; 
			 this.growthRate = 2;
			 this.personality = Personality.HARDY; 
			 this.size = 20;
			 this.vegetableImage = BitmapFactory.decodeResource(activity.getResources(), edu.unitec.assignment3.R.drawable.cabbage);			 
			 break;
		
		case CARROT:
			 this.finalAge = 50; 
			 this.hungerRate = 5; 
			 this.thirstRate = 5;
			 this.growthRate = 3; // was 3 but for debugging made this a bigger value to easily to see progress 
			 this.personality = Personality.SASSY; 
			 this.size = 13;
			 this.vegetableImage = BitmapFactory.decodeResource(activity.getResources(), edu.unitec.assignment3.R.drawable.carrot);
			 break;
			
		case POTATO:
			 this.finalAge = 60; 
			 this.hungerRate = 5; 
			 this.thirstRate = 3;
			 this.growthRate = 3;
			 this.personality = Personality.TIMID; 
			 this.size = 12; 
			 this.vegetableImage = BitmapFactory.decodeResource(activity.getResources(), edu.unitec.assignment3.R.drawable.potato);
			 break;
		
		case EGGPLANT:
			 this.finalAge = 80; 
			 this.hungerRate = 2; 
			 this.thirstRate = 2;
			 this.growthRate = 4;
			 this.personality = Personality.HARDY; 
			 this.size = 18;
			 this.vegetableImage = BitmapFactory.decodeResource(activity.getResources(), edu.unitec.assignment3.R.drawable.eggplant);
			 break;

		case NULL:
			break;
		}
 	}
 	
 	public void setPosition()
 	{
 		int widthMax = activity.getResources().getDisplayMetrics().widthPixels;
 		int heightMax = activity.getResources().getDisplayMetrics().heightPixels;
 		
 		int imageWidth = vegetableImage.getWidth()/2;
 		int imageHeight =vegetableImage.getHeight()/2;
 		
 		growthMultiplierX = vegetableImage.getWidth()/20;
 		growthMultiplierY = vegetableImage.getHeight()/20;
 		
 		Random r = new Random();
 		int xPosOffset = r.nextInt(widthMax + 1);
 		if(xPosOffset + imageWidth >= widthMax)
 		{
 			xPosOffset -= imageWidth;
 		}
 		else if (xPosOffset - imageWidth <= 0)
 		{
 			xPosOffset += imageWidth;
 		}
 		
 		//Not sure why the values had to be multiplied at this point
 		//setBoundingRect(new Rect(xPosOffset,heightMax-vegetableImage.getHeight()*3,xPosOffset+vegetableImage.getWidth(), heightMax-vegetableImage.getHeight()*2));
 		
 		setBoundingRect(new Rect(xPosOffset - (imageWidth/2),(heightMax/2) - imageHeight /2,xPosOffset + (imageWidth/2),(heightMax/2) + imageHeight /2)); 	 	
 	}
 	
 	public void drawVegetables(Canvas canvas, ArrayList<Vegetable> veges)
 	{
 		for(int i = 0; i<veges.size(); i++)
 		{
 			canvas.drawBitmap(veges.get(i).getVegetableImage(), null, veges.get(i).getBoundingRect(), null);
 		}
 	}
 	
	/** 
	* Updates the the vegetable by reducing finalAge, waterLevel,foodLevel, updates mood
	*/ 	
 	public void update ()
 	{
 		this.finalAge -= 1;
 		this.foodLevel -= hungerRate;
 		this.waterLevel -= thirstRate;
 		
 		if (!isDead())  // if vegetable is alive 
 		{
 			grow (); // it grows
 					 // image resizes by changing the rectangle size
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
 			setMood(Mood.HAPPY);
 		}
 		
 		if (score >= 100)
 		{
 			setMood(Mood.OK);
 		}
 		
 		if (score <= 100)
 		{
 			setMood(Mood.SAD);
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
	* Increments the size of the Vegetable bitmap rescaling the rectangle
	* Used for showing vegitable growth
	*/ 
 	public void  resizeImage()
 	{
 		boundingRect.left -= growthMultiplierX;
 		//boundingRect.top -= growthMultiplierY;
 		//boundingRect.right += growthMultiplierX;
 		boundingRect.bottom += growthMultiplierY;
 	}
 	
 	
	/** 
	* I dont understand the purpose of this method to implement it 
	*/
 	public void move ()
 	{
 		
 	}
 	
	/** 
	* Initialize an Image to the vegetable
	*@param vegeBitmap The bitmap containing the image to be initialized
	*/
 	public void setVegetableImage(Bitmap vegeBitmap)
 	{
 		vegetableImage = vegeBitmap;
 	}
 	
	public Bitmap getVegetableImage()
	{
		return vegetableImage;
	}
 	
	public String getTypeStr() {
		return typeStr;
	}

	public void setTypeStr(String typeStr) {
		this.typeStr = typeStr;
	}

	public String getPersonalityStr() {
		return personalityStr;
	}

	public void setPersonalityStr(String personalityStr) {
		this.personalityStr = personalityStr;
	}

	public int getVegeId() {
		return vegeId;
	}

	public void setVegeId(int vegeId) {
		this.vegeId = vegeId;
	}

	public VegetableType getType() {
		return type;
	}

	public void setType(VegetableType type) {
		this.type = type;
	}

	public int getCurrentAge() {
		return currentAge;
	}

	public void setCurrentAge(int currentAge) {
		this.currentAge = currentAge;
	}

	public double getFinalAge() {
		return finalAge;
	}

	public void setFinalAge(double finalAge) {
		this.finalAge = finalAge;
	}

	public Mood getMood() {
		return mood;
	}

	public void setMood(Mood mood) {
		this.mood = mood;
	}

	public Personality getPersonality() {
		return personality;
	}

	public void setPersonality(Personality personality) {
		this.personality = personality;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getCondition() {
		return condition;
	}

	public void setCondition(int condition) {
		this.condition = condition;
	}
	
	public int getWaterLevel()
	{
		return waterLevel;
	}
	
	public void setWaterLevel(int waterLevel){
		this.waterLevel = waterLevel;
	}
	
	public int getFoodLevel(){
		return foodLevel;
	}
	
	public void setFoodLevel(int foodLevel){
		this.foodLevel = foodLevel;
	}
	
	public int getShadeLevel(){
		return shadeLevel;
	}
	
	public void setShadeLevel(int shadeLevel){
		this.shadeLevel = shadeLevel;
	}
	
	public double getHungerRate(){
		return hungerRate;
	}
	
	public void setHungerRate(double hungerRate){
		this.hungerRate = hungerRate;
	}
	
	public double getThirstRate(){
		return thirstRate;
	}
	
	public void setThirstRate(double thirstRate){
		this.thirstRate = thirstRate;
	}

//	public int getLocationX() {
//		return locationX;
//	}
//
//	public void setLocationX(int locationX) {
//		this.locationX = locationX;
//	}
//
//	public int getLocationY() {
//		return locationY;
//	}
//
//	public void setLocationY(int locationY) {
//		this.locationY = locationY;
//	}

	public Rect getBoundingRect() {
		return boundingRect;
	}

	public void setBoundingRect(Rect boundingRect) {
		this.boundingRect = boundingRect;
	}
}
