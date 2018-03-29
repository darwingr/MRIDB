package game;

import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.Random;

import engine.AbstractGame;
import engine.GameContainer;
import engine.Renderer;
import engine.gfx.Font;
import game.gui.Attribute;
import game.gui.Button;
import game.gui.CheckBox;
import game.gui.CommandLine;
import game.gui.FilterManager;
import game.gui.Gui;
import game.gui.Log;
import game.gui.Page;
import models.UserModel;

public class GameManager extends AbstractGame {

	private Log log;
	private FilterManager filter;
	private Page page;
	private Gui leftSide, rightSide;
	private boolean login, hippaAuthorized;
	private int gate;

	private CommandLine userName;
	private CommandLine password;
	private UserModel user = new UserModel();

	public GameManager() {
		filter = new FilterManager();
		leftSideInit();
		rightSideInit();
		Attribute[] attribs = new Attribute[100];
		for (int i = 0; i < 100; i++) {
			attribs[i] = new Attribute("Measurement In a certain region " + i * new Random().nextInt(100),
					"" + new Random().nextInt(567) * new Random().nextFloat());

		}
		page = new Page("Test", attribs);

		login = true;
		if (login)
			hippaAuthorized = false;
		userName = new CommandLine("Username:", GameContainer.width / 2 - 128, GameContainer.height / 2, 256, 64);
		password = new CommandLine("Password:", GameContainer.width / 2 - 128, GameContainer.height / 2 + 96, 256, 64);
		password.censor();
		userName.setSelected(true);
		gate = 0;
	}

	private void leftSideInit() {
		leftSide = new Gui();
		leftSide.addTab(0, 0, 144, GameContainer.height - 1);
		leftSide.addButton("Logout", GameContainer.height - 65, 64, 0, false);
		leftSide.addButtonBranch("Filters", leftSide, 144, 256, 0, 64, 0, false,
				new String[] { "Disorders", "Brain Region" });
		leftSide.addSection("Patient Filters", 64, 128, 0);
		leftSide.addCheckBox("Male:", 44, 38, 10, 0, 0);
		leftSide.addCheckBox("Female:", 120, 38, 10, 0, 0);
		leftSide.addInputBox("Search:", 60, 54, 48, 32, 0, 0);
	}

	private void rightSideInit() {
		rightSide = new Gui();
		rightSide.addTab(GameContainer.width - 360, 0, 359, GameContainer.height);
		rightSide.setGraph(0, 256, 0);
		rightSide.getLastTabAdded().addOutPutLog(GameContainer.height - 128, 128);
		log = rightSide.getLastTabAdded().getLog();
		Attribute[] attribs = new Attribute[100];
		for (int i = 0; i < 100; i++) {
			attribs[i] = new Attribute("Name"+new Random().nextInt(100),
					"" + new Random().nextInt(128) * new Random().nextFloat());

		}
		Float[] ages = new Float[100];
		for (int i = 0; i < ages.length; i++) {
			ages[i] = (float) new Random().nextInt(99) + 5;
		}
		rightSide.getLastTabAdded().addGraphAttribute(attribs, ages);
		for (int i = 0; i < ages.length; i++) {
			ages[i] = (float) new Random().nextInt(99) + 5;
		}
		rightSide.getLastTabAdded().addGraphAttribute(attribs, ages);
		for (int i = 0; i < ages.length; i++) {
			ages[i] = (float) new Random().nextInt(99) + 5;
		}
		rightSide.getLastTabAdded().addGraphAttribute(attribs, ages);
		for (int i = 0; i < ages.length; i++) {
			ages[i] = (float) new Random().nextInt(99) + 5;
		}
		rightSide.getLastTabAdded().addGraphAttribute(attribs, ages);
		for (int i = 0; i < ages.length; i++) {
			ages[i] = (float) new Random().nextInt(99) + 5;
		}
		rightSide.getLastTabAdded().addGraphAttribute(attribs, ages);

	}

	public void update(GameContainer gc, float dt) {
		if (login) {// Active Gui
			leftSide.update(gc, dt);
			rightSide.update(gc, dt);
			if (page != null)
				page.update(gc, dt);
			for (Button b : leftSide.getTab(0).getButtons()) {
				if (isButton(b, "Logout") && b.isSelected() && gc.getInput().isButtonDown(1)) {
					login = false;
				}

			}
			for (CheckBox c : leftSide.getTab(0).getSections().get(0).getCheckBoxes()) {
				boolean maleActive = false, femaleActive = false;
				if (c.getText().equals("Male:"))
					maleActive=c.isActive();
				if (c.getText().equals("Female:")) 
					femaleActive=c.isActive();
				if(maleActive==femaleActive) {
					filter.filter(FilterManager.ALL_GENDERS);
				} else {
					if(femaleActive)
						filter.filter(FilterManager.FEMALE_ONLY);
					if(maleActive)
						filter.filter(FilterManager.MALE_ONLY);
				}
			}
		} else {// Login Screen
			userName.update(gc, dt);
			password.update(gc, dt);
			if (userName.isSelected() && gc.getInput().isKeyDown(KeyEvent.VK_ENTER)) {
				password.setSelected(true);
				userName.setDisplayCursor(false);
				userName.setSelected(false);
			}
			try {
				if (gc.getInput().isKeyDown(KeyEvent.VK_ENTER)) {
					if (user.authenticate(userName.getWord(), password.getWord())) {
						hippaAuthorized = true;
						userName.clearText();
						password.clearText();
						password.setDisplayCursor(false);
						login = true;
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public void render(GameContainer gc, Renderer r) {
		r.drawFillRect(0, 0, gc.getWidth(), gc.getHeight(), 0xffffffff);
		if (login && page != null)
			page.render(gc, r);
		if (login && gate == 0) {
			gate = -1;
			r.setAmbientColor(0xffCCCCCC);
		}
		if (!login && gate == -1) {
			gate = 0;
			r.setAmbientColor(0xff555555);
		}
		leftSide.render(gc, r);
		rightSide.render(gc, r);
		if (hippaAuthorized)
			r.drawText(Font.STANDARD, "HIPPA Authorized", gc.getWidth() - 256, gc.getHeight() - 64, 0xffff00ff);
		else
			r.drawText(Font.STANDARD, "not HIPPA Authorized", gc.getWidth() - 256, gc.getHeight() - 64, 0xffff00ff);
		if (!login) {
			userName.render(gc, r);
			password.render(gc, r);
		}
		log.err("Filter->" + filter.genderFilter(), r);
	}

	public boolean isButton(Button b, String text) {
		return (b.getText().equals(text));
	}

	public static void main(String[] args) {
		GameContainer gc = new GameContainer(new GameManager());
		gc.setScale(2);
		gc.start();
	}

}
