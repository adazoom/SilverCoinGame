/**
	 * Main Application to set up the Game
	 */

import javax.swing.JFrame;

public class SilverCoinApplication {
	
		
		/**
		 * Constructor
		 */
		public static void main(String[] args)
		{
			//start the game
			SilverCoinGUI game=new SilverCoinGUI();
			JFrame silverCoinFrame = new JFrame("Play with Alfredina II");
			silverCoinFrame.setSize( 1200, 200 );
			silverCoinFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			silverCoinFrame.add( game );
			silverCoinFrame.setVisible( true );
		}
	}


