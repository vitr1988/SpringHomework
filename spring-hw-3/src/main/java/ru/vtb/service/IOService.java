package ru.vtb.service;

import java.io.InputStream;

public interface IOService {
    InputStream getInputStream();
    void println(String line);
}
