package org.example.gatchbackend.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
public class SendMail {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    public void sendVerificationMail(String destination, String title, String username, String token){
        MimeMessage message = mailSender.createMimeMessage();

        try{
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(destination);
            helper.setSubject(title);
            helper.setFrom("gatchdevelopment@gmail.com");

            Context context = new Context();
            context.setVariable("username", username);
            context.setVariable("token", token);

            String html = templateEngine.process("mail", context);

            helper.setText(html, true);

            mailSender.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Error al enviar el correo: " + e.getMessage());
        }
    }
}
