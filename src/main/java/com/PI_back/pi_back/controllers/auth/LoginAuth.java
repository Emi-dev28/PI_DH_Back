package com.PI_back.pi_back.controllers.auth;

import com.PI_back.pi_back.dto.AuthenticationRequest;
import com.PI_back.pi_back.dto.AuthenticationResponse;
import com.PI_back.pi_back.dto.RegisterRequest;
import com.PI_back.pi_back.security.AuthenticationServiceImplement;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/auth/")
public class LoginAuth {

    @Autowired
    private final AuthenticationServiceImplement authenticationServiceImplement;

    @Autowired
    public LoginAuth(AuthenticationServiceImplement authenticationServiceImplement) {
        this.authenticationServiceImplement = authenticationServiceImplement;
    }


    @PostMapping("register")
    public ResponseEntity<AuthenticationResponse> registry(
            @RequestBody RegisterRequest registerRequest
    ){
        enviarCorreoConfirmacion(registerRequest.getName(), registerRequest.getEmail());
        return ResponseEntity.ok(authenticationServiceImplement.register(registerRequest));
    }
    private void enviarCorreoConfirmacion(String nombre, String destinatario) {
        final String userName = "dhtechnolgy@gmail.com"; //same fromMail
        final String password = "myjaitlcuazczkec";


        System.out.println("TLSEmail Start");
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

        jakarta.mail.Session session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected jakarta.mail.PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }



        });

        try{
            String link = "http://127.0.0.1:5173/auth/login";
            String content = "<h1>" + nombre +"</h1>" + "<p> Muchas gracias por registrarte en <b> DH TECHNOLOGY</b></p> <p>Ya puedes loguearte con tu email y contraseña en: " + link + "<br><br> <h3>DH TECHNOLOGY</h3>";

            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario, true));
            message.setSubject("Mail de Confirmacion");
            message.setText(content,"UTF-8", "html");
            System.out.println("sending...");
            Transport.send(message);
            System.out.println("Sent message successfully....");

        }catch (MessagingException me){
            System.out.println("Exception: "+me);

        }

    }





    @PostMapping("authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest authRequest
            ){
        return ResponseEntity.ok(authenticationServiceImplement.login(authRequest));
    }
    @PostMapping("refresh-token")
    public ResponseEntity<AuthenticationResponse> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        return ResponseEntity.ok(authenticationServiceImplement.refreshToken(request,response));
    }
}
