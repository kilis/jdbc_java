package com.company;

/**
 * Created by Kristaps on 15.05.2015.
 * Mysql datubazes pieslegsanas 
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class MySQLAccess {
    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    public void readMysqlDB() throws Exception {
        try {
            // Tiek ieladets mysql datubaze driveris, kuru iegust no mysql connector jar faila
            Class.forName("com.mysql.jdbc.Driver");
            // Tiek veiks pieslçgums ar datubazi
            connect = DriverManager
                    .getConnection("jdbc:mysql://localhost/2_1_db_stalizans_am_uzskaite?"
                            + "user=root&password=root");

            // Izveido statemntu lai varetu izveidot sql vaicajumu
            statement = connect.createStatement();
            // izveido resultset komandu lai veidotu sql vaicajumu
            resultSet = statement
                    .executeQuery("select * from 2_1_db_stalizans_am_uzskaite.apkalpojamas_firmas");
            //Izssauc unkciju kura izvada rezultatu
            writeResultSetMySQL(resultSet);
            System.out.println("\n");
            System.out.println("Ieada jaunas vertibas taubula\n");
            // Sagatavots steitments var saturet mainigos
            PreparedStatement preparedStatement = connect
                    .prepareStatement("insert into  2_1_db_stalizans_am_uzskaite.apkalpojamas_firmas values (?, ?, ?)");
            // "F_KODS, Firma, Adrese from 2_1_db_stalizans_am_uzskaite.apkalpojamas_firmas");
            // parametri sakas ar 1
            preparedStatement.setString(1, "AKC");
            preparedStatement.setString(2, "Akacis RVC");
            preparedStatement.setString(3, "akacis.lv");
            //dati tiek ievietoti tabula
            preparedStatement.executeUpdate();
            //Iegustam datus no atajunotas tabulas
            System.out.println("\n");
            System.out.println("Jaunais ieraksts ievietots\n");
            preparedStatement = connect
                    .prepareStatement("SELECT F_KODS, Firma, Adrese from 2_1_db_stalizans_am_uzskaite.apkalpojamas_firmas");
            //izpilda vaicajumu
            resultSet = preparedStatement.executeQuery();
            //izvada rezultatu uz ekrana
            writeResultSetMySQL(resultSet);
            System.out.println("\n");
            System.out.println("Izdzesh ierakstu no tabulas\n");
            // Ar vaiaicaumua palidzibu tiek izdzçsts ieraksts ar firmas kodu AKC
            preparedStatement = connect
                    .prepareStatement("delete from 2_1_db_stalizans_am_uzskaite.apkalpojamas_firmas where F_KODS= ? ; ");
            preparedStatement.setString(1, "AKC");
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
    private void writeResultSetMySQL(ResultSet resultSet) throws SQLException {
        System.out.printf("%1s  %-4s %-6s%n","F_KODS","Firma","Adrese");
        System.out.printf("-----------------------------------\n");
        while (resultSet.next()) {
            ///Iegust dautus no tabulas un izvad uz ekrana.
            String kods = resultSet.getString("F_KODS");
            String firma = resultSet.getString("Firma");
            String adrese = resultSet.getString("Adrese");
            System.out.printf("%1s  %7s   %9s\n",kods,firma,adrese);
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
