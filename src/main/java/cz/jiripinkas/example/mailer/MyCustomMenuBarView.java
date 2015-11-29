package cz.jiripinkas.example.mailer;

import com.vaadin.navigator.View;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Layout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.VerticalLayout;

import cz.jiripinkas.example.mailer.security.SpringSecurityHelper;

@SuppressWarnings("serial")
public abstract class MyCustomMenuBarView extends CustomComponent implements
		View {

	protected VerticalLayout mainLayout;

	public MyCustomMenuBarView() {
		mainLayout = new VerticalLayout();
		mainLayout.addComponent(constructMenuBar());
		setCompositionRoot(mainLayout);

		// add rest of the page. Do not remove!!!
		mainLayout.addComponent(buildLayout());
		setListeners();
	}

	private MenuBar constructMenuBar() {
		MenuBar menuBar1 = new MenuBar();
		menuBar1.setWidth("100%");

		MenuItem mainItem = menuBar1.addItem("Main", null);
		mainItem.addItem("User details", new MenuBar.Command() {
			@Override
			public void menuSelected(MenuItem selectedItem) {
				getUI().getNavigator()
						.navigateTo(MyVaadinUI.VIEW_CONFIGURATION);
			}
		});

		mainItem.addItem("Logout", new MenuBar.Command() {
			@Override
			public void menuSelected(MenuItem selectedItem) {
				getUI().getPage().open("/logout", null);
			}
		});

		MenuItem dataItem = menuBar1.addItem("Data", null);

		dataItem.addItem("All emails", new MenuBar.Command() {
			@Override
			public void menuSelected(MenuItem selectedItem) {
				getUI().getNavigator().navigateTo(MyVaadinUI.VIEW_ALL_EMAILS);
			}
		});

		dataItem.addItem("Send email", new MenuBar.Command() {
			@Override
			public void menuSelected(MenuItem selectedItem) {
				getUI().getNavigator().navigateTo(MyVaadinUI.VIEW_SEND_EMAIL);
			}
		});

		if (SpringSecurityHelper.hasRole("ROLE_ADMIN")) {
			dataItem.addItem("All users", new MenuBar.Command() {
				@Override
				public void menuSelected(MenuItem selectedItem) {
					getUI().getNavigator().navigateTo(MyVaadinUI.VIEW_USERS);
				}
			});
		}

		return menuBar1;
	}

	protected abstract Layout buildLayout();

	protected abstract void setListeners();

}
