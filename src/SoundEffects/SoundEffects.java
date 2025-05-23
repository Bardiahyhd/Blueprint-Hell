package SoundEffects;

import Config.Config;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundEffects {
    private static final AudioClip clickSound;
    private static final AudioClip impactSound;

    static {
        clickSound = new AudioClip(SoundEffects.class.getResource("ConnectedClick.mp3").toString());
        impactSound = new AudioClip(SoundEffects.class.getResource("ImpactSound.mp3").toString());
    }

    public static void ConnectedSoundEffect() {
        clickSound.play();
    }

    public static void ImpactSoundEffect() {
        impactSound.play();
    }
}
