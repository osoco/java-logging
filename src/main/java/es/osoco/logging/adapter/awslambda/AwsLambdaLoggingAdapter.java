package es.osoco.logging.adapter.awslambda;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import es.osoco.logging.adapter.AbstractLoggingAdapter;
import es.osoco.logging.adapter.LoggingAdapter;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * A {@link LoggingAdapter} for AWS-Lambda.
 */
@ToString
@EqualsAndHashCode(callSuper=true)
@SuppressWarnings("unused")
public class AwsLambdaLoggingAdapter
    extends AbstractLoggingAdapter<AwsLambdaLoggingConfiguration> {

    /**
     * Creates a new {@code AwsLambdaLoggingAdapter}.
     * @param config the {@link AwsLambdaLoggingConfiguration}.
     */
    public AwsLambdaLoggingAdapter(final AwsLambdaLoggingConfiguration config) {
        super(config);
    }

    /**
     * Logs a message using the {@link LambdaLogger}.
     * @param msg the message to log.
     */
    protected void log(final String msg) {
        getLoggingConfiguration().getLambdaLogger().log(msg);
    }

    @Override
    public void logError(final String msg) {
        log(msg);
    }

    @Override
    public void logWarn(final String msg) {
        log(msg);
    }

    @Override
    public void logInfo(final String msg) {
        log(msg);
    }

    @Override
    public void logDebug(final String msg) {
        log(msg);
    }

    @Override
    public void logTrace(final String msg) {
        log(msg);
    }
}
