package game;

import java.awt.event.KeyEvent;
import java.sql.SQLException;

import engine.AbstractGame;
import engine.GameContainer;
import engine.Renderer;
import engine.gfx.Font;
import game.gui.Attribute;
import game.gui.Button;
import game.gui.CommandLine;
import game.gui.FilterManager;
import game.gui.Gui;
import game.gui.Log;
import game.gui.Page;
import game.gui.PopUp;
import models.PatientModel;
import models.UserModel;

public class GameManager extends AbstractGame {
	
	private Log log;
	private FilterManager filter;
	private CommandLine patientSearch;
	private Page page;
	private Gui leftSide, rightSide;
	private boolean login, hippaAuthorized;
	private int gate;

	private CommandLine userName;
	private CommandLine password;
	private UserModel user = new UserModel();
	private PopUp editUser,addUser,addPatient,removeUser;

	public GameManager() {
		filter = new FilterManager();
		leftSideInit();
		rightSideInit();
		page = new Page("Test", new Attribute[] {new Attribute("Brain Mass:","165")});

		login = true;
		if (login)
			hippaAuthorized = false;
		userName = new CommandLine("Username:", GameContainer.width / 2 - 128, GameContainer.height / 2, 256, 64);
		password = new CommandLine("Password:", GameContainer.width / 2 - 128, GameContainer.height / 2 + 96, 256, 64);
		password.censor();
		userName.setSelected(true);
		patientSearch = new CommandLine("Search by ID:", 256, 0, 64, 32);
		gate = 0;
		
		addUser = new PopUp("Add User", 256, 256);
		addUser.addInput(96, 0, 128, 32, "First Name:");
		addUser.addInput(96, 32, 128, 32, "Last Name:");
		addUser.addInput(96, 64, 128, 32, "Email:");
		addUser.addInput(96, 96, 128, 32, "Password:");
		addUser.addCheckBox("HIPAA Authorized:", 156, 144, 12);
		editUser = new PopUp("Edit User",256,256);
		editUser.addInput(96, 0, 128, 32, "First Name:");
		editUser.addInput(96, 32, 128, 32, "Last Name:");
		editUser.addInput(96, 64, 128, 32, "Email:");
		editUser.addInput(96, 96, 128, 32, "Password:");
		editUser.addCheckBox("HIPAA Authorized", 156, 144, 12);
		removeUser = new PopUp("Remove User", 256, 128);
		removeUser.addInput(128, 0, 128, 32, "UserID to delete");
		addPatient = new PopUp("Add Patient", 256, 256);
		addPatient.addInput(96, 0, 128, 32, "First Name:");
		addPatient.addInput(96, 32, 128, 32, "Last Name:");
		addPatient.addInput(96, 64, 128, 32, "Address:");
		addPatient.addInput(96, 96, 128, 32, "Gender:");
		addPatient.addInput(96, 128, 128, 32, "DOB(m/d/y):");
		
	}

	private void leftSideInit() {
		leftSide = new Gui();
		leftSide.addTab(0, 0, 144, GameContainer.height - 1);
		leftSide.addButton("Logout", GameContainer.height - 65, 64, 0, false);
		leftSide.addSection("Filters", 0, 256, 0);
		leftSide.getLastTabAdded().getSections().get(0).addCheckbox("Male:", 62, 8, 8);
		leftSide.getLastTabAdded().getSections().get(0).addCheckbox("Female:", 62, 32, 8);
		leftSide.getLastTabAdded().getSections().get(0).addCheckbox("ADHD: ", 128, 8, 8);
		leftSide.getLastTabAdded().getSections().get(0).addCheckbox("Autism:", 128, 32, 8);
		leftSide.getLastTabAdded().getSections().get(0).addInput("Min Age:", 64, 64, 78, 32);
		leftSide.getLastTabAdded().getSections().get(0).addInput("Max Age:", 64, 98, 78, 32);
		leftSide.addButton("Add User", 256, 64, 0, false);
		leftSide.addButton("Edit User", 320, 64, 0, false);
		leftSide.addButton("Delete User", 384, 64, 0, false);
		leftSide.addButton("Add Patient", 444, 64, 0, false);

	}

	private void rightSideInit() {
		rightSide = new Gui();
		rightSide.addTab(GameContainer.width - 360, 0, 359, GameContainer.height);
		rightSide.setGraph(0, 256, 0);
		rightSide.getLastTabAdded().addOutPutLog(GameContainer.height - 128, 128);
		log = rightSide.getLastTabAdded().getLog();
		
	}

	public void update(GameContainer gc, float dt) {
		if (login) {// Active Gui
			leftSide.update(gc, dt);
			rightSide.update(gc, dt);
			if (page != null)
				page.update(gc, dt);
			if (leftSide.getTab(0).isButtonActive("Logout"))
				login = false;
			boolean mActive = leftSide.getTab(0).isCheckBoxActive("Male:", 0);
			boolean fActive = leftSide.getTab(0).isCheckBoxActive("Female:", 0);
			boolean adhdActive = leftSide.getTab(0).isCheckBoxActive("ADHD: ", 0);
			boolean autismActive = leftSide.getTab(0).isCheckBoxActive("Autism:", 0);
			if (mActive == fActive)
				filter.filter(FilterManager.ALL_GENDERS);
			else {
				if (mActive)
					filter.filter(FilterManager.MALE_ONLY);
				if (fActive)
					filter.filter(FilterManager.FEMALE_ONLY);
			}
			if (adhdActive == autismActive) {
				filter.filter(FilterManager.ALL_DISORDERS);
			} else {
				if (adhdActive)
					filter.filter(FilterManager.ADHD_ONLY);
				if (autismActive)
					filter.filter(FilterManager.AUTISM_ONLY);
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
					try {
						if (user.authenticate(userName.getWord(), password.getWord())) {
							hippaAuthorized = true;
							userName.clearText();
							password.clearText();
							password.setDisplayCursor(false);
							login = true;
						}
					} catch (NullPointerException e) {
						hippaAuthorized = false;
						userName.clearText();
						password.clearText();
						password.setDisplayCursor(false);
						login = true;
						Log.print("Not connected to DB adapter!!");
						e.printStackTrace();
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		patientSearch.update(gc, dt);
		float min = FilterManager.DEFAULT_AGE_LOWER_BOUND;
		try {
			min = Float.parseFloat(leftSide.getLastTabAdded().getSections().get(0).getInput(0).getWord());
			if (min > FilterManager.DEFAULT_AGE_LOWER_BOUND)
				min = FilterManager.DEFAULT_AGE_LOWER_BOUND;
		} catch (NumberFormatException e) {
		}
		float max = FilterManager.DEFAULT_AGE_UPPER_BOUND;
		try {
			max = Float.parseFloat(leftSide.getLastTabAdded().getSections().get(0).getInput(1).getWord());
			if(max > FilterManager.DEFAULT_AGE_UPPER_BOUND)
				max = FilterManager.DEFAULT_AGE_UPPER_BOUND;
		} catch(NumberFormatException e) {
		}
		if(addUser.shouldClose()) {
			UserModel newUser = new UserModel(addUser.getStringFromInput(0),addUser.getStringFromInput(1),addUser.getStringFromInput(2),addUser.getStringFromInput(3),addUser.boxTicked("HIPAA Authorized:"));
			try {
				newUser.create();
			} catch (SQLException e) {
				Log.print("Failed to create new User!");
				e.printStackTrace();
			}
			addUser.close();
		}
		if(!addUser.isClosed()) {
			addUser.update(gc, dt);
		}
		if(editUser.shouldClose()) {
			user = new UserModel(editUser.getStringFromInput(0),editUser.getStringFromInput(1),editUser.getStringFromInput(2),editUser.getStringFromInput(3),editUser.boxTicked("HIPAA Authorized:"));
			try {
				user.create();
			} catch (SQLException e) {
				Log.print("Failed to edit current user");
				e.printStackTrace();
			}
			editUser.close();
		}
		if(!editUser.isClosed()) {
			editUser.update(gc, dt);
		}
		if(removeUser.shouldClose()) {
			try {
				UserModel u = user.findByID(Integer.parseInt(removeUser.getStringFromInput(0)));
				u.delete();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(!removeUser.isClosed()) {
			removeUser.update(gc, dt);
		}
		if(addPatient.shouldClose()) {
			PatientModel p = new PatientModel(addPatient.getStringFromInput(0),addPatient.getStringFromInput(1));
			try {
				p.create();
			} catch (SQLException e) {
				Log.print("Patient Failed to be added!");
				e.printStackTrace();
			}
		}
		if(!addPatient.isClosed()) {
			addPatient.update(gc, dt);
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

		r.drawFillRect(145, 0, 512, 32, 0xffffffff);
		patientSearch.render(gc, r);
		if(leftSide.getTab(0).isButtonActive("Add User")) {
			addUser.open();
		}
		if(leftSide.getTab(0).isButtonActive("Edit User")) {
			editUser.setStringFromInput(user.fullName().split(" ")[0], 0);
			editUser.setStringFromInput(user.fullName().split(" ")[1], 1);
			editUser.setStringFromInput(user.getEmail(), 2);
			editUser.setStringFromInput(user.getPassword(), 3);
			editUser.setCheckBox(user.isAuthorized(), 0);
			editUser.open();
		}
		if(leftSide.getTab(0).isButtonActive("Delete User")) {
			removeUser.open();
		}
		if(leftSide.getTab(0).isButtonActive("Add Patient")) {
			addPatient.open();
		}
		editUser.render(gc,r);
		addUser.render(gc, r);
		removeUser.render(gc, r);
		addPatient.render(gc, r);
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
