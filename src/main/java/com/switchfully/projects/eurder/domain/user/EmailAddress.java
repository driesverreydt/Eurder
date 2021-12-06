package com.switchfully.projects.eurder.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

public class EmailAddress {

    private static final String EMAIL_AT = "@";
    private static final String EMAIL_DOT = ".";
    private static final String EMAIL_REGEX = "^(.+)@(.+)$";
    private final String userName;
    private final String domainName;
    private final String extension;

    public EmailAddress(String userName, String domainName, String extension) {
        this.userName = userName;
        this.domainName = domainName;
        this.extension = extension;
    }

    public String getUserName() {
        return userName;
    }

    public String getDomainName() {
        return domainName;
    }

    public String getExtension() {
        return extension;
    }

    @JsonIgnore
    public String getFullEmailAddress() {
        return userName + EMAIL_AT + domainName + EMAIL_DOT + extension;
    }

    public static EmailAddress parseEmailAddressString(String emailAddressString) {
        validateEmailAddressString(emailAddressString);
        String userName = emailAddressString.substring(0, emailAddressString.indexOf(EMAIL_AT));
        String domainName = emailAddressString.substring(emailAddressString.indexOf(EMAIL_AT) + 1, emailAddressString.lastIndexOf(EMAIL_DOT));
        String extension = emailAddressString.substring(emailAddressString.lastIndexOf(EMAIL_DOT) + 1);
        return new EmailAddress(userName, domainName, extension);
    }

    private static void validateEmailAddressString(String emailAddressString) {
        if (!emailAddressString.matches(EMAIL_REGEX)) {
            throw new InvalidEmailStructureException("The provided email " + emailAddressString + " does not have a valid email structure");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailAddress that = (EmailAddress) o;
        return Objects.equals(userName, that.userName) && Objects.equals(domainName, that.domainName) && Objects.equals(extension, that.extension);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, domainName, extension);
    }


}
