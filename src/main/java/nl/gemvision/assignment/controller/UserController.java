package nl.gemvision.assignment.controller;

import nl.gemvision.assignment.domain.User;
import nl.gemvision.assignment.domain.dto.UserCredentialsDTO;
import nl.gemvision.assignment.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class UserController {

  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  private UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/signup")
  public User signup(@RequestBody UserCredentialsDTO userCredentialsDTO) {
    logger.info("signup controller");

    return userService.signupUser(userCredentialsDTO);
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody UserCredentialsDTO userCredentialsDTO) {
    User user = userService.loginUser(userCredentialsDTO);

    return ResponseEntity.ok(user);
  }

  @GetMapping("/api/user")
  public User getUser() {
    return userService.getLoggedInUser();
  }

  @DeleteMapping("/api/user")
  public void deleteUser() {
    userService.deleteUserByName();
  }

}
