/*
  This program is free software: you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.

  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.

  You should have received a copy of the GNU General Public License
  along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package es.osoco.logging.impl;

import es.osoco.logging.LoggingContext;

import java.util.HashMap;
import java.util.Map;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * A {@link ThreadLocal}-based {@link LoggingContext} implementation.
 */
@ToString
@EqualsAndHashCode
public class ThreadLocalLoggingContext
    implements LoggingContext {

    /**
     * The underlying map.
     */
    private final ThreadLocal<Map<String, ?>> map = ThreadLocal.withInitial(HashMap::new);

    /**
     * Creates an empty context.
     */
    public ThreadLocalLoggingContext() {
    }

    /**
     * Retrieves the map.
     * @return such collection.
     */
    protected final Map<String, ?> immutableGetMap() {
        return this.map.get();
    }

    /**
     * Retrieves an stored value (for the current thread), or {@code null} if not found.
     * @param key the key.
     * @param <T> the value type.
     * @return the value.
     */
    @SuppressWarnings("unchecked")
    public <T> T get(final String key) {
        return (T) immutableGetMap().get(key);
    }

    /**
     * Stores a value under given key, locally to the current thread.
     * @param key the key (if {@code null}, it's not stored).
     * @param value the value (if {@code null}, it's not stored).
     * @param <T> the value type.
     */
    @SuppressWarnings("unchecked")
    public <T> void put(final String key, final T value) {
        if ((key != null) && (value != null)) {
            ((Map<String, T>) immutableGetMap()).put(key, value);
        }
    }
}
