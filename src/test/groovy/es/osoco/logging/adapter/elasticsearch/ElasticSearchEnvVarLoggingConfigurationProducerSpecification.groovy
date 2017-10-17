package es.osoco.logging.adapter.elasticsearch

import es.osoco.logging.config.LoggingConfiguration
import es.osoco.logging.annotations.LoggingConfigurationProducer
import es.osoco.logging.config.LoggingConfigurationRegistry
import spock.lang.Specification

class ElasticSearchEnvVarLoggingConfigurationProducerSpecification extends Specification {

    def "The LoggingConfigurationRegistry doesn't find the ElasticSearchConfiguration if no environment variables are found" () {
        setup:
        LoggingConfigurationRegistry registry = LoggingConfigurationRegistry.getInstance()

        expect:
        registry.get("ElasticSearch") == null
    }

    def "The LoggingConfigurationRegistry finds the ElasticSearchConfiguration if the environment variables are found" () {
        setup:
        System.metaClass.static.getProperty = { String propName ->
            def result

            if (propName == 'elasticsearch.host') {
                result = 'elasticsearch'
            } else {
                result = null
            }

            return result
        }
        LoggingConfigurationRegistry registry = LoggingConfigurationRegistry.getInstance()

        expect:
        registry.get("ElasticSearch") != null
    }

    static class TestableElasticSearchEnvVarLoggingConfiguration
        extends ElasticSearchEnvVarLoggingConfiguration {

        @LoggingConfigurationProducer(key = "ElasticSearch")
        LoggingConfiguration createLoggingConfiguration() {
            return super.createLoggingConfiguration()
        }

        @Override
        String getHost() {
            return System.getProperty(ELASTICSEARCH_HOST_PROPERTY)
        }

        @Override
        protected LoggingConfiguration produceConfiguration(
            final String key, final String host, final int port, final String scheme) {
            return new ElasticSearchLoggingConfiguration.ElasticSearchLoggingConfigurationData(key, host, port, scheme)
        }
    }


}
