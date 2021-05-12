package utils;

import java.io.BufferedInputStream;
import java.io.InputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;



public class MusicStuff {

    private Clip clip;
    private AudioInputStream audioInputNormal;
    private AudioInputStream audioInputFight;

    public MusicStuff() {
        try {
            InputStream normalIs = getClass().getResourceAsStream("/Music/NormalMusic.wav");
            InputStream fightIs = getClass().getResourceAsStream("/Music/FightMusic.wav");
            if (normalIs != null && fightIs != null) {
                InputStream normalIsBuffured = new BufferedInputStream(normalIs);
                InputStream fightIsBuffured = new BufferedInputStream(fightIs);
                audioInputNormal = AudioSystem.getAudioInputStream(normalIsBuffured);
                audioInputFight = AudioSystem.getAudioInputStream(fightIsBuffured);
            } else {
                System.out.println("nul");
                System.exit(1);
            }
            clip = AudioSystem.getClip();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void playNormalMusic() {
        clip.stop();
        clip.close();
        try {
            clip.open(audioInputNormal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        clip.start();
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void playFightMusic() {
        clip.stop();
        clip.close();
        try {
            clip.open(audioInputFight);
        } catch (Exception e) {
            e.printStackTrace();
        }
        clip.start();
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
}
