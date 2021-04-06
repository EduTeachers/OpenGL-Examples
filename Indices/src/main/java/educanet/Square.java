package educanet;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL33;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Square {

    private float[] vertices;
    private static final int[] indices = {
            0, 1, 2, // First triangle
            1, 2, 3, // Second triangle

    };

    private float[] colors = {
            0.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.0f,
    };

    private int squareVaoId;
    private int squareVboId;
    private int squareEboId;
    private int colorsId;

    private FloatBuffer cb;

    public Square(float x, float y, float width) {
        vertices = new float[12];
        // Generate all the ids
        squareVaoId = GL33.glGenVertexArrays();

        squareVboId = GL33.glGenBuffers();
        squareEboId = GL33.glGenBuffers();
        colorsId = GL33.glGenBuffers();


        //set verticies
        for (int i = 0; i < 4; i++) {

            vertices[i * 3] = x + width * (i % 2);
            vertices[i * 3 + 1] = y - width * (Math.round(i / 2));
            vertices[i * 3 + 2] = 0.0f;

        }

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

        // Change to Color...
        // Tell OpenGL we are currently writing to this buffer (colorsId)
        GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, colorsId);

        cb = BufferUtils.createFloatBuffer(colors.length)
                .put(colors)
                .flip();

        // Send the buffer (positions) to the GPU
        GL33.glBufferData(GL33.GL_ARRAY_BUFFER, cb, GL33.GL_STATIC_DRAW);
        GL33.glVertexAttribPointer(1, 3, GL33.GL_FLOAT, false, 0, 0);
        GL33.glEnableVertexAttribArray(1);

        // Clear the buffer from the memory (it's saved now on the GPU, no need for it here)
        //MemoryUtil.memFree(cb);

    }

    public void draw() {
        //GL33.glUseProgram(Shaders.shaderProgramId);
        GL33.glBindVertexArray(squareVaoId);
        GL33.glDrawElements(GL33.GL_TRIANGLES, indices.length, GL33.GL_UNSIGNED_INT, 0);

        //update colors
        GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, colorsId);
        cb.put(colors).flip();
        GL33.glBufferData(GL33.GL_ARRAY_BUFFER, cb, GL33.GL_STATIC_DRAW);
    }

    public void changeColors(float value) {
        for (int i = 0; i < colors.length; i += 3) {
            colors[i] = (float) (Math.cos((vertices[i] + vertices[i + 1]) / Math.sin(value)%2)); //red
            colors[i + 1] = (float) (Math.cos((vertices[i] + vertices[i + 2]) / Math.sin(value)%7)); //green
            colors[i + 2] = (float) (Math.cos((vertices[i] + vertices[i + 1]) / Math.sin(value)%4)); //blue
        }
    }
}

