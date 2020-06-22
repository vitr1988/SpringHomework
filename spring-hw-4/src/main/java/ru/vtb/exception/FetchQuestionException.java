package ru.vtb.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FetchQuestionException extends Exception {

    private final String message;
    private final Throwable originalException;
}
