package es.osoco.logging.adapter.elasticsearch;

import es.osoco.logging.adapter.AbstractLoggingAdapterBuilder;

/**
 * {@link es.osoco.logging.adapter.LoggingAdapterBuilder} for remote ElasticSearch servers.
 */
@SuppressWarnings("unused")
public class ElasticsearchLoggingAdapterBuilder
    extends AbstractLoggingAdapterBuilder<ElasticSearchLoggingConfiguration, ElasticsearchLoggingAdapter> {

    /**
     * Creates a new {@code ElasticsearchLoggingAdapterBuilder}.
     * @param config the configuration.
     */
    protected ElasticsearchLoggingAdapterBuilder(final ElasticSearchLoggingConfiguration config) {
        super(config.getRegistryKey(), config);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ElasticsearchLoggingAdapter build(final ElasticSearchLoggingConfiguration config){
        return new ElasticsearchLoggingAdapter(config);
    }
}
