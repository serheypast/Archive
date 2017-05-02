package by.bsuir.socket.implementations;

import by.bsuir.logger.AppLogger;
import by.bsuir.models.request.implementations.ClientRequest;
import by.bsuir.models.response.implementations.ServerResponse;
import by.bsuir.socket.interfaces.SocketDao;

import java.io.*;
import java.net.Socket;

/**
 * Created by Сергей on 15.04.2017.
 */
public class SocketDaoClient implements SocketDao {
    private Socket socket;
    private final static int SERVET_PORT = 2057;
    private final static String ADDRESS = "127.0.0.1";
    private ObjectOutput output;
    private ObjectInput input;


    public SocketDaoClient() {
    }

    public SocketDaoClient(Socket socket) {
        setSocketSingleton(socket);
    }

    public void setSocketSingleton(Socket _socket) {
        socket = (socket == null) ? _socket : socket;
        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            AppLogger.getLogger().error(e.getMessage());
        }
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public Object getMessage() {
        try {
            ClientRequest request = (ClientRequest) input.readObject();
            return request;
        }catch (ClassNotFoundException e){
           AppLogger.getLogger().error(e.getMessage());
        }catch (IOException e){
            AppLogger.getLogger().error(e.getMessage());
        }
        return null;
    }

    @Override
    public void createConn() {

    }

    @Override
    public void sendMessage(Object message)  {
        try {
            ServerResponse response = (ServerResponse) message;
            output.writeObject(response);
            output.flush();
        }catch (IOException e){
            AppLogger.getLogger().error(e.getMessage());
            AppLogger.getLogger().error("send error");
        }
    }

    @Override
    public void closeConn() {

    }


}
