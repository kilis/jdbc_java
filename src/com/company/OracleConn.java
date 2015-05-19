package com.company;
import java.sql.*;

import static java.lang.System.*;
/**
 * Created by Kristaps on 17.05.2015.
 * Piesegsanas Oracle datubazei ziamntojot Oracle Java connectoru
 */
class OracleConn {
    private final Connection conn = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    public void readOracleDB() {
        try {
            // Tiek ieladets oracle datubazes driveris izmantojot
            Class.forName("oracle.jdbc.driver.OracleDriver");
            //Universistates servera IP prots
            String serverName = "85.254.218.228";
            //Srevera piesleguma ports
            String portNumber = "1521";
            String sid = "DITF11";
            //Piesleguma savienojuma adrese
            String url = "jdbc:oracle:thin:@" + serverName + ":" + portNumber + ":" + sid;
            //Pieejas dati datubaze
            String username = "DB_131RDB305";
            String password = "DB_131RDB305";
            //Piesleguma izveide datubazie
            Connection conn = DriverManager.getConnection(url, username, password);
            out.println( "Pieslegties Izdevas Oracle RTU datubaze" );
            //Izveido statementu datubazes pieslegsansas
            statement = conn.createStatement();
            //Vaicaums datubazei
            resultSet = statement
                    .executeQuery("select * from F_IZDEVUMI");
            //Izvada datubas metadatus
            writeMetaDataOracle(resultSet);
            System.out.printf("Tabulas F_IZDEVUMI saturs\n");
            //Izvada tabulas saturu
            writeResultSetOracle(resultSet);
            System.out.println("\n");
            System.out.println("Ievada jaunas vertibas taubulai F_IZDEVUMI\n");
            // Sagatavots steitments var saturet mainigos
            PreparedStatement preparedStatement = conn
                    .prepareStatement("insert into F_IZDEVUMI values (?, ?, ?, ?, ?, ?)");
            // ""NR_IZD","FIRMA","PAVADZIMES_NR","APRAKSTS","CENA","BANKA" no F_IZDEVUMI");
            // parametri sakas ar 1
            preparedStatement.setString(1, "6");
            preparedStatement.setString(2, "Ziedi JP");
            preparedStatement.setString(3, "RT-3434");
            preparedStatement.setString(4, "Meslojums 100 m^3");
            preparedStatement.setString(5, "343.23");
            preparedStatement.setString(6, "SEB");
            //dati tiek ievietoti tabula
            preparedStatement.executeUpdate();
            //Iegustam datus no atajunotas tabulas
            System.out.println("\n");
            System.out.println("Jaunais ieraksts ievietots\n");
            preparedStatement = conn
                    .prepareStatement("SELECT * from F_IZDEVUMI");
            //izpilda vaicajumu
            resultSet = preparedStatement.executeQuery();
            //izvada rezultatu uz ekrana
            writeResultSetOracle(resultSet);
            System.out.println("\n");
            System.out.println("Izdzesh ierakstu no tabulas\n");
            // Ar vaiaicaumua palidzibu tiek izdzçsts ieraksts ar NR_IZD =  6
            preparedStatement = conn
                    .prepareStatement("delete from F_IZDEVUMI where NR_IZD= ? ");
            preparedStatement.setString(1, "6");
            preparedStatement.executeUpdate();
            //Aizver savienujumu ar datubazi
            close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    //Iegust Metadatus
    private void writeMetaDataOracle(ResultSet resultSet) throws SQLException {
        // Iegust metadatus no tabulas

        System.out.println("Tabulas kolonas ir: ");

        System.out.println("Tabula: " + resultSet.getMetaData().getTableName(1));
        for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){
            System.out.println("Kolona " +i  + " "+ resultSet.getMetaData().getColumnName(i));
        }
    }
    private void writeResultSetOracle(ResultSet resultSet) throws SQLException {
        System.out.printf("%1s  %-4s   %-7s   %-6s   %-6s   %-6s%n","NR_IZD","FIRMA","PAVADZIMES_NR","APRAKSTS","CENA","BANKA");
        System.out.printf("--------------------------------------------------------------------\n");
        while (resultSet.next()) {
            ///Iegust dautus no tabulas un izvad uz ekrana.
            String nr = resultSet.getString("NR_IZD");
            String firma = resultSet.getString("FIRMA");
            String p_nr = resultSet.getString("PAVADZIMES_NR");
            String AP = resultSet.getString("APRAKSTS");
            String CN = resultSet.getString("CENA");
            String BN = resultSet.getString("BANKA");
            System.out.printf("%1s  %7s   %9s   %9s   %4s   %4s%n",nr,firma,p_nr,AP,CN,BN);
        }
    }
    // Funkcija kas aizver pieslegumu dubazei.
    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (conn != null) {
                conn.close();
            }
        } catch (Exception ignored) {

        }
    }
}
