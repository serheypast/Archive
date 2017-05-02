package by.bsuir.socket.interfaces;

/**
 * Created by Сергей on 15.04.2017.
 */
public interface SocketDao {

    /**
     * create socket connection with server use some port and Ip
     */
    public void createConn();

    /**
     * Get message from server
     * @return Response object where are save server response
     */
    public Object getMessage();

    /**
     * Send message to server
     * @param message
     */
    public void sendMessage(Object message);


}
