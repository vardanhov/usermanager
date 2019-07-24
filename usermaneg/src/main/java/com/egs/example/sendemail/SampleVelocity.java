package com.egs.example.sendemail;

import com.egs.example.data.model.User;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import java.io.StringWriter;

public class SampleVelocity {

    public StringWriter configVelocity(String text,  User user) {

        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.init();

        Template template = ve.getTemplate("templates/email-template.vm");

        VelocityContext context = new VelocityContext();
        context.put("user", user);
        context.put("text", text);

        StringWriter out = new StringWriter();
        template.merge(context, out);
        return out;
    }
}
