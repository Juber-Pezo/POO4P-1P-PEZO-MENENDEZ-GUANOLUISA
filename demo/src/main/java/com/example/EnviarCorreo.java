package com.example;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;
/**
 * Clase EnviarCorreo
 *
 * Permite enviar correos electronicos utlizando el protocolo SMTP mediante Gmail.
 * Es utilizada para notificar a los clientes y repartidores sobre el estado de sus pedidos.
 *
 *@ Guanoluisa Jelthon
 */
public class EnviarCorreo {
    
    public static void enviarCorreo(String destinatario, String asunto, String cuerpo) {
        final String remitente = "sistemadeventas4@gmail.com"; // correo creado
        final String clave = "zgyd klge pwrm cuuc"; // contrasseña de aplicacion
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remitente, clave);
            }
        });

        try {
            Message mensaje = new MimeMessage(session);
            mensaje.setFrom(new InternetAddress(remitente));
            mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            mensaje.setSubject(asunto);
            mensaje.setText(cuerpo);

            Transport.send(mensaje);
            System.out.println("Correo enviado a: " + destinatario);
        } catch (MessagingException e) {
            System.out.println("Error al enviar correo: " + e.getMessage());
        }
    }
}
