package engine.ari.engine_main.rendering;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Shader {
    private int program;

    public Shader(final String vertexShaderFilePath, final String fragmentShaderFilePath) {
        int vertexShader   = createShader(loadShaderSource(vertexShaderFilePath), GL_VERTEX_SHADER);
        int fragmentShader = createShader(loadShaderSource(fragmentShaderFilePath), GL_FRAGMENT_SHADER);

        program = glCreateProgram();
        glAttachShader(vertexShader, program);
        glAttachShader(fragmentShader, program);
        glLinkProgram(program);
        glValidateProgram(program); //just during development

        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);
    }

    private String loadShaderSource(final String shaderFilePath) {
        String shaderSourceString = "";
        try {
            shaderSourceString = new String(Files.readAllBytes(Paths.get(shaderFilePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return shaderSourceString;
    }

    private int createShader(final String shaderSourceString, final int type) {
        int shader = glCreateShader(type);
        glShaderSource(shader, shaderSourceString);
        glCompileShader(shader);
        checkShaderCompileError(shader);

        return shader;
    }

    private void checkShaderCompileError(int shader) {
        if(glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println("Could not compile shader.");
            System.err.println(glGetShaderInfoLog(program));
            System.exit(-1);
        }
    }

    public void bind() {
        glUseProgram(program);
    }

    public void unbind() {
        glUseProgram(0);
    }

    public void cleanUp() {
        glDeleteProgram(program);
    }
}