package ru.vtb.service;

/**
 * Сервис работы с вводом/выводом
 */
public interface IOService {

    /**
     * Чтение строки из потока ввода
     *
     * @return получение считанной строки
     */
    String nextLine();

    /**
     * Чтение целого значения из потока ввода
     *
     * @return получение считанного значения
     */
    int nextInt();

    /**
     * Запись строки в поток вывода
     *
     * @param line строка для печати
     */
    void println(String line);
}
