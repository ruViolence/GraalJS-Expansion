package ru.violence.papi.expansion.graaljs.evaluator;

public class DummyParsedScript implements ParsedScript {
    private static final String DUMMY_TEXT = "Not running GraalVM!";

    @Override
    public String onPlaceholderRequest(Object... params) {
        return DUMMY_TEXT;
    }

    @Override
    public String onRelPlaceholderRequest(Object... params) {
        return DUMMY_TEXT;
    }

    @Override
    public void terminate() {

    }
}
