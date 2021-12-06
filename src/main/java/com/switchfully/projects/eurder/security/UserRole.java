package com.switchfully.projects.eurder.security;

public enum UserRole {
    GUEST(0),
    CUSTOMER(1),
    ADMIN(2);

    private final int authorizationLevel;

    UserRole(int authorizationLevel) {
        this.authorizationLevel = authorizationLevel;
    }

    public int getAuthorizationLevel() {
        return authorizationLevel;
    }

    public static UserRole getNameForAuthorizationLevel(int authorisationLevel) {
        return switch (authorisationLevel) {
            case 1 -> CUSTOMER;
            case 2 -> ADMIN;
            default -> GUEST;
        };
    }
}
