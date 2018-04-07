package game.gui;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import engine.GameContainer;
import engine.Renderer;
import engine.gfx.Font;

public class CommandLine {

	private final int BACKGROUND_COLOR = 0xff000000;
	private final int FONT_COLOR = 0xffE5E5D1;
	private String title,defaultWord;
	private List<String> text;
	private int x, y;
	private int width, height;
	private boolean dispalyCursor;
	private boolean censor;
	private float ctr = 0;

	private boolean selected;

	public CommandLine(String title, int x, int y, int width, int height) {
		this.title = title;
		text = new ArrayList<String>();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		selected = false;
		censor = false;
		defaultWord = "";
	}

	public void censor() {
		censor = true;
	}

	public void update(GameContainer gc, float delta) {
		if (selected) {
			if (timer(.5f, delta)) {
				dispalyCursor = !dispalyCursor;
			}
			checkKey(gc, "/", KeyEvent.VK_SLASH);
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
			checkKey(gc, "0", KeyEvent.VK_0);
			checkKey(gc, "1", KeyEvent.VK_1);
			if(!gc.getInput().isKey(KeyEvent.VK_SHIFT))
				checkKey(gc, "2", KeyEvent.VK_2);
			checkKey(gc, "3", KeyEvent.VK_3);
			checkKey(gc, "4", KeyEvent.VK_4);
			checkKey(gc, "5", KeyEvent.VK_5);
			checkKey(gc, "6", KeyEvent.VK_6);
			checkKey(gc, "7", KeyEvent.VK_7);
			checkKey(gc, "8", KeyEvent.VK_8);
			checkKey(gc, "9", KeyEvent.VK_9);

			
			if(gc.getInput().isKeyDown(KeyEvent.VK_2)&&gc.getInput().isKey(KeyEvent.VK_SHIFT)) {
				enterChar("@");
			}
			if (gc.getInput().isKeyDown(KeyEvent.VK_SPACE))
				enterChar(" ");
			if (gc.getInput().isKeyDown(KeyEvent.VK_BACK_SPACE) && !gc.getInput().isKey(KeyEvent.VK_SHIFT))
				removeChar();
			if (gc.getInput().isKeyDown(KeyEvent.VK_BACK_SPACE) && gc.getInput().isKey(KeyEvent.VK_SHIFT))
				text.clear();
		}
		int mx = gc.getInput().getMouseX();
		int my = gc.getInput().getMouseY();
		if (intersects(mx, my) && gc.getInput().isButtonDown(1)) {
			selected = true;
		}
		if (!intersects(mx, my) && gc.getInput().isButtonDown(1)) {
			selected = false;
		}
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public void setWord(String word) {
		text.add(word);
	}

	public String getWord() {
		String a = "";
		for (String s : text) {
			a += s;
		}
		return a;
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
			if (censor)
				word += "*";
			else
				word += a;
		}
		if (dispalyCursor)
			word += "_";
		r.drawText(Font.SMALL_STANDARD, word, x + 2, y + height / 2, FONT_COLOR);
		r.drawText(Font.SMALL_STANDARD, title, x - title.length() * 8, y - 4 + (height / 2), 0xff000000);
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

	public boolean intersects(int mx, int my) {
		return (mx > x && my > y && mx < x + width && my < y + height);

	}

	public void clearText() {
		text.clear();
		selected = false;
	}

	// Validations on user inputs
	public boolean validInt() {
		try {
			int n = Integer.parseInt(getWord());
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	public boolean validNumber() {
		return getWord().matches("[-+]?\\d*\\.?\\d+");
	}

	public boolean validPositiveNumber() {
		// Assumes is a number
		double value = Double.parseDouble(getWord());
		return (value >= 0);
	}

	public boolean validIntMember(List<Integer> keys) {
		// Assumes is Integer
		int n = Integer.parseInt(getWord());
		return keys.contains(n);
	}

	public boolean validWidth(int width_limit) {
		int length = getWord().length();
		if (validNumber() && !validPositiveNumber()) length--;
		return (length <= width_limit);
	}

	public boolean validNotEmpty() {
		return (!getWord().isEmpty());
	}

	public boolean validDate(String date_format) {
		try {
			SimpleDateFormat df = new SimpleDateFormat(date_format);
			df.setLenient(false);
			df.parse(getWord());
			return true;
		} catch (ParseException pe) {
			return false;
		}
	}

	// Expected format "dd/MM/yy"
	// NOTE DB constraint on visits requires dob <= check_in
	public boolean validDateInPast(String date_format) {
		// Up to 24 hour slack is acceptable
		Calendar today = Calendar.getInstance();
		SimpleDateFormat sdtf = new SimpleDateFormat(date_format);
		// Assumes is proper date so exception returns false
		Calendar input_date = Calendar.getInstance();
		try {
			input_date.setTime(sdtf.parse(getWord()));
		} catch (ParseException pe) {
			return false;
		}
		if (today.compareTo(input_date) < 0) return false;
		return true;
	}

	public boolean validEmailAddress() {
		java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("^.+@.+\\..+$");
		java.util.regex.Matcher matcher = pattern.matcher(getWord());
		return matcher.matches();
	}
}
