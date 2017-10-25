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
