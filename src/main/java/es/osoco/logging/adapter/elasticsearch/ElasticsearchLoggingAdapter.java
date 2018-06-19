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
package es.osoco.logging.adapter.elasticsearch;

import es.osoco.logging.LoggingContext;
import es.osoco.logging.adapter.AbstractLoggingAdapter;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * {@link es.osoco.logging.adapter.LoggingAdapter} for ElasticSearch, based on a REST client.
 */
@EqualsAndHashCode(callSuper=false)
@Getter
@ToString
public class ElasticsearchLoggingAdapter
    extends AbstractLoggingAdapter<ElasticSearchLoggingConfiguration> {

    /**
     * The REST client.
     */
    @Nullable
    private RestClient restClient;

    /**
     * Creates a new {@link ElasticsearchLoggingAdapter} with given configuration.
     * @param config the {@link ElasticSearchLoggingConfiguration}.
     */
    public ElasticsearchLoggingAdapter(@NonNull final ElasticSearchLoggingConfiguration config) {
        super(config);
        this.restClient = null;
    }

    /**
     * Specifies the Rest client.
     * @param client such instance.
     */
    protected final void immutableSetRestClient(@NonNull final RestClient client) {
        this.restClient = client;
    }

    /**
     * Specifies the Rest client. Override me if necessary.
     * @param client such instance.
     */
    @SuppressWarnings("unused")
    protected void setRestClient(@NonNull final RestClient client) {
        immutableSetRestClient(client);
    }

    /**
     * Retrieves the Rest client.
     * @return such instance.
     */
    @Nullable
    protected final RestClient immutableGetRestClient() {
        return this.restClient;
    }

    /**
     * Retrieves the Rest client. Override me if necessary.
     * @return such instance.
     */
    @SuppressWarnings("unused")
    @NonNull
    public RestClient getRestClient() {
        final RestClient result;

        @Nullable final RestClient aux = immutableGetRestClient();

        if (aux == null) {
            result = buildRestClient();
            setRestClient(result);
        } else {
            result = aux;
        }

        return result;
    }

    /**
     * Builds the REST client.
     * @return such {@link RestClient} instance.
     */
    @NonNull
    protected RestClient buildRestClient() {
        final RestClient result =
            RestClient.builder(
                new HttpHost(
                    getLoggingConfiguration().getHost(),
                    getLoggingConfiguration().getPort(),
                    getLoggingConfiguration().getScheme()))
                .build();
        Runtime.getRuntime().addShutdownHook(new Thread(this::cleanup));

        return result;
    }

    @Override
    protected void logError(@Nullable final String category, @NonNull final String msg) {
        logToElasticSearch(category, msg, getLoggingContext());
    }

    @Override
    protected void logWarn(@Nullable final String category, @NonNull final String msg) {
        logToElasticSearch(category, msg, getLoggingContext());
    }

    @Override
    protected void logInfo(@Nullable final String category, @NonNull final String msg) {
        logToElasticSearch(category, msg, getLoggingContext());
    }

    @Override
    protected void logDebug(@Nullable final String category, @NonNull final String msg) {
        logToElasticSearch(category, msg, getLoggingContext());
    }

    @Override
    protected void logTrace(@Nullable final String category, @NonNull final String msg) {
        logToElasticSearch(category, msg, getLoggingContext());
    }

    protected void logToElasticSearch(
        @Nullable final String category, @NonNull final String msg, @NonNull final LoggingContext ctx) {
        @NonNull RestClient restClient = getRestClient();

        final long id = System.currentTimeMillis() / 1000;

        @Nullable final String application = ctx.get("application");
        @Nullable final String event = ctx.get("event");
        @Nullable final String useCase = ctx.get("useCase");

        @NonNull final LocalDateTime now = LocalDateTime.now();

        @NonNull final StringBuilder data = new StringBuilder("{");

        boolean firstElement = true;

        if (category != null) {
            data.append("\n    \"category\": \"");
            data.append(category);
            data.append("\"");
            firstElement = false;
        }
        if (event != null) {
            if (!firstElement) {
                data.append(",");
            }
            data.append("\n    \"events\" : \"");
            data.append(escapeDoubleQuotes(event));
            data.append("\"");
            firstElement = false;
        }

        if (useCase != null) {
            if (!firstElement) {
                data.append(",");
            }
            data.append("\n    \"useCase\" : \"");
            data.append(escapeDoubleQuotes(useCase));
            data.append("\"");
            firstElement = false;
        }

        if (!firstElement) {
            data.append(",");
        }
        data.append("\n    \"application\": \"");
        data.append(application);
        data.append("\"");

        data.append(",\n    \"message\": \"");
        data.append(escapeDoubleQuotes(msg));
        data.append("\",\n    \"timestamp\": \"");
        data.append(TIMESTAMP_FORMATTER.format(now));
        data.append("\"\n}");

        @NonNull final String content = data.toString();

        @NonNull final HttpEntity entity =
            new NStringEntity(content, ContentType.APPLICATION_JSON);

        try {
            @NonNull final Response indexResponse =
                restClient.performRequest(
                    "PUT",
                    "/log-" + INDEX_FORMATTER.format(now) + "/" + application + "/" + id,
                    Collections.emptyMap(),
                    entity);

            int statusCode = indexResponse.getStatusLine().getStatusCode();
            if (statusCode >= 400) {
                ctx.put("latestErrorCode", statusCode);
            }
        } catch (final IOException ioException) {
            ctx.put("latestErrorCode", ioException);
        }
    }

    public void cleanup() {
        @Nullable final RestClient restClient = getRestClient();

        if (restClient != null) {
            try {
                restClient.close();
                getLoggingContext().put("latestError", null);
            } catch (final IOException ioException) {
                getLoggingContext().put("latestError", ioException);
            }
        }

        setLoggingContext(null);
    }

    /**
     * Escapes any double-quotes in given text.
     * @param txt the text to escape.
     * @return the resulting text.
     */
    @NonNull
    protected String escapeDoubleQuotes(@NonNull final String txt) {
        final Matcher matcher = DOUBLE_QUOTES_ESCAPER.matcher(txt);
        return matcher.replaceAll("\\\\\"");
    }

    @NonNull
    public static final DateTimeFormatter INDEX_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd");

    @NonNull
    public static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @NonNull
    protected static final Pattern DOUBLE_QUOTES_ESCAPER = Pattern.compile("\"");

    protected static int CURRENT_COUNT = 0;
}
