package com.switchfully.projects.eurder.domain.user;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class PhoneNumber {

    @Column(name = "phone_number_digits")
    private String phoneNumberDigits;

    @Column(name = "phone_number_country")
    private String country;

    public PhoneNumber(String phoneNumberDigits, String country) {
        this.phoneNumberDigits = phoneNumberDigits;
        this.country = country;
    }

    private PhoneNumber() {
    }

    public String getPhoneNumberDigits() {
        return phoneNumberDigits;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneNumber that = (PhoneNumber) o;
        return Objects.equals(phoneNumberDigits, that.phoneNumberDigits) && Objects.equals(country, that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phoneNumberDigits, country);
    }
}
