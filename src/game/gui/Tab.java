package game.gui;

import java.util.ArrayList;
import java.util.List;

import engine.GameContainer;
import engine.Renderer;

public class Tab {

	private boolean selected;
	private int x, y;
	private int width, height;
	private int borderColor;
	private int insideColor;
	private List<Button> buttons;
	
	public Tab(int x, int y, int width, int height, int borderColor, int insideColor) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.borderColor = borderColor;
		this.insideColor = insideColor;
		buttons = new ArrayList<Button>();
		selected = false;
	}

	public void render(Renderer r) {
		r.drawRect(x, y, width, height, borderColor);
		r.drawFillRect(x+1, y+1, width-2, height-2, insideColor);
		for(Button b: buttons)
			b.render(r);
	}
	
	public void update(GameContainer gc) {
		int mx = gc.getInput().getMouseX();
		int my = gc.getInput().getMouseY();
		if(intersects(mx,my)) {
			selected = true;
		} else
			selected = false;
		for(Button b: buttons)
			b.update(gc);
	}

	public List<Button> getButtons() {
		return buttons;
	}

	public boolean intersects(int mx, int my) {
		return (mx > x && my > y && mx < x + width && my < y + height);

	}
	
	public void addButton(String text, int pos, int size, boolean smallText) {
		int x, y, width, height;
		if(this.width > this.height) {
			x = pos;
			y = this.y;
			height = this.height;
			width = size;
		} else {
			x = this.x;
			y = pos;
			height = size;
			width = this.width;
		}
		buttons.add(new Button(x,y,width,height,borderColor,insideColor).setTitle(text,smallText));
	}
	
	public boolean isSelected() {
		return selected;
	}
	
}
