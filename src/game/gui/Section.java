package game.gui;

import java.util.ArrayList;
import java.util.List;

import engine.GameContainer;
import engine.Renderer;
import engine.gfx.Font;

public class Section {

	private int x, y;
	private int width, height;
	private String title;
	private List<CommandLine> inputs;
	private List<CheckBox> checkBoxes;
	private List<Button> buttons;

	public Section(String title, int x, int y, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		checkBoxes = new ArrayList<CheckBox>();
		inputs = new ArrayList<CommandLine>();
		buttons = new ArrayList<Button>();

	}

	public void update(GameContainer gc, float delta) {
		for (CommandLine c : inputs)
			c.update(gc, delta);
		for (CheckBox b : checkBoxes)
			b.update(gc);
		for(Button u: buttons)
			u.update(gc);
	}

	public void render(GameContainer gc, Renderer r) {
		r.drawText(Font.STANDARD, title, x, y+4, 0xff000000);
		r.drawRect(x, y, width, height/4, 0xff000000);
		r.drawRect(x+1, y+1, width-2, height-2, 0xff000000);
		for (CommandLine c : inputs)
			c.render(gc, r);
		for (CheckBox b : checkBoxes)
			b.render(r);
		for(Button u: buttons)
			u.render(r);
		
	}

	public void addInput(String title, int xOff, int yOff, int width, int height) {
		inputs.add(new CommandLine(title, x + xOff, y + yOff, width, height));
	}

	public void addCheckbox(String text, int xOff, int yOff, int size) {
		checkBoxes.add(new CheckBox(text, xOff+x, yOff+y, size));
	}
	
	public void addButton(String text, int xOff, int yOff, int width, int height) {
		buttons.add(new Button(x+xOff,y+yOff,width,height).setTitle(text, true));
	}

	public String getTitle() {
		return title;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

}
