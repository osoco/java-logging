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
package es.osoco.logging.annotations

import es.osoco.logging.preferences.LoggingPrefs
import spock.lang.Specification

class LoggingPreferencesSpecification extends Specification {

    def "A method annotated with @Logging uses System.err preferred adapter"() {
        setup:
        def caller = new TestableCaller()
        caller.callWithSystemErr()

        expect:
        caller.preferredLogging != null
        caller.preferredLogging.size() == 1
        caller.preferredLogging[0] == "System.err"
    }

    def "A method annotated with @Logging uses System.out preferred adapter"() {
        setup:
        def caller = new TestableCaller()
        caller.callWithSystemOut()

        expect:
        caller.preferredLogging != null
        caller.preferredLogging.size() == 1
        caller.preferredLogging[0] == "System.out"
    }

    def "A method annotated with @Logging uses both System.err and System.out as preferred adapters"() {
        setup:
        def caller = new TestableCaller()
        caller.callWithSystemErrAndSystemOut()

        expect:
        caller.preferredLogging != null
        caller.preferredLogging.size() == 2
        caller.preferredLogging[0] == "System.err"
        caller.preferredLogging[1] == "System.out"
    }

    def "A method annotated with @Logging prefers ElasticSearch and uses System.err as fallback"() {
        setup:
        def caller = new TestableCaller()
        caller.callWithElasticSearchAndAwsLambdaAsFallback()

        expect:
        caller.preferredLogging != null
        caller.preferredLogging.size() == 1
        caller.preferredLogging[0] == "ElasticSearch"
        caller.fallback != null
        caller.fallback.size() == 1
        caller.fallback[0] == "aws-lambda"
    }


    def "A class annotated with @Logging prefers AWS-Lambda and uses ElasticSearch and LogStash as fallbacks"() {
        setup:
        def caller = new TestableCaller()
        caller.callWithClassPreferences()

        expect:
        caller.preferredLogging != null
        caller.preferredLogging.size() == 1
        caller.preferredLogging[0] == "aws-lambda"
        caller.fallback != null
        caller.fallback.size() == 2
        caller.fallback[0] == "ElasticSearch"
        caller.fallback[1] == "LogStash"
    }

    @LoggingPreferences(preferred="aws-lambda", fallback=["ElasticSearch", "LogStash"])
    class TestableCaller {
        public String[] preferredLogging
        public String[] fallback

        @LoggingPreferences(preferred="System.err")
        void callWithSystemErr() {
            preferredLogging = LoggingPrefs.getInstance().myPreferredLogging()
        }

        @LoggingPreferences(preferred="System.out")
        void callWithSystemOut() {
            preferredLogging = LoggingPrefs.getInstance().myPreferredLogging()
        }

        @LoggingPreferences(preferred=[ "System.err","System.out" ])
        void callWithSystemErrAndSystemOut() {
            preferredLogging = LoggingPrefs.getInstance().myPreferredLogging()
        }

        @LoggingPreferences(preferred="ElasticSearch", fallback="aws-lambda")
        void callWithElasticSearchAndAwsLambdaAsFallback() {
            preferredLogging = LoggingPrefs.getInstance().myPreferredLogging()
            fallback = LoggingPrefs.getInstance().myFallbackLogging()
        }

        void callWithClassPreferences() {
            preferredLogging = LoggingPrefs.getInstance().myPreferredLogging()
            fallback = LoggingPrefs.getInstance().myFallbackLogging()
        }
    }
}
