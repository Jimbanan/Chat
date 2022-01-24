package File;

/**
 * Данный класс описывает файл, отправленный пользователем
 * @author Koziakov Nikolay
 */
public class MyFile {

    private String name;
    private byte[] data;

    /**
     * Конструктор, инициализирующий, файл
     * @param name - Имя файла
     * @param data - Байтовое представление файла
     */
    public MyFile(String name, byte[] data){

        this.name = name;
        this.data = data;

    }

    /**
     * Геттер, возвращающий имя файла
     * @return Возврат имени файла
     */
    public String getName() {
        return name;
    }

    /**
     * Сеттер, устанавливающий имя файла
     * @param name Устанавливает имя файла
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Геттер, возвращающий байтовое представление файла
     * @return Возврат массива byte
     */
    public byte[] getData() {
        return data;
    }

    /**
     * Сеттер, устанавливающий байтовое представление файла
     * @param data Устанавливает байтовое представление файла
     */
    public void setData(byte[] data) {
        this.data = data;
    }
}

