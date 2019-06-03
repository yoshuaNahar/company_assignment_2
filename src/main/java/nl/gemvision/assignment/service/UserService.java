package nl.gemvision.assignment.service;

import java.util.Collections;
import nl.gemvision.assignment.domain.Group;
import nl.gemvision.assignment.domain.Role;
import nl.gemvision.assignment.domain.User;
import nl.gemvision.assignment.domain.dto.UserCredentialsDTO;
import nl.gemvision.assignment.repository.GroupRepository;
import nl.gemvision.assignment.repository.UserRepository;
import nl.gemvision.assignment.security.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private static final Logger logger = LoggerFactory.getLogger(UserService.class);

  private GroupRepository groupRepository;
  private UserRepository userRepository;
  private PasswordEncoder passwordEncoder;
  private JwtTokenProvider jwtTokenProvider;
  private AuthenticationManager authenticationManager;

  @Autowired
  public UserService(UserRepository userRepository,
      GroupRepository groupRepository,
      PasswordEncoder passwordEncoder,
      JwtTokenProvider jwtTokenProvider,
      AuthenticationManager authenticationManager) {
    this.userRepository = userRepository;
    this.groupRepository = groupRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtTokenProvider = jwtTokenProvider;
    this.authenticationManager = authenticationManager;
  }

  public User signupUser(UserCredentialsDTO userCredentialsDTO) {
    User user = new User();
    user.setUsername(userCredentialsDTO.getUsername());
    user.setPassword(passwordEncoder.encode(userCredentialsDTO.getPassword()));

    Group defaultGroup = groupRepository.findById(1L).orElseThrow(IllegalStateException::new);
    user.setGroups(Collections.singletonList(defaultGroup));

    user.setRoles(Collections.singletonList(Role.USER));

    logger.info("username: {}", userCredentialsDTO.getUsername());

    return userRepository.save(user);
  }

  public User loginUser(UserCredentialsDTO userCredentialsDTO) {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userCredentialsDTO.getUsername(), userCredentialsDTO.getPassword()));

    User user = userRepository.findByUsername(userCredentialsDTO.getUsername())
        .orElseThrow(() -> new IllegalArgumentException("Incorrect login! (username)"));

    user.setJwt(jwtTokenProvider.generateJwt(user));

    return user;
  }

  public void deleteUserByName() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    userRepository.deleteByUsername(authentication.getName());
  }

  public User getLoggedInUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return userRepository.findByUsername(authentication.getName())
        .orElseThrow(IllegalStateException::new);
  }

}
