package game.gui;

import engine.GameContainer;

public class AlertPopUp extends PopUp {

	public AlertPopUp(String title, int width, int height) {
		super(title, width, height);
		this.y = GameContainer.width / 3 - width / 2;
		buttons.clear();
		buttons.add(new Button(x + width * 2 / 4, y, width / 2, 32).setTitle("OK", false));
	}

	public void update(GameContainer gc, float dt) {
		if (!close) {
			for(CheckBox c: boxes) {
				c.update(gc);
			}
			for (Button b : buttons) {
				if(b.isActive() && b.getText().equals("OK"))
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
}
