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
package es.osoco.logging.config

import es.osoco.logging.Logging
import es.osoco.logging.LoggingFactory
import es.osoco.logging.adapter.printstream.PrintStreamLoggingAdapter
import es.osoco.logging.annotations.LoggingPreferences
import spock.lang.Specification
/**
  * Tests related to {@link LoggingConfiguration}.
  */
class LoggingConfigurationSpecification
    extends Specification {

    @LoggingPreferences(preferred='System.out')
    def "System.out allows up to 'info' level by default"() {
        setup:
        final Logging logging = LoggingFactory.instance.createLogging()

        expect:
        logging.preferred
        logging.preferred.size() == 1
        logging.preferred.first()
        logging.preferred.first().class == PrintStreamLoggingAdapter
        logging.errorEnabled
        logging.infoEnabled
        ! logging.debugEnabled
        ! logging.traceEnabled
    }

    @LoggingPreferences(preferred='System.err')
    def "System.err allows up to 'info' level by default"() {
        setup:
        final Logging logging = LoggingFactory.instance.createLogging()

        expect:
        logging.preferred
        logging.preferred.size() == 1
        logging.preferred.first()
        logging.preferred.first().class == PrintStreamLoggingAdapter
        logging.errorEnabled
        ! logging.infoEnabled
        ! logging.debugEnabled
        ! logging.trace()
    }
}
