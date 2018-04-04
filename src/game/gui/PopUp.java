package game.gui;

import java.util.ArrayList;
import java.util.List;

import engine.GameContainer;
import engine.Renderer;
import engine.gfx.Font;

public class PopUp {

	private String title;
	private int x, y, width, height;
	private List<Button> buttons;
	private List<CommandLine> inputs;
	private List<CheckBox> boxes;
	private boolean close,shouldClose;
	
	public PopUp(String title, int width, int height) {
		this.x = GameContainer.width / 2 - width / 2;
		this.y = GameContainer.height / 2 - height / 2;
		this.width = width;
		this.height = height;
		this.title = title;
		close = true;
		shouldClose = false;
		buttons = new ArrayList<Button>();
		inputs = new ArrayList<CommandLine>();
		boxes = new ArrayList<CheckBox>();
		buttons.add(new Button(x + width * 2 / 4, y, width / 2, 32).setTitle("Save & Exit", false));
	}

	public void update(GameContainer gc, float dt) {
		if (!close) {
			for(CheckBox c: boxes) {
				c.update(gc);
			}
			for (Button b : buttons) {
				if(b.isActive() && b.getText().equals("Save & Exit"))
					shouldClose = true;
				b.update(gc);
			}
			for (int i = 0; i < inputs.size(); i++) {
				CommandLine in = inputs.get(i);
				if (in != null) {
					in.update(gc, dt);
				}
			}
		}
	}

	public void render(GameContainer gc, Renderer r) {
		if (!close) {
			r.drawFillRect(x, y, width, height, Gui.BACKGROUND_COLOR);
			r.drawRect(x, y, width, height, 0xff000000);
			r.drawRect(x, y, width, 32, 0xff000000);
			r.drawText(Font.STANDARD, title, x, y + 8, 0xff000000);
			for (Button b : buttons)
				b.render(r);
			for (CommandLine l : inputs)
				l.render(gc, r);
			for (CheckBox c: boxes)
				c.render(r);
		}
	}

	public boolean boxTicked(String text) {
		for(CheckBox c: boxes) {
			if(c.getText().equals(text) && c.isActive())
				return true;
			else
				return false;
		}
		return false;
	}
	
	public void clearTexts() {
		for(CommandLine l: inputs) {
			l.clearText();
		}
	}
	
	public void addCheckBox(String text, int xOff, int yOff, int size) {
		boxes.add(new CheckBox(text,x+xOff,y+yOff+32,size));
	}
	
	public void addButton(String text, int xOff, int yOff, int width, int height) {
		buttons.add(new Button(x + xOff, y + yOff + 32, width, height).setTitle(text, false));
	}

	public void addInput(int xOff, int yOff, int width, int height, String title) {
		inputs.add(new CommandLine(title, x + xOff, y + yOff + 32, width, height));
	}

	public String getStringFromInput(int inputID) {
		return inputs.get(inputID).getWord();
	}
	
	public void setStringFromInput(String text, int inputID) {
		inputs.get(inputID).setWord(text);;
	}

	public void setCheckBox(boolean active, int checkID) {
		boxes.get(checkID).setActive(active);
	}
	
	public boolean shouldClose() {
		return shouldClose;
	}
	
	public boolean isClosed() {
		return close;
	}

	public void open() {
		close = false;
		shouldClose = false;
	}

	public void close() {
		close = true;
		shouldClose = true;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}
