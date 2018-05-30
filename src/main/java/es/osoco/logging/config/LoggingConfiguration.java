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
package es.osoco.logging.config;

/**
 * Models logging configurations.
 */
public interface LoggingConfiguration {
    /**
     * Retrieves the registry key.
     * @return such information.
     */
    String getRegistryKey();

    /**
     * Returns whether the "error" level is enabled by default.
     * @return such behavior.
     */
    default boolean isErrorEnabledByDefault() {
        return true;
    }

    /**
     * Returns whether the "warn" level is enabled by default.
     * @return such behavior.
     */
    default boolean isWarnEnabledByDefault() {
        return true;
    }

    /**
     * Returns whether the "info" level is enabled by default.
     * @return such behavior.
     */
    default boolean isInfoEnabledByDefault() {
        return true;
    }

    /**
     * Returns whether the "debug" level is enabled by default.
     * @return such behavior.
     */
    default boolean isDebugEnabledByDefault() {
        return false;
    }

    /**
     * Returns whether the "trace" level is enabled by default.
     * @return such behavior.
     */
    default boolean isTraceEnabledByDefault() {
        return false;
    }
}
