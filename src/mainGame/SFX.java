package mainGame;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
public class SFX {
	private Clip hit, shot, eHit;
	public SFX() {
		try {
			AudioInputStream hitStream;
			hitStream = AudioSystem.getAudioInputStream(ClassLoader.getSystemResource("hit.wav"));
			hit = AudioSystem.getClip();
			hit.open(hitStream);
			AudioInputStream shootStream;
			shootStream = AudioSystem.getAudioInputStream(ClassLoader.getSystemResource("shot.wav"));
			shot = AudioSystem.getClip();
			shot.open(shootStream);
			AudioInputStream eHitStream;
			eHitStream = AudioSystem.getAudioInputStream(ClassLoader.getSystemResource("eHit.wav"));
			eHit = AudioSystem.getClip();
			eHit.open(eHitStream);
		} catch (Exception e) {
			System.out.println("Sound error");
			e.printStackTrace();
		}
	}
	public void hit() {
		if(hit != null) {
			hit.setFramePosition(0);
			hit.start();
		}
	}
	public void shot() {
		if(shot != null) {
			shot.setFramePosition(0);
			shot.start();
		}
	}
	public void eHit() {
		if(eHit != null) {
			eHit.setFramePosition(0);
			eHit.start();
		}
	}
}
