package com.example.test.service;

import com.example.test.model.User;
import com.example.test.repository.UserRepo;
//import org.springframework.beans.factory.annotation.Autowired;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {

    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public void saveUser(User user) {
        userRepo.save(user);
    }


    @Value("553PecMkecG4cqGRXYtP2J3ygcP6xXQYyPYPPtSvO3d7NDPQ4WtSzOby3kXfrPmE9zJ7mqlhonFI9yyhN3KVOw")
//    @Value("${jwt.secret}")
    private String jwtSecret;

    public String generateJWT(User user) {
        // Set the JWT expiration time (2 hrs)
        long expirationTimeInMillis = System.currentTimeMillis() + (60 * 60 * 8000);

        // Build the JWT
        String token = Jwts.builder()
                .setSubject(user.getUsername())
                .claim("role", user.getRole())
                .setExpiration(new Date(expirationTimeInMillis))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
        return token;
    }

    public boolean verifyToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true; // Token is valid
        } catch (JwtException | IllegalArgumentException e) {
            return false; // Token is invalid or has expired
        }
    }

    public ResponseEntity<String> signIn(String username, String password) {
        User user = userRepo.findByUsername(username);

//        if (user == null || !password.equals(user.getPassword())) {
        if (user == null || !user.checkPassword(password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }

        String token = generateJWT(user);

        return ResponseEntity.ok(token);
    }

    public boolean isAdminRole(String username) {
        User user = findByUsername(username);
        return user != null && "Admin".equals(user.getRole());
    }

}
