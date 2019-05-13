package ar.edu.unq.desapp.grupoa.service;

import org.junit.Test;
import org.springframework.stereotype.Controller;

import static org.junit.Assert.*;

@Controller
public class EmailSenderServiceTest {


    @Test
    public void sendMailTo() {
        EmailSenderService essSUT = new EmailSenderService();

        essSUT.sendMailTo();
    }
}