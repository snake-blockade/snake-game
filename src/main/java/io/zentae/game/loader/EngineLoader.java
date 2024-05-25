package io.zentae.game.loader;

import io.zentae.game.build.DataPoet;
import io.zentae.snake.engine.controller.game.DefaultGameController;
import io.zentae.snake.engine.controller.game.GameController;
import io.zentae.snake.engine.controller.snake.DefaultSnakeController;
import io.zentae.snake.engine.entity.Location;
import io.zentae.snake.engine.entity.arena.Arena;
import io.zentae.snake.engine.entity.game.DefaultGame;
import io.zentae.snake.engine.entity.game.Game;
import io.zentae.snake.engine.entity.player.DefaultPlayer;
import io.zentae.snake.engine.entity.player.Player;
import io.zentae.snake.engine.entity.snake.Snake;
import io.zentae.snake.engine.event.CollisionEvent;
import io.zentae.snake.engine.event.EventBus;
import io.zentae.snake.engine.event.snake.SnakeGrowEvent;
import io.zentae.snake.engine.factory.arena.DefaultArenaFactory;
import io.zentae.snake.engine.factory.arena.LinearArenaFactory;
import io.zentae.snake.engine.factory.handler.DefaultHandlerFactory;
import io.zentae.snake.engine.factory.handler.HandlerFactory;
import io.zentae.snake.engine.factory.snake.DefaultSnakeFactory;
import io.zentae.snake.engine.factory.snake.SnakeFactory;
import io.zentae.snake.engine.handler.MovementHandler;
import io.zentae.snake.engine.handler.GameType;
import io.zentae.snake.engine.listener.CollisionListener;
import io.zentae.snake.engine.listener.SnakeGrowListener;

import java.util.ArrayList;
import java.util.List;

public class EngineLoader implements Loader {

    private static final SnakeFactory SNAKE_FACTORY = new DefaultSnakeFactory();
    private static final HandlerFactory HANDLER_FACTORY = new DefaultHandlerFactory();

    @Override
    public Object[] load(Object... data) {
        // check if the given data is correct.
        if(data.length == 0 || !(data[0] instanceof GameType gameType))
            throw new UnsupportedOperationException("The given data to the engine loader is incorrect or is missing !");
        // register the default listeners.
        EventBus.subscribe(SnakeGrowEvent.class, new SnakeGrowListener());
        EventBus.subscribe(CollisionEvent.class, new CollisionListener());
        // build the snakes.
        List<Snake> snakes = new ArrayList<>();
        snakes.add(SNAKE_FACTORY.create(new Location(2, 2)));
        snakes.add(SNAKE_FACTORY.create(new Location(9, 19)));
        // build the arena : if LAN, launches a linear arena, else a random arena.
        Arena arena = gameType == GameType.LAN
                    ? new LinearArenaFactory().create(snakes, 12, 22)
                    : new DefaultArenaFactory().create(snakes, 12, 22);
        // initialize a new players list.
        List<Player> players = new ArrayList<>();
        // initialize the game handler.
        MovementHandler movementHandler = HANDLER_FACTORY.create(gameType);
        // build the game.
        Game game = new DefaultGame(movementHandler, arena, players, 4);
        // loop through each snake and build a player.
        for(Snake snake : snakes)
            players.add(new DefaultPlayer(new DefaultSnakeController(snake)));
        // initialize the game controller.
        GameController gameController = new DefaultGameController(game);
        // return the data.
        return DataPoet.build(gameController);
    }
}
