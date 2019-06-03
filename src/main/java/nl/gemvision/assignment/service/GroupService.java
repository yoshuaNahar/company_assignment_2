package nl.gemvision.assignment.service;

import nl.gemvision.assignment.domain.Group;
import nl.gemvision.assignment.domain.dto.AddGroupDTO;
import nl.gemvision.assignment.repository.GroupRepository;
import nl.gemvision.assignment.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class GroupService {

  private static final Logger logger = LoggerFactory.getLogger(GroupService.class);

  private GroupRepository groupRepository;
  private UserRepository userRepository;

  @Autowired
  public GroupService(GroupRepository groupRepository,
      UserRepository userRepository) {
    this.groupRepository = groupRepository;
    this.userRepository = userRepository;
  }

  public AddGroupDTO addGroup(AddGroupDTO addGroupDTO) {
    Group group = new Group();
    group.setName(addGroupDTO.getName());

//    addGroupDTO.getUserIds().forEach(id -> {
//      User user = userRepository.findById(id).orElseThrow(IllegalStateException::new);
//      user.getGroups().add(group);
//
//      group.getUsers().add(user);
//    });

    // because front-end doesn't return users, add all users
    userRepository.findAll().forEach(user -> {
      user.getGroups().add(group);
      group.getUsers().add(user);

      addGroupDTO.getUserIds().add(user.getId());
      addGroupDTO.getUserNames().add(user.getUsername());
    });

    logger.info("group: {}", group.getUsers());
    groupRepository.save(group);

    return addGroupDTO;
  }

  private boolean isUserInGroup(Long groupId) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    return groupRepository.findById(groupId)
        .orElseThrow(IllegalStateException::new)
        .getUsers().stream()
        .anyMatch(user -> user.getUsername().equals(authentication.getName()));
  }

}
