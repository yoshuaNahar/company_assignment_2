package nl.gemvision.assignment.controller;

import nl.gemvision.assignment.domain.dto.AddGroupDTO;
import nl.gemvision.assignment.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class GroupController {

  private GroupService groupService;

  @Autowired
  public GroupController(GroupService groupService) {
    this.groupService = groupService;
  }

  @PostMapping("/api/group")
  public AddGroupDTO postGroup(@RequestBody AddGroupDTO addGroupDTO) {
    return groupService.addGroup(addGroupDTO);
  }

}
