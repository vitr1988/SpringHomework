package ru.vtb.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.vtb.service.IOService;

import javax.annotation.PreDestroy;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

@Service
public class IOServiceImpl implements IOService {

    private final Scanner scanner;
    private final PrintStream printStream;

    public IOServiceImpl(@Value("#{T(java.lang.System).in}") InputStream inputStream,
                         @Value("#{T(java.lang.System).out}") PrintStream printStream) {
        this.scanner = new Scanner(inputStream);
        this.printStream = printStream;
    }

    @Override
    public void println(String line) {
        printStream.println(line);
    }

    @Override
    public String nextLine() {
        return scanner.nextLine();
    }

    @Override
    public int nextInt() {
        return scanner.nextInt();
    }

    @PreDestroy
    public void shutdown() {
        scanner.close();
    }
}
