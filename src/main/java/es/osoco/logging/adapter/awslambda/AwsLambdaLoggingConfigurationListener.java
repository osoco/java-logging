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

import es.osoco.logging.adapter.LoggingAdapterBuilderRegistry;
import es.osoco.logging.config.LoggingConfiguration;
import es.osoco.logging.config.LoggingConfigurationListener;

/**
 * Gets notified when a new {@link LoggingConfiguration} is published in the
 * registry, and checks if it corresponds to AWS-Lambda. If so, creates a builder
 * so that any client can use it right away.
 */
@SuppressWarnings("unused")
public class AwsLambdaLoggingConfigurationListener
    implements LoggingConfigurationListener {

        @Override
        public void newLoggingConfigurationAvailable(final LoggingConfiguration config) {
            if (config instanceof AwsLambdaLoggingConfiguration) {
                LoggingAdapterBuilderRegistry.getInstance().put(
                    config.getRegistryKey(),
                    new AwsLambdaLoggingAdapterBuilder(
                        config.getRegistryKey(), ((AwsLambdaLoggingConfiguration) config).getLambdaLogger()));
            }
        }

}
