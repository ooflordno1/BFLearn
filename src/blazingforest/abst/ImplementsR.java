package blazingforest.abst;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import blazingforest.input.InputEvent;
import blazingforest.main.ReadFile;

public abstract class ImplementsR {
	
	private int width, height;

	private ReadFile rFile;
	
	private String name;
	private String parentURL;
	
	private Type type;
	
	public enum Type {
		exercise,
		info,
		select;
	}
	
	public ImplementsR(int width, int height, ReadFile rFile, String parentURL, String name) {
		this.setWidth(width);
		this.setHeight(height);
		this.setrFile(rFile);
		this.setParentURL(parentURL);
		this.setName(name);
	}
	
	public abstract void open();
	
	public abstract void render(Graphics2D g);
	
	public abstract void register(InputEvent e);
	
	public abstract void addAll();
	
	protected boolean isMouseInDim(MouseEvent e, int x, int y, int w, int h) {
		if(e.getX() > x && e.getX() < x + w) {
			if(e.getY() > y && e.getY() < y + h) {
				return true;
			} else return false;
		} else return false;
	}
	
	protected void drawCenteredString(String string, Graphics2D g, int h) {
		FontMetrics fm = g.getFontMetrics();
	    int x = (getWidth() - fm.stringWidth(string)) / 2;
	    int y = (fm.getAscent() + (h - (fm.getAscent() + fm.getDescent())) / 2);
	    g.drawString(string, x, y);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public ReadFile getrFile() {
		return rFile;
	}

	public void setrFile(ReadFile rFile) {
		this.rFile = rFile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentURL() {
		return parentURL;
	}

	public void setParentURL(String parentURL) {
		this.parentURL = parentURL;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	
}
