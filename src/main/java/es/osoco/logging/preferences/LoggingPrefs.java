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
package es.osoco.logging.preferences;

import es.osoco.logging.helper.EnvironmentHelper;
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.github.lukehutch.fastclasspathscanner.matchprocessor.ClassAnnotationMatchProcessor;
import io.github.lukehutch.fastclasspathscanner.matchprocessor.MethodAnnotationMatchProcessor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Executable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * <p>Finds out the logging preferences in the context of the caller.</p>
 * <p>I will traverse the stack trace for any logging annotation, unless either
 * "automatically.discover.logging.annotations" property or
 * "AUTOMATICALLY_DISCOVER_LOGGING_ANNOTATIONS" environment variable is set to "false".</p>
 * <p>In that case, the default preferred and default fallback mechanisms will be used.</p>
 * <p>Such mechanisms are "System.out" and "System.err" respectively, by default, but can be
 * overridden using "default.preferred.logging" property or "DEFAULT_PREFERRED_LOGGING"
 * environment variable.</p>
 * <p>Notice the default preferred and default fallback mechanisms are used also when
 * no annotations are available in the Thread's stack trace.</p>
 */
public class LoggingPrefs {

    /**
     * The default preferred logging (System.out).
     */
    static final String[] DEFAULT_PREFERRED = new String[] { "System.out" };

    /**
     * The default fallback logging (System.err).
     */
    static final String[] DEFAULT_FALLBACK = new String[] { "System.err" };

    /**
     * The property to enable or disable automatically discovering of logging annotations.
     */
    public static final String AUTOMATICALLY_DISCOVER_LOGGING_ANNOTATIONS_PROPERTY =
        "automatically.discover.logging.annotations";

    /**
     * The environment variable to enable or disable automatically discovering of logging annotations.
     */
    public static final String AUTOMATICALLY_DISCOVER_LOGGING_ANNOTATIONS_ENVVAR =
        "AUTOMATICALLY_DISCOVER_LOGGING_ANNOTATIONS";

    /**
     * The default behavior for automatically discovering of logging annotations.
     */
    public static final boolean DEFAULT_AUTOMATICALLY_DISCOVER_LOGGING_ANNOTATIONS = true;

    /**
     * The property to override the preferred logging mechanism.
     */
    public static final String DEFAULT_PREFERRED_LOGGING_PROPERTY =
        "default.preferred.logging";

    /**
     * The environment variable to override the preferred logging mechanism.
     */
    public static final String DEFAULT_PREFERRED_LOGGING_ENVVAR =
        "DEFAULT_PREFERRED_LOGGING";

    /**
     * The property to override the fallback logging mechanism.
     */
    public static final String DEFAULT_FALLBACK_LOGGING_PROPERTY =
        "default.fallback.logging";

    /**
     * The environment variable to override the fallback logging mechanism.
     */
    public static final String DEFAULT_FALLBACK_LOGGING_ENVVAR =
        "DEFAULT_FALLBACK_LOGGING";

    /**
     * The discovered per-class preferred logging.
     */
    public final Map<String, String[]> classPreferred = new HashMap<>();

    /**
     * The discovered per-method preferred logging.
     */
    public final Map<String, String[]> methodPreferred = new HashMap<>();

    /**
     * The discovered per-class fallback logging.
     */
    public final Map<String, String[]> classFallback = new HashMap<>();

    /**
     * The discovered per-method fallback logging.
     */
    public final Map<String, String[]> methodFallback = new HashMap<>();

    /**
     * Singleton implemented to avoid double-check locking.
     */
    protected static class LoggingPreferencesSingletonContainer {
        /**
         * The singleton implementation.
         */
        public static final LoggingPrefs SINGLETON = new LoggingPrefs();
    }

    /**
     * Creates a new instance.
     */
    protected LoggingPrefs() {
        discoverLoggingAnnotations();
    }

    /**
     * Retrieves the singleton instance.
     * @return such instance.
     */
    public static LoggingPrefs getInstance() {
        return LoggingPreferencesSingletonContainer.SINGLETON;
    }

    /**
     * Retrieves the class preferred logging.
     * @return such preferences.
     */
    protected Map<String, String[]> getClassPreferred() {
        return this.classPreferred;
    }

    /**
     * Retrieves the method preferred logging.
     * @return such preferences.
     */
    protected Map<String, String[]> getMethodPreferred() {
        return this.methodPreferred;
    }

    /**
     * Retrieves the class fallback logging.
     * @return such preferences.
     */
    protected Map<String, String[]> getClassFallback() {
        return this.classFallback;
    }

    /**
     * Retrieves the method fallback logging.
     * @return such preferences.
     */
    protected Map<String, String[]> getMethodFallback() {
        return this.methodFallback;
    }

    /**
     * Scans the classpath for {@code @LoggingPrefs} annotations.
     */
    protected final void discoverLoggingAnnotations() {
        if (discoverLoggingAnnotationsEnabled()) {
            final ClassLoggingAnnotationMatchProcessor classProcessor =
                new ClassLoggingAnnotationMatchProcessor(this);
            final MethodLoggingAnnotationMatchProcessor methodProcessor =
                new MethodLoggingAnnotationMatchProcessor(this);
            final FastClasspathScanner scanner = new FastClasspathScanner();
            scanner.matchClassesWithMethodAnnotation(
                es.osoco.logging.annotations.LoggingPreferences.class, methodProcessor);
            scanner.matchClassesWithAnnotation(
                es.osoco.logging.annotations.LoggingPreferences.class, classProcessor);
            scanner.scan();
        }
    }

    /**
     * Checks whether we are allowed to automatically discover the logging annotations.
     * @return {@code true} in such case.
     */
    protected boolean discoverLoggingAnnotationsEnabled() {
        return
            EnvironmentHelper.getInstance().retrieveBooleanFromSystemPropertyOrEnvironmentVariableOrElse(
                AUTOMATICALLY_DISCOVER_LOGGING_ANNOTATIONS_PROPERTY,
                AUTOMATICALLY_DISCOVER_LOGGING_ANNOTATIONS_ENVVAR,
                DEFAULT_AUTOMATICALLY_DISCOVER_LOGGING_ANNOTATIONS);
    }

    /**
     * Retrieves the default preferred mechanism.
     * @return such mechanism.
     */
    @NonNull
    protected String[] getDefaultPreferred() {
        return
            EnvironmentHelper.getInstance().retrieveStringArrayFromSystemPropertyOrEnvironmentVariableOrElse(
                DEFAULT_PREFERRED_LOGGING_PROPERTY,
                DEFAULT_PREFERRED_LOGGING_ENVVAR,
                DEFAULT_PREFERRED);
    }

    /**
     * Retrieves the default fallback mechanism.
     * @return such mechanism.
     */
    @NonNull
    protected String[] getDefaultFallback() {
        return
            EnvironmentHelper.getInstance().retrieveStringArrayFromSystemPropertyOrEnvironmentVariableOrElse(
                DEFAULT_FALLBACK_LOGGING_PROPERTY,
                DEFAULT_FALLBACK_LOGGING_ENVVAR,
                DEFAULT_FALLBACK);
    }

    /**
     * Retrieves the preferred logging of current context.
     * @return the ordered array of {@link es.osoco.logging.adapter.LoggingAdapterBuilderRegistry} keys.
     */
    @NonNull
    public String[] myPreferredLogging() {

        @NonNull final String[] result;

        @Nullable final String[] aux;

        if (discoverLoggingAnnotationsEnabled()) {
            aux = findPreferred(Thread.currentThread().getStackTrace());
        } else {
            aux = null;
        }

        if (aux == null) {
            result = getDefaultPreferred();
        } else {
            result = aux;
        }

        return result;
    }

    /**
     * Retrieves the fallback logging of current context.
     * @return the ordered array of {@link es.osoco.logging.adapter.LoggingAdapterBuilderRegistry} keys.
     */
    @NonNull
    public String[] myFallbackLogging() {
        @NonNull final String[] result;

        @Nullable final String[] aux;

        if (discoverLoggingAnnotationsEnabled()) {
            aux = findFallback(Thread.currentThread().getStackTrace());
        } else {
            aux = null;
        }

        if (aux == null) {
            result = getDefaultFallback();
        } else {
            result = aux;
        }

        return result;
    }

    /**
     * Adds a new class-specific logging preferences.
     * @param className the class name.
     * @param prefs the preferences.
     */
    public void addClassPreferred(@NonNull final String className, @NonNull final String[] prefs) {
        getClassPreferred().put(className, prefs);
    }

    /**
     * Adds a new class-specific logging fallback.
     * @param className the class name.
     * @param prefs the preferences.
     */
    public void addClassFallback(@NonNull final String className, @NonNull final String[] prefs) {
        getClassFallback().put(className, prefs);
    }

    /**
     * Builds a method key.
     * @param clazz the class name.
     * @param method the method name.
     * @return the key.
     */
    protected String buildMethodKey(@NonNull final String clazz, @NonNull final String method) {
        return clazz + "::" + method;
    }

    /**
     * Adds a new method-specific logging preferences.
     * @param element the stack trace element.
     * @param prefs the preferences.
     */
    public void addMethodPreferred(@NonNull final StackTraceElement element, @NonNull final String[] prefs) {
        addMethodPreferred(buildMethodKey(element.getClassName(), element.getMethodName()), prefs);
    }

    /**
     * Adds a new method-specific logging preferences.
     * @param key the method key.
     * @param prefs the preferences.
     */
    protected void addMethodPreferred(@NonNull final String key, @NonNull final String[] prefs) {
        getMethodPreferred().put(key, prefs);
    }

    /**
     * Adds a new method-specific logging fallback.
     * @param key the method key.
     * @param prefs the preferences.
     */
    public void addMethodFallback(@NonNull final String key, @NonNull final String[] prefs) {
        getMethodFallback().put(key, prefs);
    }

    /**
     * Finds the preferences for given stack trace.
     * @param stackTrace the stack trace.
     * @return the preferred logging keys.
     */
    @Nullable
    protected String[] findPreferred(final StackTraceElement[] stackTrace) {
        return find(stackTrace, getClassPreferred(), getMethodPreferred(), getDefaultPreferred());
    }

    /**
     * Finds the fallback for given stack trace.
     * @param stackTrace the stack trace.
     * @return the fallback logging keys.
     */
    @Nullable
    protected String[] findFallback(final StackTraceElement[] stackTrace) {
        return find(stackTrace, getClassFallback(), getMethodFallback(), getDefaultFallback());
    }

    /**
     * Finds the preferences for given stack trace.
     * @param stackTrace the stack trace.
     * @param discoveredClasses the already-discovered classes.
     * @param discoveredMethods the already-discovered methods.
     * @param defaultValue the default value.
     * @return the preferred logging keys.
     */
    @Nullable
    protected String[] find(
        @NonNull final StackTraceElement[] stackTrace,
        @NonNull final Map<String, String[]> discoveredClasses,
        @NonNull final Map<String, String[]> discoveredMethods,
        @NonNull final String[] defaultValue) {

        @Nullable String[] result = defaultValue;

        for (@NonNull final StackTraceElement stackElement : stackTrace) {
            @Nullable final String[] prefs =
                findElement(stackElement, discoveredClasses, discoveredMethods, null);
            if (prefs != null) {
                result = prefs;
                break;
            }
        }

        return result;
    }

    /**
     * Finds the preferences for given stack trace element.
     * @param stackElement the stack trace.
     * @param discoveredClasses the already-discovered classes.
     * @param discoveredMethods the already-discovered methods.
     * @param defaultValue the default values if none found.
     * @return the preferred logging keys.
     */
    @Nullable
    protected String[] findElement(
        @NonNull final StackTraceElement stackElement,
        @NonNull final Map<String, String[]> discoveredClasses,
        @NonNull final Map<String, String[]> discoveredMethods,
        @Nullable final String[] defaultValue) {

        @Nullable final String[] result;

        @NonNull final String className = stackElement.getClassName();
        @NonNull final String method = stackElement.getMethodName();
        @Nullable final String[] methodPrefs = discoveredMethods.get(buildMethodKey(className, method));
        if (methodPrefs != null) {
            result = methodPrefs;
        } else {
            @Nullable final String[] classPrefs = discoveredClasses.get(className);
            if (classPrefs != null) {
                result = classPrefs;
            } else {
                result = defaultValue;
            }
        }

        return result;
    }

    /**
     * Base class for the inline processors.
     */
    @EqualsAndHashCode
    @ToString
    protected abstract static class AbstractLoggingAnnotationMatchProcessor {

        /**
         * The preferences.
         */
        @NonNull
        private LoggingPrefs preferences;

        /**
         * Creates a new instance to use given preferences.
         * @param prefs the {@link LoggingPrefs}.
         */
        public AbstractLoggingAnnotationMatchProcessor(@NonNull final LoggingPrefs prefs) {
            this.preferences = prefs;
        }

        /**
         * Specifies the proferences to use.
         * @param prefs such {@link LoggingPrefs}.
         */
        protected final void immutableSetPreferences(@NonNull final LoggingPrefs prefs) {
            this.preferences = prefs;
        }

        /**
         * Specifies the preferences to use. Override me if necessary.
         * @param prefs such {@link LoggingPrefs}.
         */
        @SuppressWarnings("unused") protected void setPreferences(@NonNull final LoggingPrefs prefs) {
            immutableSetPreferences(prefs);
        }

        /**
         * Retrieves the preferences.
         * @return such preferences.
         */
        @NonNull
        public LoggingPrefs getPreferences() {
            return preferences;
        }

        /**
         * Process a match and checks whether to register it in given preferences.
         * @param clazz the class.
         * @param key the key (the class name or the method name).
         * @param annotation the annotation.
         * @param prefs the preferences.
         */
        protected void match(
            @NonNull final Class<?> clazz,
            @NonNull final String key,
            @NonNull final Annotation annotation,
            @NonNull final LoggingPrefs prefs) {

            try {
                final Class<? extends Annotation> type = annotation.annotationType();
                final Method preferredAccessor = type.getDeclaredMethod("preferred");
                final String[] preferred = (String[]) preferredAccessor.invoke(annotation);
                final Method fallbackAccessor = type.getDeclaredMethod("fallback");
                final String[] fallback = (String[]) fallbackAccessor.invoke(annotation);
                processMatch(clazz, key, preferred, fallback, prefs);

            } catch (@NonNull final IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        /**
         * Processes the match.
         * @param clazz the class.
         * @param key the key.
         * @param preferred the preferred value.
         * @param fallback the fallback value.
         * @param prefs the {@link LoggingPrefs} instance.
         */
        protected abstract void processMatch(
            @NonNull Class<?> clazz,
            @NonNull String key,
            @NonNull String[] preferred,
            @NonNull String[] fallback,
            @NonNull LoggingPrefs prefs);
    }

    /**
     * Scans the stack trace looking for annotated methods.
     */
    @EqualsAndHashCode(callSuper = true)
    @ToString
    protected static class MethodLoggingAnnotationMatchProcessor
        extends AbstractLoggingAnnotationMatchProcessor
        implements MethodAnnotationMatchProcessor {

        /**
         * Creates a new instance to use given preferences.
         * @param prefs the {@link LoggingPrefs}.
         */
        public MethodLoggingAnnotationMatchProcessor(@NonNull final LoggingPrefs prefs) {
            super(prefs);
        }

        @Override
        public void processMatch(@NonNull final Class<?> annotatedClass, @NonNull final Executable method) {
            processMatch(annotatedClass, method, getPreferences());
        }

        /**
         * Processes a match.
         * @param annotatedClass the class with the annotated method.
         * @param method the method.
         * @param prefs the {@link LoggingPrefs}.
         */
        protected void processMatch(
            @NonNull final Class<?> annotatedClass, @NonNull final Executable method, @NonNull final LoggingPrefs prefs) {
            match(
                annotatedClass,
                prefs.buildMethodKey(annotatedClass.getName(), method.getName()),
                method.getAnnotation(es.osoco.logging.annotations.LoggingPreferences.class),
                prefs);
        }

        @Override
        protected void processMatch(
            @NonNull final Class<?> className,
            @NonNull final String key,
            @NonNull final String[] preferred,
            @NonNull final String[] fallback,
            @NonNull final LoggingPrefs prefs) {

            prefs.addMethodPreferred(key, preferred);
            prefs.addMethodFallback(key, fallback);
        }
    }

    /**
     * Scans the stack trace looking for annotated classes.
     */
    @EqualsAndHashCode(callSuper = true)
    @ToString
    protected static class ClassLoggingAnnotationMatchProcessor
        extends AbstractLoggingAnnotationMatchProcessor
        implements ClassAnnotationMatchProcessor {

        /**
         * Creates a new instance to use given preferences.
         * @param prefs the {@link LoggingPrefs}.
         */
        public ClassLoggingAnnotationMatchProcessor(@NonNull final LoggingPrefs prefs) {
            super(prefs);
        }

        @Override
        public void processMatch(@NonNull final Class<?> annotatedClass) {
            match(
                annotatedClass,
                annotatedClass.getName(),
                annotatedClass.getAnnotation(es.osoco.logging.annotations.LoggingPreferences.class),
                getPreferences());
        }

        @Override
        protected void processMatch(
            @NonNull final Class<?> annotatedClass,
            @NonNull final String key,
            @NonNull final String[] preferred,
            @NonNull String[] fallback,
            @NonNull final LoggingPrefs prefs) {
            prefs.addClassPreferred(key, preferred);
            prefs.addClassFallback(key, fallback);
        }
    }
}
