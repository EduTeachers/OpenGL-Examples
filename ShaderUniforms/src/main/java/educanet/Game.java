package educanet;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL33;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Game {

    private static final float[] vertices = {
            0.5f, 0.5f, 0.0f, // 0 -> Top right
            0.5f, -0.5f, 0.0f, // 1 -> Bottom right
            -0.5f, -0.5f, 0.0f, // 2 -> Bottom left
            -0.5f, 0.5f, 0.0f, // 3 -> Top left
    };

    private static final int[] indices = {
            0, 1, 3, // First triangle
            1, 2, 3 // Second triangle
    };

    private static int squareVaoId;
    private static int squareVboId;
    private static int squareEboId;
    private static int uniformColorLocation;
    private static int matrixLocation;
    private static FloatBuffer matrixFloatBuffer;
    private static Matrix4f viewMatrix = new Matrix4f()
            .identity()
            .scale(0.25f);

    public static void init(long window) {
        // Setup shaders
        Shaders.initShaders();

        matrixLocation = GL33.glGetUniformLocation(Shaders.shaderProgramId,"matrix");

        // Get uniform location
        uniformColorLocation = GL33.glGetUniformLocation(Shaders.shaderProgramId, "outColor");

        matrixFloatBuffer = BufferUtils.createFloatBuffer(16);

        // Generate all the ids
        squareVaoId = GL33.glGenVertexArrays();
        squareVboId = GL33.glGenBuffers();
        squareEboId = GL33.glGenBuffers();

        // Tell OpenGL we are currently using this object (vaoId)
        GL33.glBindVertexArray(squareVaoId);

        // Tell OpenGL we are currently writing to this buffer (eboId)
        GL33.glBindBuffer(GL33.GL_ELEMENT_ARRAY_BUFFER, squareEboId);
        IntBuffer ib = BufferUtils.createIntBuffer(indices.length)
                .put(indices)
                .flip();
        GL33.glBufferData(GL33.GL_ELEMENT_ARRAY_BUFFER, ib, GL33.GL_STATIC_DRAW);

        // Change to VBOs...
        // Tell OpenGL we are currently writing to this buffer (vboId)
        GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, squareVboId);

        FloatBuffer fb = BufferUtils.createFloatBuffer(vertices.length)
                .put(vertices)
                .flip();

        // Send the buffer (positions) to the GPU
        GL33.glBufferData(GL33.GL_ARRAY_BUFFER, fb, GL33.GL_STATIC_DRAW);
        GL33.glVertexAttribPointer(0, 3, GL33.GL_FLOAT, false, 0, 0);
        GL33.glEnableVertexAttribArray(0);

        // Clear the buffer from the memory (it's saved now on the GPU, no need for it here)
        MemoryUtil.memFree(fb);

        GL33.glUseProgram(Shaders.shaderProgramId);
        GL33.glUniform3f(uniformColorLocation, 1f, 0.5f, 0.0f);
    }

    public static void render(long window) {
        GL33.glUseProgram(Shaders.shaderProgramId);

        // Draw using the glDrawElements function
        GL33.glBindVertexArray(squareVaoId);
        GL33.glDrawElements(GL33.GL_TRIANGLES, indices.length, GL33.GL_UNSIGNED_INT, 0);
    }

    public static void update(long window) {
        if(GLFW.glfwGetKey(window, GLFW.GLFW_KEY_D ) == GLFW.GLFW_PRESS){
            viewMatrix.translate(0.0001f,0.0f,0.0f);
        }
        else if(GLFW.glfwGetKey(window, GLFW.GLFW_KEY_A ) == GLFW.GLFW_PRESS){
            viewMatrix.translate(-0.0001f,0.0f,0.0f);
        }
        else if(GLFW.glfwGetKey(window, GLFW.GLFW_KEY_W ) == GLFW.GLFW_PRESS){
            viewMatrix.translate(0.0f,0.0001f,0.0f);
        }
        else if(GLFW.glfwGetKey(window, GLFW.GLFW_KEY_S ) == GLFW.GLFW_PRESS){
            viewMatrix.translate(0.0f,-0.0001f,0.0f);
        }
        viewMatrix.get(matrixFloatBuffer);
        GL33.glUniformMatrix4fv(matrixLocation,false, matrixFloatBuffer);
    }

}
