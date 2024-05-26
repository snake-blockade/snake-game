package io.zentae.game;

import io.zentae.game.loader.GameLoader;

public class SnakeApplication {

    public static void main(String[] args) {
        new GameLoader().load(args[0], args[1]);
    }

}
