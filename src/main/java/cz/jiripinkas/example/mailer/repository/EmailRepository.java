package cz.jiripinkas.example.mailer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cz.jiripinkas.example.mailer.entity.Email;

public interface EmailRepository extends JpaRepository<Email, Integer> {

	List<Email> findByUserName(String username);

}
