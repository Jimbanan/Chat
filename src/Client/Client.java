package Client;

import Connection.*;
import DataBase.DataBase;
import File.MyFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Данный класс, является точкой входа и содержит всю основную логику по работе клиента
 * @author Koziakov Nikolay
 */
public class Client {
    private Connection connection;
    private static ModelGuiClient model;
    private static ViewGuiClient gui;
    Socket socket;
    ArrayList<MyFile> files = new ArrayList<MyFile>();

    private volatile boolean isConnect = false; //флаг отобаржающий состояние подключения клиента  серверу
    // Массив для ввода строки с клавиатуры

    /**
     * Данный метод возвращает флаг о состоянии подключения клиента
     * @return Состояние подключения клиента
     */
    public boolean isConnect() {
        return isConnect;
    }

    /**
     * Днный метод устанавливает флаг о состоянии подключения пользователя
     * @param connect Подключение клиента
     */
    public void setConnect(boolean connect) {
        isConnect = connect;
    }

    /**
     * Точка входа в клиентскую часть программы
     * @param args
     */
    //точка входа в клиентское приложение
    public static void main(String[] args) {
        Client client = new Client();

        model = new ModelGuiClient();
        gui = new ViewGuiClient(client);

        System.out.println(gui.n);

        if (gui.n == 1) {
            client.userRegistration();
        }
        gui.initFrameClient();

        while (true) {

            if (gui.n == 0) {
                if (client.isConnect()) {

                    gui.connectVisible();
                    client.nameUserRegistration();
                    client.receiveMessageFromServer();
                    client.receiveFileFromServer();
                    client.setConnect(false);


                }
            }

            if (gui.n == 1) {
                if (client.isConnect()) {
                    client.nameUserRegistration();
                    client.receiveMessageFromServer();
                    client.receiveFileFromServer();
                    client.setConnect(false);
                }
            }

            if (gui.n == 2) {
                System.exit(1);
            }
        }
    }

    /**
     * Данный метод реализует создание нового объекта класса Socket с дальнейшей передачей данного объекта в класс Connection
     */
    protected void connectToServer() {
        if (!isConnect) {
            while (true) {
                try {
                    String addressServer = "127.0.0.1";
                    int port = 8000;
                    socket = new Socket(addressServer, port);
                    connection = new Connection(socket);
                    isConnect = true;
                    gui.addMessage("Сервисное сообщение: Вы подключились к серверу.\n");
                    break;
                } catch (Exception e) {
                    gui.errorDialogWindow("Произошла ошибка! Возможно Вы ввели не верный адрес сервера или порт. Попробуйте еще раз");
                    break;
                }
            }
        } else gui.errorDialogWindow("Вы уже подключены!");
    }

    /**
     * Данный метод реализует проверку введенных данных пользователя при авторизации с данными, хранящимися в базе данных.
     * Если пользователь ввел корректные данные, то авторизация проходит успешно и в базу данных записывается IP – адрес его
     * последней авторизации
     */
    protected void nameUserRegistration() {
        String login = gui.getLogin();
        String passwordUser = gui.getPasswordUser();

        while (true) {

            DataBase dataBase = new DataBase();

            if (dataBase.find(login, passwordUser) == true) {


                try {
                    Message message = connection.receive();
                    if (message.getTypeMessage() == MessageType.REQUEST_NAME_USER) {

                        //Ввод Логина
                        connection.send(new Message(MessageType.USER_NAME, login));
                        String IP = connection.getRemoteSocketAddress();
                        //Запись в бд об IP
                        System.out.println(IP);
                        dataBase.ip(IP);

                    }
                    if (message.getTypeMessage() == MessageType.NAME_ACCEPTED) {
                        gui.addMessage("Сервисное сообщение: ваше имя принято!\n");
                        model.setUsers(message.getListUsers());
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    gui.errorDialogWindow("Произошла ошибка при регистрации имени. Попробуйте переподключиться");
                    try {
                        connection.close();
                        isConnect = false;
                        break;
                    } catch (IOException ex) {
                        gui.errorDialogWindow("Ошибка при закрытии соединения");
                    }
                }
            } else {
                gui.errorDialogWindow("Введены неверные данные");
                login = gui.getLogin();
                passwordUser = gui.getPasswordUser();
            }

        }
    }

    /**
     * Данный метод осуществляет регистрацию пользователей и записывает данные в ба-зу данных для возможности дальнейшей авторизации
     * и работы с данным пользователем. При регистрации, данный метод осуществляет проверку на уникальность «Логина»
     */
    protected void userRegistration() {
        try {
            DataBase dataBase = new DataBase();
            String login = gui.getLogin();
            while (dataBase.findLogin(login) == false) {
                gui.errorReg("Такой логин уже имеется");
                login = gui.getLogin();
            }

            String passwordUser = gui.getPasswordUser();
            String nameUser = gui.getNameUser();
            String surname = gui.getSurnameUser();


            //Ввод Логина
            dataBase.addUser(login, passwordUser, nameUser, surname);//Добавить IP
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Данный метод позволяет пользователям отправлять файлы. Выбранный пользователем файл, переводится в массив byte и
     * отправляется на сервер, где происходит рассылка всем подключенным пользователям
     * @param name Объект класса File
     */
    protected void sendFile(File name) {
        System.out.println(name.getName());
        final File[] fileToSend = new File[1];
        fileToSend[0] = name;
        try {
//            connection.send(new Message(MessageType.TEXT_MESSAGE, name.getName()));

            String fileName = fileToSend[0].getName();
            byte[] fileNameBytes = fileName.getBytes();

            byte[] fileContentBytes = new byte[(int) fileToSend[0].length()];

//            connection.send(new Message(MessageType.FILE_MESSAGE, name.getName()));

//            for (int i = 0; i < fileContentBytes.length; i++) {
//                System.out.print(fileContentBytes[i] + " ");
//            }
//            System.out.println(fileContentBytes);
//            connection.send(new Message(MessageType.FILE_MESSAGE, fileContentBytes));

            FileInputStream fileInputStream = new FileInputStream(fileToSend[0].getAbsolutePath());
////
//            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
//
            fileInputStream.read(fileContentBytes);
////
//            dataOutputStream.writeInt(fileNameBytes.length);
//            dataOutputStream.write(fileNameBytes);
////
//            dataOutputStream.writeInt(fileContentBytes.length);
//            dataOutputStream.write(fileContentBytes);

            for (int i = 0; i < fileContentBytes.length; i++) {
                System.out.print(fileContentBytes[i] + " ");
            }
//            System.out.println(fileContentBytes);

//            MyFile myFile = new MyFile(0, fileName, fileContentBytes, null);

//            connection.send(new Message(MessageType.FILE_MESSAGE, fileContentBytes, fileName));
            connection.send(new Message(MessageType.FILE_MESSAGE, fileContentBytes, fileName));


        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    /**
     * Данный метод считывает массив byte, полученный от сервера и позволяет каждому пользователю работать с данным файлом дальше
     */
    protected void receiveFileFromServer() {
        while (isConnect) {
            try {
                Message message = connection.receive();
                if (message.getTypeMessage() == MessageType.FILE_MESSAGE) {
//                    gui.addMessage(message.getTextMessage());
                }
            } catch (Exception e) {
                gui.errorDialogWindow("Ошибка при приеме сообщения от сервера.");
                setConnect(false);
                break;
            }
        }
    }

    /**
     * Данный метод отправляет сообщения, написанные пользователями на сервер, предварительно, считав нынешнее время с помощью
     * объеков классов Date и SimpleDateFormat и записав данное сообщение в базу данных
     * @param text Текст сообщения
     */
    protected void sendMessageOnServer(String text) {
        try {
            connection.send(new Message(MessageType.TEXT_MESSAGE, text));
            DataBase dataBase = new DataBase();

            Date dateNow = new Date();
            SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");


            dataBase.addMessage(text, formatForDateNow.format(dateNow));

            System.out.println(text);

        } catch (Exception e) {
            gui.errorDialogWindow("Ошибка при отправки сообщения");
        }
    }

    /**
     * Данный метод считывает сообщения, полученные от сервера, проверяя тип сообщения
     */
    protected void receiveMessageFromServer() {
        while (isConnect) {
            try {
                Message message = connection.receive();
                if (message.getTypeMessage() == MessageType.TEXT_MESSAGE) {
                    gui.addMessage(message.getTextMessage());
                }
                if (message.getTypeMessage() == MessageType.FILE_MESSAGE) {

                    addListFiles(message);

                    gui.addFile(files);

                }
                if (message.getTypeMessage() == MessageType.USER_ADDED) {
                    model.addUser(message.getTextMessage());
                    gui.refreshListUsers(model.getUsers());
                    gui.addMessage(String.format("Сервисное сообщение: пользователь %s присоединился к чату.\n", message.getTextMessage()));
                }
                if (message.getTypeMessage() == MessageType.REMOVED_USER) {
                    model.removeUser(message.getTextMessage());
                    gui.refreshListUsers(model.getUsers());
                    gui.addMessage(String.format("Сервисное сообщение: пользователь %s покинул чат.\n", message.getTextMessage()));
                }
            } catch (Exception e) {
                gui.errorDialogWindow("Ошибка при приеме сообщения от сервера.");
                setConnect(false);
                gui.refreshListUsers(model.getUsers());
                break;
            }
        }
    }

    /**
     * Данный метод, записывает все файлы, полученный с момента авторизации пользователя в соответствующий список
     * @param message Объект класса Message
     */
    protected void addListFiles(Message message) {
        files.add(new MyFile(message.getFileName(), message.getFile()));
    }

    /**
     * Данный метод реализует выход пользователя из чата, с переключением флага о подключении пользователя в соответствующее
     * состояние и отправкой сообщения о выходе данного пользователя из чата.
     */
    protected void disableClient() {
        try {
            if (isConnect) {
                connection.send(new Message(MessageType.DISABLE_USER));
                model.getUsers().clear();
                isConnect = false;
                gui.refreshListUsers(model.getUsers());
            } else gui.errorDialogWindow("Вы уже отключены.");
        } catch (Exception e) {
            gui.errorDialogWindow("Сервисное сообщение: произошла ошибка при отключении.");
        }
    }
}
