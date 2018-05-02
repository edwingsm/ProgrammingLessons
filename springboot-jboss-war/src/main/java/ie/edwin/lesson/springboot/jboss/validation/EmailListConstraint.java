package ie.edwin.lesson.springboot.jboss.validation;

import java.util.List;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A {@link ConstraintValidator} Implementation to validate Strings in a List
 * match with Email regex
 * 
 * @author DEI
 *
 */
public class EmailListConstraint implements ConstraintValidator<EmailConstraint, List<String>> {
	private static final Logger logger = LogManager.getLogger(EmailListConstraint.class);
	private final static Pattern p = Pattern.compile(
			"^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");

	@Override
	public void initialize(EmailConstraint emailConstraint) {
		logger.debug("EmailListConstraint initialize");
	}

	@Override
	public boolean isValid(List<String> value, ConstraintValidatorContext context) {
		if (value != null && !value.isEmpty()) {
			logger.debug("EmailListConstraint validating input");
			/* Checking all entries in List match the email regex */
			return value.stream().allMatch(st -> p.matcher(st).matches());
		}
		logger.debug("EmailListConstraint inputs were empty or null");
		return false;
	}

}
