package Pull;

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

import static io.restassured.RestAssured.given;
import static io.restassured.matcher.RestAssuredMatchers.matchesXsdInClasspath;

public class VerifyFRI {
    private static RequestSpecification requestSpec = APISpecification.getRequestSpecification();
    private static final ResponseSpecification responseSpec = APISpecification.getResponseSpecification();
    private Response response = null;

    @Test(priority = 1)
    public void verifyFRIActiveMB() {
        response = given()
                .spec(requestSpec)
                .relaxedHTTPSValidation()
                .body(Bodies.getVerifyFRIBody(SQLquery.getCurrentRWFAccountActiveMB("A")))
                .expect().spec(responseSpec)
                .body(matchesXsdInClasspath("verifyFRI.xsd"))
                .when()
                .post(EndPoints.VERIFYFRI);
        Logs.info("Response: " + response.asString());
        Assert.assertEquals(Parser.getStatusVerifyFRI(response), "true");
    }

    @Test(priority = 2, enabled = false)
    public void verifyFRIInactiveCustomerMB() {
        response = given()
                .spec(requestSpec)
                .relaxedHTTPSValidation()
                .body(Bodies.getVerifyFRIBody(SQLquery.getAccountInactiveCustomerMB()))
                .expect().spec(responseSpec)
                .body(matchesXsdInClasspath("verifyFRI.xsd"))
                .when()
                .post( EndPoints.VERIFYFRI);
        Logs.info("Response: " + response.asString());
        Assert.assertEquals(Parser.getStatusVerifyFRI(response), "false");
    }

    @Test(priority = 3)
    public void verifyFRIInactiveAccountMB() {
        response = given()
                .spec(requestSpec)
                .relaxedHTTPSValidation()
                .body(Bodies.getVerifyFRIBody(SQLquery.getAccountInactiveAccountMB()))
                .expect().spec(responseSpec)
                .body(matchesXsdInClasspath("verifyFRI.xsd"))
                .when()
                .post( EndPoints.VERIFYFRI);
        Logs.info("Response: " + response.asString());
        Assert.assertEquals(Parser.getStatusVerifyFRI(response), "false");
    }

    @Test(priority = 4)
    public void verifyFRINotMatchingPhone() {
        response = given()
                .spec(requestSpec)
                .relaxedHTTPSValidation()
                .body(Bodies.getVerifyFRIBody(SQLquery.getNotMatchingPhoneActiveMB()))
                .expect().spec(responseSpec)
                .body(matchesXsdInClasspath("verifyFRI.xsd"))
                .when()
                .post( EndPoints.VERIFYFRI);
        Logs.info("Response: " + response.asString());
        Assert.assertEquals(Parser.getStatusVerifyFRI(response), "false");
    }

    @Test(priority = 5)
    public void verifyFRINotExistingAccount() {
        response = given()
                .spec(requestSpec)
                .relaxedHTTPSValidation()
                .body(Bodies.getVerifyFRIBody(SQLquery.getNotExistingAccountMB()))
                .expect().spec(responseSpec)
                .body(matchesXsdInClasspath("verifyFRI.xsd"))
                .when()
                .post(EndPoints.VERIFYFRI);
        Logs.info("Response: " + response.asString());
        Assert.assertEquals(Parser.getStatusVerifyFRI(response), "false");
    }

    @Test(priority = 6)
    public void verifyFRIInvalidFRIFormat() {
        response = given()
                .spec(requestSpec)
                .relaxedHTTPSValidation()
                .body(Bodies.getVerifyFRIInvalidBody(SQLquery.getCurrentRWFAccountActiveMB("A")))
                .expect().spec(responseSpec)
                .body(matchesXsdInClasspath("verifyFRI.xsd"))
                .when()
                .post( EndPoints.VERIFYFRI);
        Logs.info("Response: " + response.asString());
        Assert.assertEquals(Parser.getStatusVerifyFRI(response), "false");
    }

    @Test(priority = 7)
    public void verifyFRIInvalidAuthorization() {
        requestSpec = APISpecification.getInvalidRequestSpecification();
        response = given()
                .spec(requestSpec)
                .relaxedHTTPSValidation()
                .body(Bodies.getVerifyFRIInvalidBody(SQLquery.getCurrentRWFAccountActiveMB("A")))
                .expect().spec(responseSpec)
                .body(matchesXsdInClasspath("verifyFRI.xsd"))
                .when()
                .post( EndPoints.VERIFYFRI);
        Logs.info("Response: " + response.asString());
        Assert.assertEquals(Parser.getStatusVerifyFRI(response), "false");
    }
}
