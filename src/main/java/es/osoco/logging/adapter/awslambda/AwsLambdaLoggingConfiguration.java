package es.osoco.logging.adapter.awslambda;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import es.osoco.logging.config.LoggingConfiguration;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * LoggingPrefs configuration for {@link LambdaLogger}.
 */
@ToString
@EqualsAndHashCode
public class AwsLambdaLoggingConfiguration
    implements LoggingConfiguration {

    /**
     * The AWS lambda logger.
     */
    private LambdaLogger logger;

    /**
     * Creates a new AWS-Lambda logger configuration.
     * @param logger the {@link LambdaLogger}.
     */
    public AwsLambdaLoggingConfiguration(final LambdaLogger logger) {
        immutableSetLambdaLogger(logger);
    }

    /**
     * Specifies the {@link LambdaLogger}.
     * @param logger the {@link LambdaLogger} instance.
     */
    protected final void immutableSetLambdaLogger(final LambdaLogger logger) {
        this.logger = logger;
    }

    /**
     * Specifies the underlying {@link LambdaLogger}. Override me if necessary.
     * @param logger the logger.
     */
    @SuppressWarnings("unused")
    protected void setLambdaLogger(final LambdaLogger logger) {
        immutableSetLambdaLogger(logger);
    }

    /**
     * Retrieves the underlying {@link LambdaLogger}.
     * @return the logger.
     */
    protected final LambdaLogger immutableGetLambdaLogger() {
        return this.logger;
    }

    /**
     * Retrieves the underlying {@link LambdaLogger}. Override me if necessary.
     * @return the logger.
     */
    @SuppressWarnings("unused")
    protected LambdaLogger getLambdaLogger() {
        return immutableGetLambdaLogger();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRegistryKey() {
        return "aws-lambda";
    }
}
