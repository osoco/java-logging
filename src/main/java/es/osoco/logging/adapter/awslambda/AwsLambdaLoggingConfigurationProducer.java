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
package es.osoco.logging.adapter.awslambda;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import es.osoco.logging.config.LoggingConfiguration;
import es.osoco.logging.config.LoggingConfigurationRegistry;

/**
 * Produces AWS Lambda-based {@link LoggingConfiguration}s.
 */
@SuppressWarnings("unused")
public class AwsLambdaLoggingConfigurationProducer {

    /**
     * Creates an empty instance.
     */
    public AwsLambdaLoggingConfigurationProducer() {}

    /**
     * Configures the logging to optionally use given {@link LambdaLogger}.
     * @param logger the logger.
     */
    @SuppressWarnings("unused")
    public void configureLogging(final LambdaLogger logger) {
        configureLogging("aws-lambda", logger);
    }

    /**
     * Configures the logging to optionally use given {@link LambdaLogger}, using a custom key.
     * @param key the key.
     * @param logger the logger.
     */
    @SuppressWarnings("unused")
    public void configureLogging(final String key, final LambdaLogger logger) {
        LoggingConfigurationRegistry.getInstance().put(key, new AwsLambdaLoggingConfiguration(logger));
    }
}
