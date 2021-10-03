package de.dtonal.payroll.security.user;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import de.dtonal.payroll.model.auth.User;

public class UserMapper {
	public static UserPrincipal map(User user) {
		UserPrincipal userPrincipal = new UserPrincipal();
		userPrincipal.setEnabled(user.isEnabled());
		userPrincipal.setPassword(user.getPassword());
		userPrincipal.setUsername(user.getUsername());
		userPrincipal.setUserId(user.getId());

		List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName())).collect(Collectors.toList());
		userPrincipal.setAuthorities(authorities);
		return userPrincipal;

	}
}
