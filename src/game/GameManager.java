package game;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.Arrays;

import engine.AbstractGame;
import engine.GameContainer;
import engine.Renderer;
import engine.gfx.Font;
import game.gui.AlertPopUp;
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
	private boolean login,
            hippaAuthorized;
	private int gate;

	private CommandLine userName;
	private CommandLine password;

	private UserModel user = new UserModel();
	private PopUp editUser,
			addUser,
			addPatient,
			removeUser,
			unauthorizedAlert,
			invalidInputAlert;
	private AttributeSearch search;

	public GameManager() {
		filter = new FilterManager();
		leftSideInit();
		rightSideInit();
		page = null;

		login = false;
		if (login)
			hippaAuthorized = false;
		userName = new CommandLine("Username:",
				GameContainer.width / 2 - 128,
				GameContainer.height / 2,
				256, 64);
		password = new CommandLine("Password:",
				GameContainer.width / 2 - 128,
				GameContainer.height / 2 + 96,
				256, 64);
		password.censor();
		userName.setSelected(true);
		patientSearch = new CommandLine("Patient Search by ID:", 356, 0, 64, 32);
		gate = 0;

		unauthorizedAlert = new PopUp("Unauthorized Action", 416, 64);
		invalidInputAlert = new AlertPopUp("Invalid Input", 416, 64);

		addUser = new PopUp("Add User", 256, 256);
		addUser.addInput(96,  0, 128, 32, "First Name:");
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
		//page = new Page("test", new Attribute[] {new Attribute("I went to the shop to count up how dumb i am for using this hose in the wrong spot one day i thought i was a nod","")});
	}

	private void leftSideInit() {
		leftSide = new Gui();
		leftSide.addTab(0, 0, 144, GameContainer.height - 1);
		leftSide.addButton("Logout", GameContainer.height - 65, 64, 0, false);
		leftSide.addSection("Measurements", 0, 256, 0);
		leftSide.getLastTabAdded().getSections().get(0).addCheckbox("Male:", 48, 64, 8);
		leftSide.getLastTabAdded().getSections().get(0).addCheckbox("Female:", 128, 64, 8);
		leftSide.getLastTabAdded().getSections().get(0).addInput("Min Age:", 64, 96, 78, 32);
		leftSide.getLastTabAdded().getSections().get(0).addInput("Max Age:", 64, 128, 78, 32);
		leftSide.addButton("Reset Graph", 193, 64, 0, false);

		leftSide.addButton("Add Patient", 288, 64, 0, false);

		leftSide.addButton("Add User",    416, 64, 0, false);
		leftSide.addButton("Delete User", 480, 64, 0, false);

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

		// Data is passed to the graph
		if (search.getWord().length() > 0 && gc.getInput().isKeyDown(KeyEvent.VK_ENTER)) {
			DataSet ds = search.search(search.getWord(), filter);
			rightSide.getTab(0).addGraphAttribute(ds.getAttribs(), ds.getAges());
		}

		// Alert PopUp for unauthorized user action
		if (!unauthorizedAlert.isClosed()) unauthorizedAlert.update(gc, dt);
		if (unauthorizedAlert.shouldClose() && !unauthorizedAlert.isClosed()) {
			unauthorizedAlert.clearTexts();
			unauthorizedAlert.close();
		}

		// Alert PopUp for invalid user input
		if (!invalidInputAlert.isClosed()) invalidInputAlert.update(gc, dt);
		if (invalidInputAlert.shouldClose() && !invalidInputAlert.isClosed()) {
			invalidInputAlert.clearTexts();
			invalidInputAlert.close();
		}

		// Add User - validation
		if (!addUser.isClosed()) addUser.update(gc, dt);
		if (addUser.shouldClose() && !addUser.isClosed()) {
			if (!(
				// First Name
				addUser.validNotEmpty(0)
				&& addUser.validWidth(0, 20)
				// Last Name
				&& addUser.validNotEmpty(1)
				&& addUser.validWidth(1, 20)
				// Email Address
				&& addUser.validNotEmpty(2)
				&& addUser.validWidth(2, 50)
				&& addUser.validEmailAddress(2)
				// Password
				&& addUser.validNotEmpty(3)
				&& addUser.validWidth(3, 8)
			)) {
				invalidInputAlert.open();
				addUser.close();
			} else {
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
		}
		search.update(gc, filter, dt);

		// Change Password - validation
		if (editUser.shouldClose() && !editUser.isClosed()) {
			if (!(
				// New password
				editUser.validNotEmpty(0)
				&& editUser.validWidth(0, 8)
			)) {
				invalidInputAlert.open();
				editUser.close();
			} else {
				try {
					CURRENT_USER.changePassword(editUser.getStringFromInput(0));
					editUser.clearTexts();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				editUser.close();
			}
		}
		if (!editUser.isClosed()) editUser.update(gc, dt);

		// Delete User - validation
		if (removeUser.shouldClose() && !removeUser.isClosed()) {
			if (!(
				// User ID
				removeUser.validNotEmpty(0)
				&& removeUser.validInt(0)
				&& removeUser.validPositiveNumber(0)
			)) {
				invalidInputAlert.open();
				removeUser.close();
			} else {
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
		}
		if (!removeUser.isClosed()) removeUser.update(gc, dt);

		// Add Patient - validation
		if (addPatient.shouldClose() && !addPatient.isClosed()) {
			if (!(
				// First Name
				addPatient.validNotEmpty(0)
				&& addPatient.validWidth(0, 15)
				// Last Name
				&& addPatient.validNotEmpty(1)
				&& addPatient.validWidth(1, 20)
				// Address
				&& addPatient.validWidth(2, 30)
				// Gender
				&& addPatient.validNotEmpty(3)
				&& addPatient.validInt(3)
				&& addPatient.validIntMember(3, Arrays.asList(1, 2, 3))
				// DOB
				&& addPatient.validNotEmpty(4)
				&& addPatient.validDate(4, "MM/dd/yyyy")
				&& addPatient.validDateInPast(4, "MM/dd/yyyy")
			)) {
				invalidInputAlert.open();
				addPatient.close();
			} else {
				PatientModel p = new PatientModel(
						addPatient.getStringFromInput(0),
						addPatient.getStringFromInput(1) );
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
		}
		if (!addPatient.isClosed()) addPatient.update(gc, dt);

		// PatientFile Report - authorization and validation
		if (patientSearch.getWord().length()>0
				&& gc.getInput().isKeyDown(KeyEvent.VK_ENTER)) {
			if (hippaAuthorized) {
				try {
					if (!(
						// Patient ID
						patientSearch.validInt()
						&& patientSearch.validPositiveNumber()
					)) {
						invalidInputAlert.open();
						patientSearch.clearText();
					} else {
						int patient_id_input = Integer.parseInt(patientSearch.getWord());
						PatientFileModel pfm = PatientFileModel.findByID(patient_id_input);
						String[] lines = PatientFileModel.printReport(pfm.getID()).split("\n");
						Attribute[] attribs = new Attribute[lines.length];
						for (int i = 0 ; i < lines.length; i++) {
							attribs[i] = new Attribute(lines[i],"");
						}
						PatientModel p = PatientModel.findByID(pfm.getID());
						page = new Page(p.fullName(),attribs);
					}
				} catch (NumberFormatException | SQLException e) {
					e.printStackTrace();
				}
			} else {
				String authorization_error_title = "UNAUTHORIZED ACTION";
				String authorization_error_details =
						"You are logged in as a user that is not authorized to view patient data.\n";
				Attribute[] attribs = new Attribute[1];
				attribs[0] = new Attribute(authorization_error_details, "");

				page = new Page(authorization_error_title, attribs);
			}
		}

		// Left Menu Buttons - click detection and authorization
		if (leftSide.getTab(0).isButtonActive("Add User")) {
			if (hippaAuthorized)
				addUser.open();
			else
				unauthorizedAlert.open();
		}
		if (leftSide.getTab(0).isButtonActive("Password")) {
			editUser.open();
		}
		if (leftSide.getTab(0).isButtonActive("Delete User")) {
			if (hippaAuthorized)
				removeUser.open();
			else
				unauthorizedAlert.open();
		}
		if (leftSide.getTab(0).isButtonActive("Add Patient")) {
			if (hippaAuthorized)
				addPatient.open();
			else
				unauthorizedAlert.open();
		}
		if (leftSide.getTab(0).isButtonActive("Reset Graph")) {
			rightSide.getTab(0).clearGraph();
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

		unauthorizedAlert.render(gc, r);
		invalidInputAlert.render(gc, r);
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
