package game.gui;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import engine.GameContainer;
import engine.Renderer;
import engine.gfx.Font;

public class CommandLine {

	private final int BACKGROUND_COLOR = 0xff000000;
	private final int FONT_COLOR = 0xffE5E5D1;
	private List<String> text;
	private int x, y;
	private int width, height;
	private boolean dispalyCursor;
	private float ctr = 0;

	public CommandLine(int x, int y, int width, int height) {
		text = new ArrayList<String>();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public void update(GameContainer gc, float delta) {
		if (timer(.5f, delta)) {
			dispalyCursor = !dispalyCursor;
		}
		checkKey(gc, "q", KeyEvent.VK_Q);
		checkKey(gc, "w", KeyEvent.VK_W);
		checkKey(gc, "e", KeyEvent.VK_E);
		checkKey(gc, "r", KeyEvent.VK_R);
		checkKey(gc, "t", KeyEvent.VK_T);
		checkKey(gc, "y", KeyEvent.VK_Y);
		checkKey(gc, "u", KeyEvent.VK_U);
		checkKey(gc, "i", KeyEvent.VK_I);
		checkKey(gc, "o", KeyEvent.VK_O);
		checkKey(gc, "p", KeyEvent.VK_P);
		checkKey(gc, "a", KeyEvent.VK_A);
		checkKey(gc, "s", KeyEvent.VK_S);
		checkKey(gc, "d", KeyEvent.VK_D);
		checkKey(gc, "f", KeyEvent.VK_F);
		checkKey(gc, "g", KeyEvent.VK_G);
		checkKey(gc, "h", KeyEvent.VK_H);
		checkKey(gc, "j", KeyEvent.VK_J);
		checkKey(gc, "k", KeyEvent.VK_K);
		checkKey(gc, "l", KeyEvent.VK_L);
		checkKey(gc, "z", KeyEvent.VK_Z);
		checkKey(gc, "x", KeyEvent.VK_X);
		checkKey(gc, "c", KeyEvent.VK_C);
		checkKey(gc, "v", KeyEvent.VK_V);
		checkKey(gc, "b", KeyEvent.VK_B);
		checkKey(gc, "n", KeyEvent.VK_N);
		checkKey(gc, "m", KeyEvent.VK_M);
		checkKey(gc, ";", KeyEvent.VK_SEMICOLON);
		checkKey(gc, ",", KeyEvent.VK_COMMA);
		checkKey(gc, ".", KeyEvent.VK_PERIOD);

		if (gc.getInput().isKeyDown(KeyEvent.VK_SPACE))
			enterChar(" ");
		if (gc.getInput().isKeyDown(KeyEvent.VK_BACK_SPACE) && !gc.getInput().isKey(KeyEvent.VK_SHIFT))
			removeChar();
		if (gc.getInput().isKeyDown(KeyEvent.VK_BACK_SPACE) && gc.getInput().isKey(KeyEvent.VK_SHIFT))
			text.clear();

	}

	public void checkKey(GameContainer gc, String key, int keyID) {
		if (gc.getInput().isKeyDown(keyID) && !gc.getInput().isKey(KeyEvent.VK_SHIFT))
			enterChar(key.toLowerCase());
		if (gc.getInput().isKeyDown(keyID) && gc.getInput().isKey(KeyEvent.VK_SHIFT))
			enterChar(key.toUpperCase());
	}

	public void render(GameContainer gc, Renderer r) {
		r.drawRect(x, y, width, height, 0xffffffff);
		r.drawFillRect(x + 1, y + 1, width - 2, height - 2, BACKGROUND_COLOR);
		String word = new String();
		for (int i = 0; i < text.size(); i++) {
			String a = text.get(i);
			word += a;
		}
		if (dispalyCursor)
			word += "_";
		r.drawText(Font.SMALL_STANDARD, word, x + 2, y + height / 2, FONT_COLOR);
	}

	public boolean timer(float timeInSeconds, float delta) {
		ctr += delta;
		if (ctr > timeInSeconds) {
			ctr = 0;
			return true;
		}
		return false;
	}

	public void enterChar(String s) {
		String t = s;
		text.add(t);

	}

	public void setDisplayCursor(boolean dispaly) {
		dispalyCursor = dispaly;
	}
	
	public void removeChar() {
		if (text.size() > 0)
			text.remove(text.size() - 1);
	}

}
