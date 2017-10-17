package es.osoco.logging.config

import es.osoco.logging.adapter.LoggingAdapterBuilderRegistry
import es.osoco.logging.adapter.printstream.PrintStreamLoggingConfiguration
import spock.lang.Specification

class LoggingConfigurationRegistrySpecification
        extends Specification {

    def "LoggingConfigurationRegistry provides already-existing LoggingConfiguration instances"() {
        setup:
        def registry = LoggingConfigurationRegistry.getInstance()
        def config = new PrintStreamLoggingConfiguration("my-system-out", System.out)
        registry.put(config.getRegistryKey(), config)

        expect:
        registry.get(config.getRegistryKey()) == config
    }

    def "LoggingConfigurationRegistry notifies SystemOutLoggingAdapterBuilder when the System.out-LoggingConfiguration is published"() {
        setup:
        def configRegistry = LoggingConfigurationRegistry.getInstance()
        def config = new PrintStreamLoggingConfiguration("System.out", System.out)
        configRegistry.put(config.getRegistryKey(), config)
        def builderRegistry = LoggingAdapterBuilderRegistry.getInstance()

        expect:
        builderRegistry.get(config.getRegistryKey()) != null

    }
}
