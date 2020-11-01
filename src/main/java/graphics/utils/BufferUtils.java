package graphics.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class BufferUtils {
    private BufferUtils() {}

    public static FloatBuffer createFloatBuffer(float[] data) {
        FloatBuffer buffer = ByteBuffer.allocateDirect(4 * data.length).order(ByteOrder.nativeOrder()).asFloatBuffer();
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    public static IntBuffer createIntBuffer(int[] data) {
        IntBuffer buffer = ByteBuffer.allocateDirect(4 * data.length).order(ByteOrder.nativeOrder()).asIntBuffer();
        buffer.put(data);
        buffer.flip();
        return buffer;
    }
}
