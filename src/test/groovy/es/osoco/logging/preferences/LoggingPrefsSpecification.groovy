package es.osoco.logging.preferences

import spock.lang.Specification


class LoggingPrefsSpecification extends Specification {

    def "LoggingPreferences can extract the class name of a stack trace element"() {
        setup:
        def prefs = LoggingPrefs.getInstance()
        def stackTrace = new StackTraceElement(String.class.getName(), "toString", "file", 13)
        String[] preferred = new String[1]
        preferred[0] = "abc"
        prefs.addMethodPreferred(stackTrace, preferred)

        expect:
        prefs.findPreferred(stackTrace, null) != null
        prefs.findPreferred(stackTrace, null).size() == 1
        prefs.findPreferred(stackTrace, null)[0] == "abc"
    }

}
