package com.company;
import com.company.MySQLAccess;
import com.company.MsAcess;
import com.company.excell;
import com.company.OracleConn;
import java.util.Scanner;

class Main {
//Galvena izpildes funkcija
    public static void main(String[] args) throws Exception {
        //Izveido ojektu pieeju noteiktajiem java failiem
        MySQLAccess Mysql = new MySQLAccess();
        MsAcess MsAccess = new MsAcess();
        OracleConn Oracle = new OracleConn();
        excell Excel = new excell();
        String izvele;
        Scanner scan = new Scanner(System.in);
        do {
            System.out.println("Izvelne: ");
            System.out.println("a: MySQL datubazes lokala servera pieslegums");
            System.out.println("b: Access datubazes faila pieslegums");
            System.out.println("c: Oracle Unversitates servera pieslegums");
            System.out.println("d: Excell faila pieslegums");
            System.out.println("q: Iziet");
            izvele = scan.nextLine();
            switch (izvele) {
                case "a":
                    System.out.println("----------------------------------------\n");
                    System.out.println("Notiek Mysql datubazes pievienoðanas\n");
                    Mysql.readMysqlDB();
                    System.out.println("----------------------------------------\n");
                    break;
                case "b":
                    System.out.println("----------------------------------------\n");
                    System.out.println("Notiek MS Access datubazes pievienoðanas\n");
                    MsAccess.readAccessDB();
                    System.out.println("----------------------------------------\n");
                    break;
                case "c":
                    System.out.println("----------------------------------------\n");
                    System.out.println("Notiek Oracle datubazes pievienoðanas\n");
                    Oracle.readOracleDB();
                    System.out.println("----------------------------------------\n");
                    break;
                case "d":
                    System.out.println("----------------------------------------\n");
                    System.out.println("Notiek Excell pievienoðanas\n");
                    Excel.readExcelFile();
                    System.out.println("----------------------------------------\n");
                    break;
                default:
            }
        } while (!izvele.equals("q"));
        System.out.println("");
    }
}
