package es.osoco.logging.adapter

import es.osoco.logging.adapter.printstream.PrintStreamLoggingAdapter
import es.osoco.logging.adapter.printstream.PrintStreamLoggingConfiguration
import spock.lang.Specification
/**
 * Some tests on {@link AbstractLoggingAdapter}.
 */
class AbstractLoggingAdapterSpecification
    extends Specification {

    def "The default category doesn't include any java-logging reference"() {
        setup:
        final AbstractLoggingAdapter<PrintStreamLoggingConfiguration> adapter =
            new PrintStreamLoggingAdapter()

        when:
        final String category = adapter.retrieveDefaultCategory()

        then:
        category
        !category.startsWith('es.osoco.logging')
    }

    def "The default category doesn't include any org.codehaus.groovy reference"() {
        setup:
        final AbstractLoggingAdapter<PrintStreamLoggingConfiguration> adapter =
            new PrintStreamLoggingAdapter()

        when:
        final String category = adapter.retrieveDefaultCategory()

        then:
        category
        !category.startsWith('org.codehaus.groovy')
    }
}
