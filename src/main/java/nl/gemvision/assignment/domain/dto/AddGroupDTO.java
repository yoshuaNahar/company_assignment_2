package nl.gemvision.assignment.domain.dto;

import java.util.ArrayList;
import java.util.List;

public class AddGroupDTO {

  private Long id;
  private String name;
  private List<Long> userIds = new ArrayList<>();
  private List<String> userNames = new ArrayList<>();

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Long> getUserIds() {
    return userIds;
  }

  public void setUserIds(List<Long> userIds) {
    this.userIds = userIds;
  }

  public List<String> getUserNames() {
    return userNames;
  }

  public void setUserNames(List<String> userNames) {
    this.userNames = userNames;
  }

}
