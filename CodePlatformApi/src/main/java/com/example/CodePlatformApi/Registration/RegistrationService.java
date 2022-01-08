package com.example.CodePlatformApi.Registration;

import com.example.CodePlatformApi.appuser.AppUser;
import com.example.CodePlatformApi.appuser.AppUserRole;
import com.example.CodePlatformApi.appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.apache.naming.factory.SendMailFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final  EmailValidator emailValidator;
    private final  AppUserService appUserService;

    public String register(RegistrationRequest request) {

        boolean isValidEmail = emailValidator.test(request.getEmail());
        if(!isValidEmail){
            throw  new IllegalStateException("An invalid email :/...");
        }

        return appUserService.signUpUser(
                new AppUser(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        AppUserRole.USER
                )
        );
    }
}
