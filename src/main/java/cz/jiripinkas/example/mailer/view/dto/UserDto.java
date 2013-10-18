package cz.jiripinkas.example.mailer.view.dto;

public class UserDto {
	private Integer userId;
	private String name;
	private String email;
	private boolean enabled;

	public UserDto() {
	}

	public UserDto(Integer userId, String name, String email, boolean enabled) {
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.enabled = enabled;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}