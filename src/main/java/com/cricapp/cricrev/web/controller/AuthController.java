package com.cricapp.cricrev.web.controller;
import com.cricapp.cricrev.model.User;
import com.cricapp.cricrev.payload.LoginDto;
import com.cricapp.cricrev.payload.SignUpDto;
import com.cricapp.cricrev.repository.UserRepository;
import com.cricapp.cricrev.response.AuthResponse;
import com.cricapp.cricrev.utility.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserRepository userRepository;

   /* @Autowired
    private RoleRepository roleRepository;*/

    @Autowired
    private PasswordEncoder passwordEncoder;
    @CrossOrigin
    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody LoginDto loginDto, HttpServletRequest request){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
        UsernamePasswordAuthenticationToken token= new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword());
        token.setDetails(new WebAuthenticationDetails(request));
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        try {
            String jwt = jwtTokenUtil.generateToken(loginDto.getUsernameOrEmail());
            return new ResponseEntity<>(new AuthResponse(jwt, "User signed-in successfully!."), HttpStatus.OK);
        }
        catch(Exception e){

            return new ResponseEntity<>(new AuthResponse("", "User signed-in successfully!."), HttpStatus.NON_AUTHORITATIVE_INFORMATION);
        }
    }
    @CrossOrigin
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){

        // add check for username exists in a DB
        Optional<User> userEx =userRepository.findByUsername(signUpDto.getUsername());
        if(userEx.isPresent()){
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }
        Optional<User> emailEx =userRepository.findByUsername(signUpDto.getEmail());
        if(emailEx.isPresent()){
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        // create user object
        User user = new User();
        user.setUsername(signUpDto.getUsername());
        user.setFirstname(signUpDto.getFirstname());
        user.setLastname(signUpDto.getLastname());
        user.setEmail(signUpDto.getEmail());
        user.setDob(signUpDto.getDob());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);

    }
}