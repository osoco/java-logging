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
package es.osoco.logging.preferences

import spock.lang.Specification


class LoggingPrefsSpecification extends Specification {

    def "LoggingPreferences can extract the class name of a stack trace element"() {
        setup:
        def prefs = LoggingPrefs.getInstance()
        def stackTrace = new StackTraceElement(String.class.getName(), "toString", "file", 13)
        String[] preferred = new String[1]
        preferred[0] = "abc"
        prefs.addMethodPreferred(stackTrace, preferred)

        expect:
        prefs.findPreferred(stackTrace, null) != null
        prefs.findPreferred(stackTrace, null).size() == 1
        prefs.findPreferred(stackTrace, null)[0] == "abc"
    }

}
