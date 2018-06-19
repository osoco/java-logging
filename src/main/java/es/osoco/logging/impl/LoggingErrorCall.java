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
package es.osoco.logging.impl;

import es.osoco.logging.adapter.LoggingAdapter;
import org.checkerframework.checker.nullness.qual.NonNull;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Calls #error(msg) and #isErrorEnabled() on a given adapter.
 */
@EqualsAndHashCode
@ToString
public class LoggingErrorCall
    implements LoggingCall {

    @Override
    public void log(@NonNull final LoggingAdapter adapter, @NonNull final String msg) {
        adapter.error(msg);
    }

    @Override
    public void log(@NonNull final LoggingAdapter adapter, @NonNull final String category, @NonNull final String msg) {
        adapter.error(category, msg);
    }

    @Override
    public boolean isEnabled(@NonNull final LoggingAdapter adapter) {
        return adapter.isErrorEnabled();
    }

    @Override
    public boolean isEnabled(@NonNull final LoggingAdapter adapter, @NonNull final String category) {
        return adapter.isErrorEnabled(category);
    }
}
