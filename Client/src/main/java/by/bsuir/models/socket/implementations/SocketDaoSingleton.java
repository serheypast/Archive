package by.bsuir.models.socket.implementations;

import by.bsuir.logger.AppLogger;
import by.bsuir.models.request.implementations.ClientRequest;
import by.bsuir.models.request.interfaces.Request;
import by.bsuir.models.response.implementations.ServerResponse;
import by.bsuir.models.socket.interfaces.SocketDao;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Сергей on 15.04.2017.
 */
public class SocketDaoSingleton implements SocketDao {
    private static SocketDaoSingleton socketSingelton;
    private final static int SERVET_PORT = 2057;
    private final static String ADDRESS = "127.0.0.1";
    private ObjectOutput output;
    private ObjectInput input;


    public SocketDaoSingleton() {
    }

    public static SocketDaoSingleton getSocketSingelton() {
        return (socketSingelton == null) ? new SocketDaoSingleton() : socketSingelton;
    }

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

    @Override
    public void createConn() {
        try {
            InetAddress ipAddress = InetAddress.getByName(ADDRESS); // создаем объект который отображает вышеописанный IP-адрес.
            Socket socket = new Socket(ipAddress, SERVET_PORT); // создаем сокет используя IP-адрес и порт сервера.
            // Берем входной и выходной потоки сокета, теперь можем получать и отсылать данные клиентом.
            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();

            output = new ObjectOutputStream(sout);
            input = new ObjectInputStream(sin);

        }catch (Exception e){
            AppLogger.getLogger().error(e.getMessage());
        }
    }

    @Override
    public void sendMessage(Object message)  {
        try {
            ClientRequest request = (ClientRequest) message;
            output.writeObject(request);
        }catch (IOException e){
            AppLogger.getLogger().error(e.getMessage());
        }
    }

    @Override
    public void closeConn() {

    }


}
