package io.zentae.game.loader;

import io.zentae.fx.cache.ImageCache;
import io.zentae.fx.frame.GameFrame;
import io.zentae.fx.frame.MenuFrame;
import io.zentae.fx.listener.PlayerButtonListener;
import io.zentae.fx.panel.AbstractPanel;
import io.zentae.game.build.DataPoet;
import io.zentae.game.listener.GameEndListener;
import io.zentae.game.listener.SnakeMoveListener;
import io.zentae.snake.engine.GameEngine;
import io.zentae.snake.engine.controller.game.GameController;
import io.zentae.snake.engine.event.EventBus;
import io.zentae.snake.engine.event.GameEndEvent;
import io.zentae.snake.engine.event.snake.SnakeMoveEvent;
import io.zentae.snake.engine.handler.GameType;
import io.zentae.snake.engine.io.Channel;
import io.zentae.snake.engine.io.ProtocolCoordinator;

import javax.swing.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class MenuFrameLoader implements Loader {

    private static final Loader ENGINE_LOADER = new EngineLoader();

    private final BiConsumer<GameType, Boolean> actionConsumer = (gameType, playsFirst) -> {
        // get the game controller.
        GameController gameController = DataPoet.cast(ENGINE_LOADER.load(gameType)[0], GameController.class);
        // create a new image cache.
        ImageCache imageCache = new ImageCache();
        // build & open game frame to the user.
        GameFrame gameFrame = new GameFrame(gameController, imageCache);
        gameFrame.open();
        // register listeners.
        EventBus.subscribe(SnakeMoveEvent.class, new SnakeMoveListener(gameFrame));
        EventBus.subscribe(GameEndEvent.class, new GameEndListener(gameFrame));
        // load & start engine.
        GameEngine.loadEngine(gameType, gameController);
        GameEngine.start(gameType, playsFirst);
    };

    @Override
    public Object[] load(Object... data) {
        // get menu frame.
        MenuFrame menuFrame = new MenuFrame();
        // get the frame's panel.
        AbstractPanel panel = menuFrame.getPanel();
        // initialize GameType.
        CompletableFuture<GameType> completableFuture = new CompletableFuture<>();
        // add the listeners for every button.
        panel.getComponent("player-1-ai", JButton.class).addActionListener(new PlayerButtonListener(menuFrame, event -> {
            // load the engine.
            this.actionConsumer.accept(GameType.LOCAL_AI, true);
            // set the atomic reference.
            completableFuture.complete(GameType.LOCAL_AI);
            // register move event for fruits implementation.
            EventBus.subscribe(SnakeMoveEvent.class, new io.zentae.snake.engine.listener.SnakeMoveListener());
        }));
        panel.getComponent("player-2-ai", JButton.class).addActionListener(new PlayerButtonListener(menuFrame, event -> {
            // load the engine.
            this.actionConsumer.accept(GameType.LOCAL_AI, false);
            // set the atomic reference.
            completableFuture.complete(GameType.LOCAL_AI);
            // register move event for fruits implementation.
            EventBus.subscribe(SnakeMoveEvent.class, new io.zentae.snake.engine.listener.SnakeMoveListener());
        }));
        panel.getComponent("player-1-lan", JButton.class).addActionListener(new PlayerButtonListener(menuFrame, event -> {
            // get data.
            //Object[] channelsData = new ChannelsLoader().load(data[0], data[1]);
            Object[] channelsData = new ChannelsLoader().load("ChatTest", "ChatTest");
            // load the channels.
            Channel<String> writerChannel = DataPoet.cast(channelsData[0], Channel.class);
            Channel<String> readerChannel = DataPoet.cast(channelsData[1], Channel.class);
            // load protocol coordinator.
            ProtocolCoordinator.loadProtocols(writerChannel, readerChannel);
            System.out.println("[-] Successfully loaded the protocols ! Playing in LAN is now possible.");
            // load the engine.
            this.actionConsumer.accept(GameType.LAN, true);
            // set the atomic reference.
            completableFuture.complete(GameType.LAN);
        }));
        panel.getComponent("player-2-lan", JButton.class).addActionListener(new PlayerButtonListener(menuFrame, event -> {
            // get data.
            Object[] channelsData = new ChannelsLoader().load("ChatTest", "ChatTest");
            // load the channels.
            Channel<String> writerChannel = DataPoet.cast(channelsData[0], Channel.class);
            Channel<String> readerChannel = DataPoet.cast(channelsData[1], Channel.class);
            // load protocol coordinator.
            ProtocolCoordinator.loadProtocols(writerChannel, readerChannel);
            System.out.println("[-] Successfully loaded the protocols ! Playing in LAN is now possible.");
            // load the engine.
            this.actionConsumer.accept(GameType.LAN, false);
            // set the atomic reference.
            completableFuture.complete(GameType.LAN);
        }));
        panel.getComponent("opponent", JButton.class).addActionListener(new PlayerButtonListener(menuFrame, event -> {
            // load the engine.
            this.actionConsumer.accept(GameType.LOCAL_1V1, true);
            // set the atomic reference.
            completableFuture.complete(GameType.LOCAL_1V1);
            // register move event for fruits implementation.
            EventBus.subscribe(SnakeMoveEvent.class, new io.zentae.snake.engine.listener.SnakeMoveListener());
        }));
        // return the data.
        return DataPoet.build(menuFrame, completableFuture);
    }
}
