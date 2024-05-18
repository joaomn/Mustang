package br.com.mustang.services.interfaces;

import jakarta.mail.MessagingException;

public interface EmailService {
	
	public void sendSimpleEmail(String to, String bodyText);
	
	void sendEmailWhitAttachmentCSV(String to, byte[] attachment, String attachmentName, String emailBody) throws MessagingException;

}
