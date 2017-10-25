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
 * Listens to notifications of new {@link LoggingConfiguration} availability.
 */
@FunctionalInterface
public interface LoggingConfigurationListener {
    /**
     * Gets notified of a new {@link LoggingConfiguration}.
     * @param config the new configuration.
     */
    void newLoggingConfigurationAvailable(LoggingConfiguration config);
}
