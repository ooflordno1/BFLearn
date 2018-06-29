package blazingforest.input;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class InputEvent {
	
	public MouseEvent mouse;
	public KeyEvent key;
	
	public InputState input;
	
	public boolean caps;
	
	public enum InputState {
		mouse,
		key;
	}
	
}
