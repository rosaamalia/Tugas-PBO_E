package finalprojectPBO;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

public class Sound {
	public static final AudioClip END = Applet.newAudioClip(Sound.class.getResource("end.wav"));
	public static final AudioClip PING = Applet.newAudioClip(Sound.class.getResource("ping.wav"));
	public static final AudioClip SCORE = Applet.newAudioClip(Sound.class.getResource("scoreup.wav"));

}
