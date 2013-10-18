package cz.jiripinkas.example.mailer.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

import cz.jiripinkas.example.mailer.MyCustomMenuBarView;
import cz.jiripinkas.example.mailer.MyVaadinUI;
import cz.jiripinkas.example.mailer.entity.User;
import cz.jiripinkas.example.mailer.security.SpringSecurityHelper;
import cz.jiripinkas.example.mailer.service.UserService;

@SuppressWarnings("serial")
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UserDetailsView extends MyCustomMenuBarView {

	@Autowired
	private UserService userService;

	private PasswordField newPassword;
	private TextField name;
	private TextField email;
	private Label heading;
	private Button btnSend;
	private User user;

	@Override
	public void enter(ViewChangeEvent event) {
		user = userService.findOne(SpringSecurityHelper.getPrincipalName());
		name.setValue(user.getName());
		email.setValue(user.getEmail());
	}

	@Override
	protected FormLayout buildLayout() {
		FormLayout layout = new FormLayout();
		layout.setMargin(true);

		heading = new Label("User details:");
		layout.addComponent(heading);

		name = new TextField("name");
		name.setWidth("30%");
		layout.addComponent(name);

		newPassword = new PasswordField("new password");
		newPassword.setWidth("30%");
		layout.addComponent(newPassword);

		email = new TextField("email");
		email.setWidth("30%");
		layout.addComponent(email);

		btnSend = new Button("send");
		layout.addComponent(btnSend);

		return layout;
	}

	@Override
	protected void setListeners() {
		btnSend.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				user.setName(name.getValue());
				user.setEmail(email.getValue());
				if (newPassword.getValue().isEmpty()) {
					userService.update(user);
				} else {
					user.setPassword(newPassword.getValue());
					userService.updateWithNewPassword(user);
				}
				Notification.show("saved");
				getUI().getNavigator().navigateTo(MyVaadinUI.VIEW_SEND_EMAIL);
			}
		});
	}

}
