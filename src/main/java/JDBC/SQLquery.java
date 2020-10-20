package JDBC;

import utilities.Logs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
        // return convertAccountNumb(account);
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
        return "1" + customer.substring(1);
    }


    public static ArrayList<String> getAccountActiveCustomerMB() {
        ArrayList<String> sqlResult = new ArrayList<>();
        try {
            String query = "Select top 1  ccodcta, cl.cMobileBankingPhone from accmctas ac\n" +
                    "INNER JOIN climide cl ON ac.ccodcli = cl.ccodcli\n" +
                    "where ac.cestado = 'A' and ac.cclaper = 1 and ac.cmoneda=1 and ac.MobileRegistrationStatus=2 and ac.cMobileBanking='A' and nsaldis > 100";
            ResultSet resultSet = JDBCConnection.selectFromDB(query);
            sqlResult.add(resultSet.getString("ccodcta"));
            sqlResult.add(resultSet.getString("cMobileBankingPhone").trim());
            Logs.info("Account number is " + sqlResult.get(0) + ", MobileBankingPhone is " + sqlResult.get(1));
        } catch (SQLException e) {
            Logs.error(e.getMessage());
        }
        return sqlResult;
    }

    public static ArrayList<String> getNotExistingAccountMB() {
        ArrayList<String> sqlResult = new ArrayList<>();
        try {
            String query = "Select top 1  ccodcta, cl.cMobileBankingPhone from accmctas ac\n" +
                    "INNER JOIN climide cl ON ac.ccodcli = cl.ccodcli\n" +
                    "where ac.cestado = 'A' and ac.cclaper = 1 and ac.cmoneda=1 and ac.MobileRegistrationStatus=2 and ac.cMobileBanking='A' and nsaldis > 0";
            ResultSet resultSet = JDBCConnection.selectFromDB(query);
            //Changing the first character to 1 in order to make account not existing
            sqlResult.add("1" + resultSet.getString("ccodcta").substring(1));
            sqlResult.add(resultSet.getString("cMobileBankingPhone").trim());
            Logs.info("Account number is " + sqlResult.get(0) + ", MobileBankingPhone is " + sqlResult.get(1));
        } catch (SQLException e) {
            Logs.error(e.getMessage());
        }
        return sqlResult;
    }

    public static ArrayList<String> getAccountInactiveCustomerMB() {
        ArrayList<String> sqlResult = new ArrayList<>();
        try {
            String query = "Select top 1  ccodcta, cl.cMobileBankingPhone from accmctas ac\n" +
                    "INNER JOIN climide cl ON ac.ccodcli = cl.ccodcli\n" +
                    "where ac.cestado = 'A' and ac.cclaper = 1 and ac.cmoneda=1 and cl.cMobileBanking != 'A' and cl.cMobileBankingPhone is not null and nsaldis > 0";
            ResultSet resultSet = JDBCConnection.selectFromDB(query);
            sqlResult.add(resultSet.getString("ccodcta"));
            sqlResult.add(resultSet.getString("cMobileBankingPhone").trim());
            Logs.info("Account number is " + sqlResult.get(0) + ", MobileBankingPhone is " + sqlResult.get(1));
        } catch (SQLException e) {
            Logs.error(e.getMessage());
        }
        return sqlResult;
    }

    public static ArrayList<String> getAccountInactiveAccountMB() {
        ArrayList<String> sqlResult = new ArrayList<>();
        try {
            String query = "Select  top 1 ccodcta, cl.cMobileBankingPhone from accmctas ac\n" +
                    "INNER JOIN climide cl ON ac.ccodcli = cl.ccodcli\n" +
                    "where ac.cestado = 'A' and ac.cclaper = 1 and ac.cmoneda=1 and cl.cMobileBanking = 'A' and cl.MobileRegistrationStatus = 2  and nsaldis > 0 and ac.cMobileBanking != 'A'";
            ResultSet resultSet = JDBCConnection.selectFromDB(query);
            sqlResult.add(resultSet.getString("ccodcta"));
            sqlResult.add(resultSet.getString("cMobileBankingPhone").trim());
            Logs.info("Account number is " + sqlResult.get(0) + ", MobileBankingPhone is " + sqlResult.get(1));
        } catch (SQLException e) {
            Logs.error(e.getMessage());
        }
        return sqlResult;
    }



    public static ArrayList<String> getNotMatchingPhoneActiveMB() {
        ArrayList<String> sqlResult = new ArrayList<>();
        try {
            String query = "Select top 2  ccodcta, cl.cMobileBankingPhone from accmctas ac\n" +
                    "INNER JOIN climide cl ON ac.ccodcli = cl.ccodcli\n" +
                    "where ac.cestado = 'A' and ac.cclaper = 1 and ac.cmoneda=1 and ac.MobileRegistrationStatus=2 and ac.cMobileBanking='A' and nsaldis > 100";
            ResultSet resultSet = JDBCConnection.selectFromDB(query);
            resultSet.first();
            sqlResult.add(resultSet.getString("ccodcta"));
            resultSet.last();
            sqlResult.add(resultSet.getString("cMobileBankingPhone").trim());
            Logs.info("Account number is " + sqlResult.get(0) + ", MobileBankingPhone is " + sqlResult.get(1));
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
}
