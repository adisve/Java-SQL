package utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface ConnectionUtils {

    public abstract ResultSet query(String string);

    public abstract int update(String string);

    public abstract void displayQuery(ResultSet result);

    public abstract void close();

    public abstract Boolean isConnected() throws SQLException;

    public abstract Connection getConnection();

    public abstract String getHost();

}
