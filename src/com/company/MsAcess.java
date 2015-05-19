package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.PreparedStatement;

/**
 * Created by Kristaps on 15.05.2015.
 * Pieslsanas MS Acccess datubazeei izmantojot ucanaccess driveri
 */
@SuppressWarnings("ALL")
class MsAcess {
    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    public void readAccessDB() throws Exception {
        try {
    Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            // Tiek ieladets access datubaze driveris, kuru iegust no uncannacces konektora
            connect = DriverManager.getConnection("jdbc:ucanaccess://A:/db.mdb");
            //Izveido statemntu datubazes pieslegsanas
            statement = connect.createStatement();
            //Veic vaicajumu
            resultSet = statement
                    .executeQuery("Select * from epasti");
            //Izvada vaicajumu
            writeResultSetAccess(resultSet);
            System.out.println("\n");
            System.out.println("Ieada jaunas vertibas taubula\n");
            // Sagatavots steitments var saturet mainigos
            PreparedStatement preparedStatement = connect
                    .prepareStatement("insert into epasti values (?, ?, ?)");
            // "E_ID","Epasts","Parole" no epasti");
            // parametri sakas ar 1
            preparedStatement.setString(1, "100");
            preparedStatement.setString(2, "arc@arcers.lv");
            preparedStatement.setString(3, "1112");
            //dati tiek ievietoti tabula
            preparedStatement.executeUpdate();
            //Iegustam datus no atajunotas tabulas
            System.out.println("\n");
            System.out.println("Jaunais ieraksts ievietots\n");
            preparedStatement = connect
                    .prepareStatement("SELECT * from epasti");
            //izpilda vaicajumu
            resultSet = preparedStatement.executeQuery();
            //izvada rezultatu uz ekrana
            writeResultSetAccess(resultSet);
            System.out.println("\n");
            System.out.println("Izdzesh ierakstu no tabulas\n");
            // Ar vaiaicaumua palidzibu tiek izdzçsts ieraksts ar E_ID = 100
            preparedStatement = connect
                    .prepareStatement("delete from epasti where E_ID= ? ; ");
            preparedStatement.setString(1, "100");
            preparedStatement.executeUpdate();
            //Tiek ieguti metadati no tabulas datori
            System.out.println("\n");
            System.out.println("Iegust meta datus no taublas\n");
            resultSet = statement
                    .executeQuery("select * from datori");
            writeMetaData(resultSet);
        } finally {
            close();
        }

    }
    //Funkcija kas iegûst no tabulas metadatus
    private void writeMetaData(ResultSet resultSet) throws SQLException {
        // Iegust metadatus no tabulas

        System.out.println("Tabulas kolonas ir: ");

        System.out.println("Tabula: " + resultSet.getMetaData().getTableName(1));
        for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){
            System.out.println("Kolona " +i  + " "+ resultSet.getMetaData().getColumnName(i));
        }
    }
    //Funkcija datu izvadei no tabulas.
    private void writeResultSetAccess(ResultSet resultSet) throws SQLException {
        System.out.printf("%1s  %-4s %-6s%n","E_ID","Epasts","Parole");
        System.out.printf("-----------------------------------\n");
        while (resultSet.next()) {
            ///Iegust dautus no tabulas un izvad uz ekrana.
            String Eid = resultSet.getString("E_ID");
            String epasts = resultSet.getString("Epasts");
            String parole = resultSet.getString("Parole");
            System.out.printf("%1s  %7s   %9s\n",Eid,epasts,parole);
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

            if (connect != null) {
                connect.close();
            }
        } catch (Exception ignored) {

        }
    }
}
