package engine.renderers;


import engine.Camera;
import engine.interfaces.Disposable;
import engine.shaders.BoardShader;
import game.Board;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.List;

import static org.lwjgl.opengl.GL45.*;

public class BoardRenderer implements Disposable {
    private final BoardShader shader;
    private int vao;
    private int pbo, vbo, vboSize = 0;

    public BoardRenderer() {
        this.shader = new BoardShader();

        createMesh();
    }

    private void createMesh() {
        this.vao = glCreateVertexArrays();
        this.vbo = glCreateBuffers();
        this.pbo = glCreateBuffers();

        glVertexArrayVertexBuffer(vao, 0, pbo, 0, 8);
        glVertexArrayVertexBuffer(vao, 1, vbo, 0, 16);

        glEnableVertexArrayAttrib(vao, 0);
        glEnableVertexArrayAttrib(vao, 1);

        glVertexArrayAttribFormat(vao, 0, 2, GL_FLOAT, false, 0);
        glVertexArrayAttribFormat(vao, 1, 4, GL_FLOAT, false, 0);

        glVertexArrayAttribBinding(vao, 0, 0);
        glVertexArrayAttribBinding(vao, 1, 1);

        glVertexArrayBindingDivisor(vao, 1, 1);

        glNamedBufferData(pbo, new float[]{
                1, 1, //1
                0, 1, //2
                0, 0, //3
                0, 0, //3
                1, 0, //4
                1, 1, //1
        }, GL_STATIC_DRAW);
    }

    private void uploadData(FloatBuffer color) {
        int vertexSize = color.limit() * Float.BYTES;
        if (vertexSize > vboSize) {
            if (vbo != 0) {
                glDeleteBuffers(vbo);
                this.vbo = glCreateBuffers();
                glVertexArrayVertexBuffer(vao, 1, vbo, 0, 16);
            }
            glNamedBufferStorage(vbo, vertexSize, GL_DYNAMIC_STORAGE_BIT);
            this.vboSize = vertexSize;
        }
        glNamedBufferSubData(vbo, 0, color);
    }

    public void setBoard(Board board) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer color = stack.mallocFloat(4 * board.getWidth() * board.getHeight());

            for (int i = 0, isBlack=0; i < board.getWidth(); i++) {
                if (board.getWidth()%2==0){
                    isBlack++;
                }
                for (int j = 0; j < board.getHeight(); j++,isBlack++) {
                    if(isBlack % 2 == 0) {
                        color.put(new float[]{0, 0, 0, 0});
                    } else {
                        color.put(new float[]{255, 255, 255, 255});
                    }
                }
            }
            color.flip();
            uploadData(color);
        }
    }

    public void render(Camera camera, Board board) {
        shader.bind();
        shader.loadProjectionView(camera.getProjectionViewMatrix());
//        shader.loadLightProjectionView(shadowRenderer.getShadowProjViewMatrix());
//        shader.loadLightProjectionView(camera.getProjectionViewMatrix());
        shader.loadBoardWidth(board.getWidth());
        glBindVertexArray(vao);
        glDrawArraysInstanced(GL_TRIANGLES, 0, 12, board.getWidth() * board.getHeight());
    }

    @Override
    public void dispose() {
        glDeleteBuffers(pbo);
        glDeleteBuffers(vbo);
        glDeleteVertexArrays(vao);
        this.shader.dispose();
    }
}

