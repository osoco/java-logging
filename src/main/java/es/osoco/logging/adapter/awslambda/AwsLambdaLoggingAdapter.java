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
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

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
    protected void log(@Nullable final String category, @NonNull final String msg) {
        getLoggingConfiguration().getLambdaLogger().log(buildCategoryPrefix(category) + msg);
    }

    @Override
    public void logError(@Nullable final String category, @NonNull final String msg) {
        log(category, msg);
    }

    @Override
    public void logWarn(@Nullable final String category, @NonNull final String msg) {
        log(category, msg);
    }

    @Override
    public void logInfo(@Nullable final String category, @NonNull final String msg) {
        log(category, msg);
    }

    @Override
    public void logDebug(@Nullable final String category, @NonNull final String msg) {
        log(category, msg);
    }

    @Override
    public void logTrace(@Nullable final String category, @NonNull final String msg) {
        log(category, msg);
    }
}
