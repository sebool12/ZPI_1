package cz.jiripinkas.example.mailer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import cz.jiripinkas.example.mailer.entity.Email;
import cz.jiripinkas.example.mailer.entity.Role;
import cz.jiripinkas.example.mailer.entity.User;
import cz.jiripinkas.example.mailer.entity.Role.ROLE_TYPE;

@Service
public class InitDbService {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private EmailService emailService;

    // initialize database each day
    @Scheduled(fixedDelay = 86400000)
	public void init() {
		// init database and create test database
		System.out.println("*** CREATE TEST DATABASE START ***");
		int roleUserId = initDatabase();
		createGuest(roleUserId);
		createJirka(roleUserId);
		System.out.println("*** CREATE TEST DATABASE FINISH ***");
	}

	private int initDatabase() {
		Role roleAdmin = new Role();
		roleAdmin.setName(ROLE_TYPE.ROLE_ADMIN);
		roleAdmin = roleService.save(roleAdmin);

		Role roleUser = new Role();
		roleUser.setName(ROLE_TYPE.ROLE_USER);
		roleUser = roleService.save(roleUser);

		Role rolePremium = new Role();
		rolePremium.setName(ROLE_TYPE.ROLE_PREMIUM);
		rolePremium = roleService.save(rolePremium);

		User userAdmin = new User();
		userAdmin.setName("admin");
		userAdmin.setPassword("admin");
		userAdmin.setEmail("admin@email.com");
		userAdmin = userService.create(userAdmin);
		userService.assignRole(userAdmin.getUserId(), roleAdmin.getRoleId());
		userService.assignRole(userAdmin.getUserId(), roleUser.getRoleId());
		userService.assignRole(userAdmin.getUserId(), rolePremium.getRoleId());
		return roleUser.getRoleId();
	}

	private void createGuest(int roleUserId) {
		User userGuest = new User();
		userGuest.setName("guest");
		userGuest.setPassword("guest");
		userGuest.setEmail("guest@email.com");
		userGuest = userService.create(userGuest);
		userService.assignRole(userGuest.getUserId(), roleUserId);

		{
			Email email = new Email();
			email.setSubject("test from guest");
			email.setEmailTo("test@email.com");
			email.setBody("hello,\n\nI guest just sent an email!\n\nCheers, Guest");
			email.setUser(userGuest);
			emailService.save(email);
		}
		{
			Email email = new Email();
			email.setSubject("test 2 from guest");
			email.setEmailTo("test@email.com");
			email.setBody("Testing another email\n\nCheers, Guest");
			email.setUser(userGuest);
			emailService.save(email);
		}
	}

	private void createJirka(int roleUserId) {
		User userGuest = new User();
		userGuest.setName("jirka");
		userGuest.setPassword("jirka");
		userGuest.setEmail("jirka@email.com");
		userGuest = userService.create(userGuest);
		userService.assignRole(userGuest.getUserId(), roleUserId);

		{
			Email email = new Email();
			email.setSubject("test from jirka");
			email.setEmailTo("test@email.com");
			email.setBody("hello,\n\nI guest just sent an email!\n\nCheers, Jirka");
			email.setUser(userGuest);
			emailService.save(email);
		}
		{
			Email email = new Email();
			email.setSubject("test 2 from jirka");
			email.setEmailTo("test@email.com");
			email.setBody("Testing another email\n\nCheers, Jirka");
			email.setUser(userGuest);
			emailService.save(email);
		}
	}

}
