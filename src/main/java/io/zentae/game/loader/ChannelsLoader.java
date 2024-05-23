package io.zentae.game.loader;

import io.zentae.game.build.DataPoet;
import io.zentae.snake.engine.io.Channel;

public class ChannelsLoader implements Loader {

    @Override
    public Object[] load(Object... data) {
        // if insufficient arguments, return nothing.
        if(data.length < 2)
            return DataPoet.build();
        // get first two elements.
        String writerChannelIdentifier = DataPoet.cast(data[0], String.class);
        String readerChannelIdentifier = DataPoet.cast(data[0], String.class);
        // build channels.
        Channel<String> writerChannel = new Channel<>(writerChannelIdentifier);
        Channel<String> readerChannel = new Channel<>(readerChannelIdentifier);
        // return the two channels.
        return DataPoet.build(writerChannel, readerChannel);
    }
}
