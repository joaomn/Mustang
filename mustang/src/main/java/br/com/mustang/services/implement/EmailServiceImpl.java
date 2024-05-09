package br.com.mustang.services.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import br.com.mustang.services.interfaces.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {
	
	@Autowired
	private JavaMailSender emailSender;

	@Override
	public void sendSimpleEmail(String to) {
		try {
			SimpleMailMessage email = new SimpleMailMessage();
			
			email.setTo(to);
			email.setSubject("Email de teste da api");
			email.setText("chegou ai? hahaha pegou carai!");
			email.setFrom("MUSTANG API EMAIL SERVICE <api.mustang.contato@gmail.com>");
			
			emailSender.send(email);
			
			System.out.println("enviou o email");
			
		} catch (Exception e) {
			throw new RuntimeException("erro ao enviar email");
		}
		
	}

	@Override
	public void sendEmailWhitAttachmentCSV(String to, byte[] attachment, String attachmentName, String emailBody)
			throws MessagingException {
		 MimeMessage message = emailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message, true);

	        helper.setTo(to);
	        helper.setSubject("Assunto do Email");
	        helper.setText(emailBody);
	        helper.setFrom("MUSTANG API EMAIL SERVICE <api.mustang.contato@gmail.com>");

	        // Adiciona o anexo
	        helper.addAttachment(attachmentName, new ByteArrayResource(attachment), "text/csv");

	        // Envie o e-mail
	        emailSender.send(message);
		
	}

}
