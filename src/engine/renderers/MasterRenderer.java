package engine.renderers;

import engine.Camera;
import engine.Window;
import engine.interfaces.Disposable;
import game.Board;

import static org.lwjgl.opengl.GL11.*;

public class MasterRenderer implements Disposable {
    private final BoardRenderer boardRenderer;
//    private final PieceRenderer pieceRenderer;
//    private final ShadowRenderer shadowRenderer;

    public MasterRenderer() {
        this.boardRenderer = new BoardRenderer();
//        this.pieceRenderer = new PieceRenderer();
//        this.shadowRenderer = new ShadowRenderer();

        glEnable(GL_CULL_FACE); // dont render back faces
        glEnable(GL_DEPTH_TEST); // dont render faces behind other faces
    }

    private void shadowPass() {

    }

    private void colorPass(Camera camera, Board board) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        this.boardRenderer.setBoard(board);
        this.boardRenderer.render(camera, board);
//        this.pieceRenderer.render(board, camera, cup);
    }

    public void render(Window window, Camera camera, Board board) {
        // Render shadow map
//        this.shadowRenderer.startPass();
//        this.pieceRenderer.shadowPass(shadowRenderer, board, cup);
//        this.shadowRenderer.endPass(window);

        // Render color
        colorPass(camera, board);
    }

    @Override
    public void dispose() {
        boardRenderer.dispose();
//        pieceRenderer.dispose();
//        shadowRenderer.dispose();
    }

    public void renderWon(String winner) {
    }
}
