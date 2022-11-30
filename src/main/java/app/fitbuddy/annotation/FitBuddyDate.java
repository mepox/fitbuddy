package app.fitbuddy.annotation;

import app.fitbuddy.annotation.validator.FitBuddyDateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = FitBuddyDateValidator.class)
public @interface FitBuddyDate {

	String message() default "{fitbuddy.validation.message.invaliddate}";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

}
