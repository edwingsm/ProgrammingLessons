package ie.edwin.lesson.springboot.jboss;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
//@SpringBootApplication
public class SpringBootJbossWarServletInitializer extends SpringBootServletInitializer implements CommandLineRunner {

	private static final Logger logger = LogManager.getLogger(SpringBootJbossWarServletInitializer.class);
	public static void main(String[] args) {
		logger.info("SpringBootJbossWarServletInitializer initilize SpringBootServletInitializer");
		SpringApplication.run(SpringBootJbossWarServletInitializer.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		logger.info("SpringBootJbossWarServletInitializer configuring SpringApplicationBuilder ");
		return application.sources(SpringBootJbossWarServletInitializer.class);
	}

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		logger.info("SpringBootJbossWarServletInitializer  setting servletContext onStartup ");
		super.onStartup(servletContext);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Testing Somthing");
		
	}

}
