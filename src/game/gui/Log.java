package game.gui;

import engine.GameContainer;
import engine.Renderer;
import engine.gfx.Font;

public class Log {
	
	private int x,y,width,height,outX,outY;
	
	public Log(int x, int y, int width, int height) {
		this.x=x;
		this.y=y;
		this.outX=x;;
		this.outY=y+height-24;
		this.width=width;
		this.height=height;
		
	}
	
	public void update(GameContainer gc, float dt) {
		
	}
	
	public void render(GameContainer gc, Renderer r) {
		r.drawRect(x, y, width, height, 0xff000000);
		r.drawFillRect(x, y+height/4, width, height, 0xff000000);
	}
	
	public void err(String msg, Renderer r) {
		r.drawText(Font.STANDARD, msg, outX, outY, 0xff00ff00);
	}
	
}

