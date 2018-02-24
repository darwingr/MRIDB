package game.gui;

import java.util.ArrayList;
import java.util.List;

import engine.GameContainer;
import engine.Renderer;
import engine.gfx.Font;
import game.Vector2i;

public class Gui {

	private final int FONT_COLOR = 0xff303030;
	private List<Tab> tabs;
	private List<String> texts;
	private List<Vector2i> textPos;

	public Gui() {
		tabs = new ArrayList<Tab>();
		texts = new ArrayList<String>();
		textPos = new ArrayList<Vector2i>();
	}

	public void update(GameContainer gc, float dt) {
		for (int i = 0; i < tabs.size(); i++) {
			tabs.get(i).update(gc);
		}
	}

	public void render(GameContainer gc, Renderer r) {
		for (Tab t : tabs) {
			t.render(r);
		}
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

	public void addButtonBranch(String text, Gui gui, int tabWidth, int tabHeight, int pos, int size, int tabID, boolean smallText, String[] labels) {
		if (tabs.size() > tabID)
			tabs.get(tabID).addButtonBranch(text, gui, tabWidth, tabHeight, pos, size, smallText, labels);;
	}
	
	public void addTab(int x, int y, int width, int height) {
		tabs.add(new Tab(x, y, width, height, 0xff303030, 0xffd3c23d));
	}

	public Tab getTab(int id) {
		return tabs.get(id);
	}
	
	public void removeTab(int id) {
		if(tabs.size()>1)
			tabs.remove(id);
	}
	
	public Tab getLastTabAdded() {
		return tabs.get(tabs.size()-1);
	}
	
	public int getLastTabIDAdded() {
		return tabs.size()-1;
	}
	
	public void clearText() {
		texts.clear();
	}

}
