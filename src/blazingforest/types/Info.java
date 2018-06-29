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

public abstract class Info extends ImplementsR {
	
	private List<String> content = new ArrayList<String>();
	private List<Integer> catagory = new ArrayList<Integer>();
	
	private int curent;
	private int amount;
	
	public Info(int width, int height, ReadFile rFile, String parentURL, String name) {
		super(width, height, rFile, parentURL, name);
		
		setType(Type.select);
	}
	
	public void open() {
		addAll();
		
		curent = 1;
		amount = 0;

		int last = 0;
		
		for(int i = 0; i < catagory.size(); i++) {
			if(last != catagory.get(i)) {
				last = catagory.get(i);
				amount++;
			}
		}
	}
	
	public void render(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 60, getWidth(), getHeight());
		
		g.setColor(new Color(255, 240, 190));
		g.fillRect(0, 0, getWidth(), 60);
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("Calibri", Font.BOLD, 30));
		g.drawString("Exit", 30, 40);
		
		g.setFont(new Font("Calibri", Font.PLAIN, 36));
		drawCenteredString(getName(), g, 60);
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("Calibri", Font.PLAIN, 24));
		
		int h = 0;
		
		for(int i = 0; i < catagory.size(); i++) {
			if(catagory.get(i) == curent) {
				h++;
				drawCenteredString(content.get(i), g, ( h + 1 ) * 100);
			}
		}
		
		g.setColor(new Color(220, 220, 220));
		g.fillRect(0, getHeight() - 100, getWidth(), 70);
		
		g.setColor(new Color(220, 230, 255));
		g.fillRect(0, getHeight() - 100, 280, 70);
		
		int percent = ( curent * 100 ) / amount;
		
		g.setFont(new Font("Calibri", Font.PLAIN, 30));
		g.setColor(Color.BLACK);
		g.drawString(percent + "% Overall", 60, getHeight() - 60);
		
		if(amount != 1) {
			g.setColor(new Color(230, 255, 230));
			if(curent != 1) g.fillRect(getWidth() - 400, getHeight() - 100, 400, 70);
			else g.fillRect(getWidth() - 200, getHeight() - 100, 200, 70);
			
			g.setColor(Color.BLACK);
			
			if(curent == amount)
				g.drawString("Exit", getWidth() - 130, getHeight() - 60);
			else
				g.drawString("Next", getWidth() - 140, getHeight() - 60);
			
			if(curent != 1) g.drawString("Back", getWidth() - 330, getHeight() - 60);
		} else {
			g.setColor(new Color(220, 255, 220));
			g.fillRect(getWidth() - 200, getHeight() - 100, 200, 70);
			
			g.setColor(Color.BLACK);
			g.drawString("Exit", getWidth() - 130, getHeight() - 60);
		}
		
	}
	
	public void register(InputEvent e) {
		if(e.input == InputState.mouse) {
			if(getParentURL() != null)
				if(isMouseInDim(e.mouse, 0, 0, 120, 60))
					getrFile().setCurentClass(getParentURL());
			
			if(isMouseInDim(e.mouse, getWidth() - 200, getHeight() - 100, 200, 60)) {
				if(curent == amount)
					getrFile().setCurentClass(getParentURL());
				else
					curent++;
			}
			
			if(isMouseInDim(e.mouse, getWidth() - 400, getHeight() - 100, 200, 60)) {
				if(curent != 1)
					curent--;
			}
		}
	}
	
	public void add(String string, int i) {
		this.content.add(string);
		this.catagory.add(i);
	}
	
}
