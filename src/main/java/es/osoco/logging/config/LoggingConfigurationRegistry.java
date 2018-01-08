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
package es.osoco.logging.config;

import es.osoco.logging.annotations.LoggingConfigurationProducer;
import es.osoco.logging.helper.EnvironmentHelper;
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.github.lukehutch.fastclasspathscanner.matchprocessor.ImplementingClassMatchProcessor;
import io.github.lukehutch.fastclasspathscanner.matchprocessor.MethodAnnotationMatchProcessor;

import java.lang.reflect.Executable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Registry for {@link LoggingConfiguration}s.</p>
 * <p>It will automatically discover logging configurations, unless
 * either "automatically.discover.logging.configurations" property or
 * "AUTOMATICALLY_DISCOVER_LOGGING_CONFIGURATIONS" environment variable is set to "false".</p>
 * <p>In that case, only logging configurations provided explicitly will be available.</p>
 * <p>Similarly, it will automatically discover logging configuration producers, unless
 * either "automatically.discover.logging.configuration.producers" property or
 * "AUTOMATICALLY_DISCOVER_LOGGING_CONFIGURATION_PRODUCERS" environment variable is set to "false".</p>
 * <p>In that case, only logging configuration producers explicitly provided will be available.</p>
 */
@SuppressWarnings("unused")
public class LoggingConfigurationRegistry {

    /**
     * The property to enable or disable automatically discovering of logging configurations.
     */
    public static final String AUTOMATICALLY_DISCOVER_LOGGING_CONFIGURATIONS_PROPERTY =
        "automatically.discover.logging.configurations";

    /**
     * The environment variable to enable or disable automatically discovering of logging configurations.
     */
    public static final String AUTOMATICALLY_DISCOVER_LOGGING_CONFIGURATIONS_ENVVAR =
        "AUTOMATICALLY_DISCOVER_LOGGING_CONFIGURATIONS";

    /**
     * The default behavior for automatically discovering of logging configurations.
     */
    public static final boolean DEFAULT_AUTOMATICALLY_DISCOVER_LOGGING_CONFIGURATIONS = true;

    /**
     * The property to enable or disable automatically discovering of logging configuration producers.
     */
    public static final String AUTOMATICALLY_DISCOVER_LOGGING_CONFIGURATION_PRODUCERS_PROPERTY =
        "automatically.discover.logging.configuration.producers";

    /**
     * The environment variable to enable or disable automatically discovering of logging configuration producers.
     */
    public static final String AUTOMATICALLY_DISCOVER_LOGGING_CONFIGURATION_PRODUCERS_ENVVAR =
        "AUTOMATICALLY_DISCOVER_LOGGING_CONFIGURATION_PRODUCERS";

    /**
     * The default behavior for automatically discovering of logging configuration producers.
     */
    public static final boolean DEFAULT_AUTOMATICALLY_DISCOVER_LOGGING_CONFIGURATION_PRODUCERS = true;

    /**
     * The underlying logging configuration map.
     */
    private Map<String, ? extends LoggingConfiguration> map = new HashMap<>();

    /**
     * The listeners.
     */
    private List<LoggingConfigurationListener> listeners = new ArrayList<>();

    /**
     * Singleton implementation to avoid double-check locking.
     */
    protected static final class LoggingConfigurationRegistrySingletonContainer {
        public static final LoggingConfigurationRegistry SINGLETON = new LoggingConfigurationRegistry();
    }

    /**
     * Default constructor to avoid public instantiation.
     */
    protected LoggingConfigurationRegistry() {
        scan();
    }

    /**
     * Scans the classpath looking for configurations and producers.
     */
    protected final void scan() {
        discoverLoggingConfigurations();
        discoverLoggingConfigurationProducers();
    }

    /**
     * Cleans up the current configuration.
     */
    protected void clean() {
        immutableGetMap().clear();
        immutableGetListeners().clear();
    }

    /**
     * Adds a new adapter {@link LoggingConfiguration}.
     * @param configuration the configuration.
     */
    protected void register(final LoggingConfiguration configuration) {
        immutableGetMap().put(configuration.getRegistryKey(), configuration);
    }

    /**
     * Retrieves the {@code LoggingConfigurationRegistry}.
     * @return such instance.
     */
    public static LoggingConfigurationRegistry getInstance() {
        return LoggingConfigurationRegistrySingletonContainer.SINGLETON;
    }

    /**
     * Retrieves the underlying map.
     * @param <T> the type of the values.
     * @return such map.
     */
    @SuppressWarnings("unchecked")
    protected final <T extends LoggingConfiguration> Map<String, T> immutableGetMap() {
        return (Map<String, T>) this.map;
    }

    /**
     * Retrieves the map. Override me if necessary.
     * @param <T> the type of the values.
     * @return such map.
     */
    protected <T extends LoggingConfiguration> Map<String, T> getMap() {
        return immutableGetMap();
    }

    /**
     * Retrieves the listeners.
     * @return such listeners.
     */
    protected final List<LoggingConfigurationListener> immutableGetListeners() {
        return this.listeners;
    }

    /**
     * Retrieves the listeners. Override me if necessary.
     * @return such listeners.
     */
    protected List<LoggingConfigurationListener> getListeners() {
        return immutableGetListeners();
    }

    /**
     * Adds a new listener.
     * @param listener the {@link LoggingConfigurationListener} listener.
     */
    public void addListener(final LoggingConfigurationListener listener) {
        getListeners().add(listener);
    }

    /**
     * Retrieves the {@link LoggingConfiguration} for given key.
     * @param key the key.
     * @param <T> the type of the value.
     * @return the associated configuration.
     */
    @SuppressWarnings("unchecked")
    public <T extends LoggingConfiguration> T get(final String key) {
        final T result = (T) immutableGetMap().get(key);

        return result;
    }

    /**
     * Specifies a new {@link LoggingConfiguration}.
     * @param config the config.
     * @param <T> the type of the value.
     */
    @SuppressWarnings("unchecked")
    public <T extends LoggingConfiguration> void put(final T config) {
        put(config.getRegistryKey(), config);
    }

    /**
     * Specifies a new {@link LoggingConfiguration}.
     * @param key the key.
     * @param config the config.
     * @param <T> the type of the value.
     */
    @SuppressWarnings("unchecked")
    public <T extends LoggingConfiguration> void put(final String key, final T config) {
        getMap().put(key, config);
        notifyListeners(config);
    }

    /**
     * Notifies the listeners.
     * @param config the new {@link LoggingConfiguration}.
     * @param <T> the LoggingConfiguration implementation.
     */
    protected <T extends LoggingConfiguration> void notifyListeners(final T config) {
        notifyListeners(config, getListeners());
    }

    /**
     * Notifies the listeners.
     * @param config the new {@link LoggingConfiguration}.
     * @param listeners the {@link LoggingConfigurationListener}s.
     * @param <T> the LoggingConfiguration implementation.
     */
    protected <T extends LoggingConfiguration> void notifyListeners(
        final T config, final List<LoggingConfigurationListener> listeners) {
        listeners.forEach( l -> l.newLoggingConfigurationAvailable(config));
    }

    /**
     * Scans the classpath for methods with {@code @LoggingConfigurationProducer} annotations.
     */
    protected final void discoverLoggingConfigurations() {
        if (discoverLoggingConfigurationsEnabled()) {
            new LoggingConfigurationListenerDiscoverer().discover(this);
        }
    }

    /**
     * Checks whether we are allowed to automatically discover the logging configurations.
     * @return {@code true} in such case.
     */
    protected boolean discoverLoggingConfigurationsEnabled() {
        return
            EnvironmentHelper.getInstance().retrieveBooleanFromSystemPropertyOrEnvironmentVariableOrElse(
                AUTOMATICALLY_DISCOVER_LOGGING_CONFIGURATIONS_PROPERTY,
                AUTOMATICALLY_DISCOVER_LOGGING_CONFIGURATIONS_ENVVAR,
                DEFAULT_AUTOMATICALLY_DISCOVER_LOGGING_CONFIGURATIONS);
    }

    /**
     * Discovers any listeners in the classpath.
     */
    protected static class LoggingConfigurationListenerDiscoverer {
        /**
         * Scans the classpath for {@link LoggingConfigurationListener} implementations.
         * @param registry the registry where the listeners will be annotated.
         */
        public void discover(final LoggingConfigurationRegistry registry) {
            final ImplementingClassMatchProcessor<LoggingConfigurationListener> processor = implementingClass -> {
                try {
                    final LoggingConfigurationListener listener = implementingClass.newInstance();
                    registry.addListener(listener);
                } catch (final InstantiationException | IllegalAccessException error) {
                    // TODO: Deal with me
                }
            };
            final FastClasspathScanner scanner = new FastClasspathScanner();
            scanner.matchClassesImplementing(LoggingConfigurationListener.class, processor);
            scanner.scan();
        }
    }

    /**
     * Scans the classpath for {@link LoggingConfigurationListener}s.
     */
    protected final void discoverLoggingConfigurationProducers() {
        if (discoverLoggingConfigurationProducersEnabled()) {
            final LoggingConfigurationProducerAnnotationMatchProcessor processor =
                new LoggingConfigurationProducerAnnotationMatchProcessor(this);
            final FastClasspathScanner scanner = new FastClasspathScanner();
            scanner.matchClassesWithMethodAnnotation(LoggingConfigurationProducer.class, processor);
            scanner.scan();
        }
    }


    /**
     * Checks whether we are allowed to automatically discover the logging configuration producers.
     * @return {@code true} in such case.
     */
    protected boolean discoverLoggingConfigurationProducersEnabled() {
        return
            EnvironmentHelper.getInstance().retrieveBooleanFromSystemPropertyOrEnvironmentVariableOrElse(
                AUTOMATICALLY_DISCOVER_LOGGING_CONFIGURATION_PRODUCERS_PROPERTY,
                AUTOMATICALLY_DISCOVER_LOGGING_CONFIGURATION_PRODUCERS_ENVVAR,
                DEFAULT_AUTOMATICALLY_DISCOVER_LOGGING_CONFIGURATION_PRODUCERS);
    }

    /**
     * Base class for discover producers in the classpath.
     */
    protected static class LoggingConfigurationProducerAnnotationMatchProcessor
        implements MethodAnnotationMatchProcessor {

        /**
         * The registry.
         */
        private LoggingConfigurationRegistry registry;

        /**
         * Creates a net instance to use given registry.
         * @param registry the {@link LoggingConfigurationRegistry}.
         */
        public LoggingConfigurationProducerAnnotationMatchProcessor(final LoggingConfigurationRegistry registry) {
            immutableSetRegistry(registry);
        }

        /**
         * Specifies the registry to use.
         * @param registry such {@link LoggingConfigurationRegistry}.
         */
        protected final void immutableSetRegistry(final LoggingConfigurationRegistry registry) {
            this.registry = registry;
        }

        /**
         * Specifies the registry to use. Override me if necessary.
         * @param registry such {@link LoggingConfigurationRegistry}.
         */
        @SuppressWarnings("unused") protected void setRegistry(final LoggingConfigurationRegistry registry) {
            immutableSetRegistry(registry);
        }

        /**
         * Retrieves the registry.
         * @return such registry.
         */
        public LoggingConfigurationRegistry getRegistry() {
            return registry;
        }

        @Override
        public void processMatch(final Class<?> classWithAnnotation, final Executable matchingMethod) {
            processMatch(classWithAnnotation, matchingMethod, getRegistry());
        }

        /**
         * Process a match and checks whether to register it in given registry.
         * @param classWithAnnotation the class with the annotation.
         * @param matchingMethod the annotated method.
         * @param registry the registry.
         */
        protected void processMatch(
            final Class<?> classWithAnnotation,
            final Executable matchingMethod,
            final LoggingConfigurationRegistry registry) {
            try {
                final Object producer = classWithAnnotation.newInstance();
                if (matchingMethod instanceof Method) {
                    Object config = ((Method) matchingMethod).invoke(producer);
                    if (config instanceof LoggingConfiguration) {
                        final LoggingConfiguration loggingConfiguration = (LoggingConfiguration) config;
                        registry.put(loggingConfiguration.getRegistryKey(), loggingConfiguration);
                    } else {
                        // Invalid method signature.
                    }
                } else {
                    // Annotation on a constructor.
                }
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
