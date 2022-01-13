package com.example.CodePlatformApi.Registration;

import com.example.CodePlatformApi.Registration.Token.ConfirmationToken;
import com.example.CodePlatformApi.Registration.Token.ConfirmationTokenRepository;
import com.example.CodePlatformApi.Registration.Token.ConfirmationTokenService;
import com.example.CodePlatformApi.appuser.AppUser;
import com.example.CodePlatformApi.appuser.AppUserRole;
import com.example.CodePlatformApi.appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final  EmailValidator emailValidator;
    private final  AppUserService appUserService;
    private  final  ConfirmationTokenService confirmationTokenService;

    public String register(RegistrationRequest request) {

        boolean isValidEmail = emailValidator.test(request.getEmail());
        if (!isValidEmail) {
            throw new IllegalStateException("An invalid email :/...");
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
    public String confirmToken(String token){
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token)
                .orElseThrow(() -> new IllegalStateException("token not found"));
        if(confirmationToken.getConfirmedAt() != null){
            throw new IllegalStateException("email already confirmed");
        }
        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if(expiredAt.isBefore(LocalDateTime.now())){
            throw new IllegalStateException("token expired");
        }
        confirmationTokenService.setConfirmedAt(token);
            appUserService.enableAppUser(confirmationToken.getAppUser().getEmail());
            return "confirmed";
    }
}
