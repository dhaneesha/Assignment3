/**
 * Our model class to store all the vegetable data
 * Created 13.01.16
 */

package edu.unitec.data;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Vegetable {
	
	public enum VegetableType {  
		CABBAGE,
		CARROT,
		POTATO,
		EGGPLANT,
		NULL
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
	
	private String typeStr;
	private String personalityStr;
	
	private int vegeId;	//unique
	
	private VegetableType type; // unique
	private int currentAge; 
	private double finalAge; // unique
	private int waterLevel; 
	private int foodLevel;
	private int shadeLevel;
	//double temperature; // We dont need shade and temperature. Either will work
	private double hungerRate; // unique
	private double thirstRate; // unique
	private double growthRate;
	public int testingGrowth=1; // temporary varible to test the growth of Vege
	
	private Mood mood;
	private Personality personality; // unique
	private double size; // unique
	private int location = 200;  // just initializing some value for ease
	private String status = "LIVING";
	private int condition = 100;
	
	private boolean cannibalize = false;
	private boolean dead = false;
	
	public Bitmap vegetableImage ;
	
	//
	int width = 20;
	int height = 30;
	//
	/** 
	* Vegetable constructor.
	* Sets up the default values for vegetables
	*/
 	public Vegetable ()
 	{
 		this.setCurrentAge(0); 
 		this.waterLevel = 100; 
 		this.foodLevel = 100;
 		this.shadeLevel = 100;
 		this.setMood(Mood.HAPPY);		
 	}
 	
 	//Used to 'load game'
 	public Vegetable (String savedVege)
 	{
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
 	}
 	
	/** 
	* Setup unique values depending on vegetable type.
	* Sets up the default values for vegetables
	* @param vType values will be initialized depending on the VegetableType
	*/ 	
 	public void setVegetableType(VegetableType vType)
 	{		 		
 		switch (vType) {
		case CABBAGE:
			 setFinalAge(100); 
			 hungerRate = 1; 
			 thirstRate = 1; 
			 growthRate = 2;
			 setPersonality(Personality.HARDY); 
			 setSize(200); 
			break;
		
		case CARROT:
			 setFinalAge(50); 
			 hungerRate = 5; 
			 thirstRate = 5;
			 growthRate = 3; // was 3 but for debugging made this a bigger value to easily to see progress 
			 setPersonality(Personality.SASSY); 
			 setSize(130); 
			break;
			
		case POTATO:
			 setFinalAge(60); 
			 hungerRate = 5; 
			 thirstRate = 3;
			 growthRate = 3;
			 setPersonality(Personality.TIMID); 
			 setSize(120); 
			break;
		
		case EGGPLANT:
			 setFinalAge(80); 
			 hungerRate = 2; 
			 thirstRate = 2;
			 growthRate = 4;
			 setPersonality(Personality.HARDY); 
			 setSize(180); 
			break;

		case NULL:
			break;
		}	
 	}
 	
	/** 
	* Updates the the vegetable by reducing finalAge, waterLevel,foodLevel, updates mood
	*/ 	
 	public void update ()
 	{
 		setFinalAge(getFinalAge() - 1);
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
 		setSize(getSize() + growthRate);
 	}
 	
	/** 
	* I dont understand the purpose of this method to impliment it 
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
 		vegetableImage= vegeBitmap;
 	}
 	
	/** 
	* draw the vegetable on a provided canvas at a given position with a givven paint object
	* @param canvas the canvas to be drawn on
	* @param x coordinate of the canvas
	* @param y coordinate of the canvas
	* @param paint object which has all the settings to draw the vegetable
	* 
	* Gives and error when tring to rescale with usin variables
	* Probabaly due to threads clashing
	* Might need to delete this method
	* Alternative is drawing through GameView
	*/
 	public void drawVegetable(Canvas canvas,float x, float y,Paint paint )
 	{		

 	}
 	
	/** 
	* Not in original plan. Just somthing to check communication and methods
	*/
 	public void testingFeed()
 	{
 		testingGrowth++;
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

	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
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
}
