package cz.jiripinkas.example.mailer.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

/**
 * @author xpoft
 */
public class SpringSecurityHelper {
	public static boolean hasRole(String role) {
		if (SecurityContextHolder.getContext().getAuthentication() != null) {
			User user = (User) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			return user.getAuthorities().contains(
					new SimpleGrantedAuthority(role));
		}
		return false;
	}

	public static String getPrincipalName() {
		if (SecurityContextHolder.getContext().getAuthentication() != null) {
			return SecurityContextHolder.getContext().getAuthentication()
					.getName();
		}
		return null;
	}
}
