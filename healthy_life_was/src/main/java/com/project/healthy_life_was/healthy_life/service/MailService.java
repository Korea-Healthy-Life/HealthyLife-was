package com.project.healthy_life_was.healthy_life.service;

import com.project.healthy_life_was.healthy_life.provider.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    public MimeMessage createMailForId (String email, String token) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        message.setFrom(senderEmail);
        message.setRecipients(MimeMessage.RecipientType.TO, email);

        String subject;
        String body;

        subject = "HealthyLife 이메일 인증 링크";
        body = "<h3> HealthyLife 이메일 인증 링크입니다.</h3>";
        body += "<a href=\"http://localhost:3000/find-id/" + token + "\"> 해당 링크를 클릭하여 인증을 완료해 주세요.</a>";
        body += "<p>감사합니다.</p>";

        message.setSubject(subject);
        message.setText(body, "UTF-8", "html");
        return message;
    }

    public MimeMessage createMailForPw(String email, String username, String token) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        message.setFrom(senderEmail);
        message.setRecipients(MimeMessage.RecipientType.TO, email);

        String subject;
        String body;

        subject = "HealthyLife 이메일 인증 링크";
        body = "<h3>" + username + "님 HealthyLife 이메일 인증 링크입니다.</h3>";
        body += "<a href=\"http://localhost:3000/find-password/" + token + "\"> 해당 링크를 클릭하여 인증을 완료해 주세요.</a>";
        body += "<p>감사합니다.</p>";

        message.setSubject(subject);
        message.setText(body, "UTF-8", "html");
        return message;
    }

    public void sendMail(MimeMessage message) throws MessagingException {
        javaMailSender.send(message);
    }
}