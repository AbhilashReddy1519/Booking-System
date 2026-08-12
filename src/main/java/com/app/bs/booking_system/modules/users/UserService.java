package com.app.bs.booking_system.modules.users;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.bs.booking_system.modules.users.dto.UserCreateDTO;
import com.app.bs.booking_system.modules.users.dto.UserCreatedResponseDTO;
import com.app.bs.booking_system.utils.APIResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRespository;
  private final PasswordEncoder passwordEncoder;
  public APIResponse<?> createUser(UserCreateDTO dto) {
    Optional<User> user = userRespository.findByEmail(dto.getEmail());
    if(user.isPresent()) {
      return APIResponse.error("User with email already exists");
    }

    User newUser = User.builder()
        .email(dto.getEmail())
        .passwordHash(passwordEncoder.encode(dto.getPassword()))
        .role(Role.CUSTOMER)
        .build();
    newUser = userRespository.save(newUser);
      
    return APIResponse.success(
      UserCreatedResponseDTO.builder()
      .email(newUser.getEmail())
      .userId(newUser.getId())
      .role(newUser.getRole().toString())
      .build(), 
      "User created successfully");
  }
}
