package game.gui;

import java.util.ArrayList;
import java.util.List;

import engine.GameContainer;
import engine.Renderer;

public class PopUp {

	private String title;
	private int x, y, width, height;
	private List<Button> buttons;
	private List<CommandLine> inputs;
	private boolean close;

	public PopUp(String title, int width, int height) {
		this.x = GameContainer.width / 2 - width / 2;
		this.y = GameContainer.height / 2 - height / 2;
		close = false;
		buttons = new ArrayList<Button>();
		inputs = new ArrayList<CommandLine>();
	}

	public void update(GameContainer gc, float dt) {
		if(!close) {
			for (Button b : buttons)
				b.update(gc);
			for (int i = 0; i < inputs.size(); i++) {
				CommandLine in = inputs.get(i);
				if (in != null) {
					in.update(gc, dt);
				}
			}
		}
	}

	public void pop(Renderer r) {
		if (!close) {
			r.drawFillRect(x, y, width, height, Gui.BACKGROUND_COLOR);
			r.drawRect(x, y, width, height, 0xff000000);
		}
	}

	public void addButton(String text, int x, int y, int width, int height) {
		buttons.add(new Button(x, y, width, height).setTitle(text, false));
	}

	public void addInput(int x, int y, int width, int height, String title) {
		inputs.add(new CommandLine(title, x, y, width, height));
	}

	public void close() {
		close = true;
	}

}
