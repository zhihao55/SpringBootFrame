package com.zhihao.service.email;

public interface MailService {
    void sendMail(String to, String subject, String verifyCode);

    void sendHtmlMail(String to, String subject, String content);
}
