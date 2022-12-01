package app.fitbuddy.testhelper.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;

import org.springframework.security.test.context.support.WithSecurityContext;

@Retention(RUNTIME)
@WithSecurityContext(factory = WithMockAppUserPrincipalSecurityContextFactory.class)
public @interface WithMockAppUserPrincipal {
	
	int id() default 1;
	String name() default "user";
	String password() default "password";
	String authority() default "USER";

}
