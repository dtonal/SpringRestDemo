package de.dtonal.payroll.rest.controller.auth;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.dtonal.payroll.events.UserSignedUpEvent;
import de.dtonal.payroll.model.auth.Role;
import de.dtonal.payroll.model.auth.User;
import de.dtonal.payroll.repository.RoleRepository;
import de.dtonal.payroll.repository.UserRepository;
import de.dtonal.payroll.rest.controller.MessageResponse;
import de.dtonal.payroll.security.jwt.JwtProvider;
import de.dtonal.payroll.security.user.UserPrincipal;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtProvider tokenProvider;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@PostMapping("/signin")
	public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginDTO login) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();
		String jwt = tokenProvider.generateToken(authentication);
		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername()));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (!userRepository.findByUsername(signUpRequest.getUsername()).isEmpty()) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}
		// Create new user's account
		User user = new User();
		user.setUsername(signUpRequest.getUsername());
		user.setPassword(encoder.encode(signUpRequest.getPassword()));
		user.setEmail(signUpRequest.getEmail());

		List<Role> roles = new ArrayList<>();
		roles.add(roleRepository.getById(1L));
		user.setRoles(roles);
		user.setEnabled(false);
		userRepository.save(user);

		eventPublisher.publishEvent(new UserSignedUpEvent(user));
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
}