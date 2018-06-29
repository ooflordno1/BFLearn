package blazingforest.types;

import blazingforest.main.ReadFile;

public abstract class PerfectTenseExercise extends Exercise {
	
	private String lang = "";
	
	public PerfectTenseExercise(int width, int height, ReadFile file, String parentURL, String name, String language) {
		super(width, height, file, parentURL, name);
		this.lang = language;
	}
	
	public void add(String question, String answer) {
		this.add("Perfect tense of 'I " + question + "' in " + lang, answer + "i", true);
		this.add("Perfect tense of 'You (s) " + question + "' in " + lang, answer + "isti", true);
		this.add("Perfect tense of 'He " + question + "s' in " + lang, answer + "it", true);
		this.add("Perfect tense of 'We " + question + "' in " + lang, answer + "imus", true);
		this.add("Perfect tense of 'You (p) " + question + "' in " + lang, answer + "istis", true);
		this.add("Perfect tense of 'They " + question + "' in " + lang, answer + "erunt", true);
	}
	
}
