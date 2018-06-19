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

/**
 * Knows how to build specific {@link LoggingAdapter}s.
 * @param <C> the type of the associated {@link LoggingConfiguration}.
 * @param <L> the type of the {@link LoggingAdapter} this builder knows how to build.
 */
public interface LoggingAdapterBuilder<C extends LoggingConfiguration, L extends LoggingAdapter> {

    /**
     * Retrieves the registry key. Override this if necessary.
     * @return such key.
     */
    @NonNull
    String getRegistryKey();

    /**
     * Retrieves the {@link LoggingConfiguration} to use. Override this if necessary.
     * @return such instance.
     */
    @NonNull
    C getLoggingConfiguration();

    /**
     * Builds a new {@link Logging}.
     * @return such adapter.
     */
    @Nullable
    L build();
}
