package com.rubnikovich.provider;

import com.rubnikovich.provider.service.UsersService;
import jakarta.mail.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.User;

@SpringBootApplication
public class ProviderApplication {

    public static void main(String[] args) throws MessagingException {
        SpringApplication.run(ProviderApplication.class, args);

//        MailSender mailSender = new MailSender();
//        mailSender.sendMail("liftedfish@gmail.com", "custom message");

    }
}
