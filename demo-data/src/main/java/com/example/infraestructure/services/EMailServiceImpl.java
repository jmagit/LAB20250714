package com.example.infraestructure.services;

import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.contracts.application.EMailService;

@Service
//@Primary
public class EMailServiceImpl implements EMailService {

	@Override
	@Async
	public void sendEmail(String to, String subject, String body) {
		// TODO Auto-generated method stub

	}

	@Override
	@Async
	public void sendEmailWithAttachment(String to, String subject, String body, String attachmentPath) {
		// TODO Auto-generated method stub

	}

}
