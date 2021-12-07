package com.switchfully.projects.eurder.security;

import com.switchfully.projects.eurder.domain.exception.AuthorisationNotGrantedException;
import com.switchfully.projects.eurder.domain.user.EmailAddress;
import com.switchfully.projects.eurder.domain.user.User;
import com.switchfully.projects.eurder.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthorizationService {

    private final Map<EurderFeature, UserRole> featureAuthorizations;
    private final UserService userService;

    public AuthorizationService(UserService userService) {
        this.userService = userService;
        this.featureAuthorizations = new HashMap<>();
        featureAuthorizations.put(EurderFeature.CUSTOMER_CREATE, UserRole.GUEST);
    }

    public void authorize(EurderFeature eurderFeature, String authorization) {
        if (featureAuthorizations.get(eurderFeature).getAuthorizationLevel() > getAuthorizationLevel(authorization)) {
            throw new AuthorisationNotGrantedException("User with role " +
                    UserRole.getNameForAuthorizationLevel(getAuthorizationLevel(authorization)).name().toLowerCase() +
                    " does not have rights to feature " + eurderFeature.name().toLowerCase());
        }
    }

    public int getAuthorizationLevel(String authorization) {
        UserRole userRole = UserRole.GUEST;
        if (authorization != null) {
            User existingUser = getUser(authorization);
            checkPassword(existingUser.getPassword(),authorization);
            userRole = existingUser.getUserRole();
        }
        return userRole.getAuthorizationLevel();
    }

    private User getUser(String authorization){
        String userEmailAddressString = parseAuthorizationUserName(authorization);
        EmailAddress userEmailAddress = EmailAddress.parseEmailAddressString(userEmailAddressString);
        Collection<User> usersByEmail = userService.getUsersByEmail(userEmailAddress);
        if(usersByEmail.isEmpty()){
            throw new AuthorisationNotGrantedException("No user found with email " + userEmailAddress.getFullEmailAddress());
        }
        return usersByEmail.iterator().next();
    }

    private void checkPassword(String expectedPassword, String authorization){
        String userPassword = parseAuthorizationPassword(authorization);
        String userEmailAddressString = parseAuthorizationUserName(authorization);
        if(!expectedPassword.equals(userPassword)){
            throw new AuthorisationNotGrantedException("Password " + userPassword +
                    " does not match the password for user with email " + userEmailAddressString);
        }
    }

    private String parseAuthorizationUserName(String authorization) {
        String decodedUsernameAndPassword = new String(Base64.getDecoder().decode(authorization.substring("Basic ".length())));
        return decodedUsernameAndPassword.substring(0, decodedUsernameAndPassword.indexOf(":"));
    }

    private String parseAuthorizationPassword(String authorization) {
        String decodedUsernameAndPassword = new String(Base64.getDecoder().decode(authorization.substring("Basic ".length())));
        return decodedUsernameAndPassword.substring(decodedUsernameAndPassword.indexOf(":")+1);
    }

}
