package br.com.portabilit.wsmarcaponto.configuration;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.portabilit.wsmarcaponto.service.email.EmailService;
import br.com.portabilit.wsmarcaponto.service.email.SmtpEmailService;
import br.com.portabilit.wsmarcaponto.service.utils.DatabaseService;

@Configuration
@Profile("prod")
public class ProdConfig {

	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;

	@Autowired
	private DatabaseService databaseService;

	@Bean
	public boolean instantiateDatabase() throws ParseException {
		if (!"update".equals(strategy)) {
			return false;
		}
		databaseService.instantiateTestDatabase();
		return true;
	}

	@Bean
	public EmailService emailService() {
		return new SmtpEmailService();
	}
}
