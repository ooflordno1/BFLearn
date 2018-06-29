package blazingforest.main;

import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.util.Map;

import blazingforest.input.Keyboard;
import blazingforest.input.Mouse;

public class Main extends Canvas implements Runnable {
	
	private static final long serialVersionUID = 5650672376092945488L;
	
	private int width = 1280, height = 720;
	
	private Thread thread;
	
	private Mouse mouse;
	private Keyboard keyboard;
	
	private ReadFile readFile;
	private boolean isOnline;
	
	public Main() {
		isOnline = false;
		
		String initialURL = "";
		if(isOnline) initialURL = "https://raw.githubusercontent.com/blazingforest/BFLearn/master";
		else initialURL = System.getProperty("user.dir");
		
		if(isOnline) readFile = new ReadFile(initialURL + "/BFLearnFiles/Dashboard", width, height, isOnline);
		else readFile = new ReadFile(initialURL + "\\BFLearnFiles\\Dashboard", width, height, isOnline);
		
		System.out.println(readFile.getURL());
		
		mouse = new Mouse(readFile);
		this.addMouseListener(mouse);
		
		keyboard = new Keyboard(readFile);
		this.addKeyListener(keyboard);
		
		new Display(width, height, this);
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
	}
	
	@Override
	public void run() {
		while(true) {
			render();
		}
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		
		Map<?, ?> desktopHints = (Map<?, ?>) Toolkit.getDefaultToolkit().getDesktopProperty("awt.font.desktophints");
		
		if (desktopHints != null)
			g.setRenderingHints(desktopHints);
		
		readFile.render(g);
		
		g.dispose();
		bs.show();
	}
	
	public static void main(String[] args) {
		new Main();
	}
	
}
