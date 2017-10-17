package es.osoco.logging.adapter.awslambda;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import es.osoco.logging.config.LoggingConfiguration;
import es.osoco.logging.config.LoggingConfigurationRegistry;

/**
 * Produces AWS Lambda-based {@link LoggingConfiguration}s.
 */
@SuppressWarnings("unused")
public class AwsLambdaLoggingConfigurationProducer {

    /**
     * Creates an empty instance.
     */
    public AwsLambdaLoggingConfigurationProducer() {}

    /**
     * Configures the logging to optionally use given {@link LambdaLogger}.
     * @param logger the logger.
     */
    @SuppressWarnings("unused")
    public void configureLogging(final LambdaLogger logger) {
        configureLogging("aws-lambda", logger);
    }

    /**
     * Configures the logging to optionally use given {@link LambdaLogger}, using a custom key.
     * @param key the key.
     * @param logger the logger.
     */
    @SuppressWarnings("unused")
    public void configureLogging(final String key, final LambdaLogger logger) {
        LoggingConfigurationRegistry.getInstance().put(key, new AwsLambdaLoggingConfiguration(logger));
    }
}
