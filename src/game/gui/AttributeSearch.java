package game.gui;

import engine.GameContainer;
import engine.Renderer;

public class AttributeSearch {

	private int x, y, width, height;
	private CommandLine input;
	private Attribute[] uAttribs;

	public AttributeSearch(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		input = new CommandLine("Search Measurement: ", x, y, width, height);

	}

	public void update(GameContainer gc, FilterManager filter, float dt) {
		input.update(gc, dt);
		
	}

	public void render(GameContainer gc, Renderer r) {
		input.render(gc, r);
	}
	
	public String getWord() {
		return input.getWord();
	}

}
