package cz.jiripinkas.example.mailer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cz.jiripinkas.example.mailer.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findByName(String name);

}
