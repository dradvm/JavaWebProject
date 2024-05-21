package com.JavaWebProject.JavaWebProject.services;

import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.stereotype.Service;


@Service
public class MailService {
    
    public boolean sendMail(String to, String subject, String message) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", 465);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.port", 465);
        Authenticator auth = new Authenticator() {
            
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("plateportal@gmail.com", "mmrteyjgzluzwolm");
            }
        };
        Session session = Session.getInstance(props, auth);
        MimeMessage mime = new MimeMessage(session);
        try {
            mime.addHeader("Content-type", "text/HTML; charset=UTF-8");
            mime.addHeader("format", "flowed");
            mime.addHeader("Content-Transfer-Encoding", "8bit");
            mime.setFrom(new InternetAddress("plateportal@gmail.com", "Plate Portal"));
            mime.setReplyTo(InternetAddress.parse("plateportal@gmail.com", false));
            mime.setSubject(subject, "UTF-8");
            mime.setText(message, "UTF-8");
            mime.setSentDate(new Date());
            mime.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
            Transport.send(mime);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
