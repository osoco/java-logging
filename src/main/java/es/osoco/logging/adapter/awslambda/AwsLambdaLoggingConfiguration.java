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
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * LoggingPrefs configuration for {@link LambdaLogger}.
 */
@ToString
@EqualsAndHashCode
public class AwsLambdaLoggingConfiguration
    implements LoggingConfiguration {

    public static AwsLambdaLoggingConfiguration REMOVEME;

    /**
     * The AWS lambda logger.
     */
    private LambdaLogger logger;

    /**
     * Creates a new AWS-Lambda logger configuration.
     * @param logger the {@link LambdaLogger}.
     */
    public AwsLambdaLoggingConfiguration(final LambdaLogger logger) {
        immutableSetLambdaLogger(logger);
        REMOVEME = this;
    }

    /**
     * Specifies the {@link LambdaLogger}.
     * @param logger the {@link LambdaLogger} instance.
     */
    protected final void immutableSetLambdaLogger(final LambdaLogger logger) {
        this.logger = logger;
    }

    /**
     * Specifies the underlying {@link LambdaLogger}. Override me if necessary.
     * @param logger the logger.
     */
    @SuppressWarnings("unused")
    protected void setLambdaLogger(final LambdaLogger logger) {
        immutableSetLambdaLogger(logger);
    }

    /**
     * Retrieves the underlying {@link LambdaLogger}.
     * @return the logger.
     */
    protected final LambdaLogger immutableGetLambdaLogger() {
        return this.logger;
    }

    /**
     * Retrieves the underlying {@link LambdaLogger}. Override me if necessary.
     * @return the logger.
     */
    @SuppressWarnings("unused")
    public LambdaLogger getLambdaLogger() {
        return immutableGetLambdaLogger();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRegistryKey() {
        return "aws-lambda";
    }
}
