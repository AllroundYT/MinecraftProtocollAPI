package de.allround;

import de.allround.network.Server;
import de.allround.player.PlayerConnection;
import org.jetbrains.annotations.NotNull;

import java.net.InetSocketAddress;

public class MinecraftServer {
    private static MinecraftServer _instance;
    private final Server server;

    public static MinecraftServer getInstance() {
        if (_instance == null) _instance = new MinecraftServer();
        return _instance;
    }

    private boolean started;


    public MinecraftServer() {
        this.server = new Server();
        server.handleClient(client -> {
            System.out.println("Client connected: " + client.getRemoteAddress());
            client.handleClose(throwable -> {
                System.out.println("Server closed: " + client.getRemoteAddress());
                if (throwable != null){
                    throwable.printStackTrace();
                }
            });
            new PlayerConnection(client);
        });
    }

    public void start(){
        start("127.0.0.1",25565);
    }

    public void start(int port){
        start("127.0.0.1",port);
    }

    public void start(@NotNull InetSocketAddress socketAddress){
        start(socketAddress.getAddress().getHostAddress(), socketAddress.getPort());
    }

    public void start(String host, int port){
        if (started) return;
        server.bind(host, port).onSuccess(unused -> {
            System.out.println("Server started!");
            started = true;
        });
    }

    public static void main(String[] args) {
        MinecraftServer.getInstance().start();
    }
}