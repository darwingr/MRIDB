package engine;

import engine.gfx.Font;

public class GameContainer implements Runnable {

	private Thread thread;
	private Window window;
	private Renderer renderer;
	private Input input;
	private AbstractGame game;
	
	private boolean running = false;
	private final double UPDATE_CAP = 1.0 / 60.0;
	public static final int width = 1080, height = 720;
	private float scale = 1f;
	private String title = "Juvanile Statistical Database (JSDB)";
	
	
	public GameContainer(AbstractGame game) {
		this.game = game;
	}

	public void start() {
		window = new Window(this);
		renderer = new Renderer(this);
		input = new Input(this);
		
		thread = new Thread(this);
		thread.run();// Run is main thread start is side thread
	}

	public void stop() {

	}

	public void run() {

		running = true;

		boolean render = false;
		double firstTime = 0;
		double lastTime = System.nanoTime() / 1000000000.0;
		double pastTime = 0;
		double unprocessedTime = 0;

		double frameTime = 0;
		int frames = 0;
		int fps = 0;
		
		while (running) {
			render = false;
			
			firstTime = System.nanoTime() / 1000000000.0;
			pastTime = firstTime - lastTime;
			lastTime = firstTime;

			unprocessedTime += pastTime;
			frameTime += pastTime;
			
			
			while (unprocessedTime >= UPDATE_CAP) {
				unprocessedTime -= UPDATE_CAP;
				render = true;
				game.update(this, (float)UPDATE_CAP);
				
				input.update();
				
				if(frameTime >= 1.0) {
					frameTime = 0;
					fps = frames;
					frames = 0;
				}
			}

			if (render) {
				renderer.clear();
				game.render(this, renderer);
				renderer.drawText(Font.SMALL_STANDARD, "Fps:"+fps, width-64, height-96, 0xffffffff);
				renderer.process();
				window.update();
				frames++;
			} else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		dispose();
		
	}
	
	private void dispose() {
		
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Window getWindow() {
		return window;
	}

	public Input getInput() {
		return input;
	}

}
