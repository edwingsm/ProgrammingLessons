package ie.edwin.lesson.springboot.jboss.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;

import ie.edwin.lesson.springboot.jboss.model.User;

/**
 * A Database interface for Subscriber api to query user table  
 * @author edwin
 *
 */
public interface UserRepository {
	/**
	 * 
	 * @param emails
	 * @return {@link List} {@link EmailSubscription}
	 * @throws DataAccessException
	 */
	public List<User> findSubscriptionForEmails(List<String> emails) throws DataAccessException;
	
	/**
	 * 
	 * @param email
	 * @return {@link EmailSubscription}
	 * @throws DataAccessException
	 */
	public User findSubscriptionForEmail(String email) throws DataAccessException;

}
