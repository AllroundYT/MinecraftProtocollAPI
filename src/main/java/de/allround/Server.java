package de.allround;

public class Server {
    private static Server _instance;

    public static Server getInstance() {
        if (_instance == null) _instance = new Server();
        return _instance;
    }


}