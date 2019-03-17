package software.jevera.configuration;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.context.annotation.Import;

@Retention(RetentionPolicy.RUNTIME)
@Import(PropertyPlaceholderConfig.class)
public @interface EnablePropertyPlaceholder {

}
