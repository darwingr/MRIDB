package game.gui;

import java.util.ArrayList;
import java.util.List;

import engine.GameContainer;
import engine.Renderer;

public class Gui {

	public static final int FONT_COLOR = 0xff303030;
	public static final int BACKGROUND_COLOR = 0xffffffff;
	private List<Tab> tabs;
	private List<String> texts;

	public Gui() {
		tabs = new ArrayList<Tab>();
		texts = new ArrayList<String>();
	}

	public void update(GameContainer gc, float dt) {
		for (int i = 0; i < tabs.size(); i++) {
			tabs.get(i).update(gc,dt);
		}
	}

	public void render(GameContainer gc, Renderer r) {
		for (Tab t : tabs) {
			t.render(gc,r);
		}
		
	}

	public void addText(String text, int x, int y) {
		texts.add(text);
	}
	
	public void addSection(String title, int pos, int size, int tabID) {
		if(tabs.size() > tabID) {
			tabs.get(tabID).addSection(title, pos, size);
		}
	}
	
	public void addCheckBox(String text, int xOff, int yOff, int size, int tabID, int sectionID) {
		if(tabs.size() > tabID)
			tabs.get(tabID).addCheckBox(text, xOff, yOff, size, sectionID);
	}
	
	public void addInputBox(String title, int xOff, int yOff, int width, int height, int tabID, int sectionID) {
		tabs.get(tabID).addInputBox(title, xOff, yOff, width, height, sectionID);
	}
	
	public void addButton(String text, int pos, int size, int tabID, boolean smallText) {
		if (tabs.size() > tabID)
			tabs.get(tabID).addButton(text, pos, size, smallText);
	}

	public void addButtonBranch(String text, Gui gui, int tabWidth, int tabHeight, int pos, int size, int tabID, boolean smallText, String[] labels) {
		if (tabs.size() > tabID)
			tabs.get(tabID).addButtonBranch(text, gui, tabWidth, tabHeight, pos, size, smallText, labels);;
	}
	
	public void addButtonToSection(String text, int xOff, int yOff, int width, int height, int tabID, int sectionID) {
		if(tabs.size() > tabID)
			tabs.get(tabID).addButtonToSection(text, xOff, yOff, width, height, sectionID);
	}
	
	public void addTab(int x, int y, int width, int height) {
		tabs.add(new Tab(x, y, width, height));
	}

	public void setGraph(int pos, int size, int tabID) {
		tabs.get(tabID).setGraph(pos, size);
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
