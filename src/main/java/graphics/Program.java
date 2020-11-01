package graphics;

import graphics.utils.FileUtils;

import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL20.*;

public class Program {
    public int programID;

    public Program(String vertFile, String fragFile) {
        int vertHandle = loadAndCompile(vertFile, "vert");
        int fragHandle = loadAndCompile(fragFile, "frag");

        programID = glCreateProgram();
        glAttachShader(programID, vertHandle);
        glAttachShader(programID, fragHandle);
        glLinkProgram(programID);
        if (glGetProgrami(programID, GL_LINK_STATUS) != GL_TRUE) {
            System.err.println("Link error: " + glGetProgramInfoLog(programID));
        }

        // usuwamy niepotrzebne shadery
        glDeleteShader(fragHandle);
        glDeleteShader(vertHandle);
    }

    private int loadAndCompile(String filename, String type) {
        int handle = 0;
        if (type.equals("vert")) {
            handle = glCreateShader(GL_VERTEX_SHADER);
        } else if (type.equals("frag")) {
            handle = glCreateShader(GL_FRAGMENT_SHADER);
        } else {
            System.err.println("zly typ shadera!!");
        }

        String source = FileUtils.loadFile(filename);
        glShaderSource(handle, source);
        glCompileShader(handle);
        if (glGetShaderi(handle, GL_COMPILE_STATUS) != GL_TRUE) {
            System.err.println(type + " shader error: " + glGetShaderInfoLog(handle));
        }

        return handle;
    }
}
