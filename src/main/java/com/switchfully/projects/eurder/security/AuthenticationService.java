package com.switchfully.projects.eurder.security;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {

    private final Map<EurderFeature,UserRole> featureAuthorizations;

    public AuthenticationService() {
        this.featureAuthorizations = new HashMap<>();
    }
}
