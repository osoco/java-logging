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

/**
 * Interface for all {@link Logging} adapters.
 * @param <LC> the type of {@link LoggingConfiguration} this adapter is associated to.
 */
public interface LoggingAdapter<LC extends LoggingConfiguration>
    extends Logging {
    /**
     * Retrieves the {@link LoggingConfiguration}.
     * @return such configuration.
     */
    LC getLoggingConfiguration();
}
