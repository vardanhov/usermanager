package com.egs.example.sendemail;

import com.egs.example.data.model.User;
import com.egs.example.exception.MailException;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public class SendEmail {

    private Session session = null;
    private Properties prop;
    private static final SendEmail instance = new SendEmail();
    private static final SampleVelocity sampleVelocity = new SampleVelocity();


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


    public void sendEmailConfirmation(String addressTo, String token, User user) {

        String url = "http://localhost:8080/registration-confirm?token=" + token + "&email=" + addressTo;
        String text = "Please confirm you registration by clicking the following link <a href=\"" + url + "\">confirm email</a>";
        sampleVelocity.sendEmail(text, session, user, addressTo);
    }

    public void sendResetPassword(String addressTo, String token, User user) {

        String url = "http://localhost:8080/confirm-change-password?token=" + token + "&email=" + addressTo;
        String text = "Please  click the following link to change password <a href=\"" + url + "\">Change Password</a>";
        sampleVelocity.sendEmail(text, session, user, addressTo);
    }

    public void sendChangeEmail(String addressTo, String token, User user) {

        String url = "http://localhost:8080/user/confirm-change-email?token=" + token + "&email=" + addressTo;
        String text = "Please  click the following link to change email <a href=\"" + url + "\">Change Email</a>";
        sampleVelocity.sendEmail(text, session, user, addressTo);
    }
}