import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;

public class Client implements Serializable{

    static Socket socket = null;

    public static void main(String[] args) {

        socket = null;
        try {
            socket = new Socket("localhost", 8080);
            new Client(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    Client(Socket socket) {

        new LoginPage(socket);

    }


}