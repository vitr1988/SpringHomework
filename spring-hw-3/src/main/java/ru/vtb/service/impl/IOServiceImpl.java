package ru.vtb.service.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vtb.service.IOService;

import java.io.InputStream;
import java.io.PrintStream;

@Service
@RequiredArgsConstructor
public class IOServiceImpl implements IOService {
    @Getter
    private final InputStream inputStream;
    private final PrintStream printStream;

    @Override
    public void println(String line) {
        printStream.println(line);
    }
}
