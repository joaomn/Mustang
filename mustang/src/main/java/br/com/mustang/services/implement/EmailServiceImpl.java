package br.com.mustang.services.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import br.com.mustang.services.interfaces.EmailService;

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

}
