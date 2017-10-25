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
import es.osoco.logging.adapter.AbstractLoggingAdapter;
import es.osoco.logging.adapter.LoggingAdapter;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * A {@link LoggingAdapter} for AWS-Lambda.
 */
@ToString
@EqualsAndHashCode(callSuper=true)
@SuppressWarnings("unused")
public class AwsLambdaLoggingAdapter
    extends AbstractLoggingAdapter<AwsLambdaLoggingConfiguration> {

    /**
     * Creates a new {@code AwsLambdaLoggingAdapter}.
     * @param config the {@link AwsLambdaLoggingConfiguration}.
     */
    public AwsLambdaLoggingAdapter(final AwsLambdaLoggingConfiguration config) {
        super(config);
    }

    /**
     * Logs a message using the {@link LambdaLogger}.
     * @param msg the message to log.
     */
    protected void log(final String msg) {
        getLoggingConfiguration().getLambdaLogger().log(msg);
    }

    @Override
    public void logError(final String msg) {
        log(msg);
    }

    @Override
    public void logWarn(final String msg) {
        log(msg);
    }

    @Override
    public void logInfo(final String msg) {
        log(msg);
    }

    @Override
    public void logDebug(final String msg) {
        log(msg);
    }

    @Override
    public void logTrace(final String msg) {
        log(msg);
    }
}
