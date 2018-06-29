package blazingforest.input;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import blazingforest.input.InputEvent.InputState;
import blazingforest.main.ReadFile;

public class Mouse extends MouseAdapter {
	
	private ReadFile file;
	
	public Mouse(ReadFile file) {
		this.file = file;
	}
	
	public void mousePressed(MouseEvent e) {
		InputEvent inputEvent = new InputEvent();
		inputEvent.mouse = e;
		inputEvent.input = InputState.mouse;
		
		file.register(inputEvent);
	}
	
}
