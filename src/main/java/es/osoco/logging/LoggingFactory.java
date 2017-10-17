package es.osoco.logging;

import es.osoco.logging.adapter.LoggingAdapter;
import es.osoco.logging.adapter.LoggingAdapterBuilder;
import es.osoco.logging.adapter.LoggingAdapterBuilderRegistry;
import es.osoco.logging.impl.CompositeLogging;
import es.osoco.logging.preferences.LoggingPrefs;

import java.util.ArrayList;
import java.util.List;

/**
 * Knows how to provide {@link Logging} instances. Use this to create them.
 */
public class LoggingFactory {

    /**
     * Singleton container to avoid double-check locking.
     */
    protected static class LoggingFactorySingletonContainer {
        /**
         * The singleton instance.
         */
        protected static final LoggingFactory SINGLETON = new LoggingFactory();
    }

    /**
     * Retrieves the <code>LoggingFactory</code> instance.
     * @return such instance.
     */
    public static LoggingFactory getInstance() {
        return LoggingFactorySingletonContainer.SINGLETON;
    }

    /**
     * Creates the {@link Logging} instance.
     * @return such instance.
     */
    public Logging createLogging() {
        return createLogging(LoggingPrefs.getInstance(), LoggingAdapterBuilderRegistry.getInstance());
    }

    /**
     * Creates the {@link Logging} instance.
     * @param prefs the {@link LoggingPrefs} instance.
     * @param registry the {@link LoggingAdapterBuilderRegistry} instance.
     * @return such instance.
     */
    protected Logging createLogging(final LoggingPrefs prefs, final LoggingAdapterBuilderRegistry registry) {
        final String[] preferred = prefs.myPreferredLogging();
        final String[] fallback = prefs.myFallbackLogging();
        final List<LoggingAdapterBuilder<?, ?>> preferredBuilders = toBuilders(preferred, registry);
        final List<LoggingAdapterBuilder<?, ?>> fallbackBuilders = toBuilders(fallback, registry);

        return new CompositeLogging(toAdapters(preferredBuilders), toAdapters(fallbackBuilders));
    }

    /**
     * Converts given keys to {@link LoggingAdapterBuilder}s.
     * @param keys the keys.
     * @param registry the builder registry.
     * @return the builder instances.
     */
    protected List<LoggingAdapterBuilder<?, ?>> toBuilders(
        final String[] keys, final LoggingAdapterBuilderRegistry registry) {

        final List<LoggingAdapterBuilder<?, ?>> result = new ArrayList<>(keys.length);

        for (String key: keys) {
            final LoggingAdapterBuilder builder = registry.get(key);
            if (builder != null) {
                result.add(builder);
            }
        }

        return result;
    }

    /**
     * Requests given builders to build {@link LoggingAdapter}s.
     * @param builders the builders.
     * @return the adapters just built.
     */
    protected List<LoggingAdapter> toAdapters(final List<LoggingAdapterBuilder<?, ?>> builders) {
        final List<LoggingAdapter> result = new ArrayList<>(builders.size());

        for (LoggingAdapterBuilder<?, ?> builder : builders) {
            final LoggingAdapter adapter = builder.build();

            if (adapter != null) {
                result.add(adapter);
            }
        }

        return result;
    }
}
