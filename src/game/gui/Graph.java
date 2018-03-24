package game.gui;

import java.awt.event.KeyEvent;
import java.util.List;

import engine.GameContainer;
import engine.Renderer;
import engine.gfx.Font;

public class Graph {

	private int width, height, x, y, axisColor = 0xff000000, xScale, yScale, maxX, maxY, oX, oY;
	private List<Attribute[]> attribs;
	private String xLabel, yLabel, title;
	private List<Integer> colors;

	public Graph(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		xScale = 1;
		yScale = 1;
		maxX = 99;
		maxY = 10;
		xLabel = "Age in years";
		yLabel = "Measurement in mm";
		title = "Plot";
		oX = x + 32;
		oY = y + height - 33;
	}

	public void addAttributes(Attribute[] attribs) {
		this.attribs.add(attribs);
	}

	public void update(GameContainer gc, float dt) {
		if (gc.getInput().isKeyDown(KeyEvent.VK_UP))
			yScale++;
		if (gc.getInput().isKeyDown(KeyEvent.VK_DOWN) && yScale > 1)
			yScale--;
		if (gc.getInput().isKeyDown(KeyEvent.VK_RIGHT))
			xScale++;
		if (gc.getInput().isKeyDown(KeyEvent.VK_LEFT) && xScale > 1)
			xScale--;
	}

	public void render(GameContainer gc, Renderer r) {
		r.drawRect(x, y, width, height, axisColor);
		r.drawRect(oX, oY, width-64, 1, axisColor);
		r.drawRect(oX, y+32, 1, height-64, axisColor);
		r.drawText(Font.STANDARD, title, x, y-16, axisColor);
		int ctr = 0;
		for(int i = 0 ; oY-i > y+32; i+=yScale) {
			if(yScale > 8 && ctr % 2 == 0) {
				r.drawText(Font.SMALL_STANDARD, ""+ctr, oX-(""+ctr).length()*11-1, (oY-6)-i, 0xff000000);
				r.drawRect(oX-4, oY-i, 8, 1, 0xff000000);
			} else if(yScale > 4 && ctr % 4 == 0) {
				r.drawText(Font.SMALL_STANDARD, ""+ctr, oX-(""+ctr).length()*11-1, (oY-6)-i, 0xff000000);
				r.drawRect(oX-4, oY-i, 8, 1, 0xff000000);
			} else if(ctr % 16 == 0) {
				r.drawText(Font.SMALL_STANDARD, ""+ctr, oX-(""+ctr).length()*11-1, (oY-6)-i, 0xff000000);
				r.drawRect(oX-4, oY-i, 8, 1, 0xff000000);
			} else {
				r.drawRect(oX-2, oY-i, 4, 1, 0xff000000);
			}
			ctr++;
		}
		ctr = 0;
		for(int j = 0; oX+j < x+width-32; j+=xScale) {
			if(xScale > 8 && ctr % 2 == 0) {
				r.drawText(Font.SMALL_STANDARD, ""+ctr, (oX-4)+j, oY+16, 0xff000000);
				r.drawRect(oX+j, oY-4, 1, 8, 0xff000000);
			} else if(xScale > 4 && ctr % 4 == 0) {
				r.drawText(Font.SMALL_STANDARD, ""+ctr, (oX-4)+j, oY+16, 0xff000000);
				r.drawRect(oX+j, oY-4, 1, 8, 0xff000000);
			} else {
				r.drawRect(oX+j, oY-2, 1, 4, 0xff000000);
			}
			ctr++;
		}
		r.drawFillRect((oX+xScale*24)-2, (oY-yScale*32)-2, 4, 4, 0xff000000);
	}

}
