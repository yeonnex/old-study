package com.example.designpatterns._08_bridge.java;

import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class BridgeInSpring {
    public static void main(String[] args) {
        MailSender javaMailSender = new JavaMailSenderImpl();

    }
}
