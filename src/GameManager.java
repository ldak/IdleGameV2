import engine.Camera;
import engine.Window;
import engine.inputs.MouseListener;
import engine.interfaces.Disposable;
import engine.renderers.MasterRenderer;
import engine.inputs.MousePicker;
import engine.util.Timer;
import game.Board;


public class GameManager implements Disposable {

    private Timer deltaTimer;
    private MousePicker mousePicker;
    private Window window;
    private MasterRenderer renderer;
    private Board board;
    private Camera camera;
    private MouseListener mouseListener;

    public GameManager() {
        this.window = new Window("Idle Game v2");
        this.renderer = new MasterRenderer();
        this.board = new Board();
        this.camera = new Camera(this.window, board);
        this.mousePicker = new MousePicker(camera);
        this.mouseListener = new MouseListener();
        this.window.registerMouseCallback(mouseListener);
        this.deltaTimer = new Timer();
    }

    public void run() {
        while(!this.window.shouldClose()) {
            float delta = deltaTimer.elapsed();
            this.window.poll();
//            if (mousePos!=null){
//                Vector2f vec = new Vector2f(  window.getCursorPosition().x - mousePos.x, window.getCursorPosition().y - mousePos.y);
//                camera.rotateView(vec, delta);
//            }
            this.mousePicker.update(window.getNDCPosition().x, window.getNDCPosition().y);

            camera.update(window, delta);

//            renderer.render(window,camera, board);

            this.window.display();

        }
    }

    @Override
    public void dispose() {
        this.window.dispose();
    }
}
