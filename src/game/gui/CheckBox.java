package game.gui;

import java.awt.event.MouseEvent;

import engine.GameContainer;
import engine.Renderer;
import engine.gfx.Font;

public class CheckBox {

	private final int selectedColor = 0xff777777;

	private String text;
	private boolean selected;

	private int width, height;
	private int x, y;
	private boolean active;

	public CheckBox(String text, int x, int y, int size) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.width = size;
		this.height = size;
		selected = false;
		active = false;

	}

	public void update(GameContainer gc) {
		int mx = gc.getInput().getMouseX();
		int my = gc.getInput().getMouseY();
		if (intersects(mx, my)) {
			selected = true;
		} else
			selected = false;
		
		if(selected && gc.getInput().isButtonDown(MouseEvent.BUTTON1))
			active=!active;

	}

	public void render(Renderer r) {
		if (selected) {
			r.drawRect(x, y, width, height, Gui.FONT_COLOR);
			r.drawFillRect(x + 1, y + 1, width - 2, height - 2, selectedColor);
		} else {
			r.drawRect(x, y, width, height, Gui.FONT_COLOR);
			r.drawRect(x + 1, y + 1, width - 2, height - 2, Gui.FONT_COLOR);
			r.drawFillRect(x + 2, y + 2, width - 4, height - 4, Gui.BACKGROUND_COLOR);
		}
		if(active) {
			r.drawRect(x+width/2, y, 1, height, Gui.FONT_COLOR);
			r.drawRect(x, y+height/2, width, 1, Gui.FONT_COLOR);
		}
		r.drawText(Font.SMALL_STANDARD, text, x-text.length()*8, y-4, Gui.BACKGROUND_COLOR);
	}

	public boolean intersects(int mx, int my) {
		return (mx > x && my > y && mx < x + width && my < y + height);

	}

	public int getX() {
		return x;
	}

	public String getText() {
		return text;
	}

	public boolean isSelected() {
		return selected;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getY() {
		return y;
	}

}
