package cz.educanet;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL33;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

public class Game {

    private static final float vertices[] = {
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f,
            0.5f, 0.5f, 0.0f/*, // Uncomment this for the square
            -0.5f, -0.5f, 0.0f,
            -0.5f, 0.5f, 0.0f,
            0.5f, 0.5f, 0.0f*/
    };
    private static int triangleVaoId;
    private static int triangleVboId;

    public static void init(long window) {
        // Setup shaders
        Shaders.initShaders();

        // Generate all the ids
        triangleVaoId = GL33.glGenVertexArrays();
        triangleVboId = GL33.glGenBuffers();

        // Tell OpenGL we are currently using this object (vaoId)
        GL33.glBindVertexArray(triangleVaoId);
        // Tell OpenGL we are currently writing to this buffer (vboId)
        GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, triangleVboId);

        FloatBuffer fb = BufferUtils.createFloatBuffer(vertices.length)
                .put(vertices)
                .flip();

        // Send the buffer (positions) to the GPU
        GL33.glBufferData(GL33.GL_ARRAY_BUFFER, fb, GL33.GL_STATIC_DRAW);
        GL33.glVertexAttribPointer(0, 3, GL33.GL_FLOAT, false, 0, 0);
        GL33.glEnableVertexAttribArray(0);

        // Clear the buffer from the memory (it's saved now on the GPU, no need for it here)
        MemoryUtil.memFree(fb);
    }

    public static void render(long window) {
        GL33.glUseProgram(Shaders.shaderProgramId);
        GL33.glBindVertexArray(triangleVaoId);
        GL33.glDrawArrays(GL33.GL_TRIANGLES, 0, vertices.length / 3);
    }

    public static void update(long window) {
    }

}
