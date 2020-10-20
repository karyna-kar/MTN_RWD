package utilities;

import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

import java.util.HashMap;

public class Parser {

    public static String getStatusGetFRI(Response response) {
        String resp = response.andReturn().asString();
        XmlPath xmlPath = new XmlPath(resp);
        String status = xmlPath.get("ns2:getfinancialresourceinformationresponse.extension.statuscode");
        Logs.info("Status code is: " + status);
        return status;
    }

    public static String getMessageGetFRI(Response response) {
        String resp = response.andReturn().asString();
        XmlPath xmlPath = new XmlPath(resp);
        String message = xmlPath.get("ns2:getfinancialresourceinformationresponse.message");
        Logs.info("Message is: " + message);
        return message;
    }

    public static String getStatusVerifyFRI(Response response) {
        String resp = response.andReturn().asString();
        XmlPath xmlPath = new XmlPath(resp);
        String status = xmlPath.get("ns2:getfinancialresourceinformationrequest.valid");
        Logs.info("Status code is: " + status);
        return status;
    }

    public static HashMap <String, String>  parseWithdrawalResponse(Response response) {
        HashMap <String, String> responses= new HashMap<>();
        String resp = response.andReturn().asString();
        XmlPath xmlPath = new XmlPath(resp);
        responses.put("Status", xmlPath.get("ns2:withdrawresponse.status"));
        responses.put("Message", xmlPath.get("ns2:withdrawresponse.message"));
        responses.put("TransactionID", xmlPath.get("ns2:withdrawresponse.transactionid"));
        responses.put("ProviderTransactionID", xmlPath.get("ns2:withdrawresponse.providertransactionid"));
        return responses;
    }
}
