package ar.edu.unq.desapp.grupoa.service;

import ar.edu.unq.desapp.grupoa.service.email.Mail;
import ar.edu.unq.desapp.grupoa.utils.SmtpServerRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import java.io.IOException;

import static org.junit.Assert.*;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class EmailSenderServiceTest {

    @Autowired
    private EmailSenderService emailService;

    @Rule
    public SmtpServerRule smtpServerRule = new SmtpServerRule(2525, "user", "password");

    @Test
    public void whenCreateAndSendAMail_isSended() throws MessagingException, IOException {
        //Setup(Given)
        Mail mail = new Mail();
        mail.setFrom("no-reply@memorynotfound.com");
        mail.setTo("someMail@test.com");
        mail.setSubject("Spring Mail Integration Testing with GreenMail Example");
        mail.setContent("We show how to write Integration Tests using Spring and GreenMail.");

        //Exercise(When)
        emailService.send(mail);

        //Test(Then)
        MimeMessage[] receivedMessages = smtpServerRule.getMessages();
        assertEquals(1, receivedMessages.length);

        MimeMessage current = receivedMessages[0];

        assertEquals(mail.getSubject(), current.getSubject());
        assertEquals(mail.getTo(), current.getAllRecipients()[0].toString());
        assertTrue(String.valueOf(current.getContent()).contains(mail.getContent()));
    }

}
