package com.srivatsan177.switter.users.Users.controllers;

import com.mongodb.MongoWriteException;
import com.srivatsan177.switter.users.Users.exceptions.ApplicationExceptions;
import com.srivatsan177.switter.users.Users.models.User;
import com.srivatsan177.switter.users.Users.repositories.UserRepository;
import com.srivatsan177.switter.users.Users.request.UserSigninRequestModel;
import com.srivatsan177.switter.users.Users.request.UserSignupRequestModel;
import com.srivatsan177.switter.users.Users.response.JWTResponseModel;
import com.srivatsan177.switter.users.Users.utils.JwtService;
import com.srivatsan177.switter.users.Users.utils.PasswordHasher;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/user")
public class UsersController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/signup")
    public ResponseEntity<JWTResponseModel> signup(@Valid @RequestBody UserSignupRequestModel userSignup) throws NoSuchAlgorithmException, ApplicationExceptions {

        User user = new User(userSignup.getUsername(), PasswordHasher.hash(userSignup.getPassword()), userSignup.getEmail(), userSignup.getDob());
        // check is user already exists

        User userFromDB = userRepository.findOneByUsername(user.getUsername()).orElseGet(() -> null);
        if(userFromDB == null) {
            userRepository.save(user);
        } else {
            throw new ApplicationExceptions("Username already exists");
        }

        return ResponseEntity.ok(new JWTResponseModel(jwtService.generateJwt(user)));
    }

    @PostMapping("/signin")
    public ResponseEntity<JWTResponseModel> signin(@Valid @RequestBody UserSigninRequestModel userSigninRequestModel) throws ApplicationExceptions, NoSuchAlgorithmException {
        User user = userRepository.findOneByUsername(userSigninRequestModel.getUsername()).orElseGet(() -> null);
        if(user == null) {
            throw new ApplicationExceptions("Username not found");
        }
        if(!user.getPassword().equals(PasswordHasher.hash(userSigninRequestModel.getPassword()))) {
            throw new ApplicationExceptions("Password is incorrect");
        }
        return ResponseEntity.ok(new JWTResponseModel(jwtService.generateJwt(user)));
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
