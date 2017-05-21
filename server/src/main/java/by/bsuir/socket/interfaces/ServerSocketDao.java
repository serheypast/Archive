package by.bsuir.socket.interfaces;

import java.net.Socket;

/**
 * Created by Сергей on 16.04.2017.
 */
public interface ServerSocketDao {
    public void createServerSocket();

    public void waitClient();

    public void createThreadClient(Socket socket);

    public void closeServerSocket();
}
