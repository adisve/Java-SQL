package utils;

import java.lang.System;
import java.sql.ResultSet;
import java.util.Scanner;

public class User {
    private String name;
    private String password;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return this.name;
    }

    public String getPassword() {
        return this.password;
    }

    public void selectQuery(Connector connector, Scanner sc) {

        System.out.println("\nType 'exit' anytime to close the program.\n");

        System.out.print("\nEnter your SQL query >> ");
        var query = sc.nextLine();
        if(query.equals("exit")) {
            sc.close();
            connector.close();
            System.exit(0);
        }
        if(query.matches(".*\\w.*")){
            try {
                ResultSet result = connector.query(query);
                connector.displayQuery(result);
            } catch (Exception e) {
                System.out.println("\nCannot issue emtpy query.\n");
            }
        }
    }

    public void updateQuery(Connector connector, Scanner sc){
        System.out.println("\nType 'exit' anytime to close the program.\n");
        System.out.print("\nEnter your SQL query >> ");
        var query = sc.nextLine();
        if(query.equals("exit")) {
            sc.close();
            connector.close();
            System.exit(0);
        }
        if(query.matches(".*\\w.*")){
            try {
                int result = connector.update(query);
                if(result == 1) System.out.println("\nSuccess\n");
                else System.out.println("Something went wrong");
            } catch (Exception e) {
                System.out.println("\nCannot issue emtpy query.\n");
            }
        }
    }

}
