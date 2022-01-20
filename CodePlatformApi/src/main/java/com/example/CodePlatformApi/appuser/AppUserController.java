package com.example.CodePlatformApi.appuser;

import com.example.CodePlatformApi.Registration.RegistrationRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppUserController {
    private UserRepository userRepository;

    @Operation(summary = "api/v1/registration/users", description = "get a hello user message", tags = "Get")
            @ApiResponses(value = {
                    @ApiResponse(responseCode ="200", description = "prints hello user",

                     content = {@Content(mediaType = "application/json",

                             schema = @Schema(implementation = RegistrationRequest.class))}),
                    @ApiResponse(responseCode = "404", description = "nothing found",
                    content = @Content)
            })



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
