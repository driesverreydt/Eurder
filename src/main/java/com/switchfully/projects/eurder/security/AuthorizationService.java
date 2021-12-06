package com.switchfully.projects.eurder.security;

import com.switchfully.projects.eurder.domain.user.EmailAddress;
import com.switchfully.projects.eurder.domain.user.User;
import com.switchfully.projects.eurder.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Base64;
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
            String userEmailAddressString = parseAuthorization(authorization);
            EmailAddress userEmailAddress = EmailAddress.parseEmailAddressString(userEmailAddressString);
            User userByEmail = userService.getUserByEmail(userEmailAddress);
            userRole = userByEmail.getUserRole();
        }
        return userRole.getAuthorizationLevel();
    }

    public String parseAuthorization(String authorization) {
        System.out.println(authorization);
        String decodedUsernameAndPassword = new String(Base64.getDecoder().decode(authorization.substring("Basic ".length())));
        return decodedUsernameAndPassword.substring(0, decodedUsernameAndPassword.indexOf(":"));
    }
}
