package APISpecifications;

import java.util.ArrayList;

public class Bodies {
    public static String getGetFRIBody(String account) {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<ns0:getfinancialresourceinformationrequest\txmlns:ns0=\"http://www.ericsson.com/em/emm/serviceprovider/v1_0/backend/client\">\n" +
                "\t<resource>FRI:" + account + "@UBA.bank/SP</resource>\n" +
                "\t<accountholderid>ID:231886501444/MSISDN</accountholderid>\n" +
                "</ns0:getfinancialresourceinformationrequest>";
    }

    public static String getGetFRIInvalidBody(String account) {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<ns0:getfinancialresourceinformationrequest\txmlns:ns0=\"http://www.ericsson.com/em/emm/serviceprovider/v1_0/backend/client\">\n" +
                "\t<resource>FR1I:" + account + "@UBA.bank/SP</resource>\n" +
                "\t<accountholderid>ID:231886501444/MSISDN</accountholderid>\n" +
                "</ns0:getfinancialresourceinformationrequest>";
    }

    public static String getVerifyFRIBody(ArrayList<String> details) {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<ns0:verifyfinancialresourceinformationrequest xmlns:ns0=\"http://www.ericsson.com/em/emm/serviceprovider/v1_0/backend/client\">   \n" +
                "\t\t<resource>FRI:" + details.get(0) + "@FISA.bank/SP</resource>\n" +
                "    <accountholderid>ID:" + details.get(1) + "/MSISDN</accountholderid>\n" +
                "   <extension/>\n" +
                "</ns0:verifyfinancialresourceinformationrequest>";
    }

    public static String getVerifyFRIInvalidBody(ArrayList<String> details) {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<ns0:verifyfinancialresourceinformationrequest xmlns:ns0=\"http://www.ericsson.com/em/emm/serviceprovider/v1_0/backend/client\">   \n" +
                "\t\t<resource>FR1I:" + details.get(0) + "@FISA.bank/SP</resource>\n" +
                "    <accountholderid>ID:" + details.get(1) + "/MSISDN</accountholderid>\n" +
                "   <extension/>\n" +
                "</ns0:verifyfinancialresourceinformationrequest>";
    }

    public static String getPaymentBody(ArrayList<String> details) {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<ns0:paymentrequest\txmlns:ns0=\"http://www.ericsson.com/em/emm/serviceprovider/v1_0/backend/client\">\n" +
                "\t<transactionid>" + details.get(0) + "</transactionid>\n" +
                "\t<accountholderid>ID:0783150895/MSISDN</accountholderid>\n" +
                "<receivingfri>FRI:" + details.get(1) + "@pushpull.bank/SP</receivingfri>\n" +
                "\t<amount>\n" +
                "\t\t<amount>" + details.get(3) + "</amount>\n" +
                "\t\t<currency>RWF</currency>\n" +
                "\t</amount>\n" +
                "\t<message>Test</message>\n" +
                "\t<extension/>\n" +
                "</ns0:paymentrequest>";
    }

    public static String getWithdrawalBody(ArrayList<String> details) {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<ns0:withdrawrequest xmlns:ns0=\"http://www.ericsson.com/em/emm/serviceprovider/v1_0/backend/client\">\n" +
                "    <resource>FRI:"+details.get(0)+"@FISA.bank/SP</resource>\n" +
                "    <accountholderid>ID:"+details.get(1)+"/MSISDN</accountholderid>\n" +
                "    <amount>\n" +
                "        <amount>"+details.get(2)+"</amount>\n" +
                "        <currency>RWF</currency>\n" +
                "    </amount>\n" +
                "    <transactionid>"+details.get(3)+"</transactionid>\n" +
                "</ns0:withdrawrequest>";
    }
}
