package cz.jiripinkas.example.mailer.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

import cz.jiripinkas.example.mailer.MyCustomMenuBarView;
import cz.jiripinkas.example.mailer.MyVaadinUI;
import cz.jiripinkas.example.mailer.entity.Email;
import cz.jiripinkas.example.mailer.service.EmailService;

@SuppressWarnings("serial")
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SendEmailView extends MyCustomMenuBarView {

	@Autowired
	private EmailService emailService;

	private Button btnSend;
	private TextArea body;
	private TextField subject;
	private TextField emailTo;
	private Label heading;
	private FieldGroup fieldGroup;

	@Override
	public void enter(ViewChangeEvent event) {
		fieldGroup = new BeanFieldGroup<Email>(Email.class);
		Email email = new Email();
		fieldGroup.setItemDataSource(new BeanItem<Email>(email));
		fieldGroup.bindMemberFields(this);
	}

	@Override
	protected Layout buildLayout() {
		FormLayout layout = new FormLayout();
		layout.setMargin(true);

		heading = new Label("New email:");
		layout.addComponent(heading);

		emailTo = new TextField("to");
		emailTo.setWidth("30%");
		layout.addComponent(emailTo);

		subject = new TextField("subject");
		subject.setWidth("30%");
		layout.addComponent(subject);

		body = new TextArea("body");
		body.setWidth("30%");
		layout.addComponent(body);

		btnSend = new Button("send");
		layout.addComponent(btnSend);

		return layout;
	}

	@Override
	protected void setListeners() {
		btnSend.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					fieldGroup.commit(); // performs bean validation

					@SuppressWarnings("unchecked")
					BeanItem<Email> beanItem = (BeanItem<Email>) fieldGroup
							.getItemDataSource();
					Email email = beanItem.getBean();

					emailService.save(email);
					Notification.show("email sent");
					getUI().getNavigator().navigateTo(
							MyVaadinUI.VIEW_ALL_EMAILS);
				} catch (CommitException e) {
					Notification.show(e.getMessage(), Type.ERROR_MESSAGE);
				}
			}
		});
	}

}
