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
