package ar.edu.unq.desapp.grupoa.utils;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import org.junit.rules.ExternalResource;
import javax.mail.internet.MimeMessage;

public class SmtpServerRule extends ExternalResource {

    private GreenMail smtpServer;
    private int port;
    private String username;
    private String password;

    public SmtpServerRule(int port, String user, String password) {
        this.username = user;
        this.password = password;
        this.port = port;
    }

    @Override
    protected void before() throws Throwable {
        super.before();
        smtpServer = new GreenMail(new ServerSetup(this.port, "localhost", "smtp"));
        smtpServer.setUser(this.username,this.password);
        smtpServer.start();
    }

    public MimeMessage[] getMessages() {
        return smtpServer.getReceivedMessages();
    }

    @Override
    protected void after() {
        super.after();
        smtpServer.stop();
    }
}