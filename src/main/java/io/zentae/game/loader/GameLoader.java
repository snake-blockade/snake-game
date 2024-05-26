package io.zentae.game.loader;

import io.zentae.fx.frame.MenuFrame;
import io.zentae.game.build.DataPoet;
import io.zentae.renderer.Translator;
import io.zentae.renderer.layer.DefaultTranslationLayer;
import io.zentae.snake.engine.entity.fruit.Fruit;
import io.zentae.snake.engine.entity.obstacle.Obstacle;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class GameLoader implements Loader {

    private static final Loader FRAMES_LOADER = new MenuFrameLoader();

    @Override
    public Object[] load(Object... data) {
        // map the data.
        Map<Class<?>, String> map = new HashMap<>();
        map.put(Fruit.class, "A");
        map.put(Obstacle.class, "P");
        Translator.register(new DefaultTranslationLayer(40, map));
        // get data.
        Object[] framesData = FRAMES_LOADER.load(data);
        // load the frames.
        MenuFrame menuFrame = DataPoet.cast(framesData[0], MenuFrame.class);
        menuFrame.open();
        // return an array with the data.
        return DataPoet.build(DataPoet.cast(framesData[1], CompletableFuture.class));
    }
}
