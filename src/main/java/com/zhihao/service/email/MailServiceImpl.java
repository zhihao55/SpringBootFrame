package com.zhihao.service.email;

import java.io.UnsupportedEncodingException;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender mailSender;
    @Value("${mail.fromMail.addr}")
    private String from;
    @Autowired
    TemplateEngine templateEngine;
    @Autowired
    private JavaMailSender javaMailSender;

    public MailServiceImpl() {
    }

    public void sendMail(String to, String subject, String verifyCode) {
        Context context = new Context();
        context.setVariable("verifyCode", verifyCode);
        String emailContent = templateEngine.process("emailTemplate", context);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(emailContent);

        try {
            this.mailSender.send(message);
        } catch (Exception var8) {
            System.out.println(var8.getMessage());
        }

    }

    public void sendHtmlMail(String to, String subject, String content) {
        MimeMessage message = this.javaMailSender.createMimeMessage();
        MimeMessageHelper helper = null;

        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(this.from, "学习论坛-好好学习");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
        } catch (MessagingException var7) {
            var7.printStackTrace();
        } catch (UnsupportedEncodingException var8) {
            var8.printStackTrace();
        }

        this.javaMailSender.send(message);
    }
}

