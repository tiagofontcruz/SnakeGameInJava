package snake;                                                                                     //
                                                                                                   //
import java.awt.*;                                                                                 //
import java.awt.event.*;                                                                           //
import java.io.BufferedReader;                                                                     //
import java.io.File;                                                                               //
import java.io.FileReader;                                                                         //
import java.io.IOException;                                                                        //
import java.nio.charset.StandardCharsets;                                                          //
import java.nio.file.Files;                                                                        //
import java.nio.file.Path;                                                                         //
import java.nio.file.Paths;                                                                        //
                                                                                                   //
import javax.swing.*;                                                                              //
import java.util.Random;                                                                           //
                                                                                                   //
public class Game extends JPanel implements ActionListener{                                        //
	                                                                                               //
	private static final long serialVersionUID = 1L;                                               //
	                                                                                               //
	private Food food;                                                                             //
	private Snake snake;                                                                           //
	private Timer gameTimer;                                                                       //
	private Random random;                                                                         //
	                                                                                               //
	public static final int BOARD_WIDTH = 750;                                                     //
	public static final int BOARD_HEIGHT = 750;                                                    //
	public static final int SQUARE_SIZE = 25;                                                      //
	public static final int AXIS_SIZE = (BOARD_WIDTH * BOARD_HEIGHT) / (SQUARE_SIZE * SQUARE_SIZE);//
	public static final int SPEED = 300;                                                           //
	                                                                                               //
	private int axisX[] = new int[AXIS_SIZE];                                                      //
	private int axisY[] = new int[AXIS_SIZE];	                                                   //
	private int foodScore;                                                                         //
	private boolean right = true;                                                                  //
	private boolean left = false;                                                                  //
	private boolean up = false;                                                                    //
	private boolean down = false;                                                                  //
	private boolean isRunning = false;	                                                           //
	                                                                                               //
	public Game(){                                                                                 //
		food = new Food();                                                                         //
		snake = new Snake();                                                                       //
		random = new Random();                                                                     //
		this.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));                           //
		this.setBackground(Color.BLACK);                                                           //
		this.setFocusable(true);                                                                   //
		this.addKeyListener(new MyKeyAdapter());                                                   //
		startGame();                                                                               //
	}                                                                                              //
	                                                                                               //
	public void startGame() {                                                                      //
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	startGame()
		//
		// Method parameters	:	none
		//
		// Method return		:	void
		//
		// Synopsis				:   This method starts the game.						
		//							
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2022-11-21		Tiago   				none.
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		loadFile();                                                                                //
		foodRespawn();                                                                             //
		isRunning = true;                                                                          //
		gameTimer = new Timer(SPEED, this);                                                        //
		gameTimer.start();                                                                         //
	}                                                                                              //
	                                                                                               //
	public void paintComponent(Graphics g) {                                                       //
		super.paintComponent(g);                                                                   //
		drawGame(g);                                                                               //
	}                                                                                              //
	                                                                                               //
	public void drawGame(Graphics g) {		                                                       //
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	drawGame()
		//
		// Method parameters	:	Graphics g
		//
		// Method return		:	void
		//
		// Synopsis				:   This method draws the game itself (snake, food and score).						
		//							
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2022-11-21		Tiago   				none.
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		if(isRunning) {                                                                            //Verifies if the game still standing.
			food.drawFood(g);                                                                      //Draws the food.
			int index;                                                                             //
			for(index = 0; index < snake.getBodyPieces(); index++) {                               //Loops to draw each part of the snake.
				snake.drawSnake(g, axisX[index], axisY[index], index);			                   //
			}                                                                                      //
			g.setColor(Color.WHITE);                                                               //
			g.setFont(new Font("Arial", Font.BOLD, 38));                                           //
			FontMetrics metrics = getFontMetrics(g.getFont());                                     //
			g.drawString("Score: " + foodScore, (BOARD_WIDTH -                                     //Draws the score.
					metrics.stringWidth("Score: " + foodScore)) / 2, g.getFont().getSize());       //
		}else {                                                                                    //
			gameOver(g);                                                                           //Sets Game Over screen.
		}                                                                                          //
		                                                                                           //
	}                                                                                              //
	                                                                                               //
	public void foodRespawn(){                                                                     //
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	foodRespawn()
		//
		// Method parameters	:	none
		//
		// Method return		:	void
		//
		// Synopsis				:   This method generates the random position for the food appears.						
		//							
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2022-11-21		Tiago   				none.
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		food.setAxisX(random.nextInt((int)(BOARD_WIDTH / SQUARE_SIZE)) * SQUARE_SIZE);             //
		food.setAxisY(random.nextInt((int)(BOARD_HEIGHT / SQUARE_SIZE)) * SQUARE_SIZE);            //
	}                                                                                              //
	                                                                                               //
	public void move(){                                                                            //
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	move()
		//
		// Method parameters	:	none
		//
		// Method return		:	void
		//
		// Synopsis				:   This method updates the snake position.						
		//							
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2022-11-21		Tiago   				none.
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		int index;                                                                                 //
		for(index = snake.getBodyPieces(); index > 0; index--) {                                   //Keeps updating the snake.
			axisX[index] = axisX[index - 1];                                                       //
			axisY[index] = axisY[index - 1];                                                       //
		}                                                                                          //
		                                                                                           //
		if (up) {                                                                                  //
			axisY[0] -= SQUARE_SIZE;                                                               //Updates Y coordinate.
        }else if (down) {                                                                          //
        	axisY[0] += SQUARE_SIZE;                                                               //Updates Y coordinate.
        }else if (left) {                                                                          //
        	axisX[0] -= SQUARE_SIZE;                                                               //Updates X coordinate.
        }else if (right) {                                                                         //
        	axisX[0] += SQUARE_SIZE;                                                               //Updates X coordinate.
        }		                                                                                   //
	}                                                                                              //
	                                                                                               //
	public void checkFood() {                                                                      //
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	checkFood()
		//
		// Method parameters	:	none
		//
		// Method return		:	void
		//
		// Synopsis				:   This method checks collision between the snake's head and the food positions, add points and sets the respawn.						
		//							
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2022-11-21		Tiago   				none.
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		if(axisX[0] == food.getAxisX() && axisY[0] == food.getAxisY()) {                           //Checks the collision.
			snake.setBodyPieces(snake.getBodyPieces() + 1);                                        //Draws another piece of the snake.
			foodScore++;                                                                           //Add points.
			foodRespawn();                                                                         //Repawns the food.
		}                                                                                          //
	}                                                                                              //
	                                                                                               //
	public void checkCollisions() {		                                                           //
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	checkCollisions()
		//
		// Method parameters	:	none
		//
		// Method return		:	void
		//
		// Synopsis				:   This method checks collision between the snake's and the board, also between the snake's head and body.						
		//							
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2022-11-21		Tiago   				none.
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		int index;		                                                                           //
		for(index = snake.getBodyPieces(); index > 0; index--) {                                   //Checks if the head is going to collide with the body.
			if(axisX[0] == axisX[index] && axisY[0] == axisY[index]) {                             //
				isRunning = false;                                                                 //
			}                                                                                      //
		}                                                                                          //
                                                                                                   //
		if(axisX[0] < 0) {                                                                         //Checks the collision on the edges.
			isRunning = false;                                                                     //
		}else if(axisX[0] > BOARD_WIDTH) {                                                         //
			isRunning = false;                                                                     //
		}else if(axisY[0] < 0) {                                                                   //
			isRunning = false;                                                                     //
		}else if(axisY[0] > BOARD_HEIGHT) {                                                        //
			isRunning = false;                                                                     //
		}                                                                                          //
		                                                                                           //
		if(!isRunning) {                                                                           //Check if a collision happened.
			gameTimer.stop();                                                                      //
		}                                                                                          //
	}                                                                                              //
	                                                                                               //
	public void gameOver(Graphics g) {                                                             //
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	gameOver()
		//
		// Method parameters	:	Graphics g
		//
		// Method return		:	void
		//
		// Synopsis				:   This method show the Game Over screen and saves the score into a file.						
		//							
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2022-11-21		Tiago   				none.
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		//Score text                                                                               //
		g.setColor(Color.WHITE);                                                                   //
		g.setFont( new Font("Arial",Font.BOLD, 38));                                               //
		FontMetrics scoreMetrics = getFontMetrics(g.getFont());                                    //
		g.drawString("Score: " + foodScore, (BOARD_WIDTH -                                         //
				scoreMetrics.stringWidth("Score: " + foodScore)) / 2, g.getFont().getSize());      //
		//GameOver text                                                                            //
		g.setColor(Color.WHITE);                                                                   //
		g.setFont( new Font("Arial",Font.BOLD, 72));                                               //
		FontMetrics gameOverMetrics = getFontMetrics(g.getFont());                                 //
		g.drawString("Game Over", (BOARD_WIDTH -                                                   //
				gameOverMetrics.stringWidth("Game Over")) / 2, BOARD_HEIGHT / 2);                  //
		saveFile();                                                                                //
	}                                                                                              //
	                                                                                               //
	public void saveFile() {                                                                       //
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	saveFile()
		//
		// Method parameters	:	none
		//
		// Method return		:	void
		//
		// Synopsis				:   This method saves the score into a file.						
		//							
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2022-11-21		Tiago   				none.
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        Path path = Paths.get("D:\\snakeLastScore_Java.txt");                                      //Creates an instance of the file.
        String str = foodScore + "";                                                               //Custom string as an input.
        try {                                                                                      //Tries to block to check for exceptions.
            Files.writeString(path, str, StandardCharsets.UTF_8);                                  //Calls Files.writeString() method with path , content & standard charsets.
        }catch (IOException ex) {                                                                  //Catches the block to handle the exception.
            System.out.print("Invalid Path");                                                      //
        }                                                                                          //
	}                                                                                              //
	                                                                                               //
	public void loadFile() {                                                                       //
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	loadFile()
		//
		// Method parameters	:	none
		//
		// Method return		:	void
		//
		// Synopsis				:   This method load the score into the game.						
		//							
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2022-11-21		Tiago   				none.
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        File file = new File("D:\\snakeLastScore_Java.txt");                                       //Creating file object and specify file path.
        try {                                                                                      //Tries to block to check for exceptions.
        	BufferedReader br = new BufferedReader(new FileReader(file));                          //Creates an object of BufferedReader class.
            String line = br.readLine();                                                           //Represents an input object in form of string.
            foodScore = Integer.parseInt(line);                                                    //
            br.close();                                                                            //
        }catch (IOException e) {                                                                   //Catches the block to handle the exception.
            e.printStackTrace();                                                                   //
        }                                                                                          //
	}                                                                                              //
	                                                                                               //
	@Override                                                                                      //
	public void actionPerformed(ActionEvent e) {		                                           //
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	actionPerformed()
		//
		// Method parameters	:	ActionEvent e
		//
		// Method return		:	void
		//
		// Synopsis				:   This method checks if the game still standing and call methods for movement food checking and collisions checking.						
		//							Also, updates the graphics.
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2022-11-21		Tiago   				none.
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		if(isRunning) {                                                                            //
			move();                                                                                //
			checkFood();                                                                           //
			checkCollisions();                                                                     //
		}                                                                                          //
		repaint();                                                                                 //
	}                                                                                              //
	                                                                                               //
	public class MyKeyAdapter extends KeyAdapter{                                                  //This class captures the keyboard buttons.
		@Override                                                                                  //and sets the directions.
		public void keyPressed(KeyEvent e) {                                                       //
			switch(e.getKeyCode()) {                                                               //
			case KeyEvent.VK_LEFT:                                                                 //
				if(!right) {                                                                       //
					left = true;                                                                   //
	                up = false;                                                                    //
	                down = false;                                                                  //
				}                                                                                  //
				break;                                                                             //
			case KeyEvent.VK_RIGHT:                                                                //
				if(!left) {                                                                        //
					right = true;                                                                  //
	                up = false;                                                                    //
	                down = false;                                                                  //
				}                                                                                  //
				break;                                                                             //
			case KeyEvent.VK_UP:                                                                   //
				if(!down) {                                                                        //
					up = true;                                                                     //
	                right = false;                                                                 //
	                left = false;                                                                  //
				}                                                                                  //
				break;                                                                             //
			case KeyEvent.VK_DOWN:                                                                 //
				if(!up) {                                                                          //
					down = true;                                                                   //
	                right = false;                                                                 //
	                left = false;                                                                  //
				}                                                                                  //
			}
		}
	}
}
