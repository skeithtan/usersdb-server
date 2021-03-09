package fr.epita.userdb.datatransfer;

import javax.validation.constraints.NotNull;

public class UserRegistrationDTO {
    @NotNull
    private UserCredentialsDTO credentials;

    @NotNull
    private UserContactDTO contact;

    @NotNull
    private UserAddressDTO billingAddress;

    public UserCredentialsDTO getCredentials() {
        return credentials;
    }

    public void setCredentials(UserCredentialsDTO credentials) {
        this.credentials = credentials;
    }

    public UserContactDTO getContact() {
        return contact;
    }

    public void setContact(UserContactDTO contact) {
        this.contact = contact;
    }

    public UserAddressDTO getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(UserAddressDTO billingAddress) {
        this.billingAddress = billingAddress;
    }
}
