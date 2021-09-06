package com.best.javaSdk.converter.util.jsonReader;

public interface JSONErrorListener {
    void start(String text);
    void error(String message, int column);
    void end();
}
