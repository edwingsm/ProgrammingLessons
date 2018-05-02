package ie.edwin.lesson.springboot.jboss.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;


import ie.edwin.lesson.springboot.jboss.model.User;

/**
 * A mapper class to Map the result of Sql Query to {@link User} Objects
 * The mapper is used because Subscription api is not using JAP apis.
 * 
 * @author edwin
 *
 */
public class EmailSubscriptionRowMapper implements RowMapper<User> {
	private static final Logger logger = LogManager.getLogger(EmailSubscriptionRowMapper.class); 
	
	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		logger.debug("Found Subscription for user with email {}",rs.getString("email_address"));
		User emailSubscription = new User();
		emailSubscription.setEmail(rs.getString("email_address"));
		emailSubscription.setEmail(rs.getString("name"));
		emailSubscription.setCreationDate(rs.getDate("creation_date"));
		return emailSubscription;
	}

}
