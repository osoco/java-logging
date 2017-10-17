package es.osoco.logging.annotations;

import es.osoco.logging.config.LoggingConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Use this annotation to indicate a method able to provide a new {@link LoggingConfiguration}.
 * The class itself needs to have a public default constructor for this to work.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LoggingConfigurationProducer {

    String key() default "default";
}
