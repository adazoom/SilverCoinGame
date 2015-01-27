/** Should set up the board and read input
 * *
 * @author zhuma22a
 *
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class AZhuma_MyGame {
	// fields
		private AZhuma_SilverGame sg;
		//player indicator
		private String player;

		
		
		/**
		 * Constructor initializes variables.
		 */
		public AZhuma_MyGame(int size, int amount) 
		{
			//initialize variables
			sg= new AZhuma_SilverGame(size, amount);
			
			
		}

		/**
		 * Constructor. No amount of coins
		 */
		public AZhuma_MyGame(int size) 
		{
			//call the constructor with random number of coins
			this(size, calculateRanCoins(size));
		}
		/**
		 * Calculate amount of coins if not specified by user. Called every time. 
		 * @return 
		 */
		public static int calculateRanCoins(int n){
			//generate random num
		    Random rand = new Random();
		    //minimum amount of coins is 3. Range is exclusive
		    int range = n- 4 + 1;
		    int random = rand.nextInt(range)+2;
		    if(random>5)
		    return random-3;
		    return random;
		}
		
		

		public AZhuma_SilverGame getSg() {
			return sg;
		}
}
