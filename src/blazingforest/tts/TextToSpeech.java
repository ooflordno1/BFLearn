package blazingforest.tts;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.AudioInputStream;

import marytts.LocalMaryInterface;
import marytts.MaryInterface;
import marytts.exceptions.MaryConfigurationException;
import marytts.exceptions.SynthesisException;

public class TextToSpeech {
	
	private AudioPlayer tts;
	private MaryInterface marytts;
	
	public TextToSpeech() {
		try {
			marytts = new LocalMaryInterface();
			marytts.setVoice("cmu-rms-hsmm");
		} catch (MaryConfigurationException ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public void speak(String text) {
		if (tts != null)
			tts.cancel();
		
		try (AudioInputStream audio = marytts.generateAudio(text)) {
			tts = new AudioPlayer();
			tts.setAudio(audio);
			tts.setGain(2.0f);
			tts.setDaemon(false);
			tts.start();
			if (true)
				tts.join();
		} catch (SynthesisException ex) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING, "Error saying phrase.", ex);
		} catch (IOException ex) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING, "IO Exception", ex);
		} catch (InterruptedException ex) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING, "Interrupted ", ex);
			tts.interrupt();
		}
	}
	
}
