package by.bsuir.socket.implementations;

import by.bsuir.logger.AppLogger;
import by.bsuir.models.request.implementations.ClientRequest;
import by.bsuir.models.response.implementations.ServerResponse;
import by.bsuir.socket.interfaces.SocketDao;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Сергей on 15.04.2017.
 */
public class SocketDaoSingleton implements SocketDao {
    /** a socket on which the client connected with server */
    private static SocketDaoSingleton socketSingelton;
    /**Object socket */
    private Socket socket;
    /**Server port*/
    private final static int SERVET_PORT = 2057;
    /**IP for connected with server*/
    private final static String ADDRESS = "127.0.0.1";
    /**stream for input/output object in/from socket */
    private ObjectOutput output;
    private ObjectInput input;


    public SocketDaoSingleton() {
    }

    /**
     * Get a socket on which the client connected with server
     * @return object socketSingleton
     */
    public static SocketDaoSingleton getSocketSingelton() {
        return (socketSingelton == null) ? new SocketDaoSingleton() : socketSingelton;
    }

    /**
     * Get message from server
     * @return Response object where are save server response
     */
    @Override
    public Object getMessage() {
        try {
            ServerResponse response = (ServerResponse)input.readObject();
            return response;
        }catch (Exception e){
            AppLogger.getLogger().error(e.getMessage());
            return null;
        }

    }

    /**
     *
     * create socket connection with server use some port and Ip
     *
     */
    @Override
    public void createConn() {
        try {
            InetAddress ipAddress = InetAddress.getByName(ADDRESS); // создаем объект который отображает вышеописанный IP-адрес.
            socket = new Socket(ipAddress, SERVET_PORT); // создаем сокет используя IP-адрес и порт сервера.
            // Берем входной и выходной потоки сокета, теперь можем получать и отсылать данные клиентом.
            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();

            output = new ObjectOutputStream(sout);
            input = new ObjectInputStream(sin);
            socketSingelton = this;
            AppLogger.getLogger().info("Create connection");
        }catch (Exception e){
            AppLogger.getLogger().error(e.getMessage());
        }
    }
    /**
     * Send message to server
     * @param message send to server request object from client
     */
    @Override
    public void sendMessage(Object message)  {
        try {
            ClientRequest request = (ClientRequest) message;
            output.writeObject(request);
            output.flush();
        }catch (IOException e){
            AppLogger.getLogger().error(e.getMessage());
        }
    }


}
