package ie.edwin.lesson.springboot.jboss.config;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@EnableWebMvc
@Configuration
@ComponentScan
public class WebConfiguration implements WebMvcConfigurer {
	private static final Logger logger = LogManager.getLogger(WebConfiguration.class);

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		logger.debug("Configuring messagage converters for REST end point");
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
		builder.serializationInclusion(JsonInclude.Include.NON_NULL);
		builder.serializationInclusion(Include.NON_EMPTY);
		// builder.featuresToEnable(SerializationFeature.WRAP_ROOT_VALUE);
		builder.defaultUseWrapper(false);
		builder.autoDetectGettersSetters(true);
		builder.indentOutput(true).dateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZZ"));
		converters.add(new MappingJackson2HttpMessageConverter(builder.build()));
		converters.add(new MappingJackson2XmlHttpMessageConverter(builder.createXmlMapper(true).build()));
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		logger.debug("addCorsMappings for REST end point");
		registry.addMapping("subscriber-api/api/**");
	}
}
