package io.zentae.game.listener;

import io.zentae.fx.frame.AbstractFrame;
import io.zentae.snake.engine.event.GameEndEvent;
import io.zentae.snake.engine.listener.Listener;

public class GameEndListener extends Listener<GameEndEvent> {

    private final AbstractFrame frame;
    public GameEndListener(AbstractFrame frame) {
        super(GameEndEvent.class);
        this.frame = frame;
    }

    @Override
    public void onListen(GameEndEvent event) {
        // close the program.
        System.exit(0);
    }
}
