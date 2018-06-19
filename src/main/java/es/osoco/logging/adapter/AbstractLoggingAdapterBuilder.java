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

import es.osoco.logging.Logging;
import es.osoco.logging.config.LoggingConfiguration;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Base class for {@link Logging logging} {@link LoggingAdapter adapter} builders.
 * @param <C> the type of the {@link LoggingConfiguration}.
 * @param <L> the type of the {@link LoggingAdapter}.
 */
@ToString
@EqualsAndHashCode
public abstract class AbstractLoggingAdapterBuilder<C extends LoggingConfiguration, L extends LoggingAdapter>
    implements LoggingAdapterBuilder<C, L> {
    /**
     * The registry key.
     */
    private String registryKey;

    /**
     * The logging configuration.
     */
    private C loggingConfiguration;

    /**
     * Creates a new {@code AbstractLoggingAdapterBuilder}.
     * @param regKey the registry key.
     * @param config the {@link LoggingConfiguration}.
     */
    protected AbstractLoggingAdapterBuilder(@NonNull final String regKey, @NonNull final C config) {
        this.registryKey = regKey;
        this.loggingConfiguration = config;
    }

    /**
     * Specifies the registry key.
     * @param key the key.
     */
    protected final void immutableSetRegistryKey(@NonNull final String key) {
        this.registryKey = key;
    }

    /**
     * Specifies the registry key. Override this if necessary.
     * @param key the key.
     */
    @SuppressWarnings("unused")
    protected void setRegistryKey(@NonNull final String key) {
        immutableSetRegistryKey(key);
    }

    /**
     * Retrieves the registry key.
     * @return such key.
     */
    @NonNull
    protected final String immutableGetRegistryKey() {
        return registryKey;
    }

    /**
     * Retrieves the registry key. Override this if necessary.
     * @return such key.
     */
    @Override
    @NonNull
    public String getRegistryKey() {
        return immutableGetRegistryKey();
    }

    /**
     * Specifies the {@link LoggingConfiguration} to use.
     * @param config such instance.
     */
    protected final void immutableSetLoggingConfiguration(@NonNull final C config) {
        this.loggingConfiguration = config;
    }

    /**
     * Specifies the {@link LoggingConfiguration} to use.
     * @param config such instance.
     */
    @SuppressWarnings("unused")
    protected void setLoggingConfiguration(@NonNull final C config) {
        immutableSetLoggingConfiguration(config);
    }

    /**
     * Retrieves the {@link LoggingConfiguration} to use.
     * @return such instance.
     */
    @NonNull
    protected final C immutableGetLoggingConfiguration() {
        return this.loggingConfiguration;
    }

    /**
     * Retrieves the {@link LoggingConfiguration} to use. Override this if necessary.
     * @return such instance.
     */
    @Override
    public C getLoggingConfiguration() {
        return immutableGetLoggingConfiguration();
    }

    /**
     * Builds a new {@link Logging}.
     * @return such adapter.
     */
    @Override
    @Nullable
    public L build() {
        return this.build(getLoggingConfiguration());
    }

    /**
     * Builds a new {@link Logging}.
     * @param config the {@link LoggingConfiguration} to use.
     * @return such adapter.
     */
    @Nullable
    protected abstract L build(final C config);
}
