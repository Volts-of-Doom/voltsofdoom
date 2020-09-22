
package vision.voltsofdoom.gamebase.core;

/**
 * This class contains the implementation for a variable timestep game loop.
 *
 * @author Heiko Brumme
 */
public class VariableTimestepGame extends Game {

    public VariableTimestepGame(String resourceRoot) {
        super(resourceRoot);
    }

    @Override
    public void gameLoop() {
        float delta;

        while (running) {
            /* Check if game should close */
            if (window.isClosing()) {
                running = false;
            }

            /* Get delta time */
            delta = timer.getDelta();

            /* Handle input */
            String stateChange = input();
            if (stateChange != null) {
                state.change(stateChange);
            }

            /* Update game and timer UPS */
            update(delta);
            timer.updateUPS();

            /* Render game and update timer FPS */
            render();
            timer.updateFPS();

            /* Update timer */
            timer.update();

            /* Update window to show the new screen */
            window.update();

            /* Synchronize if v-sync is disabled */
            if (!window.isVSyncEnabled()) {
                sync(TARGET_FPS);
            }
        }
    }

}
