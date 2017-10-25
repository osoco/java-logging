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
import es.osoco.logging.adapter.AbstractLoggingAdapterBuilder;
import es.osoco.logging.adapter.LoggingAdapterBuilder;

/**
 * {@link LoggingAdapterBuilder} for AWS Lambda.
 */
@SuppressWarnings("unused")
public class AwsLambdaLoggingAdapterBuilder
    extends AbstractLoggingAdapterBuilder<AwsLambdaLoggingConfiguration, AwsLambdaLoggingAdapter> {

    /**
     * Creates a new {@code AwsLambdaLoggingAdapterBuilder} for given {@link LambdaLogger}.
     * @param logger the {@link LambdaLogger}.
     */
    public AwsLambdaLoggingAdapterBuilder(final LambdaLogger logger) {
        this("aws-lambda", logger);
    }

    /**
     * Creates a new {@code AwsLambdaLoggingAdapterBuilder} for given key and {@link LambdaLogger}.
     * @param key the key.
     * @param logger the {@link LambdaLogger}.
     */
    public AwsLambdaLoggingAdapterBuilder(final String key, final LambdaLogger logger) {
        super(key, new AwsLambdaLoggingConfiguration(logger));
    }

    @Override
    public AwsLambdaLoggingAdapter build(final AwsLambdaLoggingConfiguration config) {
        return new AwsLambdaLoggingAdapter(config);
    }

}
