package com.switchfully.projects.eurder.domain.user;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Address {

    @Column(name = "address_street_name")
    private String streetName;

    @Column(name = "address_street_number")
    private int streetNumber;

    @Column(name = "address_postal_code")
    private int postalCode;

    @Column(name = "address_city_name")
    private String cityName;

    public Address(String streetName, int streetNumber, int postalCode, String cityName) {
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.postalCode = postalCode;
        this.cityName = cityName;
    }

    private Address() {
    }

    public String getStreetName() {
        return streetName;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public String getCityName() {
        return cityName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return streetNumber == address.streetNumber && postalCode == address.postalCode && Objects.equals(streetName, address.streetName) && Objects.equals(cityName, address.cityName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(streetName, streetNumber, postalCode, cityName);
    }
}
