package com.example.CodePlatformApi.Home;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeResource {

    @GetMapping("/registration/")
    public String home(){
        return ("<h1>Welcome home </h1>");
    }

    @GetMapping("/registration/user")
    public String user(){
        return ("<h1>Welcome home  user</h1>");
    }

    @GetMapping("/registration/admin")
    public String admin(){
        return ("<h1>Welcome home admin </h1>");
    }
}
