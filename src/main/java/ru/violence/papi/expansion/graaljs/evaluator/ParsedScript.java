package ru.violence.papi.expansion.graaljs.evaluator;

public interface ParsedScript {
    String onPlaceholderRequest(Object... params);

    String onRelPlaceholderRequest(Object... params);

    void terminate();
}
