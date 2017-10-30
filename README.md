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

The dependency is already in maven-central. Just add the dependency to your `pom.xml`.

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

While the underlying logging configuration are auto-discovered at runtime, you should
specify your preferences. You do so by using the `@LoggingPreferences` annotation either in a method or a class.
Your preferences will define which logging mechanism will be used, within the execution context they are defined.
The general use case is to define your preferences in the application's entry point(s).
That way, they'll "stick" to all the code running within that execution flow.

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

### A more complex scenario

Let's say your code is modelled after DDD principles, and at the application level you have
different services or use cases interacting with your domain classes.
For illustration purposes, let's say your application is listening both RabbitMQ messages, and HTTP requests for a REST API routed by an AWS API Gateway, spawning an AWS Lambda with your code.

```
public class RabbitMQAdapter implements ...
...
    public void onMessage(...) {
        // run the use case
    }
...
}
```
```
public class HttpServerAdapter implements ...
...
    public void onGet(...) {
        // run the use case
    }
...
}
```

In this scenario, let's say you'd like to log differently depending on the adapter:  when the application receives messages from the RabbitMQ queue, you want to send the logs to a ElasticSearch server, whereas the Lambda should use the AWS-Lambda built-in logging mechanism itself.

You can accomplish that using `@LoggingPreferences` annotations:
```
@LoggingPreferences(preferred="ElasticSearch")
public class RabbitMQAdapter implements ...
```
```
@LoggingPreferences(preferred="aws-lambda")
public class HttpServerAdapter implements ...
```

# Design concepts

This library uses two concepts: Logging Configuration, and Logging adapters. Logging Configurations are abstractions to represent required configurations needed by Logging Adapters, but they don't know who uses them. Logging Adapters are the materializations of the `Logging` interface the client uses at runtime.


## Logging Configuration

Logging Configurations are responsible to provide whatever information is needed for a Logging Adapter to work correctly. It includes file paths, remote server IPs and ports, and so on. They include the necessary checks as well. For example, file permissions, if the remote servers are up and running, etc.

Logging Configurations can be explicitly defined, and automatically discovered.

If your application can provide logging configuration automatically by itself, wrap that logic in a method, and annotate it with a `@LoggingConfigurationProducer` annotation.
For example:
```
    @LoggingConfigurationProducer(key="logstash")
    public LoggingConfiguration initLogging() {
        return LogstashLoggingConfiguration("logstash", ...);
    }
```
The first time the library is loaded, it will auto-discover all `@LoggingConfigurationProducer` annotations, run the annotated methods, and collect the results into `LoggingConfigurationRegistry`.

The `LoggingConfigurationRegistry` discovers all child classes of `LoggingConfigurationListener`, and notifies them whenever a new `LoggingConfiguration` is collected.

## Logging Adapters

The main responsibility of a `LoggingConfigurationListener` is to check if the new `LoggingConfiguration` is meaningful for him. If so, it will create a `LoggingAdapterBuilder` instance, and publish it into the `LoggingAdapterBuilderRegistry`.

Such registry simply maps keys with logging adapter builders. Builders know how to build `LoggingAdapter`s using the `LoggingConfiguration` information.

## LoggingFactory

The `LoggingFactory` resolves which logging keys are bound to the runtime context, based on the current stack trace. Once that keys (/preferred/ and /fallback/) are known, it creates a composite instance after asking the relevant builders to create the `LoggingAdapter`s. That composite logging then delegates the actual logging calls to the adapters: the log message is broadcasted to all preferred mechanisms. In case any of them fails, the same message is broadcasted to all fallback mechanisms.

# Build from source

To build the artifact(s) yourself, just install Maven (2 or 3) and run:

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

Shibata team at OSOCO.

# License

This project is licensed under the GPLv3 License - see the LICENSE.md file for details

# Acknowledgments

The idea behind this library arose spontaneously after a refactoring of a DDD project. It used its own AWS-Lambda-based Logging, and it
modelled it after the ports-and-adapters approach borrowed from hexagonal architectures.
We wanted to reuse that module in other contexts, so we stole the Logging code and started implementing this library.
However, the design was heavily influenced by Pharo (a Smalltalk dialect we at OSOCO are big fans of), and was made possible thanks to the awesome fast-classpath-scanner library from lukehutch.
