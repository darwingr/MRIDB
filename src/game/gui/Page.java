package game.gui;

import java.util.ArrayList;
import java.util.List;

import engine.GameContainer;
import engine.Renderer;
import engine.gfx.Font;

public class Page {

	private final int CHAR_MAX = 90;
	private final int TEXT_OFFSET = 176;

	private int scrollBarPos = 0;
	private int spacing = 20;

	private List<Attribute> lines;
	private String title;

	public Page(String title, Attribute[] data) {
		this.title = title;
		lines = new ArrayList<Attribute>();
		for (int i = 0; i < data.length; i++) {
			if (data[i].getDisplayText().length() > CHAR_MAX) {
				String b = data[i].getDisplayText().substring(0, CHAR_MAX-1);
				String a = data[i].getDisplayText().substring(CHAR_MAX);
				lines.add(new Attribute(a, ""));
				lines.add(new Attribute(b, ""));
			} else
				lines.add(data[i]);
		}
		
	}

	public void update(GameContainer gc, float dt) {
		if (gc.getInput().getScroll() > 0)
			scrollBarPos += 8;
		if (gc.getInput().getScroll() < 0)
			scrollBarPos -= 8;
	}

	public void render(GameContainer gc, Renderer r) {
		r.drawText(Font.STANDARD, title, TEXT_OFFSET, 32 - scrollBarPos, 0xff000000);
		r.drawFillRect(TEXT_OFFSET, 56 - scrollBarPos, title.length() * 10, 1, 0xff000000);
		for (int i = 0; i < lines.size(); i++) {
			r.drawText(Font.SMALL_STANDARD, "-" + lines.get(i).getDisplayText(), TEXT_OFFSET,
					(64 + i * spacing) - scrollBarPos, 0xff000000);
		}
	}

	public boolean intersects(int x, int y, int width, int height, int mx, int my) {
		return (mx > x && my > y && mx < x + width && my < y + height);

	}

}
