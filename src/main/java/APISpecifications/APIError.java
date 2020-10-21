package APISpecifications;

public enum APIError {
    NOTEXISTINGACCOUNT("The account does not exist."),
    INVALIDFRIFORMAT("Account number is required."),
    INVALIDAUTHENTICATION("User not Authenticated."),
    MULTIPLEACCOUNTS("This customer has multiple accounts. Please specify exact account number."),
    NOTEXISTINGCUSTOMER("The customer is invalid."),
    INVALIDACCOUNTSTATUS("The account does not have an eligible status."),
    EXCEEDINGMINTRANSLIMIT("Minimum transaction limit exceeded."),
    INVALIDCURRENCY("Currency of the account does not match to currency of transaction."),
    INACTIVEMBCUTSOMER("The customer is not activated for mobile banking."),
    INACTIVEMBACCOUNT("The account is not activated for mobile banking."),
    INVALIDMOBILE("The sender mobile number is invalid.");






    private final String errorMessage;

    APIError(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
