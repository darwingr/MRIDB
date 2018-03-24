package game.gui;

import java.util.ArrayList;
import java.util.List;

import engine.GameContainer;
import engine.Renderer;

public class Log {
	
	private List<String> logs;
	private int maxMessages=8,x,y,width,height;
	
	public Log(int x, int y, int width, int height) {
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		logs = new ArrayList<String>();
		
	}
	
	public void update(GameContainer gc, float dt) {
		if(logs.size()>maxMessages) {
			logs.remove(0);
		}
	}
	
	public void render(GameContainer gc, Renderer r) {
		r.drawRect(x, y, width, height, 0xff000000);
		r.drawFillRect(x, y+height/4, width, height, 0xff000000);
	}

	public void print(String msg) {
		logs.add(msg);
	}
	
	public static void err(String msg, Renderer r) {
		
	}
	
}

