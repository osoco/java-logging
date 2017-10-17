package es.osoco.logging.config.init

import es.osoco.logging.config.LoggingConfigurationRegistry
import spock.lang.Specification
import spock.util.mop.ConfineMetaClassChanges

@ConfineMetaClassChanges([System])
class LoggingConfigurationRegistryAutoInitializerSpecification
        extends Specification {

    def "The AutoInitializer publishes the System.out configuration" () {
        setup:
        LoggingConfigurationRegistry registry = LoggingConfigurationRegistry.getInstance()

        expect:
        registry.get("System.out") != null
    }

    def "The AutoInitializer publishes the System.err configuration" () {
        setup:
        LoggingConfigurationRegistry registry = LoggingConfigurationRegistry.getInstance()

        expect:
        registry.get("System.err") != null
    }
}
