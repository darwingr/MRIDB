package game;

import java.util.List;

import engine.AbstractGame;
import engine.GameContainer;
import engine.Renderer;
import game.gui.Button;
import game.gui.Gui;

public class GameManager extends AbstractGame {

	private Gui leftSide, rightSide;

	public GameManager() {
		leftSide = new Gui();
		rightSide = new Gui();
		leftSide.addTab(0, 0, 128, GameContainer.height - 1);
		rightSide.addTab(GameContainer.width - 129, 0, 128, GameContainer.height - 1);
		leftSide.addButton("Filters", 0, 64, 0, false);
	}

	public void update(GameContainer gc, float dt) {
		leftSide.update(gc, dt);
		rightSide.update(gc, dt);
		leftButtonChecks(gc);
	}

	public void render(GameContainer gc, Renderer r) {
		r.drawFillRect(0, 0, gc.getWidth(), gc.getHeight(), 0xffE5E5D1);
		leftSide.render(gc, r);
		rightSide.render(gc, r);
	}

	public void leftButtonChecks(GameContainer gc) {
		List<Button> buttons = leftSide.getTab(0).getButtons();
		for (int i = 0; i < buttons.size(); i++) {
			Button b = buttons.get(i);
			//FILTER BUTTON
			if (isButton(b, "Filters")) {
				if (b.isSelected() && gc.getInput().isButtonDown(1) && !b.isActive()) {
					leftSide.addTab(gc.getInput().getMouseX(), gc.getInput().getMouseY(), 128, 256);
					leftSide.addButton("ADHD", 0, 48, leftSide.getLastTabIDAdded(), false);
					leftSide.addButton("DIABETES", 48, 48, leftSide.getLastTabIDAdded(), false);
					b.setActive(true);
				}
				boolean close = false;
				if (b.isActive()) {
					for (int j = 0; j < leftSide.getLastTabAdded().getButtons().size(); j++) {
						Button b1 = leftSide.getLastTabAdded().getButtons().get(j);
						if (b1.isSelected() && gc.getInput().isButtonDown(1)) {
							switch (b1.getText()) {
							case "ADHD":
								System.out.println("Selected " + b1.getText());
								break;
							case "DIABETES":
								System.out.println("Selected " + b1.getText());
								break;
							}
						}
						if (!b.isSelected() && gc.getInput().isButtonDown(1) && !b1.isSelected()) {
							close = true;
						}
					}
				}
				if (close) {
					leftSide.removeTab(leftSide.getLastTabIDAdded());
					b.setActive(false);
				}
			}
			//END OF FILTER BUTTON
		}
	}

	public boolean isButton(Button b, String text) {
		return (b.getText().equals(text));
	}

	public static void main(String[] args) {
		GameContainer gc = new GameContainer(new GameManager());
		gc.start();
	}

}
