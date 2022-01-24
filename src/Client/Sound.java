package Client;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Класс, описывающий работу с аудио составляющей
 * @author Koziakov Nikolay
 */
public class Sound implements AutoCloseable {
    /**  Поле успешности загрузки звука   */
    private boolean released = false;
    /**  Поле потока воспроизведения   */
    private AudioInputStream stream = null;
    /**  Поле класса Clip   */
    private Clip clip = null;
    /**  Поле класса FloatControl   */
    private FloatControl volumeControl = null;
    /**  Поле проверки воспроизведения   */
    private boolean playing = false;

    /**
     * Конструктор, инициализирующий класс Sound
     * @param f Объект класса File
     */
    public Sound(File f) {
        try {
            stream = AudioSystem.getAudioInputStream(f);
            clip = AudioSystem.getClip();
            clip.open(stream);
            clip.addLineListener(new Listener());
            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            released = true;
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException exc) {
            exc.printStackTrace();
            released = false;

            close();
        }
    }

    // true если звук успешно загружен, false если произошла ошибка
    public boolean isReleased() {
        return released;
    }

    /**
     * Проверка, проигрывается звук в данный момент или нет
     * @return Проигрывается или Нет
     */
    // проигрывается ли звук в данный момент
    public boolean isPlaying() {
        return playing;
    }

    // Запуск
	/*
	  breakOld определяет поведение, если звук уже играется
	  Если breakOld==true, о звук будет прерван и запущен заново
	  Иначе ничего не произойдёт
	*/

    /**
     * Запуск звука
     * @param breakOld Проверка, проигрывается ли звук
     */
    public void play(boolean breakOld) {
        if (released) {
            if (breakOld) {
                clip.stop();
                clip.setFramePosition(0);
                clip.start();
                playing = true;
            } else if (!isPlaying()) {
                clip.setFramePosition(0);
                clip.start();
                playing = true;
            }
        }
    }

    /**
     * Запуск звука
     */
    // То же самое, что и play(true)
    public void play() {
        play(true);
    }

    /**
     * Остановка воспроизведение
     */
    // Останавливает воспроизведение
    public void stop() {
        if (playing) {
            clip.stop();
        }
    }

    /**
     * Остановка потока
     */
    public void close() {
        if (clip != null)
            clip.close();

        if (stream != null)
            try {
                stream.close();
            } catch (IOException exc) {
                exc.printStackTrace();
            }
    }

    // Установка громкости
	/*
	  x долже быть в пределах от 0 до 1 (от самого тихого к самому громкому)
	*/

    /**
     * Изменение громкости
     * @param x Громкость
     */
    public void setVolume(float x) {
//        if (x<0) x = 0;
//        if (x>1) x = 1;
        float min = volumeControl.getMinimum();
        float max = volumeControl.getMaximum();
        volumeControl.setValue((max-min)*x+min);
    }

    /**
     * Метод, для получения текущей громкости
     * @param value Значение громкости
     * @return Текущая громкость
     */
    // Возвращает текущую громкость (число от 0 до 1)
    public float getVolume(int value) {
        float v = volumeControl.getValue();
        float min = volumeControl.getMinimum();
        float max = volumeControl.getMaximum();
        return (v-min)/(max-min);
    }

    /**
     *
     */
    // Дожидается окончания проигрывания звука
    public void join() {
        if (!released) return;
        synchronized(clip) {
            try {
                while (playing)
                    clip.wait();
            } catch (InterruptedException exc) {}
        }
    }

    /**
     *
     * @param path
     * @return
     */
    // Статический метод, для удобства
    public static Sound playSound(String path) {
        File f = new File(path);
        Sound snd = new Sound(f);
        snd.play();
        return snd;
    }

    /**
     *
     */
    private class Listener implements LineListener {
        public void update(LineEvent ev) {
            if (ev.getType() == LineEvent.Type.STOP) {
                playing = false;
                synchronized(clip) {
                    clip.notify();
                }
            }
        }
    }
}