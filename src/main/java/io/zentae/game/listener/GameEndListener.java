package io.zentae.game.listener;

import io.zentae.fx.frame.AbstractFrame;
import io.zentae.fx.frame.DeathFrame;
import io.zentae.fx.frame.GameFrame;
import io.zentae.snake.engine.controller.game.DeathType;
import io.zentae.snake.engine.controller.game.GameController;
import io.zentae.snake.engine.event.GameEndEvent;
import io.zentae.snake.engine.io.ProtocolCoordinator;
import io.zentae.snake.engine.listener.Listener;

public class GameEndListener extends Listener<GameEndEvent> {

    private final AbstractFrame frame;
    public GameEndListener(AbstractFrame frame) {
        super(GameEndEvent.class);
        this.frame = frame;
    }

    @Override
    public void onListen(GameEndEvent event) {
        // get controller.
        GameController controller = event.getGame();
        // get death reason.
        DeathType deathType = controller.getDeathType();
        // if there is no death reason, it means the player quit the game purposefully.
        if(deathType == null || deathType == DeathType.NONE) {
            // close the service from the protocols if loaded.
            if(ProtocolCoordinator.isLoaded())
                ProtocolCoordinator.get().close();
            // exit.
            System.exit(0);
            // return.
            return;
        }
        // dispose of the old frame.
        this.frame.dispose();
        // call the death frame.
        DeathFrame deathFrame = new DeathFrame(controller);
        deathFrame.open();
    }
}
