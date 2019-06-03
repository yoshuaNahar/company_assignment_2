package nl.gemvision.assignment.controller;

import nl.gemvision.assignment.domain.Message;
import nl.gemvision.assignment.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class MessageController {

  private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

  private MessageService messageService;

  @Autowired
  public MessageController(MessageService messageService) {
    this.messageService = messageService;
  }

//  @MessageMapping("/user")
//  @SendTo("/topic/default")
//  public Message receiveDefaultRoomMessage(@Payload Message message) {
//    logger.info("{}", message);
//
//    return messageService.addMessage(message);
//  }

  @MessageMapping("/user")
  public void receivePrivateRoomMessage(@Payload Message message) {
    logger.info("{}", message.getMessage());

    if (messageService.userIsAuthenticated(message)) {
      messageService.sendMessage(message);
    }
    messageService.addMessage(message);
  }

}
