package blazingforest.types;

import blazingforest.main.ReadFile;

public abstract class DerivationExercise extends Exercise {
	
	private String lang = "";
	
	public DerivationExercise(int width, int height, ReadFile file, String parentURL, String name, String language) {
		super(width, height, file, parentURL, name);
		this.lang = language;
	}
	
	public void add(String question, String answer) {
		this.add("What is the derivation for '" + question + "' in English?", answer, true);
		this.add("What does '" + answer + "' derive from in " + lang + "?", question, true);
	}
	
}
