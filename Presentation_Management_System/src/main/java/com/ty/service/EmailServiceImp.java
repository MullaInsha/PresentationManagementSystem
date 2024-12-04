package com.ty.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ty.dto.EmailDto;

@Service
public class EmailServiceImp implements EmailService{
@Autowired
private JavaMailSender javaMailSender;

@Override
public void test(EmailDto d) {
	// TODO Auto-generated method stub
	SimpleMailMessage m=new SimpleMailMessage();
	m.setTo(d.getTo());
	m.setCc(d.getCc());
	m.setSubject(d.getSubject());
	m.setText(d.getBody());
	
	javaMailSender.send(m);
}



}