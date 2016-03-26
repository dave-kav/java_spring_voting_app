package dk.cit.oossp.thymeleaf;

import java.util.Locale;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@SpringBootApplication
public class Assignment2VotingApplication extends WebMvcConfigurerAdapter {


	@Bean
	public LocaleResolver localeResolver() {
	    CookieLocaleResolver localeResolver = new CookieLocaleResolver();
	    localeResolver.setDefaultLocale(Locale.US);
	    return localeResolver;
	}
	
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
	    LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
	    lci.setParamName("lang");
	    return lci;
	}
	
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

	public static void main(String[] args) {
		SpringApplication.run(Assignment2VotingApplication.class, args);
	}
}
