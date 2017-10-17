package es.osoco.logging.adapter
import es.osoco.logging.adapter.printstream.SystemOutLoggingAdapterBuilder
import es.osoco.logging.config.LoggingConfigurationRegistry
import spock.lang.Specification

class LoggingAdapterBuilderRegistrySpecification
        extends Specification {

    def "LoggingAdapterBuilderRegistry knows about the System.out-based LoggingAdapterBuilder"() {
        setup:
        LoggingConfigurationRegistry.getInstance()
        LoggingAdapterBuilderRegistry registry = LoggingAdapterBuilderRegistry.getInstance()

        expect:
        registry.get("System.out") != null
        registry.get("System.out") instanceof SystemOutLoggingAdapterBuilder
    }
}
