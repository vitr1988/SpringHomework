package ru.vtb.service;

public interface IOService {

    /**
     * Reading string from input stream
     *
     * @return fetched string from stream
     */
    String nextLine();

    /**
     * Reading integer from input stream
     *
     * @return fetched value from stream
     */
    int nextInt();

    /**
     * Write string in output stream
     *
     * @param line string for printing
     */
    void println(String line);
}
