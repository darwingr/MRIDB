package game.gui;

import java.util.ArrayList;
import java.util.List;

import engine.GameContainer;
import engine.Renderer;
import engine.gfx.Font;

public class Button {

	private final int selectedColor = 0xff8B7F28;

	private String text;
	private boolean selected;
	private boolean smallText;

	private int width, height;
	private int borderColor;
	private int insideColor;
	private int x, y;
	private boolean active;

	private Gui master;
	private List<String> buttons;
	private int buttonScale, tabWidth, tabHeight;

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
		buttons = new ArrayList<String>();
	}

	public Button setTitle(String text, boolean small) {
		this.text = text;
		smallText = small;
		return this;
	}

	public Button setBranchButton(Gui gui, int tabWidth, int tabHeight, int buttonScale) {
		this.master = gui;
		this.buttonScale = buttonScale;
		this.tabWidth = tabWidth;
		this.tabHeight = tabHeight;
		return this;
	}

	public Button addBranchButton(String text) {
		buttons.add(text);
		return this;
	}

	public void update(GameContainer gc) {
		int mx = gc.getInput().getMouseX();
		int my = gc.getInput().getMouseY();
		if (intersects(mx, my)) {
			selected = true;
		} else
			selected = false;
		if (master != null) {
			List<Button> buttons = master.getTab(0).getButtons();
			for (int i = 0; i < buttons.size(); i++) {
				Button b = buttons.get(i);
				// FILTER BUTTON
				if (b.getText().equals(text)) {
					if (b.isSelected() && gc.getInput().isButtonDown(1) && !b.isActive()) {
						master.addTab(gc.getInput().getMouseX(), gc.getInput().getMouseY(), tabWidth, tabHeight);
						int pos = 0;
						for (String s : this.buttons) {
							if (s != null) {
								master.addButton(s, pos, buttonScale, master.getLastTabIDAdded(), smallText);
								pos += buttonScale;
							}
						}
						b.setActive(true);
					}
					boolean close = false;
					if (b.isActive()) {
						for (int j = 0; j < master.getLastTabAdded().getButtons().size(); j++) {
							Button b1 = master.getLastTabAdded().getButtons().get(j);
							if (b1.isSelected() && gc.getInput().isButtonDown(1)) {
								for (String s : this.buttons) {
									if (b1.getText().equals(s)) {
										System.out.println("Clicked->" + s);
										close = true;
									}
								}
								if (!b.isSelected() && gc.getInput().isButtonDown(1) && !b1.isSelected()) {
									close = true;
								}
							}
						}
						if (close) {
							master.removeTab(master.getLastTabIDAdded());
							b.setActive(false);
						}
					}
				}
			}
		}
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
