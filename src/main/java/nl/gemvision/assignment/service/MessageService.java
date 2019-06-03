package nl.gemvision.assignment.service;

import java.time.LocalDateTime;
import nl.gemvision.assignment.domain.Message;
import nl.gemvision.assignment.domain.User;
import nl.gemvision.assignment.domain.dto.MessageDTO;
import nl.gemvision.assignment.repository.MessageRepository;
import nl.gemvision.assignment.repository.UserRepository;
import nl.gemvision.assignment.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

  private MessageRepository messageRepository;
  private UserRepository userRepository;
  private SimpMessagingTemplate simpMessagingTemplate;
  private JwtTokenProvider jwtTokenProvider;

  @Autowired
  public MessageService(MessageRepository messageRepository,
      UserRepository userRepository,
      SimpMessagingTemplate simpMessagingTemplate,
      JwtTokenProvider jwtTokenProvider) {
    this.messageRepository = messageRepository;
    this.userRepository = userRepository;
    this.simpMessagingTemplate = simpMessagingTemplate;
    this.jwtTokenProvider = jwtTokenProvider;
  }

  public Message addMessage(Message message) {
    return messageRepository.save(message);
  }

  public void sendMessage(Message message) {
    message.setTimestamp(LocalDateTime.now());

    String topic = "/topic/" + message.getGroupName().replace(" ", "");

    simpMessagingTemplate.convertAndSend(topic, convertMessageToMessageDTO(message));
  }

  private MessageDTO convertMessageToMessageDTO(Message message) {
    MessageDTO messageDTO = new MessageDTO();
    messageDTO.setText(message.getMessage());
    messageDTO.setUsername(message.getUser().getUsername());
    messageDTO.setTimestamp(message.getTimestamp());

    return messageDTO;
  }

  public boolean userIsAuthenticated(Message message) {
    if (jwtTokenProvider.validateJws(message.getJwt())) {
      String username = jwtTokenProvider.getUsername(message.getJwt());
      User user = userRepository.findByUsername(username).orElseThrow(IllegalStateException::new);
      message.setUser(user);

      return true;
    }
    return false;
  }

}
