package nl.gemvision.assignment.domain.dto;

import java.time.LocalDateTime;

public class MessageDTO {

  private String username;
  private String text;
  private LocalDateTime timestamp;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

}
