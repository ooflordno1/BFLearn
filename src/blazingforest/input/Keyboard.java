package blazingforest.input;

import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import blazingforest.input.InputEvent.InputState;
import blazingforest.main.ReadFile;

public class Keyboard extends KeyAdapter {
	
	private ReadFile file;
	
	public Keyboard(ReadFile file) {
		this.file = file;
	}
	
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
			case 0:
				break;
			case 12:
				break;
			case 16:
				break;
			case 17:
				break;
			case 18:
				break;
			case 20:
				break;
			case 27:
				break;
			case 33:
				break;
			case 34:
				break;
			case 35:
				break;
			case 36:
				break;
			case 37:
				break;
			case 38:
				break;
			case 39:
				break;
			case 40:
				break;
			case 112:
				break;
			case 113:
				break;
			case 114:
				break;
			case 115:
				break;
			case 116:
				break;
			case 117:
				break;
			case 118:
				break;
			case 119:
				break;
			case 120:
				break;
			case 121:
				break;
			case 123:
				break;
			case 122:
				break;
			case 144:
				break;
			case 155:
				break;
			case 524:
				break;
			default:
				InputEvent inputEvent = new InputEvent();
				inputEvent.key = e;
				inputEvent.input = InputState.key;
				inputEvent.caps = Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK);
				
				file.register(inputEvent);
				break;
		}
	}
	
}
