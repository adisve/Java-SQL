package utils;

public interface ConnectionUtils {
    
    public abstract void query();

    public abstract String displayQuery();

    public abstract void close();

    public abstract Boolean isConnected();
}
