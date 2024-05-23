package io.zentae.game.build;

import jakarta.annotation.Nonnull;

import java.util.Arrays;

public interface DataPoet {

    /**
     * A method that allows to simply create an array from a varargs argument.
     * @param data the varargs data.
     * @return the varargs data into an array form.
     */
    static Object[] build(Object... data) {
        return Arrays.copyOf(data, data.length);
    }

    /**
     * Dynamically casts a data into a specified type class.
     * @param data the data to cast.
     * @param type the type the data will be cast to.
     * @return the cast data.
     */
    static <T> T cast(@Nonnull Object data, @Nonnull Class<T> type) {
        return type.cast(data);
    }

}
