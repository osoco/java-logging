package es.osoco.logging.adapter.awslambda;

import es.osoco.logging.adapter.LoggingAdapterBuilderRegistry;
import es.osoco.logging.config.LoggingConfiguration;
import es.osoco.logging.config.LoggingConfigurationListener;

/**
 * Gets notified when a new {@link LoggingConfiguration} is published in the
 * registry, and checks if it corresponds to AWS-Lambda. If so, creates a builder
 * so that any client can use it right away.
 */
@SuppressWarnings("unused")
public class AwsLambdaLoggingConfigurationListener
    implements LoggingConfigurationListener {

        @Override
        public void newLoggingConfigurationAvailable(final LoggingConfiguration config) {
            if (config instanceof AwsLambdaLoggingConfiguration) {
                LoggingAdapterBuilderRegistry.getInstance().put(
                    config.getRegistryKey(),
                    new AwsLambdaLoggingAdapterBuilder(
                        config.getRegistryKey(), ((AwsLambdaLoggingConfiguration) config).getLambdaLogger()));
            }
        }

}
