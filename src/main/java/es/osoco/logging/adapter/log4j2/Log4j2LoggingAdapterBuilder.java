package es.osoco.logging.adapter.log4j2;

import es.osoco.logging.adapter.AbstractLoggingAdapterBuilder;
import org.checkerframework.checker.nullness.qual.NonNull;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Is able to build {@link Log4j2LoggingAdapter}s.
 */
@EqualsAndHashCode(callSuper = true)
@ToString
public class Log4j2LoggingAdapterBuilder
    extends AbstractLoggingAdapterBuilder<Log4j2LoggingConfiguration, Log4j2LoggingAdapter> {

    protected Log4j2LoggingAdapterBuilder(@NonNull final Log4j2LoggingConfiguration config) {
        super(config.getRegistryKey(), config);
    }

    @Override
    @NonNull
    protected Log4j2LoggingAdapter build(@NonNull final Log4j2LoggingConfiguration config) {
        return new Log4j2LoggingAdapter(config);
    }
}
