package es.osoco.logging;

/**
 * Used to add additional information to the underlying logging mechanism.
 */
public interface LoggingContext {

    /**
     * Retrieves an stored value (for the current thread), or {@code null} if not found.
     * @param key the key.
     * @param <T> the value type.
     * @return the value.
     */
    <T> T get(final String key);

    /**
     * Stores a value under given key, locally to the current thread.
     * @param key the key (if {@code null}, it's not stored).
     * @param value the value (if {@code null}, it's not stored).
     * @param <T> the value type.
     */
    <T> void put(final String key, final T value);
}
