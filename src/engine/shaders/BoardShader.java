package engine.shaders;

import engine.shaders.uniforms.UniformFloat;
import engine.shaders.uniforms.UniformMatrix;
import org.joml.Matrix4f;

public class BoardShader extends ShaderProgram {
    private UniformMatrix projectionView;
//    private UniformMatrix lightProjView;
    private UniformFloat boardWidth;

    public BoardShader() {
        super("board.vert", "board.frag");
        projectionView = new UniformMatrix("projView");
//        lightProjView = new UniformMatrix("lightProjView");
        boardWidth = new UniformFloat("boardWidth");
        super.getAllUniformLocations(projectionView, boardWidth);
    }

//    public void loadLightProjectionView(Matrix4f mat) {
//        lightProjView.loadMatrix(mat);
//    }

    public void loadProjectionView(Matrix4f mat) {
        projectionView.loadMatrix(mat);
    }

    public void loadBoardWidth(float v) {
        boardWidth.loadFloat(v);
    }
}

