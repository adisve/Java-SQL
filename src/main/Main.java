package main;

import java.util.Scanner;

import utils.*;

public class Main {

    public static void main(String[] args) {

        String username = "guest";
        String password = "password";

        Scanner sc = new Scanner(System.in);

        /**** Ask user if they want to use the default config ****/
        System.out.println("\nWould you like to use the default login options? (Username: guest, Password: password)?\n(Y/N)\n>> ");

        var choice = sc.nextLine();

        if(choice.equals("N") || choice.equals("n")){
            /**** Here we collect the users login credentials ****/
            System.out.print("\nUsername >> ");
            username = sc.nextLine();
            System.out.print("\nPassword >> ");
            password = sc.nextLine();

        }
        System.out.print("\nDatabase >> ");
        String database = sc.nextLine();

        /**** Create user object ****/
        var user = new User(username, password);
        

        /**** Here we attempt to establish a connection between the user and the database ****/
        try {

            var connector = new Connector("localhost", database, user);

            if (connector.isConnected())
                System.out.printf("\nSuccessfully connected to %s\n", connector.getHost());

                /**** Main event loop ****/
                while(connector.isConnected()) {

                    System.out.println("1. Query\n2. Update/Insert/Delete\n3. Exit\n>> ");

                    var user_input = Integer.parseInt(sc.nextLine());
                
                    switch (user_input) {
                        case 1:
                            user.selectQuery(connector, sc);
                            break;
                    
                        case 2:
                            user.updateQuery(connector, sc);
                            break;
                        
                            case 3:
                            connector.close();
                        default:
                            break;
                    }
                }

        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            sc.close();
        }
    }
}
