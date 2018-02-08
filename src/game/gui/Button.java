package game.gui;

import engine.GameContainer;
import engine.Renderer;
import engine.gfx.Font;

public class Button {

	private final int selectedColor = 0xffCCCCBB;

	private String text;
	private boolean selected;
	private boolean smallText;

	private int width, height;
	private int borderColor;
	private int insideColor;
	private int x, y;
	private boolean active;
	
	public Button(int x, int y, int width, int height, int borderColor, int insideColor) {
		this.x = x;
		this.y = y;
		this.borderColor = borderColor;
		this.insideColor = insideColor;
		this.width = width;
		this.height = height;
		selected = false;
		smallText = false;
		active = false;
	}

	public Button setTitle(String text, boolean small) {
		this.text = text;
		smallText = small;
		return this;
	}

	public void update(GameContainer gc) {
		int mx = gc.getInput().getMouseX();
		int my = gc.getInput().getMouseY();
		if (intersects(mx, my)) {
			selected = true;
		} else
			selected = false;
	}

	public void render(Renderer r) {
		if (selected) {
			r.drawRect(x, y, width, height, borderColor);
			r.drawFillRect(x + 1, y + 1, width - 2, height - 2, selectedColor);
		} else {
			r.drawRect(x, y, width, height, borderColor);
			r.drawRect(x + 1, y + 1, width - 2, height - 2, borderColor);
			r.drawFillRect(x + 2, y + 2, width - 4, height - 4, insideColor);
		}
		if (text != null)
			if (smallText)
				r.drawText(Font.SMALL_STANDARD, text, x + (width / 8), y + (height / 4), borderColor);
			else
				r.drawText(Font.STANDARD, text, x + (width / 8), y + (height / 4), borderColor);
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
