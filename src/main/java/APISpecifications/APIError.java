package APISpecifications;

public enum APIError {
    NOTEXISTINGACCOUNT("The account does not exist."),
    INVALIDFRIFORMAT("Account number is required."),
    INVALIDAUTHENTICATION("User not Authenticated."),
    MULTIPLEACCOUNTS("This customer has multiple accounts. Please specify exact account number."),
    NOTEXISTINGCUSTOMER("The customer is invalid.");

    private final String errorMessage;

    APIError(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
