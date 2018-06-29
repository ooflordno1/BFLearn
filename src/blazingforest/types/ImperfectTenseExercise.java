package blazingforest.types;

import blazingforest.main.ReadFile;

public abstract class ImperfectTenseExercise extends Exercise {
	
	private String lang = "";
	
	public ImperfectTenseExercise(int width, int height, ReadFile file, String parentURL, String name, String language) {
		super(width, height, file, parentURL, name);
		this.lang = language;
	}
	
	public void add(String question, String answer) {
		this.add("Imperfect tense of 'I " + question + "' in " + lang, answer + "bam", true);
		this.add("Imperfect tense of 'You (s) " + question + "' in " + lang, answer + "bas", true);
		this.add("Imperfect tense of 'He " + question + "s' in " + lang, answer + "bat", true);
		this.add("Imperfect tense of 'We " + question + "' in " + lang, answer + "bamus", true);
		this.add("Imperfect tense of 'You (p) " + question + "' in " + lang, answer + "batis", true);
		this.add("Imperfect tense of 'They " + question + "' in " + lang, answer + "bant", true);
	}
	
}
