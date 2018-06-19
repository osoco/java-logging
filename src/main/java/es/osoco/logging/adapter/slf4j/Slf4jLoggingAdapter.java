package es.osoco.logging.adapter.slf4j;

import es.osoco.logging.adapter.AbstractLoggingAdapter;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Logging implementation based on SLF4J.
 */
@EqualsAndHashCode(callSuper = true)
@ToString
public class Slf4jLoggingAdapter
    extends AbstractLoggingAdapter<Slf4jLoggingConfiguration> {

    /**
     * The logger factory class.
     */
    private Class loggerFactoryClass;

    /**
     * The logger class.
     */
    private Class loggerClass;

    /**
     * A mapping between category and loggers.
     */
    private Map<String, Object> loggerMapping = new HashMap<>();

    /**
     * A mapping between category and error methods.
     */
    private Map<String, Map<String, Method>> methodMapping = new HashMap<>();

    /**
     * Creates a new adapter.
     * @param config the {@link Slf4jLoggingConfiguration} instance.
     */
    public Slf4jLoggingAdapter(@NonNull final Slf4jLoggingConfiguration config) {
        super(config);
    };

    /**
     * Annotates the logger class.
     * @param clazz such instance.
     */
    protected void setLoggerClass(@NonNull final Class clazz) {
        this.loggerClass = clazz;
    }

    /**
     * Retrieves the logger class.
     * @return such instance.
     */
    protected Class getLoggerClass() {
        return this.loggerClass;
    }


    /**
     * Annotates the logger factory class.
     * @param clazz such instance.
     */
    protected void setLoggerFactoryClass(@NonNull final Class clazz) {
        this.loggerFactoryClass = clazz;
    }

    /**
     * Retrieves the logger factory class.
     * @return such instance.
     */
    protected Class getLoggerFactoryClass() {
        return this.loggerFactoryClass;
    }

    /**
     * Retrieves the logger mapping.
     * @return mapping the mapping.
     */
    @NonNull
    protected Map<String, Object> getLoggerMapping() {
        return loggerMapping;
    }

    /**
     * Retrieves the logger mapping.
     * @return mapping the mapping.
     */
    @NonNull
    protected Map<String, Method> retrieveMethodMapping(@NonNull final String category) {
        @NonNull final Map<String, Method> result;
        @Nullable final Map<String, Method> existing = methodMapping.get(category);

        if (existing == null) {
            result = new HashMap<>();
            methodMapping.put(category, result);
        } else {
            result = existing;
        }

        return result;
    }

    /**
     * Retrieves the logger class.
     * @return such class.
     */
    protected Class retrieveLoggerClass() {
        @Nullable Class result = this.getLoggerClass();

        if (result == null) {

            try {
                result = Class.forName("org.slf4j.Logger");
                setLoggerClass(result);
            } catch (final Throwable throwable) {
            }
        }

        return result;
    }

    /**
     * Retrieves the LoggerFactory class.
     * @return such class.
     */
    protected Class retrieveLoggerFactoryClass() {
        @Nullable Class result = this.getLoggerFactoryClass();

        if (result == null) {
            try {
                result = Class.forName("org.slf4j.LoggerFactory");
                this.setLoggerFactoryClass(result);
            } catch (final Throwable throwable) {

            }
        }

        return result;
    }

    /**
     * Retrieves the Logger object for given category.
     * @param category the category.
     * @return the Logger instance.
     */
    protected Object retrieveLogger(@Nullable final String category) {
        @NonNull final String cat = (category == null) ? retrieveDefaultCategory() : category;

        @Nullable Object result = getLoggerMapping().get(cat);

        if (result == null) {
            @Nullable final Class factoryClass = retrieveLoggerFactoryClass();
            if (factoryClass != null) {
                setLoggerFactoryClass(factoryClass);
                result = retrieveLogger(cat, factoryClass);
                if (result != null) {
                    getLoggerMapping().put(cat, result);
                }
            }
        }

        return result;
    }

    /**
     * Retrieves the Logger object for given category, using the factory class provided.
     * @param category the category.
     * @param factoryClass the LoggerFactory class.
     * @return the Logger instance.
     */
    protected Object retrieveLogger(@NonNull final String category, @NonNull final Class factoryClass) {
        @Nullable Object result = null;

        try {
            @NonNull final Method method = factoryClass.getMethod("getLogger", String.class);
            if (method != null) {
                result = method.invoke(null, category);
                getLoggerMapping().put(category, result);
            }
        } catch (final Throwable throwable) {
        }

        return result;
    }

    /**
     * Logs a message using a certain level, in given category.
     * @param level the log level (corresponds to Logger method names).
     * @param category the category.
     * @param msg the message to throw.
     * @throws Throwable if any error occurs.
     */
    protected void log(@NonNull final String level, @Nullable final String category, @NonNull final String msg)
        throws Throwable {
        @Nullable Method method = retrieveMethodMapping(category).get(level);

        @Nullable final Object logger = retrieveLogger(category);

        if (method == null) {

            @Nullable final Class clazz = retrieveLoggerClass();

            if (logger != null) {
                method = clazz.getMethod(level, String.class);
                retrieveMethodMapping(category).put(level, method);
            }
        }

        if (method != null) {
            method.invoke(logger, msg);
        }
    }

    @Override
    protected void logError(@Nullable final String category, @NonNull final String msg) {
        try {
            log("error", category, msg);
        } catch (@NonNull final Throwable throwable) {
            super.setErrorEnabled(category, false);
        }
    }

    @Override
    protected void logWarn(@Nullable final String category, @NonNull final String msg) {
        try {
            log("warn", category, msg);
        } catch (@NonNull final Throwable throwable) {
            super.setWarnEnabled(category, false);
        }
    }

    @Override
    protected void logInfo(@Nullable final String category, @NonNull final String msg) {
        try {
            log("info", category, msg);
        } catch (@NonNull final Throwable throwable) {
            super.setInfoEnabled(category, false);
        }
    }

    @Override
    protected void logDebug(@Nullable final String category, @NonNull final String msg) {
        try {
            log("debug", category, msg);
        } catch (@NonNull final Throwable throwable) {
            super.setDebugEnabled(category, false);
        }
    }

    @Override
    protected void logTrace(@Nullable final String category, @NonNull final String msg) {
        try {
            log("trace", category, msg);
        } catch (@NonNull final Throwable throwable) {
            super.setTraceEnabled(category, false);
        }
    }
}
