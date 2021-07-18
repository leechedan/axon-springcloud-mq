package org.github.axon.tag.common.util;

import lombok.extern.slf4j.Slf4j;
import org.github.axon.tag.common.exception.BusinessError;
import org.github.axon.tag.common.exception.BusinessException;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @author lee
 * @date 2020/1/5 14:36
 */
@Slf4j
public enum MailUtils {
    ;

    private static final String EMAIL = "v834250018@163.com";
    private static final String AUTH_CODE = "JyY4i9HG7hRkB5fT";
    private static final String EMAIL_HOST = "smtp.163.com";

    public static void sendMail(String subject, String content, String... addressees) {

        Properties properties = System.getProperties();
        properties.put("mail.smtp.ssl.enable", "true");
        // 设置邮件服务器
        properties.setProperty("mail.smtp.host", EMAIL_HOST);
        properties.put("mail.smtp.auth", "true");
        // 获取默认session对象
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL, AUTH_CODE);
            }
        });

        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(EMAIL));
            List<InternetAddress> list = new ArrayList();
            for (String addressee : addressees) {
                list.add(new InternetAddress(addressee));
            }
            message.addRecipients(Message.RecipientType.TO, list.toArray(new Address[0]));
            message.setSubject(subject);
            message.setText(content, "UTF-8", "html");
            Transport.send(message);
        } catch (MessagingException e) {
            log.error(BusinessError.BU_9200.getMsg() + e.getMessage());
            throw new BusinessException(BusinessError.BU_9200, e.getMessage());
        }
    }
}
