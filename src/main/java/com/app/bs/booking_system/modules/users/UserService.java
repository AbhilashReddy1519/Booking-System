package com.app.bs.booking_system.modules.users;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.bs.booking_system.exceptions.UnauthorizedException;
import com.app.bs.booking_system.modules.users.dto.LoginDTO;
import com.app.bs.booking_system.modules.users.dto.LoginResponse;
import com.app.bs.booking_system.modules.users.dto.UserCreateDTO;
import com.app.bs.booking_system.modules.users.dto.UserCreatedResponseDTO;
import com.app.bs.booking_system.security.JWTService;
import com.app.bs.booking_system.utils.APIResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JWTService jwtService;

  public APIResponse<?> createUser(UserCreateDTO dto) {
    Optional<User> user = userRepository.findByEmail(dto.getEmail());
    if(user.isPresent()) {
      return APIResponse.error("User with email already exists");
    }

    User newUser = User.builder()
        .email(dto.getEmail())
        .passwordHash(passwordEncoder.encode(dto.getPassword()))
        .role(Role.CUSTOMER)
        .build();
    newUser = userRepository.save(newUser);
      
    return APIResponse.success(
      UserCreatedResponseDTO.builder()
      .email(newUser.getEmail())
      .userId(newUser.getId())
      .role(newUser.getRole().toString())
      .build(), 
      "User created successfully");
  }

  public APIResponse<?> login(LoginDTO dto) {
    User user = userRepository.findByEmail(dto.getEmail())
      .orElseThrow(() -> new UnauthorizedException("Invalid credentials"));

    if (!passwordEncoder.matches(dto.getPassword(), user.getPasswordHash())) {
      throw new UnauthorizedException("Invalid credentials");
    }
    String token = jwtService.generateToken(user.getId(),user.getRole());

    return APIResponse.success(
      LoginResponse.builder()
        .email(user.getEmail())
        .token(token)
        .build()
      , "User logged in successfully");
  }
}
