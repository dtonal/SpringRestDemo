package de.dtonal.payroll.security.user;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import de.dtonal.payroll.model.auth.User;
import de.dtonal.payroll.repository.UserRepository;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User foundByUsername = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("No user found with username " + username));
		return UserMapper.map(foundByUsername);
	}

}
