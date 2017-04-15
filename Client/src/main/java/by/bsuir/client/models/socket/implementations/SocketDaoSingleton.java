package by.bsuir.client.models.socket.implementations;

import by.bsuir.client.logger.AppLogger;
import by.bsuir.client.models.socket.interfaces.SocketDao;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Сергей on 15.04.2017.
 */
public class SocketDaoSingleton implements SocketDao {
    private static SocketDaoSingleton socketSingelton;
    private Socket _socket;
    private final static int SERVET_PORT = 2057;
    private final static String ADDRESS = "127.0.0.1";
    public SocketDaoSingleton() {
    }

    public static SocketDaoSingleton getSocketSingelton() {
        return (socketSingelton == null) ? new SocketDaoSingleton() : socketSingelton;
    }

    @Override
    public Object getMessage() {
        return null;
    }

    @Override
    public void createConn() {
        try {
            InetAddress ipAddress = InetAddress.getByName(ADDRESS); // создаем объект который отображает вышеописанный IP-адрес.
            Socket socket = new Socket(ipAddress, SERVET_PORT); // создаем сокет используя IP-адрес и порт сервера.
            // Берем входной и выходной потоки сокета, теперь можем получать и отсылать данные клиентом.
            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();

            // Конвертируем потоки в другой тип, чтоб легче обрабатывать текстовые сообщения.
            DataInputStream in = new DataInputStream(sin);
            DataOutputStream out = new DataOutputStream(sout);


        }catch (Exception e){
            AppLogger.getLogger().error(e.getMessage());
        }
    }

    @Override
    public void sendMessage(Object message) {

    }

    @Override
    public void closeConn() {

    }


}
