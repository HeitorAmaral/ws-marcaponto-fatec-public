package br.com.portabilit.wsmarcaponto.service.email;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

public abstract class AbstractEmailService implements EmailService {

	@Value("${default.sender}")
	private String from;

	@Override
	public void sendRegisterConfirmationEmail(Integer id, String nome, String username, String password) {
		SimpleMailMessage simpleMailMessage = prepareSimpleMailMessageFromUsuario(id, nome, username, password);
		sendEmail(simpleMailMessage);
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromUsuario(Integer id, String nome, String username,
			String password) {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setTo(username);
		simpleMailMessage.setFrom(from);
		simpleMailMessage.setSubject("MarcaPonto - Alerta: Novo usuário");
		simpleMailMessage.setSentDate(new Date(System.currentTimeMillis()));

		StringBuilder builder = new StringBuilder();
		builder.append("Olá ");
		builder.append(nome);
		builder.append("!\n");
		builder.append("\nFoi criado um novo usuário no sistema MarcaPonto utilizando o seu email!\n");
		builder.append("\nIdentificador: ");
		builder.append(id);
		builder.append("\nUsuário: ");
		builder.append(username);
		builder.append("\nSenha padrão (Gerada automáticamente): ");
		builder.append(password);
		builder.append("\n\n**A senha poderá ser alterada ao realizar o login, utilizando o \"Esqueci a senha\"!**");
		simpleMailMessage.setText(builder.toString());
		return simpleMailMessage;
	}

}
