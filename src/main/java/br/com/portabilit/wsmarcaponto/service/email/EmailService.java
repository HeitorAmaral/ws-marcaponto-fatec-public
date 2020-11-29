package br.com.portabilit.wsmarcaponto.service.email;

import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

	void sendRegisterConfirmationEmail(Integer id, String nome, String username, String password);

	void sendEmail(SimpleMailMessage simpleMailMessage);

}
