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

    private final long device;
    private final long context;

    public SoundManager() {
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

    public void playBackgroundMusic() {
        try {
            SoundBuffer soundBuffer = new SoundBuffer("/Sounds/a_new_leaf.ogg");
            SoundSource soundSource = new SoundSource(true, true);

            soundSource.setBuffer(soundBuffer.getBufferId());
            soundSource.setGain(2.0f);
            soundSource.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
