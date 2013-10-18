package cz.jiripinkas.example.mailer.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vaadin.dialogs.ConfirmDialog;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import cz.jiripinkas.example.mailer.MyCustomMenuBarView;
import cz.jiripinkas.example.mailer.entity.Email;
import cz.jiripinkas.example.mailer.security.SpringSecurityHelper;
import cz.jiripinkas.example.mailer.service.EmailService;

@SuppressWarnings("serial")
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MailsTableView extends MyCustomMenuBarView {

	@Autowired
	private EmailService emailService;

	private TextArea emailDetail;
	private Label emailTableLabel;
	private Label emailBodyLabel;
	private Table emailsTable;
	private BeanContainer<Integer, Email> beansContainer;

	@Override
	public void enter(ViewChangeEvent event) {
		String principalName = SpringSecurityHelper.getPrincipalName();
		List<Email> emails = emailService.findAll(principalName);
		beansContainer = new BeanContainer<Integer, Email>(Email.class);
		beansContainer.setBeanIdProperty("emailId");
		beansContainer.addAll(emails);
		emailsTable.setContainerDataSource(beansContainer);
		// Show just some columns
		emailsTable.setVisibleColumns("emailTo", "sentDate", "subject");
		emailsTable.setColumnHeader("emailTo", "to");
		emailsTable.setColumnHeader("sentDate", "sent date");
	}

	@Override
	protected Layout buildLayout() {
		VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);

		emailTableLabel = new Label("Sent emails:");
		layout.addComponent(emailTableLabel);

		emailsTable = new Table();
		emailsTable.setWidth("30%");
		emailsTable.setSelectable(true);
		emailsTable.setImmediate(true);
		layout.addComponent(emailsTable);

		emailBodyLabel = new Label("Email body:");
		layout.addComponent(emailBodyLabel);

		emailDetail = new TextArea();
		emailDetail.setWidth("30%");
		layout.addComponent(emailDetail);

		return layout;
	}

	@Override
	protected void setListeners() {

		emailsTable.addValueChangeListener(new Property.ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (emailsTable.getValue() != null) {
					int selectedEmailId = Integer.parseInt(emailsTable
							.getValue().toString());
					Email email = emailService.findOne(selectedEmailId);
					emailDetail.setValue(email.getBody());
				}
			}
		});

		emailsTable.addActionHandler(new Handler() {

			private Action actionDelete = new Action("Delete");
			private Action actionDetail = new Action("Body");

			@Override
			public void handleAction(Action action, Object sender, final Object target) {
				if (action == actionDelete) {
					final Integer emailId = (Integer) target;
					if (emailId != null) {
						// ConfirmDialog plugin
						ConfirmDialog.show(getUI(), "Really delete?",
								new ConfirmDialog.Listener() {

									public void onClose(ConfirmDialog dialog) {
										if (dialog.isConfirmed()) {
											beansContainer.removeItem(target);
											emailService.delete(emailId);
										}
									}
								});
					}
				} else if (action == actionDetail) {
					Integer emailId = (Integer) target;
					if (emailId != null) {
						HorizontalLayout layout = new HorizontalLayout();
						TextArea textArea = new TextArea();
						textArea.setSizeFull();
						textArea.setValue(emailService.findOne(emailId)
								.getBody());
						layout.addComponent(textArea);
						layout.setSizeFull();
						Window window = new Window();
						window.setContent(layout);
						window.center();
						window.setWidth("600px");
						window.setHeight("400px");
						getUI().addWindow(window);
					}
				}
			}

			@Override
			public Action[] getActions(Object target, Object sender) {
				return new Action[] { actionDelete, actionDetail };
			}
		});

	}
}
