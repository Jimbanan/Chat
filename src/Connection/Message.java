package Connection;

import java.io.Serializable;
import java.util.Set;

/**
 * Класс описывающий тип сообщений
 * @author Koziakov Nikolay
 */
public class Message implements Serializable {
    private MessageType typeMessage;
    private String textMessage;
    private String fileName;
    private byte[] File;
    private Set<String> listUsers;

    /**
     * Конструктор для текстового сообщения
     * @param typeMessage Тип сообщения
     * @param textMessage Текст сообщения
     */
    public Message(MessageType typeMessage, String textMessage) {
        this.textMessage = textMessage;
        this.typeMessage = typeMessage;
        this.listUsers = null;
    }

    /**
     * Конструктор для взаимодействия с пользовательскими уведомлениями
     * @param typeMessage Тип сообщения
     * @param listUsers Список пользователей
     */
    public Message(MessageType typeMessage, Set<String> listUsers) {
        this.typeMessage = typeMessage;
        this.textMessage = null;
        this.listUsers = listUsers;
    }

    /**
     * Конструктор для системных уведомлений
     * @param typeMessage Тип сообщения
     */
    public Message(MessageType typeMessage) {
        this.typeMessage = typeMessage;
        this.textMessage = null;
        this.listUsers = null;
    }

    /**
     * Конструктор для отправки и получения файла
     * @param typeMessage Тип сообщений
     * @param fileContentBytes Байтовое представление файла
     * @param fileName Название файла
     */
    public Message(MessageType typeMessage, byte[] fileContentBytes, String fileName) {
        this.typeMessage = typeMessage;
        this.File = fileContentBytes;
        this.fileName = fileName;
    }

    /**
     * Геттер возвращающий тип сообщения
     * @return Тип сообщений
     */
    public MessageType getTypeMessage() {
        return typeMessage;
    }

    /**
     * Геттер, возвращающий список пользователей
     * @return Список пользователей
     */
    public Set<String> getListUsers() {
        return listUsers;
    }

    /**
     * Геттер, возвращающий текст сообщений
     * @return Текст сообщений
     */
    public String getTextMessage() {
        return textMessage;
    }

    /**
     * Геттер, возвращающий байтовое представление файла
     * @return Байтовое представление файла
     */
    public byte[] getFile() {
        return File;
    }

    /**
     * Геттер, возвращающий имя файла
     * @return Имя файла
     */
    public String getFileName() {
        return fileName;
    }
}
