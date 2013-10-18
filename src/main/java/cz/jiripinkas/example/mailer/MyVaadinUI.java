package cz.jiripinkas.example.mailer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

import cz.jiripinkas.example.mailer.view.MailsTableView;
import cz.jiripinkas.example.mailer.view.SendEmailView;
import cz.jiripinkas.example.mailer.view.UserDetailsView;
import cz.jiripinkas.example.mailer.view.UsersTableView;

@Theme("mytheme")
@SuppressWarnings("serial")
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MyVaadinUI extends UI {

	/*
	 * All views
	 */
	public static final String VIEW_ALL_EMAILS = "";
	public static final String VIEW_SEND_EMAIL = "send-email";
	public static final String VIEW_CONFIGURATION = "configuration";
	public static final String VIEW_USERS = "users";

	@Autowired
	private MailsTableView mailsTableView;

	@Autowired
	private SendEmailView sendEmailView;
	
	@Autowired
	private UserDetailsView userDetailsView;
	
	@Autowired
	private UsersTableView usersTableView;

	Navigator navigator;

	@Override
	protected void init(VaadinRequest request) {
		navigator = new Navigator(this, this);
		navigator.addView(VIEW_SEND_EMAIL, sendEmailView);
		navigator.addView(VIEW_ALL_EMAILS, mailsTableView);
		navigator.addView(VIEW_CONFIGURATION, userDetailsView);
		navigator.addView(VIEW_USERS, usersTableView);
	}

}
