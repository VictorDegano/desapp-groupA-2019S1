package ar.edu.unq.desapp.grupoa.service;

import ar.edu.unq.desapp.grupoa.service.email.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender javaMailSender;


    public void sendInvitation(String mailOwner, String mailGuest, String nameGuest, String eventName){
        String subject=String.format("Invitacion a Evento %s", eventName);
        String content=String.format("Hola %s! Fuiste invitado al evento %s",nameGuest,eventName);


        new Mail(mailOwner,mailGuest,subject,content);
    }

    public void send(Mail mail){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(mail.getSubject());
        message.setText(mail.getContent());
        message.setTo(mail.getTo());
        message.setFrom("tadlosrebu@gmail.com");

        javaMailSender.send(message);
    }

}
