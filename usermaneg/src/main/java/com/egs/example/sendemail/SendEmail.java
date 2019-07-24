package com.egs.example.sendemail;

import com.egs.example.data.model.User;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Properties;

public class SendEmail {

    private Session session;
    private Properties prop;
    private static final SendEmail instance = new SendEmail();
    private static final SampleVelocity sampleVelocity = new SampleVelocity();


    private SendEmail() {
        InputStream input = SendEmail.class.getClassLoader().getResourceAsStream("mail-config.properties");
        {
            prop = new Properties();
            if (input == null) {
                System.out.println("Sorry, unable to find mail-config.properties");
            } else {
                try {
                    prop.load(input);
                    session = Session.getDefaultInstance(prop, new Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(
                                    prop.getProperty("mail.username"),
                                    prop.getProperty("mail.password"));
                        }
                    });
                } catch (Exception ex) {
                    throw new RuntimeException("Unable to send email", ex);
                }
            }
        }
    }

    public static SendEmail getInstance() {
        return instance;
    }

    public void sendEmailConfirmation(String addressTo, String token, User user) {

        try {
            BodyPart messageBodyPart = new MimeBodyPart();
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(addressTo));
            message.setSubject("Confirm Registration");

            String url = prop.getProperty("mail.host") + "/registration-confirm?token=" + token + "&email=" + addressTo;
            String text = "Please confirm you registration by clicking the following link <a href=\"" + url + "\">Confirm email</a>";
            StringWriter out = sampleVelocity.configVelocity(text, user);

            messageBodyPart.setContent(out.toString(), "text/html");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart, "text/html");

            Transport.send(message);
            System.out.println("message sent successfully");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendResetPassword(String addressTo, String token, User user) {

        try {
            BodyPart messageBodyPart = new MimeBodyPart();
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(addressTo));
            message.setSubject("Change Password");

            String url = prop.getProperty("mail.host") + "/confirm-change-password?token=" + token + "&email=" + addressTo;
            String text = "Please  click the following link to change password <a href=\"" + url + "\">Change Password</a>";
            StringWriter out = sampleVelocity.configVelocity(text,  user);

            messageBodyPart.setContent(out.toString(), "text/html");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart, "text/html");

            Transport.send(message);
            System.out.println("message sent successfully");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendChangeEmail(String addressTo, String token, User user) {
        try {
            BodyPart messageBodyPart = new MimeBodyPart();
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(addressTo));
            message.setSubject("Change Email");

            String url = prop.getProperty("mail.host") + "/user/confirm-change-email?token=" + token + "&email=" + addressTo;
            String text = "Please  click the following link to change email <a href=\"" + url + "\">Change Email</a>";
            StringWriter out = sampleVelocity.configVelocity(text,  user);

            messageBodyPart.setContent(out.toString(), "text/html");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart, "text/html");

            Transport.send(message);
            System.out.println("message sent successfully");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}