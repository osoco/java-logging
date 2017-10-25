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
