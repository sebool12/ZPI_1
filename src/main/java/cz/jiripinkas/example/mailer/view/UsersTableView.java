package cz.jiripinkas.example.mailer.view;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import com.vaadin.data.util.BeanContainer;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import cz.jiripinkas.example.mailer.MyCustomMenuBarView;
import cz.jiripinkas.example.mailer.entity.User;
import cz.jiripinkas.example.mailer.service.UserService;
import cz.jiripinkas.example.mailer.view.dto.UserDto;



@SuppressWarnings("serial")
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UsersTableView extends MyCustomMenuBarView {

	@Autowired
	private UserService userService;
	private BeanContainer<Integer, UserDto> beansContainer;
	private Table table;

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Override
	public void enter(ViewChangeEvent event) {
//		if(!SpringSecurityHelper.hasRole("ROLE_ADMIN")) {
//			return;
//		}
		List<User> list = userService.findAll();
		List<UserDto> listDtos = new ArrayList<UserDto>();
		for (User user : list) {
			listDtos.add(new UserDto(user.getUserId(), user.getName(), user.getEmail(), user.isEnabled()));
		}
		beansContainer = new BeanContainer<Integer, UserDto>(UserDto.class);
		beansContainer.setBeanIdProperty("userId");
		beansContainer.addAll(listDtos);
		table.setContainerDataSource(beansContainer);
		table.setVisibleColumns("name", "email", "enabled");
	}

	@Override
	protected Layout buildLayout() {
		VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		Label lbl = new Label("Users:");
		layout.addComponent(lbl);
		table = new Table();
		table.setSelectable(true);
		table.setImmediate(true);
		layout.addComponent(table);
		return layout;
	}

	@Override
	protected void setListeners() {
	}

}
