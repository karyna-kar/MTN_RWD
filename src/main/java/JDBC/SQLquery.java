package JDBC;

import utilities.Logs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class SQLquery {
    public static String account = "";
    public static String customer = "";

    public static String getActiveCurrentAccount() {
        try {
            String query = "Select top 1 ccodcta from lfsbaku.dbo.accmctas where cclaper=1 and cestado = 'A'";
            ResultSet resultSet = JDBCConnection.selectFromDB(query);
            account = resultSet.getString("ccodcta");
            Logs.info("Account number is " + account);
        } catch (SQLException e) {
            Logs.error(e.getMessage());
        }
        return account;
    }

    public static String getNotExistingAccount() {
        try {
            String query = "Select top 1 ccodcta from lfsbaku.dbo.accmctas where cclaper=1 and cestado = 'A'";
            ResultSet resultSet = JDBCConnection.selectFromDB(query);
            account = resultSet.getString("ccodcta");
            Logs.info("Account number is " + account);
        } catch (SQLException e) {
            Logs.error(e.getMessage());
        }
        //Changing the first character to 1 in order to make account not existing
        return "1" + account.substring(1);
    }

    public static String getCustomerWithOneAccount() {
        try {
            String query = "select top 1 c.ccodcli, sum(c.opened ) ,  sum(c.closed ) from (\n" +
                    "select ccodcli, ccodcta, cestado, case when cestado in ('A', 'G', 'R') then 1 else 0 end as opened, case when cestado not in ('A', 'G', 'R') then 1 else 0 end as closed  from accmctas  where cclaper = 1 \n" +
                    "union all\n" +
                    "select ccodcli, ccodcta, cestado, case when cestado in ('A', 'G', 'R') then 1 else 0 end as opened, case when cestado not in ('A', 'G', 'R') then 1 else 0 end as closed   from avimctas where cclaper = 1 )c\n" +
                    "Group by c.ccodcli\n" +
                    "having sum(c.opened) =1";
            ResultSet resultSet = JDBCConnection.selectFromDB(query);
            customer = resultSet.getString("ccodcli");
            Logs.info("Customer number is " + customer);
        } catch (SQLException e) {
            Logs.error(e.getMessage());
        }
        return customer;
    }

    public static String getCustomerWithMultipleAccount() {
        try {
            String query = "select top 1 c.ccodcli, sum(c.opened ) ,  sum(c.closed ) from (\n" +
                    "select ccodcli, ccodcta, cestado, case when cestado in ('A', 'G', 'R') then 1 else 0 end as opened, case when cestado not in ('A', 'G', 'R') then 1 else 0 end as closed  from accmctas  where cclaper = 1 \n" +
                    "union all\n" +
                    "select ccodcli, ccodcta, cestado, case when cestado in ('A', 'G', 'R') then 1 else 0 end as opened, case when cestado not in ('A', 'G', 'R') then 1 else 0 end as closed   from avimctas where cclaper = 1 )c\n" +
                    "Group by c.ccodcli\n" +
                    "having sum(c.opened) >1";
            ResultSet resultSet = JDBCConnection.selectFromDB(query);
            customer = resultSet.getString("ccodcli");
            Logs.info("Customer number is " + customer);
        } catch (SQLException e) {
            Logs.error(e.getMessage());
        }
        return customer;
    }

    public static String getNotExistingCustomer() {
        try {
            String query = "select top 1 c.ccodcli, sum(c.opened ) ,  sum(c.closed ) from (\n" +
                    "select ccodcli, ccodcta, cestado, case when cestado in ('A', 'G', 'R') then 1 else 0 end as opened, case when cestado not in ('A', 'G', 'R') then 1 else 0 end as closed  from accmctas  where cclaper = 1 \n" +
                    "union all\n" +
                    "select ccodcli, ccodcta, cestado, case when cestado in ('A', 'G', 'R') then 1 else 0 end as opened, case when cestado not in ('A', 'G', 'R') then 1 else 0 end as closed   from avimctas where cclaper = 1 )c\n" +
                    "Group by c.ccodcli\n" +
                    "having sum(c.opened) =1";
            ResultSet resultSet = JDBCConnection.selectFromDB(query);
            customer = resultSet.getString("ccodcli");
            Logs.info("Customer number is " + customer);
        } catch (SQLException e) {
            Logs.error(e.getMessage());
        }
        //Changing the first character to 1 in order to make account not existing
        return "1" + customer.substring(1);
    }

    public static HashMap<String, String> getCurrentRWFAccountActiveMB(String status, Double amount) {
        HashMap<String, String> sqlResult = new HashMap<>();
        try {
            String query = "Select top 1  ccodcta, cl.cMobileBankingPhone from accmctas ac\n" +
                    "INNER JOIN climide cl ON ac.ccodcli = cl.ccodcli\n" +
                    "where ac.cestado = '"+status+"' and ac.cclaper = 1 and ac.cmoneda=1 and ac.MobileRegistrationStatus=2 and ac.cMobileBanking='A' and nsaldis >= "+amount+"";
            ResultSet resultSet = JDBCConnection.selectFromDB(query);
            sqlResult.put("Account", resultSet.getString("ccodcta"));
            sqlResult.put("MobilePhone", resultSet.getString("cMobileBankingPhone").trim());
            Logs.info("Account number is " + sqlResult.get("Account") + ", MobileBankingPhone is " + sqlResult.get("MobilePhone"));
        } catch (SQLException e) {
            Logs.error(e.getMessage());
        }
        return sqlResult;
    }

    public static HashMap<String, String> getCurrentRWFAccountInsufficientBalance(Double amount) {
        HashMap<String, String> sqlResult = new HashMap<>();
        try {
            String query = "Select top 1  ccodcta, cl.cMobileBankingPhone from accmctas ac\n" +
                    "INNER JOIN climide cl ON ac.ccodcli = cl.ccodcli\n" +
                    "where ac.cestado = 'A' and ac.cclaper = 1 and ac.cmoneda=1 and ac.MobileRegistrationStatus=2 and ac.cMobileBanking='A' and nsaldis < "+amount+"";
            ResultSet resultSet = JDBCConnection.selectFromDB(query);
            sqlResult.put("Account", resultSet.getString("ccodcta"));
            sqlResult.put("MobilePhone", resultSet.getString("cMobileBankingPhone").trim());
            Logs.info("Account number is " + sqlResult.get("Account") + ", MobileBankingPhone is " + sqlResult.get("MobilePhone"));
        } catch (SQLException e) {
            Logs.error(e.getMessage());
        }
        return sqlResult;
    }

    public static HashMap<String, String> getCurrentRWFAccountActiveMB(String status) {
        HashMap<String, String> sqlResult = new HashMap<>();
        try {
            String query = "Select top 1  ccodcta, cl.cMobileBankingPhone from accmctas ac\n" +
                    "INNER JOIN climide cl ON ac.ccodcli = cl.ccodcli\n" +
                    "where ac.cestado = '"+status+"' and ac.cclaper = 1 and ac.cmoneda=1 and ac.MobileRegistrationStatus=2 and ac.cMobileBanking='A' and nsaldis > 0";
            ResultSet resultSet = JDBCConnection.selectFromDB(query);
            sqlResult.put("Account", resultSet.getString("ccodcta"));
            sqlResult.put("MobilePhone", resultSet.getString("cMobileBankingPhone").trim());
            Logs.info("Account number is " + sqlResult.get("Account") + ", MobileBankingPhone is " + sqlResult.get("MobilePhone"));
        } catch (SQLException e) {
            Logs.error(e.getMessage());
        }
        return sqlResult;
    }

    public static HashMap<String, String> getActiveSavingRWFAccountActiveMB() {
        HashMap<String, String> sqlResult = new HashMap<>();
        try {
            String query = "Select top 1  ccodcta, cl.cMobileBankingPhone from avimctas av\n" +
                    "INNER JOIN climide cl ON av.ccodcli = cl.ccodcli\n" +
                    "where av.cestado = 'A' and av.cclaper = 1 and av.cmoneda=1 and av.MobileRegistrationStatus=2 and av.cMobileBanking='A' and nsaldis > 100";
            ResultSet resultSet = JDBCConnection.selectFromDB(query);
            sqlResult.put("Account", resultSet.getString("ccodcta"));
            sqlResult.put("MobilePhone", resultSet.getString("cMobileBankingPhone").trim());
            Logs.info("Account number is " + sqlResult.get("Account") + ", MobileBankingPhone is " + sqlResult.get("MobilePhone"));
        } catch (SQLException e) {
            Logs.error(e.getMessage());
        }
        return sqlResult;
    }

    public static HashMap<String, String> getActiveUSDAccountActiveMB() {
        HashMap<String, String> sqlResult = new HashMap<>();
        try {
            String query = "Select top 1  ccodcta, cl.cMobileBankingPhone from accmctas ac\n" +
                    "INNER JOIN climide cl ON ac.ccodcli = cl.ccodcli\n" +
                    "where ac.cestado = 'A' and ac.cclaper = 1 and ac.cmoneda=2 and ac.MobileRegistrationStatus=2 and ac.cMobileBanking='A' and nsaldis > 2";
            ResultSet resultSet = JDBCConnection.selectFromDB(query);
            sqlResult.put("Account", resultSet.getString("ccodcta"));
            sqlResult.put("MobilePhone", resultSet.getString("cMobileBankingPhone").trim());
            Logs.info("Account number is " + sqlResult.get("Account") + ", MobileBankingPhone is " + sqlResult.get("MobilePhone"));
        } catch (SQLException e) {
            Logs.error(e.getMessage());
        }
        return sqlResult;
    }

    public static HashMap <String, String> getNotExistingAccountMB() {
        HashMap <String, String> sqlResult = new HashMap <>();
        try {
            String query = "Select top 1  ccodcta, cl.cMobileBankingPhone from accmctas ac\n" +
                    "INNER JOIN climide cl ON ac.ccodcli = cl.ccodcli\n" +
                    "where ac.cestado = 'A' and ac.cclaper = 1 and ac.cmoneda=1 and ac.MobileRegistrationStatus=2 and ac.cMobileBanking='A' and nsaldis > 0";
            ResultSet resultSet = JDBCConnection.selectFromDB(query);
            //Changing the first character to 1 in order to make account not existing
            sqlResult.put("Account", "1" + resultSet.getString("ccodcta").substring(1));
            sqlResult.put("MobilePhone", resultSet.getString("cMobileBankingPhone").trim());
            Logs.info("Account number is " + sqlResult.get("Account") + ", MobileBankingPhone is " + sqlResult.get("MobilePhone"));
        } catch (SQLException e) {
            Logs.error(e.getMessage());
        }
        return sqlResult;
    }

    public static HashMap<String, String> getAccountInactiveCustomerMB() {
        HashMap<String, String> sqlResult = new HashMap<>();
        try {
            String query = "Select top 1  ccodcta, cl.cMobileBankingPhone from accmctas ac\n" +
                    "INNER JOIN climide cl ON ac.ccodcli = cl.ccodcli\n" +
                    "where ac.cestado = 'A' and ac.cclaper = 1 and ac.cmoneda=1 and cl.cMobileBanking != 'A' and cl.cMobileBankingPhone is not null and nsaldis > 0";
            ResultSet resultSet = JDBCConnection.selectFromDB(query);
            sqlResult.put("Account", resultSet.getString("ccodcta"));
            sqlResult.put("MobilePhone",resultSet.getString("cMobileBankingPhone").trim());
            Logs.info("Account number is " + sqlResult.get("Account") + ", MobileBankingPhone is " + sqlResult.get("MobilePhone"));
        } catch (SQLException e) {
            Logs.error(e.getMessage());
        }
        return sqlResult;
    }

    public static HashMap<String, String> getAccountInactiveAccountMB() {
        HashMap<String, String> sqlResult = new HashMap<>();
        try {
            String query = "Select  top 1 ccodcta, cl.cMobileBankingPhone from accmctas ac\n" +
                    "INNER JOIN climide cl ON ac.ccodcli = cl.ccodcli\n" +
                    "where ac.cestado = 'A' and ac.cclaper = 1 and ac.cmoneda=1 and cl.cMobileBanking = 'A' and cl.MobileRegistrationStatus = 2  and nsaldis > 0 and ac.cMobileBanking != 'A'";
            ResultSet resultSet = JDBCConnection.selectFromDB(query);
            sqlResult.put("Account", resultSet.getString("ccodcta"));
            sqlResult.put("MobilePhone", resultSet.getString("cMobileBankingPhone").trim());
            Logs.info("Account number is " + sqlResult.get("Account") + ", MobileBankingPhone is " + sqlResult.get("MobilePhone"));
        } catch (SQLException e) {
            Logs.error(e.getMessage());
        }
        return sqlResult;
    }

    public static HashMap<String, String> getNotMatchingPhoneActiveMB() {
        HashMap <String, String> sqlResult = new HashMap<>();
        try {
            String query = "Select top 2  ccodcta, cl.cMobileBankingPhone from accmctas ac\n" +
                    "INNER JOIN climide cl ON ac.ccodcli = cl.ccodcli\n" +
                    "where ac.cestado = 'A' and ac.cclaper = 1 and ac.cmoneda=1 and ac.MobileRegistrationStatus=2 and ac.cMobileBanking='A' and nsaldis > 100";
            ResultSet resultSet = JDBCConnection.selectFromDB(query);
            resultSet.first();
            sqlResult.put("Account",resultSet.getString("ccodcta"));
            resultSet.last();
            sqlResult.put("MobilePhone",resultSet.getString("cMobileBankingPhone").trim());
            Logs.info("Account number is " + sqlResult.get("Account") + ", MobileBankingPhone is " + sqlResult.get("MobilePhone"));
        } catch (SQLException e) {
            Logs.error(e.getMessage());
        }
        return sqlResult;
    }

    //Convert the account to 13 characters
    public static String convertAccountNumb(String fullAccount) {
        StringBuilder outputAccount = new StringBuilder();
        char[] digits = fullAccount.toCharArray();
        for (int i = 0; i < fullAccount.length(); i++) {
            if (i > 3 && i != 11) {
                outputAccount = outputAccount.append(digits[i]);
            }
        }
        return outputAccount.toString();
    }

    public static String getUniqueTransactionID() {
        String providerTransactionID = "";
        try {
            String query = "Select top 1 * from lfsbaku.dbo.ExternalProviderTransaction \n" +
                    "where ProviderTransactionId not like '%-R' order by ID desc";
            ResultSet resultSet = JDBCConnection.selectFromDB(query);
            providerTransactionID = resultSet.getString("ProviderTransactionId");
            Logs.info("ProviderTransactionId " + providerTransactionID);
        } catch (SQLException e) {
            Logs.error(e.getMessage());
        }
        return Long.toString(Long.parseLong(providerTransactionID)+1);
    }

    public static String getExistingTransactionID() {
        String providerTransactionID = "";
        try {
            String query = "Select top 1 * from lfsbaku.dbo.ExternalProviderTransaction \n" +
                    "where ProviderTransactionId not like '%-R' order by ID desc";
            ResultSet resultSet = JDBCConnection.selectFromDB(query);
            providerTransactionID = resultSet.getString("ProviderTransactionId");
            Logs.info("ProviderTransactionId " + providerTransactionID);
        } catch (SQLException e) {
            Logs.error(e.getMessage());
        }
        return providerTransactionID;
    }


    public static String getVoucherNumber(String transactionID) {
        String voucherNumber = "";
        try {
            String query = "Select top 1 VoucherNumber from lfsbaku.dbo.ExternalProviderTransaction \n" +
                    "where ProviderTransactionId = '"+transactionID+"'";
            ResultSet resultSet = JDBCConnection.selectFromDB(query);
            voucherNumber = resultSet.getString("VoucherNumber");
            Logs.info("VoucherNumber is" + voucherNumber);
        } catch (SQLException e) {
            Logs.error(e.getMessage());
        }
        return voucherNumber;
    }


  public static String getTransactionMinimumLimit() {
      String minLimit = "";
      try {
          String query = "  select * from lfsbaku.api.CurrencyConfiguration\n" +
                  "  where Currency  = 'RWF' and ProviderID = (Select ID from lfsbaku..ExternalProvider where ProviderCode = 'Ericsson')";
          ResultSet resultSet = JDBCConnection.selectFromDB(query);
          minLimit = resultSet.getString("TransactionMinimumLimit");
          Logs.info("Transaction Minimum Limit RWF is" + minLimit);
      } catch (SQLException e) {
          Logs.error(e.getMessage());
      }
      return minLimit;
    }
}
