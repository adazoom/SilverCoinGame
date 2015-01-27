import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class SilverCoinGUI extends JPanel implements KeyListener ,MouseListener{
	
	private AZhuma_MyGame game;
	private int blockSize;
	private Ellipse2D oval;	
	private Rectangle2D rect; 
	private int firstClick;
	private int secClick;
	private ArrayList<Shape> shapes = new ArrayList<Shape>();
	 private Image alfred;
	
	 /**
	 * Constructor initiates the game
	 */
	public SilverCoinGUI () 
	{
		super();	
		setBackground(generateRandomColor(Color.PINK));
		 Random rand = new Random(); 
		 int range = 11 -7  + 1;
		 int r = rand.nextInt(range) + 7; 
		 loadImage();
		//create new game
		game= new AZhuma_MyGame(r);
		blockSize=computeBlockSize();
		firstClick=0;
		secClick=0;
		//register mouse events on the component
		addMouseListener(this);
		setFocusable(true);
		addKeyListener(this);
		
		
	}
	
	   @Override
	   public Dimension getPreferredSize() {
	      return new Dimension(800,400);
	   }
	   
	   public static Color generateRandomColor(Color mix) {
		   Random random = new Random();
		   int red = random.nextInt(256);
		   int green = random.nextInt(256);
		   int blue = random.nextInt(256);

		   // mix the color
		   if (mix != null) {  
		     red = (red + mix.getRed()) / 2;
		     green = (green + mix.getGreen()) / 2;
		     blue = (blue + mix.getBlue()) / 2;
		   }

		   Color color = new Color(red, green, blue);
		   color.getHSBColor(60, 90, 90);
		   return color;
		 }
	   
	   
	/**
	 * Handle mouse click
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		Iterator<Shape> iter = shapes.iterator();
		while (iter.hasNext()) {
			Shape clicked = iter.next();
			if (clicked.contains(e.getX(), e.getY())) {
				
				if (clicked instanceof Rectangle2D) {
					if (firstClick != 0 && secClick == 0) {
						secClick = clickedRectIs(e.getX());
						if (firstClick > secClick) {
							System.out.println("firstClick " + firstClick
									+ " secClick " + secClick);
							game.getSg().userClicked(firstClick, secClick);
							repaint();
						} else if (firstClick < secClick) {
							JOptionPane.showMessageDialog(null,
									"Can only move to the left!");
						}
					}
					firstClick = 0;
					secClick = 0;
					this.removeMouseListener(this);
				}
				if (clicked instanceof Ellipse2D) {
					if (firstClick == 0) {
						firstClick = clickedRectIs(e.getX());
					}
					else if(firstClick!=0){
						JOptionPane.showMessageDialog(null,
								"You have already selected the coin");
					}
				}
			}
		}
		
		if (game.getSg().gameOver()) {
			JOptionPane.showMessageDialog(null, "Congratulations! You won, smart one! ");
			System.exit(0);
		}
		
	}

	/**
	 * Method to get the number of the square
	 */
	public int clickedRectIs(double x){
		int numIs=0;
		for(int i=0; i<game.getSg().squares; i++){
			if(x>50+blockSize*i && x<(50+(i+1)*blockSize)){
				numIs=i;
			}
		}
		return numIs;
	}

	/**
	 * Behaviour after the key is clicked
	 */
	public void keyPressed( KeyEvent e)
	{
		int keyCode = e.getKeyCode();
		//handle the event
	    switch( keyCode ) { 
	        case KeyEvent.VK_SPACE:
	            // move random coin by random number 
	        	game.getSg().compMove();
	        	repaint();
	        	if(game.getSg().gameOver()){
	        		JOptionPane.showMessageDialog(null,"Game over. You lost. Can't beat a computer. Go home!");
	        		System.exit(0);
	        	}
	            break;
	     }
		this.addMouseListener(this);
	}

	/**
	 * Behaviour after the key is released
	 */
	public void keyReleased(KeyEvent e)
	{
		// Handle the key-released event.
	}
	
	/**
	 * Behaviour after the smthng is typed in
	 */
	public void keyTyped(KeyEvent e)
	{
		//Handle the key typed event.
	}

	/**
	 * Compute the best block size for the current width and height.
	 */
	private int computeBlockSize() 
	{
		int size=  getPreferredSize().width/game.getSg().squares+30;
		return size;
	}
	
	/**
	 * Paint
	 */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		shapes.removeAll(shapes);
		int j=1;
		for(int i=0; i<game.getSg().squares; i++){
			if(game.getSg().stripArray[i]==0){
				// Concatenate with another string
				drawRect(g,i);
			}
		else{
				drawCoin(g,i);
				//change the value of a coin in the array to remember it. 
				game.getSg().stripArray[i]=j;
				j++;
		}					
		}
	
	}
	
	public void drawRect(Graphics g, int row){
		Graphics2D g2d = (Graphics2D) g;
		int xMin= row*blockSize+50;
		// fill with the color
		
		g2d.setColor(Color.getHSBColor(70, 80, 90)); 
		rect = new Rectangle2D.Double(xMin, 20, blockSize, blockSize);
		g2d.fill(rect); 
		g2d.setColor(Color.BLACK); 
		g2d.draw(rect);
		shapes.add(rect);

	}
	public void drawCoin(Graphics g, int row){
		Graphics2D g2d = (Graphics2D) g;
		int xMin= row*blockSize+50;	
		g.setColor(Color.getHSBColor(70, 80, 90));
		g.fillRect(xMin, 20, blockSize, blockSize);
		g.setColor(Color.BLACK); 
		g.drawRect(xMin, 20, blockSize, blockSize);
		
		oval = new Ellipse2D.Double(xMin+10, 25, blockSize-20, blockSize-20);

		g2d.drawImage(alfred, xMin+10, 25, blockSize-20, blockSize-20, null);
		
		Color c=new Color(0f,0f,0f,.1f );
		g2d.setColor(c); 
		g2d.fill(oval);
		
		
		shapes.add(oval);

	}
	  private void loadImage() {

	        alfred = new ImageIcon("coin.png").getImage();
	    }
	
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	/**
	*Mouse is clicked
	**/
	 public void mousePressed(MouseEvent e)
	 {
	 	
     }
}
