package game;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

import engine.AbstractGame;
import engine.GameContainer;
import engine.Renderer;
import engine.gfx.Font;
import game.gui.Attribute;
import game.gui.AttributeSearch;
import game.gui.Button;
import game.gui.CommandLine;
import game.gui.FilterManager;
import game.gui.Gui;
import game.gui.Log;
import game.gui.Page;
import game.gui.PopUp;
import game.gui.DataSet;

import models.PatientFileModel;
import models.PatientModel;
import models.UserModel;

import adapters.EnvironmentAdapter;

public class GameManager extends AbstractGame {
	
	private UserModel CURRENT_USER = null;
	
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
	private AttributeSearch search;

	public GameManager() {
		filter = new FilterManager();
		leftSideInit();
		rightSideInit();
		page = null;
		
		login = false;
		if (login)
			hippaAuthorized = false;
		userName = new CommandLine("Username:", GameContainer.width / 2 - 128, GameContainer.height / 2, 256, 64);
		password = new CommandLine("Password:", GameContainer.width / 2 - 128, GameContainer.height / 2 + 96, 256, 64);
		password.censor();
		userName.setSelected(true);
		patientSearch = new CommandLine("Patient Search by ID:", 356, 0, 64, 32);
		gate = 0;
		
		addUser = new PopUp("Add User", 256, 256);
		addUser.addInput(96, 0, 128, 32, "First Name:");
		addUser.addInput(96, 32, 128, 32, "Last Name:");
		addUser.addInput(96, 64, 128, 32, "Email:");
		addUser.addInput(96, 96, 128, 32, "Password:");
		addUser.addCheckBox("HIPAA Authorized:", 156, 144, 12);
		editUser = new PopUp("Password", 256, 128);
		editUser.addInput(112, 0, 128, 32, "New Password:");
		removeUser = new PopUp("Remove User", 256, 128);
		removeUser.addInput(128, 0, 128, 32, "UserID to delete");
		addPatient = new PopUp("Add Patient", 320, 256);
		addPatient.addInput(176,   0, 128, 32, "First Name:");
		addPatient.addInput(176,  32, 128, 32, "Last Name:");
		addPatient.addInput(176,  64, 128, 32, "Address:");
		addPatient.addInput(176,  96, 128, 32, "Gender (M=1,F=2,?=3):");
		addPatient.addInput(176, 128, 128, 32, "DOB (mm/dd/yyyy):");
		search = new AttributeSearch(1,32,142,32);
	}

	private void leftSideInit() {
		leftSide = new Gui();
		leftSide.addTab(0, 0, 144, GameContainer.height - 1);
		leftSide.addSection("Measurements", 0, 256, 0);
		leftSide.getLastTabAdded().getSections().get(0).addCheckbox("Male:", 48, 64, 8);
		leftSide.getLastTabAdded().getSections().get(0).addCheckbox("Female:", 128, 64, 8);
		leftSide.getLastTabAdded().getSections().get(0).addInput("Min Age:", 64, 96, 78, 32);
		leftSide.getLastTabAdded().getSections().get(0).addInput("Max Age:", 64, 128, 78, 32);

		leftSide.addButton("Add Patient", 256, 64, 0, false);
		leftSide.addButton("Add User",    384, 64, 0, false);//64
		leftSide.addButton("Delete User", 448, 64, 0, false);//444-384

		leftSide.addButton("Password", GameContainer.height - (65*2), 64, 0, false);
		leftSide.addButton("Logout",   GameContainer.height - 65,     64, 0, false);
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
			if (mActive == fActive)
				filter.filter(FilterManager.ALL_GENDERS);
			else {
				if (mActive)
					filter.filter(FilterManager.MALE_ONLY);
				if (fActive)
					filter.filter(FilterManager.FEMALE_ONLY);
			
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
						if (user.authenticate(userName.getWord(), password.getWord()).isLoggedIn()) {
							CURRENT_USER = UserModel.findByID(user.authenticate(userName.getWord(), password.getWord()).getUserID());
							hippaAuthorized = CURRENT_USER.isAuthorized();
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
			if (min < FilterManager.DEFAULT_AGE_LOWER_BOUND)
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
		filter.filterAge(min, max);
		
		if(search.getWord().length() > 0 && gc.getInput().isKeyDown(KeyEvent.VK_ENTER)) {
			DataSet ds = search.search(search.getWord(), filter);
			// Data is passed to the graph here
			rightSide.getTab(0).addGraphAttribute(ds.getAttribs(), ds.getAges());
		}
		if(!addUser.isClosed()) {
			addUser.update(gc, dt);
		}
		if(addUser.shouldClose() && !addUser.isClosed()) {
			UserModel newUser = new UserModel(
					addUser.getStringFromInput(0),
					addUser.getStringFromInput(1),
					addUser.getStringFromInput(2),
					addUser.getStringFromInput(3),
					addUser.boxTicked("HIPAA Authorized:") );
			try {
				if(newUser.create()) {
					addUser.clearTexts();
				}
			} catch (SQLException e) {
				Log.print("Failed to create new User!");
				e.printStackTrace();
			}
			addUser.close();
		}
		search.update(gc, filter, dt);

		// Editing the current user (change password
		if(editUser.shouldClose() && !editUser.isClosed()) {
			// TODO Handle bad inputs
			try {
				CURRENT_USER.changePassword(editUser.getStringFromInput(0));
				editUser.clearTexts();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			editUser.close();
		}
		if(!editUser.isClosed()) {
			editUser.update(gc, dt);
		}
		if(removeUser.shouldClose() && !removeUser.isClosed()) {
			try {
				UserModel u = UserModel.findByID(Integer.parseInt(removeUser.getStringFromInput(0)));
				u.delete();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			removeUser.close();
		}
		if(!removeUser.isClosed()) {
			removeUser.update(gc, dt);
		}
		if(addPatient.shouldClose() && !addPatient.isClosed()) {
			PatientModel p = new PatientModel(addPatient.getStringFromInput(0),addPatient.getStringFromInput(1));
			try {
				if(p.create()) {
					addPatient.close();
					addPatient.clearTexts();
				}
			} catch (SQLException e) {
				Log.print("Patient Failed to be added!");
				e.printStackTrace();
			}
			addPatient.close();
		}
		if(!addPatient.isClosed()) {
			addPatient.update(gc, dt);
		}

		// Showing PatientFile with user authorization check
		if(patientSearch.getWord().length()>0 && gc.getInput().isKeyDown(KeyEvent.VK_ENTER)) {
			if (hippaAuthorized) {
				try {
				PatientFileModel pfm = PatientFileModel.findByID(Integer.parseInt(patientSearch.getWord()));
				String[] lines = PatientFileModel.printReport(pfm.getID()).split("\n");
				Attribute[] attribs = new Attribute[lines.length];
				for (int i = 0 ; i< lines.length; i++) {
					attribs[i] = new Attribute(lines[i],"");
				}
				PatientModel p = PatientModel.findByID(pfm.getID());
				page = new Page(p.fullName(), attribs);
				} catch (NumberFormatException | SQLException e) {
					e.printStackTrace();
				}
			} else {
				String authorization_error_title = "ERROR: USER NOT AUTHORIZED";
				String authorization_error_details =
						"You are logged in as a user that is not authorized to view patient data.\n";
				Attribute[] attribs = new Attribute[1];
				attribs[0] = new Attribute(authorization_error_details, "");

				page = new Page(authorization_error_title, attribs);
			}
		}

		if(leftSide.getTab(0).isButtonActive("Add User")) {
			addUser.open();
		}
		if(leftSide.getTab(0).isButtonActive("Password")) {
			editUser.open();
		}
		if(leftSide.getTab(0).isButtonActive("Delete User")) {
			removeUser.open();
		}
		if(leftSide.getTab(0).isButtonActive("Add Patient")) {
			addPatient.open();
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
		search.render(gc, r);
		editUser.render(gc,r);
		addUser.render(gc, r);
		removeUser.render(gc, r);
		addPatient.render(gc, r);
	}

	public boolean isButton(Button b, String text) {
		return (b.getText().equals(text));
	}

	public static void main(String[] args) {
		EnvironmentAdapter.setup();

		GameContainer gc = new GameContainer(new GameManager());
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		if (screenSize.width <= 1920) {
			gc.setScale(1);
		} else {
			gc.setScale(2);
		}
		gc.start();
	}

}
