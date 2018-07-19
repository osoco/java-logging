# java-logging
A (yet another) Logging framework for Java / Groovy.

# Getting Started

This framework provides your application multiple logging capabilities, while hiding you the details of how it's working internally.

You express your logging preferences using the `@LoggingPreferences` annotation. Within that execution context, your preferences will be honored.
To log a message, you just need to call `LoggingFactory.getInstance().createLogging()`. It will return a `Logging` instance which gives you the methods you are expecting: `info(String)`, `debug(String)` and the like.

Besides that, the `Logging` instance also provides you a way to pass additional context information so that the logging mechanism can optionally use it. By calling `Logging#getLoggingContext()`, you get a `LoggingContext`, which is a Map-like API storing the information locally to the thread.

# Why another logging library

This library allows your code to express its logging preferences via annotations, and then use a minimal API.
By preferences, we mean "if possible, use ElasticSearch and Log4J. If any of them fails, then use SLF4J and System.out".

In practice, we've found that using DDD allowed us to reuse the code in different scenarios. Frequently, those scenarios
differ in the runtime infrastructure. Some of them use ElasticSearch, some of them delegate logging to CloudWatch (via AWS-Lambda logger).
So we came up with a solution that has some benefits:

- You declare your logging preferences.
- You're not bound to any logging framework. Ours is just a thin layer that just resolves your preferences to the logging solutions available at runtime.
- The logging API is the bare minimum: logging and logging context (for MDC/NDC capabilities).
- You still use your current logging framework.
- You still configure your logging as you need.

# Runtime flags

By default, the framework will automatically discover logging configurations and producer implementations. Additionally, it finds out which logging preferences are defined, based on the methods in the stack. In some scenarios, such as AWS Lambda, that behavior is undesirable. You can switch them off using the following environment variables / system properties:
- *AUTOMATICALLY_DISCOVER_LOGGING_CONFIGURATIONS* / *automatically.discover.logging.configurations*: Set to `false` to disable runtime discovery of logging configurations.
- *AUTOMATICALLY_DISCOVER_LOGGING_ANNOTATIONS* / *automatically.discover.logging.annotations*: Set to `false` to disable runtime discovery of logging annotations.
- *AUTOMATICALLY_DISCOVER_LOGGING_CONFIGURATION_PRODUCERS* / *automatically.discover.logging.configuration.producers*: Set to `false` to disable runtime discovery of logging configuration producers.
- *DEFAULT_PREFERRED_LOGGING* / *default.preferred.logging*: Set to `aws-lambda` in your AWS Lambda functions.


# Prerequisites

First, add Java-Logging as dependency in your `pom.xml` or `build.gradle`.

## Maven dependency

The dependency is already in maven-central. Just add the dependency to your `pom.xml`.

```
<dependency>
  <groupId>es.osoco.logging</groupId>
  <artifactId>java-logging</artifactId>
  <version>0.2</version>
</dependency>
```

## Gradle coordinates

Similarly, the Gradle coordinates are:

```
dependencies {
    compile(es.osoco.logging:java-logging:0.2)
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
Logging logging = LoggingFactory.createLogging();
```

Once you have the `Logging` instance, use it for, well, logging:
```
logging.info("Use case started");
```

## Logging preferences

While the underlying logging configuration is auto-discovered at runtime, you should
specify your preferences. You do so by using the `@LoggingPreferences` annotation either in a method or in a class.
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

In this scenario, let's say you'd like to log differently depending on the adapter:  when the application receives messages from the RabbitMQ queue, you want to send the logs to an ElasticSearch server, whereas the Lambda should use the AWS-Lambda built-in logging mechanism itself.

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

This library uses two concepts: Logging Configuration, and Logging Adapters. Logging Configurations are abstractions to represent required configurations needed by Logging Adapters, but they don't know who uses them. Logging Adapters are the materializations of the `Logging` interface the client uses at runtime.


## Logging Configuration

Logging Configurations are responsible for providing whatever information is needed for a Logging Adapter to work correctly. It includes file paths, remote server IPs and ports, and so on. They include the necessary checks as well. For example, file permissions, if the remote servers are up and running, etc.

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

You can also provide your own logging configuration whenever it's available. For example, the AWS-Lambda `Logger` instance is only available in the context of a Lambda execution. In such cases, you'll need to provide a `LoggingConfiguration` yourself.
For example, in an AWS-Lambda `RequestHandler`, you'd want to call
```
new AwsLambdaLoggingConfigurationProducer().configureLogging(context.getLogger());
```

There's no API for `LoggingConfigurationProducer`, since there's no way for the library to automatically discover them (the ones that can be auto-discovered should use the `@LoggingConfigurationProducer` annotation). Fear not. Your code just needs to call:

```
LoggingConfigurationRegistry.getInstance().put("your-key", yourLoggingConfiguration);
```

## Logging Adapters

The main responsibility of a `LoggingConfigurationListener` is to check if the new `LoggingConfiguration` is meaningful for him. If so, it will create a `LoggingAdapterBuilder` instance, and publish it into the `LoggingAdapterBuilderRegistry`.

Such registry simply maps keys with logging adapter builders. Builders know how to build `LoggingAdapter`s using the `LoggingConfiguration` information.

## LoggingFactory

The `LoggingFactory` resolves which logging keys are bound to the runtime context, based on the current stack trace. Once that keys (*preferred* and *fallback*) are known, it creates a composite instance after asking the relevant builders to create the `LoggingAdapter`s. That composite logging then delegates the actual logging calls to the adapters: the log message is broadcasted to all preferred mechanisms. In case any of them fails, the same message is broadcasted to all fallback mechanisms.

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

or

```
gradle test
```

# Contributing

Please read CONTRIBUTING.md for details on our code of conduct, and the process for submitting pull requests to us.

# Authors

Shibata team at OSOCO. See the `<developers>` section in the Maven `pom.xml`.

# License

This project is licensed under the GPLv3 License - see the LICENSE.md file for details

# Acknowledgments

The idea behind this library arose spontaneously after a refactoring of a DDD project. It used its own AWS-Lambda-based Logging, and it
modelled it after the ports-and-adapters approach borrowed from hexagonal architectures.

We wanted to reuse that module in other contexts, so we stole the Logging code and started implementing this library.

The design was heavily influenced by [Pharo](https://pharo.org) (a Smalltalk dialect we at [OSOCO](http://www.osoco.es) are big fans of), and was made possible thanks to the awesome [fast-classpath-scanner](https://github.com/lukehutch/fast-classpath-scanner) library from [lukehutch](https://github.com/lukehutch).
