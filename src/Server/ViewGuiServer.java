package Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Класс описывающий визуальную составляющую сервера
 * @author Koziakov Nikolay
 */
public class ViewGuiServer {
    private final JFrame frame = new JFrame("Запуск сервера");
    private final JTextArea dialogWindow = new JTextArea(10, 40);
    private final JButton buttonStartServer = new JButton("Запустить сервер");
    private final JButton buttonStopServer = new JButton("Остановить сервер");
    private final JPanel panelButtons = new JPanel();
    private final Server server;
    ImageIcon img = new ImageIcon("C:\\Users\\userv\\Desktop\\KP\\src\\Materials\\Server.png");

    /**
     * Конструктор для инициализации сервера
     * @param server Объект класса Server
     */
    public ViewGuiServer(Server server) {
        this.server = server;
    }

    /**
     * Инициализация окна сервера
     */
    protected void initFrameServer() {
        buttonStartServer.setBackground((new Color(224, 255, 255)));//Цвет кнопка Отправить;
        buttonStopServer.setBackground((new Color(224, 255, 255)));//Цвет кнопка Выйти;
        dialogWindow.setBackground(new Color(245, 245, 220));//Цвет сообщений

        dialogWindow.setEditable(false);
        dialogWindow.setLineWrap(true);
        frame.add(new JScrollPane(dialogWindow), BorderLayout.CENTER);
        panelButtons.add(buttonStartServer);
        panelButtons.add(buttonStopServer);
        frame.setIconImage(img.getImage());

        frame.add(panelButtons, BorderLayout.SOUTH);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                server.stopServer();
                System.exit(0);
            }
        });
        frame.setSize(500, 250);
        frame.setVisible(true);

        buttonStartServer.addActionListener(e -> {
            int port = 8000;
            server.startServer(port);
        });
        buttonStopServer.addActionListener(e -> server.stopServer());
    }

    /**
     * Обновление системных уведомлений
     * @param serviceMessage Системное уведомление
     */
    public void refreshDialogWindowServer(String serviceMessage) {
        dialogWindow.setFont(new Font("Dialog", Font.PLAIN, 14));
        dialogWindow.append(serviceMessage);
    }


}