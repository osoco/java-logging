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

import com.amazonaws.services.lambda.runtime.LambdaLogger
import es.osoco.logging.adapter.awslambda.AwsLambdaLoggingConfigurationProducer
import spock.lang.Specification

class LoggingConfigurationProducerSpecification
        extends Specification {

    def "LambdaLoggingProducer triggers a Lambda-based LoggingAdapterBuilder gets registered"() {
        setup:
        LambdaLogger logger = Mock()
        def producer = new AwsLambdaLoggingConfigurationProducer()
        producer.configureLogging("my-key", logger)

        expect:
        LoggingConfigurationRegistry.getInstance().get("my-key") != null
    }
}
