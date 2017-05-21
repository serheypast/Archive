package by.bsuir.socket.implementations;

import by.bsuir.clientThread.implementations.ClientThreadDao;
import by.bsuir.logger.AppLogger;
import by.bsuir.socket.interfaces.ServerSocketDao;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Сергей on 16.04.2017.
 */
public class ServerSocketDaoSingleton implements ServerSocketDao {
    /**
     * Main socket
     */
    private static ServerSocketDaoSingleton socketSingleton;
    private final static int SERVER_PORT = 2057;
    ServerSocket serverSocket;

    @Override
    public void createServerSocket() {
        try {
            serverSocket = new ServerSocket(SERVER_PORT); // создаем сокет сервера и привязываем его к вышеуказанному порту
        } catch (IOException e) {
            AppLogger.getLogger().error(e.getMessage());
        }
        AppLogger.getLogger().info("Create ServerSocket");
    }

    @Override
    public void waitClient() {
        try {
            while(true) {
                int i = 0;
                final Socket socket = serverSocket.accept();
                System.out.println("Client connected");
                createThreadClient(socket);
                System.out.println(i++);
            }
        } catch (IOException e) {
            AppLogger.getLogger().error(e.getMessage());
        }

    }

    @Override
    public void createThreadClient(Socket socket) {
        SocketDaoClient socketDaoSingleton = new SocketDaoClient(socket);
        Thread thread = new Thread(new ClientThreadDao(socketDaoSingleton));
        thread.start();
    }

    @Override
    public void closeServerSocket() {

    }
}
