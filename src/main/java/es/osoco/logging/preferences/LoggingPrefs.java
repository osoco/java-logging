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

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.github.lukehutch.fastclasspathscanner.matchprocessor.ClassAnnotationMatchProcessor;
import io.github.lukehutch.fastclasspathscanner.matchprocessor.MethodAnnotationMatchProcessor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Executable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Finds out the logging preferences in the context of the caller.
 */
public class LoggingPrefs {

    /**
     * The default preferred logging (System.out).
     */
    static final String[] DEFAULT_PREFERRED = new String[] { "System.out" };

    /**
     * The default fallback logging (System.out).
     */
    static final String[] DEFAULT_FALLBACK = new String[] { "System.out" };

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
        final ClassLoggingAnnotationMatchProcessor classProcessor =
            new ClassLoggingAnnotationMatchProcessor(this);
        final MethodLoggingAnnotationMatchProcessor methodProcessor =
            new MethodLoggingAnnotationMatchProcessor(this);
        final FastClasspathScanner scanner = new FastClasspathScanner();
        scanner.matchClassesWithMethodAnnotation(es.osoco.logging.annotations.LoggingPreferences.class, methodProcessor);
        scanner.matchClassesWithAnnotation(es.osoco.logging.annotations.LoggingPreferences.class, classProcessor);
        scanner.scan();
    }

    /**
     * Retrieves the preferred logging of current context.
     * @return the ordered array of {@link es.osoco.logging.adapter.LoggingAdapterBuilderRegistry} keys.
     */
    public String[] myPreferredLogging() {
        return findPreferred(Thread.currentThread().getStackTrace());
    }

    /**
     * Retrieves the fallback logging of current context.
     * @return the ordered array of {@link es.osoco.logging.adapter.LoggingAdapterBuilderRegistry} keys.
     */
    public String[] myFallbackLogging() {
        return findFallback(Thread.currentThread().getStackTrace());
    }

    /**
     * Adds a new class-specific logging preferences.
     * @param className the class name.
     * @param prefs the preferences.
     */
    public void addClassPreferred(final String className, final String[] prefs) {
        getClassPreferred().put(className, prefs);
    }

    /**
     * Adds a new class-specific logging fallback.
     * @param className the class name.
     * @param prefs the preferences.
     */
    public void addClassFallback(final String className, final String[] prefs) {
        getClassFallback().put(className, prefs);
    }

    /**
     * Builds a method key.
     * @param clazz the class name.
     * @param method the method name.
     * @return the key.
     */
    protected String buildMethodKey(final String clazz, final String method) {
        return clazz + "::" + method;
    }

    /**
     * Adds a new method-specific logging preferences.
     * @param element the stack trace element.
     * @param prefs the preferences.
     */
    public void addMethodPreferred(final StackTraceElement element, final String[] prefs) {
        addMethodPreferred(buildMethodKey(element.getClassName(), element.getMethodName()), prefs);
    }

    /**
     * Adds a new method-specific logging preferences.
     * @param key the method key.
     * @param prefs the preferences.
     */
    protected void addMethodPreferred(final String key, final String[] prefs) {
        getMethodPreferred().put(key, prefs);
    }

    /**
     * Adds a new method-specific logging fallback.
     * @param key the method key.
     * @param prefs the preferences.
     */
    public void addMethodFallback(final String key, final String[] prefs) {
        getMethodFallback().put(key, prefs);
    }

    /**
     * Finds the preferences for given stack trace.
     * @param stackTrace the stack trace.
     * @return the preferred logging keys.
     */
    protected String[] findPreferred(final StackTraceElement[] stackTrace) {
        return find(stackTrace, getClassPreferred(), getMethodPreferred(), DEFAULT_PREFERRED);
    }

    /**
     * Finds the fallback for given stack trace.
     * @param stackTrace the stack trace.
     * @return the fallback logging keys.
     */
    protected String[] findFallback(final StackTraceElement[] stackTrace) {
        return find(stackTrace, getClassFallback(), getMethodFallback(), DEFAULT_FALLBACK);
    }

    /**
     * Finds the preferences for given stack trace.
     * @param stackTrace the stack trace.
     * @param discoveredClasses the already-discovered classes.
     * @param discoveredMethods the already-discovered methods.
     * @param defaultValue the default value.
     * @return the preferred logging keys.
     */
    protected String[] find(
        final StackTraceElement[] stackTrace,
        final Map<String, String[]> discoveredClasses,
        final Map<String, String[]> discoveredMethods,
        final String[] defaultValue) {

        String[] result = defaultValue;

        for (final StackTraceElement stackElement : stackTrace) {
            final String[] prefs = findElement(stackElement, discoveredClasses, discoveredMethods, null);
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
    protected String[] findElement(
        final StackTraceElement stackElement,
        final Map<String, String[]> discoveredClasses,
        final Map<String, String[]> discoveredMethods,
        final String[] defaultValue) {
        final String[] result;

        final String className = stackElement.getClassName();
        final String method = stackElement.getMethodName();
        final String[] methodPrefs = discoveredMethods.get(buildMethodKey(className, method));
        if (methodPrefs != null) {
            result = methodPrefs;
        } else {
            final String[] classPrefs = discoveredClasses.get(className);
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
    protected abstract static class AbstractLoggingAnnotationMatchProcessor {

        /**
         * The preferences.
         */
        private LoggingPrefs preferences;

        /**
         * Creates a new instance to use given preferences.
         * @param prefs the {@link LoggingPrefs}.
         */
        public AbstractLoggingAnnotationMatchProcessor(final LoggingPrefs prefs) {
            immutableSetPreferences(prefs);
        }

        /**
         * Specifies the proferences to use.
         * @param prefs such {@link LoggingPrefs}.
         */
        protected final void immutableSetPreferences(final LoggingPrefs prefs) {
            this.preferences = prefs;
        }

        /**
         * Specifies the preferences to use. Override me if necessary.
         * @param prefs such {@link LoggingPrefs}.
         */
        @SuppressWarnings("unused") protected void setPreferences(final LoggingPrefs prefs) {
            immutableSetPreferences(prefs);
        }

        /**
         * Retrieves the preferences.
         * @return such preferences.
         */
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
            final Class<?> clazz, final String key, final Annotation annotation, final LoggingPrefs prefs) {
            try {
                final Class<? extends Annotation> type = annotation.annotationType();
                final Method preferredAccessor = type.getDeclaredMethod("preferred");
                final String[] preferred = (String[]) preferredAccessor.invoke(annotation);
                final Method fallbackAccessor = type.getDeclaredMethod("fallback");
                final String[] fallback = (String[]) fallbackAccessor.invoke(annotation);
                processMatch(clazz, key, preferred, fallback, prefs);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
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
            Class<?> clazz, String key, String[] preferred, String[] fallback, LoggingPrefs prefs);
    }

    /**
     * Scans the stack trace looking for annotated methods.
     */
    protected static class MethodLoggingAnnotationMatchProcessor
        extends AbstractLoggingAnnotationMatchProcessor
        implements MethodAnnotationMatchProcessor {

        /**
         * Creates a new instance to use given preferences.
         * @param prefs the {@link LoggingPrefs}.
         */
        public MethodLoggingAnnotationMatchProcessor(final LoggingPrefs prefs) {
            super(prefs);
        }

        @Override
        public void processMatch(final Class<?> annotatedClass, final Executable method) {
            processMatch(annotatedClass, method, getPreferences());
        }

        /**
         * Processes a match.
         * @param annotatedClass the class with the annotated method.
         * @param method the method.
         * @param prefs the {@link LoggingPrefs}.
         */
        protected void processMatch(
            final Class<?> annotatedClass, final Executable method, final LoggingPrefs prefs) {
            match(
                annotatedClass,
                prefs.buildMethodKey(annotatedClass.getName(), method.getName()),
                method.getAnnotation(es.osoco.logging.annotations.LoggingPreferences.class),
                prefs);
        }

        @Override
        protected void processMatch(
            final Class<?> className,
            final String key,
            final String[] preferred,
            final String[] fallback,
            final LoggingPrefs prefs) {
            prefs.addMethodPreferred(key, preferred);
            prefs.addMethodFallback(key, fallback);
        }
    }

    /**
     * Scans the stack trace looking for annotated classes.
     */
    protected static class ClassLoggingAnnotationMatchProcessor
        extends AbstractLoggingAnnotationMatchProcessor
        implements ClassAnnotationMatchProcessor {

        /**
         * Creates a new instance to use given preferences.
         * @param prefs the {@link LoggingPrefs}.
         */
        public ClassLoggingAnnotationMatchProcessor(final LoggingPrefs prefs) {
            super(prefs);
        }

        @Override
        public void processMatch(final Class<?> annotatedClass) {
            match(
                annotatedClass, annotatedClass.getName(), annotatedClass.getAnnotation(es.osoco.logging.annotations.LoggingPreferences.class), getPreferences());
        }

        @Override
        protected void processMatch(
            final Class<?> annotatedClass,
            final String key,
            final String[] preferred,
            final String[] fallback,
            final LoggingPrefs prefs) {
            prefs.addClassPreferred(key, preferred);
            prefs.addClassFallback(key, fallback);
        }
    }
}
