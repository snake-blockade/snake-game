package io.zentae.game.listener;

import io.zentae.fx.frame.AbstractFrame;
import io.zentae.snake.engine.event.snake.SnakeMoveEvent;
import io.zentae.snake.engine.listener.Listener;

public class SnakeMoveListener extends Listener<SnakeMoveEvent> {

    private final AbstractFrame frame;
    public SnakeMoveListener(AbstractFrame frame) {
        super(SnakeMoveEvent.class);
        this.frame = frame;
    }

    @Override
    public void onListen(SnakeMoveEvent event) {
        this.frame.repaint();
    }
}
