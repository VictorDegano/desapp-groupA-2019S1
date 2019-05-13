package ar.edu.unq.desapp.grupoa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailSenderService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMailTo() {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("flame.el22@gmail.com", "DominikowIvan@gmail.com", "ivanjaratamargo@gmail.com", "minusmisaki@gmail.com");

        msg.setSubject("Testing from Spring Boot");
        msg.setText("Hello World! \n Spring Boot Email it's runnning!! yuhuuu!! doh!");

        this.javaMailSender.send(msg);
    }
}
