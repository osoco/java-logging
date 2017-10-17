package es.osoco.logging.adapter.awslambda;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import es.osoco.logging.adapter.AbstractLoggingAdapterBuilder;
import es.osoco.logging.adapter.LoggingAdapterBuilder;

/**
 * {@link LoggingAdapterBuilder} for AWS Lambda.
 */
@SuppressWarnings("unused")
public class AwsLambdaLoggingAdapterBuilder
    extends AbstractLoggingAdapterBuilder<AwsLambdaLoggingConfiguration, AwsLambdaLoggingAdapter> {

    /**
     * Creates a new {@code AwsLambdaLoggingAdapterBuilder} for given {@link LambdaLogger}.
     * @param logger the {@link LambdaLogger}.
     */
    public AwsLambdaLoggingAdapterBuilder(final LambdaLogger logger) {
        this("aws-lambda", logger);
    }

    /**
     * Creates a new {@code AwsLambdaLoggingAdapterBuilder} for given key and {@link LambdaLogger}.
     * @param key the key.
     * @param logger the {@link LambdaLogger}.
     */
    public AwsLambdaLoggingAdapterBuilder(final String key, final LambdaLogger logger) {
        super(key, new AwsLambdaLoggingConfiguration(logger));
    }

    @Override
    public AwsLambdaLoggingAdapter build(final AwsLambdaLoggingConfiguration config) {
        return new AwsLambdaLoggingAdapter(config);
    }

}
