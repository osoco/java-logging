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

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

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
        @NonNull
        public static final EnvironmentHelper SINGLETON = new EnvironmentHelper();
    }

    protected EnvironmentHelper() {}

    /**
     * Retrieves the singleton instance.
     * @return such instance.
     */
    @NonNull
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
    @Nullable
    public String retrieveStringFromSystemPropertyOrEnvironmentVariableOrElse(
        @NonNull final String sysPropName, @NonNull final String envVarName, @Nullable final String defaultValue) {

        @Nullable final String result;

        @Nullable final String sysProp = System.getProperty(sysPropName);

        if (sysProp == null) {
            @Nullable final String envVar = System.getenv(envVarName);

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
     * Retrieves a String value from System property (-DsysPropName). If it's not found,
     * try the environment variable envVarName. If it's still not found, return the defaultValue.
     * @param sysPropName the system property name.
     * @param envVarName the environment variable name.
     * @param defaultValue the default value.
     * @return such value.
     */
    @NonNull
    public String[] retrieveStringArrayFromSystemPropertyOrEnvironmentVariableOrElse(
        @NonNull final String sysPropName, @NonNull final String envVarName, @NonNull final String[] defaultValue) {

        @NonNull final String[] result;

        @Nullable final String aux;

        @Nullable final String sysProp = System.getProperty(sysPropName);

        if (sysProp == null) {
            final String envVar = System.getenv(envVarName);

            if (envVar == null) {
                aux = null;
            } else {
                aux = envVar;
            }
        } else {
            aux = sysProp;
        }

        if (aux == null) {
            result = defaultValue;
        } else {
            result = aux.split(",");
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
        @NonNull final String sysPropName, @NonNull final String envVarName, final int defaultValue) {
        int result;

        @Nullable final String value =
            retrieveStringFromSystemPropertyOrEnvironmentVariableOrElse(sysPropName, envVarName, (String) null);

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

    /**
     * Retrieves a boolean value from System property (-DsysPropName). If it's not found,
     * try the environment variable envVarName. If it's still not found, return the defaultValue.
     * @param sysPropName the system property name.
     * @param envVarName the environment variable name.
     * @param defaultValue the default value.
     * @return such value.
     */
    public boolean retrieveBooleanFromSystemPropertyOrEnvironmentVariableOrElse(
        @NonNull final String sysPropName, @NonNull final String envVarName, final boolean defaultValue) {

        final boolean result;

        @Nullable final String value =
            retrieveStringFromSystemPropertyOrEnvironmentVariableOrElse(sysPropName, envVarName, (String) null);

        if (value == null) {
            result = defaultValue;
        } else {
            result = Boolean.valueOf(value);
        }

        return result;
    }
}
