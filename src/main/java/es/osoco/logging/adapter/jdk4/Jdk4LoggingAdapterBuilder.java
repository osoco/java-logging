package es.osoco.logging.adapter.jdk4;

import es.osoco.logging.adapter.AbstractLoggingAdapterBuilder;
import org.checkerframework.checker.nullness.qual.NonNull;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Builds {@link Jdk4LoggingAdapter} instances.
 */
@EqualsAndHashCode(callSuper = true)
@ToString
public class Jdk4LoggingAdapterBuilder
    extends AbstractLoggingAdapterBuilder<Jdk4LoggingConfiguration, Jdk4LoggingAdapter> {

    public Jdk4LoggingAdapterBuilder() {
        this(new Jdk4LoggingConfiguration());
    }

    public Jdk4LoggingAdapterBuilder(@NonNull final Jdk4LoggingConfiguration config) {
        super("java.util.logging", config);
    }

    @Override
    protected Jdk4LoggingAdapter build(@NonNull final Jdk4LoggingConfiguration config) {
        return new Jdk4LoggingAdapter(config);
    }
}
