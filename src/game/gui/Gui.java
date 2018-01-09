package game.gui;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import engine.GameContainer;
import engine.Renderer;
import engine.gfx.Font;
import game.Vector2i;

public class Gui {

	private final int BACKGROUND_COLOR = 0xffE5E5D1;
	private final int FONT_COLOR = 0xff303030;
	private List<Tab> tabs;
	private List<String> texts;
	private List<Vector2i> textPos;
	private CommandLine search;
	private boolean searchSelected;

	public Gui() {
		tabs = new ArrayList<Tab>();
		texts = new ArrayList<String>();
		textPos = new ArrayList<Vector2i>();
		search = new CommandLine(96, 24, 256, 48);
	}

	public void update(GameContainer gc, float dt) {
		for (Tab t : tabs) {
			t.update(gc);
			for (Button b : t.getButtons()) {
				if (b.getText() == "Search" && b.isSelected() && gc.getInput().isButtonDown(MouseEvent.BUTTON1)) {
					searchSelected = !searchSelected;
					
				}
				if(b.getText() != "Search" && b.isSelected() && gc.getInput().isButtonDown(MouseEvent.BUTTON1)) {
					searchSelected = false;
				}
			}
		}
		if(!searchSelected)
			search.setDisplayCursor(false);
		if (gc.getInput().isKeyDown(KeyEvent.VK_ENTER))
			searchSelected = !searchSelected;
		if (gc.getInput().isKeyDown(KeyEvent.VK_ESCAPE)) {
			searchSelected = false;
			search.setDisplayCursor(false);
		}
		if (searchSelected)
			search.update(gc, dt);
	}

	public void render(GameContainer gc, Renderer r) {
		for (Tab t : tabs) {
			t.render(r);
		}
		search.render(gc, r);
		for (String s : texts)
			for (Vector2i v : textPos)
				r.drawText(Font.SMALL_STANDARD, s, v.x, v.y, FONT_COLOR);
	}

	public void addText(String text, int x, int y) {
		texts.add(text);
	}

	public void addButton(String text, int pos, int size, int tabID, boolean smallText) {
		if (tabs.size() > tabID)
			tabs.get(tabID).addButton(text, pos, size, smallText);
	}

	public void addTab(int x, int y, int width, int height) {
		tabs.add(new Tab(x, y, width, height, 0xff303030, 0xffE5E5D1));
	}

	public void clearText() {
		texts.clear();
	}

}
