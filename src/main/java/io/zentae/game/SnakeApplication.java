package io.zentae.game;

import io.zentae.game.loader.GameLoader;

public class SnakeApplication {

    public static void main(String[] args) {
        // if no channel id has been specified.
        if(args == null || args.length == 0)
            new GameLoader().load("ChatTest", "ChatTest");
        // if only one of the two channel ids has been specified, we assume that the writer channel's id has been told.
        if(args.length == 1)
            new GameLoader().load(args[0], "ChatTest");
        // if both of the channels id have been specified.
        if(args.length >= 2)
            new GameLoader().load(args[0], args[1]);
    }

}
