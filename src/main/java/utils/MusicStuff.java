package utils;

import java.io.BufferedInputStream;
import java.io.InputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;



public class MusicStuff {

    private Clip fightingClip;
    private Clip normalClip;

    public MusicStuff() {
        try {
            fightingClip = AudioSystem.getClip();
            normalClip = AudioSystem.getClip();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playNormalMusic() {
        initInputStream("/Music/NormalMusic.wav", fightingClip, normalClip);
    }

    public void playFightMusic() {
        initInputStream("/Music/FightMusic.wav", normalClip, fightingClip);
    }

    private void initInputStream(String path, Clip normalClip, Clip fightingClip) {
        try {
            InputStream is = getClass().getResourceAsStream(path);
            assert is != null;
            InputStream IsBuffured = new BufferedInputStream(is);
            normalClip.stop();
            normalClip.close();
            fightingClip.stop();
            fightingClip.close();
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(IsBuffured);
            fightingClip.open(audioInput);
        } catch (Exception e) {
            e.printStackTrace();
        }
        fightingClip.start();
        fightingClip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void playChest() {
        InputStream is = getClass().getResourceAsStream("/Music/chest_low.wav");
        assert is != null;
        InputStream IsBuffured = new BufferedInputStream(is);
        try {
            Clip clipTmp = AudioSystem.getClip();
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(IsBuffured);
            clipTmp.open(audioInput);
            clipTmp.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
