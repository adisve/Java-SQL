package main;

import java.sql.SQLException;
import java.util.Scanner;
import utils.*;

public class Main {

    final String USERNAME = "API";
    final String PASSWORD = "P@sSw0r_D";
    final String DATABASE = "pillow_db";
    final static Menus MENUS = new Menus();

    public static void main(String[] args) {
        Main main = new Main();
        Scanner sc = new Scanner(System.in);

        /**** Create user object ****/
        var user = new User(main.USERNAME, main.PASSWORD);
        

        /**** Here we attempt to establish a connection between the user and the database ****/
        try {
            Connector connector = new Connector("localhost", main.DATABASE, user);
            if(connector.isConnected()) System.out.printf("\nSuccessfully connected to %s\n", connector.getHost());

            while(connector.isConnected()){

                /**** Main event loop ****/
                System.out.println("1. SELECT\n2. UPDATE\n3. DELETE\n4. EXIT\n>> ");
                var user_input = sc.next();
                while (!main.checkInput(user_input)) {
                    System.out.println("\nInput has to be a number in the range 1 - 4\n");
                    System.out.println("1. SELECT\n2. UPDATE\n3. DELETE\n4. EXIT>> ");
                    user_input = sc.next();
                }
                int option = Integer.parseInt(user_input);
                if(option != 4){
                    MENUS.generalMenu(sc, option, connector);
                }else{
                    break;
                }
            }
        } catch (SQLException e) {
            System.out.println("Fatal error\n");
            e.printStackTrace();
        }finally{
            sc.close();
        }
    }

    private boolean checkInput(String input){
        return input.matches("[1-4]?");
    }
}
