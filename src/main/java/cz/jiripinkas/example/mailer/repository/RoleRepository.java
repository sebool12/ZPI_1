package cz.jiripinkas.example.mailer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cz.jiripinkas.example.mailer.entity.Role;
import cz.jiripinkas.example.mailer.entity.Role.ROLE_TYPE;

public interface RoleRepository extends JpaRepository<Role, Integer> {

	Role findByName(ROLE_TYPE name);

}
