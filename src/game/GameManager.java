package game;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import engine.AbstractGame;
import engine.GameContainer;
import engine.Renderer;
import engine.gfx.Font;
import game.gui.Button;
import game.gui.CommandLine;
import game.gui.Gui;

public class GameManager extends AbstractGame {

	private List<User> users = new ArrayList<User>();
	
	private Gui leftSide, rightSide;
	private boolean login;
	private int gate;
	private CommandLine userName;
	private CommandLine password;

	public GameManager() {
		leftSide = new Gui();
		rightSide = new Gui();
		leftSide.addTab(0, 0, 144, GameContainer.height - 1);
		rightSide.addTab(GameContainer.width - 145, 0, 144, GameContainer.height - 1);
		leftSide.addButtonBranch("Filters", leftSide, 128, 256, 0, 64, 0, false, new String[] {"ADHD","DIABETES"});
		leftSide.addButtonBranch("Branch", leftSide, 128, 256, 64, 64, 0, false, new String[] {"Option 1", "Option 2","Option 3"});
		rightSide.addButton("Logout", 0, 64, 0, false);
		login = false;
		userName = new CommandLine(GameContainer.width/2-128,GameContainer.height/2,256,64);
		password = new CommandLine(GameContainer.width/2-128,GameContainer.height/2+96,256,64);
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
	
	public void update(GameContainer gc, float dt) {
		if(login) {//Active Gui
		leftSide.update(gc, dt);
		rightSide.update(gc, dt);
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
		r.drawFillRect(0, 0, gc.getWidth(), gc.getHeight(), 0xffd3c23d);
		if(login && gate == 0) {
			gate = -1;
			r.setAmbientColor(0xff999999);
		}
		if(!login && gate == -1) {
			gate = 0;
			r.setAmbientColor(0xff666666);
		}
		leftSide.render(gc, r);
		rightSide.render(gc, r);
		if(!login) {
			r.drawText(Font.STANDARD, "Username:", GameContainer.width/2-240,GameContainer.height/2+16, 0xff000000);
			r.drawText(Font.STANDARD, "Password:", GameContainer.width/2-240,GameContainer.height/2+128, 0xff000000);
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
