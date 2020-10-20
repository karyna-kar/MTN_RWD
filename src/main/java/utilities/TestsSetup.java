package utilities;

import JDBC.JDBCConnection;
import io.restassured.RestAssured;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class TestsSetup {
    @BeforeMethod
    public void startTest() {
        //RestAssured.useRelaxedHTTPSValidation();
        Logs.info("---- Start test ----");
    }

    @AfterMethod
    public void finishTest() {
        JDBCConnection.closeConnection();
        Logs.info("---- Finish test ---");
    }
}
