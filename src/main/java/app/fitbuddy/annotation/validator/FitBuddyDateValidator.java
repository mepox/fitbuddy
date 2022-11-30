package app.fitbuddy.annotation.validator;

import app.fitbuddy.annotation.FitBuddyDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
public class FitBuddyDateValidator implements ConstraintValidator<FitBuddyDate, String> {

	private final DateTimeFormatter dateTimeFormatter;

	@Autowired
	public FitBuddyDateValidator(@Value("${fitbuddy.validation.pattern.date:yyyy-MM-dd}") String datePattern) {
		dateTimeFormatter = DateTimeFormatter.ofPattern(datePattern);
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		try {
			LocalDate.parse(value, dateTimeFormatter);
			return true;
		} catch (DateTimeParseException parseException) {
			return false;
		}
	}
}
