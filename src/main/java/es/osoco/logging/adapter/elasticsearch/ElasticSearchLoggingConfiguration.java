package es.osoco.logging.adapter.elasticsearch;

import es.osoco.logging.config.LoggingConfiguration;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Required configuration for ElasticSearch logging.
 */
public interface ElasticSearchLoggingConfiguration
    extends LoggingConfiguration {
    /**
     * Retrieves the ElasticSearch host.
     * @return such host.
     */
    String getHost();

    /**
     * Retrieves the ElasticSearch port.
     * @return such port.
     */
    int getPort();

    /**
     * Retrieves the ElasticSearch scheme.
     * @return such scheme.
     */
    String getScheme();

    @ToString
    @EqualsAndHashCode
    class ElasticSearchLoggingConfigurationData
        implements ElasticSearchLoggingConfiguration {
        /**
         * The registry key.
         */
        private String registryKey;

        /**
         * The host.
         */
        private String host;

        /**
         * The port.
         */
        private int port;

        /**
         * The scheme.
         */
        private String scheme;

        /**
         * Creates a new instance.
         * @param key the key.
         * @param host the host.
         * @param port the port.
         * @param scheme the scheme.
         */
        public ElasticSearchLoggingConfigurationData(
            final String key, final String host, final int port, final String scheme) {
            immutableSetRegistryKey(key);
            immutableSetHost(host);
            immutableSetPort(port);
            immutableSetScheme(scheme);
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
        @SuppressWarnings("unused")
        protected void setRegistryKey(final String key) {
            immutableSetRegistryKey(key);
        }

        @Override
        public String getRegistryKey() {
            return registryKey;
        }

        /**
         * Specifies the host.
         * @param host such host.
         */
        protected final void immutableSetHost(final String host) {
            this.host = host;
        }

        /**
         * Specifies the host. Override me if necessary.
         * @param host such host.
         */
        @SuppressWarnings("unused")
        protected void setHost(final String host) {
            immutableSetHost(host);
        }

        @Override
        public String getHost() {
            return host;
        }

        /**
         * Specifies the port.
         * @param port such port.
         */
        protected final void immutableSetPort(final int port) {
            this.port = port;
        }

        /**
         * Specifies the port. Override me if necessary.
         * @param port such port.
         */
        @SuppressWarnings("unused")
        protected void setPort(final int port) {
            immutableSetPort(port);
        }

        @Override
        public int getPort() {
            return port;
        }

        /**
         * Specifies the scheme.
         * @param scheme such scheme.
         */
        protected final void immutableSetScheme(final String scheme) {
            this.scheme = scheme;
        }

        /**
         * Specifies the scheme. Override me if necessary.
         * @param scheme such scheme.
         */
        @SuppressWarnings("unused")
        protected void setScheme(final String scheme) {
            immutableSetScheme(scheme);
        }

        @Override
        public String getScheme() {
            return scheme;
        }
    }
}
