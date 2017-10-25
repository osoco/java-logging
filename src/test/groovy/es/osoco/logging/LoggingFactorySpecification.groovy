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
package es.osoco.logging

import com.amazonaws.services.lambda.runtime.LambdaLogger
import es.osoco.logging.adapter.awslambda.AwsLambdaLoggingAdapter
import es.osoco.logging.adapter.awslambda.AwsLambdaLoggingConfigurationProducer
import es.osoco.logging.adapter.printstream.PrintStreamLoggingAdapter
import es.osoco.logging.annotations.LoggingPreferences
import es.osoco.logging.impl.CompositeLogging
import spock.lang.Specification

class LoggingFactorySpecification
    extends Specification {

    def "createLogging() uses System.err if the caller prefers it regardless of the availability of the AWS Lambda adapter"() {
        setup:
        LambdaLogger logger = Mock()
        def producer = new AwsLambdaLoggingConfigurationProducer()
        producer.configureLogging("aws-lambda", logger)
        AwsLambdaOtherwiseElasticSearchOrLogStashCaller caller = new AwsLambdaOtherwiseElasticSearchOrLogStashCaller()
        caller.iPreferSystemErr()

        expect:
        caller.logging != null
        caller.logging instanceof CompositeLogging
        caller.logging.getPreferred() != null
        caller.logging.getPreferred().size() == 1
        caller.logging.getPreferred().get(0) instanceof PrintStreamLoggingAdapter
        caller.logging.getPreferred().get(0).getLoggingConfiguration().getPrintStream() == System.err

    }

    def "createLogging() uses System.out if the caller prefers it regardless of the availability of the AWS Lambda adapter"() {
        setup:
        LambdaLogger logger = Mock()
        def producer = new AwsLambdaLoggingConfigurationProducer()
        producer.configureLogging("aws-lambda", logger)
        AwsLambdaOtherwiseElasticSearchOrLogStashCaller caller = new AwsLambdaOtherwiseElasticSearchOrLogStashCaller()
        caller.iPreferSystemOut()

        expect:
        caller.logging != null
        caller.logging instanceof CompositeLogging
        caller.logging.getPreferred() != null
        caller.logging.getPreferred().size() == 1
        caller.logging.getPreferred().get(0) instanceof PrintStreamLoggingAdapter
        caller.logging.getPreferred().get(0).getLoggingConfiguration().getPrintStream() == System.out

    }

    def "createLogging() uses both System.err and System.out if the caller prefers it regardless of the availability of the AWS Lambda adapter"() {
        setup:
        LambdaLogger logger = Mock()
        def producer = new AwsLambdaLoggingConfigurationProducer()
        producer.configureLogging("aws-lambda", logger)
        AwsLambdaOtherwiseElasticSearchOrLogStashCaller caller = new AwsLambdaOtherwiseElasticSearchOrLogStashCaller()
        caller.iPreferSystemErrAndSystemOut()

        expect:
        caller.logging != null
        caller.logging instanceof CompositeLogging
        caller.logging.getPreferred() != null
        caller.logging.getPreferred().size() == 2
        caller.logging.getPreferred().get(0) instanceof PrintStreamLoggingAdapter
        caller.logging.getPreferred().get(0).getLoggingConfiguration().getPrintStream() == System.err
        caller.logging.getPreferred().get(1) instanceof PrintStreamLoggingAdapter
        caller.logging.getPreferred().get(1).getLoggingConfiguration().getPrintStream() == System.out
    }

    def "createLogging() uses AWS-Lambda if it's configured as fallback and the preferred ElasticSearch adapter is not available"() {
        setup:
        LambdaLogger logger = Mock()
        def producer = new AwsLambdaLoggingConfigurationProducer()
        producer.configureLogging("aws-lambda", logger)
        AwsLambdaOtherwiseElasticSearchOrLogStashCaller caller = new AwsLambdaOtherwiseElasticSearchOrLogStashCaller()
        caller.iPreferElasticSearchWithAwsLambdaAsFallback()

        expect:
        caller.logging != null
        caller.logging instanceof CompositeLogging
        caller.logging.getPreferred() != null
        caller.logging.getPreferred().size() == 0
        caller.logging.getFallback() != null
        caller.logging.getFallback().size() == 1
        caller.logging.getFallback().get(0) instanceof AwsLambdaLoggingAdapter
    }

    def "createLogging() uses AWS-Lambda as preferred with no fallback since ElasticSearch adapter is not available"() {
        setup:
        LambdaLogger logger = Mock()
        def producer = new AwsLambdaLoggingConfigurationProducer()
        producer.configureLogging("aws-lambda", logger)
        AwsLambdaOtherwiseElasticSearchOrLogStashCaller caller = new AwsLambdaOtherwiseElasticSearchOrLogStashCaller()
        caller.callWithClassPreferences()

        expect:
        caller.logging != null
        caller.logging instanceof CompositeLogging
        caller.logging.getPreferred() != null
        caller.logging.getPreferred().size() == 1
        caller.logging.getPreferred().get(0) instanceof AwsLambdaLoggingAdapter
        caller.logging.getFallback() != null
        caller.logging.getFallback().size() == 0
    }

    @LoggingPreferences(preferred="aws-lambda", fallback=["ElasticSearch", "LogStash"])
    class AwsLambdaOtherwiseElasticSearchOrLogStashCaller {
        public Logging logging

        @LoggingPreferences(preferred="System.err")
        void iPreferSystemErr() {
            logging = LoggingFactory.getInstance().createLogging()
        }

        @LoggingPreferences(preferred="System.out")
        void iPreferSystemOut() {
            logging = LoggingFactory.getInstance().createLogging()
        }

        @LoggingPreferences(preferred=[ "System.err","System.out" ])
        void iPreferSystemErrAndSystemOut() {
            logging = LoggingFactory.getInstance().createLogging()
        }

        @LoggingPreferences(preferred="ElasticSearch", fallback="aws-lambda")
        void iPreferElasticSearchWithAwsLambdaAsFallback() {
            logging = LoggingFactory.getInstance().createLogging()
        }

        void callWithClassPreferences() {
            logging = LoggingFactory.getInstance().createLogging()
        }
    }
}
