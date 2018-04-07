package game.gui;

import java.util.ArrayList;
import java.util.List;

import engine.GameContainer;
import engine.Renderer;
import engine.gfx.Font;

public class PopUp {

	private String title;
	protected int x;
	protected int y;
	private int width;
	private int height;
	protected List<Button> buttons;
	protected List<CommandLine> inputs;
	protected List<CheckBox> boxes;
	protected boolean close;
	protected boolean shouldClose;
	
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

	// Validations on user inputs (CommandLine)
	// SIDE EFFECT: clears the CommandLine if invalid
	public boolean validInt(int input_id) {
		boolean is_valid = inputs.get(input_id).validInt();
		if (!is_valid) inputs.get(input_id).clearText();
		return is_valid;
	}

	public boolean validNumber(int input_id) {
		boolean is_valid = inputs.get(input_id).validNumber();
		if (!is_valid) inputs.get(input_id).clearText();
		return is_valid;
	}

	public boolean validPositiveNumber(int input_id) {
		boolean is_valid = inputs.get(input_id).validPositiveNumber();
		if (!is_valid) inputs.get(input_id).clearText();
		return is_valid;
	}

	public boolean validIntMember(int input_id, List<Integer> keys) {
		boolean is_valid = inputs.get(input_id).validIntMember(keys);
		if (!is_valid) inputs.get(input_id).clearText();
		return is_valid;
	}

	public boolean validWidth(int input_id, int width_limit) {
		boolean is_valid = inputs.get(input_id).validWidth(width_limit);
		if (!is_valid) inputs.get(input_id).clearText();
		return is_valid;
	}

	public boolean validNotEmpty(int input_id) {
		boolean is_valid = inputs.get(input_id).validNotEmpty();
		if (!is_valid) inputs.get(input_id).clearText();
		return is_valid;
	}

	public boolean validDate(int input_id, String date_format) {
		boolean is_valid = inputs.get(input_id).validDate(date_format);
		if (!is_valid) inputs.get(input_id).clearText();
		return is_valid;
	}

	public boolean validDateInPast(int input_id, String date_format) {
		boolean is_valid = inputs.get(input_id).validDateInPast(date_format);
		if (!is_valid) inputs.get(input_id).clearText();
		return is_valid;
	}

	public boolean validEmailAddress(int input_id) {
		boolean is_valid = inputs.get(input_id).validEmailAddress();
		if (!is_valid) inputs.get(input_id).clearText();
		return is_valid;
	}
}