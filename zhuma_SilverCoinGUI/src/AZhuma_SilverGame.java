/** 
 * Maintains the staus of game
 * @author zhuma22a
 *
 */
import java.util.Random;
import java.util.Arrays;

public class AZhuma_SilverGame {
	//fields
	public  int squares;
	public  int coins;
	
	// array representing paper strip
	 protected int[] stripArray; 
	// randomnly generated number 
	Random random = new Random();
	int coinsOn=0;
		
		
	/**
	 * Constructor
	 **/
	public AZhuma_SilverGame(int sq, int cn) {
		//get the number of squares and coins
		squares=sq;
		coins=cn;
		// set up the strip
		initArray();
		//set up coins
		setCoins();
		System.out.println("squares "+ squares);
		System.out.println("coins "+ coins);
	
	}

	/**
	 * Initiate strip with falses
	 **/
	private void initArray() {
		
		// set up the board with falses
		stripArray = new int[squares];
		for (int i = 0; i < stripArray.length; i++) {
				// fill the array with falses
				stripArray[i] = 0;	
			}
	}
	/**
	 * Create and add coins to the strip
	 **/
	public void setCoins() {
		//var to represent the coin
		boolean coinPlaced;
		int block;
		
		//assign true to the random block for each coin
		for(int i=0; i<coins; i++)
		{ 	do{
			//get random block in the strip
			 int range = squares - 2;
			 block= random.nextInt(range)+2;
			
			if(stripArray[block]== -1)
			{
				coinPlaced= true;
			}
			else
			{
				coinPlaced= false;
			}
		}while (coinPlaced);
		//set the boolean in strip to true for a coin
		stripArray[block]= -1;
		}
		

	}
	/**
	 * Figure out which coin was clicked and where to move it
	 */
	public void userClicked(int first, int sec){
		//first click and second click represents the number of square it was clicked on
		// see which coin was clicked
			int numCoin=stripArray[first];
		//figure out by how many blocks to move
			int bySteps=first-sec;
			moveCoin(numCoin,bySteps);	
	}
	/**
	 * This method moves the coin to the left if the move is valid
	 * @return
	 */
	public boolean moveCoin(int num, int by) {
		
		// catch any out of bounds exceptions
		try {
			if (num <= coins) {
				// iterate through the array and find the chosen coin
				for (int i = 0; i < stripArray.length; i++) {
					if (stripArray[i] == num) {
						if (validMove(i, by)) {
							// check if the block holds any coins already
							if (stripArray[i - by] == 0) {
								stripArray[i - by] = num;
								stripArray[i] = 0;
								return true;
							} else {
								System.out.println("Cannot place two coins on the one slot.");
							}
						}
					}

				}
			}// if cannot find entered coin in the array there is no such a coin
			else {
				System.out.println("The coin number " + Integer.toString(num)
						+ " doesn't exist. Try another one");
			}
			
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Cannot move the coin by the "
					+ Integer.toString(by) + " steps. Try again");
		}
		return false; 
	}
	/**
	 * Algorithm for the coin to be moved randomly
	 */
	public void compMove(){
		//rightmost coin should be move by two or one space if it can be moved
		//two spaces if the number of possible moves is even
		//one space if num of pos moves is odd
		lolYouAreLoser();
		int movesPos=calcEmpty();
		if(movesPos%2==0){
			evenMove();
		}else{
			oddMove();
		}
   	}
	/**
	 * if only one move is possible
	 */
	public void lolYouAreLoser(){
		int count=0;
		for(int i=0; i<coins;i++){
			if(!(stripArray[i]==0)){
				count++;
			}else{
				break;
			}
		}
		if(count==coins-1)//its a easy win!!
		{
			//find the coin
			for (int i = 0; i < stripArray.length; i++){
				if (stripArray[i] == coins) {
					stripArray[count]=stripArray[i];
					stripArray[i]=0;
				}
			}
		}else{
			return;
		}
		
		
	}
	/**
	 * method to move by two slots
	 */
	public void evenMove(){
		try{
		for (int j = stripArray.length-1; j>0 ; j--){
			if(stripArray[j]!=0){
				//determine if this coin can move two slots
				
				if(stripArray[j-1]==0 && stripArray[j-2]==0){
					stripArray[j-2]=stripArray[j];
					stripArray[j]=0;
					break;
				}
			}
		}
		}catch(ArrayIndexOutOfBoundsException e){
			System.out.println("out of bounds");
			oddMove();
		}
			
		}
		

	/**
	 * method to move by one slot
	 */
	public void oddMove(){
		for (int j = stripArray.length-1; j>0 ; j--){
			if(stripArray[j]!=0){
				//determine if this coin can move two slots
				if(stripArray[j-1]==0){
					stripArray[j-1]=stripArray[j];
					stripArray[j]=0;
					break;
				}
			}
		}
	}
	/**
	 * Calculate empty slots
	 */
	public int calcEmpty(){
		//for every coin calculate the number of empty slots before it
				int totalEmpty=0;
				for (int j = 0; j < stripArray.length; j++){
					if(stripArray[j]!=0){
						for(int i=0;i<j;i++){
							if(stripArray[i]==0){
								totalEmpty++;
							}
						}
					}
				}
				return totalEmpty;
	}
	/**
	 * Method to check whteher the move is valid, ie No coin may pass another.
	 */
	public boolean validMove(int pos, int st){
		for(int j=pos-1; j>=pos-st; j--){
			//check if there are any coins before the chosen one
			if(!(stripArray[j]==0)){
				System.out.println("The move is not valid. No coin may pass another.");
				return false;
			}
		}
		return true;
	}
	/**
	 * Check whether the game is over
	 */
	public boolean gameOver(){
		//check whether the most left blocks are filled 
		int count=0;
		for(int i=0; i<coins;i++){
			if(!(stripArray[i]==0)){
				count++;
			}
		}
		if(count==coins)//the game is over
		return true;
		
		return false;
	}

	
}
