package com.egs.example.controller.common;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;
import org.apache.velocity.servlet.VelocityServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SampleVelocity extends VelocityServlet {

    public Template handleRequest(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Context context) {
        Template template = null;
        try {
            template =  getTemplate("templates/email-template.vm");
        } catch(Exception e) {
            System.out.println("Error " + e);
        }
        return template;
    }
}