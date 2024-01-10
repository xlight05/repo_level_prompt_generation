/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diaoc;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMailOK {

    public static final String Server = "smtp.gmail.com";
    public static final String companyMail = "chodiaocaq@gmail.com";
    public static final String companyMailPass = "qacoaidohc";
    public SendMailOK(){
        
    }

    public static void send(String smtpServer, String to, String from, String psw,
    String subject, String body) throws Exception{
            // java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            Properties props = System.getProperties();
            // –
            props.put("mail.smtp.host", smtpServer);
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.starttls.enable", true);
            final String login = from; //”nth001@gmail.com”;//usermail
            final String pwd = psw;     //”password cua ban o day”;
            Authenticator pa = null; //default: no authentication
            if (login != null && pwd != null) { //authentication required?
                    props.put("mail.smtp.auth", true);
                    pa = new Authenticator (){
                            public PasswordAuthentication getPasswordAuthentication() {
                                    return new PasswordAuthentication(login, pwd);
                            }
                    };
            }//else: no authentication

            Session session = Session.getInstance(props, pa);

            // — Create a new message –
            Message msg = new MimeMessage(session);
            // — Set the FROM and TO fields –
            msg.setFrom(new InternetAddress(from));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(
            to, false));

            // — Set the subject and body text –
            msg.setSubject(subject);
            msg.setText(body);
            // — Set some other header information –
            msg.setHeader("X-Mailer", "LOTONtechEmail");
            msg.setSentDate(new Date());
            msg.saveChanges();
            // — Send the message –
            Transport.send(msg);
            System.out.println("Message sent OK.");

    }
    /**
    * Main method to send a message given on the command line.
    */
//    public static void main(String[] args) {
//            {
//            try
//            {
//                    String smtpServer="smtp.gmail.com";
//                    String to="Nixforest@gmail.com";
//                    String from="chodiaocaq@gmail.com";
//                    String subject="Đây là email được gửi từ ban quản lý Chợ địa ốc A & Q";
//                    String body="Đây là email được gửi từ ban quản lý Chợ địa ốc A & Q.";
//                    String password="qacoaidohc";
//                    send(smtpServer, to, from, password, subject, body);
//                    System.out.println("Finish!");
//            }
//            catch (Exception ex)
//            {
//                    System.out.println("Usage: "+ex.getMessage());
//            }
//
//            }

            /**
            * “send” method to send the message.
            */

//    }

}