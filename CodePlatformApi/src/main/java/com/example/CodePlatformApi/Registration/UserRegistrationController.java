package com.example.CodePlatformApi.Registration;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path= "api/v1/registration")
@AllArgsConstructor
public class UserRegistrationController {

    private  RegistrationService registrationService;

    @Operation(summary = "api/v1/registration/", description = "input user email and password", tags = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200", description = "display token",

                    content = {@Content(mediaType = "application/json",

                            schema = @Schema(implementation = RegistrationRequest.class))}),
            @ApiResponse(responseCode = "404", description = "sign in",
                    content = @Content)
    })

    @PostMapping
    public  String register(@RequestBody RegistrationRequest request){
        return registrationService.register(request);
    }

    @Operation(summary = "api/v1/registration/confirm", description = "validates token", tags = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200", description = "Confirmed",

                    content = {@Content(mediaType = "application/json",

                            schema = @Schema(implementation = RegistrationRequest.class))}),
            @ApiResponse(responseCode = "404", description = "email already taken",
                    content = @Content)
    })
    @GetMapping(path = "confirm")
    public  String confirm(@RequestParam("token") String token){
        return registrationService.confirmToken(token);
    }
}
