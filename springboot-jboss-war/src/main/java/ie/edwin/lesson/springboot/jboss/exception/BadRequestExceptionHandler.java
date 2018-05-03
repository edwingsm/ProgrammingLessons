package ie.edwin.lesson.springboot.jboss.exception;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * BadRequestExceptionHandler 
 * @author Edwin
 *
 */
@ControllerAdvice
public class BadRequestExceptionHandler {

	private static final Logger logger = LogManager.getLogger(BadRequestExceptionHandler.class);
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handleValidationException(ConstraintViolationException e){
		for(ConstraintViolation<?> s:e.getConstraintViolations()){
			logger.error("seems like a bad request "+s.getInvalidValue()+": "+s.getMessage());
			return s.getInvalidValue()+": "+s.getMessage();
		}
		logger.error("seems like a bad request with invalid parmaeters");
		return "Request with invalid parmaeters";
	}
}
