package utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Menus {
    
    private ResultSet rs;
    private PreparedStatement ps;

    /**** SQL statements that are used in prepared statements ****/
    private final String[] selectStatements = {"SELECT * FROM user where user.user_id = ?", "SELECT user.name as Name, user.email as Email " +
        "phone.imei as IMEI, phone.mac AS MAC FROM user INNER JOIN phone on phone.user_id = user.user_id WHERE user.name = ?", 
        "SELECT user.name, schedule.alarm_date, schedule.name FROM user INNER JOIN schedule ON schedule.user_id = user.user_id WHERE user.name = ?"};

    private final String[] deleteStatements = {"DELETE FROM user WHERE name = ?", "DELETE FROM schedule WHERE name = ?", 
        "DELETE FROM pillow WHERE pillow_id = ?", "DELETE FROM bluetooth_module WHERE module_id = ?", "DELETE FROM theme WHERE theme_id = ?"};

    private final String[] insertStatements = {"INSERT INTO user(email, password, name) VALUES(?, ?, ?)", 
        "INSERT INTO schedule(alarm_date, user_id, schedule_name) VALUES(?, ?, ?)", "INSERT INTO pillow(model, user_id, version_number)",
        "INSERT INTO bluetooth_module(mac, uuid, pillow_id) VALUES(?, ?, ?)", "INSERT INTO theme(color, pattern, movement, speed, schedule_id) VALUES(?, ?, ?, ?, ?)"};

    private final String[] updateStatements = {};
    
    private final Map<Integer, String[]> statements = new HashMap<Integer, String[]>() {{
        put(1, selectStatements);
        put(2, deleteStatements);
        put(3, insertStatements);
        put(4, updateStatements);
    }};

    private final Map<Integer, String> funcTypes = new HashMap<Integer, String>() {{
        put(1, "SELECT");
        put(2, "DELETE");
        put(3, "INSERT");
        put(4, "UPDATE");
    }};

    private final Map<Integer, String> tableNames = new HashMap<Integer, String>(){{
        put(1, "User");
        put(2, "Phone");
        put(3, "Pillow");
        put(4, "Schedule");
    }};


    public void generalMenu(Scanner sc, int option, Connector connector){

        Menus menus = new Menus();
        String[] prepStmt = getPrepStmt(option);
        String funcType = getFuncType(option);

        System.out.printf("\nWhat table do you want to %s?\n", funcType);
        System.out.println("1. User\t2.Phone");
        System.out.println("2.Pillow\tSchedule");

        var input = sc.nextLine();
        while (menus.checkInput(input)) {
            System.out.println("\nInput has to be a number\n");
            System.out.println("1. SELECT\n2. UPDATE\n3. INSERT\n4. DELETE\n>> ");
            input = sc.nextLine();
        }
        var tableName = funcTypes.get(Integer.parseInt(input));

        System.out.printf("\n1. %s all information about a %s\n2. "
        + "%s phone information about a %s\n3. %s schedule for a %s\n",
            funcType, tableName);

        try{
            System.out.printf("\nEnter the ID of the %s \n>> ", tableName);
            switch(option){
                case 1:
                    String param = sc.nextLine();
                    ps = connector.getConnection().prepareStatement(prepStmt[0]);
                    ps.setString(1, param);
                    rs =  ps.executeQuery();
                    connector.displayQuery(rs);
                    sc.nextLine();
                    break;
                case 2:
                    var phone_user = sc.next();
                    ps = connector.getConnection().prepareStatement(prepStmt[1]);
                    ps.setString(1, phone_user);
                    rs =  ps.executeQuery();
                    connector.displayQuery(rs);
                    sc.nextLine();
                    break;
                case 3:
                    var schedule_user = sc.next();
                    ps = connector.getConnection().prepareStatement(prepStmt[2]);
                    ps.setString(1, schedule_user);
                    rs =  ps.executeQuery();
                    connector.displayQuery(rs);
                    sc.nextLine();
                    break;
                case 4:
            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    private String getFuncType(int option) {
        return funcTypes.get(option);
    }

    private String[] getPrepStmt(int option) {
        return statements.get(option);
    }
    private boolean checkInput(String input){
        return input.matches("^[1-9]\\d*$");
    }
    private String getTableName(int option){
        return tableNames.get(option);
    }
}