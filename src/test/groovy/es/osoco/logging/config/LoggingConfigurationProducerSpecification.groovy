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
