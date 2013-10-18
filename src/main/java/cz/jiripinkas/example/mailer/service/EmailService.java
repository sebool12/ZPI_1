package cz.jiripinkas.example.mailer.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.jiripinkas.example.mailer.entity.Email;
import cz.jiripinkas.example.mailer.repository.EmailRepository;

@Service
@Transactional
public class EmailService {
	
	@Autowired
	private EmailRepository emailRepository;

	public Email save(Email email) {
		email.setSentDate(new Date());
		return emailRepository.save(email);
	}

	public List<Email> findAll() {
		return emailRepository.findAll();
	}
	
	public List<Email> findAll(String username) {
		return emailRepository.findByUserName(username);
	}
	
	public Email findOne(int emailId) {
		return emailRepository.findOne(emailId);
	}
	
	public void delete(Integer emailId) {
		emailRepository.delete(emailId);
	}
}
