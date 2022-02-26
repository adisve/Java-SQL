package main;

import java.util.Scanner;

import utils.Connector;
import utils.User;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("\nDatabase >> ");
        String database = sc.nextLine();
        System.out.print("\nUsername >> ");
        String username = sc.nextLine();
        System.out.print("\nPassword >> ");
        String password = sc.nextLine();

        /* Create user object */
        var user = new User(username, password);
        
        try {

            var connector = new Connector("localhost", database, user);

            if (connector.isConnected())
                System.out.printf("\nSuccessfully connected to %s\n", connector.getHost());
                while(connector.isConnected()) user.createQuery(connector, sc);

        } catch (Exception e) {
        }finally{
            sc.close();
        }
    }
}
