package com.trc.services;

import java.util.Random;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService 
{
	private JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) 
    {
        this.javaMailSender=javaMailSender;
    }

    public void sendMail(String toEmail, String subject, String message) 
    {

        SimpleMailMessage mailMessage=new SimpleMailMessage();

        mailMessage.setTo(toEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailMessage.setFrom("contact@peoplesbestbuy.com");

        javaMailSender.send(mailMessage);
    }
	
	
    public int generateRandomIntIntRange(int min, int max) 
    {
        Random r=new Random();
        return r.nextInt((max-min)+1)+ min;
    }
    
}
