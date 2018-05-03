package ie.edwin.lesson.springboot.jboss;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootJbossWarApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJbossWarApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		System.out.println("Testing Somthing");
		
	}
}
