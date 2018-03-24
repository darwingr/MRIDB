package game.gui;

import java.util.ArrayList;
import java.util.List;

import engine.GameContainer;
import engine.Renderer;

public class Tab {

	private boolean selected;
	private int x, y;
	private int width, height;
	private List<Button> buttons;
	private List<Section> sections;
	private Graph graph;
	
	public Tab(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		selected = false;
		sections = new ArrayList<Section>();
		buttons = new ArrayList<Button>();
	}

	public void render(GameContainer gc, Renderer r) {
		r.drawRect(x, y, width, height, Gui.FONT_COLOR);
		r.drawFillRect(x+1, y+1, width-2, height-2, Gui.BACKGROUND_COLOR);
		for(Button b: buttons)
			b.render(r);
		for(Section s: sections)
			s.render(gc, r);
		if(graph!=null)
			graph.render(gc, r);
	}
	
	public void update(GameContainer gc, float delta) {
		int mx = gc.getInput().getMouseX();
		int my = gc.getInput().getMouseY();
		if(intersects(mx,my)) {
			selected = true;
		} else
			selected = false;
		for(Button b: buttons)
			b.update(gc);
		for(Section s: sections)
			s.update(gc, delta);
		if(graph!=null)
			graph.update(gc, delta);
	}

	public List<Button> getButtons() {
		return buttons;
	}

	public boolean intersects(int mx, int my) {
		return (mx > x && my > y && mx < x + width && my < y + height);

	}
	
	public void setGraph(int pos, int size) {
		int x, y, width, height;
		if(this.width > this.height) {
			x = pos+this.x;
			y = this.y;
			height = this.height;
			width = size;
		} else {
			x = this.x;
			y = pos+this.y;
			height = size;
			width = this.width;
		}
		graph=new Graph(x,y,width,height);
	}
	
	public void addSection(String title, int pos, int size) {
		int x, y, width, height;
		if(this.width > this.height) {
			x = pos+this.x;
			y = this.y;
			height = this.height;
			width = size;
		} else {
			x = this.x;
			y = pos+this.y;
			height = size;
			width = this.width;
		}
		sections.add(new Section(title,x,y,width,height));
	}
	
	public void addButton(String text, int pos, int size, boolean smallText) {
		int x, y, width, height;
		if(this.width > this.height) {
			x = pos+this.x;
			y = this.y;
			height = this.height;
			width = size;
		} else {
			x = this.x;
			y = pos+this.y;
			height = size;
			width = this.width;
		}
		buttons.add(new Button(x,y,width,height).setTitle(text,smallText));
	}
	
	public void addButtonBranch(String text,Gui gui, int tabWidth, int tabHeight, int pos, int size, boolean smallText, String[] labels) {
		int x, y, width, height;
		if(this.width > this.height) {
			x = pos+this.x;
			y = this.y;
			height = this.height;
			width = size;
		} else {
			x = this.x;
			y = pos+this.y;
			height = size;
			width = this.width;
		}
		Button b = new Button(x,y,width,height).setTitle(text,smallText).setBranchButton(gui, tabWidth, tabHeight, size);
		for(int i = 0; i < labels.length;i++) {
			b.addBranchButton(labels[i]);
		}
		buttons.add(b);
	}
	
	public void addCheckBox(String text, int xOff, int yOff, int size, int sectionID) {
		sections.get(sectionID).addCheckbox(text, xOff, yOff, size);
	}
	
	public void addInputBox(String title, int xOff, int yOff, int width, int height, int sectionID) {
		sections.get(sectionID).addInput(title, xOff, yOff, width, height);
	}
	
	public void addButtonToSection(String text, int xOff, int yOff, int width, int height, int sectionID) {
		sections.get(sectionID).addButton(text, xOff, yOff, width, height);
	}
	
	public boolean isSelected() {
		return selected;
	}
	
}
