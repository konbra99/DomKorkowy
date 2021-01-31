package sound;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;


import static org.lwjgl.openal.AL10.alDistanceModel;
import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class SoundManager {

    private long device;
    private long context;


    private static SoundSource backgroundSource;
    private static boolean soundEffects = true;
    private static float volume = 0.1f;
    private static SoundBuffer knifeBuffer;
    private static SoundBuffer jumpBuffer;

    public SoundManager() {
        // urzadzenia
        this.device = alcOpenDevice((ByteBuffer) null);
        if (device == NULL) {
            throw new IllegalStateException("Failed to open the default OpenAL device.");
        }
        ALCCapabilities deviceCaps = ALC.createCapabilities(device);
        this.context = alcCreateContext(device, (IntBuffer) null);
        if (context == NULL) {
            throw new IllegalStateException("Failed to create OpenAL context.");
        }
        alcMakeContextCurrent(context);
        AL.createCapabilities(deviceCaps);

        // muzyka w tle
        try {
            // muzyka w tle
            SoundBuffer backgroundBuffer = new SoundBuffer("/Sounds/a_new_leaf.ogg");
            backgroundSource = new SoundSource(true, true);
            backgroundSource.setBuffer(backgroundBuffer.getBufferId());
            backgroundSource.setGain(volume);

            // efekty dzwiekowe
            knifeBuffer = new SoundBuffer("/Sounds/knife1.ogg");
            jumpBuffer = new SoundBuffer("/Sounds/jump1.ogg");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void playSoundSource(SoundSource soundSource) {
        if (soundSource != null && !soundSource.isPlaying()) {
            soundSource.play();
        }
    }

    public void setAttenuationModel(int model) {
        alDistanceModel(model);
    }
    
    public void cleanup() {

        if (context != NULL) {
            alcDestroyContext(context);
        }
        if (device != NULL) {
            alcCloseDevice(device);
        }
    }

    public static void playBackgroundMusic() {
        backgroundSource.play();
    }

    public static void stopBackgroundMusic() {
        backgroundSource.stop();
    }

    public static void enableSoundEffects() {
        soundEffects = true;
    }

    public static void playKnifeSound() {
        if (soundEffects) {
            SoundSource source = new SoundSource(false, true);
            source.setBuffer(knifeBuffer.getBufferId());
            source.setGain(volume);
            source.play();
        }
    }

    public static void playJumpSound() {
        if (soundEffects) {
            SoundSource source = new SoundSource(false, true);
            source.setBuffer(jumpBuffer.getBufferId());
            source.setGain(volume);
            source.play();
        }
    }

    public static void disableSoundEffects() {
        soundEffects = false;
    }
}
