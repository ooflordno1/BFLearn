package blazingforest.main;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import blazingforest.abst.ImplementsR;
import blazingforest.input.InputEvent;
import blazingforest.tts.TextToSpeech;
import blazingforest.types.DerivationExercise;
import blazingforest.types.Exercise;
import blazingforest.types.ImperfectTenseExercise;
import blazingforest.types.Info;
import blazingforest.types.PerfectTenseExercise;
import blazingforest.types.Select;
import blazingforest.types.TTSExercise;
import blazingforest.types.TranslationExercise;

public class ReadFile {
	
	private String curentURL, previousURL;
	private int width, height;
	private ImplementsR currentClass;
	private TextToSpeech tts;
	private boolean isOnline;
	private String initialURL;
	
	public ReadFile(String url, int width, int height, boolean isOnline) {
		this.width = width;
		this.height = height;
		this.isOnline = isOnline;
		this.initialURL = url;
		
		try {
			this.tts = new TextToSpeech();
		} catch(Error e) {
			tts = null;
		} catch(IllegalArgumentException e) {
			tts = null;
		}
			
		this.curentURL = url;
		this.previousURL = null;
		
		setCurentClass(url);
	}
	
	public void setURL(String url) {
		this.curentURL = url;
		
		String[] split = curentURL.split("/");
		String built = "";
		
		for(int i = 0; i < split.length - 1; i++) {
			if((!split[i].equals("/") && !split[i].equals("") ) || split[i - 1].equals("https:"))
				built = built + split[i] + "/";
		}
		
		this.previousURL = built;
	}
	
	public String getURL() {
		return curentURL.toString();
	}
	
	public List<String> readFile() {
		try {
			URL mainDir = null;
			File fileDir = null;
			BufferedReader reader = null;
			
			if(isOnline) mainDir = new URL(curentURL + "/Main");
			else fileDir = new File(curentURL + "/Main");
			
			if(isOnline) reader = new BufferedReader(new InputStreamReader(mainDir.openStream(), StandardCharsets.UTF_8));
			if(!isOnline) reader = new BufferedReader(new FileReader(fileDir));
			
			List<String> fileList = new ArrayList<String>();
			
			reader.lines().forEach(fileList::add);
			
			reader.close();
			
			return fileList;
		} catch (UnknownHostException e) {
			List<String> sList = new ArrayList<String>();
			sList.add("Error");
			sList.add("Connection Issue - Cannot Connect to Server");
			sList.add("Server name: " + curentURL);
			return sList;
		} catch (IOException e) {
			List<String> sList = new ArrayList<String>();
			sList.add("Error");
			sList.add("IO Issue");
			sList.add(e.getMessage());
			return sList;
		}
	}
	
	public ImplementsR makeClassFromURL() {
		List<String> file = readFile();
		
		if(file.get(0).equals("Select")) {
			ImplementsR d = new Select(width, height, this, previousURL, file.get(1)){
				public void addAll() {
					for(int i = 0; i < file.size() - 2; i++)
						add(getURL() + file.get(i + 2), file.get(i + 2).split("/")[1]);
				}
			};
			
			return d;
		} else if(file.get(0).equals("Translation")) {
			ImplementsR d = new TranslationExercise(width, height, this, previousURL, file.get(1), file.get(2)) {
				public void addAll() {
					for(int i = 0; i < file.size() - 3; i++)
						add(file.get(i + 3).split(" ")[0], file.get(i + 3).split(" ")[1]);
				}
			};
			
			return d;
		} else if(file.get(0).equals("Derivation")) {
			ImplementsR d = new DerivationExercise(width, height, this, previousURL, file.get(1), file.get(2)) {
				public void addAll() {
					for(int i = 0; i < file.size() - 3; i++)
						add(file.get(i + 3).split(" ")[0], file.get(i + 3).split(" ")[1]);
				}
			};
			
			return d;
		}  else if(file.get(0).equals("TTS")) {
			ImplementsR d = new TTSExercise(width, height, this, previousURL, file.get(1), tts) {
				public void addAll() {
					for(int i = 0; i < file.size() - 2; i++)
						add(file.get(i + 2));
				}
			};
			
			return d;
		} else if(file.get(0).equals("Perfect")) {
			ImplementsR d = new PerfectTenseExercise(width, height, this, previousURL, file.get(1), file.get(2)) {
				public void addAll() {
					setInfo(file.get(3));
					add(file.get(4).split(" ")[0], file.get(4).split(" ")[1]);
				}
			};
			
			return d;
		} else if(file.get(0).equals("Imperfect")) {
			ImplementsR d = new ImperfectTenseExercise(width, height, this, previousURL, file.get(1), file.get(2)) {
				public void addAll() {
					setInfo(file.get(3));
					add(file.get(4).split(" ")[0], file.get(4).split(" ")[1]);
				}
			};
			
			return d;
		} else if(file.get(0).equals("Exercise")) {
			ImplementsR d = new Exercise(width, height, this, previousURL, file.get(1)) {
				public void addAll() {
					for(int i = 0; i < file.size() - 2; i++)
						add(file.get(i + 2).split(" ")[0], file.get(i + 2).split(" ")[1], true);
				}
			};
			
			return d;
		} else if(file.get(0).equals("Error")) {
			ImplementsR d = new Info(width, height, this, curentURL, file.get(0)) {
				public void addAll() {
					int count = 1;
					int t = 1;
					for(int i = 0; i < file.size() - 1; i++) {
						add(file.get(i + 1), t);
						
						if(count == 8) {
							count = 0;
							t++;
						}
						
						count++;
					}
				}
			};
			
			return d;
		} else if(file.get(0).equals("Info")) {
			ImplementsR d = new Info(width, height, this, previousURL, file.get(1)) {
				public void addAll() {
					int count = 1;
					int t = 1;
					for(int i = 0; i < file.size() - 2; i++) {
						add(file.get(i + 2), t);
						
						if(count == 8) {
							count = 0;
							t++;
						}
						
						count++;
					}
				}
			};
			
			return d;
		} else {
			ImplementsR d = new Info(width, height, this, previousURL, "Class Not Found"){
				public void addAll() {
					add("This Class Contains Content That Doesn't Work On This Version", 1);
					add("Please Update To The Latest Version", 1);
					add("Incase This Is An Error The Type Of Content The Program Doesn't Recognize Is", 1);
					add(file.get(0), 1);
				}
			};
			
			return d;
		}
	}
	
	public void setCurentClass(String newURL) {
		if(newURL.equals(initialURL) && previousURL != null) return;
		setURL(newURL);
		currentClass = makeClassFromURL();
		currentClass.open();
	}
	
	public void render(Graphics2D g) {
		currentClass.render(g);
	}

	public void register(InputEvent inputEvent) {
		currentClass.register(inputEvent);
	}
	
}
