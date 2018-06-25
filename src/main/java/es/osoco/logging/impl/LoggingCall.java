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

import es.osoco.logging.adapter.LoggingAdapter;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Function implementations to delegate calls to given adapters.
 */
public interface LoggingCall {
    /**
     * Delegates the logging operation on given adapter.
     * @param adapter the adapter.
     * @param msg the message to log.
     */
    void log(@NonNull LoggingAdapter adapter, @NonNull String msg);

    /**
     * Delegates the logging operation on given adapter.
     * @param adapter the adapter.
     * @param msg the message to log.
     * @param error the error.
     */
    void log(@NonNull LoggingAdapter adapter, @NonNull String msg, @NonNull Throwable error);

    /**
     * Delegates the logging operation on given adapter for given category.
     * @param adapter the adapter.
     * @param category the category.
     * @param msg the message to log.
     */
    void log(@NonNull LoggingAdapter adapter, @NonNull String category, @NonNull String msg);

    /**
     * Delegates the logging operation on given adapter for given category.
     * @param adapter the adapter.
     * @param category the category.
     * @param msg the message to log.
     * @param error the error.
     */
    void log(@NonNull LoggingAdapter adapter, @NonNull String category, @NonNull String msg, @NonNull Throwable error);

    /**
     * Delegates given adapter the responsibility to check whether a given log level is enabled.
     * @param adapter the adapter.
     * @return {@code true} if it's enabled, {@code false} otherwise.
     */
    boolean isEnabled(@NonNull LoggingAdapter adapter);

    /**
     * Delegates given adapter the responsibility to check whether a given log level is enabled, for given category.
     * @param adapter the adapter.
     * @param category the category.
     * @return {@code true} if it's enabled, {@code false} otherwise.
     */
    boolean isEnabled(@NonNull LoggingAdapter adapter, @NonNull String category);
}
