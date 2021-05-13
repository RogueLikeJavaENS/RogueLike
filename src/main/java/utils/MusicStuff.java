package utils;

import java.io.BufferedInputStream;
import java.io.InputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;



public class MusicStuff {

    private Clip musicClip;
    private Clip fxClip;
    private Clip fxClip1;

    public MusicStuff() {
        try {
            musicClip = AudioSystem.getClip();
            fxClip = AudioSystem.getClip();
            fxClip1 = AudioSystem.getClip();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playNormalMusic() {
        initInputStream("/Music/NormalMusic.wav");
    }

    public void playFightMusic() {
        initInputStream("/Music/FightMusic.wav");
    }

    public void playCoinFx() {
        playFX("/FX/coin.wav");
    }

    public void playChestFX() {
        playFX("/FX/chest.wav");
    }

    public void playButtonFX() {
        playFX("/FX/button.wav");
    }

    public void playDieFX() {
        playFX("/FX/die.wav");
    }

    public void playDoorFX() {
        playFX("/FX/door.wav");
    }

    public void playGraveFX() {
        playFX("/FX/grave.wav");
    }

    public void playMerchantFX() {
        playFX("/FX/merchant.wav");
    }

    public void playFallFX() {
        playFX("/FX/fall.wav");
    }

    public void playStabsFX() {
        playFX("/FX/stabs.wav");
    }


    public void playFX(String path) {
        try {
            Clip fxClipToUse = null;
            if (!fxClip.isActive()) {
                fxClip.close();
                fxClipToUse = fxClip;
            }
            if (!fxClip1.isActive()) {
                fxClip1.close();
                fxClipToUse = fxClip1;
            }
            InputStream is = getClass().getResourceAsStream(path);
            assert is != null;
            InputStream IsBuffered = new BufferedInputStream(is);
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(IsBuffered);
            if (fxClipToUse != null) {
                fxClipToUse.open(audioInput);
                fxClipToUse.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initInputStream(String path) {
        try {
            musicClip.stop();
            musicClip.close();
            InputStream is = getClass().getResourceAsStream(path);
            assert is != null;
            InputStream IsBuffured = new BufferedInputStream(is);
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(IsBuffured);
            musicClip.open(audioInput);
        } catch (Exception e) {
            e.printStackTrace();
        }
        musicClip.start();
        musicClip.loop(Clip.LOOP_CONTINUOUSLY);
    }
}
