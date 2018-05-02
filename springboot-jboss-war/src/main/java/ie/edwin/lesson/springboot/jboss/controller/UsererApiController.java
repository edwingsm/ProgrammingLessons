package ie.edwin.lesson.springboot.jboss.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



import ie.edwin.lesson.springboot.jboss.model.User;
import ie.edwin.lesson.springboot.jboss.service.UserApiService;
import ie.edwin.lesson.springboot.jboss.validation.EmailConstraint;

@RestController
@RequestMapping("/api")
@Validated
public class UsererApiController {
	private static final Logger logger = LogManager.getLogger(UsererApiController.class);
	@Autowired
	private UserApiService service;

	@GetMapping(path = { "/checksubscription/{type}", "/checksubscription" }, produces= {MediaType.APPLICATION_JSON_UTF8_VALUE,MediaType.APPLICATION_XML_VALUE})
	public List<User> getSubscriptionForUsersByType(@EmailConstraint @RequestParam("email") final List<String> emails,
			@PathVariable(name = "type", required = false) final String type) {
		logger.info("Going to check subscribtions for users with email {}",emails.toString());
		final String subscriptiontype = StringUtils.isEmpty(type) ? "all" : type;
		final List<User> subscriptions = service.getSubscriptionForEmailsByType(emails, subscriptiontype);
		return subscriptions;
	}

}