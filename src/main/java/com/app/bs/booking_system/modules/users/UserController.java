package com.app.bs.booking_system.modules.users;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.bs.booking_system.modules.users.dto.UserCreateDTO;
import com.app.bs.booking_system.utils.APIResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
  private final UserService userService;

  @PostMapping("/create")
  public APIResponse<?> createUser(@Valid @RequestBody UserCreateDTO dto) {
    return userService.createUser(dto);
  }
}
