package ir.etelli.kids.Gmail;

import android.util.Log;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {

    public static void send(String subject, String messageBody) {
        Session session;

        Log.e("SendMail", "SendMail 1");


//        //        String host = "smtp.gmail.com";
//        String host = "mail.farazclub.ir";
//        String from = "kids@farazclub.ir";
//        String port = "465";
////        String host = "mail.etelli.ir";
////        String from = "etelliir@etelli.ir";
//        String to = "ezattelli@gmail.com";
//        String userName = "kids@farazclub.ir";
////        String userName = "etelliir@etelli.ir";
//        String password = "pashmak5000";

        //        String host = "smtp.gmail.com";
        String host = "smtp-relay.sendinblue.com";
        String from = "kids@farazclub.ir";
        String port = "587";
//        String host = "mail.etelli.ir";
//        String from = "etelliir@etelli.ir";
        String to = "ezattelli@gmail.com";
        String userName = "kids@farazclub.ir";
//        String userName = "etelliir@etelli.ir";
        String password = "pashmak5000";


        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", host);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.port", port);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", port);

        session = javax.mail.Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {

                Log.e("SendMail", "SendMail 2");
                try {

                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(from));
                    message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
                    message.setSubject(subject);
                    message.setContent(messageBody, "text/html; charset=utf-8");
//                    Transport.send(message);

                    Transport transport = session.getTransport("smtp");
                    transport.connect(host, userName, password);
                    transport.sendMessage(message, message.getAllRecipients());
                    transport.close();

//                    GMailSender sender = new GMailSender("ezattelli", "pashmak5000");
////                    GMailSender sender = new GMailSender("etelliir@etelli.ir", "pashmak5000");
//                    sender.sendMail("ارسال مستقیم ایمیل با اندروید",
//                            "This is Body",
//                            "ezattelli@yahoo.com",
////                            "etelliir@etelli.ir",
////                    "user@yahoo.com");
//                            "ezattelli@gmail.com");
                } catch (Exception e) {
                    Log.e("SendMail", e.getMessage(), e);
                }


            }
        }).start();
    }


}
