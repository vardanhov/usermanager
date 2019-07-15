package com.egs.example.sendemail;

import com.egs.example.exception.MailException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public class SendEmail {

    private Session session = null;
    private Properties prop;
    private static final SendEmail instance = new SendEmail();

    public SendEmail() {
        InputStream input = SendEmail.class.getClassLoader().getResourceAsStream("mail-config.properties");
        {
            prop = new Properties();
            if (input == null) {
                System.out.println("Sorry, unable to find mail-config.properties");
            }

            try {
                prop.load(input);
                session = Session.getDefaultInstance(prop, new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                prop.getProperty("mail.username"),
                                prop.getProperty("mail.password"));
                    }
                });
            } catch (FileNotFoundException e) {
                System.out.println("File not found");
            } catch (Exception ex) {

            }
        }
    }

    public static SendEmail getInstance() {
        return instance;
    }


    public void sendEmailConfirmation(String addressTo, String token) throws MailException {
        try {
            BodyPart messageBodyPart = new MimeBodyPart();
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(addressTo));
            message.setSubject("Registration");

            String url = "http://localhost:8080/registration-confirm?token=" + token + "&email=" + addressTo;
            String text = "Please confirm you registration by clicking the following link <a href=\"" + url + "\">confirm email</a>";
            messageBodyPart.setContent(text, "text/html");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);

            Transport.send(message);
            System.out.println("message sent successfully");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendResetPassword(String addressTo, String token) throws MailException {
        try {
            BodyPart messageBodyPart = new MimeBodyPart();
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(addressTo));
            message.setSubject("Reset Password");

            String url = "http://localhost:8080/confirm-change-password?token=" + token + "&email=" + addressTo;
            String text = "Please  click the following link to change password <a href=\"" + url + "\">Change Password</a>";
            messageBodyPart.setContent(text, "text/html");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);

            Transport.send(message);
            System.out.println("message sent successfully");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendChangeEmail(String addressTo, String token) throws MailException {
        try {
            BodyPart messageBodyPart = new MimeBodyPart();
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(addressTo));
            message.setSubject("Change Email");

            String url = "http://localhost:8080/confirm-change-email?token=" + token + "&email=" + addressTo;
            String text = "Please  click the following link to change email <a href=\"" + url + "\">Change Email</a>";
            messageBodyPart.setContent(text, "text/html");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);

            Transport.send(message);
            System.out.println("message sent successfully");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}