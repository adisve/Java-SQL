package main;

import java.util.Scanner;

import utils.*;

public class Main {

    public static void main(String[] args) {

        String username = "guest";
        String password = "password";
        String database = "pillow_db";
        Menus menus = new Menus();

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
        /**** Create user object ****/
        var user = new User(username, password);
        

        /**** Here we attempt to establish a connection between the user and the database ****/
        try {

            var connector = new Connector("localhost", database, user);

            if(connector.isConnected()) System.out.printf("\nSuccessfully connected to %s\n", connector.getHost());

            while(connector.isConnected()){

                /**** Main event loop ****/
                System.out.println("1. SELECT\n2. UPDATE\n3. INSERT\n4. DELETE\n>> ");
                sc.reset();
                var user_input = sc.nextLine();
                while (true) {
                    if(user_input.matches("^[1-9]\\d*$")){
                        break;
                    }else{
                        System.out.println("\nInput has to be a number\n");
                        System.out.println("1. SELECT\n2. UPDATE\n3. INSERT\n4. DELETE\n>> ");
                        user_input = sc.nextLine();
                    }
                }
                var options = Integer.parseInt(user_input);

                switch (options) {
                    case 1:
                        menus.selectMenu(sc, connector);
                        break;
                
                    case 2:
                        menus.updateMenu(sc, connector);
                        break;

                    case 3:
                        menus.insertMenu(sc, connector);

                    case 4:
                        menus.deleteMenu(sc, connector);

                    case 5:
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
