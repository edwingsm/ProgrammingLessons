package ie.edwin.lesson.springboot.jboss.repository;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;


import ie.edwin.lesson.springboot.jboss.model.User;

/**
 * 
 * @author edwin
 *
 */
@Repository
public class EmailSubscriptionRepositoryImpl implements UserRepository {
	private static final Logger logger = LogManager.getLogger(EmailSubscriptionRepositoryImpl.class);
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public List<User> findSubscriptionForEmails(List<String> emails) throws DataAccessException {
		logger.debug("Contacting DB to find is subscription enable for users with following emails {}", emails.toString());
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("emails", emails);
		return namedParameterJdbcTemplate.query("select * from email_subscription where email_address IN (:emails)", parameters,new EmailSubscriptionRowMapper());
	}

	@Override
	public User findSubscriptionForEmail(String email) throws DataAccessException {
		logger.debug("Contacting DB to find is subscription enable for user with following email {}", email);
		User emailSubscription = new User(email, false);
		try {
			//if the query didn't find any result will throw exception
			emailSubscription = jdbcTemplate.queryForObject("select * from email_subscription where email_address=?", new Object[] { email }, new EmailSubscriptionRowMapper());
		} catch (Exception e) {
			logger.error("Failed to execute Data base query to retrive infromation for user with emai {}", email);
		}
		return emailSubscription;
	}

	
}
