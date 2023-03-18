package snake;

import java.awt.Color;
import java.awt.Graphics;

public class Snake {

	private int bodyPieces = 4;

	//GETTERS AND SETTERS ********************************
	public int getBodyPieces() {
		return bodyPieces;
	}
	
	public void setBodyPieces(int bodyPieces) {
		this.bodyPieces = bodyPieces;
	}
	
	public void drawSnake(Graphics g, int axisX, int axisY, int index) {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	drawSnake()
		//
		// Method parameters	:	Graphics g, int axisX, int axisY, int index
		//
		// Method return		:	void
		//
		// Synopsis				:   This method draws the snake on the screen.							
		//							If the index is 0 it draws the head in red color. Otherwise draws in green.
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2022-11-21		Tiago   				none.
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		if(index == 0) {
			g.setColor(Color.RED);
			g.fillRect(axisX, axisY, Game.SQUARE_SIZE, Game.SQUARE_SIZE);
		}else {
			g.setColor(Color.GREEN);
			g.fillRect(axisX, axisY, Game.SQUARE_SIZE, Game.SQUARE_SIZE);
		}			
	}	
}
