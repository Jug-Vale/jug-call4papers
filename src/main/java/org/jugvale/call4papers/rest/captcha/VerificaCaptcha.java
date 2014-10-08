package org.jugvale.call4papers.rest.captcha;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * Determina que métodos terão o interceptor de captcha invocados
 * 
 * @author william
 *
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface VerificaCaptcha {

}
