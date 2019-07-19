package com.egs.example.sendemail;

import com.egs.example.data.model.User;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.StringWriter;

public class SampleVelocity {


public void sendEmail(String text, Session session, User user, String addressTo){

    try {
        BodyPart messageBodyPart = new MimeBodyPart();
        MimeMessage message = new MimeMessage(session);
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(addressTo));
        message.setSubject("Change Email");


        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.init();

        Template template = ve.getTemplate("templates/email-template.vm");

        VelocityContext context = new VelocityContext();
        context.put("name", user.getName());
        context.put("surname", user.getSurname());
        context.put("text", text);

        StringWriter out = new StringWriter();
        template.merge(context, out);

        messageBodyPart.setContent(out.toString(), "text/html");
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        message.setContent(multipart, "text/html");

        Transport.send(message);
        System.out.println("message sent successfully");
    }
    catch (MessagingException e) {
        throw new RuntimeException(e);
    }
}
}
