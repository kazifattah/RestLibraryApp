package org.example.restlibraryapp.controller.auth;

import org.example.restlibraryapp.entity.auth.MyUser;
import org.example.restlibraryapp.service.auth.MyUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for creating new users with specific roles that have access to specific endpoints
 */

@RestController
@RequestMapping("/library/")
public class RegistrationController {
    private MyUserService myUserService;

    public RegistrationController(MyUserService myUserService) {
        this.myUserService = myUserService;
    }

    @PostMapping("/register-user")
    public MyUser registerUser(@RequestBody MyUser user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        myUserService.saveUser(user);
        return user;
    }

    @PutMapping("/update-user/{user_id}")
    public ResponseEntity<MyUser> updateUser(@RequestBody MyUser user,  @PathVariable("user_id") long user_id) {
        myUserService.updateUser(user_id, user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/delete-user/{user_id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("user_id") long user_id) {
        myUserService.deleteUser(user_id);
        return new  ResponseEntity<>(HttpStatus.OK);
    }
}
