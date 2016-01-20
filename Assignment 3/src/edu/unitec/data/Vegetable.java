/**
 * Our model class to store all the vegetable data
 * @author 	Lance Donnell and Dhaneesha Rajakaruna
 * @version 1.0
 * @since 	2016-01-13
 */

package edu.unitec.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
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
	private Calendar createdDate;
	private Calendar currentDate; 
	private int currentAge; 
	private double finalAge; // unique
	private double agingRate = 0.1;
	
	//WATERLVL, FOODLVL, SHADELVL
	private int waterLevel; 
	private int foodLevel;
	private int shadeLevel;
	private int maxWaterLevel = 100;
	private int maxFoodLevel = 100;

	//HUNGERRATE, THIRSTRATE, GROWTH
	private double hungerRate; 
	private double thirstRate; 
	private double growthRate;	// Higher number mean slower growth
	private boolean growthHindered =false;

	//PERSONALITY
	public enum Personality {AGGRESIVE,HARDY,TIMID}
	private String personalityStr;	//the personality of the vegetable as a string, used when loading the game
	private Personality personality; 
	
	//SIZE
	private double size;
	private int growthMultiplierX; // This is a small value initialized during vegetable creation which is used set the image size by multiplying with the current size of vegetable 
	private int growthMultiplierY;
	private int growthCounter=1;

	//STATUS
	private String status;	//defaults to LIVING (two values are LIVING and DEAD)
	private boolean dead = false;	
	private boolean eaten=false;

	//CONDITION
	private int condition;
	
	//The following values are not recorded in the database recorded in database:
	public enum Mood {HAPPY, OK, SAD, ANGRY}	//determines the mood of the vegetable
	//Mood will be defined based on current statistics
	private Mood mood;
	
	//CANNIBAL  
	private boolean cannibalistic = false;
	private int canibalPoints = 0;
	
	private Bitmap vegetableImage;

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
 		this.createdDate = Calendar.getInstance(); 
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
        if(personalityStr.equals("AGGRESIVE"))
        	this.personality = Personality.AGGRESIVE;
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
	* Unique values are initialized here
	*/ 	
 	private void setVegetableStats()
 	{				
 		switch (type) {
		case CABBAGE:
			this.finalAge = 100; 
			this.hungerRate = 0.01; 
			this.thirstRate = 0.1; 
			this.growthRate = 2;
			this.personality = Personality.HARDY; 
			this.size = 10;
			this.vegetableImage = BitmapFactory.decodeResource(activity.getResources(), edu.unitec.assignment3.R.drawable.cabbage);			 
			break;
		
		case CARROT:
			this.finalAge = 50; 
			this.hungerRate = 0.05; 
			this.thirstRate = 0.5;
			this.growthRate = 4; // was 3 but for debugging made this a bigger value to easily to see progress 
			this.personality = Personality.AGGRESIVE; 
			this.size = 11;
			this.vegetableImage = BitmapFactory.decodeResource(activity.getResources(), edu.unitec.assignment3.R.drawable.carrot);
			break;
			
		case POTATO:
			this.finalAge = 60; 
			this.hungerRate = 0.04; 
			this.thirstRate = 0.03;
			this.growthRate = 5;
			this.personality = Personality.TIMID; 
			this.size = 6; 
			this.vegetableImage = BitmapFactory.decodeResource(activity.getResources(), edu.unitec.assignment3.R.drawable.potato);
			break;
		
		case EGGPLANT:
			this.finalAge = 80; 
			this.hungerRate = 0.02; 
			this.thirstRate = 0.02;
			this.growthRate = 3;
			this.personality = Personality.HARDY; 
			this.size = 18;
			this.vegetableImage = BitmapFactory.decodeResource(activity.getResources(), edu.unitec.assignment3.R.drawable.eggplant);
			break;

		case NULL:
			break;
		}
 	}
 	
 	/**
 	 * retrieves the size of the screen and creates the bounding rectangle around the vegetable from those values
 	 * Randomly selects the vegetable's starting position on the x axis
 	 * @return void 
 	 */
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
 		
 		setBoundingRect(new Rect(xPosOffset - (imageWidth/2),(heightMax/2) - imageHeight /2,xPosOffset + (imageWidth/2),(heightMax/2) + imageHeight /2)); 		
 	}
 	
 	/**
 	 * Takes an ArrayList of vegetables and draws them to the provided canvas as well as checking if the vegetable has been eaten
 	 * @param canvas		the canvas the vegetable should be drawn on
 	 * @param veges			the vegetables to be drawn
 	 */
 	public void drawVegetables(Canvas canvas, ArrayList<Vegetable> veges)
 	{
 		if (eaten)
 		{
 			setEatenVegetables();
 		}
 		
 		for(int i = 0; i<veges.size(); i++)
 		{
 			canvas.drawBitmap(veges.get(i).getVegetableImage(), null, veges.get(i).getBoundingRect(), null);
 		}
 	}
 	
	/** 
	* Updates the the vegetable by reducing finalAge, waterLevel,foodLevel, updates mood
	* Also controls the growth rate based on food and water level
	*/ 	
 	public void update ()
 	{
 		this.finalAge -= agingRate;
 		this.foodLevel -= hungerRate;
 		this.waterLevel -= thirstRate;
 		currentDate = Calendar.getInstance();
 		currentAge= currentDate.get(Calendar.MINUTE) - createdDate.get(Calendar.MINUTE);
 		
 		if (!isDead())  // if vegetable is alive 
 		{
 			grow (); // it grows image resizes by changing the rectangle size
 			calculateMood();
 			checkGrowthRate();
 			cannibalPoints();
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
 		
 		else if (score >= 100)
 		{
 			setMood(Mood.OK);
 		}
 		
 		if (score <= 100)
 		{
 			setMood(Mood.SAD);
 		}
 		
 		if (foodLevel < 15)
 		{
 			setMood(Mood.ANGRY);
 			setCannibalistic(true);
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
 			finalAge =0; 
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
 		if(foodLevel + food <= maxFoodLevel) // Controlling the food level 
 			this.foodLevel += food;		
 		else
 		{
 			this.foodLevel = maxFoodLevel; 
 		}
 	}
 	
	/** 
	* Increments the waterlevel of the Vegetable
	* @param water how much water to increment
	*/ 
 	public void irrigate (int water)
 	{
 		if(waterLevel + water <= maxWaterLevel)
 			this.waterLevel += water;
 	}
 	
	/** 
	* Increments the size of the Vegetable bitmap rescaling the rectangle
	* Used for showing vegitable growth
	*/ 
 	public void  grow() // this controlls how fast a vegetable grows
 	{
 		if (growthCounter == (int) growthRate) // when the growth rate reaches growth counter value the  vegetable wil grow
 		{
			boundingRect.right += growthMultiplierX; // two values are used to maintain the aspect ratio of the vegetable bitmap
			boundingRect.bottom += growthMultiplierY; // with each time a vegetaable grow the size of the boundingRect will get bigger
			growthCounter =1;							// and growth counnter will be set back to one 
 		}
 		else
 		{
 			growthCounter++; // If the vegetable didnt grow the growh counter increases till its ready to grow (till growthCounter == growthRate)
 		}		
 	}
 	
	/** 
	* Checks the current condition of the veges and decides the growth rate
	*/
 	public void checkGrowthRate()
 	{		
		if (waterLevel < maxWaterLevel/2 || foodLevel < maxFoodLevel/2)  // if food level is at a 50% the vegetable will not grow very rapidly 
		{
			growthRate *=2;			// this is achived by multiplying the growth counter by 2 
									// When growth rate 2X  higher this mean the update method has to be called two times the growth valueto have one grow cycle
									// This effectivly cuts down growth in half
			growthHindered = true;	// & sets this variable to make sure the code runs smooth 
		}
		else if (growthHindered) // If water and food level is back to over 50 then normal grow conditions apply
		{
			growthRate /=2;
			growthHindered = false;
		}
 	}
 	
	/** 
	* This method will calculate the cannibal points which will be based on the vegetables personality and condidion
	* Depending on the points the system will decide weather to eat a vege or leave it
	* Also considers current status of the vegetable
	*/
 	public void cannibalPoints()
 	{
 		setCanibalPoints(0); // reset previously set points
 		switch (personality) {
		case AGGRESIVE:
			setCanibalPoints(getCanibalPoints() + 50); // aggresive vegetables get more points to improve their chance of eating another vegatable like in real life
			break;			
		
		case HARDY:
			setCanibalPoints(getCanibalPoints() + 40); // Hardy ones are are also quite likly to cannibalize
			break;	
			
		case TIMID:
			setCanibalPoints(getCanibalPoints() + 10); // Timed is scaread and most likely wont eat anything else
			break;

		default:
			break;
		}
 		
 		setCanibalPoints(getCanibalPoints() + foodLevel+waterLevel); // setting the new points	(The current water level and food directly effects a vegetabless capacity to canibalize)	
 	}
 	
	/** 
	* Onece a vegetable has been eaten its appearance changes to the image of the eaten vegetable with a chunk missing
	*/
 	public void setEatenVegetables()
 	{
 		if (! eaten) // checks weather the vegetable was eaten
 		{
 			return;
 		}
 		switch (type) 
 		{ // If it was eaten assignes the proper image depending on the vegetable type
		case CARROT:
			this.vegetableImage = BitmapFactory.decodeResource(activity.getResources(), edu.unitec.assignment3.R.drawable.eaten_carrot);
			break;			
		
		case POTATO:
			this.vegetableImage = BitmapFactory.decodeResource(activity.getResources(), edu.unitec.assignment3.R.drawable.eaten_potato);
			break;	
			
		case CABBAGE:
			this.vegetableImage = BitmapFactory.decodeResource(activity.getResources(), edu.unitec.assignment3.R.drawable.eaten_cabbage);
			break;

		case EGGPLANT:
			this.vegetableImage = BitmapFactory.decodeResource(activity.getResources(), edu.unitec.assignment3.R.drawable.eaten_eggplant);
			break;
			
		default:
			break;
		} 		
 		setCanibalPoints(getCanibalPoints() + foodLevel+waterLevel); 		
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

	public Rect getBoundingRect() {
		return boundingRect;
	}

	public void setBoundingRect(Rect boundingRect) {
		this.boundingRect = boundingRect;
	}

	public boolean isCannibalistic() {
		return cannibalistic;
	}

	public void setCannibalistic(boolean cannibalistic) {
		this.cannibalistic = cannibalistic;
	}

	public int getCanibalPoints() {
		return canibalPoints;
	}

	public void setCanibalPoints(int canibalPoints) {
		this.canibalPoints = canibalPoints;
	}
	
	public void setEaten(boolean eaten) {
		this.eaten = eaten;
	}
	
	public boolean getEaten() {
		return this.eaten ;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}
	public boolean getDead() {
		return this.dead ;
	}
}