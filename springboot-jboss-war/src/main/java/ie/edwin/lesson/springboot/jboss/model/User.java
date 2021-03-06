package ie.edwin.lesson.springboot.jboss.model;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
/**
 * DB model for user Table
 * This model hold properties that represent all the columns of subscription table 
 * This model has an additional property isResgisterd that is used to represent the subscription status, default value is true
 * This is table is special case :  Table doesn't have a primary key, so it's not possible to use JPA api over the model
 * 
 * We can achieve the JPA usage by using an embedded ID to over come the primary key issue, but it's not what I'm intended to do in this project  
 * 
 * @author Edwin
 *
 */
public class User {

	private String email;

	private Date creationDate;
	
	private String name;
	
	private boolean isResgisterd=true;

	

	public User(String email, boolean isregistered) {
		// TODO Auto-generated constructor stub
	}

	public User() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isResgisterd() {
		return isResgisterd;
	}

	public void setResgisterd(boolean isResgisterd) {
		this.isResgisterd = isResgisterd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	
}