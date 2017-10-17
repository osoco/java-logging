# java-logging
A (yet another) Logging framework for Java / Groovy.

# Getting Started

This framework provides your application multiple logging capabilities hiding you the details of how it's working internally.
You express your logging preferences using the `@LoggingPreferences` annotation. Within that execution context, your preferences will be honored.
To log a message, you just need to call `LoggingFactory.getInstance().createLogging()`. It will return a `Logging` instance which gives you the methods you are expecting: `info(String)`, `debug(String)` and the like.

Besides that, the `Logging` instance also provides you a way to pass additional context information so that the logging mechanism can optionally use it. By calling `Logging#getLoggingContext()`, you get a `LoggingContext`, which is a Map-like API storing the information locally to the thread.

# Prerequisites

First, add Java-Logging as dependency in your `pom.xml` or `build.gradle`.

## Maven dependency

Currently we're in the process of releasing the pre-built binaries to jcenter(). The Maven dependency will be:

```
<dependency>
  <groupId>es.osoco.logging</groupId>
  <artifactId>java-logging</artifactId>
  <version>0.1</version>
</dependency>
```

## Gradle coordinates

Similarly, the Gradle coordinates are:

```
dependencies {
    compile(es.osoco.logging:java-logging:0.1)
}
```

# Usage

When your code needs to log anything, first import the required classes:
```
import es.osoco.logging.Logging;
import es.osoco.logging.LoggingFactory;
```

Then, retrieve the `Logging` instance using the `LoggingFactory`:
```
Logging logging = LogFactory.getLogging();
```

Once you have the `Logging` instance, use it for, well, logging:
```
logging.info("Use case started");
```

## Logging preferences

The underlying logging mechanisms are auto-discovered at runtime. However, you can
specify your preferences, by using the `@LoggingPreferences` annotation either in a method or a class.
Your preferences will define which logging will be used, in the execution context they are defined.
The general use case is to define your preferences in the application's entry points.
That way, they'll "stick" to all the code run within that execution flow.

For example, you could express your preferences in a `static void main()` method:
```
import es.osoco.logging.LoggingFactory;
import es.osoco.logging.annotations.LoggingPreferences;

public class EntryPoint {
    @LoggingPreferences(preferred="ElasticSearch", fallback="System.err")
    public static void main(String[] args) {
        LoggingFactory.getInstance().createLogging().info("Application started");
    }
}
```

## Building it yourself

To build the artifact(s) yourself, just install Maven (2 or 3) and run
```
mvn install
```

It will generate the artifacts under the `target/` folder.

# Running the tests

Java-Logging uses Spock as testing framework. To run the specifications, run

```
mvn test
```

# Contributing

Please read CONTRIBUTING.md for details on our code of conduct, and the process for submitting pull requests to us.

# Versioning

# Authors

# License

This project is licensed under the GPLv3 License - see the LICENSE.md file for details

# Acknowledgments

