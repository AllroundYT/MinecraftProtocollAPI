import de.allround.network.Server;
import de.allround.player.PlayerConnection;

public class Test {
    public static void main(String[] args) {
        Server server = new Server();
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
        server.bind("127.0.0.1",25565).onSuccess(unused -> {
            System.out.println("Server started!");
        });
    }
}
