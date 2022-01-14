package com.example.CodePlatformApi.appuser;

import org.springframework.web.bind.annotation.*;

@RestController
public class AppUserController {
    private UserRepository userRepository;

    public AppUserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/api/v1/registration/users", method = RequestMethod.GET)
    public String getUser(){
        return "hello user";
    }
    public AppUser createUser(@RequestBody AppUser user){
        AppUser object = new AppUser();
        object.setEmail(user.getEmail());
        object.setPassword(user.getPassword());
        object.setFirstName(user.getFirstName());
        object.setLastName(user.getLastName());
        object.setUsername(user.getUsername());


        return  userRepository.save(object);
    }
}
