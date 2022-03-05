package utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Menus {
    
    private ResultSet rs;
    private PreparedStatement ps;
    
    /**** SQL statements that are used in prepared statements ****/
    String[] select_statements = {"SELECT * FROM user where user.name = ?", "SELECT user.name as Name, user.email as Email " +
        "phone.imei as IMEI, phone.mac AS MAC FROM user INNER JOIN phone on phone.user_id = user.user_id WHERE user.name = ?", 
        "SELECT user.name, schedule.alarm_date, schedule.name FROM user INNER JOIN schedule ON schedule.user_id = user.user_id WHERE user.name = ?"};
    String[] delete_statements = {"DELETE FROM user WHERE name = ?", "DELETE FROM schedule WHERE name = ?", 
    "DELETE FROM pillow WHERE pillow_id = ?", "DELETE FROM bluetooth_module WHERE module_id = ?", "DELETE FROM theme WHERE theme_id = ?"};

    public void updateMenu(Scanner sc, Connector connector){
        
    }

    public void selectMenu(Scanner sc, Connector connector) throws SQLException{
        
        System.out.println("\n1. Fetch all information about a user\n2. "
        + "Fetch phone information about a user\n3. Fetch wakeup routines for a user\n");

        var option = Integer.parseInt(sc.nextLine());

        try{
            System.out.println("\nEnter the name of the user >> ");
            switch(option){
                case 1:
                    var name_user = sc.next();
                    ps = connector.getConnection().prepareStatement(select_statements[0]);
                    ps.setString(1, name_user);
                    rs =  ps.executeQuery();
                    connector.displayQuery(rs);
                    sc.nextLine();
                case 2:
                    var phone_user = sc.next();
                    ps = connector.getConnection().prepareStatement(select_statements[1]);
                    ps.setString(1, phone_user);
                    rs =  ps.executeQuery();
                    connector.displayQuery(rs);
                    sc.nextLine();
                case 3:
                    var schedule_user = sc.next();
                    ps = connector.getConnection().prepareStatement(select_statements[2]);
                    ps.setString(1, schedule_user);
                    rs =  ps.executeQuery();
                    connector.displayQuery(rs);
                    sc.nextLine();
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void insertMenu(Scanner sc, Connector connector){

    }

    public void deleteMenu(Scanner sc, Connector connector){
        System.out.println("\n1. Delete user\n2. Delete schedule\n3. Delete pillow\n4. Delete Bluetooth module\n5. Delete Theme");

        var option = Integer.parseInt(sc.nextLine());

        try{
            switch(option){
                case 1:
                    System.out.println("\nEnter the name of user you wish to delete >> ");
                    var user_name = sc.next();
                        ps = connector.getConnection().prepareStatement(delete_statements[0]);
                        ps.setString(1, user_name);
                        ps.executeQuery();

                        connector.getConnection().createStatement().execute("");
                        sc.nextLine();
                case 2:
                    System.out.println("\nEnter the id of the schedule you wish to delete >> ");
                        var schedule_id = sc.next();
                        ps = connector.getConnection().prepareStatement(delete_statements[0]);
                        ps.setString(1, schedule_id);
                        ps.executeQuery();

                        connector.getConnection().createStatement().execute("");
                        sc.nextLine();
                case 3:
                    System.out.println("\nEnter the id of the pillow you wish to delete >> ");
                        var pillow_id = sc.next();
                        ps = connector.getConnection().prepareStatement(delete_statements[0]);
                        ps.setString(1, pillow_id);
                        ps.executeQuery();

                        connector.getConnection().createStatement().execute("");
                        sc.nextLine();
                case 4:
                    System.out.println("\nEnter the id of the module you wish to delete >> ");
                        var module_id = sc.next();
                        
                        ps = connector.getConnection().prepareStatement(delete_statements[0]);
                        ps.setString(1, module_id);
                        ps.executeQuery();

                        connector.getConnection().createStatement().execute("");
                        sc.nextLine();
                        
                case 5:
                    System.out.println("\nEnter the id of the theme you wish to delete >> ");
                        var theme_id = sc.next();
                    
                        ps = connector.getConnection().prepareStatement(delete_statements[0]);
                        ps.setString(1, theme_id);
                        ps.executeQuery();

                        connector.getConnection().createStatement().execute("");
                        sc.nextLine();
                    
                    } 
            }catch(Exception e){
                e.printStackTrace();
        }
    }
}
