package game.gui;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import engine.GameContainer;
import engine.Renderer;
import engine.gfx.Font;

public class Graph {

	private int width, height, x, y, axisColor = 0xff000000, xScale, yScale, oX, oY;
	private int displayScale = 5;
	private String label, title;
	private List<Integer> colors;
	private List<Integer> usedColors;
	private List<Point> points;
	private HashMap<Integer,String> attribs;

	public class Point {
		public float x,y;
		public int color;

		public Point(float x, float y) {
			this.x=x;
			this.y=y;
			color = 0xff000000;
		}
		
		public Point(float x, float y, int color) {
			this.x = x;
			this.y = y;
			this.color = color;
		}
	}

	public Graph(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		xScale = 2;
		yScale = 1;
		label = "Units ";
		title = "Measurement to Age";
		oX = x + 32;
		oY = y + height - 33;
		points = new ArrayList<Point>();
		colors = new ArrayList<Integer>();
		usedColors = new ArrayList<Integer>();
		attribs = new HashMap<Integer,String>();
		colors.add(0xff000099);
		colors.add(0xff009900);
		colors.add(0xff990000);
		colors.add(0xff009999);
		colors.add(0xff990099);
		colors.add(0xff999900);
		
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
		r.drawRect(oX, oY, width, 1, axisColor);
		r.drawRect(oX, y, 1, height - 32, axisColor);
		r.drawText(Font.STANDARD, title, x+width/4, y, axisColor);
		int ctr = 0;
		for (int i = 0; oY - i > y; i += yScale) {
			if (yScale > 8 && ctr % 2 == 0) {
				r.drawText(Font.SMALL_STANDARD, "" + ctr*displayScale, oX - ("" + ctr*displayScale).length() * 11 - 1, (oY - 6) - i, 0xff000000);
				r.drawRect(oX - 4, oY - i, 8, 1, 0xff000000);
			} else if (yScale > 4 && ctr % 4 == 0) {
				r.drawText(Font.SMALL_STANDARD, "" + ctr*displayScale, oX - ("" + ctr*displayScale).length() * 11 - 1, (oY - 6) - i, 0xff000000);
				r.drawRect(oX - 4, oY - i, 8, 1, 0xff000000);
			} else if (ctr % 16 == 0) {
				r.drawText(Font.SMALL_STANDARD, "" + ctr*displayScale, oX - ("" + ctr*displayScale).length() * 11 - 1, (oY - 6) - i, 0xff000000);
				r.drawRect(oX - 4, oY - i, 8, 1, 0xff000000);
			} else {
				r.drawRect(oX - 2, oY - i, 4, 1, 0xff000000);
			}
			ctr++;
		}
		ctr = 0;
		for (int j = 0; oX + j < x + width; j += xScale) {
			if (xScale > 8 && ctr % 2 == 0) {
				r.drawText(Font.SMALL_STANDARD, "" + ctr, (oX - 4) + j, oY + 8, 0xff000000);
				r.drawRect(oX + j, oY - 4, 1, 8, 0xff000000);
			} else if (xScale > 4 && ctr % 4 == 0) {
				r.drawText(Font.SMALL_STANDARD, "" + ctr, (oX - 4) + j, oY + 8, 0xff000000);
				r.drawRect(oX + j, oY - 4, 1, 8, 0xff000000);
			} else if (ctr % 32 == 0) {
				r.drawText(Font.SMALL_STANDARD, "" + ctr, (oX - 4) + j, oY + 8, 0xff000000);
				r.drawRect(oX + j, oY - 4, 1, 8, 0xff000000);

			} else {
				r.drawRect(oX + j, oY - 2, 1, 4, 0xff000000);
			}
			ctr++;
		}
		for(Point p: points) {
			if(p!=null)
				drawPoint(p.x,p.y,p.color,r);
		}
		r.drawRect(x, y+height, width, 32, 0xff000000);
		r.drawRect(x, y+height+196, width, 1, 0xff000000);
		r.drawText(Font.STANDARD, label + " Age in (yrs)", x+2, y+height, 0xff000000);
		for(int i = 0; i < usedColors.size(); i++) {
			r.drawText(Font.SMALL_STANDARD, attribs.get(usedColors.get(i)), x, y+height+32+i*20, usedColors.get(i));
		}
		
		for(int i = 0; i < 100; i++) {
			drawPoint(i, (float)Math.log(i)*i, 0xff44FF00, r);
		}
		
		
	}
	
	public void clear() {
		points.clear();
		usedColors.clear();
	}
	
	public int getColor() {
		int color = colors.get(new Random().nextInt(colors.size()-1));
		if(usedColors.contains(color)) {
			color = getColor();
			return color;
		}
		usedColors.add(color);
		return color;
	}
	
	public void addAttribute(Attribute[] attribs, Float[] ages) {
		if(attribs.length!=ages.length)
			return;
		label="Units in ("+attribs[0].getUnits()+")|";
		int color = getColor();
		this.attribs.put(color, attribs[0].getName());
		for(int i = 0; i < ages.length;i ++) {
			points.add(new Point(ages[i],attribs[i].getDataAsFloat(),color));
		}
		
	}
	
	public void addPoint(float x, float y) {
		points.add(new Point(x,y));
	}
	
	public void drawPoint(float posX, float posY, int color, Renderer r) {
		r.drawFillRect((int) (oX + xScale * posX) - 2, (int) (oY - (yScale * posY)/displayScale) - 2, 4, 4, color);
	}

}
