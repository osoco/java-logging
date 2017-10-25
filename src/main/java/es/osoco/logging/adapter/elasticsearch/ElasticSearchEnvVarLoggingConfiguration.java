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

import es.osoco.logging.config.LoggingConfiguration;
import es.osoco.logging.annotations.LoggingConfigurationProducer;
import es.osoco.logging.helper.EnvironmentHelper;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * ElasticSearch configuration based on environment variables.
 */
@ToString
@EqualsAndHashCode
@SuppressWarnings("unused")
public class ElasticSearchEnvVarLoggingConfiguration
    implements ElasticSearchLoggingConfiguration {

    /**
     * The registry key.
     */
    private String registryKey;

    /**
     * The ElasticSearch host property: "elasticsearch.host".
     */
    public static final String ELASTICSEARCH_HOST_PROPERTY = "elasticsearch.host";

    /**
     * The ElasticSearch host environment variable: "ELASTICSEARCH_HOST".
     */
    public static final String ELASTICSEARCH_HOST_ENVVAR = "ELASTICSEARCH_HOST";

    /**
     * The default ElasticSearch host: "elasticsearch".
     */
    public static final String DEFAULT_ELASTICSEARCH_HOST = "elasticsearch";

    /**
     * The ElasticSearch port property: "elasticsearch.port".
     */
    public static final String ELASTICSEARCH_PORT_PROPERTY = "elasticsearch.port";

    /**
     * The ElasticSearch port environment variable: "ELASTICSEARCH_PORT".
     */
    public static final String ELASTICSEARCH_PORT_ENVVAR = "ELASTICSEARCH_PORT";

    /**
     * The default ElasticSearch port: 9200.
     */
    public static final int DEFAULT_ELASTICSEARCH_PORT = 9200;

    /**
     * The ElasticSearch schema property: "elasticsearch.scheme".
     */
    public static final String ELASTICSEARCH_SCHEME_PROPERTY = "elasticsearch.scheme";

    /**
     * The ElasticSearch scheme environment variable: "ELASTICSEARCH_SCHEME".
     */
    public static final String ELASTICSEARCH_SCHEME_ENVVAR = "ELASTICSEARCH_SCHEME";

    /**
     * The default ElasticSearch host: "https".
     */
    public static final String DEFAULT_ELASTICSEARCH_SCHEME = "http";

    /**
     * Creates an empty instance.
     */
    public ElasticSearchEnvVarLoggingConfiguration() {}

    /**
     * Retrieves the ElasticSearch host.
     * @return such host.
     */
    public String getHost() {
        return
            EnvironmentHelper.getInstance().retrieveStringFromSystemPropertyOrEnvironmentVariableOrElse(
                ELASTICSEARCH_HOST_PROPERTY, ELASTICSEARCH_HOST_ENVVAR, null);
    }

    /**
     * Retrieves the ElasticSearch port.
     * @return such host.
     */
    public int getPort() {
        return
            EnvironmentHelper.getInstance().retrieveIntFromSystemPropertyOrEnvironmentVariableOrElse(
                ELASTICSEARCH_PORT_PROPERTY, ELASTICSEARCH_PORT_ENVVAR, DEFAULT_ELASTICSEARCH_PORT);
    }

    /**
     * Retrieves the ElasticSearch host.
     * @return such host.
     */
    public String getScheme() {
        return
            EnvironmentHelper.getInstance().retrieveStringFromSystemPropertyOrEnvironmentVariableOrElse(
                ELASTICSEARCH_SCHEME_PROPERTY, ELASTICSEARCH_SCHEME_ENVVAR, DEFAULT_ELASTICSEARCH_SCHEME);
    }

    /**
     * Creates a logging configuration if the environment variables are set.
     * The @LoggingConfigurationProducer annotation ensures this method is found
     * automatically at startup.
     * @return the {@link LoggingConfiguration} instance, or {@code null} if not present.
     */
    @SuppressWarnings("unused")
    @LoggingConfigurationProducer(key = "ElasticSearch")
    public LoggingConfiguration createLoggingConfiguration() {
        final LoggingConfiguration result;

        final String key = "ElasticSearch"; // Needs to match the @LoggingConfigurationProducer's key.
        final String host = getHost();

        if (host != null) {
            result = produceConfiguration(key, host, getPort(), getScheme());
        } else {
            result = null;
        }

        return result;
    }

    /**
     * Produces a new configuration.
     * @param key the registry key.
     * @param host the host.
     * @param port the port.
     * @param scheme the scheme.
     * @return the {@link LoggingConfiguration}.
     */
    protected LoggingConfiguration produceConfiguration(
        final String key, final String host, final int port, final String scheme) {
        return new ElasticSearchLoggingConfigurationData(key, host, port, scheme);
    }

    /**
     * Specifies the registry key.
     * @param key such key.
     */
    protected final void immutableSetRegistryKey(final String key) {
        this.registryKey = key;
    }

    /**
     * Specifies the registry key. Override me if necessary.
     * @param key such key.
     */
    protected void setRegistryKey(final String key) {
        immutableSetRegistryKey(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRegistryKey() {
        return registryKey;
    }
}
