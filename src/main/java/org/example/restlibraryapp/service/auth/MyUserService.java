package org.example.restlibraryapp.service.auth;

import org.example.restlibraryapp.entity.auth.MyUser;
import org.example.restlibraryapp.repository.auth.MyUserRepo;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


// has to implement UserDetailsService

// this is a Spring Security interface to manage user authentication

@Service
public class MyUserService implements UserDetailsService {

    private MyUserRepo myUserRepo;

    public MyUserService(MyUserRepo myUserRepo) {
        this.myUserRepo = myUserRepo;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MyUser> myUser = myUserRepo.findByUsername(username);
        if (myUser.isPresent()) {
            MyUser user = myUser.get();
            return User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
//                    .roles(user.getRoles())
                    .roles(getRoles(user))
                    .build();
        }
        return null;
    }

    private String[] getRoles(MyUser myUser) {
        if(myUser.getRole() == null)
            return new String[] {"LIBRARYMEMBER"};
        return myUser.getRole().split(",");
    }

    // Create New User

    public void saveUser(MyUser myUser) {
        myUserRepo.save(myUser);
    }


    // UPDATE USER

    public void updateUser(Long id, MyUser myUser) {
        MyUser existingUser =  myUserRepo.findById(id).orElse(null);
        if (existingUser != null) {
            existingUser.setUsername(myUser.getUsername());
            existingUser.setRole(myUser.getRole());
        }
        myUserRepo.save(myUser);
    }

    // DELETE USER

    public void deleteUser(Long id) {
        MyUser myUser = myUserRepo.findById(id).orElse(null);
        if (myUser != null) {
            myUserRepo.delete(myUser);
        }
    }




}
