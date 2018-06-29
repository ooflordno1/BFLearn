package blazingforest.types;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import blazingforest.abst.ImplementsR;
import blazingforest.input.InputEvent;
import blazingforest.input.InputEvent.InputState;
import blazingforest.main.ReadFile;

public abstract class Select extends ImplementsR {
	
	private int offset;
	private List<String> names = new ArrayList<String>();
	private List<String> classRedir = new ArrayList<String>();
	
	public Select(int width, int height, ReadFile rFile, String parentURL, String name) {
		super(width, height, rFile, parentURL, name);
		setType(Type.select);
	}
	
	public void open() {
		offset = 0;
		addAll();
	}
	
	public void render(Graphics2D g) {
		g.setColor(new Color(255, 240, 190));
		g.fillRect(0, 60, getWidth(), getHeight());
		
		g.setColor(Color.WHITE);
		g.fillRect(100, 120, getWidth() - 200, getHeight() - 240);
		
		g.setColor(new Color(200, 220, 255));
		g.fillRect(100, 120, getWidth() - 200, 60);
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("Calibri", Font.BOLD, 36));
		g.drawString(getName(), 120, 162);
		
		g.setFont(new Font("Calibri", Font.PLAIN, 30));
		
		for(int i = 0; i + offset < names.size() && i < 8; i++) {
			g.drawString(i + 1 + offset + ". " + names.get(i + offset), 140, 230 + i * 50);
			if(names.size() > 8) g.drawString(">" , getWidth() - 250, 230 + i * 50);
			else g.drawString(">" , getWidth() - 200, 230 + i * 50);
		}
		
		if(names.size() > 8) {
			g.setColor(new Color(200, 220, 255));
			g.fillRect(getWidth() - 180, 120, 80, getHeight() - 240);
			
			if(offset > 0) g.setColor(Color.BLACK);
			else g.setColor(Color.LIGHT_GRAY);
			g.drawString("/\\", getWidth() - 150, getHeight() - 480);
			
			if(offset < names.size() - 8) g.setColor(Color.BLACK);
			else g.setColor(Color.LIGHT_GRAY);
			g.drawString("\\/", getWidth() - 150, getHeight() - 220);
		}
		
		g.setColor(new Color(210, 210, 210));
		g.fillRect(0, 0, getWidth(), 60);
		
		g.setFont(new Font("Calibri", Font.BOLD, 36));
		
		g.setColor(Color.BLACK);
		g.drawString("BFLearn", 20, 40);
		
		g.setFont(new Font("Calibri", Font.BOLD, 30));
		
		String[] split = getrFile().getURL().split("/");
		
		if(!split[split.length - 1].equals("Dashboard") && getParentURL() != null)
			g.drawString("Back", 300, 40);
	}
	
	public void register(InputEvent e) {
		if(e.input == InputState.mouse) {
			for(int i = 0; i < names.size(); i++) {
				if(names.size() > 8) {
					if(isMouseInDim(e.mouse, 100, 195 + i * 50, getWidth() - 280, 50)) {
						getrFile().setCurentClass(classRedir.get(i));
					}
				} else {
					if(isMouseInDim(e.mouse, 100, 195 + i * 50, getWidth() - 200, 50)) {
						getrFile().setCurentClass(classRedir.get(i));
					}
				}
			}
			
			if(isMouseInDim(e.mouse, getWidth() - 180, 120, 80, getHeight() - 480)) {
				if(offset > 0) offset--;
			}
			
			if(isMouseInDim(e.mouse, getWidth() - 180, 360, 80, getHeight() - 480)) {
				if(offset < names.size() - 8) offset++;
			}
			
			if(getParentURL() != null) {
				if(isMouseInDim(e.mouse, 280, 0, 100, 60)) {
					String[] split = getrFile().getURL().split("/");
					
					if(!split[split.length - 1].equals("Dashboard") && getParentURL() != null)
						getrFile().setCurentClass(getParentURL());
				}
			}
		}
	}
	
	public void add(String url, String childrenName) {
		this.classRedir.add(url);
		this.names.add(childrenName);
	}
	
}
