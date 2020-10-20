package APISpecifications;

import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import utilities.Property;

public class APISpecification {

    public static RequestSpecification getRequestSpecification() {

        PreemptiveBasicAuthScheme auth = new PreemptiveBasicAuthScheme();
        auth.setUserName(Property.getAuthProperty("mtn.AuthUser"));
        auth.setPassword(Property.getAuthProperty("mtn.AuthPassword"));

        return new RequestSpecBuilder()
                //.setBaseUri(EndPoints.BASEURL)
                .setAuth(auth)
                .setContentType(ContentType.XML)
                .log(LogDetail.ALL)
                .build();
    }

    public static RequestSpecification getInvalidRequestSpecification() {

        PreemptiveBasicAuthScheme auth = new PreemptiveBasicAuthScheme();
        auth.setUserName(Property.getAuthProperty("userInvalid"));
        auth.setPassword(Property.getAuthProperty("paswInvalid"));

        return new RequestSpecBuilder()
                //.setBaseUri(EndPoints.BASEURL)
                .setAuth(auth)
                .setContentType(ContentType.XML)
                .log(LogDetail.ALL)
                .build();
    }

    public static ResponseSpecification getResponseSpecification() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();
    }
}
