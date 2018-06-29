package blazingforest.types;

import blazingforest.main.ReadFile;

public abstract class TranslationExercise extends Exercise {
	
	private String lang = "";
	
	public TranslationExercise(int width, int height, ReadFile file, String parentURL, String name, String language) {
		super(width, height, file, parentURL, name);
		this.lang = language;
	}
	
	public void add(String question, String answer) {
		this.add("What is '" + question + "' in English?", answer, true);
		this.add("What is '" + answer + "' in " + lang + "?", question, true);
	}
	
}
