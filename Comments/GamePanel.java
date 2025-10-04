import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

import javax.swing.JPanel;
//imports

public class GamePanel extends JPanel implements ActionListener{ //extends jpanel & implements actionlistener interface
	
	static final int SCREEN_WIDTH = 600; //screen width
	static final int SCREEN_HEIGHT = 600; //screen height
	static final int UNIT_SIZE = 25; //how big our game objects will be (25 pixels)
	static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE; //how many objects we can fit in
	static final int DELAY = 75; //higher the number slower the game
	final int x[] = new int[GAME_UNITS]; 
	final int y[] = new int[GAME_UNITS];
	//coordinates for body parts of the snake
	
	int bodyParts = 6; //initial amount of body parts of the snake
	int applesEaten; //initially 0
	int appleX;
	int appleY;
	//apple location
	char direction = 'R'; //direction of the snake in the beginning
	boolean running = false;
	Timer timer; //declaring timer
	Random random; //instance of the random class
	
	
	GamePanel(){ //constructor
		
		random = new Random(); //create instance of the random class
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT)); //dimensions
		this.setBackground(Color.black); //background color
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter()); //keyListener
		startGame(); //calling start game method
	}
	
	public void startGame() { //method
		
		newApple(); //calling newApple method for creating a new apple on screen
		running = true; //setting to true cause it's false to begin with
		timer = new Timer(DELAY,this); //passing delay value ;) using action listener interface
		timer.start(); //starting
		
	}
	
	public void paintComponent(Graphics g) { //method and parameter graphics g
		
		super.paintComponent(g); //passing g
		draw(g); //passing g
		
	}
	
	public void draw(Graphics g) { //method and parameter graphics g
		
		if(running) { //if game is running, do all these
			
			//Code for grid lines start here :)
			
			for(int i = 0; i < SCREEN_HEIGHT/UNIT_SIZE; i++) {
				
				g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
				g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
					//inserting starting and ending coordinates
			}
			
			//And ends here :) Remove if necessary :)
			
			
			g.setColor(Color.red); //setting apple color
			g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE); //how large the apple is
			
			for(int i = 0; i < bodyParts; i++) { //iterating all the body parts of the snake
				
				if(i==0) { //head of the snake
					
					g.setColor(Color.green); //setting color
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
				else {
					g.setColor(new Color(45, 180, 0)); //different shade of green
					
					//Random color generator code
					g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
					//Random color generator code^^
					
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
			}
			g.setColor(Color.red);
			g.setFont(new Font("Ink Free", Font.BOLD, 40));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score:"+applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
		}
		else { //else we are calling the game over method
			
			gameOver(g);
			//g is our graphics that we are receiving
		}
	}
	
	public void newApple() { //creating a new apple on the screen
		
		appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
		
		//casting as integer just to be safe
		//coordinates of the randomly appearing apple
		//generating a new apple everytime
	}
	
	public void move() { //method for moving the snake
		
		for(int i = bodyParts; i > 0; i--) { //iterating all the body parts of the snake
			
			//shifting all the body parts of the snake around the map
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		
		switch(direction) { //changing our snake direction
		
		case 'U': //up
			y[0] = y[0] - UNIT_SIZE;
			break;
			
		case 'D': //down
			y[0] = y[0] + UNIT_SIZE;
			break;
			
		case 'L': //left
			x[0] = x[0] - UNIT_SIZE;
			break;
			
		case 'R': //right
			x[0] = x[0] + UNIT_SIZE;
			break;
		}
	}
	
	public void checkApple() { //snake eats apple method
		
		if((x[0] == appleX) && (y[0] == appleY)) {
			
			//x and y position of snake and apple
			bodyParts++; //incrementing by 1
			applesEaten++; //increasing score by 1
			newApple(); //generating a new apple after one is eaten
			
			//examining the coordinates of the snake and apple
		}
		
		
	}
	
	public void checkCollisions() { //checking for collisions method
		//checks if head collides with body
		for(int i = bodyParts; i > 0; i--) {
			
			if((x[0] == x[i]) && (y[0] == y[i])) {
				
				running = false;
			}
		}
		//checks if head touches left border
		if(x[0] < 0) {
			
			running = false;
		}
		//checks if head touches right border
		if(x[0] > SCREEN_WIDTH) {
			
			running = false;
		}
		//checks if head touches top border
		if(y[0] < 0) {
			
			running = false;
		}
		//check if head touches bottom border
		if(y[0] > SCREEN_HEIGHT) {
			
			running = false;
		}
		
		if(!running) {
			
			timer.stop(); //stopping the timer
		}
			
		}
	public void gameOver(Graphics g) { //gameover method and parameter graphics g
		
		//displaying Score on gameOver screen
		g.setColor(Color.red); //setting color
		g.setFont(new Font("Ink Free", Font.BOLD, 40)); //fonts
		FontMetrics metrics1 = getFontMetrics(g.getFont()); //instance of fontMetrics
		g.drawString("Score:"+applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
		//putting text on the top^
		
		
		//Game Over text
		g.setColor(Color.red); //setting color
		g.setFont(new Font("Ink Free", Font.BOLD, 75)); //fonts
		FontMetrics metrics2 = getFontMetrics(g.getFont()); //instance of fontMetrics
		g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
		//putting text on the center^
		
	}
//unimplemented methods
	@Override
	public void actionPerformed(ActionEvent e) { //actionPerformed method
		
		if(running) { //if game is running
			
			move(); //moving the snake
			checkApple(); //if we eat the apple
			checkCollisions(); //if we hit the wall
		}
		repaint(); //if game not running
		
	}
	
	public class MyKeyAdapter extends KeyAdapter{ //interclass extends keyAdapter
		
		@Override
		public void keyPressed(KeyEvent e) { //method & parameter keyEvent e
			
			switch(e.getKeyCode()) {
			
			case KeyEvent.VK_LEFT: //left arrow key
				
				if(direction != 'R') { //limiting user to 90 degree turns (no 180 degree turns)
					
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT: //right arrow key
				
				if(direction != 'L') { //limiting user to 90 degree turns (no 180 degree turns)
					
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				
				if(direction != 'D') { //limiting user to 90 degree turns (no 180 degree turns)
					
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				
				if(direction != 'U') { //limiting user to 90 degree turns (no 180 degree turns)
					
					direction = 'D';
				}
				break;
			
			}
		}
	}
}