package br.com.portabilit.wsmarcaponto.configuration;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.portabilit.wsmarcaponto.service.email.EmailService;
import br.com.portabilit.wsmarcaponto.service.email.MockEmailService;
import br.com.portabilit.wsmarcaponto.service.utils.DatabaseService;

@Configuration
@Profile("test")
public class TestConfig {

	@Autowired
	private DatabaseService databaseService;

	@Bean
	public boolean instantiateDatabase() throws ParseException {
		databaseService.instantiateTestDatabase();
		return true;
	}

	@Bean
	public EmailService emailService() {
		return new MockEmailService();
	}
}
