package snake;

import java.awt.Color;
import java.awt.Graphics;

public class Food {	
	private int axisX;
	private int axisY;
	
	//GETTERS AND SETTERS ********************************	
	public int getAxisX() {
		return axisX;
	}

	public void setAxisX(int axisX) {
		this.axisX = axisX;
	}

	public int getAxisY() {
		return axisY;
	}

	public void setAxisY(int axisY) {
		this.axisY = axisY;
	}

	public void drawFood(Graphics g) {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	drawFood()
		//
		// Method parameters	:	Graphics g
		//
		// Method return		:	void
		//
		// Synopsis				:   This method draws the food on the screen.							
		//
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2022-11-21		Tiago   				none.
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		g.setColor(Color.YELLOW);
		g.fillOval(axisX, axisY, Game.SQUARE_SIZE, Game.SQUARE_SIZE);	
	}
}
