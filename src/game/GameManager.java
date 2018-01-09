package game;

import engine.AbstractGame;
import engine.GameContainer;
import engine.Renderer;
import game.gui.Gui;

public class GameManager extends AbstractGame {
	
	private Gui topBar, sideBar;
	
	public GameManager() {
		topBar = new Gui();
		topBar.addTab(0, 24, GameContainer.width-1, 48);
		topBar.addButton("Search", 0, 96, 0, false);
		topBar.addButton("Compare", 256+96, 128, 0, false);
		sideBar = new Gui();
		sideBar.addTab(0, 72, 96, GameContainer.height-1);
	}

	public void update(GameContainer gc, float dt) {
		topBar.update(gc,dt);
		sideBar.update(gc, dt);
	}

	public void render(GameContainer gc, Renderer r) {
		r.drawFillRect(0, 0, gc.getWidth(), gc.getHeight(), 0xffE5E5D1);
		topBar.render(gc, r);
		sideBar.render(gc, r);
	}

	public static void main(String[] args) {
		GameContainer gc = new GameContainer(new GameManager());
		gc.start();
	}

}
