package es.osoco.logging.helper;

/**
 * Utility methods when dealing with environment variables and system properties.
 */
public class EnvironmentHelper {

    /**
     * Singleton implemented to avoid double-check locking.
     */
    protected static final class EnvironmentHelperSingletonContainer {
        /**
         * The singleton instance.
         */
        public static final EnvironmentHelper SINGLETON = new EnvironmentHelper();
    }

    protected EnvironmentHelper() {}

    /**
     * Retrieves the singleton instance.
     * @return such instance.
     */
    public static EnvironmentHelper getInstance() {
        return EnvironmentHelperSingletonContainer.SINGLETON;
    }

    /**
     * Retrieves a String value from System property (-DsysPropName). If it's not found,
     * try the environment variable envVarName. If it's still not found, return the defaultValue.
     * @param sysPropName the system property name.
     * @param envVarName the environment variable name.
     * @param defaultValue the default value.
     * @return such value.
     */
    public String retrieveStringFromSystemPropertyOrEnvironmentVariableOrElse(
        final String sysPropName, final String envVarName, final String defaultValue) {
        final String result;

        final String sysProp = System.getProperty(sysPropName);

        if (sysProp == null) {
            final String envVar = System.getenv(envVarName);

            if (envVar == null) {
                result = defaultValue;
            } else {
                result = envVar;
            }
        } else {
            result = sysProp;
        }

        return result;
    }

    /**
     * Retrieves a integer value from System property (-DsysPropName). If it's not found,
     * try the environment variable envVarName. If it's still not found, return the defaultValue.
     * @param sysPropName the system property name.
     * @param envVarName the environment variable name.
     * @param defaultValue the default value.
     * @return such value.
     */
    public int retrieveIntFromSystemPropertyOrEnvironmentVariableOrElse(
        final String sysPropName, final String envVarName, final int defaultValue) {
        int result;

        final String value =
            retrieveStringFromSystemPropertyOrEnvironmentVariableOrElse(sysPropName, envVarName, null);

        if (value == null) {
            result = defaultValue;
        } else {
            try {
                result = Integer.parseInt(value);
            } catch (final NumberFormatException invalidInt) {
                result = defaultValue;
            }
        }

        return result;
    }
}
