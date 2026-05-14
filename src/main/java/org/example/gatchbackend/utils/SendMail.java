package org.example.gatchbackend.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;
import java.util.Map;

@Component
public class SendMail {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private TemplateEngine templateEngine;
    @Value("${spring.mail.username}")
    private String mailFrom;

    public void sendMail(String destination, String title, String htmlName, Map<String, Object> variableMap){
        MimeMessage message = mailSender.createMimeMessage();
        try{
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(destination);
            helper.setSubject(title);
            helper.setFrom(mailFrom);

            Context context = new Context();
            for(Map.Entry<String, Object> variable: variableMap.entrySet()){
                context.setVariable(variable.getKey(), variable.getValue());
            }
            String html = templateEngine.process(htmlName, context);
            helper.setText(html, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
