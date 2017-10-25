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
package es.osoco.logging.adapter;

import es.osoco.logging.config.LoggingConfiguration;

import java.util.HashMap;
import java.util.Map;

/**
 * Registry for {@link LoggingAdapterBuilder}s.
 */
@SuppressWarnings("unused")
public class LoggingAdapterBuilderRegistry {
    /**
     * The underlying logging configuration map.
     */
    private Map<String, ? extends LoggingAdapterBuilder<?, ?>> map = new HashMap<>();

    /**
     * Singleton implementation to avoid double-check locking.
     */
    protected static final class LoggingAdapterBuilderRegistrySingletonContainer {
        public static final LoggingAdapterBuilderRegistry SINGLETON = new LoggingAdapterBuilderRegistry();
    }

    /**
     * Default constructor to avoid public instantiation.
     */
    protected LoggingAdapterBuilderRegistry() {
    }

    /**
     * Adds a new adapter builder.
     * @param builder the builder.
     */
    protected void registerBuilder(final LoggingAdapterBuilder<?, ?> builder) {
        immutableGetMap().put(builder.getRegistryKey(), builder);
    }

    /**
     * Retrieves the {@code LoggingAdapterBuilderRegistry}.
     * @return such instance.
     */
    public static LoggingAdapterBuilderRegistry getInstance() {
        return LoggingAdapterBuilderRegistrySingletonContainer.SINGLETON;
    }

    /**
     * Retrieves the underlying map.
     * @param <T> the type of the values.
     * @return such map.
     */
    @SuppressWarnings("unchecked")
    protected final <T extends LoggingAdapterBuilder<?, ?>> Map<String, T> immutableGetMap() {
        return (Map<String, T>) this.map;
    }

    /**
     * Retrieves the {@link LoggingAdapterBuilder} for given key.
     * @param key the key.
     * @param <T> the type of the values.
     * @return the associated configuration.
     */
    @SuppressWarnings("unchecked")
    public <T extends LoggingAdapterBuilder> T get(final String key) {
        return (T) immutableGetMap().get(key);
    }

    /**
     * Specifies a new {@link LoggingConfiguration}.
     * @param key the key.
     * @param builder the builder.
     * @param <T> the type of the builder.
     */
    @SuppressWarnings("unchecked")
    public <T extends LoggingAdapterBuilder> void put(final String key, final T builder) {
        immutableGetMap().put(key, builder);
    }
}
