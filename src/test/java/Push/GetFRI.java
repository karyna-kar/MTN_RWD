package Push;
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
import utilities.TestsSetup;


import static io.restassured.RestAssured.given;
import static io.restassured.matcher.RestAssuredMatchers.matchesXsdInClasspath;

public class GetFRI extends TestsSetup {
    private static final RequestSpecification requestSpec = APISpecification.getRequestSpecification();
    private static final ResponseSpecification responseSpec = APISpecification.getResponseSpecification();
    private Response response = null;

    @Test(priority = 1)
    public void getFRICurrentAccount() {
        response = given()
                .spec(requestSpec)
                .relaxedHTTPSValidation()
                .body(Bodies.getGetFRIBody(SQLquery.getActiveCurrentAccount()))
                .expect().spec(responseSpec)
                .when()
                .post( EndPoints.GETFRI);
        //Logs.info("Request: " + requestSpec.log().all().toString());
        Logs.info("Response: " + response.asString());
        response.then().body(matchesXsdInClasspath("GetFRI.xsd"));
        Assert.assertEquals(Parser.getStatusGetFRI(response), "01");
    }

    @Test(priority = 2)
    public void getFRINotExistingAccount() {
        response = given()
                .spec(requestSpec)
                .relaxedHTTPSValidation()
                .body(Bodies.getGetFRIBody(SQLquery.getNotExistingAccount()))
                .expect().spec(responseSpec)
                .when()
                .post(EndPoints.GETFRI);
        Logs.info("Response: " + response.asString());
        response.then().body(matchesXsdInClasspath("GetFRI.xsd"));
        Assert.assertEquals(Parser.getStatusGetFRI(response), "00");
        Assert.assertEquals(Parser.getMessageGetFRI(response), APIError.NOTEXISTINGACCOUNT.getErrorMessage());
    }

    @Test(priority = 3)
    public void getFRIByCustomerOneAccount() {
        response = given()
                .spec(requestSpec)
                .relaxedHTTPSValidation()
                .body(Bodies.getGetFRIBody(SQLquery.getCustomerWithOneAccount()))
                .expect().spec(responseSpec)
                .when()
                .post(EndPoints.GETFRI);
        Logs.info("Response: " + response.asString());
        response.then().body(matchesXsdInClasspath("GetFRI.xsd"));
        Assert.assertEquals(Parser.getStatusGetFRI(response), "01");
    }

    @Test(priority = 4)
    public void getFRIByCustomerMultipleAccounts() {
        response = given()
                .spec(requestSpec)
                .relaxedHTTPSValidation()
                .body(Bodies.getGetFRIBody(SQLquery.getCustomerWithMultipleAccount()))
                .expect().spec(responseSpec)
                .when()
                .post(EndPoints.GETFRI);
        Logs.info("Response: " + response.asString());
        response.then().body(matchesXsdInClasspath("GetFRI.xsd"));
        Assert.assertEquals(Parser.getStatusGetFRI(response), "00");
        Assert.assertEquals(Parser.getMessageGetFRI(response), APIError.MULTIPLEACCOUNTS.getErrorMessage());
    }

    @Test(priority = 5)
    public void getFRINotExistingCustomer() {
        response = given()
                .spec(requestSpec)
                .relaxedHTTPSValidation()
                .body(Bodies.getGetFRIBody(SQLquery.getNotExistingCustomer()))
                .expect().spec(responseSpec)
                .when()
                .post(EndPoints.GETFRI);
        Logs.info("Response: " + response.asString());
        response.then().body(matchesXsdInClasspath("GetFRI.xsd"));
        Assert.assertEquals(Parser.getStatusGetFRI(response), "00");
        Assert.assertEquals(Parser.getMessageGetFRI(response), APIError.NOTEXISTINGCUSTOMER.getErrorMessage());
    }

    @Test(priority = 6)
    public void getFRIInvalidFRIFormat() {
        response = given()
                .spec(requestSpec)
                .relaxedHTTPSValidation()
                .body(Bodies.getGetFRIInvalidBody(SQLquery.getActiveCurrentAccount()))
                .expect().spec(responseSpec)
                .when()
                .post( EndPoints.GETFRI);
        Logs.info("Response: " + response.asString());
        response.then().body(matchesXsdInClasspath("GetFRI.xsd"));
        Assert.assertEquals(Parser.getStatusGetFRI(response), "00");
        Assert.assertEquals(Parser.getMessageGetFRI(response), APIError.INVALIDFRIFORMAT.getErrorMessage());
    }

    @Test(priority = 7)
    public void getFRIInvalidAuthorization() {
        RequestSpecification invalidRequestSpec = APISpecification.getInvalidRequestSpecification();
        response = given()
                .spec(invalidRequestSpec)
                .relaxedHTTPSValidation()
                .body(Bodies.getGetFRIBody(SQLquery.getActiveCurrentAccount()))
                .expect().spec(responseSpec)
                .when()
                .post( EndPoints.GETFRI);
        Logs.info("Response: " + response.asString());
        response.then().body(matchesXsdInClasspath("GetFRI.xsd"));
        Assert.assertEquals(Parser.getStatusGetFRI(response), "00");
        Assert.assertEquals(Parser.getMessageGetFRI(response), APIError.INVALIDAUTHENTICATION.getErrorMessage());
    }
}
