/*
  This program is free software: you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.

  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.

  You should have received a copy of the GNU General Public License
  along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
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
