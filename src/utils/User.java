package utils;

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

    public void createQuery(Connector connector, Scanner sc) {
        System.out.print("\nEnter your SQL query >> ");
        String query = sc.nextLine();
        try {
            ResultSet result = connector.query(query);
            connector.displayQuery(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
