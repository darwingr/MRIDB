package game;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import engine.AbstractGame;
import engine.GameContainer;
import engine.Renderer;
import game.gui.Attribute;
import game.gui.Button;
import game.gui.CommandLine;
import game.gui.Gui;
import game.gui.Page;

public class GameManager extends AbstractGame {

	private List<User> users = new ArrayList<User>();
	
	private Page page;
	private Gui leftSide, rightSide;
	private boolean login;
	private int gate;
	private CommandLine userName;
	private CommandLine password;

	public GameManager() {
		leftSideInit();
		rightSideInit();
		Attribute[] attribs = new Attribute[100];
		for(int i = 0 ; i < 100; i++) {
			attribs[i]=new Attribute("Measurement In a certain region "+i*new Random().nextInt(100),""+new Random().nextInt(567)*new Random().nextFloat());
		}
		page = new Page("Test",attribs);
		
		login = false;
		userName = new CommandLine("Username:",GameContainer.width/2-128,GameContainer.height/2,256,64);
		password = new CommandLine("Password:",GameContainer.width/2-128,GameContainer.height/2+96,256,64);
		password.censor();
		userName.setSelected(true);
		gate = 0;
		userInit();
	}

	private void userInit() {
		users.add(new User("sev","007"));
		users.add(new User("mike","achilli3s"));
		users.add(new User("cyn","123"));
		users.add(new User("dar","win"));
		
	}
	
	private void leftSideInit() {
		leftSide = new Gui();
		leftSide.addTab(0, 0, 144, GameContainer.height - 1);
		leftSide.addButton("Logout", GameContainer.height-65, 64, 0, false);
		leftSide.addButtonBranch("Filters", leftSide, 144, 256, 0, 64, 0, false, new String[] {"Disorders","Brain Region"});
		leftSide.addSection("Patient Filters", 64, 128, 0);
		leftSide.addCheckBox("Male:", 44, 38, 10, 0, 0);
		leftSide.addCheckBox("Female:", 120, 38, 10, 0, 0);
		leftSide.addInputBox("Search:",60, 54, 48, 32, 0, 0);
	}
	
	private void rightSideInit() {
		rightSide = new Gui();
		rightSide.addTab(GameContainer.width-360, 0, 359, GameContainer.height);
		rightSide.setGraph(0, 256, 0);
		
	}
	
	public void update(GameContainer gc, float dt) {
		if(login) {//Active Gui
		leftSide.update(gc, dt);
		rightSide.update(gc, dt);
		if(page!=null)
			page.update(gc, dt);
		for(Button b: rightSide.getTab(0).getButtons()) {
			if(isButton(b, "Logout") && b.isSelected() && gc.getInput().isButtonDown(1)) {
				login = false;
			}
		}
		} else {//Login Screen
			userName.update(gc, dt);
			password.update(gc, dt);
			if(userName.isSelected()&&gc.getInput().isKeyDown(KeyEvent.VK_ENTER)) {
				password.setSelected(true);
				userName.setDisplayCursor(false);
				userName.setSelected(false);
			}
			for(User u: users) {
				if(userName.getWord().equals(u.getName())&&password.getWord().equals(u.getPassword())&&gc.getInput().isKeyDown(KeyEvent.VK_ENTER)) {
					userName.clearText();
					password.clearText();
					password.setDisplayCursor(false);
					login=true;
				}
			}
		}
		
	}

	public void render(GameContainer gc, Renderer r) {
		r.drawFillRect(0, 0, gc.getWidth(), gc.getHeight(), 0xffffffff);
		if(login && page!=null)
			page.render(gc, r);
		if(login && gate == 0) {
			gate = -1;
			r.setAmbientColor(0xff999999);
		}
		if(!login && gate == -1) {
			gate = 0;
			r.setAmbientColor(0xff555555);
		}
		leftSide.render(gc, r);
		rightSide.render(gc, r);
		if(!login) {
			userName.render(gc, r);
			password.render(gc, r);
		}
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
