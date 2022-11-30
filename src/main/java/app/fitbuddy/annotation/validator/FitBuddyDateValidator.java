package app.fitbuddy.annotation.validator;

import app.fitbuddy.annotation.FitBuddyDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class FitBuddyDateValidator implements ConstraintValidator<FitBuddyDate, String> {

	private final String datePattern;

	@Autowired
	public FitBuddyDateValidator(@Value("${fitbuddy.validation.pattern.date:yyyy-MM-dd}") String datePattern) {
		this.datePattern = datePattern;
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(datePattern);
		try {
			LocalDate.parse(value, dateTimeFormatter);
			return true;
		} catch (Throwable ignoredThrowable) {
			return false;
		}
	}
}
