package fr.epita.userdb.datatransfer;


import fr.epita.userdb.entities.UserAddress;

import javax.validation.constraints.NotNull;

public class UserAddressDTO implements EntityDataTransferObject<UserAddress> {
    @NotNull
    private String country;

    @NotNull
    private String area;

    @NotNull
    private String city;

    @NotNull
    private String street;

    @NotNull
    private String number;

    public UserAddress toEntity() {
        var address = new UserAddress();
        address.setCountry(this.getCountry());
        address.setArea(this.getArea());
        address.setCity(this.getCity());
        address.setStreet(this.getStreet());
        address.setNumber(this.getNumber());
        return address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
