package es.osoco.logging.adapter.slf4j;

import es.osoco.logging.adapter.AbstractLoggingAdapterBuilder;
import org.checkerframework.checker.nullness.qual.NonNull;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Is able to create {@link Slf4jLoggingAdapter} instances.
 */
@EqualsAndHashCode(callSuper = true)
@ToString
public class Slf4jLoggingAdapterBuilder
    extends AbstractLoggingAdapterBuilder<Slf4jLoggingConfiguration, Slf4jLoggingAdapter> {

    /**
     * Creates a new builder for given config.
     * @param config the {@link Slf4jLoggingConfiguration}.
     */
    public Slf4jLoggingAdapterBuilder(@NonNull final Slf4jLoggingConfiguration config) {
        super(config.getRegistryKey(), config);
    }

    @Override
    @NonNull
    protected Slf4jLoggingAdapter build(@NonNull final Slf4jLoggingConfiguration config) {
        return new Slf4jLoggingAdapter(config);
    }
}
