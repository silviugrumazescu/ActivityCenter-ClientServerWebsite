package com.ActivityCenterServer.api;

import com.ActivityCenterServer.database.repository.UserRepository;
import com.ActivityCenterServer.model.User;
import com.ActivityCenterServer.model.UserDetailsPrincipal;
import com.ActivityCenterServer.utils.JwtUtils;
import com.ActivityCenterServer.web.payload.JwtResponse;
import com.ActivityCenterServer.web.payload.MessageResponse;
import com.ActivityCenterServer.web.payload.SigninRequest;
import com.ActivityCenterServer.web.payload.SignupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(maxAge = 3600)
@RestController
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("api/auth/signin")
    public ResponseEntity<?> signIn(@RequestBody SigninRequest signinRequest) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getUsername(), signinRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsPrincipal userDetails = (UserDetailsPrincipal) authentication.getPrincipal();
        ArrayList<String> roles =(ArrayList<String>) userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getUsername(),
                userDetails.getEmail(),
                userDetails.getFirstname(),
                userDetails.getLastname(),
                roles));
    }

    @PostMapping("api/auth/signup")
    public ResponseEntity<?> signUp(@RequestBody SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signupRequest.getFirstname(),
                            signupRequest.getLastname(),
                            passwordEncoder.encode(signupRequest.getPassword()),
                            signupRequest.getUsername(),
                            signupRequest.getEmail(),
                            false);

        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

}
