package io.zentae.game.loader;

public interface Loader {

    /**
     * Loads a specific entity using - if needed - the given data parameter.
     * @param data the potential data that can be used to load the entity.
     * @return some data that can be used.
     */
    Object[] load(Object... data);

}
