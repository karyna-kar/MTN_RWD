package Pull;

import APISpecifications.APIError;
import APISpecifications.APISpecification;
import APISpecifications.Bodies;
import APISpecifications.EndPoints;
import JDBC.SQLquery;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.Logs;
import utilities.Parser;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static io.restassured.matcher.RestAssuredMatchers.matchesXsdInClasspath;

public class Withdrawal {
    private static RequestSpecification requestSpec = APISpecification.getRequestSpecification();
    private static final ResponseSpecification responseSpec = APISpecification.getResponseSpecification();
    private Response response = null;
    private String amount = Double.toString(Double.parseDouble(SQLquery.getTransactionMinimumLimit())+0.01);

    @Test(priority = 1)
    public void withdrawalActiveCurrentRWFAccount() {
        HashMap<String, String> requestData = SQLquery.getCurrentRWFAccountActiveMB("A",Double.parseDouble(amount));
        requestData.put("TransactionID", SQLquery.getUniqueTransactionID());
        requestData.put("Amount", amount);
        requestData.put("Currency", "RWF");

        response = given()
                .spec(requestSpec)
                .relaxedHTTPSValidation()
                .body(Bodies.getWithdrawalBody(requestData))
                .expect().spec(responseSpec)
                .body(matchesXsdInClasspath("WithdrawalCompleted.xsd"))
                .when()
                .post(EndPoints.WITHDRAWAL);
        Logs.info("Response: " + response.asString());
        Assert.assertEquals(Parser.parseWithdrawalResponse(response).get("Status"), "COMPLETED");
        Assert.assertEquals(Parser.parseWithdrawalResponse(response).get("TransactionID"), requestData.get("TransactionID"));
        Assert.assertTrue(Parser.parseWithdrawalResponse(response).get("Message").contains(requestData.get("Account")));
        Assert.assertEquals(Parser.parseWithdrawalResponse(response).get("ProviderTransactionID"), SQLquery.getVoucherNumber(Parser.parseWithdrawalResponse(response).get("TransactionID")));
    }

    @Test
    public void withdrawalActiveSavingRWFAccount() {
        HashMap<String, String> requestData = SQLquery.getActiveSavingRWFAccountActiveMB();
        requestData.put("TransactionID", SQLquery.getUniqueTransactionID());
        requestData.put("Amount", amount);
        requestData.put("Currency", "RWF");

        response = given()
                .spec(requestSpec)
                .relaxedHTTPSValidation()
                .body(Bodies.getWithdrawalBody(requestData))
                .expect().spec(responseSpec)
                .body(matchesXsdInClasspath("WithdrawalCompleted.xsd"))
                .when()
                .post(EndPoints.WITHDRAWAL);
        Logs.info("Response: " + response.asString());
        Assert.assertEquals(Parser.parseWithdrawalResponse(response).get("Status"), "COMPLETED");
        Assert.assertEquals(Parser.parseWithdrawalResponse(response).get("TransactionID"), requestData.get("TransactionID"));
        Assert.assertTrue(Parser.parseWithdrawalResponse(response).get("Message").contains(requestData.get("Account")));
        Assert.assertEquals(Parser.parseWithdrawalResponse(response).get("ProviderTransactionID"), SQLquery.getVoucherNumber(Parser.parseWithdrawalResponse(response).get("TransactionID")));
    }

    @Test
    public void withdrawalActiveUSDAccount() {
        HashMap<String, String> requestData = SQLquery.getActiveUSDAccountActiveMB();
        requestData.put("TransactionID", SQLquery.getUniqueTransactionID());
        requestData.put("Amount", "1.01");
        requestData.put("Currency", "USD");

        response = given()
                .spec(requestSpec)
                .relaxedHTTPSValidation()
                .body(Bodies.getWithdrawalBody(requestData))
                .expect().spec(responseSpec)
                .body(matchesXsdInClasspath("WithdrawalCompleted.xsd"))
                .when()
                .post(EndPoints.WITHDRAWAL);
        Logs.info("Response: " + response.asString());
        Assert.assertEquals(Parser.parseWithdrawalResponse(response).get("Status"), "COMPLETED");
        Assert.assertEquals(Parser.parseWithdrawalResponse(response).get("TransactionID"), requestData.get("TransactionID"));
        Assert.assertTrue(Parser.parseWithdrawalResponse(response).get("Message").contains(requestData.get("Account")));
        Assert.assertEquals(Parser.parseWithdrawalResponse(response).get("ProviderTransactionID"), SQLquery.getVoucherNumber(Parser.parseWithdrawalResponse(response).get("TransactionID")));
    }

    @Test
    public void withdrawalBlockedByCollateralRWFAccount() {
        HashMap<String, String> requestData = SQLquery.getCurrentRWFAccountActiveMB("G");
        requestData.put("TransactionID", SQLquery.getUniqueTransactionID());
        requestData.put("Amount", amount);
        requestData.put("Currency", "RWF");

        response = given()
                .spec(requestSpec)
                .relaxedHTTPSValidation()
                .body(Bodies.getWithdrawalBody(requestData))
                .expect().spec(responseSpec)
                .body(matchesXsdInClasspath("WithdrawalFailed.xsd"))
                .when()
                .post(EndPoints.WITHDRAWAL);
        Logs.info("Response: " + response.asString());
        Assert.assertEquals(Parser.parseWithdrawalResponse(response).get("Status"), "FAILED");
        Assert.assertEquals(Parser.parseWithdrawalResponse(response).get("Message"), APIError.INVALIDACCOUNTSTATUS.getErrorMessage());
    }

    @Test
    public void withdrawalBlockedForWithdrawalRWFAccount() {
        HashMap<String, String> requestData = SQLquery.getCurrentRWFAccountActiveMB("R");
        requestData.put("TransactionID", SQLquery.getUniqueTransactionID());
        requestData.put("Amount", amount);
        requestData.put("Currency", "RWF");

        response = given()
                .spec(requestSpec)
                .relaxedHTTPSValidation()
                .body(Bodies.getWithdrawalBody(requestData))
                .expect().spec(responseSpec)
                .body(matchesXsdInClasspath("WithdrawalFailed.xsd"))
                .when()
                .post(EndPoints.WITHDRAWAL);
        Logs.info("Response: " + response.asString());
        Assert.assertEquals(Parser.parseWithdrawalResponse(response).get("Status"), "FAILED");
        Assert.assertEquals(Parser.parseWithdrawalResponse(response).get("Message"), APIError.INVALIDACCOUNTSTATUS.getErrorMessage());
    }

    @Test
    public void withdrawalBlockedRWFAccount() {
        HashMap<String, String> requestData = SQLquery.getCurrentRWFAccountActiveMB("B");
        requestData.put("TransactionID", SQLquery.getUniqueTransactionID());
        requestData.put("Amount", amount);
        requestData.put("Currency", "RWF");

        response = given()
                .spec(requestSpec)
                .relaxedHTTPSValidation()
                .body(Bodies.getWithdrawalBody(requestData))
                .expect().spec(responseSpec)
                .body(matchesXsdInClasspath("WithdrawalFailed.xsd"))
                .when()
                .post(EndPoints.WITHDRAWAL);
        Logs.info("Response: " + response.asString());
        Assert.assertEquals(Parser.parseWithdrawalResponse(response).get("Status"), "FAILED");
        Assert.assertEquals(Parser.parseWithdrawalResponse(response).get("Message"), APIError.INVALIDACCOUNTSTATUS.getErrorMessage());
    }

    @Test
    public void withdrawalInvalidCurrency() {
        HashMap<String, String> requestData = SQLquery.getCurrentRWFAccountActiveMB("A",Double.parseDouble(amount));
        requestData.put("TransactionID", SQLquery.getUniqueTransactionID());
        requestData.put("Amount", amount);
        requestData.put("Currency", "USD");

        response = given()
                .spec(requestSpec)
                .relaxedHTTPSValidation()
                .body(Bodies.getWithdrawalBody(requestData))
                .expect().spec(responseSpec)
                .body(matchesXsdInClasspath("WithdrawalFailed.xsd"))
                .when()
                .post(EndPoints.WITHDRAWAL);
        Logs.info("Response: " + response.asString());
        Assert.assertEquals(Parser.parseWithdrawalResponse(response).get("Status"), "FAILED");
        Assert.assertEquals(Parser.parseWithdrawalResponse(response).get("Message"), APIError.INVALIDCURRENCY.getErrorMessage());
    }

   ////
    @Test
    public void withdrawalNotEnoughBalance() {
        HashMap<String, String> requestData = SQLquery.getCurrentRWFAccountNotMoney("A",Double.parseDouble(amount));
        requestData.put("TransactionID", SQLquery.getUniqueTransactionID());
        requestData.put("Amount", amount);
        requestData.put("Currency", "RWF");

        response = given()
                .spec(requestSpec)
                .relaxedHTTPSValidation()
                .body(Bodies.getWithdrawalBody(requestData))
                .expect().spec(responseSpec)
                .body(matchesXsdInClasspath("WithdrawalFailed.xsd"))
                .when()
                .post(EndPoints.WITHDRAWAL);
        Logs.info("Response: " + response.asString());
        Assert.assertEquals(Parser.parseWithdrawalResponse(response).get("Status"), "FAILED");
        Assert.assertEquals(Parser.parseWithdrawalResponse(response).get("Message"), APIError.INVALIDCURRENCY.getErrorMessage());
    }

    @Test
    public void withdrawalInactiveCustomerMB() {
        HashMap<String, String> requestData = SQLquery.getAccountInactiveCustomerMB();
        requestData.put("TransactionID", SQLquery.getUniqueTransactionID());
        requestData.put("Amount", amount);
        requestData.put("Currency", "RWF");

        response = given()
                .spec(requestSpec)
                .relaxedHTTPSValidation()
                .body(Bodies.getWithdrawalBody(requestData))
                .expect().spec(responseSpec)
                .body(matchesXsdInClasspath("WithdrawalFailed.xsd"))
                .when()
                .post(EndPoints.WITHDRAWAL);
        Logs.info("Response: " + response.asString());
        Assert.assertEquals(Parser.parseWithdrawalResponse(response).get("Status"), "FAILED");
        Assert.assertEquals(Parser.parseWithdrawalResponse(response).get("Message"), APIError.INACTIVEMBCUTSOMER.getErrorMessage());
    }

    @Test
    public void withdrawalInactiveAccountMB() {
        HashMap<String, String> requestData = SQLquery.getAccountInactiveAccountMB();
        requestData.put("TransactionID", SQLquery.getUniqueTransactionID());
        requestData.put("Amount", amount);
        requestData.put("Currency", "RWF");

        response = given()
                .spec(requestSpec)
                .relaxedHTTPSValidation()
                .body(Bodies.getWithdrawalBody(requestData))
                .expect().spec(responseSpec)
                .body(matchesXsdInClasspath("WithdrawalFailed.xsd"))
                .when()
                .post(EndPoints.WITHDRAWAL);
        Logs.info("Response: " + response.asString());
        Assert.assertEquals(Parser.parseWithdrawalResponse(response).get("Status"), "FAILED");
        Assert.assertEquals(Parser.parseWithdrawalResponse(response).get("Message"), APIError.INACTIVEMBACCOUNT.getErrorMessage());
    }

    @Test
    public void withdrawalNotMatchingPhone() {
        HashMap<String, String> requestData = SQLquery.getNotMatchingPhoneActiveMB();
        requestData.put("TransactionID", SQLquery.getUniqueTransactionID());
        requestData.put("Amount", amount);
        requestData.put("Currency", "RWF");

        response = given()
                .spec(requestSpec)
                .relaxedHTTPSValidation()
                .body(Bodies.getWithdrawalBody(requestData))
                .expect().spec(responseSpec)
                .body(matchesXsdInClasspath("WithdrawalFailed.xsd"))
                .when()
                .post(EndPoints.WITHDRAWAL);
        Logs.info("Response: " + response.asString());
        Assert.assertEquals(Parser.parseWithdrawalResponse(response).get("Status"), "FAILED");
        Assert.assertEquals(Parser.parseWithdrawalResponse(response).get("Message"), APIError.INVALIDMOBILE.getErrorMessage());
    }

    @Test
    public void withdrawalNotExistingAccount() {
        HashMap<String, String> requestData = SQLquery.getNotExistingAccountMB();
        requestData.put("TransactionID", SQLquery.getUniqueTransactionID());
        requestData.put("Amount", amount);
        requestData.put("Currency", "RWF");

        response = given()
                .spec(requestSpec)
                .relaxedHTTPSValidation()
                .body(Bodies.getWithdrawalBody(requestData))
                .expect().spec(responseSpec)
                .body(matchesXsdInClasspath("WithdrawalFailed.xsd"))
                .when()
                .post(EndPoints.WITHDRAWAL);
        Logs.info("Response: " + response.asString());
        Assert.assertEquals(Parser.parseWithdrawalResponse(response).get("Status"), "FAILED");
        Assert.assertEquals(Parser.parseWithdrawalResponse(response).get("Message"), APIError.NOTEXISTINGACCOUNT.getErrorMessage());
    }

    @Test
    public void withdrawalExceedingMinTransactionLimit() {
        amount = Double.toString(Double.parseDouble(SQLquery.getTransactionMinimumLimit())-0.01);
        HashMap<String, String> requestData = SQLquery.getCurrentRWFAccountActiveMB("A",Double.parseDouble(amount));
        requestData.put("TransactionID", SQLquery.getUniqueTransactionID());
        requestData.put("Amount", amount);
        requestData.put("Currency", "RWF");

        response = given()
                .spec(requestSpec)
                .relaxedHTTPSValidation()
                .body(Bodies.getWithdrawalBody(requestData))
                .expect().spec(responseSpec)
                .body(matchesXsdInClasspath("WithdrawalFailed.xsd"))
                .when()
                .post(EndPoints.WITHDRAWAL);
        Logs.info("Response: " + response.asString());
        Assert.assertEquals(Parser.parseWithdrawalResponse(response).get("Status"), "FAILED");
        Assert.assertEquals(Parser.parseWithdrawalResponse(response).get("Message"), APIError.EXCEEDINGMINTRANSLIMIT.getErrorMessage());
    }

    @Test
    public void withdrawalInvalidFRIFormat() {
        HashMap<String, String> requestData = SQLquery.getCurrentRWFAccountActiveMB("A");
        requestData.put("TransactionID", SQLquery.getUniqueTransactionID());
        requestData.put("Amount", amount);
        requestData.put("Currency", "RWF");

        response = given()
                .spec(requestSpec)
                .relaxedHTTPSValidation()
                .body(Bodies.getWithdrawalInvalidBody(requestData))
                .expect().spec(responseSpec)
                .body(matchesXsdInClasspath("WithdrawalFailed.xsd"))
                .when()
                .post(EndPoints.WITHDRAWAL);
        Logs.info("Response: " + response.asString());
        Assert.assertEquals(Parser.parseWithdrawalResponse(response).get("Status"), "FAILED");
        Assert.assertEquals(Parser.parseWithdrawalResponse(response).get("Message"), APIError.INVALIDFRIFORMAT.getErrorMessage());
    }


    @Test
    public void withdrawalInvalidAuthorization() {
        requestSpec = APISpecification.getInvalidRequestSpecification();
        HashMap<String, String> requestData = SQLquery.getCurrentRWFAccountActiveMB("A");
        requestData.put("TransactionID", SQLquery.getUniqueTransactionID());
        requestData.put("Amount", amount);
        requestData.put("Currency", "RWF");

        response = given()
                .spec(requestSpec)
                .relaxedHTTPSValidation()
                .body(Bodies.getWithdrawalBody(requestData))
                .expect().spec(responseSpec)
                .body(matchesXsdInClasspath("WithdrawalFailed.xsd"))
                .when()
                .post(EndPoints.WITHDRAWAL);
        Logs.info("Response: " + response.asString());
        Assert.assertEquals(Parser.parseWithdrawalResponse(response).get("Status"), "FAILED");
        Assert.assertEquals(Parser.parseWithdrawalResponse(response).get("Message"), APIError.INVALIDAUTHENTICATION.getErrorMessage());
    }


}
