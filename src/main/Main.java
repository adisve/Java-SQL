package main;
import java.util.Scanner;

import utils.Connector;
import utils.User;
public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Hostname: ");
        String hostname = sc.nextLine();
        System.out.println("Database: ");
        String database = sc.nextLine();
        System.out.println("Username: ");
        String username = sc.nextLine();
        System.out.println("Password: ");
        String password = sc.nextLine();

        /* Create user object */
        User user = new User(username, password);

        try{

            Connector connector = new Connector(hostname, database, user);

            if(connector.isConnected()) System.out.printf("Successfully connected to %s", connector.getHost());

        }catch (Exception e) {
            e.printStackTrace();
        } {

        }
    }
}
