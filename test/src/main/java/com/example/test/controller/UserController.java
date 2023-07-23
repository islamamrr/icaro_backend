package com.example.test.controller;
import com.example.test.model.User;
import com.example.test.model.UserCredentials;
import com.example.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(value = {"http://localhost:63342", "http://www.dksolidwaste.com"}, allowCredentials = "true")
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }



    @PostMapping("/signin")
    public ResponseEntity<String> signIn(@RequestBody UserCredentials credentials) {
        String username = credentials.getUsername();
        String password = credentials.getPassword();

        return userService.signIn(username, password);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody UserCredentials credentials, HttpServletRequest request) {
        String adminUsername = request.getHeader("Admin-Username");
        String adminPassword = request.getHeader("Admin-Password");

        // Sign in the admin user to validate the credentials
        ResponseEntity<String> signInResponse = userService.signIn(adminUsername, adminPassword);
        if (signInResponse.getStatusCode() != HttpStatus.OK) {
            return signInResponse;
        }

        if (!userService.isAdminRole(adminUsername)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only admins can create new users");
        }

        String username = credentials.getUsername();
        String password = credentials.getPassword();
        String role = credentials.getRole();
        String siteNo = credentials.getSiteNo();

        User existingUser = userService.findByUsername(username);
        if (existingUser != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username is already taken");
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setRole(role);
        newUser.setSiteNo(siteNo);

        // Save the new user
        userService.saveUser(newUser);

        // Generate JWT
        String token = userService.generateJWT(newUser);

        // Return the JWT to the client
        return ResponseEntity.ok(token);
    }

    @GetMapping("/protected-route")
    public ResponseEntity<String> isAuthorized(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring("Bearer ".length());

        if (userService.verifyToken(token)) {
            return ResponseEntity.ok("Authorized");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
    }
}
