package es.osoco.logging.adapter.elasticsearch

import es.osoco.logging.config.LoggingConfigurationRegistry

class ElasticSearchEnvVarIsNotConfiguredSpecification { // extends Specification {

    def "The LoggingConfigurationRegistry doesn't find the ElasticSearchConfiguration if no environment variables are found" () {
        setup:
        LoggingConfigurationRegistry registry = LoggingConfigurationRegistry.getInstance()

        expect:
        registry.get("ElasticSearch") == null
    }
}
