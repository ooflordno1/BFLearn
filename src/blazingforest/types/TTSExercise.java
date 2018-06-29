package blazingforest.types;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import blazingforest.abst.ImplementsR;
import blazingforest.input.InputEvent;
import blazingforest.input.InputEvent.InputState;
import blazingforest.main.ReadFile;
import blazingforest.tts.TextToSpeech;

public abstract class TTSExercise extends ImplementsR {
	
	private List<String> info = new ArrayList<String>();
	
	private List<String> qaa = new ArrayList<String>();
	private String input;
	
	private int curent;
	
	private boolean isCorrect = false;
	
	private State state;
	
	private TextToSpeech tts;
	
	public abstract void addAll();
	
	private enum State {
		info,
		question,
		answer;
	}
	
	public TTSExercise(int width, int height, ReadFile readFile, String parentURL, String name, TextToSpeech tts) {
		super(width, height, readFile, parentURL, name);
		this.tts = tts;
		
		curent = 0;
		state = State.info;
		input = "";
		setType(Type.exercise);
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
		
		if(state == State.info) {
			
			g.setFont(new Font("Calibri", Font.PLAIN, 64));
			drawCenteredString(getName(), g, 300);
			
			g.setFont(new Font("Calibri", Font.PLAIN, 36));
			for(int i = 0; i < info.size(); i++)
				drawCenteredString(info.get(i), g, 500 + ( i * 100 ));
			
			g.setColor(new Color(220, 255, 220));
			g.fillRect(getWidth() / 12 * 5, getHeight() / 3 * 2, getWidth() / 6, 80);
			
			g.setColor(Color.BLACK);
			g.drawRect(getWidth() / 12 * 5, getHeight() / 3 * 2, getWidth() / 6, 80);
			
			drawCenteredString("Start", g, 1035);
			
		} else if(state == State.question) {
			
			g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 64));
			g.setColor(Color.BLACK);
			drawCenteredString("How Do You Spell:", g, 420);
			
			g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 42));
			drawCenteredString(input, g, 700);
			g.drawRect(80, 320, getWidth() - 140, 60);
			
			g.setColor(new Color(220, 255, 220));
			g.fillRect(400, getHeight() / 5 * 3, getWidth() - 800, 100);
			
			g.setColor(Color.BLACK);
			g.drawRect(400, getHeight() / 5 * 3, getWidth() - 800, 100);
			
			drawCenteredString("Next", g, 950);
			
		} else if(state == State.answer) {
			
			g.setFont(new Font("Calibri", Font.PLAIN, 64));
			g.setColor(Color.BLACK);
			
			if(isCorrect) {
				drawCenteredString("Correct!", g, 550);
			} else if (!isCorrect) {
				drawCenteredString("Incorrect", g, 550);
				
				g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 32));
				drawCenteredString("The correct answer was : " + qaa.get(curent), g, 680);
			}
			
			g.drawRect(450, 400, getWidth() - 900, 100);
			drawCenteredString("Next", g, 890);
		}
		
	}
	
	public void register(InputEvent e) {
		if(e.input == InputState.mouse) {
			
			if(getParentURL() != null)
				if(isMouseInDim(e.mouse, 0, 0, 120, 60))
					getrFile().setCurentClass(getParentURL());
			
			if(state == State.info) {
				Collections.shuffle(qaa);
				
				if(isMouseInDim(e.mouse, getWidth() / 12 * 5, getHeight() / 3 * 2, getWidth() / 6, 80)) {
					if(qaa.size() < 1) getrFile().setCurentClass(getParentURL());
					
					state = State.question;
					curent = 0;
					tts.speak(qaa.get(curent));
				}
				
			} else if(state == State.question) {
				
				if(isMouseInDim(e.mouse, 400, getHeight() / 5 * 3, getWidth() - 800, 100))
					checkIfAnswerIsCorrect();
				
			} else if(state == State.answer) {
				
				if(isMouseInDim(e.mouse, 450, 400, getWidth() - 900, 100))
					 checkIfCanContinue();
				
			}
			
		} else if(e.input == InputState.key) {
			
			switch(e.key.getKeyCode()) {
				case 8 :
					if (input != null && input.length() > 0)
						input = input.substring(0, input.length() - 1);
					break;
				case 127 :
					if (input != null && input.length() > 0)
						input = input.substring(0, input.length() - 1);
					break;
				case 10:
					if(state == State.question) checkIfAnswerIsCorrect();
					else if(state == State.answer) checkIfCanContinue();
					break;
				default :
					input = input + e.key.getKeyChar();
					break;
			}
			
		}
	}
	
	public void add(String answer) {
		qaa.add(answer);
	}
	
	public void checkIfAnswerIsCorrect() {
		if(qaa.get(curent).toLowerCase().equals(input.toLowerCase())) {
			input = "";
			
			isCorrect = true;
			state = State.answer;
		} else {
			qaa.add(qaa.get(curent));
			
			isCorrect = false;
			state = State.answer;
		}
	}
	
	public void checkIfCanContinue() {
		if(curent == qaa.size())
			getrFile().setCurentClass(getParentURL());
		
		if (curent < qaa.size()) {
			input = "";
			curent++;
			state = State.question;
			tts.speak(qaa.get(curent));
		} else
			getrFile().setCurentClass(getParentURL());
	}
	
	public void open() {
		qaa.clear();
		addAll();
	}
	
	public void setInfo(String info) {
		this.info.add(info);
	}
	
}
