package es.osoco.logging.adapter.jul;

import es.osoco.logging.adapter.AbstractLoggingAdapterBuilder;
import org.checkerframework.checker.nullness.qual.NonNull;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Builds {@link JulLoggingAdapter} instances.
 */
@EqualsAndHashCode(callSuper = true)
@ToString
public class JulLoggingAdapterBuilder
    extends AbstractLoggingAdapterBuilder<JulLoggingConfiguration, JulLoggingAdapter> {

    public JulLoggingAdapterBuilder() {
        this(new JulLoggingConfiguration());
    }

    public JulLoggingAdapterBuilder(@NonNull final JulLoggingConfiguration config) {
        super(config.getRegistryKey(), config);
    }

    @Override
    protected JulLoggingAdapter build(@NonNull final JulLoggingConfiguration config) {
        return new JulLoggingAdapter(config);
    }
}
