package Connection;

import java.io.*;
import java.net.Socket;
import java.net.SocketAddress;


/**
 * Класс, реализующий отправку и получение сообщений
 * @author Koziakov Nikolay
 */
public class Connection implements Closeable {
    /**  Поле сокета клиента   */
    private final Socket socket;
    /**  Поле потока чтения   */
    private final ObjectOutputStream out;
    /**  Поле потока отправки   */
    private final ObjectInputStream in;

    /**
     * Конструктор инициализирующий параметры для отправки - получения сообщений для конкретного пользователя
     * @param socket Сокет пользователя
     * @throws IOException
     */
    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());
    }

    /**
     * Отправка сообщения
     * @param message Сообщение
     * @throws IOException
     */
    public void send(Message message) throws IOException {
        synchronized (this.out) {
            out.writeObject(message);
        }
    }
    /**
     * Получение сообщения
     * @return Сообщение
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Message receive() throws IOException, ClassNotFoundException {
        synchronized (this.in) {
            Message message = (Message) in.readObject();
            return message;
        }
    }

    /**
     * Метод, убивающий все потоки
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
        in.close();
        out.close();
        socket.close();
    }

    /**
     * Получения IP - адреса пользователя
     * @return IP - адрес пользователя
     */
    public String getRemoteSocketAddress() {
        SocketAddress IP = socket.getRemoteSocketAddress();
        String ip=(((SocketAddress) socket.getRemoteSocketAddress()).toString().replace("/",""));
        String[] req = ip.split(":");
        return req[0];
    }
}
